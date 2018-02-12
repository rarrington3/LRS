package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.BatchStatusRef;
import gov.hud.lrs.common.enumeration.BinderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;

public interface BatchRepository extends JpaRepository<Batch, String> {

	List<Batch> findByOwnerPersonnelPersonnelId(String personnelId);

	List<Batch> findByLenderLenderIdAndBatchStatusRefCodeNotIn(String lenderId, List<String> batchStatusCodes);
	
	List<Batch> findByLenderLenderIdAndBatchStatusRefCodeIn(String lenderId, List<String> batchStatusCodes);

	@Query(
		value =
			"SELECT B.* FROM BATCH B " +
			"INNER JOIN BATCH_STATUS_REF BS ON (BS.BATCH_STATUS_ID = B.BATCH_STATUS_ID) " +
			"WHERE " +
				"(BS.CODE NOT IN ('" + BatchStatusRef.CANCELLED + "', '" + BatchStatusRef.COMPLETED + "')) AND (" +
				"(B.OWNER_PERSONNEL_ID = ?1) OR " +
				"B.REVIEW_LOCATION_ID = ?2 OR " +
				"EXISTS (SELECT * FROM BATCH_PERSONNEL BP WHERE (BP.BATCH_ID = B.BATCH_ID) AND (BP.PERSONNEL_ID = ?1))) ",
		nativeQuery = true
	)
	List<Batch> findByOwnerOrBatchTeamMemberOrReviewLocation(String personnelId, String locationId);

	@Query(
		value =
			"SELECT B.* FROM BATCH B " +
			"INNER JOIN BATCH_STATUS_REF BS ON (BS.BATCH_STATUS_ID = B.BATCH_STATUS_ID) " +
			"WHERE " +
				"(BS.CODE = '" + BatchStatusRef.COMPLETED + "') AND (" +
				"(B.OWNER_PERSONNEL_ID = ?1) OR " +
				"B.REVIEW_LOCATION_ID = ?2 OR " +
				"EXISTS (SELECT * FROM BATCH_PERSONNEL BP WHERE (BP.BATCH_ID = B.BATCH_ID) AND (BP.PERSONNEL_ID = ?1))) ",
		nativeQuery = true
	)
	List<Batch> findByOwnerOrBatchTeamMemberOrReviewLocationCompleted(String personnelId, String locationId);

	@Query(
		value =
			"SELECT B.* FROM BATCH B " +
			"INNER JOIN BATCH_STATUS_REF BS ON (BS.BATCH_STATUS_ID = B.BATCH_STATUS_ID) " +
			"WHERE " +
				"(BS.CODE NOT IN ('" + BatchStatusRef.CANCELLED + "', '" + BatchStatusRef.COMPLETED + "')) AND (" +
				"B.REVIEW_LOCATION_ID = ?1) ",
		nativeQuery = true
	)
	List<Batch> findByReviewLocation(String locationId);

	@Query(
		value =
			"SELECT B.* FROM BATCH B " +
			"INNER JOIN BATCH_STATUS_REF BS ON (BS.BATCH_STATUS_ID = B.BATCH_STATUS_ID) " +
			"WHERE " +
				"(BS.CODE = '" + BatchStatusRef.COMPLETED + "') AND (" +
				"B.REVIEW_LOCATION_ID = ?1) ",
		nativeQuery = true
	)
	List<Batch> findByReviewLocationCompleted(String locationId);

	// this query's purpose is to check if we all batch review levels are done, so we can proceed to batch owner review
	// this is why we leave out the PENDING_BATCH_REVIEW status
	@Query(
		value =
			"SELECT COUNT(*) FROM BATCH B " +
			"INNER JOIN REVIEW R ON (R.BATCH_ID = B.BATCH_ID) " +
			"INNER JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = R.REVIEW_TYPE_ID) " +
			"INNER JOIN REVIEW_LEVEL RL ON (RL.REVIEW_ID = R.REVIEW_ID) " +
			"INNER JOIN REVIEW_LEVEL_STATUS_REF RLS ON (RLS.REVIEW_LEVEL_STATUS_ID = RL.REVIEW_LEVEL_STATUS_ID) " +
			"WHERE " +
				"(RLS.CODE IN ( " +
					"'" + ReviewLevelStatusCodes.AWAITING_ASSIGNMENT + "', " +
					"'" + ReviewLevelStatusCodes.ASSIGNED + "', " +
					"'" + ReviewLevelStatusCodes.IN_PROGRESS + "', " +
					"'" + ReviewLevelStatusCodes.EXCEPTION + "' " +		// talked with Tom about this, we'll hold the batch until exceptions are resolved
				")) AND " +
				"(RT.REVIEW_TYPE_CD <> '" + ReviewTypeCodes.OPERATIONAL + "') AND " +
				"(B.BATCH_ID = ?1) AND " +
				"(R.REVIEW_ID <> ?2)",
		nativeQuery = true
	)
	int findActiveReviewLevelCountByBatchIdAndReviewIdNot(String batchId, String reviewId);

