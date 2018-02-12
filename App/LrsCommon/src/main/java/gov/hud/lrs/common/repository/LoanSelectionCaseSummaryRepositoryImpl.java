package gov.hud.lrs.common.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class LoanSelectionCaseSummaryRepositoryImpl implements LoanSelectionCaseSummaryRepositoryCustom {

	@PersistenceContext(unitName = "lrs")
	private EntityManager entityManager;

	@Override
	public String findValueBySelectionId(String dbColumn, String selectionId) {
		String sql = "SELECT " + dbColumn + " FROM LOAN_SELECTION_CASE_SUMMARY WHERE SELECTION_ID = :selectionId";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("selectionId", selectionId);
		Object result = query.getSingleResult();
		return (result != null) ? result.toString() : null;
	}

}
