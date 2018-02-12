package gov.hud.lrs.common.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class RvwLvlCaseSummaryRepositoryImpl implements RvwLvlCaseSummaryRepositoryCustom {
	@PersistenceContext(unitName = "lrs")
	private EntityManager entityManager;

	@Override
	public String findValueByReviewLevelId(String reviewLevelId, String dbColumn) {
		String sql = "SELECT " + dbColumn + " FROM RVW_LVL_CASE_SUMMARY WHERE REVIEW_LEVEL_ID = :reviewLevelId";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("reviewLevelId", reviewLevelId);
		Object result = query.getSingleResult();
		return (result != null) ? result.toString() : null;
	}

	@Override
	public void updateValueByReviewLevelId(String reviewLevelId, String dbColumn, String value, String username) {
		String sql = "UPDATE RVW_LVL_CASE_SUMMARY SET " + dbColumn + " = :value, UPDATED_BY = :username, UPDATED_TS = CURRENT_TIMESTAMP WHERE REVIEW_LEVEL_ID = :reviewLevelId";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("value", value);
		query.setParameter("username", username);
		query.setParameter("reviewLevelId", reviewLevelId);
		query.executeUpdate();
	}
}