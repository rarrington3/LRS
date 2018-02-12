package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.EmailStatusRef;

@Repository
public interface EmailStatusRefRepository extends JpaRepository<EmailStatusRef, String> {
	
	@Cacheable("EmailStatusRef")
	public EmailStatusRef findByCode(String code);
	
}
