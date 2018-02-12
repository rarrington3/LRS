package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.Document;
import gov.hud.lrs.common.entity.DocumentFile;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

	@Query(value = "SELECT D.* FROM DOCUMENT D INNER JOIN FINDING_DOCUMENT FD ON FD.DOCUMENT_ID = D.DOCUMENT_ID WHERE FD.FINDING_ID = ?1", nativeQuery = true)
	List<Document> findByFindingId(String findingId);
	
	long deleteByDocumentFile(DocumentFile documentFile);
	
	List<Document> findByBatch(Batch batch);

	Document findByReviewLevelReviewLevelId(String reviewLevelId);
	
	@Query(
		value =
			"SELECT DISTINCT D.* FROM DOCUMENT D " +
			"INNER JOIN FINDING_DOCUMENT FD ON (FD.DOCUMENT_ID = D.DOCUMENT_ID) " +
			"INNER JOIN RVW_LVL_FINDING F ON (F.FINDING_ID = FD.FINDING_ID) " +
			"WHERE (F.REVIEW_LEVEL_ID = ?1) ",
		nativeQuery = true
	)
	List<Document> findByReviewLevelId(String reviewLevelId);

}
