package gov.hud.lrs.services.bizservice;

import java.util.Date;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.Document;
import gov.hud.lrs.common.entity.DocumentFile;
import gov.hud.lrs.common.entity.FindingDocument;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.RvwLvlFinding;
import gov.hud.lrs.common.enumeration.DocumentTypeCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.BatchRepository;
import gov.hud.lrs.common.repository.DocumentFileRepository;
import gov.hud.lrs.common.repository.DocumentRepository;
import gov.hud.lrs.common.repository.DocumentTypeRefRepository;
import gov.hud.lrs.common.repository.FindingDocumentRepository;
import gov.hud.lrs.common.repository.LenderRequestRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.RvwLvlFindingRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.DocumentRepositoryService;

@Service
public class DocumentService {

	@Autowired private DocumentRepository documentRepository;
	@Autowired private FindingDocumentRepository findingDocumentRepository;
	@Autowired private DocumentTypeRefRepository documentTypeRefRepository;
	@Autowired private RvwLvlFindingRepository reviewLevelFindingRepository;
	@Autowired private DocumentFileRepository documentFileRepository;
	@Autowired private BatchRepository batchRepository;
	@Autowired private LenderRequestRepository lenderRequestRepository;

	@Autowired private DocumentRepositoryService documentRepositoryService;
	@Autowired private ReviewRepository reviewRepository;

	@Autowired private SecurityService securityService;

	private Tika tika = new Tika();

	private static final List<String> ALLOWED_MEDIA_TYPES = ImmutableList.of(
		"image/tiff",
		"image/jpeg",
		"application/pdf"
	);

	public Document getLenderResponseDocument(String findingId, String documentId) {
		Document document = documentRepository.findOne(documentId);
		if (document == null) {
			throw new NotFoundException("No Document for documentId " + documentId );
		}

		if (findingDocumentRepository.findByFindingIdAndDocumentId(findingId, documentId) == null) {
			throw new ConflictException("Document " + documentId + " is not part of finding " + findingId);
		}

		return document;
	}

	public Document getOperationalReviewDocument(String batchId, String documentId) {
		Batch batch = batchRepository.findOne(batchId);
		if (batch == null) {
			throw new NotFoundException("No Batch for batchId " + batchId);
		}
		if (batch.getRequestOperationalDocumentsInd() != 'Y') {
			throw new ConflictException("Operational documents for Batch " + batchId + " were not requested");
		}

		Document document = documentRepository.findOne(documentId);
		if (document == null) {
			throw new NotFoundException("No Document for documentId " + documentId );
		}
		if (!document.getBatch().getBatchId().equals(batch.getBatchId())) {
			throw new ConflictException("Document " + documentId + " is not part of the operational review for batch " + batchId);
		}

		return document;
	}

	public byte[] getDocumentFile(Document document) {
		DocumentFile documentFile = document.getDocumentFile();
		if (documentFile != null) {
			return documentFile.getDocumentFile();
		} else {
			return documentRepositoryService.getDocumentFile(document);
		}
	}

	@Transactional
	public Document createLenderResponseDocument(String reviewId, String findingId, String fileName, byte[] bytes) {
		RvwLvlFinding rvwLvlFinding = reviewLevelFindingRepository.findOne(findingId);
		if (rvwLvlFinding == null) {
			throw new NotFoundException("No RvwLvlFinding for findingId " + findingId);
		}
 		if (lenderRequestRepository.findActiveByFindingId(findingId) == null) {
 			throw new ConflictException("Finding " + findingId + " isn't part of the active LenderRequest");
 		}

		String mediaType = tika.detect(bytes);
		if (!ALLOWED_MEDIA_TYPES.contains(mediaType)) {
			throw new BadRequestException("Only TIFF, JPG, and PDF files are allowed");
		}

		String userId = securityService.getUserId();
		Date now = new Date();

		DocumentFile documentFile = new DocumentFile();
		documentFile.setDocumentFile(bytes);
		documentFile = documentFileRepository.save(documentFile);

		Document document = new Document();
		document.setDocumentTypeRef(documentTypeRefRepository.findByCode(DocumentTypeCodes.LENDER_RESPONSE));
		document.setDocumentFile(documentFile);
		document.setMediaType(mediaType);
		document.setFileName(fileName);
		document.setReview(rvwLvlFinding.getReviewLevel().getReview());
		document.setReviewLevel(rvwLvlFinding.getReviewLevel());
		document.setCreatedBy(userId);
		document.setCreatedTs(now);
		document.setUpdatedBy(userId);
		document.setUpdatedTs(now);
		document = documentRepository.save(document);

		FindingDocument findingDocument = new FindingDocument();
		findingDocument.setDocument(document);
		findingDocument.setRvwLvlFinding(rvwLvlFinding);
		findingDocument.setCreatedBy(userId);
		findingDocument.setCreatedTs(now);
		findingDocument.setUpdatedBy(userId);
		findingDocument.setUpdatedTs(now);
		findingDocument = findingDocumentRepository.save(findingDocument);

		// Save last action lender name
		Review review = reviewRepository.findOne(reviewId);
		review.setLastLenderName(securityService.getFullName());
		reviewRepository.save(review);
		
		return document;
	}

