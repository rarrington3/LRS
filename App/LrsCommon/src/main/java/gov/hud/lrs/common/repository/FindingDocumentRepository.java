package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Document;
import gov.hud.lrs.common.entity.FindingDocument;

@Repository
public interface FindingDocumentRepository extends CrudRepository<FindingDocument, String> {

	@Query(value = "SELECT FD.* FROM FINDING_DOCUMENT FD WHERE (FD.FINDING_ID = ?1) AND (FD.DOCUMENT_ID = ?2)", nativeQuery = true)
	FindingDocument findByFindingIdAndDocumentId(String findingId, String documentId);
	
	int deleteByDocument(Document document);

}
