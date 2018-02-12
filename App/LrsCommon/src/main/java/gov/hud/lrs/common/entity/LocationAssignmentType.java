//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="LOCATION_ASSIGNMENT_TYPE")
@SuppressWarnings("serial")
public class LocationAssignmentType implements java.io.Serializable {
	private LocationAssignmentTypeId id;
	private AssignmentTypeAdmin assignmentTypeAdmin;
	private ReviewLocation reviewLocation;
	private String createdBy;
	private Date createdTs;
	private String updateBy;
	private Date updateTs;

	public LocationAssignmentType() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="reviewLocationId", column=@Column(name="REVIEW_LOCATION_ID", nullable=false, length=36) ), 
        @AttributeOverride(name="assignmentTypeCd", column=@Column(name="ASSIGNMENT_TYPE_CD", nullable=false, length=16) ) } )
	public LocationAssignmentTypeId getId() {
		return this.id;
	}

	public void setId(LocationAssignmentTypeId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ASSIGNMENT_TYPE_CD", nullable=false, insertable=false, updatable=false)
	public AssignmentTypeAdmin getAssignmentTypeAdmin() {
		return this.assignmentTypeAdmin;
	}

	public void setAssignmentTypeAdmin(AssignmentTypeAdmin assignmentTypeAdmin) {
		this.assignmentTypeAdmin = assignmentTypeAdmin;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LOCATION_ID", nullable=false, insertable=false, updatable=false)
	public ReviewLocation getReviewLocation() {
		return this.reviewLocation;
	}

	public void setReviewLocation(ReviewLocation reviewLocation) {
		this.reviewLocation = reviewLocation;
	}

    
    @Column(name="CREATED_BY", length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

    
    @Column(name="UPDATE_BY", length=6)
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATE_TS", length=23)
	public Date getUpdateTs() {
		return this.updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

}
