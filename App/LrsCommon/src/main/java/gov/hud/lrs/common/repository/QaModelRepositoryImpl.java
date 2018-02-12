package gov.hud.lrs.common.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class QaModelRepositoryImpl implements QaModelRepositoryCustom {

	@PersistenceContext(unitName = "lrs")
	EntityManager entityManager;

	@Override
	public void callSpQaModelDuplicate(String qaModelId, String modelName, String description, String userId) {
		entityManager.createNativeQuery("exec sp_create_qa_model :1, :2, :3, :4")
		.setParameter("1", qaModelId)
		.setParameter("2", modelName)
		.setParameter("3", description)
		.setParameter("4", userId)
		.executeUpdate();
	}

}
