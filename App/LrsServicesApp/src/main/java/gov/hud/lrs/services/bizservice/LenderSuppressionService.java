package gov.hud.lrs.services.bizservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.LenderSelectionAdjustmentDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LiteLenderDTO;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LenderSuppression;
import gov.hud.lrs.common.enumeration.ReviewStatusCodes;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.LenderSuppressionRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.LenderService;
import gov.hud.lrs.common.util.DateUtils;
import gov.hud.lrs.common.util.Util;

@Service
public class LenderSuppressionService {

	@Autowired private LenderSuppressionRepository lenderSuppressionRepository;
	@Autowired private LenderService lenderService;
	@Autowired private SecurityService securityService;

	@PersistenceContext private EntityManager entityManager;

	public List<LenderSelectionAdjustmentDTO> getLenderSuppressions() {
		List<LenderSuppression> lenderSuppresions = lenderSuppressionRepository.findAll();
		List<LenderSelectionAdjustmentDTO> lenderSelectionAdjustmentDTOs = new ArrayList<LenderSelectionAdjustmentDTO>();

		@SuppressWarnings("unchecked")
		List<Object[]> lenderIdReviewCounts = entityManager
			.createNativeQuery(
				"SELECT SEL.MTGEE5, COUNT(R.REVIEW_ID) " +
				"FROM REVIEW R " +
				"INNER JOIN LOAN_SELECTION SEL ON (SEL.SELECTION_ID = R.SELECTION_ID) " +
				"INNER JOIN LENDER_SUPPRESSION SUP ON (SUP.MTGEE5 = SEL.MTGEE5) " +
				"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
				"WHERE (RS.CODE NOT IN (:reviewStatusCodes)) " +
				"GROUP BY SEL.MTGEE5"
			)
			.setParameter("reviewStatusCodes", ImmutableList.of(ReviewStatusCodes.COMPLETED, ReviewStatusCodes.CANCELLED))
			.getResultList();
		Map<String, Integer> lenderIdToReviewCounts = Util.index(lenderIdReviewCounts, x -> (String)x[0], x -> (Integer)x[1]);

		for (LenderSuppression lenderSuppression : lenderSuppresions) {
			Lender lender = lenderSuppression.getLender();
			LiteLenderDTO liteLenderDTO = new LiteLenderDTO();
			liteLenderDTO.setLenderId(lender.getLenderId());
			liteLenderDTO.setName(lender.getName());

			LenderSelectionAdjustmentDTO lenderSelectionAdjustmentDTO = new LenderSelectionAdjustmentDTO();
			lenderSelectionAdjustmentDTO.setLender(liteLenderDTO);
			lenderSelectionAdjustmentDTO.setStartDate(DateUtils.convertDateToNoonUtcDate(lenderSuppression.getCreatedTs()));

			Integer reviewCount = lenderIdToReviewCounts.get(lenderSuppression.getMtgee5());
			lenderSelectionAdjustmentDTO.setActiveReviews((reviewCount == null) ? 0 : reviewCount);

			lenderSelectionAdjustmentDTOs.add(lenderSelectionAdjustmentDTO);
		}
		return lenderSelectionAdjustmentDTOs;
	}

	@Transactional
	public LenderSuppression createLenderSuppression(String lenderId) {
		Lender lender = lenderService.getLender(lenderId);
		if (lender == null) {
			throw new NotFoundException("No lender for lenderId " + lenderId);
		}

		LenderSuppression lenderSuppression = new LenderSuppression();
		lenderSuppression.setLender(lender);
		String userId = securityService.getUserId();
		Date now = new Date();
		lenderSuppression.setCreatedBy(userId);
		lenderSuppression.setCreatedTs(now);
		lenderSuppression.setUpdatedBy(userId);
		lenderSuppression.setUpdatedTs(now);
		return lenderSuppressionRepository.save(lenderSuppression);
	}

	@Transactional
	public void deleteLenderSuppression(String lenderId) {
		if (!lenderSuppressionRepository.exists(lenderId)) {
			throw new NotFoundException("NO LenderSuppression for lenderId " + lenderId);
		}
		lenderSuppressionRepository.delete(lenderId);
	}

}
