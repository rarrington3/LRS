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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="PERSONNEL_UNAVAILABILITY")
@SuppressWarnings("serial")
public class PersonnelUnavailability implements java.io.Serializable {
	private String personnelUnavailabilityId;
	private Personnel personnel;
	private Date unavailStartTs;
	private Date unavailEndTs;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public PersonnelUnavailability() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="PERSONNEL_UNAVAILABILITY_ID", unique=true, nullable=false, length=36)
	public String getPersonnelUnavailabilityId() {
		return this.personnelUnavailabilityId;
	}

	public void setPersonnelUnavailabilityId(String personnelUnavailabilityId) {
		this.personnelUnavailabilityId = personnelUnavailabilityId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PERSONNEL_ID", nullable=false)
	public Personnel getPersonnel() {
		return this.personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UNAVAIL_START_TS", nullable=false, length=23)
	public Date getUnavailStartTs() {
		return this.unavailStartTs;
	}

	public void setUnavailStartTs(Date unavailStartTs) {
		this.unavailStartTs = unavailStartTs;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UNAVAIL_END_TS", nullable=false, length=23)
	public Date getUnavailEndTs() {
		return this.unavailEndTs;
	}

	public void setUnavailEndTs(Date unavailEndTs) {
		this.unavailEndTs = unavailEndTs;
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

}
