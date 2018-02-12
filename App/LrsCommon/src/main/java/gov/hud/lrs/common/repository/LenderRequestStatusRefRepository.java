package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.LenderRequestStatusRef;

@Repository
public interface LenderRequestStatusRefRepository extends JpaRepository<LenderRequestStatusRef, String> {

	@Cacheable("LenderRequestStatusRef")
	LenderRequestStatusRef findByCode(String code);

}
