package gov.hud.lrs.common.repository;

public interface RvwLvlCaseSummaryRepositoryCustom {
	String findValueByReviewLevelId(String reviewLevelId, String dbColumn);
	void updateValueByReviewLevelId(String reviewLevelId, String dbColumn, String value, String username);
}