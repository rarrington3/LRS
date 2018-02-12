package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.FileDeliveryLocationRef;

@Repository
public interface FileDeliveryLocationRefRepository extends JpaRepository<FileDeliveryLocationRef, String> {

	@Cacheable("FileDeliveryLocationRef")
	FileDeliveryLocationRef findByCode(String code);

}
