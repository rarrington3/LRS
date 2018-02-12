package gov.hud.lrs.common.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import gov.hud.lrs.common.dto.MuleIndemnificationDocumentDTO;
import gov.hud.lrs.common.dto.MuleIndemnificationFileDTO;
import gov.hud.lrs.common.dto.MuleIndemnificationInputDTO;
import gov.hud.lrs.common.dto.MuleIndemnificationOutputDTO;
import gov.hud.lrs.common.entity.Indemnification;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.enumeration.IndemnificationTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.exception.MessageOnlyException;
import gov.hud.lrs.common.repository.IndemnificationRepository;
import gov.hud.lrs.common.security.FhacUser;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.util.StringFunctionsUtil;

@Service
public class IndemnificationService {
	private Logger logger = LoggerFactory.getLogger(IndemnificationService.class);

	@Value("${lrs.mule.indemnification.rootUri}") private String muleIndemnificationRootUri;
	@Value("${lrs.mule.indemnification.uri}") private String muleIndemnificationUri;
	@Value("${lrs.mule.indemnification.useOAuth}") private boolean muleIndemnificationsUseOAuth;

	@Autowired private IndemnificationRepository indemnificationRepository;

	@Autowired private CommonReviewService commonReviewService;
	@Autowired private SecurityService securityService;

	@Autowired private MuleClient muleClient;

	public Indemnification createIndemnification(ReviewLevel reviewLevel) {
		Date now = new Date();

		Indemnification indemnification = new Indemnification();
		indemnification.setReviewLevel(reviewLevel);

		indemnification.setTransferrableInd('Y');
		indemnification.setIndemnificationAgreementNumber(indemnificationRepository.findNextIndemnificationAgreementNumber());
		indemnification.setCreatedBy(securityService.getUserId());
		indemnification.setCreatedTs(now);
		indemnification = indemnificationRepository.save(indemnification);

		logger.debug("Indemnification record created for review level " + reviewLevel.getReviewLevelId());
		return indemnification;
	}

	public void lenderSignIndemnification(Indemnification indemnification) {
		FhacUser fhacUser = securityService.getFhacUser();
		Date now = new Date();
		indemnification.setLenderSignerUserId(fhacUser.getUserId());
		indemnification.setLenderSignerName(fhacUser.getFirstName() + " " + fhacUser.getLastName());
		indemnification.setLenderSignedDate(now);
		indemnification.setUpdatedBy(securityService.getUserId());
		indemnification.setUpdatedTs(now);
		indemnification = indemnificationRepository.save(indemnification);

		logger.debug("User " + indemnification.getLenderSignerUserId() + " for lender " + indemnification.getReviewLevel().getReview().getLender().getLenderId() + " signed indemnification");
		logger.debug("\tIndemnification: " + indemnification.getIndemnificationId() +
			"; Agreement number: " + indemnification.getIndemnificationAgreementNumber() +
			"; Review level: " + indemnification.getReviewLevel().getReviewLevelId());
	}

	public void reviewerSignIndemnification(Indemnification indemnification, ReviewLocation reviewLocation, boolean indemnificationTransferrable) {
		Date now = new Date();
		indemnification.setTransferrableInd(indemnificationTransferrable ? 'Y' : 'N');
		indemnification.setReviewLocation(reviewLocation);
		Personnel personnel = securityService.getPersonnel();
		indemnification.setFhaSignerHudId(securityService.getUserId());
		indemnification.setFhaSignerDivision(commonReviewService.getReviewerDivision());
		indemnification.setFhaSignerName(personnel.getFirstName() +
			(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() + " " : " ") +
			personnel.getLastName());
		indemnification.setFhaSignedDate(now);
		ReviewLevel reviewLevel = indemnification.getReviewLevel();
		if (reviewLevel.getReview().getLoanSelection() == null) {
			throw new RuntimeException("LoanSelection is null for review: " + reviewLevel.getReview().getReviewId());
		}
		LoanSelectionCaseSummary loanSelectionCaseSummary = reviewLevel.getReview().getLoanSelection().getLoanSelectionCaseSummary();
		Date indemnificationStartDate = commonReviewService.getIndemnificationStartDate(reviewLevel.getReviewLevelId());
		Date expirationDate = commonReviewService.getIndemnificationExpirationDate(reviewLevel.getReviewLevelId(), indemnificationStartDate);

		indemnification.setStartDate(indemnificationStartDate);
		indemnification.setExpirationDate(expirationDate);

		indemnification.setUpdatedBy(securityService.getUserId());
		indemnification.setUpdatedTs(now);

		logger.debug("User " + indemnification.getFhaSignerHudId() + " signed indemnification");
		logger.debug("\tIndemnification: " + indemnification.getIndemnificationId() +
			"; Lender: " + indemnification.getReviewLevel().getReview().getLender().getLenderId() +
			"; Case number: " + indemnification.getReviewLevel().getReview().getCaseNumber() +
			"; Agreement number: " + indemnification.getIndemnificationAgreementNumber() +
			"; Indem Type: " + reviewLevel.getIndemnificationTypeRef().getCode() +
			"; Loan Type: " + loanSelectionCaseSummary.getLoanType() +
			"; Start date: " + indemnification.getStartDate() +
			"; Expiration date: " + indemnification.getExpirationDate() +
			"; Review level: " + indemnification.getReviewLevel().getReviewLevelId());
	}

