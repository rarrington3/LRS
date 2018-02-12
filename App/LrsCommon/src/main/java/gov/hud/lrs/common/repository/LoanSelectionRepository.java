package gov.hud.lrs.common.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.hud.lrs.common.entity.BatchStatusRef;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;

public interface LoanSelectionRepository extends JpaRepository<LoanSelection, String>, LoanSelectionRepositoryCustom {

	// TODO:
	// we would typically do this with an auto-generated query, e.g.
		// List<LoanSelection> findByLoanSelectionStatusRefCodeAndReviewLocationIsNull(String loanSelectionStatusRefCode);
	// This will cause N+1 selects, however, since LoanSelection has an @OneToOne with LoanSelectionCaseSummary *without* optional=true set (See LoanSelection.getLoanSelectionCaseSummary())
	// Non-optional @OneToOnes are *always* eager, even if you specify lazy in the annotation
	// The N+1 select will not occur if we use optional=true on the @OneToOne, however the entity generator unfortunately has no way to specify this
	// For this reason we use a custom JPQL query with an explicit join fetch to avoid the N+1 select problem
	// When we get rid of the entity generator, we can switch back to the auto-generated query

	@Query(
		value =
			"SELECT LS.* FROM LOAN_SELECTION LS " +
			"INNER JOIN LOAN_SELECTION_STATUS_REF LSS ON (LSS.LOAN_SELECTION_STATUS_ID = LS.LOAN_SELECTION_STATUS_ID) " +
			"LEFT JOIN BATCH B ON (B.BATCH_ID = LS.BATCH_ID) " +
			"LEFT JOIN BATCH_STATUS_REF BS ON (BS.BATCH_STATUS_ID = B.BATCH_STATUS_ID) " +
			"WHERE " +
				"(LSS.CODE = '" + LoanSelectionStatusCodes.DISTRIBUTED + "') AND " +
				// either it's not part of a batch, *or* it's part of a batch that has been assigned/requested (has an owner + batch team)
				// we do this to sequence binder request after batch team creation, even though it's not strictly required
				// this simplifies the batch assignment exception resolution since we don't have to worry about assigning reviews due to already-received binders
				"((LS.BATCH_ID IS NULL) OR (BS.CODE in ('" + BatchStatusRef.ASSIGNED + "', '" + BatchStatusRef.REQUESTED + "')))",
		nativeQuery = true
	)
	List<LoanSelection> findReadyForBinderRequest();

	@Query (
		value =
			"SELECT LS.* FROM LOAN_SELECTION LS " +
			"INNER JOIN LOAN_SELECTION_STATUS_REF LSS ON (LSS.LOAN_SELECTION_STATUS_ID = LS.LOAN_SELECTION_STATUS_ID) " +
			"LEFT JOIN BATCH B ON (B.BATCH_ID = LS.BATCH_ID) " +
			"WHERE " +
				"(LSS.CODE = '"+LoanSelectionStatusCodes.SELECTED +"') AND " +
				"(LS.REVIEW_LOCATION_ID IS NULL) AND " +
				"((LS.BATCH_ID IS NULL) OR (B.REVIEW_LOCATION_ID IS NOT NULL)) ",	// either it's not part of a batch, *or* it's part of a batch that *does* have a location (skip batches in dist overflow exception)
		nativeQuery = true
	)
	List<LoanSelection> findLoanSelectionsWithoutReviewLocationsNeedingDistribution();
	
	List<LoanSelection> findByCaseNumber(String caseNumber);
	
	List<LoanSelection> findByCaseNumberAndReviewTypeRefIn (String caseNumber, List<ReviewTypeRef> reviewTypeRefList);
}
