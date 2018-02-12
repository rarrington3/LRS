package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.BinderRequestStatusRef;

public interface BinderRequestStatusRefRepository extends JpaRepository<BinderRequestStatusRef, String> {

	@Cacheable("BinderRequestStatusRef")
	public BinderRequestStatusRef findByCode(String code);

}
