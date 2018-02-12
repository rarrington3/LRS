package gov.hud.lrs.services.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.CapacityDTO;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.enumeration.Roles;
import gov.hud.lrs.common.repository.LoanSelectionRepository;
import gov.hud.lrs.common.repository.LoanSelectionRepositoryCustom.ReviewLocationIdLoanSelectionCount;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.ReviewRepositoryCustom.PersonnelIdReviewCount;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.util.DateUtils;
import gov.hud.lrs.services.security.PolicyImpl;

@Controller
public class CapacityControllerImpl {
	@Autowired private SecurityService securityService;
	@Autowired private LoanSelectionRepository loanSelectionRepository;
	@Autowired private ReviewLocationRepository reviewLocationRepository;
	@Autowired private PolicyImpl policyImpl;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private ReviewRepository reviewRepository;


	// [ [id, count], [id, count], ... ] -> { { id: count }, { id: count }, ... }
	private Map<String, Integer> reviewLocationIdLoanSelectionCountListToMap(List<ReviewLocationIdLoanSelectionCount> reviewLocationIdLoanSelectionCountList) {
		HashMap<String, Integer> idCountMap = new HashMap<String, Integer>();
		for (ReviewLocationIdLoanSelectionCount reviewLocationIdLoanSelectionCount : reviewLocationIdLoanSelectionCountList) {
			idCountMap.put(reviewLocationIdLoanSelectionCount.reviewLocationId, reviewLocationIdLoanSelectionCount.loanSelectionCount);
		}
		return idCountMap;
	}

	// [ [id, count], [id, count], ... ] -> { { id: count }, { id: count }, ... }
	private Map<String, Integer> personnelIdReviewCountListToMap(List<PersonnelIdReviewCount> personnelIdReviewCountList) {
		HashMap<String, Integer> idCountMap = new HashMap<String, Integer>();
		for (PersonnelIdReviewCount personnelIdReviewCount : personnelIdReviewCountList) {
			idCountMap.put(personnelIdReviewCount.personnelId, personnelIdReviewCount.reviewCount);
		}
		return idCountMap;
	}

	public ResponseEntity<List<CapacityDTO>> getCapacityLocations(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Integer> reviewLocationLoanSelectionCounts = new HashMap<String, Integer>();
		List<ReviewLocation> reviewLocations = new ArrayList<ReviewLocation>();

		if (securityService.hasRole(Roles.ROLE_REVIEW_LOCATION_ADMIN)) {
			// Review Location Admin can only see capacity from the same location.
			ReviewLocation reviewLocation = securityService.getPersonnel().getReviewLocation();

			reviewLocationLoanSelectionCounts.put(
				reviewLocation.getReviewLocationId(),
				loanSelectionRepository.findReviewLocationLoanSelectionCountsByReviewLocationId(reviewLocation.getReviewLocationId(), DateUtils.getCurrentMonth())
			);
			reviewLocations.add(reviewLocation);

		} else {
			reviewLocationLoanSelectionCounts = reviewLocationIdLoanSelectionCountListToMap(
				loanSelectionRepository.findReviewLocationLoanSelectionCounts(DateUtils.getCurrentMonth())
			);
			reviewLocations = reviewLocationRepository.findAll();
		}

		List<CapacityDTO> capacityDTOs = new ArrayList<CapacityDTO>();
		for (ReviewLocation reviewLocation : reviewLocations) {
			CapacityDTO capacityDTO = new CapacityDTO();
			capacityDTO.setId(reviewLocation.getReviewLocationId());
			if (reviewLocation.getLocationName().equalsIgnoreCase("HQ")) {
				capacityDTO.setDisplay(reviewLocation.getLocationName());
			} else {
				capacityDTO.setDisplay(reviewLocation.getMailingCity() + ", " + reviewLocation.getMailingState() + " (" + reviewLocation.getLocationName().split("-")[1] + ")");
			}
			capacityDTO.setTotalCapacity(BigDecimal.valueOf(reviewLocation.getCommittedMonthlyCapacity()));
			Integer usedCapacity = reviewLocationLoanSelectionCounts.get(reviewLocation.getReviewLocationId());
			if (usedCapacity == null) {
				usedCapacity = 0;
			}
			capacityDTO.setAvailableCapacity(BigDecimal.valueOf(reviewLocation.getCommittedMonthlyCapacity() - usedCapacity));
			capacityDTOs.add(capacityDTO);
		}
		return new ResponseEntity<List<CapacityDTO>>(capacityDTOs, HttpStatus.OK);
	}

