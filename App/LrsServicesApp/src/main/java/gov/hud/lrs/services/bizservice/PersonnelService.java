package gov.hud.lrs.services.bizservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewerAssignmentTypeDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewerCreationDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewerDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewerLiteDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewerUnavailabilityDTO;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.PersonnelStatusRef;
import gov.hud.lrs.common.entity.PersonnelUnavailability;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.enumeration.PersonnelStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.enumeration.Roles;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.ForbiddenException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.PersonnelAssignmentTypeRepository;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.repository.PersonnelStatusRefRepository;
import gov.hud.lrs.common.repository.PersonnelUnavailabilityRepository;
import gov.hud.lrs.common.repository.ReviewLevelRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.ReviewRepositoryCustom.PersonnelIdReviewCount;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.util.DateUtils;
import gov.hud.lrs.common.util.Util;
import gov.hud.lrs.services.util.MessageService;

@Service
public class PersonnelService {

	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private PersonnelAssignmentTypeRepository personnelAssignmentTypeRepository;
	@Autowired private PersonnelStatusRefRepository personnelStatusRefRepository;
	@Autowired private PersonnelUnavailabilityRepository personnelUnavailabilityRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private ReviewLevelRepository reviewLevelRepository;
	@Autowired private ReviewLocationRepository reviewLocationRepository;

	@Autowired private MessageService messageService;
	@Autowired private SecurityService securityService;

	@PersistenceContext EntityManager entityManager;

	private static final String personnelJpql =
		"select distinct p from Personnel p " +
		"join fetch p.personnelStatusRef ps " +
		"join fetch p.reviewLocation " +
		"left join fetch p.reportsToPersonnel " +
		"left join fetch p.vettingPersonnel " +
		"left join fetch p.personnelUnavailabilities " +
		"where (ps.code in (:personnelStatusCodes)) ";

	// this abomination is only needed because we don't have a direct foreign key from assignment type to it's various ref tables
	private static final String assignmentTypeRowsSql =
		"SELECT PAT.PERSONNEL_ID, AT.ASSIGNMENT_TYPE_CATEGORY, AT.ASSIGNMENT_TYPE_REF_ID, COALESCE(SR.CODE, RT.REVIEW_TYPE_CD, PT.PRODUCT_TYPE_CD, RLT.REVIEW_LEVEL_CD) " +
		"FROM PERSONNEL_ASSIGNMENT_TYPE PAT " +
		"INNER JOIN ASSIGNMENT_TYPE_ADMIN AT ON (AT.ASSIGNMENT_TYPE_CD = PAT.ASSIGNMENT_TYPE_CD) " +
		"LEFT JOIN CONSOLIDATED_SELECTION_REASON SR ON (SR.CONSOLIDATED_SELECTION_REASON_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
		"LEFT JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
		"LEFT JOIN PRODUCT_TYPE_REF PT ON (PT.PRODUCT_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
		"LEFT JOIN REVIEW_LEVEL_TYPE_REF RLT ON (RLT.REVIEW_LEVEL_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
		"WHERE (PAT.PERSONNEL_ID IN (:personnelIds)) ";

	private class AssignmentType {

		public AssignmentType(String category, String refId, String refCode) {
			this.category = category;
			this.refId = refId;
			this.refCode = refCode;
		}

		public String category;
		public String refId;
		public String refCode;

	}

	// returns personnelId -> assignmentType
	@SuppressWarnings("unchecked")
	private Multimap<String, AssignmentType> getPersonnelIdToAssignmentTypes(List<String> personnelIds) {
		List<Object[]> rows = new ArrayList<Object[]>();
		for (int start = 0; start < personnelIds.size(); ) {
			int end = Math.min(start + 500, personnelIds.size());

			rows.addAll(entityManager
				.createNativeQuery(assignmentTypeRowsSql)
				.setParameter("personnelIds", personnelIds.subList(start, end))
				.getResultList()
			);

			start = end;
		}

		return Util.multiindex(
			rows,
			r -> (String)r[0],
			r -> new AssignmentType((String)r[1], (String)r[2], (String)r[3])
		);
	}

	public List<ReviewerDTO> getReviewerDTOs() {
		List<Personnel> personnelList;
		List<String> personnelStatusCodes = ImmutableList.of(PersonnelStatusCodes.ACTIVE, PersonnelStatusCodes.INACTIVE);
		if (securityService.hasRole(Roles.ROLE_HQ_ADMIN)) {
			personnelList = entityManager
				.createQuery(personnelJpql, Personnel.class)
				.setParameter("personnelStatusCodes", personnelStatusCodes)
				.getResultList();

		} else {
			String reviewLocationId = securityService.getReviewLocationId();

			personnelList = entityManager
				.createQuery(
					personnelJpql + " and (p.reviewLocation.reviewLocationId = :reviewLocationId) ",
					Personnel.class
				)
				.setParameter("personnelStatusCodes", personnelStatusCodes)
				.setParameter("reviewLocationId", reviewLocationId)
				.getResultList();

		}

		return convertPersonnelListToReviewerDTOs(personnelList);
	}

