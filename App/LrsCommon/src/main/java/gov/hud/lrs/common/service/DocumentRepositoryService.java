package gov.hud.lrs.common.service;

import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import gov.hud.lrs.common.dto.MuleDocumentGetOutputDTO;
import gov.hud.lrs.common.dto.MuleDocumentPostInputDTO;
import gov.hud.lrs.common.dto.MuleResponseBase;
import gov.hud.lrs.common.entity.Document;
import gov.hud.lrs.common.entity.DocumentFile;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.enumeration.DocumentTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.repository.DocumentFileRepository;
import gov.hud.lrs.common.util.StringFunctionsUtil;

@Service
public class DocumentRepositoryService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${lrs.mule.document.uri}") private String muleDocumentUri;
	@Value("${lrs.mule.document.rootUri}") private String muleDocumentRootUri;
	@Value("${lrs.mule.document.useOAuth}") private boolean muleDocumentUseOAuth;
	@Autowired private DocumentFileRepository documentFileRepository;
	@Autowired private MuleClient muleClient;
	
	private Tika tika = new Tika();

	public byte[] getDocumentFile(Document document) {
		Map<String, String> requestParameters = new HashMap<String, String>();
		String[] fileNameTokens = document.getFileName().split("\\.(?=[^\\.]+$)");
		String fileName = null;
		String documentName = null;

		try {
			fileName = URLEncoder.encode(fileNameTokens[0], "UTF-8").replace("+", "%20");
		} catch(Exception e) {
			throw new RuntimeException("Unable to encode filename: " + fileNameTokens[0]);
		}

		// First attempt use document ID
		documentName = document.getDocumentId() + "-" + fileName;

		requestParameters.put("documentName", documentName);
		requestParameters.put("documentKey", getDocumentKey(document));
		requestParameters.put("documentType", getDocumentType(document));

		ResponseEntity<MuleDocumentGetOutputDTO> responseEntity = muleClient.get(
			muleDocumentRootUri + muleDocumentUri,
			requestParameters,
			MuleDocumentGetOutputDTO.class,
			muleDocumentUseOAuth
		);
		MuleDocumentGetOutputDTO muleDocumentGetOutputDTO = responseEntity.getBody();
		String statusCode = "";
		String statusMessage = "";
		if (muleDocumentGetOutputDTO != null) {
			statusCode = muleDocumentGetOutputDTO.getStatusCode();
			statusMessage = muleDocumentGetOutputDTO.getStatusMessage();
		}

		logger.debug("Mule get document for documentId " + document.getDocumentId() + " (Document name: " + documentName + ") status code " + statusCode + ": " + statusMessage);

		if (!statusCode.equals("0")) {
			if (statusCode.equals("403")) { 
				//Retry one more time for legacy documents without the guid
				documentName = fileName;
				requestParameters.put("documentName", documentName);
				responseEntity = muleClient.get(
					muleDocumentRootUri + muleDocumentUri,
					requestParameters,
					MuleDocumentGetOutputDTO.class,
					muleDocumentUseOAuth
				);
				muleDocumentGetOutputDTO = responseEntity.getBody();
				if (muleDocumentGetOutputDTO != null) {
					statusCode = muleDocumentGetOutputDTO.getStatusCode();
					statusMessage = muleDocumentGetOutputDTO.getStatusMessage();
				}

				logger.debug("Mule retry get document for documentId " + document.getDocumentId() + " (Document name: " + documentName + ") status code " + statusCode + ": " + statusMessage);

				if (!statusCode.equals("0")) {
					throw new RuntimeException("Mule retry get document for documentId " + document.getDocumentId() + " (Document name: " + documentName + ") failed with status code " + statusCode + ": " + statusMessage);
				}
			}
			else {
				throw new RuntimeException("Mule get document for documentId " + document.getDocumentId() + " (Document name: " + documentName + ") failed with status code " + statusCode + ": " + statusMessage);
			}
		}

		return Base64.getDecoder().decode(muleDocumentGetOutputDTO.getDocument());
	}

	public void uploadDocumentFile(Document document) {
		if (document.getDocumentFile() != null) {
			// If document has not been submitted to Alfresco before
			uploadDocumentFile(document, document.getDocumentFile().getDocumentFile());
		}
	}

	public boolean uploadDocumentFile(Document document, byte[] documentFile) {
		MuleDocumentPostInputDTO muleDocumentPostInputDTO = new MuleDocumentPostInputDTO();
		muleDocumentPostInputDTO.setDocumentKey(getDocumentKey(document));
		muleDocumentPostInputDTO.setDocumentType(getDocumentType(document));
		String contentType = tika.detect(documentFile);
		if (contentType == null || !(contentType.contains("/") && contentType.lastIndexOf("/") != 0)) {
			throw new BadRequestException("Unable to detect file type for Document " + document.getDocumentId());
		}
		String fileExtension = contentType.substring(contentType.lastIndexOf("/") + 1);
		String[] fileNameTokens = document.getFileName().split("\\.(?=[^\\.]+$)");
		String fileName = fileNameTokens[0];
		String documentName = document.getDocumentId() + "-" + fileName;
		muleDocumentPostInputDTO.setDocumentName(documentName);
		muleDocumentPostInputDTO.setDocumentMimeType(fileExtension.toUpperCase().replace("JPEG", "JPG").replace("TIFF", "TIF"));
		muleDocumentPostInputDTO.setDocument(Base64.getEncoder().encodeToString(documentFile));

		ResponseEntity<MuleResponseBase> responseEntity = muleClient.post(
			muleDocumentRootUri + muleDocumentUri,
			muleDocumentPostInputDTO,
			MuleResponseBase.class,
			muleDocumentUseOAuth
		);

		MuleResponseBase muleResponseBase = responseEntity.getBody();
		String statusCode = "";
		String statusMessage = "";
		if (muleResponseBase != null) {
			statusCode = muleResponseBase.getStatusCode();
			statusMessage = muleResponseBase.getStatusMessage();
		}

		logger.debug("Mule post document for documentId " + document.getDocumentId() + " (Document name: " + documentName + ") with status code " + statusCode + ": " + statusMessage);

		if (!statusCode.equals("0")) {
			throw new RuntimeException("Mule post document for documentId " + document.getDocumentId() + " (Document name: " + documentName + ") failed with status code " + statusCode + ": " + statusMessage);
		}

		// Delete files in LRS DB after successful submission to Alfresco
		DocumentFile docFile = document.getDocumentFile();
		document.setDocumentFile(null);
		documentFileRepository.delete(docFile);
		
		return true;
	}

	private String getDocumentType(Document document) {
		String documentTypeCode = document.getDocumentTypeRef().getCode();
		if (documentTypeCode.equals(DocumentTypeCodes.INDEMNIFICATION)) {
			return "Indemnification";
		} else if (documentTypeCode.equals(DocumentTypeCodes.LENDER_RESPONSE)) {
			return "Mitigating";
		} else if (documentTypeCode.equals(DocumentTypeCodes.OPERATIONAL)) {
			return "Operational";
		} else {
			throw new RuntimeException("Unhandled document type: " + documentTypeCode + " for Document " + document.getDocumentId());
		}
	}

	private String getDocumentKey(Document document) {
		String documentTypeCode = document.getDocumentTypeRef().getCode();
		if (documentTypeCode.equals(DocumentTypeCodes.INDEMNIFICATION)) {
			return StringFunctionsUtil.caseNumberTrim(document.getReview().getCaseNumber());
		} else if (documentTypeCode.equals(DocumentTypeCodes.LENDER_RESPONSE)) {
			Review review = document.getReview();
			if (!review.getReviewTypeRef().getReviewTypeCd().equals(ReviewTypeCodes.OPERATIONAL)) {
				return StringFunctionsUtil.caseNumberTrim(review.getCaseNumber());
			} else {
				// Padded lender id with "00000"s. The lender response document for an operational review
				// is still of "mitigation" document type and requires a 10 digit document key
				return review.getBatch().getLender().getLenderId() + "00000";
			}
		} else if (documentTypeCode.equals(DocumentTypeCodes.OPERATIONAL)) {
			return document.getBatch().getLender().getLenderId();
		} else {
			throw new RuntimeException("Unhandled document type: " + documentTypeCode + " for Document " + document.getDocumentId());
		}
	}

}
