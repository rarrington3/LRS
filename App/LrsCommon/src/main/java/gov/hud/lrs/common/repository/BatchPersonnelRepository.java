package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.BatchPersonnel;
import gov.hud.lrs.common.entity.BatchPersonnelId;

public interface BatchPersonnelRepository extends JpaRepository<BatchPersonnel, BatchPersonnelId> {
}