	public List<ReviewerDTO> convertPersonnelListToReviewerDTOs(Iterable<Personnel> personnelIter) {
		List<String> personnelIds = Util.map(personnelIter, p -> p.getPersonnelId());
		Multimap<String, AssignmentType> personnelIdToAssignmentTypes = getPersonnelIdToAssignmentTypes(personnelIds);
		List<PersonnelIdReviewCount> personnelIdReviewCounts = reviewRepository.findPersonnelIdReviewCountsByPersonnelIdIn(personnelIds);
		Map<String, Integer> personnelIdToReviewCount = Util.index(
			personnelIdReviewCounts,
			r -> r.personnelId,
			r -> r.reviewCount
		);

		List<ReviewerDTO> reviewerDTOs = new ArrayList<ReviewerDTO>();
		for (Personnel personnel : personnelIter) {
			ReviewerDTO reviewerDTO = new ReviewerDTO();
			reviewerDTO.setTotalCapacity(personnel.getReviewerCapacity());
			Integer reviewCount = personnelIdToReviewCount.get(personnel.getPersonnelId());
			if (reviewCount == null) {
				reviewCount = 0;
			}
			int remainingCapacity = personnel.getReviewerCapacity() - reviewCount;
			reviewerDTO.setRemainingCapacity(remainingCapacity);
			reviewerDTO.setHudId(personnel.getLoginCredential());
			reviewerDTO.setLocationId(personnel.getReviewLocation().getReviewLocationId());
			reviewerDTO.setNameFirst(personnel.getFirstName() +
				(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() : ""));
			reviewerDTO.setNameLast(personnel.getLastName());
			if (personnel.getReportsToPersonnel() != null) {
				reviewerDTO.setReportsTo(personnel.getReportsToPersonnel().getPersonnelId());
			}
			reviewerDTO.setReviewerId(personnel.getPersonnelId());
			reviewerDTO.setStatusCode(personnel.getPersonnelStatusRef().getCode());
			if (personnel.getVettingPersonnel() != null) {
				reviewerDTO.setVettedBy(personnel.getVettingPersonnel().getPersonnelId());
			}

			if (personnel.getPersonnelUnavailabilities() != null) {
				List<ReviewerUnavailabilityDTO> reviewerUnavailabilityDTOs = personnel
					.getPersonnelUnavailabilities()
					.stream()
					.map(x -> convertPersonnelUnavailabilityToReviewerUnavailabilityDTO(x))
					.collect(Collectors.<ReviewerUnavailabilityDTO> toList())
				;
				reviewerDTO.setUnavailabilities(reviewerUnavailabilityDTOs);
			}

			List<ReviewerAssignmentTypeDTO> selectionReasons = new ArrayList<ReviewerAssignmentTypeDTO>();
			List<ReviewerAssignmentTypeDTO> reviewTypes = new ArrayList<ReviewerAssignmentTypeDTO>();
			List<ReviewerAssignmentTypeDTO> productTypes = new ArrayList<ReviewerAssignmentTypeDTO>();
			List<ReviewerAssignmentTypeDTO> reviewLevels = new ArrayList<ReviewerAssignmentTypeDTO>();
			for (AssignmentType assignmentType : personnelIdToAssignmentTypes.get(personnel.getPersonnelId())) {
				ReviewerAssignmentTypeDTO reviewerAssignmentTypeDTO = new ReviewerAssignmentTypeDTO();
				reviewerAssignmentTypeDTO.setAssignmentTypeId(assignmentType.refId);
				reviewerAssignmentTypeDTO.setAssignmentTypeCode(assignmentType.refCode);
				if (assignmentType.category.equals("Staff Management Selection Reason")) {
					selectionReasons.add(reviewerAssignmentTypeDTO);
				} else if (assignmentType.category.equals("Review Type")) {
					reviewTypes.add(reviewerAssignmentTypeDTO);
				} else if (assignmentType.category.equals("Product Type")) {
					productTypes.add(reviewerAssignmentTypeDTO);
				} else if (assignmentType.category.equals("Review Level")) {
					reviewLevels.add(reviewerAssignmentTypeDTO);
				}
			}
			reviewerDTO.setSelectionReasons(selectionReasons);
			reviewerDTO.setReviewTypes(reviewTypes);
			reviewerDTO.setProductTypes(productTypes);
			reviewerDTO.setReviewLevels(reviewLevels);

			reviewerDTOs.add(reviewerDTO);
		}

		return reviewerDTOs;
	}

