package gov.hud.lrs.common.service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import gov.hud.lrs.common.entity.Document;
import gov.hud.lrs.common.entity.DocumentFile;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.enumeration.DocumentTypeCodes;
import gov.hud.lrs.common.enumeration.IndemnificationTypeCodes;
import gov.hud.lrs.common.enumeration.ProductTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.DocumentRepository;
import gov.hud.lrs.common.repository.ReviewLevelRepository;
import gov.hud.lrs.common.repository.IndemnificationRepository;
import gov.hud.lrs.common.entity.Indemnification;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.repository.DocumentFileRepository;
import gov.hud.lrs.common.repository.DocumentTypeRefRepository;
import gov.hud.lrs.common.security.FhacUser;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class DocumentTemplateService {

	private static final String INDEMNIFICATION_NUMBER_PLACEHOLDER = "[XXXXXX]";
	private static final String REVIEW_ID_PLACEHOLDER = "[XXXXX-XXXX-XXXXXX]";
	private static final String LENDER_ID_PLACEHOLDER = "XX-INT5";
	private static final String LENDER_INSTITUTION_PLACEHOLDER = "XX-INST";
	private static final String LENDER_SIGNATURE_PLACEHOLDER = "XX-LENDER";
	private static final String LENDER_SIGNED_DATE_PLACEHOLDER = "LENDER-DATE";
	private static final String FHA_SIGNATURE_PLACEHOLDER = "XX-FHA";
	private static final String FHA_DIVISION_PLACEHOLDER = "[Division Name (PUD/QAD)]";
	private static final String FHA_SIGNED_DATE_PLACEHOLDER = "FHA-DATE";
	private static final String CASE_NUMBER_PLACEHOLDER = "[Case Number]";
	private static final String ENDORSEMENT_DATE_PLACEHOLDER = "[Endorsement Date]";
	private static final String INSTITUTION_NAME = "[Institution Name]";
	private static final String INSTITUTION_ID = "[5-Digit Institution ID]";



	@Autowired private DocumentRepository documentRepository;
	@Autowired private DocumentFileRepository documentFileRepository;
	@Autowired private DocumentTypeRefRepository documentTypeRefRepository;
	@Autowired private IndemnificationRepository indemnificationRepository;
	@Autowired private ReviewLevelRepository reviewLevelRepository;

	@Autowired private CommonReviewService commonReviewService;
	@Autowired private SecurityService securityService;

	@Autowired private ResourceLoader resourceLoader;

	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public byte[] generatePrePopulatedIndemnificationPdf(String reviewLevelId) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		if (reviewLevelId == null) {
			throw new NotFoundException("No ReviewLevelId for reviewLevelIdId " + reviewLevelId);
		}

		Indemnification indemnification = indemnificationRepository.findByReviewLevel(reviewLevel);
		if (indemnification == null) {
			indemnification = new Indemnification();
			indemnification.setReviewLevel(reviewLevel);
			indemnification.setIndemnificationAgreementNumber(indemnificationRepository.findNextIndemnificationAgreementNumber());
		}

		boolean forcedIndemnification = reviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.FORCE_INDEMNIFICATION) ? true : false;
		return generateIndemnificationPdf(indemnification, forcedIndemnification);  // prePopulated is call by both standard indem and forced indem
	}

	public byte[] generatePreSignedIndemnificationPdf(String reviewLevelId, boolean lender, boolean reviewer) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		if (reviewLevelId == null) {
			throw new NotFoundException("No ReviewLevelId for reviewLevelIdId " + reviewLevelId);
		}

		Indemnification indemnification = indemnificationRepository.findByReviewLevel(reviewLevel);
		if (indemnification == null) {
			indemnification = new Indemnification();
			indemnification.setReviewLevel(reviewLevel);
			indemnification.setIndemnificationAgreementNumber(indemnificationRepository.findNextIndemnificationAgreementNumber());
		}

		FhacUser fhacUser = securityService.getFhacUser();
		if (reviewer) {
			if (indemnification.getFhaSignerName() == null) {
				Personnel personnel = securityService.getPersonnel();
				indemnification.setFhaSignerName(personnel.getFirstName() +
					(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() + " " : " ") +
					personnel.getLastName());
				indemnification.setFhaSignedDate(new Date());
				indemnification.setFhaSignerDivision(commonReviewService.getReviewerDivision());
			}
		}
		if (lender && indemnification.getLenderSignerName() == null) {
			indemnification.setLenderSignerUserId(fhacUser.getUserId());
			indemnification.setLenderSignerName(fhacUser.getFirstName() + " " + fhacUser.getLastName());
			indemnification.setLenderSignedDate(new Date());
		}

		boolean forcedIndemnification = false;
		if (reviewer && !lender) {
			forcedIndemnification = true;
		}
		return generateIndemnificationPdf(indemnification, forcedIndemnification);
	}

	public byte[] generateIndemnificationPdf(Indemnification indemnification, boolean forcedIndemnification) {
		ReviewLevel reviewLevel = indemnification.getReviewLevel();
		Review review = reviewLevel.getReview();
		String reviewTypeCode = review.getReviewTypeRef().getReviewTypeCd();
		String productTypeCode = review.getProductTypeRef().getProductTypeCd();
		String indemnificationTypeCode = reviewLevel.getIndemnificationTypeRef().getCode();
		String templateFileName = "templates/LRSIndemTemplate_";
		if (indemnificationTypeCode.equals(IndemnificationTypeCodes.FIVE_YEAR)) {
			if (productTypeCode.equals(ProductTypeCodes.HECM)) {
				if (forcedIndemnification) {
					templateFileName = templateFileName + "Force5UWHECM.docx";
				} else {
					templateFileName = templateFileName + "S5UWHECM.docx";
				}
			} else {
				if (forcedIndemnification) {
					templateFileName = templateFileName + "Force5UWNonHECM.docx";
				} else {
					templateFileName = templateFileName + "S5UNonHecm.docx";
				}
			}
		} else if (indemnificationTypeCode.equals(IndemnificationTypeCodes.LIFE_OF_LOAN)) {
			if (reviewTypeCode.equals(ReviewTypeCodes.SERVICING) && !forcedIndemnification) {
				templateFileName = templateFileName + "SLoLServicingAllLoanTypes.docx";
			} else if (reviewTypeCode.equals(ReviewTypeCodes.UNDERWRITING)) {
				if (productTypeCode.equals(ProductTypeCodes.HECM)) {
					if (forcedIndemnification) {
						templateFileName = templateFileName + "ForceLoLUWHECM.docx";
					} else {
						templateFileName = templateFileName + "SLoLUWHECM.docx";
					}
				} else {
					if (forcedIndemnification) {
						templateFileName = templateFileName + "ForceLoLUWNonHECM.docx";
					} else {
						templateFileName = templateFileName + "SLoLUWNonHECM.docx";
					}
				}
			} else {
				throw new RuntimeException("Unhandled review type: " + reviewLevel.getIndemnificationTypeRef().getCode() + " for review " + review.getReviewId());
			}
		} else {
			throw new RuntimeException("Unhandled indemnification type : " + reviewLevel.getIndemnificationTypeRef().getCode() + " for review " + review.getReviewId());
		}
		Map<String, String> placeholderValues = new HashMap<String, String>();
		Map<String, String> tablePlaceholderValues = new HashMap<String, String>();

		//place holder for paragraphs
		placeholderValues.put(INDEMNIFICATION_NUMBER_PLACEHOLDER, String.valueOf(indemnification.getIndemnificationAgreementNumber()));
		placeholderValues.put(REVIEW_ID_PLACEHOLDER, review.getReviewReferenceId());
		Lender lender = review.getLender();
		// lender will only occur for operational reviews, which cannot be indemnified, so we *want* a NPE here if that happens
		// it would indicate a bug in our business logic
		placeholderValues.put(LENDER_ID_PLACEHOLDER, lender.getLenderId());
		placeholderValues.put(LENDER_INSTITUTION_PLACEHOLDER, lender.getName());
		placeholderValues.put(INSTITUTION_ID, lender.getLenderId());
		placeholderValues.put(INSTITUTION_NAME, lender.getName());

		//place holder for table (signature, appendix etc)
		tablePlaceholderValues.put(CASE_NUMBER_PLACEHOLDER, review.getCaseNumber());
		Date endorsementDate = review.getLoanSelection().getLoanSelectionCaseSummary().getEndrsmntDt();
		if (endorsementDate != null) {
			tablePlaceholderValues.put(ENDORSEMENT_DATE_PLACEHOLDER, dateFormat.format(endorsementDate));
		}
		tablePlaceholderValues.put(LENDER_SIGNATURE_PLACEHOLDER, (indemnification.getLenderSignerName() != null) ? indemnification.getLenderSignerName() : "");
		tablePlaceholderValues.put(LENDER_SIGNED_DATE_PLACEHOLDER, (indemnification.getLenderSignedDate() != null) ? dateFormat.format(indemnification.getLenderSignedDate()) : "");
		tablePlaceholderValues.put(FHA_SIGNATURE_PLACEHOLDER, (indemnification.getFhaSignerName() != null) ? indemnification.getFhaSignerName() : "");
		tablePlaceholderValues.put(FHA_DIVISION_PLACEHOLDER, (indemnification.getFhaSignerDivision() != null) ? indemnification.getFhaSignerDivision() : "");
		tablePlaceholderValues.put(FHA_SIGNED_DATE_PLACEHOLDER, (indemnification.getFhaSignedDate() != null) ? dateFormat.format(indemnification.getFhaSignedDate()) : "");

		InputStream inputStream = null;
		try {
			inputStream = resourceLoader.getResource("classpath:" + templateFileName).getInputStream();
			XWPFDocument document = new XWPFDocument(inputStream);

			//replace place holders in paragraphs
			for(XWPFParagraph paragraph : document.getParagraphs()) {
				for (int runIndex = 0; runIndex < paragraph.getRuns().size(); runIndex++) {
					XWPFRun run = paragraph.getRuns().get(runIndex);
					String runText = run.getText(0);
					if (runText != null) {
						for (Map.Entry<String, String> placeholderValue : placeholderValues.entrySet()) {
							String placeholder = placeholderValue.getKey();
							String value = placeholderValue.getValue();
							runText = runText.replace(placeholder, value);
						}
						//logger.debug("Run\n\t" + run.getText(0) + "\n\t" + runText);
						run.setText(runText, 0);
					}
				}
			}

			//replace place holders in tables
			for (XWPFTable tbl : document.getTables()) {
				for (XWPFTableRow row : tbl.getRows()) {
					for (XWPFTableCell cell : row.getTableCells()) {
						for (XWPFParagraph p : cell.getParagraphs()) {
							for (XWPFRun r : p.getRuns()) {
								String text = r.getText(0);
								if (text != null) {
									for (Map.Entry<String, String> tablePlaceholderValue : tablePlaceholderValues.entrySet()) {
										String placeholder = tablePlaceholderValue.getKey();
										String value = tablePlaceholderValue.getValue();
										text = text.replace(placeholder, value);
									}
									//logger.debug("Run\n\t" + run.getText(0) + "\n\t" + runText);
									r.setText(text, 0);
								}
							}
						}
					}
				}
			}

			PdfOptions options = PdfOptions.create();

			OutputStream out = new ByteArrayOutputStream();
			PdfConverter.getInstance().convert(document, out, options);
			byte[] bytes = ((ByteArrayOutputStream)out).toByteArray();
            return bytes;

		} catch (Exception e) {
			throw new RuntimeException("Exception populating indem template", e);
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public Document createIndemnificationDocument(ReviewLevel reviewLevel, byte[] pdfBytes) {
		DocumentFile documentFile = new DocumentFile();
		documentFile.setDocumentFile(pdfBytes);
		documentFile = documentFileRepository.save(documentFile);

		String userId = securityService.getUserId();
		Date now = new Date();

		Document document = new Document();
		document.setReviewLevel(reviewLevel);
		document.setDocumentFile(documentFile);
		document.setDocumentTypeRef(documentTypeRefRepository.findByCode(DocumentTypeCodes.INDEMNIFICATION));
		document.setFileName(reviewLevel.getReview().getCaseNumber() + "_" + reviewLevel.getReviewLevelId() + "_indemnification.pdf");
		document.setCreatedBy(userId);
		document.setCreatedTs(now);
		document.setUpdatedBy(userId);
		document.setUpdatedTs(now);
		document = documentRepository.save(document);

		return document;
	}

}
