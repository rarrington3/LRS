package gov.hud.lrs.services.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.FhacUserDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.CurrentUserDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LiteLenderDTO;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.FhacUserService;
import gov.hud.lrs.common.service.LenderService;

@Controller
public class CurrentUserControllerImpl {

	@Autowired private FhacUserService fhacUserService;
	@Autowired private LenderService lenderService;
	@Autowired private SecurityService securityService;

	@PersistenceContext EntityManager entityManager;

	public ResponseEntity<CurrentUserDTO> getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
		CurrentUserDTO currentUserDTO = new CurrentUserDTO();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<String> authorities = new ArrayList<String>();
		auth.getAuthorities().forEach(authority -> {
			authorities.add(authority.getAuthority());
		});
		currentUserDTO.setAuthorities(authorities);

		FhacUserDTO fhacUserDTO = null;
		fhacUserDTO = fhacUserService.getFhacUser(auth.getName());
		currentUserDTO.setHudId(fhacUserDTO.getUserId());
		String lenderId = fhacUserDTO.getLenderId();

		// If external user
		if (fhacUserService.isLender(fhacUserDTO)) {
			Lender lender = lenderService.getLender(lenderId);
			if(lender == null) {
				throw new RuntimeException("Error looking up lenderId: " + lenderId);
			}
			LiteLenderDTO liteLenderDTO = new LiteLenderDTO();
			liteLenderDTO.setLenderId(lender.getLenderId());
			liteLenderDTO.setName(lender.getName());
			currentUserDTO.setLender(liteLenderDTO);
		} else {
			// Internal user, get personnel ID
			Personnel personnel = securityService.getPersonnel();
			currentUserDTO.setFirstName(personnel.getFirstName() +
				(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() : ""));
			currentUserDTO.setLastName(personnel.getLastName());
			currentUserDTO.setPersonnelId(personnel.getPersonnelId());
			currentUserDTO.setLocationId(personnel.getReviewLocation().getReviewLocationId());

			@SuppressWarnings("unchecked")
			List<Object[]> assignmentTypeCategoryCodes = entityManager.createNativeQuery(
				"SELECT AT.ASSIGNMENT_TYPE_CATEGORY, COALESCE(SR.CODE, RT.REVIEW_TYPE_CD, PT.PRODUCT_TYPE_CD, RLT.REVIEW_LEVEL_CD) " +
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
			List<String> selectionReasons = new ArrayList<String>();
			List<String> reviewTypes = new ArrayList<String>();
			List<String> productTypes = new ArrayList<String>();
			List<String> reviewLevels = new ArrayList<String>();
			for (Object[] assignmentTypeCategoryCode : assignmentTypeCategoryCodes) {
				String assignmentTypeCategory = (String)assignmentTypeCategoryCode[0];
				String code = (String)assignmentTypeCategoryCode[1];
				if ("Staff Management Selection Reason".equals(assignmentTypeCategory)) {
					selectionReasons.add(code);
				} else if ("Review Type".equals(assignmentTypeCategory)) {
					reviewTypes.add(code);
				} else if ("Product Type".equals(assignmentTypeCategory)) {
					productTypes.add(code);
				} else if ("Review Level".equals(assignmentTypeCategory)) {
					reviewLevels.add(code);
				}
			}
			currentUserDTO.setSelectionReasonSkillCodes(selectionReasons);
			currentUserDTO.setReviewTypeSkillCodes(reviewTypes);
			currentUserDTO.setProductTypeSkillCodes(productTypes);
			currentUserDTO.setReviewLevelSkillCodes(reviewLevels);
		}

		return new ResponseEntity<CurrentUserDTO>(currentUserDTO, HttpStatus.OK);
	}
}