	@Transactional
	public Document createOperationalReviewDocument(String batchId, String fileName, byte[] bytes) {
		Batch batch = batchRepository.findOne(batchId);
		if (batch == null) {
			throw new NotFoundException("No Batch for batchId " + batchId);
		}
		if (
			(batch.getRequestOperationalDocumentsInd() == null) ||
			batch.getRequestOperationalDocumentsInd().equals('N')
		) {
			throw new ConflictException("Operational documents for Batch " + batchId + " were not requested");
		}
		if (
			(batch.getReceivedOperationalDocumentsInd() != null) &&
			batch.getReceivedOperationalDocumentsInd().equals('Y')
		) {
			throw new ConflictException("Operational documents for Batch " + batchId + " has already been received");
		}

		String mediaType = tika.detect(bytes);
		if (!ALLOWED_MEDIA_TYPES.contains(mediaType)) {
			throw new BadRequestException("Only TIFF, JPG, and PDF files are allowed");
		}

		String userId = securityService.getUserId();
		Date now = new Date();

		DocumentFile documentFile = new DocumentFile();
		documentFile.setDocumentFile(bytes);
		documentFile = documentFileRepository.save(documentFile);

		Document document = new Document();
		document.setDocumentTypeRef(documentTypeRefRepository.findByCode(DocumentTypeCodes.OPERATIONAL));
		document.setFileName(fileName);
		document.setBatch(batch);
		document.setDocumentFile(documentFile);
		document.setMediaType(mediaType);
		document.setCreatedBy(userId);
		document.setCreatedTs(now);
		document.setUpdatedBy(userId);;
		document.setUpdatedTs(now);
		document = documentRepository.save(document);

		return document;
	}

	@Transactional
	public void deleteLenderResponseDocument(String reviewId, String findingId, String documentId) {
		FindingDocument findingDocument = findingDocumentRepository.findByFindingIdAndDocumentId(findingId, documentId);
		if (findingDocument == null) {
			throw new NotFoundException("No Document " + documentId + " for RvwLvlFinding " + findingId);
		}

		Document document = findingDocument.getDocument();
		if (document.getFindingDocuments().size() == 1) {
			// I'm the last finding document, delete the document and associated file
			findingDocumentRepository.deleteByDocument(document);
			DocumentFile documentFile = document.getDocumentFile();
			documentRepository.delete(document);
			documentFileRepository.delete(documentFile);
		} else {
			// there are findings referencing this document, only delete the finding/document link
			findingDocumentRepository.delete(findingDocument);
		}
		
		// Save last action lender name
		Review review = reviewRepository.findOne(reviewId);
		review.setLastLenderName(securityService.getFullName());
		reviewRepository.save(review);		
	}

	@Transactional
	public void deleteOperationalReviewDocument(String documentId) {
		Document document = documentRepository.findOne(documentId);
		if (document == null) {
			throw new NotFoundException("No Document for documentId " + documentId );
		}
		if (!document.getDocumentTypeRef().getCode().equals(DocumentTypeCodes.OPERATIONAL)) {
			throw new ConflictException("Document " + documentId + " is " + document.getDocumentTypeRef().getCode() + ", not " + DocumentTypeCodes.OPERATIONAL);
		}
		Batch batch = document.getBatch();
		if (
			(batch.getRequestOperationalDocumentsInd() == null) ||
			batch.getRequestOperationalDocumentsInd().equals('N')
		) {
			throw new ConflictException("Operational documents for Batch " + batch.getBatchId() + " were not requested");
		}
		if (batch.getReceivedOperationalDocumentsInd() != null && batch.getReceivedOperationalDocumentsInd() != 'N') {
			throw new ConflictException("Operational documents for Batch " + batch.getBatchId() + " has already been received");
		}

		DocumentFile documentFile = document.getDocumentFile();
		documentRepository.delete(document);
		documentFileRepository.delete(documentFile);
	}

}
