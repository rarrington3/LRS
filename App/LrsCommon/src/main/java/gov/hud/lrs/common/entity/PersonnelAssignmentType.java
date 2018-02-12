//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="PERSONNEL_ASSIGNMENT_TYPE", uniqueConstraints = @UniqueConstraint(columnNames={"PERSONNEL_ID", "ASSIGNMENT_TYPE_CD"}))
@SuppressWarnings("serial")
public class PersonnelAssignmentType implements java.io.Serializable {
	private String personnelAssignmentTypeId;
	private AssignmentTypeAdmin assignmentTypeAdmin;
	private Personnel personnel;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Character activeInd;

	public PersonnelAssignmentType() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="PERSONNEL_ASSIGNMENT_TYPE_ID", unique=true, nullable=false, length=36)
	public String getPersonnelAssignmentTypeId() {
		return this.personnelAssignmentTypeId;
	}

	public void setPersonnelAssignmentTypeId(String personnelAssignmentTypeId) {
		this.personnelAssignmentTypeId = personnelAssignmentTypeId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ASSIGNMENT_TYPE_CD", nullable=false)
	public AssignmentTypeAdmin getAssignmentTypeAdmin() {
		return this.assignmentTypeAdmin;
	}

	public void setAssignmentTypeAdmin(AssignmentTypeAdmin assignmentTypeAdmin) {
		this.assignmentTypeAdmin = assignmentTypeAdmin;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PERSONNEL_ID", nullable=false)
	public Personnel getPersonnel() {
		return this.personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
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

    
    @Column(name="ACTIVE_IND", length=1)
	public Character getActiveInd() {
		return this.activeInd;
	}

	public void setActiveInd(Character activeInd) {
		this.activeInd = activeInd;
	}

}