	public List<ReviewerLiteDTO> getActiveReviewerLiteDTOs() {
		List<Personnel> personnelList;
		List<String> personnelStatusCodes = ImmutableList.of(PersonnelStatusCodes.ACTIVE);

		if (securityService.hasRole(Roles.ROLE_HQ_ADMIN)) {
			personnelList = entityManager
				.createQuery(personnelJpql, Personnel.class)
				.setParameter("personnelStatusCodes", personnelStatusCodes)
				.getResultList();

		} else {
			String reviewLocationId = securityService.getReviewLocationId();

			personnelList = entityManager
				.createQuery(
					personnelJpql + " and (p.reviewLocation.reviewLocationId = :reviewLocationId) ",
					Personnel.class
				)
				.setParameter("personnelStatusCodes", personnelStatusCodes)
				.setParameter("reviewLocationId", reviewLocationId)
				.getResultList();


		}

		return convertPersonnelToReviewerLiteDTOs(personnelList);
	}


	public List<ReviewerLiteDTO> getHqReviewerLiteDTOs() {
		List<Personnel> personnelList = entityManager
			.createQuery(
				personnelJpql + " and (p.reviewLocation.reviewLocationId = :reviewLocationId) ",
				Personnel.class
			)
			.setParameter("personnelStatusCodes", ImmutableList.of(PersonnelStatusCodes.ACTIVE))
			.setParameter("reviewLocationId", reviewLocationRepository.findByLocationName("HQ").getReviewLocationId())
			.getResultList();

		return convertPersonnelToReviewerLiteDTOs(personnelList);
	}

	private List<ReviewerLiteDTO> convertPersonnelToReviewerLiteDTOs(Iterable<Personnel> personnelIter) {
		List<String> personnelIds = Util.map(personnelIter, p -> p.getPersonnelId());
		List<PersonnelIdReviewCount> personnelIdReviewCounts = reviewRepository.findPersonnelIdReviewCountsByPersonnelIdIn(personnelIds);
		Map<String, Integer> personnelIdToReviewCount = Util.index(
			personnelIdReviewCounts,
			r -> r.personnelId,
			r -> r.reviewCount
		);

		List<ReviewerLiteDTO> reviewerLiteDTOs = new ArrayList<ReviewerLiteDTO>();
		for (Personnel personnel : personnelIter) {
			ReviewerLiteDTO reviewerLiteDTO = new ReviewerLiteDTO();

			reviewerLiteDTO.setHudId(personnel.getLoginCredential());
			Integer reviewCount = personnelIdToReviewCount.get(personnel.getPersonnelId());
			if (reviewCount == null) {
				reviewCount = 0;
			}
			int remainingCapacity = personnel.getReviewerCapacity() - reviewCount;
			reviewerLiteDTO.setRemainingCapacity(remainingCapacity);
			reviewerLiteDTO.setLocationId(personnel.getReviewLocation().getReviewLocationId());
			if (personnel.getReportsToPersonnel() != null) {
				reviewerLiteDTO.setReportsTo(personnel.getReportsToPersonnel().getPersonnelId());
			}
			reviewerLiteDTO.setNameFirst(personnel.getFirstName() +
				(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() : ""));
			reviewerLiteDTO.setNameLast(personnel.getLastName());
			reviewerLiteDTO.setReviewerId(personnel.getPersonnelId());
			reviewerLiteDTO.setStatusCode(personnel.getPersonnelStatusRef().getCode());

			reviewerLiteDTOs.add(reviewerLiteDTO);
		}

		return reviewerLiteDTOs;
	}

	@Transactional
	public Personnel createPersonnel(ReviewerCreationDTO reviewerCreationDTO) {
		// Cannot add current active user
		Personnel personnel = personnelRepository.findByLoginCredentialAndPersonnelStatusRefCode(reviewerCreationDTO.getHudId(), PersonnelStatusCodes.ACTIVE);
		if (personnel != null) {
			throw new ConflictException(ImmutableList.of(reviewerCreationDTO.getNameFirst() + " " + reviewerCreationDTO.getNameLast() + " with HUD ID " + reviewerCreationDTO.getHudId() + " has already been added"));
		}

		boolean newPerson = true;
		personnel = personnelRepository.findByLoginCredential(reviewerCreationDTO.getHudId());
		if (personnel != null) {
			// The personnel has been inacitvated or terminated before
			newPerson = false;
		} else {
			personnel = new Personnel();
		}

		personnel.setLoginCredential(reviewerCreationDTO.getHudId());
		personnel.setFirstName(reviewerCreationDTO.getNameFirst());
		personnel.setLastName(reviewerCreationDTO.getNameLast());
		personnel.setAvailabilityInd('Y');
		personnel.setReviewerCapacity(0);
		personnel.setVettingPersonnel(null);

		ReviewLocation reviewLocation = reviewLocationRepository.findOne(reviewerCreationDTO.getLocationId());
		if (reviewLocation == null) {
			throw new BadRequestException("No ReviewLocation for reviewLocationId " + reviewerCreationDTO.getLocationId());
		}
		personnel.setReviewLocation(reviewLocation);

		if (reviewerCreationDTO.getReportsTo() != null && !reviewerCreationDTO.getReportsTo().isEmpty()) {
			Personnel reportsToPersonnel = personnelRepository.findOne(reviewerCreationDTO.getReportsTo());
			if (reportsToPersonnel == null) {
				throw new BadRequestException("No reports-to Personnel for prsnnlId " + reviewerCreationDTO.getReportsTo());
			}
			personnel.setReportsToPersonnel(reportsToPersonnel);
		}
		else {
			personnel.setReportsToPersonnel(null);
		}

		PersonnelStatusRef active = personnelStatusRefRepository.findByCode(PersonnelStatusCodes.ACTIVE);
		if (active == null) {
			throw new RuntimeException("No PersonnelStatusRef for code " + PersonnelStatusCodes.ACTIVE);
		}
		personnel.setPersonnelStatusRef(active);

		String username = securityService.getUserId();
		Date now = new Date();
		if (newPerson) {
			personnel.setCreatedBy(username);
			personnel.setCreatedTs(now);
		}

		personnel.setUpdatedBy(username);
		personnel.setUpdatedTs(now);
		personnelRepository.save(personnel);

		if (!newPerson) {
			// Clean up skills and unavailabilities
			personnelAssignmentTypeRepository.deleteByPersonnel(personnel);
			personnelUnavailabilityRepository.deleteByPersonnel(personnel);
		}

		return personnel;
	}

