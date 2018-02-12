package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Note;
import gov.hud.lrs.common.entity.NoteTypeRef;

@Repository
public interface NoteRepository extends CrudRepository<Note, String> {
	
	List<Note> findByNoteTypeRefAndReviewId(NoteTypeRef noteTypeRe, String reviewId);
	
	Note findTopByNoteTypeRefAndFindingIdOrderByUpdatedTsDesc(NoteTypeRef noteTypeRe, String findingId);
}
