package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gov.hud.lrs.common.entity.ScoringModel;

@Repository
public interface ScoringModelRepository extends JpaRepository<ScoringModel, String> {
	List<ScoringModel> findAll();	
	public String findModelIdByDescription(String description);
	List<ScoringModel> findByScoringModelTypeRefCodeIn(List<String> scoringModelTypeCodes);
	ScoringModel findByCode(String code);
}
