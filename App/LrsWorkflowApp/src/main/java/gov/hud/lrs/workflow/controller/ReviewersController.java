package gov.hud.lrs.workflow.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.ReviewLevelRepository;
import gov.hud.lrs.common.util.Util;
import gov.hud.lrs.workflow.service.AssignmentService;

@RestController
@RequestMapping("/api/v1/reviewers")
public class ReviewersController {

	@Autowired private AssignmentService assignmentService;
	@Autowired private ReviewLevelRepository reviewLevelRepository;

	@RequestMapping(value = "/qualifiedToReview/{reviewId}", method = RequestMethod.GET)
	public List<String> runAssignmentModel(
		@PathVariable("reviewId") String reviewId,
		@RequestParam(name = "reviewLocationId", required = false) String reviewLocationId
	) {
		ReviewLevel reviewLevel = reviewLevelRepository.findActiveOrExceptionByReviewId(reviewId);
		if (reviewLevel == null) {
			throw new NotFoundException("No active or exception review level for reviewId " + reviewId);
		}

		List<Personnel> qualifiedPersonnel;
		if (reviewLocationId == null) {
			qualifiedPersonnel = assignmentService.runAssignmentModelForAllActivePersonnel(reviewLevel);
		} else {
			qualifiedPersonnel = assignmentService.runAssignmentModelForReviewLocation(reviewLevel, reviewLocationId);
		}

		return Util.map(qualifiedPersonnel, p -> p.getPersonnelId());
	}

}
