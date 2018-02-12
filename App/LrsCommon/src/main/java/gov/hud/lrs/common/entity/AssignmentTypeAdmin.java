//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ASSIGNMENT_TYPE_ADMIN")
@SuppressWarnings("serial")
public class AssignmentTypeAdmin implements java.io.Serializable {
	private String assignmentTypeCd;
	private String assignmentTypeCategory;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private String assignmentTypeRefId;
	private Set<LocationAssignmentType> locationAssignmentTypes = new HashSet<LocationAssignmentType>(0);
	private Set<PersonnelAssignmentType> personnelAssignmentTypes = new HashSet<PersonnelAssignmentType>(0);

	public AssignmentTypeAdmin() {
	}

    @Id 
    
    @Column(name="ASSIGNMENT_TYPE_CD", unique=true, nullable=false, length=16)
	public String getAssignmentTypeCd() {
		return this.assignmentTypeCd;
	}

	public void setAssignmentTypeCd(String assignmentTypeCd) {
		this.assignmentTypeCd = assignmentTypeCd;
	}

    
    @Column(name="ASSIGNMENT_TYPE_CATEGORY", length=36)
	public String getAssignmentTypeCategory() {
		return this.assignmentTypeCategory;
	}

	public void setAssignmentTypeCategory(String assignmentTypeCategory) {
		this.assignmentTypeCategory = assignmentTypeCategory;
	}

    
    @Column(name="CREATED_BY", length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    
    @Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

    
    @Column(name="ASSIGNMENT_TYPE_REF_ID", length=36)
	public String getAssignmentTypeRefId() {
		return this.assignmentTypeRefId;
	}

	public void setAssignmentTypeRefId(String assignmentTypeRefId) {
		this.assignmentTypeRefId = assignmentTypeRefId;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="assignmentTypeAdmin")
	public Set<LocationAssignmentType> getLocationAssignmentTypes() {
		return this.locationAssignmentTypes;
	}

	public void setLocationAssignmentTypes(Set<LocationAssignmentType> locationAssignmentTypes) {
		this.locationAssignmentTypes = locationAssignmentTypes;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="assignmentTypeAdmin")
	public Set<PersonnelAssignmentType> getPersonnelAssignmentTypes() {
		return this.personnelAssignmentTypes;
	}

	public void setPersonnelAssignmentTypes(Set<PersonnelAssignmentType> personnelAssignmentTypes) {
		this.personnelAssignmentTypes = personnelAssignmentTypes;
	}

}