	public ResponseEntity<List<CapacityDTO>> getCapacityLocationReviewers(String reviewLocationId, HttpServletRequest request, HttpServletResponse response) {
		// Review Location Admin can only see capacity from the same location.
		policyImpl.validateRequestGeography(reviewLocationId);

		Map<String, Integer> personnelLoanSelectionCounts = personnelIdReviewCountListToMap(
			reviewRepository.findPersonnelIdReviewCountsByReviewLocationId(reviewLocationId)
		);

		List<Personnel> reviewLocationPersonnel = personnelRepository.findActiveByReviewLocationId(reviewLocationId);

		List<CapacityDTO> capacityDTOs = new ArrayList<CapacityDTO>();
		for (Personnel personnel : reviewLocationPersonnel) {
			CapacityDTO capacityDTO = new CapacityDTO();
			capacityDTO.setId(personnel.getPersonnelId());
			capacityDTO.setDisplay(personnel.getFirstName() +
				(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() + " " : " ")  +
				personnel.getLastName());
			capacityDTO.setTotalCapacity(BigDecimal.valueOf(personnel.getReviewerCapacity()));
			Integer usedCapacity = personnelLoanSelectionCounts.get(personnel.getPersonnelId());
			if (usedCapacity == null) {
				usedCapacity = 0;
			}
			capacityDTO.setAvailableCapacity(BigDecimal.valueOf(personnel.getReviewerCapacity() - usedCapacity));
			capacityDTOs.add(capacityDTO);
		}
		return new ResponseEntity<List<CapacityDTO>>(capacityDTOs, HttpStatus.OK);
	}

	public ResponseEntity<List<CapacityDTO>> getCapacityReviewers(HttpServletRequest request, HttpServletResponse response) {
		// No location check. User is already restricted to see capacity from the same location.

		ReviewLocation reviewLocation = securityService.getReviewLocation();

		Map<String, Integer> personnelLoanSelectionCounts = personnelIdReviewCountListToMap(
			reviewRepository.findPersonnelIdReviewCountsByReviewLocationId(reviewLocation.getReviewLocationId())
		);

		List<Personnel> reviewLocationPersonnel = personnelRepository.findActiveByReviewLocationId(reviewLocation.getReviewLocationId());

		List<CapacityDTO> capacityDTOs = new ArrayList<CapacityDTO>();
		for (Personnel personnel : reviewLocationPersonnel) {
			CapacityDTO capacityDTO = new CapacityDTO();
			capacityDTO.setId(personnel.getPersonnelId());
			capacityDTO.setDisplay(personnel.getFirstName() +
				(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() + " " : " ") +
				personnel.getLastName());
			capacityDTO.setTotalCapacity(BigDecimal.valueOf(personnel.getReviewerCapacity()));
			Integer usedCapacity = personnelLoanSelectionCounts.get(personnel.getPersonnelId());
			if (usedCapacity == null) {
				usedCapacity = 0;
			}
			capacityDTO.setAvailableCapacity(BigDecimal.valueOf(personnel.getReviewerCapacity() - usedCapacity));
			capacityDTOs.add(capacityDTO);
		}
		return new ResponseEntity<List<CapacityDTO>>(capacityDTOs, HttpStatus.OK);
	}
}
