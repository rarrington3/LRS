package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.hud.lrs.common.entity.SelectionReason;

public interface SelectionReasonRepository extends JpaRepository<SelectionReason, String> {

	SelectionReason findByCode(String code);

	@Query(value =
		"SELECT SR.* " +
		"FROM SELECTION_REQUEST RQ " +
		"LEFT JOIN MANUAL_SELECTION_REQUEST MRQ ON (MRQ.SELECTION_REQUEST_ID = RQ.SELECTION_REQUEST_ID) " +
		"LEFT JOIN SELECTION_REASON FSR ON (FSR.SELECTION_REASON_ID = MRQ.SELECTION_REASON_ID) " +
		"INNER JOIN SELECTION_REQUEST_TYPE_REF RQT ON (RQT.SELECTION_REQUEST_TYPE_ID = RQ.SELECTION_REQUEST_TYPE_ID) " +
		"INNER JOIN SELECTION_REASON SR ON (SR.SELECTION_REASON_ID =  " +
			"(CASE " +
				"WHEN RQT.CODE = 'FHA_MANUAL' THEN FSR.SELECTION_REASON_ID " +
				"WHEN RQT.CODE = 'LENDER_MONITORING' THEN (SELECT SELECTION_REASON_ID FROM SELECTION_REASON WHERE CODE = 'LENDER_MONITORING') " +
				"WHEN RQT.CODE = 'LENDER_SELF_REPORT' THEN (SELECT SELECTION_REASON_ID FROM SELECTION_REASON WHERE CODE = 'LENDER_SELF_REPORT') " +
			"END) " +
		") " +
		"WHERE RQ.SELECTION_REQUEST_ID = ?1 ",
		nativeQuery = true
	)
	SelectionReason findSelectionReasonBySelectionRequestId(String selectionRequestId);

}
