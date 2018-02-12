package gov.hud.lrs.common.repository;

public interface LoanSelectionCaseSummaryRepositoryCustom {

	String findValueBySelectionId(String dbColumn, String selectionId);

}