	@Transactional
	public Personnel updatePersonnel(Personnel personnel, ReviewerDTO reviewerDTO) {
		Date now = new Date();
		String username = securityService.getUserId();

		personnel.setLoginCredential(reviewerDTO.getHudId());
		personnel.setReviewerCapacity(reviewerDTO.getTotalCapacity());

		ReviewLocation reviewLocation = reviewLocationRepository.findOne(reviewerDTO.getLocationId());
		if (reviewLocation == null) {
			throw new BadRequestException("No ReviewLocation for reviewLocationId " + reviewerDTO.getLocationId());
		}
		personnel.setReviewLocation(reviewLocation);

		if (reviewerDTO.getReportsTo() != null && !reviewerDTO.getReportsTo().equals("")) {
			Personnel reportsToPersonnel = personnelRepository.findOne(reviewerDTO.getReportsTo());
			if (reportsToPersonnel == null) {
				throw new RuntimeException("No reports-to Personnel for prsnnlId " + reviewerDTO.getReportsTo());
			}
			personnel.setReportsToPersonnel(reportsToPersonnel);
		} else {
			personnel.setReportsToPersonnel(null);
		}

		if (reviewerDTO.getVettedBy() != null && !reviewerDTO.getVettedBy().equals("")) {
			Personnel vettedByPersonnel = personnelRepository.findOne(reviewerDTO.getVettedBy());
			if (vettedByPersonnel == null) {
				throw new RuntimeException("No reports-to Personnel for prsnnlId " + reviewerDTO.getVettedBy());
			}
			personnel.setVettingPersonnel(vettedByPersonnel);
		} else {
			personnel.setVettingPersonnel(null);
		}

		PersonnelStatusRef personnelStatusRef = personnelStatusRefRepository.findByCode(reviewerDTO.getStatusCode());
		if (personnelStatusRef == null) {
			throw new RuntimeException("No PersonnelStatusRef for code " + reviewerDTO.getStatusCode());
		}

		if (reviewerDTO.getStatusCode() != null && reviewerDTO.getStatusCode().equals(PersonnelStatusCodes.INACTIVE)) {
			List<ReviewLevel> activeReviewLevels = reviewLevelRepository.findActiveByPersonnelId(personnel.getPersonnelId());
			if (activeReviewLevels.size() > 0) {
				throw new ConflictException("Attempting to de-activate, but Reviewer has active reviews.");
			}

			int reportingPersonnelCount = personnelRepository.findReportingPersonnelCount(personnel.getPersonnelId());
			if (reportingPersonnelCount > 0) {
				throw new ConflictException("Attempting to de-activate, but Reviewer is a supervisor or vetter of an active personnel.");
			}
		}

		if (!securityService.hasRole(Roles.ROLE_HQ_ADMIN) && isPerformingIndemSkillChange(personnel.getPersonnelId(), reviewerDTO)) {
			throw new ForbiddenException("Indemnification/Forced Indemnification skill can only be set by an HQ Admin.");
		}

		personnel.setPersonnelStatusRef(personnelStatusRef);

		personnel.setPersonnelUnavailabilities(new HashSet<PersonnelUnavailability>());
		List<String> personnelUnavailabilityIds = new ArrayList<String>();
		if (reviewerDTO.getUnavailabilities() != null) {
			for (ReviewerUnavailabilityDTO reviewerUnavailabilityDTO : reviewerDTO.getUnavailabilities()) {
				PersonnelUnavailability personnelUnavailability;
				if (reviewerUnavailabilityDTO.getUnavailabilityId() != null) {
					// TODO: this is a query per loop iteration; should change to one query for all outside the loop
					personnelUnavailability = personnelUnavailabilityRepository.findOne(reviewerUnavailabilityDTO.getUnavailabilityId());
					if (personnelUnavailability == null) {
						throw new BadRequestException("Non PersonnelUnavailability for personnelUnavailabilityId " + reviewerUnavailabilityDTO.getUnavailabilityId());
					}
				} else {
					personnelUnavailability = new PersonnelUnavailability();
					personnelUnavailability.setCreatedBy(username);
					personnelUnavailability.setCreatedTs(now);
				}

				personnelUnavailability.setPersonnel(personnel);
				personnelUnavailability.setUnavailStartTs(reviewerUnavailabilityDTO.getFrom());
				personnelUnavailability.setUnavailEndTs(reviewerUnavailabilityDTO.getTo());
				personnelUnavailability.setUpdatedBy(username);
				personnelUnavailability.setUpdatedTs(now);
				personnelUnavailability = personnelUnavailabilityRepository.save(personnelUnavailability);

				personnel.getPersonnelUnavailabilities().add(personnelUnavailability);
				personnelUnavailabilityIds.add(personnelUnavailability.getPersonnelUnavailabilityId());
			}
		}

		// delete not in does not remove last unavailability record (not in on empty list)
		// generate uuid and add to empty list so that we can delete the remaining availability record
		if (personnelUnavailabilityIds.size() == 0) {
			personnelUnavailabilityIds.add(UUID.randomUUID().toString().toUpperCase());
		}
		personnelUnavailabilityRepository.deleteByPersonnelUnavailabilityIdNotInAndPersonnelIn(personnelUnavailabilityIds, personnel);

		List<String> consolidatedSelectionReasonCodes = new ArrayList<String>();
		if (reviewerDTO.getSelectionReasons() != null) {
			consolidatedSelectionReasonCodes.addAll(reviewerDTO.getSelectionReasons().stream().map(x -> x.getAssignmentTypeCode()).collect(Collectors.toList()));
			//this is to avoid the "IN ()" empty list SQL error
			if (consolidatedSelectionReasonCodes.size() == 0) {
				consolidatedSelectionReasonCodes.add("");
			}
		}
		List<String> reviewTypeCodes = new ArrayList<String>();
		if (reviewerDTO.getReviewTypes() != null) {
			reviewTypeCodes.addAll(reviewerDTO.getReviewTypes().stream().map(x -> x.getAssignmentTypeCode()).collect(Collectors.toList()));
			//this is to avoid the "IN ()" empty list SQL error
			if (reviewTypeCodes.size() == 0) {
				reviewTypeCodes.add("");
			}
		}
		List<String> reviewLevelTypeCodes = new ArrayList<String>();
		if (reviewerDTO.getReviewLevels() != null) {
			reviewLevelTypeCodes.addAll(reviewerDTO.getReviewLevels().stream().map(x -> x.getAssignmentTypeCode()).collect(Collectors.toList()));
			//this is to avoid the "IN ()" empty list SQL error
			if (reviewLevelTypeCodes.size() == 0) {
				reviewLevelTypeCodes.add("");
			}
		}
		List<String> productTypeCodes = new ArrayList<String>();
		if (reviewerDTO.getProductTypes() != null) {
			productTypeCodes.addAll(reviewerDTO.getProductTypes().stream().map(x -> x.getAssignmentTypeCode()).collect(Collectors.toList()));
			//this is to avoid the "IN ()" empty list SQL error
			if (productTypeCodes.size() == 0) {
				productTypeCodes.add("");
			}
		}

		// note that we don't check for invalid incoming IDs or codes here
		// we could but it would be more work
		Query query = entityManager.createNativeQuery(
			"INSERT INTO PERSONNEL_ASSIGNMENT_TYPE( " +
				"PERSONNEL_ASSIGNMENT_TYPE_ID, " +
				"ASSIGNMENT_TYPE_CD, " +
				"PERSONNEL_ID, " +
				"ACTIVE_IND, " +
				"CREATED_BY, " +
				"CREATED_TS, " +
				"UPDATED_BY, " +
				"UPDATED_TS " +
			") " +
			"SELECT " +
				"NEWID(), " +
				"AT.ASSIGNMENT_TYPE_CD, " +
				":personnelId, " +
				"'Y', " +
				":userId, " +
				"CURRENT_TIMESTAMP, " +
				":userId, " +
				"CURRENT_TIMESTAMP " +
			"FROM ASSIGNMENT_TYPE_ADMIN AT " +
			"LEFT JOIN CONSOLIDATED_SELECTION_REASON CSR ON (CSR.CONSOLIDATED_SELECTION_REASON_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"LEFT JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"LEFT JOIN REVIEW_LEVEL_TYPE_REF RLT ON (RLT.REVIEW_LEVEL_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"LEFT JOIN PRODUCT_TYPE_REF PT ON (PT.PRODUCT_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"WHERE " +
				"( " +
					"(CSR.CODE IN (:consolidatedSelectionReasonCodes)) OR " +
					"(RT.REVIEW_TYPE_CD IN (:reviewTypeCodes)) OR " +
					"(RLT.REVIEW_LEVEL_CD IN (:reviewLevelTypeCodes)) OR " +
					"(PT.PRODUCT_TYPE_CD IN (:productTypeCodes)) " +
				") AND " +
				"NOT EXISTS ( " +
					"SELECT * FROM PERSONNEL_ASSIGNMENT_TYPE PAT WHERE " +
						"(PAT.ASSIGNMENT_TYPE_CD = AT.ASSIGNMENT_TYPE_CD) AND " +
						"(PAT.PERSONNEL_ID = :personnelId) " +
				") "
		);
		query.setParameter("personnelId", personnel.getPersonnelId());
		query.setParameter("userId", securityService.getUserId());
		query.setParameter("consolidatedSelectionReasonCodes", consolidatedSelectionReasonCodes);
		query.setParameter("reviewTypeCodes", reviewTypeCodes);
		query.setParameter("reviewLevelTypeCodes", reviewLevelTypeCodes);
		query.setParameter("productTypeCodes", productTypeCodes);
		query.executeUpdate();

		query = entityManager.createNativeQuery(
			"DELETE PAT FROM PERSONNEL_ASSIGNMENT_TYPE PAT "+
			"INNER JOIN ASSIGNMENT_TYPE_ADMIN AT ON (AT.ASSIGNMENT_TYPE_CD = PAT.ASSIGNMENT_TYPE_CD) " +
			"LEFT JOIN CONSOLIDATED_SELECTION_REASON CSR ON (CSR.CONSOLIDATED_SELECTION_REASON_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"LEFT JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"LEFT JOIN REVIEW_LEVEL_TYPE_REF RLT ON (RLT.REVIEW_LEVEL_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"LEFT JOIN PRODUCT_TYPE_REF PT ON (PT.PRODUCT_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"WHERE " +
				"(PAT.PERSONNEL_ID = :personnelId) AND " +
				"((CSR.CODE IS NULL) OR (CSR.CODE NOT IN (:consolidatedSelectionReasonCodes))) AND" +
				"((RT.REVIEW_TYPE_CD IS NULL) OR (RT.REVIEW_TYPE_CD NOT IN (:reviewTypeCodes))) AND " +
				"((RLT.REVIEW_LEVEL_CD IS NULL) OR (RLT.REVIEW_LEVEL_CD NOT IN (:reviewLevelTypeCodes))) AND " +
				"((PT.PRODUCT_TYPE_CD IS NULL) OR (PT.PRODUCT_TYPE_CD NOT IN (:productTypeCodes))) "
		);
		query.setParameter("personnelId", personnel.getPersonnelId());
		query.setParameter("consolidatedSelectionReasonCodes", consolidatedSelectionReasonCodes);
		query.setParameter("reviewTypeCodes", reviewTypeCodes);
		query.setParameter("reviewLevelTypeCodes", reviewLevelTypeCodes);
		query.setParameter("productTypeCodes", productTypeCodes);
		query.executeUpdate();

		return personnel;
	}

	@Transactional
	public void terminatePersonnel(String personnelId) {
		Personnel personnel = personnelRepository.findOne(personnelId);
		if (personnel == null) {
			throw new NotFoundException("No Personnel for personnelId " + personnelId);
		}

		List<String> errors = new ArrayList<String>();
		List<Personnel> vetees = personnelRepository.findByVettingPersonnel(personnel);
		if (vetees != null && vetees.size() > 0) {
			errors.add(messageService.getMessage("error.staffmanagement.assignedvetter"));
		}
		List<Personnel> subordinates = personnelRepository.findByReportsToPersonnel(personnel);
		if (subordinates != null && subordinates.size() > 0) {
			errors.add(messageService.getMessage("error.staffmanagement.assignedsupervisor"));
		}
		List<ReviewLevel> activeReviewLevels = reviewLevelRepository.findActiveByPersonnelId(personnel.getPersonnelId());
		if (activeReviewLevels != null && activeReviewLevels.size() > 0) {
			errors.add(messageService.getMessage("error.staffmanagement.assignedreviewer"));
		}
		if (!errors.isEmpty()) {
			throw new ConflictException(errors);
		}

		PersonnelStatusRef terminated = personnelStatusRefRepository.findByCode(PersonnelStatusCodes.TERMINATED);
		personnel.setPersonnelStatusRef(terminated);
		personnelRepository.save(personnel);
		personnelAssignmentTypeRepository.deleteByPersonnel(personnel);
		personnelUnavailabilityRepository.deleteByPersonnel(personnel);
	}

	private boolean isPerformingIndemSkillChange(String personnelId, ReviewerDTO reviewerDTO) {
		int existingIndemCount = personnelAssignmentTypeRepository.countByPersonnelIdAndReviewLevelType(personnelId, ReviewLevelTypeCodes.INDEMNIFICATION);
		int existingForcedIndemCount = personnelAssignmentTypeRepository.countByPersonnelIdAndReviewLevelType(personnelId, ReviewLevelTypeCodes.FORCE_INDEMNIFICATION);

		List<ReviewerAssignmentTypeDTO> reviewLevelAssignmentTypes = reviewerDTO.getReviewLevels();
		int newIndemCount = reviewLevelAssignmentTypes.stream().filter(ReviewerAssignmentTypeDTO ->
			ReviewerAssignmentTypeDTO.getAssignmentTypeCode().equals(ReviewLevelTypeCodes.INDEMNIFICATION)).findFirst().isPresent() ? 1 : 0;
		int newForcedIndemCount = reviewLevelAssignmentTypes.stream().filter(ReviewerAssignmentTypeDTO ->
			ReviewerAssignmentTypeDTO.getAssignmentTypeCode().equals(ReviewLevelTypeCodes.FORCE_INDEMNIFICATION)).findFirst().isPresent() ? 1 : 0;

		if ((existingIndemCount != newIndemCount) || (existingForcedIndemCount != newForcedIndemCount)) {
			return true;
		} else {
			return false;
		}
	}

	public ReviewerDTO convertPersonnelToReviewerDTO(Personnel personnel) {
		List<String> personnelIds = new ArrayList<String>();
		personnelIds.add(personnel.getPersonnelId());
		List<PersonnelIdReviewCount> personnelIdReviewCounts = reviewRepository.findPersonnelIdReviewCountsByPersonnelIdIn(personnelIds);
		int remainingCapacity = 0;
		if (personnelIdReviewCounts != null) {
			PersonnelIdReviewCount personnelIdReviewCount = personnelIdReviewCounts.get(0);
			remainingCapacity = personnel.getReviewerCapacity() - personnelIdReviewCount.reviewCount;
		}
		ReviewerDTO reviewerDTO = new ReviewerDTO();
		reviewerDTO.setTotalCapacity(personnel.getReviewerCapacity());
		reviewerDTO.setRemainingCapacity(remainingCapacity);
		reviewerDTO.setHudId(personnel.getLoginCredential());
		reviewerDTO.setLocationId(personnel.getReviewLocation().getReviewLocationId());
		reviewerDTO.setNameFirst(personnel.getFirstName() +
			(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() : ""));
		reviewerDTO.setNameLast(personnel.getLastName());
		if (personnel.getReportsToPersonnel() != null) {
			reviewerDTO.setReportsTo(personnel.getReportsToPersonnel().getPersonnelId());
		}
		reviewerDTO.setReviewerId(personnel.getPersonnelId());
		reviewerDTO.setStatusCode(personnel.getPersonnelStatusRef().getCode());
		if (personnel.getVettingPersonnel() != null) {
			reviewerDTO.setVettedBy(personnel.getVettingPersonnel().getPersonnelId());
		}

		// reviewerDTO.setUnavailabilities(new
		// ArrayList<ReviewerUnavailabilityDTO>());
		if (personnel.getPersonnelUnavailabilities() != null) {
			List<ReviewerUnavailabilityDTO> reviewerUnavailabilityDTOs = personnel
				.getPersonnelUnavailabilities()
				.stream()
				.map(x -> convertPersonnelUnavailabilityToReviewerUnavailabilityDTO(x))
				.collect(Collectors.<ReviewerUnavailabilityDTO> toList())
			;
			reviewerDTO.setUnavailabilities(reviewerUnavailabilityDTOs);
		}

		@SuppressWarnings("unchecked")
		List<Object[]> assignmentTypeCategoryRefIdCodes = entityManager.createNativeQuery(
			"SELECT AT.ASSIGNMENT_TYPE_CATEGORY, AT.ASSIGNMENT_TYPE_REF_ID, COALESCE(SR.CODE, RT.REVIEW_TYPE_CD, PT.PRODUCT_TYPE_CD, RLT.REVIEW_LEVEL_CD) " +
			"FROM PERSONNEL_ASSIGNMENT_TYPE PAT " +
			"INNER JOIN ASSIGNMENT_TYPE_ADMIN AT ON (AT.ASSIGNMENT_TYPE_CD = PAT.ASSIGNMENT_TYPE_CD) " +
			"LEFT JOIN CONSOLIDATED_SELECTION_REASON SR ON (SR.CONSOLIDATED_SELECTION_REASON_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"LEFT JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"LEFT JOIN PRODUCT_TYPE_REF PT ON (PT.PRODUCT_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"LEFT JOIN REVIEW_LEVEL_TYPE_REF RLT ON (RLT.REVIEW_LEVEL_TYPE_ID = AT.ASSIGNMENT_TYPE_REF_ID) " +
			"WHERE " +
				"(PAT.PERSONNEL_ID = :personnelId) AND " +
				"(PAT.ACTIVE_IND = 'Y') "
		)
		.setParameter("personnelId", personnel.getPersonnelId())
		.getResultList();
		List<ReviewerAssignmentTypeDTO> selectionReasonReviewAssignmentTypeDTOs = new ArrayList<ReviewerAssignmentTypeDTO>();
		List<ReviewerAssignmentTypeDTO> reviewTypeReviewAssignmentTypeDTOs = new ArrayList<ReviewerAssignmentTypeDTO>();
		List<ReviewerAssignmentTypeDTO> productTypeReviewAssignmentTypeDTOs = new ArrayList<ReviewerAssignmentTypeDTO>();
		List<ReviewerAssignmentTypeDTO> reviewLevelTypeReviewAssignmentTypeDTOs = new ArrayList<ReviewerAssignmentTypeDTO>();
		for (Object[] assignmentTypeCategoryRefIdCode : assignmentTypeCategoryRefIdCodes) {
			ReviewerAssignmentTypeDTO reviewerAssignmentTypeDTO = new ReviewerAssignmentTypeDTO();
			reviewerAssignmentTypeDTO.setAssignmentTypeId((String)assignmentTypeCategoryRefIdCode[1]);
			reviewerAssignmentTypeDTO.setAssignmentTypeCode((String)assignmentTypeCategoryRefIdCode[2]);

			String assignmentTypeCategory = (String)assignmentTypeCategoryRefIdCode[0];
			if ("Staff Management Selection Reason".equals(assignmentTypeCategory)) {
				selectionReasonReviewAssignmentTypeDTOs.add(reviewerAssignmentTypeDTO);
			} else if ("Review Type".equals(assignmentTypeCategory)) {
				reviewTypeReviewAssignmentTypeDTOs.add(reviewerAssignmentTypeDTO);
			} else if ("Product Type".equals(assignmentTypeCategory)) {
				productTypeReviewAssignmentTypeDTOs.add(reviewerAssignmentTypeDTO);
			} else if ("Review Level".equals(assignmentTypeCategory)) {
				reviewLevelTypeReviewAssignmentTypeDTOs.add(reviewerAssignmentTypeDTO);
			}
		}
		reviewerDTO.setSelectionReasons(selectionReasonReviewAssignmentTypeDTOs);
		reviewerDTO.setReviewTypes(reviewTypeReviewAssignmentTypeDTOs);
		reviewerDTO.setProductTypes(productTypeReviewAssignmentTypeDTOs);
		reviewerDTO.setReviewLevels(reviewLevelTypeReviewAssignmentTypeDTOs);

		return reviewerDTO;
	}

	public ReviewerLiteDTO convertPersonnelToReviewerLiteDTO(Personnel personnel) {
		List<String> personnelIds = new ArrayList<String>();
		personnelIds.add(personnel.getPersonnelId());
		List<PersonnelIdReviewCount> personnelIdReviewCounts = reviewRepository.findPersonnelIdReviewCountsByPersonnelIdIn(personnelIds);
		int remainingCapacity = 0;
		if (personnelIdReviewCounts != null) {
			PersonnelIdReviewCount personnelIdReviewCount = personnelIdReviewCounts.get(0);
			remainingCapacity = personnel.getReviewerCapacity() - personnelIdReviewCount.reviewCount;
		}
		ReviewerLiteDTO reviewerLiteDTO = new ReviewerLiteDTO();
		reviewerLiteDTO.setHudId(personnel.getLoginCredential());
		reviewerLiteDTO.setRemainingCapacity(remainingCapacity);
		reviewerLiteDTO.setLocationId(personnel.getReviewLocation().getReviewLocationId());
		if (personnel.getReportsToPersonnel() != null) {
			reviewerLiteDTO.setReportsTo(personnel.getReportsToPersonnel().getPersonnelId());
		}
		reviewerLiteDTO.setNameFirst(personnel.getFirstName() +
			(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() : ""));
		reviewerLiteDTO.setNameLast(personnel.getLastName());
		reviewerLiteDTO.setReviewerId(personnel.getPersonnelId());
		reviewerLiteDTO.setStatusCode(personnel.getPersonnelStatusRef().getCode());

		return reviewerLiteDTO;
	}

	public ReviewerUnavailabilityDTO convertPersonnelUnavailabilityToReviewerUnavailabilityDTO(PersonnelUnavailability personnelUnavailability) {
		ReviewerUnavailabilityDTO reviewerUnavailabilityDTO = new ReviewerUnavailabilityDTO();
		reviewerUnavailabilityDTO.setUnavailabilityId(personnelUnavailability.getPersonnelUnavailabilityId());
		reviewerUnavailabilityDTO.setFrom(DateUtils.convertDateToNoonUtcDate(personnelUnavailability.getUnavailStartTs()));
		reviewerUnavailabilityDTO.setTo(DateUtils.convertDateToNoonUtcDate(personnelUnavailability.getUnavailEndTs()));
		return reviewerUnavailabilityDTO;
	}

}