	List<Batch> findBySelectionRequestSelectionRequestId(String selectionRequestId);

	@Query(
		value =
			"SELECT B.* FROM BATCH B " +
			"INNER JOIN BATCH_STATUS_REF BS ON (BS.BATCH_STATUS_ID = B.BATCH_STATUS_ID) " +
			"WHERE " +
				"(BS.CODE NOT IN ('" + BatchStatusRef.CANCELLED + "', '" + BatchStatusRef.COMPLETED + "')) AND (" +
				"(B.OWNER_PERSONNEL_ID = ?1) OR " +
				"EXISTS (SELECT * FROM BATCH_PERSONNEL BP WHERE (BP.BATCH_ID = B.BATCH_ID) AND (BP.PERSONNEL_ID = ?1))) ",
		nativeQuery = true
	)
	List<Batch> findByOwnerOrBatchTeamMemberPersonnelId(String personnelId);

	@Query(
		value =
			"SELECT B.* FROM BATCH B " +
			"INNER JOIN BATCH_STATUS_REF BS ON (BS.BATCH_STATUS_ID = B.BATCH_STATUS_ID) " +
			"WHERE " +
				"(BS.CODE = '" + BatchStatusRef.COMPLETED + "') AND (" +
				"(B.OWNER_PERSONNEL_ID = ?1) OR " +
				"EXISTS (SELECT * FROM BATCH_PERSONNEL BP WHERE (BP.BATCH_ID = B.BATCH_ID) AND (BP.PERSONNEL_ID = ?1))) ",
		nativeQuery = true
	)
	List<Batch> findByOwnerOrBatchTeamMemberPersonnelIdCompleted(String personnelId);
	
	@Query(
		value =
			"SELECT B.* FROM BATCH B " +
			"INNER JOIN BATCH_STATUS_REF BS ON (BS.BATCH_STATUS_ID = B.BATCH_STATUS_ID) " +
			"WHERE " +
				"(BS.CODE = '" + BatchStatusRef.ASSIGNED + "') AND " +
				"EXISTS ( " +
					"SELECT * FROM BINDER_REQUEST BR " +
					"INNER JOIN LOAN_SELECTION LS ON (LS.SELECTION_ID = BR.SELECTION_ID) " +
					"INNER JOIN BINDER_REQUEST_STATUS_REF BRS ON (BRS.BINDER_REQUEST_STATUS_ID = BR.BINDER_REQUEST_STATUS_ID) " +
					"WHERE " +
						"(LS.BATCH_ID = B.BATCH_ID) AND " +
						"(BRS.CODE <> '" + BinderRequestStatusCodes.RECEIVED + "')" +
				")",
		nativeQuery = true
	)
	List<Batch> findInAssignedStatusWithAnyBindersNotReceived();

	@Query(
		value =
			"SELECT B.* FROM BATCH B " +
			"INNER JOIN BATCH_STATUS_REF BS ON (BS.BATCH_STATUS_ID = B.BATCH_STATUS_ID) " +
			"WHERE " +
				"(BS.CODE = '" + BatchStatusRef.REQUESTED + "') AND " +
				"NOT EXISTS ( " +
					"SELECT * FROM BINDER_REQUEST BR " +
					"INNER JOIN LOAN_SELECTION LS ON (LS.SELECTION_ID = BR.SELECTION_ID) " +
					"INNER JOIN BINDER_REQUEST_STATUS_REF BRS ON (BRS.BINDER_REQUEST_STATUS_ID = BR.BINDER_REQUEST_STATUS_ID) " +
					"WHERE " +
						"(LS.BATCH_ID = B.BATCH_ID) AND " +
						"(BRS.CODE <> '" + BinderRequestStatusCodes.RECEIVED + "')" +
				")",
		nativeQuery = true
	)
	List<Batch> findInBinderRequestStatusWithAllBindersReceived();

}
