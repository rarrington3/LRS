package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Lender;

@Repository
public interface LenderRepository extends JpaRepository<Lender, String> {

}
