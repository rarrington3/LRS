package gov.hud.lrs.common.repository.ext;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.ext.LenderLookup;

public interface LenderLookupRepository extends JpaRepository<LenderLookup, String> {
}
