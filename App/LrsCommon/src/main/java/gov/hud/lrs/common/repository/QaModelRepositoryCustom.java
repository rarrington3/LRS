package gov.hud.lrs.common.repository;

public interface QaModelRepositoryCustom {

	void callSpQaModelDuplicate(String qaModelId, String modelName, String description, String userId);

}
