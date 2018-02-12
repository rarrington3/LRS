package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.DocumentTypeRef;

@Repository
public interface DocumentTypeRefRepository extends JpaRepository<DocumentTypeRef, String> {

	@Cacheable("DocumentTypeRef")
	public DocumentTypeRef findByCode(String Code);

}
