package gov.hud.lrs.services.bizservice;

import java.util.Date;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.entity.ScoringModelVersion;
import gov.hud.lrs.common.entity.ScoringModelVersionFactor;
import gov.hud.lrs.common.enumeration.ScoringModelVersionStatusCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.ScoringModelVersionFactorRepository;
import gov.hud.lrs.common.repository.ScoringModelVersionRepository;
import gov.hud.lrs.common.repository.ScoringModelVersionStatusRefRepository;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class ScoringModelService {

	@Autowired private SecurityService securityService;
	@Autowired private ScoringModelVersionRepository scoringModelVersionRepository;
	@Autowired private ScoringModelVersionFactorRepository scoringModelFactorRepository;
	@Autowired private ScoringModelVersionStatusRefRepository scoringModelVersionStatusRefRepository;

	@PersistenceContext private EntityManager entityManager;

	@Transactional
	public ScoringModelVersion createDuplicateScoringModelVersion(String scoringModelVersionId, String modelName) {
		String userId = securityService.getUserId();
		Date now = new Date();

		// Select the review by id and detach it in order to create a new copy
		ScoringModelVersion scoringModelVersion = scoringModelVersionRepository.findOne(scoringModelVersionId);
		if (scoringModelVersion == null) {
			throw new NotFoundException(ImmutableList.of("Model Version " + scoringModelVersionId + " is not found"));
		}
		if (modelName == null) {
			throw new BadRequestException(ImmutableList.of("Model name is empty"));
		}
		if (scoringModelVersionRepository.findTopByScoringModelAndModelName(scoringModelVersion.getScoringModel(), modelName) != null) {
			throw new BadRequestException(ImmutableList.of("Model with name '" + modelName + "' already exists!"));
		}

		Set<ScoringModelVersionFactor> scoringModelVersionFactors = scoringModelVersion.getScoringModelVersionFactors();
		entityManager.detach(scoringModelVersion);
		if (modelName != null) {
			scoringModelVersion.setModelName(modelName);
		}
		scoringModelVersion.setCreatedBy(userId);
		scoringModelVersion.setCreatedTs(now);
		scoringModelVersion.setUpdatedBy(userId);
		scoringModelVersion.setUpdatedTs(now);
		scoringModelVersion.setScoringModelVersionId(null);
		scoringModelVersion.setScoringModelVersionStatusRef(scoringModelVersionStatusRefRepository.findByCode(ScoringModelVersionStatusCodes.DRAFT));
		String scoringModelId = scoringModelVersion.getScoringModel().getScoringModelId();
		if (null != scoringModelId) {
			scoringModelVersion.setModelVerNum((short) (scoringModelVersionRepository.getMaxVersion(scoringModelId) + 1));
		}
		scoringModelVersion = scoringModelVersionRepository.save(scoringModelVersion);

		for (ScoringModelVersionFactor scoringModelVersionFactor : scoringModelVersionFactors) {
			entityManager.detach(scoringModelVersionFactor);
			scoringModelVersionFactor.getId().setScoringModelVersionId(scoringModelVersion.getScoringModelVersionId());
			scoringModelVersionFactor.setCreatedBy(userId);
			scoringModelVersionFactor.setCreatedTs(now);
			scoringModelVersionFactor.setUpdatedBy(userId);
			scoringModelVersionFactor.setUpdatedTs(now);
			scoringModelFactorRepository.save(scoringModelVersionFactor);
		}
		return scoringModelVersion;
	}

	@Transactional
	public ScoringModelVersion archiveScoringModelVersion(String scoringModelVersionId) {
		ScoringModelVersion scoringModelVersion = scoringModelVersionRepository.findOne(scoringModelVersionId);
		scoringModelVersion.setScoringModelVersionStatusRef(scoringModelVersionStatusRefRepository.findByCode(ScoringModelVersionStatusCodes.ARCHIVED));
		scoringModelVersion.setUpdatedBy(securityService.getUserId());
		scoringModelVersion.setUpdatedTs(new Date());
		return scoringModelVersionRepository.save(scoringModelVersion);
	}

}
