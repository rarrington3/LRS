package gov.hud.lrs.services.bizservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.BinderDTO;
import gov.hud.lrs.common.entity.BinderRequest;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.enumeration.BinderRequestSourceCodes;
import gov.hud.lrs.common.enumeration.BinderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.enumeration.Roles;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.BinderRequestRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.BinderRequestService;
import gov.hud.lrs.common.util.DateUtils;
import gov.hud.lrs.common.util.Util;

@Service
public class BinderService {

	@Autowired private BinderRequestRepository binderRequestRepository;
	@Autowired private SecurityService securityService;
	@Autowired private BinderRequestService binderRequestService;
	@Autowired private WorkflowClient workflowClient;

	@PersistenceContext EntityManager entityManager;

	public List<BinderDTO> getFhaBinderDTOs() {
		List<BinderRequest> binderRequests;
		if (securityService.hasRole(Roles.ROLE_REVIEW_LOCATION_ADMIN) || securityService.hasRole(Roles.ROLE_HQ_ADMIN)) {
			binderRequests = entityManager.createQuery(
				"select distinct br from BinderRequest br " +
				"join fetch br.loanSelection ls " +
				"join fetch ls.loanSelectionCaseSummary lscs " +
				"join fetch ls.lender l " +
				"join fetch br.binderRequestStatusRef brs " +
				"left join fetch ls.reviewProcessExceptions rpe " +
				"where ((brs.code = :requestedBinderRequestCode) " +
				"or (brs.code = :exceptionBinderRequestCode and rpe.resolvedInd = 'N') " +
				"or ((brs.code = :receivedBinderRequestCode) and ((ls.receivedDt >= :startDate) and (ls.receivedDt < :endDate))) " +
				"or ((brs.code = :cancelledBinderRequestCode) and ((ls.updatedTs >= :startDate) and (ls.updatedTs < :endDate))))",
				BinderRequest.class
			)
			.setParameter("requestedBinderRequestCode", BinderRequestStatusCodes.REQUESTED)
			.setParameter("exceptionBinderRequestCode", BinderRequestStatusCodes.EXCEPTION)
			.setParameter("receivedBinderRequestCode", BinderRequestStatusCodes.RECEIVED)
			.setParameter("cancelledBinderRequestCode", BinderRequestStatusCodes.CANCELLED)
			.setParameter("startDate", dateFromNow(-6))
			.setParameter("endDate", dateFromNow(1))
			.getResultList();
		} else {
			binderRequests = entityManager.createQuery(
				"select distinct br from BinderRequest br " +
				"join fetch br.loanSelection ls " +
				"join fetch ls.loanSelectionCaseSummary lscs " +
				"join fetch ls.lender l " +
				"join fetch br.binderRequestStatusRef brs " +
				"left join fetch ls.reviewProcessExceptions rpe " +
				"where (ls.reviewLocation = :reviewLocation) " +
				"and ((brs.code = :requestedBinderRequestCode) or (brs.code = :exceptionBinderRequestCode and rpe.resolvedInd = 'N') " +
				"or ((brs.code = :receivedBinderRequestCode) and ((ls.receivedDt >= :startDate) and (ls.receivedDt < :endDate))) " +
				"or ((brs.code = :cancelledBinderRequestCode) and ((ls.updatedTs >= :startDate) and (ls.updatedTs < :endDate))))",
				BinderRequest.class
			)
			.setParameter("reviewLocation", securityService.getReviewLocation())
			.setParameter("requestedBinderRequestCode", BinderRequestStatusCodes.REQUESTED)
			.setParameter("exceptionBinderRequestCode", BinderRequestStatusCodes.EXCEPTION)
			.setParameter("receivedBinderRequestCode", BinderRequestStatusCodes.RECEIVED)
			.setParameter("cancelledBinderRequestCode", BinderRequestStatusCodes.CANCELLED)
			.setParameter("startDate", dateFromNow(-6))
			.setParameter("endDate", dateFromNow(1))
			.getResultList();
		}

		return convertBinderRequestsToBinderDTOs(binderRequests, true);
	}

