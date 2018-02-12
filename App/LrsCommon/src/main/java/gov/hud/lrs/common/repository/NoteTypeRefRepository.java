package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.NoteTypeRef;

@Repository
public interface NoteTypeRefRepository extends CrudRepository<NoteTypeRef, String> {
	
	@Cacheable("NoteTypeRef")
	NoteTypeRef findByNoteTypeCd(String noteTypeCd);
}
