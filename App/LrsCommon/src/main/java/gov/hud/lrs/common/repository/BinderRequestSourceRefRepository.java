package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.BinderRequestSourceRef;

public interface BinderRequestSourceRefRepository extends JpaRepository<BinderRequestSourceRef, String> {

	@Cacheable("BinderRequestSourceRef")
	public BinderRequestSourceRef findByCode(String code);

}
