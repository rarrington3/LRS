package gov.hud.lrs.services.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.CaseActivityDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.CaseDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.CaseRequestDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.CasesResponseDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LiteLenderDTO;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.entity.SelectionReason;
import gov.hud.lrs.common.enumeration.ReviewStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.enumeration.SelectionReasonCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.repository.LoanSelectionRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.ReviewTypeRefRepository;
import gov.hud.lrs.common.repository.SelectionReasonRepository;
import gov.hud.lrs.common.service.CaseUniverseService;
import gov.hud.lrs.common.service.LenderService;
import gov.hud.lrs.common.util.StringFunctionsUtil;
import gov.hud.lrs.services.util.MessageService;

@Controller
public class CasesControllerImpl {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private ReviewRepository reviewRepository;
	@Autowired private LoanSelectionRepository loanSelectionRepository;
	@Autowired private ReviewTypeRefRepository reviewTypeRefRepository;
	@Autowired private SelectionReasonRepository selectionReasonRepository;

	@Autowired private CaseUniverseService caseUniverseService;
	@Autowired private LenderService lenderService;
	@Autowired private MessageService messageService;

	public ResponseEntity<CasesResponseDTO> postCaseSearch(CaseRequestDTO caseRequestDTO, HttpServletRequest request, HttpServletResponse response) {
		CasesResponseDTO caseResponseDTO = new CasesResponseDTO();
		List<CaseDTO> cases = new ArrayList<CaseDTO>();
		List<String> errors = new ArrayList<String>();

		ReviewTypeRef reviewTypeRef = null;
		if (caseRequestDTO.getReviewType() == null) {
			throw new BadRequestException("Review type is required");
		}
		reviewTypeRef = reviewTypeRefRepository.findByReviewTypeCd(caseRequestDTO.getReviewType());
		if (reviewTypeRef == null) {
			throw new BadRequestException("No ReviewTypeRef for reviewTypeId " + caseRequestDTO.getReviewType());
		}

		SelectionReason selectionReason = null;
		if (caseRequestDTO.getSelectionReason() != null) {
			selectionReason = selectionReasonRepository.findByCode(caseRequestDTO.getSelectionReason());
			if (selectionReason == null) {
				throw new BadRequestException("No SelectionReason for selectionReasonId " + caseRequestDTO.getSelectionReason());
			}
		}

		List<String> selectCases = new ArrayList<String>(caseRequestDTO.getCaseNumbers());
		List<String> selectCasesNumbersPaded = selectCases.stream().map(x -> StringFunctionsUtil.caseNumberPad(x)).collect(Collectors.<String> toList());
		if ((selectionReason != null) && (reviewTypeRef != null) && (selectionReason.getCode().equals(SelectionReasonCodes.REVIEW_LOCATION_QC))) {
			List<String> reviewedCaseNumbers = reviewRepository.findReviewedCases(selectCasesNumbersPaded, reviewTypeRef.getReviewTypeId());
			List<String> selectCasesCopy = new ArrayList<String>(selectCasesNumbersPaded);

			if (reviewedCaseNumbers != null && reviewedCaseNumbers.size() > 0) {
				selectCasesCopy.removeAll(reviewedCaseNumbers);
			}
			for (String caseNum : selectCasesCopy) {
				errors.add(messageService.getMessage("error.manualselection.casenotreviewed", caseNum));
				selectCasesNumbersPaded.remove(caseNum);
			}
		}

		// if selection reason is "Post Mortem" and the case number has not been reviewed, then remove it from the
		// list for selection and send an error message for this case number
		if ((selectionReason != null) && (reviewTypeRef != null) && selectionReason.getCode().equals(SelectionReasonCodes.NATIONAL_QC)) {
			for(String caseNumber : caseRequestDTO.getCaseNumbers()) {
				// TODO: this is inefficient; make it a single query for *all* case numbers
				// also its an existence check so we don't need the full record
				List<Review> reviews = reviewRepository.findByCaseNumberAndReviewStatusRefCodeAndLoanSelectionReviewTypeRef(caseNumber, ReviewStatusCodes.COMPLETED, reviewTypeRef);
				if (reviews.isEmpty()) {
					selectCasesNumbersPaded.remove(caseNumber);
					errors.add(messageService.getMessage("error.manualselection.casenotreviewed", caseNumber));
				}
			}
		}

		for (String caseNumber : selectCasesNumbersPaded) {
			LoanSelectionCaseSummary loanSelectionCaseSummary = caseUniverseService.getLoanSelectionCaseSummary(caseNumber);
			// Add error when loan selection case summary is not available
			// or when endorsement date is null for non Test Case selections
			if (loanSelectionCaseSummary == null  || (!caseRequestDTO.getSelectionReason().equals(SelectionReasonCodes.TEST_CASE) && loanSelectionCaseSummary.getEndrsmntDt() == null)) {
				errors.add(messageService.getMessage("error.manualselection.casenotfound", new Object[] { caseNumber }));
				continue;
			}
			
			CaseDTO caseDTO = new CaseDTO();
			caseDTO.setBorrower(loanSelectionCaseSummary.getBorr1Name());
			caseDTO.setCaseNumber(caseNumber);
			caseDTO.setPropertyAddress(StringFunctionsUtil.buildAddress(
				loanSelectionCaseSummary.getPropAddr1(),
				loanSelectionCaseSummary.getPropAddr2(),
				loanSelectionCaseSummary.getPropAddrCity(),
				loanSelectionCaseSummary.getPropAddrSt(),
				loanSelectionCaseSummary.getPropAddrZip()
			));

			String lenderId = null;
			if (reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.SERVICING)) {
				lenderId = loanSelectionCaseSummary.getSrvcrMtgee5A43();
				if (lenderId == null) {
					errors.add(messageService.getMessage("error.manualselection.lenderIdnotfound", new Object[] { caseNumber }));
					continue;
				}
			} else if (reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.UNDERWRITING) || reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.COMPREHENSIVE)) {
				lenderId = loanSelectionCaseSummary.getUndrwrtingMtgee5();
			} else {
				throw new RuntimeException("Unhandled review type: " + reviewTypeRef.getReviewTypeCd());
			}

			Lender lender = lenderService.getLender(lenderId);
			if (lender == null) {
				logger.error("Error looking up lenderId " + lenderId);
				errors.add(messageService.getMessage("error.manualselection.lendernotfound", new Object[] { caseNumber }));
				continue;
			}

			LiteLenderDTO liteLenderDTO = new LiteLenderDTO();
			liteLenderDTO.setLenderId(lender.getLenderId());
			liteLenderDTO.setName(lender.getName());
			caseDTO.setLender(liteLenderDTO);

			List<CaseActivityDTO> caseActivityDTOs = buildCaseActivty(caseNumber, reviewTypeRef);
			caseDTO.setCaseActivity(caseActivityDTOs);

			cases.add(caseDTO);
		}
		caseResponseDTO.setCases(cases);
		caseResponseDTO.setErrors(errors);

		return new ResponseEntity<CasesResponseDTO>(caseResponseDTO, HttpStatus.OK);
	}

	private List<CaseActivityDTO> buildCaseActivty(String caseNumber, ReviewTypeRef reviewTypeRef) {
		List<CaseActivityDTO> caseActivityDTOs = new ArrayList<CaseActivityDTO>();
		List<String> reviewSelectionIds = new ArrayList<String>();
		List<ReviewTypeRef> reviewTypeRefList = new ArrayList<ReviewTypeRef>();
		reviewTypeRefList.add(reviewTypeRef);
		//Include active COMPREHENSIVE reviews also if the selected review type is UNDERWRITING or SERVCING
		if (!reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.COMPREHENSIVE)) {
			ReviewTypeRef comprehensiveReviewTypeRef = reviewTypeRefRepository.findByReviewTypeCd (ReviewTypeCodes.COMPREHENSIVE);
			reviewTypeRefList.add(comprehensiveReviewTypeRef);
		}
		List<Review> reviews = null;
		if (reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.COMPREHENSIVE)) {
			reviews = reviewRepository.findByCaseNumber(caseNumber);
		} else {
			reviews = reviewRepository.findByCaseNumberAndReviewTypeRefIn(caseNumber, reviewTypeRefList);
		}
		for (Review review : reviews) {
			LoanSelection loanSelection = review.getLoanSelection();
			reviewSelectionIds.add(loanSelection.getSelectionId());
			CaseActivityDTO caseActivityDto = new CaseActivityDTO();
			caseActivityDto.setReviewId(review.getReviewId());
			caseActivityDto.setReviewReferenceId(review.getReviewReferenceId());
			caseActivityDto.setReviewStatus(review.getReviewStatusRef().getDescription());
			caseActivityDto.setDistributionDate(loanSelection.getDistributionDt());
			caseActivityDto.setReceivedDate(loanSelection.getReceivedDt());
			caseActivityDto.setRequestedDate(loanSelection.getRequestedDtTm());
			caseActivityDto.setSelectionDate(loanSelection.getSelectionDt());
			caseActivityDto.setSelectionStatus(loanSelection.getLoanSelectionStatusRef().getDescription());

			caseActivityDTOs.add(caseActivityDto);
		}
		List<LoanSelection> loanSelections = null;
		if (reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.COMPREHENSIVE)) {
			loanSelections = loanSelectionRepository.findByCaseNumber(caseNumber);
		} else {
			loanSelections = loanSelectionRepository.findByCaseNumberAndReviewTypeRefIn(caseNumber, reviewTypeRefList);
		}
		for (LoanSelection loanSelection : loanSelections) {
			if (!(reviewSelectionIds.contains(loanSelection.getSelectionId()))) {
				CaseActivityDTO caseActivityDto = new CaseActivityDTO();
				caseActivityDto.setDistributionDate(loanSelection.getDistributionDt());
				caseActivityDto.setReceivedDate(loanSelection.getReceivedDt());
				caseActivityDto.setRequestedDate(loanSelection.getRequestedDtTm());
				caseActivityDto.setSelectionDate(loanSelection.getSelectionDt());
				caseActivityDto.setSelectionStatus(loanSelection.getLoanSelectionStatusRef().getDescription());

				caseActivityDTOs.add(caseActivityDto);
			}
		}

		return caseActivityDTOs;
	}

	public ResponseEntity<CasesResponseDTO> postCaseSelfReportSearch(CaseRequestDTO caseRequestDTO, HttpServletRequest request, HttpServletResponse response) {
		ReviewTypeRef reviewTypeRef = null;
		if (caseRequestDTO.getReviewType() == null) {
			throw new BadRequestException("Review type is required");
		}
		reviewTypeRef = reviewTypeRefRepository.findByReviewTypeCd(caseRequestDTO.getReviewType());
		if (reviewTypeRef == null) {
			throw new BadRequestException("No ReviewTypeRef for reviewTypeId " + caseRequestDTO.getReviewType());
		}

		CasesResponseDTO caseResponseDTO = new CasesResponseDTO();
		List<CaseDTO> caseDTOs = new ArrayList<CaseDTO>();
		List<String> errors = new ArrayList<String>();

		List<String> caseNumbers = caseRequestDTO.getCaseNumbers();
		List<String> casesNumbersPaded = caseNumbers.stream().map(x -> StringFunctionsUtil.caseNumberPad(x)).collect(Collectors.<String> toList());
		for (String caseNumber : casesNumbersPaded) {
			LoanSelectionCaseSummary loanSelectionCaseSummary = caseUniverseService.getLoanSelectionCaseSummary(caseNumber);
			if (loanSelectionCaseSummary == null  || loanSelectionCaseSummary.getEndrsmntDt() == null) {
				errors.add(messageService.getMessage("error.manualselection.casenotfound", new Object[] { caseNumber }));
				continue;
			}

			CaseDTO caseDTO = new CaseDTO();
			caseDTO.setBorrower(loanSelectionCaseSummary.getBorr1Name());
			caseDTO.setCaseNumber(caseNumber);
			caseDTO.setPropertyAddress(
				StringFunctionsUtil.buildAddress(
					loanSelectionCaseSummary.getPropAddr1(),
					loanSelectionCaseSummary.getPropAddr2(),
					loanSelectionCaseSummary.getPropAddrCity(),
					loanSelectionCaseSummary.getPropAddrSt(),
					loanSelectionCaseSummary.getPropAddrZip()
				)
			);

			String lenderId;

			if (reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.SERVICING)) {
				lenderId = loanSelectionCaseSummary.getSrvcrMtgee5A43();
			} else if (reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.UNDERWRITING) || reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.COMPREHENSIVE)) {
				lenderId = loanSelectionCaseSummary.getUndrwrtingMtgee5();
			} else {
				throw new RuntimeException("Unhandled review type: " + reviewTypeRef.getReviewTypeCd());
			}

			Lender lender = lenderService.getLender(lenderId);
			if (lender == null) {
				errors.add(messageService.getMessage("error.manualselection.lendernotfound", new Object[] { caseNumber }));
				continue;
			}

			LiteLenderDTO liteLenderDTO = new LiteLenderDTO();
			liteLenderDTO.setLenderId(lender.getLenderId());
			liteLenderDTO.setName(lender.getName());
			caseDTO.setLender(liteLenderDTO);

			List<CaseActivityDTO> caseActivityDTOs = buildCaseActivty(caseNumber, reviewTypeRef);
			caseDTO.setCaseActivity(caseActivityDTOs);
			caseDTOs.add(caseDTO);
		}

		caseResponseDTO.setCases(caseDTOs);
		caseResponseDTO.setErrors(errors);
		return new ResponseEntity<CasesResponseDTO>(caseResponseDTO, HttpStatus.OK);
	}

}
