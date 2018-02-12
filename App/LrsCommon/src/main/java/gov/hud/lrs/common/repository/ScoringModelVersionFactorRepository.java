package gov.hud.lrs.common.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ScoringModelVersionFactor;
import gov.hud.lrs.common.entity.ScoringModelVersion;

@Repository
public interface ScoringModelVersionFactorRepository extends JpaRepository<ScoringModelVersionFactor, String> {

	List<ScoringModelVersionFactor> findByScoringModelVersion(ScoringModelVersion scoringModelVersion);

}