	public void sendIndemnificationToHud(Indemnification indemnification, byte[] indemnificationDocumentBytes) {
		ReviewLevel reviewLevel = indemnification.getReviewLevel();
		Review review = reviewLevel.getReview();

		MuleIndemnificationFileDTO muleIndemFileDTO = new MuleIndemnificationFileDTO();
		muleIndemFileDTO.setName(reviewLevel.getReview().getCaseNumber().trim() + "_" + reviewLevel.getReviewLevelId() + "_indemnification");
		muleIndemFileDTO.setMimeType("PDF");
		muleIndemFileDTO.setDocument(Base64.getEncoder().encodeToString(indemnificationDocumentBytes));

		MuleIndemnificationDocumentDTO muleIndemDocumentDTO = new MuleIndemnificationDocumentDTO();

		MuleIndemnificationFileDTO[] files = {muleIndemFileDTO};
		muleIndemDocumentDTO.setFile(files);

		MuleIndemnificationInputDTO muleIndemInputDTO = new MuleIndemnificationInputDTO();
		muleIndemInputDTO.setDocuments(muleIndemDocumentDTO);
		muleIndemInputDTO.setCaseNumber(StringFunctionsUtil.caseNumberTrim(review.getCaseNumber()));
		muleIndemInputDTO.setMortgagee(review.getLender().getLenderId());
		String reviewTypeCode = review.getReviewTypeRef().getReviewTypeCd();
		String sponsorId = review.getLoanSelection().getLoanSelectionCaseSummary().getSpnsrMtgee10Id();
		if (reviewTypeCode.equals(ReviewTypeCodes.SERVICING)) {
			muleIndemInputDTO.setBillTo("Servicer");
		} else if (reviewTypeCode.equals(ReviewTypeCodes.UNDERWRITING)) {
			if (sponsorId != null && !sponsorId.equals("0000000000")) {
				muleIndemInputDTO.setBillTo("Sponsor");
			} else {
				muleIndemInputDTO.setBillTo("Originator");
			}
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		muleIndemInputDTO.setAgreementDate(dateFormat.format(new Date()));
		//term can only be 60 or 360, otherwise mule will return an error
		Integer term = (reviewLevel.getIndemnificationTypeRef().getCode().equals(IndemnificationTypeCodes.FIVE_YEAR)) ?
			60 :
			360
		;
		muleIndemInputDTO.setTerm(term);

		String indemnificationStart = commonReviewService.getIndemnificationStart(reviewLevel.getReviewLevelId());

		muleIndemInputDTO.setExpirationDate(dateFormat.format(indemnification.getExpirationDate()));

		muleIndemInputDTO.setStartDate(indemnificationStart);
		String lenderInsuredInd = review.getLoanSelection().getLoanSelectionCaseSummary().getMiscLndrInsrncInd();
		muleIndemInputDTO.setLenderEndorsed((lenderInsuredInd != null) && (lenderInsuredInd.equals("Y")));
		muleIndemInputDTO.setLenderExecutedAgreement(true);
		muleIndemInputDTO.setIndemnificationAgreementTransferable(indemnification.getTransferrableInd() == 'Y');
		muleIndemInputDTO.setQadLrsFileNumber(String.valueOf(indemnification.getIndemnificationAgreementNumber()));
		muleIndemInputDTO.setDocketNumber("");
		muleIndemInputDTO.setUser(securityService.getUserId());

		ResponseEntity<MuleIndemnificationOutputDTO> responseEntity = null;
		responseEntity = muleClient.post(
			muleIndemnificationRootUri + muleIndemnificationUri,
			muleIndemInputDTO,
			MuleIndemnificationOutputDTO.class,
			muleIndemnificationsUseOAuth
		);

		MuleIndemnificationOutputDTO muleIndemnificationOutputDTO = responseEntity.getBody();
		if (!muleIndemnificationOutputDTO.getStatusCode().equals("0")) {
			throw new MessageOnlyException("Indemnification failed with status " + muleIndemnificationOutputDTO.getStatusCode() + ": " + muleIndemnificationOutputDTO.getStatusMessage());
		} else {
			logger.debug("Indemnification successfully sent for");
			logger.debug("\tIndemnification: " + indemnification.getIndemnificationId() +
				"; Lender: " + indemnification.getReviewLevel().getReview().getLender().getLenderId() +
				"; Case number: " + indemnification.getReviewLevel().getReview().getCaseNumber() +
				"; Agreement number: " + indemnification.getIndemnificationAgreementNumber() +
				"; Start date: " + indemnification.getStartDate() +
				"; Expiration date: " + indemnification.getExpirationDate() +
				"; Review level: " + indemnification.getReviewLevel().getReviewLevelId());
		}
	}

}