	public List<BinderDTO> getLenderBinderDTOs() {
		List<BinderRequest> binderRequests = entityManager.createQuery(
			"select distinct br from BinderRequest br " +
			"join fetch br.loanSelection ls " +
			"join fetch ls.loanSelectionCaseSummary lscs " +
			"join fetch ls.lender l " +
			"join fetch br.binderRequestStatusRef brs " +
			"join fetch br.binderRequestSourceRef brsr " +
			"left join fetch ls.reviewProcessExceptions rpe " +
			"where (l.lenderId = :lenderId) " +
			"and ((brsr.code in (:requestedBinderRequestSourceRefCode))) " +
			"and ((brs.code = :requestedBinderRequestCode) or (brs.code = :exceptionBinderRequestCode and rpe.resolvedInd = 'N') " +
			"or ((brs.code = :receivedBinderRequestCode) and ((ls.receivedDt >= :startDate) and (ls.receivedDt < :endDate))))",
			BinderRequest.class
		)
		.setParameter("lenderId", securityService.getFhacUser().getLenderId())
		.setParameter("requestedBinderRequestCode", BinderRequestStatusCodes.REQUESTED)
		.setParameter("exceptionBinderRequestCode", BinderRequestStatusCodes.EXCEPTION)
		.setParameter("receivedBinderRequestCode", BinderRequestStatusCodes.RECEIVED)
		.setParameter("requestedBinderRequestSourceRefCode", BinderRequestSourceCodes.LENDER)
		.setParameter("startDate", dateFromNow(-6))
		.setParameter("endDate", dateFromNow(1))
		.getResultList();

		return convertBinderRequestsToBinderDTOs(binderRequests, false);
	}

	@Transactional
	public BinderRequest receiveBinder(String binderRequestId) {
		workflowClient.put("/api/v1/binders/" + binderRequestId + "/receive", null, null);
		return binderRequestRepository.findOne(binderRequestId);
	}

	@Transactional
	public BinderRequest sendBinder(String binderRequestId, BinderDTO binderDTO) {
		BinderRequest binderRequest = binderRequestRepository.findOne(binderRequestId);
		if (binderRequest == null) {
			throw new NotFoundException("BinderReqest " + binderRequestId + " not found");
		}

		Date now = new Date();
		binderRequest.setLenderSentDate(binderDTO.getSentDate());
		binderRequest.setUpdatedBy(securityService.getUserId());
		binderRequest.setUpdatedTs(now);

		return binderRequestRepository.save(binderRequest);
	}

	@Transactional
	public BinderRequest cancelBinder(String binderRequestId) {
		return binderRequestService.cancelBinderRequest(binderRequestId);
	}

	public BinderDTO convertBinderRequestToBinderDTO(BinderRequest binderRequest, boolean showAssignedTo) {
		return convertBinderRequestsToBinderDTOs(ImmutableList.of(binderRequest), showAssignedTo).get(0);
	}

