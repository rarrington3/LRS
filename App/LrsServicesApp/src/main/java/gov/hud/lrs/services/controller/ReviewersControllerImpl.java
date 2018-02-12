package gov.hud.lrs.services.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewerCreationDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewerDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewerLiteDTO;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.services.bizservice.PersonnelService;
import gov.hud.lrs.services.bizservice.WorkflowClient;

@Controller
public class ReviewersControllerImpl {

	@Autowired private PersonnelService personnelService;
	@Autowired private SecurityService securityService;
	@Autowired private PersonnelRepository personnelRepository;

	@Autowired private WorkflowClient workflowClient;

	public ResponseEntity<List<ReviewerDTO>> getReviewers(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<List<ReviewerDTO>>(personnelService.getReviewerDTOs(), HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewerLiteDTO>> getReviewerActive(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<List<ReviewerLiteDTO>>(personnelService.getActiveReviewerLiteDTOs(), HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewerLiteDTO>> getReviewerHq(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<List<ReviewerLiteDTO>>(personnelService.getHqReviewerLiteDTOs(), HttpStatus.OK);
	}

	public ResponseEntity<ReviewerDTO> postReviewer(ReviewerCreationDTO reviewerCreationDTO, HttpServletRequest request, HttpServletResponse response) {
		Personnel reviewer = personnelService.createPersonnel(reviewerCreationDTO);
		ReviewerDTO reviewerDTO = personnelService.convertPersonnelToReviewerDTO(reviewer);
		return new ResponseEntity<ReviewerDTO>(reviewerDTO, HttpStatus.OK);
	}

	public ResponseEntity<ReviewerDTO> putReviewer(String reviewerId, ReviewerDTO reviewerDTO, HttpServletRequest request, HttpServletResponse response) {
		Personnel reviewer = personnelRepository.findOne(reviewerId);
		if (reviewer == null) {
			throw new NotFoundException("No Personnel for personnelId " + reviewerId);
		}
		reviewer = personnelService.updatePersonnel(reviewer, reviewerDTO);
		ReviewerDTO updatedReviewerDTO = personnelService.convertPersonnelToReviewerDTO(reviewer);
		return new ResponseEntity<ReviewerDTO>(updatedReviewerDTO, HttpStatus.OK);
	}

	public ResponseEntity<String> deleteReviewer(String reviewerId, HttpServletRequest request, HttpServletResponse response) {
		personnelService.terminatePersonnel(reviewerId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public ResponseEntity<ReviewerDTO> getReviewerByReviewerId(String reviewerId, HttpServletRequest request, HttpServletResponse response) {
		Personnel personnel = personnelRepository.findOne(reviewerId);
		if (personnel == null) {
			throw new NotFoundException("No Personnel for personnelId " + reviewerId);
		}
		ReviewerDTO reviewerDTO = personnelService.convertPersonnelToReviewerDTO(personnel);
		return new ResponseEntity<ReviewerDTO>(reviewerDTO, HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewerLiteDTO>> getReviewerQualifiedToReviewByReviewId(String reviewId, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<String[]> responseEntity = workflowClient.get(
			"/api/v1/reviewers/qualifiedToReview/" + reviewId,
			String[].class
		);
		List<String> personnelIds = Arrays.asList(responseEntity.getBody());

		List<Personnel> personnelList = personnelRepository.findByPersonnelIdIn(personnelIds);
		List<ReviewerLiteDTO> reviewerLiteDTOs = new ArrayList<ReviewerLiteDTO>();
		for (Personnel reviewer : personnelList) {
			reviewerLiteDTOs.add(personnelService.convertPersonnelToReviewerLiteDTO(reviewer));
		}
		return new ResponseEntity<List<ReviewerLiteDTO>>(reviewerLiteDTOs, HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewerLiteDTO>> getReviewerQualifiedToReviewMyLocation(String reviewId, HttpServletRequest request, HttpServletResponse response) {
		String reviewLocationId = securityService.getPersonnel().getReviewLocation().getReviewLocationId();
		ResponseEntity<String[]> responseEntity = workflowClient.get(
			"/api/v1/reviewers/qualifiedToReview/" + reviewId + "?reviewLocationId=" + reviewLocationId,
			String[].class
		);
		List<String> personnelIds = Arrays.asList(responseEntity.getBody());

		List<Personnel> personnelList = personnelRepository.findByPersonnelIdIn(personnelIds);
		List<ReviewerLiteDTO> reviewerLiteDTOs = new ArrayList<ReviewerLiteDTO>();
		for (Personnel reviewer : personnelList) {
			reviewerLiteDTOs.add(personnelService.convertPersonnelToReviewerLiteDTO(reviewer));
		}
		return new ResponseEntity<List<ReviewerLiteDTO>>(reviewerLiteDTOs, HttpStatus.OK);
	}

}
