package gov.hud.lrs.common.repository;

import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.DocumentFile;

public interface DocumentFileRepository extends CrudRepository<DocumentFile, String> {

}
