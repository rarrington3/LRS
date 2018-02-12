package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;

@Repository
public interface LoanSelectionCaseSummaryRepository extends JpaRepository<LoanSelectionCaseSummary, String>, LoanSelectionCaseSummaryRepositoryCustom {

	@Query(value = "SELECT CASE_NUMBER FROM LOAN_SELECTION_CASE_SUMMARY WHERE SELECTION_REQUEST_ID = ?1", nativeQuery = true)
	List<String> findCaseNumbersBySelectionRequestId(String selectionRequestId);

	List<LoanSelectionCaseSummary> findByCaseNumberOrderByCreatedTsDesc(String caseNumber);
	LoanSelectionCaseSummary findByCaseNumber(String caseNumber);

}
