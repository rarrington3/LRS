package gov.hud.lrs.workflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.IndemAcceptDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewLevelInfoDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.SimpleSelectionDTO;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.ReviewLevelRepository;
import gov.hud.lrs.common.service.CommonBatchService;
import gov.hud.lrs.common.service.CommonReviewService;
import gov.hud.lrs.workflow.service.AssignmentService;
import gov.hud.lrs.workflow.service.ReviewService;

@RestController
@RequestMapping("/api/v1")
public class ReviewsController {

	@Autowired private ReviewLevelRepository reviewLevelRepository;

	@Autowired private AssignmentService assignmentService;
	@Autowired private CommonBatchService commonBatchService;
	@Autowired private CommonReviewService commonReviewService;
	@Autowired private ReviewService reviewService;

	@InitBinder("reviewLevelSubmitDTO")
	protected void initBinderReceiptDTOBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "indemnificationType" });
	}

	// returns all personnel IDs passing the scoring model, highest scoring first
	@RequestMapping(value = "/reviews/{reviewId}/runAssignmentModel", method = RequestMethod.PUT)
	public String[] runAssignmentModel(@PathVariable("reviewId") String reviewId, @PathVariable("reviewLevelId") String reviewLevelId) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		if (reviewLevel == null) {
			throw new NotFoundException("No ReviewLevel for reviewLevelId " + reviewLevelId);
		}

		List<Personnel> personnel = assignmentService.runAssignmentModelForCurrentReviewLocation(reviewLevel);
		String[] personnelIds = new String[personnel.size()];
		for (int i = 0; i < personnel.size(); i++) {
			personnelIds[i] = personnel.get(i).getPersonnelId();
		}

		return personnelIds;
	}

	@RequestMapping(value = "/reviews/{reviewId}/levels/{reviewLevelId}/submit", method = RequestMethod.PUT)
	public void submitReviewLevel(
		@PathVariable("reviewId") String reviewId,
		@PathVariable("reviewLevelId") String reviewLevelId,
		@RequestBody() ReviewLevelInfoDTO reviewLevelInfoDTO
	) {
		reviewService.submitReviewLevel(reviewLevelId, reviewLevelInfoDTO.getIndemnificationTypeCode(), reviewLevelInfoDTO.getQcOutcomeCd());
	}

	@RequestMapping(value = "/batches/{batchId}/submit", method = RequestMethod.PUT)
	public void submitBatch(@PathVariable("batchId") String batchId) {
		reviewService.submitBatch(batchId);
	}

	@RequestMapping(value = "/batches/{batchId}/submit/lender", method = RequestMethod.PUT)
	public void submitLenderBatch(@PathVariable("batchId") String batchId) {
		reviewService.submitLenderBatch(batchId);
	}

	@RequestMapping(value = "/reviews/{reviewId}/levels/{reviewLevelId}/lenderSubmit", method = RequestMethod.PUT)
	public void submitLenderResponse(@PathVariable("reviewId") String reviewId, @PathVariable("reviewLevelId") String reviewLevelId) {
		reviewService.submitLenderResponse(reviewLevelId);
	}

	@RequestMapping(value = "/reviews/{reviewId}/forceIndemnification", method = RequestMethod.PUT)
	public void forceIndemnification(@PathVariable("reviewId") String reviewId, @RequestParam("indemType") String indemnificationTypeCode) {
		reviewService.forceIndemnification(reviewId, indemnificationTypeCode);
	}

	@RequestMapping(value = "/reviews/{reviewId}/levels/{reviewLevelId}/indemnification/lenderSubmit", method = RequestMethod.PUT)
	public void indemLenderSubmit(@PathVariable("reviewId") String reviewId, @PathVariable("reviewLevelId") String reviewLevelId) {
		reviewService.lenderIndemnification(reviewLevelId);
	}

	@RequestMapping(value = "/reviews/{reviewId}/levels/{reviewLevelId}/reassign", method = RequestMethod.PUT)
	public String reassign(
		@PathVariable("reviewId") String reviewId,
		@PathVariable("reviewLevelId") String reviewLevelId,
		@RequestBody SimpleSelectionDTO simpleSelectionDTO
	) {
		Personnel personnel = reviewService.reassign(reviewLevelId, simpleSelectionDTO.getSelectionId(), simpleSelectionDTO.getReasonCode());
		return (personnel != null) ? personnel.getPersonnelId() : null;
	}

	@RequestMapping(value = "/reviews/{reviewId}/managementDecision", method = RequestMethod.PUT)
	public void managementDecision(@PathVariable("reviewId") String reviewId) {
		commonReviewService.managementDecision(reviewId);
	}

	@RequestMapping(value = "/reviews/{reviewId}/mrbReferral", method = RequestMethod.PUT)
	public void mrbReferral(@PathVariable("reviewId") String reviewId) {
		commonReviewService.mrbReferral(reviewId);
	}

	@RequestMapping(value = "reviews/{reviewId}/forceEscalation", method = RequestMethod.PUT)
	public void forceEscalation(@PathVariable("reviewId") String reviewId) {
		reviewService.forceEscalation(reviewId);
	}

	@RequestMapping(value = "/reviews/{reviewId}/cancel", method = RequestMethod.PUT)
	public void cancelReview(@PathVariable("reviewId") String reviewId, @RequestBody String reasonCode) {
		commonReviewService.cancelReview(reviewId, reasonCode);
	}

	// could be in its own controller, but meh
	@RequestMapping(value = "/batches/{batchId}/cancel", method = RequestMethod.PUT)
	public void cancelBatch(@PathVariable("batchId") String batchId) {
		commonBatchService.cancelBatch(batchId);
	}

	@RequestMapping(value = "/reviews/{reviewLevelId}/indemReviewerSubmit", method = RequestMethod.PUT)
	public void indemReviewerSubmit(
		@PathVariable("reviewLevelId") String reviewLevelId,
		@RequestBody() IndemAcceptDTO indemAcceptDTO
	) {
		reviewService.indemnificationReviewerSubmit(reviewLevelId, indemAcceptDTO.getReviewLocationId(), indemAcceptDTO.getIndemTransferrable());
	}

	@RequestMapping(value = "/reviews/{reviewLevelId}/indemReviewerReject", method = RequestMethod.PUT)
	public void indemReviewerReject(@PathVariable("reviewLevelId") String reviewLevelId) {
		reviewService.indemnificationReviewerReject(reviewLevelId);
	}
	@RequestMapping(value = "/reviews/{reviewId}/levels/{reviewLevelId}/acknowledgeVetting", method = RequestMethod.PUT)
	public void acknowledgeVetting(@PathVariable("reviewId") String reviewId, @PathVariable("reviewLevelId") String reviewLevelId) {
		reviewService.acknowledgeVetting(reviewLevelId);
	}

}
