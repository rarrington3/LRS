package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.hud.lrs.common.entity.LoanSelectionPending;

public interface LoanSelectionPendingRepository extends JpaRepository<LoanSelectionPending, String> {

	List<LoanSelectionPending> findBySelectionReasonCode(String selectionReasonCode);

}
