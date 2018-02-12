package gov.hud.lrs.common.repository;

import java.util.List;

public interface LoanSelectionRepositoryCustom {

	public class ReviewLocationIdLoanSelectionCount {
		public String reviewLocationId;
		public Integer loanSelectionCount;
	}

	List<ReviewLocationIdLoanSelectionCount> findReviewLocationLoanSelectionCounts(int month);
	Integer findReviewLocationLoanSelectionCountsByReviewLocationId(String reviewLocationId, int month);

}
