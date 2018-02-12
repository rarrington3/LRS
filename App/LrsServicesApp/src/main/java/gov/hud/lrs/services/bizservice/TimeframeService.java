package gov.hud.lrs.services.bizservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewLevelTimeLimitsDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.TimeLimitDTO;
import gov.hud.lrs.common.entity.ReviewLevelIterationTimeframe;
import gov.hud.lrs.common.entity.ReviewLevelTypeRef;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.repository.ReviewLevelIterationTimeframeRepository;
import gov.hud.lrs.common.repository.ReviewLevelTypeRefRepository;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class TimeframeService {

	@Autowired private ReviewLevelIterationTimeframeRepository reviewLevelIterationTimeframeRepository;
	@Autowired private ReviewLevelTypeRefRepository reviewLevelTypeRefRepository;

	@Autowired private SecurityService securityService;

	public List<ReviewLevelTimeLimitsDTO> getReviewTimeframe() {
		return getTimeframe(true);
	}

	public List<ReviewLevelTimeLimitsDTO> getResponseTimeframe() {
		return getTimeframe(false);
	}

	private List<ReviewLevelTimeLimitsDTO> getTimeframe(boolean reviewer) {
		List<String> excludedReviewLevelTypeCodes = new ArrayList<String>();
		if (reviewer) {
			excludedReviewLevelTypeCodes.add("");
		} else {
			excludedReviewLevelTypeCodes.add(ReviewLevelTypeCodes.INDEMNIFICATION);
			excludedReviewLevelTypeCodes.add(ReviewLevelTypeCodes.FORCE_INDEMNIFICATION);
		}

		List<ReviewLevelIterationTimeframe> reviewLevelIterationTimeframes = reviewLevelIterationTimeframeRepository.findByReviewLevelTypeRefReviewLevelCdNotIn(excludedReviewLevelTypeCodes);

		Map<String, ReviewLevelTimeLimitsDTO> reviewLevelTypeRefCodeToReviewLevelTimeLimitsDTO = new HashMap<String, ReviewLevelTimeLimitsDTO>();
		final int maxIterations = reviewer ? 6 : 5;
		for (ReviewLevelTypeRef reviewLevelTypeRef : reviewLevelTypeRefRepository.findByReviewLevelCdNotIn(excludedReviewLevelTypeCodes)) {
			String code = reviewLevelTypeRef.getReviewLevelCd();
			if (code.equalsIgnoreCase(ReviewLevelTypeCodes.INITIAL) 
				|| code.equalsIgnoreCase(ReviewLevelTypeCodes.BINDER_REQUEST)
				|| code.equalsIgnoreCase(ReviewLevelTypeCodes.INDEMNIFICATION)
				|| code.equalsIgnoreCase(ReviewLevelTypeCodes.FORCE_INDEMNIFICATION)
			) {
				ReviewLevelTimeLimitsDTO reviewLevelTimeframeDTO = new ReviewLevelTimeLimitsDTO();
				reviewLevelTimeframeDTO.setReviewLevelCode(code);
				reviewLevelTimeframeDTO.setReviewLevelDescription(reviewLevelTypeRef.getDescription());
				reviewLevelTimeframeDTO.setSelectionReasons(new ArrayList<TimeLimitDTO>());
				reviewLevelTypeRefCodeToReviewLevelTimeLimitsDTO.put(reviewLevelTimeframeDTO.getReviewLevelCode(), reviewLevelTimeframeDTO);

			} else {
				for (int iteration = 1; iteration <= maxIterations; iteration++) {
					ReviewLevelTimeLimitsDTO reviewLevelTimeframeDTO = new ReviewLevelTimeLimitsDTO();
					reviewLevelTimeframeDTO.setReviewLevelCode(code +  iteration);
					reviewLevelTimeframeDTO.setReviewLevelDescription(reviewLevelTypeRef.getDescription() + " " + iteration);
					reviewLevelTimeframeDTO.setSelectionReasons(new ArrayList<TimeLimitDTO>());
					reviewLevelTypeRefCodeToReviewLevelTimeLimitsDTO.put(reviewLevelTimeframeDTO.getReviewLevelCode(), reviewLevelTimeframeDTO);
				}
			}
		}

		for (ReviewLevelIterationTimeframe reviewLevelIterationTimeframe : reviewLevelIterationTimeframes) {
			String code = reviewLevelIterationTimeframe.getReviewLevelTypeRef().getReviewLevelCd();

			TimeLimitDTO timeLimitDTO;
			if (code.equalsIgnoreCase(ReviewLevelTypeCodes.INITIAL) 
				|| code.equalsIgnoreCase(ReviewLevelTypeCodes.BINDER_REQUEST)
				|| code.equalsIgnoreCase(ReviewLevelTypeCodes.INDEMNIFICATION)
				|| code.equalsIgnoreCase(ReviewLevelTypeCodes.FORCE_INDEMNIFICATION)
			) {
				timeLimitDTO = new TimeLimitDTO();
				timeLimitDTO.setSelectionReasonCode(reviewLevelIterationTimeframe.getConsolidatedSelectionReason().getCode());
				timeLimitDTO.setDays(new BigDecimal(
					reviewer ? reviewLevelIterationTimeframe.getReviewDaysIteration1() : reviewLevelIterationTimeframe.getResponseDaysIteration1()
				));
				reviewLevelTypeRefCodeToReviewLevelTimeLimitsDTO.get(code).getSelectionReasons().add(timeLimitDTO);

			} else {
				timeLimitDTO = new TimeLimitDTO();
				timeLimitDTO.setSelectionReasonCode(reviewLevelIterationTimeframe.getConsolidatedSelectionReason().getCode());
				timeLimitDTO.setDays(new BigDecimal(
					reviewer ? reviewLevelIterationTimeframe.getReviewDaysIteration1() : reviewLevelIterationTimeframe.getResponseDaysIteration1()
				));
				reviewLevelTypeRefCodeToReviewLevelTimeLimitsDTO.get(code + "1").getSelectionReasons().add(timeLimitDTO);

				timeLimitDTO = new TimeLimitDTO();
				timeLimitDTO.setSelectionReasonCode(reviewLevelIterationTimeframe.getConsolidatedSelectionReason().getCode());
				timeLimitDTO.setDays(new BigDecimal(
					reviewer ? reviewLevelIterationTimeframe.getReviewDaysIteration2() : reviewLevelIterationTimeframe.getResponseDaysIteration2()
				));
				reviewLevelTypeRefCodeToReviewLevelTimeLimitsDTO.get(code + "2").getSelectionReasons().add(timeLimitDTO);

				timeLimitDTO = new TimeLimitDTO();
				timeLimitDTO.setSelectionReasonCode(reviewLevelIterationTimeframe.getConsolidatedSelectionReason().getCode());
				timeLimitDTO.setDays(new BigDecimal(
					reviewer ? reviewLevelIterationTimeframe.getReviewDaysIteration3() : reviewLevelIterationTimeframe.getResponseDaysIteration3()
				));
				reviewLevelTypeRefCodeToReviewLevelTimeLimitsDTO.get(code + "3").getSelectionReasons().add(timeLimitDTO);

				timeLimitDTO = new TimeLimitDTO();
				timeLimitDTO.setSelectionReasonCode(reviewLevelIterationTimeframe.getConsolidatedSelectionReason().getCode());
				timeLimitDTO.setDays(new BigDecimal(
					reviewer ? reviewLevelIterationTimeframe.getReviewDaysIteration4() : reviewLevelIterationTimeframe.getResponseDaysIteration4()
				));
				reviewLevelTypeRefCodeToReviewLevelTimeLimitsDTO.get(code + "4").getSelectionReasons().add(timeLimitDTO);

				timeLimitDTO = new TimeLimitDTO();
				timeLimitDTO.setSelectionReasonCode(reviewLevelIterationTimeframe.getConsolidatedSelectionReason().getCode());
				timeLimitDTO.setDays(new BigDecimal(
					reviewer ? reviewLevelIterationTimeframe.getReviewDaysIteration5() : reviewLevelIterationTimeframe.getResponseDaysIteration5()
				));
				reviewLevelTypeRefCodeToReviewLevelTimeLimitsDTO.get(code + "5").getSelectionReasons().add(timeLimitDTO);

				if (reviewer) {
					timeLimitDTO = new TimeLimitDTO();
					timeLimitDTO.setSelectionReasonCode(reviewLevelIterationTimeframe.getConsolidatedSelectionReason().getCode());
					timeLimitDTO.setDays(new BigDecimal(reviewLevelIterationTimeframe.getReviewDaysIteration6()));
					reviewLevelTypeRefCodeToReviewLevelTimeLimitsDTO.get(code + "6").getSelectionReasons().add(timeLimitDTO);
				}
			}
		}

		List<ReviewLevelTimeLimitsDTO> reviewLevelTimeframeDTOs = new ArrayList<ReviewLevelTimeLimitsDTO>(reviewLevelTypeRefCodeToReviewLevelTimeLimitsDTO.values());
		reviewLevelTimeframeDTOs.sort((r1, r2) -> r1.getReviewLevelCode().compareTo(r2.getReviewLevelCode()));

		return reviewLevelTimeframeDTOs;
	}

	@Transactional
	public void updateReviewerTimeframe(String reviewLevelCode, TimeLimitDTO timeLimitDTO) {
		updateTimeframe(reviewLevelCode, timeLimitDTO, true);
	}

	@Transactional
	public void updateResponseTimeframe(String reviewLevelCode, TimeLimitDTO timeLimitDTO) {
		updateTimeframe(reviewLevelCode, timeLimitDTO, false);
	}

	@Transactional
	private void updateTimeframe(String reviewLevelCodeIteration, TimeLimitDTO timeLimitDTO, boolean review) {
		String reviewLevelCd = reviewLevelCodeIteration.substring(0, 4);
		String levelIteration = "1";
		if (reviewLevelCodeIteration.length() > 4) {
			levelIteration = reviewLevelCodeIteration.substring(4);
		}

		ReviewLevelIterationTimeframe reviewLevelIterationTimeframe = reviewLevelIterationTimeframeRepository.findByConsolidatedSelectionReasonCodeAndReviewLevelTypeRefReviewLevelCd(
			timeLimitDTO.getSelectionReasonCode(),
			reviewLevelCd
		);
		if (reviewLevelIterationTimeframe == null) {
			throw new BadRequestException("No ReviewLevelTimeframe for ReviewLevelTypeRef code " + reviewLevelCd + " and ConsolidatedSelectionReason code " + timeLimitDTO.getSelectionReasonCode());
		}

		if (timeLimitDTO.getDays() == null) {
			throw new BadRequestException("Days is required");
		}
		int days = timeLimitDTO.getDays().intValue();
		if (days < 0) {
			throw new BadRequestException("Days must be >= 0");
		}

		if (review) {
			if (levelIteration.equals("1")) { reviewLevelIterationTimeframe.setReviewDaysIteration1(days); }
			if (levelIteration.equals("2")) { reviewLevelIterationTimeframe.setReviewDaysIteration2(days); }
			if (levelIteration.equals("3")) { reviewLevelIterationTimeframe.setReviewDaysIteration3(days); }
			if (levelIteration.equals("4")) { reviewLevelIterationTimeframe.setReviewDaysIteration4(days); }
			if (levelIteration.equals("5")) { reviewLevelIterationTimeframe.setReviewDaysIteration5(days); }
			if (levelIteration.equals("6")) { reviewLevelIterationTimeframe.setReviewDaysIteration6(days); }
		} else {
			if (levelIteration.equals("1")) { reviewLevelIterationTimeframe.setResponseDaysIteration1(days); }
			if (levelIteration.equals("2")) { reviewLevelIterationTimeframe.setResponseDaysIteration2(days); }
			if (levelIteration.equals("3")) { reviewLevelIterationTimeframe.setResponseDaysIteration3(days); }
			if (levelIteration.equals("4")) { reviewLevelIterationTimeframe.setResponseDaysIteration4(days); }
			if (levelIteration.equals("5")) { reviewLevelIterationTimeframe.setResponseDaysIteration5(days); }
		}

		reviewLevelIterationTimeframe.setUpdatedBy(securityService.getUserId());
		reviewLevelIterationTimeframe.setUpdatedTs(new Date());
	}

}
