//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="NOTE_TYPE_REF")
@SuppressWarnings("serial")
public class NoteTypeRef implements java.io.Serializable {
	private String noteTypeId;
	private String noteTypeCd;
	private String description;
	private Set<Note> notes = new HashSet<Note>(0);

	public NoteTypeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="NOTE_TYPE_ID", unique=true, nullable=false, length=36)
	public String getNoteTypeId() {
		return this.noteTypeId;
	}

	public void setNoteTypeId(String noteTypeId) {
		this.noteTypeId = noteTypeId;
	}


    @Column(name="NOTE_TYPE_CD", length=4)
	public String getNoteTypeCd() {
		return this.noteTypeCd;
	}

	public void setNoteTypeCd(String noteTypeCd) {
		this.noteTypeCd = noteTypeCd;
	}


    @Column(name="DESCRIPTION", length=50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="noteTypeRef")
	public Set<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

}
