package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequestFraudTypeRef;

@Repository
public interface LenderSelfReportSelectionRequestFraudTypeRefRepository extends JpaRepository<LenderSelfReportSelectionRequestFraudTypeRef, String> {
}
