package gov.hud.lrs.common.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.LenderMonitoringSelectionRequest;

@Repository
public interface LenderMonitoringSelectionRequestRepository extends JpaRepository<LenderMonitoringSelectionRequest, String> {
	
	List<LenderMonitoringSelectionRequest> findByCreatedTsBetween(Timestamp startTime, Timestamp endTime);
	
}
