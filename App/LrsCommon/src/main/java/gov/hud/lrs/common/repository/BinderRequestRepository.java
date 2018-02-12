package gov.hud.lrs.common.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.hud.lrs.common.entity.BinderRequest;

public interface BinderRequestRepository extends JpaRepository<BinderRequest, String> {

	List<BinderRequest> findByBinderRequestStatusRefCode(String binderRequestStatusCode);

	List<BinderRequest> findByDueDateLessThanAndBinderRequestStatusRefCode(Date date, String statusCode);
	
	@Query(
			value =
			"SELECT BR.* FROM BINDER_REQUEST BR " +
					"INNER JOIN LOAN_SELECTION LS ON LS.SELECTION_ID = BR.SELECTION_ID " +
					"INNER JOIN REVIEW_TYPE_REF RTF ON RTF.REVIEW_TYPE_ID = LS.REVIEW_TYPE_ID " + 
					"INNER JOIN BINDER_REQUEST_STATUS_REF BRSF ON BRSF.BINDER_REQUEST_STATUS_ID = BR.BINDER_REQUEST_STATUS_ID " + 
					"WHERE " +
					"BRSF.CODE = ?1 AND RTF.REVIEW_TYPE_CD <> ?2", 
			nativeQuery = true
		)
	List<BinderRequest> findByBinderRequestStatusRefCodeAndReviewTypeNotIn(String binderRequestStatusCode, String reviewTypeCd);

}
