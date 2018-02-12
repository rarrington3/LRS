package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.IndemnificationTypeRef;

@Repository
public interface IndemnificationTypeRefRepository extends JpaRepository<IndemnificationTypeRef, String> {

	@Cacheable("IndemnificationTypeRef")
	IndemnificationTypeRef findByCode(String code);

}