	public List<BinderDTO> convertBinderRequestsToBinderDTOs(List<BinderRequest> binderRequests, boolean showAssignedTo) {
		Map<String, String> initialReviewers = null;
		if (showAssignedTo) {
			List<String> binderRequestIds = Util.map(binderRequests, br -> br.getBinderRequestId());
			initialReviewers = findReceivedBinderRequestsInitialReviewers(binderRequestIds);
		}

		List<BinderDTO> binderDTOs = new ArrayList<BinderDTO>();
		for (BinderRequest binderRequest: binderRequests) {
			LoanSelection loanSelection = binderRequest.getLoanSelection();
			LoanSelectionCaseSummary loanSelectionCaseSummary = loanSelection.getLoanSelectionCaseSummary();
			Lender lender = loanSelection.getLender();

			BinderDTO binderDTO = new BinderDTO();
			binderDTO.setBinderId(binderRequest.getBinderRequestId());
			binderDTO.setCaseNumber(loanSelection.getCaseNumber());
			binderDTO.setRequestedFrom(binderRequest.getBinderRequestSourceRef().getDescription());
			binderDTO.setRequestedDate(DateUtils.convertDateToNoonUtcDate(binderRequest.getRequestedDate()));
			binderDTO.setDueDate(DateUtils.convertDateToNoonUtcDate(binderRequest.getDueDate()));
			binderDTO.setSentDate(DateUtils.convertDateToNoonUtcDate(binderRequest.getLenderSentDate()));
			binderDTO.setReceivedDate(DateUtils.convertDateToNoonUtcDate(loanSelection.getReceivedDt()));
			binderDTO.setIsElectronic((binderRequest.getIsElectronicInd() != null) && (binderRequest.getIsElectronicInd().equals('Y')));
			binderDTO.setReviewLocationId(loanSelection.getReviewLocation().getLocationName());
			binderDTO.setLocationName(loanSelection.getReviewLocation().getLocationName());
			binderDTO.setLenderName(lender.getName());
            binderDTO.setReviewType(loanSelection.getReviewTypeRef().getDescription());
			binderDTO.setBorrowerName(loanSelectionCaseSummary.getBorr1Name());
			binderDTO.setPropertyStreetAddress(loanSelectionCaseSummary.getPropAddr1());
			binderDTO.setStatusCode(binderRequest.getBinderRequestStatusRef().getCode());
			if ((showAssignedTo) && (BinderRequestStatusCodes.RECEIVED.equals(binderRequest.getBinderRequestStatusRef().getCode()))) {
				binderDTO.setAssignedTo(initialReviewers.get(binderRequest.getBinderRequestId()));
			}
			binderDTOs.add(binderDTO);
		}
		return binderDTOs;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Map<String, String> findReceivedBinderRequestsInitialReviewers(List<String> binderRequestIds) {
		Map<String, String> initialReviewers = new HashMap<String, String>();
		List<Object[]> binderRequestInitReviewLevelReviewers = new ArrayList<Object[]>();
		for (int start = 0; start < binderRequestIds.size(); ) {
			int end = Math.min(start + 500, binderRequestIds.size());

			binderRequestInitReviewLevelReviewers.addAll(entityManager.createNativeQuery(
				"SELECT BR.BINDER_REQUEST_ID, P.FIRST_NAME, P.LAST_NAME, P.MIDDLE_NAME FROM REVIEW_LEVEL RL " +
					"INNER JOIN REVIEW R ON (R.REVIEW_ID = RL.REVIEW_ID) " +
					"INNER JOIN REVIEW_LEVEL_STATUS_REF RLS ON (RLS.REVIEW_LEVEL_STATUS_ID = RL.REVIEW_LEVEL_STATUS_ID) " +
					"INNER JOIN REVIEW_LEVEL_TYPE_REF RLT ON (RLT.REVIEW_LEVEL_TYPE_ID = RL.REVIEW_LEVEL_TYPE_ID) " +
					"INNER JOIN BINDER_REQUEST BR ON (BR.SELECTION_ID = R.SELECTION_ID) " +
					"INNER JOIN BINDER_REQUEST_STATUS_REF BRS ON (BRS.BINDER_REQUEST_STATUS_ID = BR.BINDER_REQUEST_STATUS_ID) " +
					"INNER JOIN PERSONNEL P ON (P.PERSONNEL_ID = RL.REVIEWER_PERSONNEL_ID) " +
					"WHERE (BRS.CODE = '" + BinderRequestStatusCodes.RECEIVED + "') " +
						"AND (RLT.REVIEW_LEVEL_CD = '" + ReviewLevelTypeCodes.INITIAL + "') " +
						"AND (RLS.CODE IN (" +
							"'" + ReviewLevelStatusCodes.ASSIGNED + "', " +
							"'" + ReviewLevelStatusCodes.IN_PROGRESS + "', " +
							"'" + ReviewLevelStatusCodes.PENDING_BATCH_REVIEW + "' " +
							") " +
						") " +
						"AND (BR.BINDER_REQUEST_ID IN (:binderRequestIds))"
			)
			.setParameter("binderRequestIds", binderRequestIds.subList(start, end))
			.getResultList()
			);

			start = end;
		}

		for (Object[] reviewer : binderRequestInitReviewLevelReviewers) {
			initialReviewers.put((String)reviewer[0], (String)reviewer[2] + ", " + reviewer[1] +
				(StringUtils.isNotBlank((String)reviewer[3]) ? " " + (String)reviewer[3] : ""));
		}
		return initialReviewers;
	}

	private Date dateFromNow(int days) {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
}

