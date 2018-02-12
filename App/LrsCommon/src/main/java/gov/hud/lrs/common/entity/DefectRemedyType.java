//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="DEFECT_REMEDY_TYPE")
@SuppressWarnings("serial")
public class DefectRemedyType implements java.io.Serializable {
	private String defectRemedyTypeId;
	private Defect defect;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;
	private String description;
	private Integer orderNumber;
	private String code;
	private Set<RvwLvlFinding> rvwLvlFindings = new HashSet<RvwLvlFinding>(0);

	public DefectRemedyType() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="DEFECT_REMEDY_TYPE_ID", unique=true, nullable=false, length=36)
	public String getDefectRemedyTypeId() {
		return this.defectRemedyTypeId;
	}

	public void setDefectRemedyTypeId(String defectRemedyTypeId) {
		this.defectRemedyTypeId = defectRemedyTypeId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_ID", nullable=false)
	public Defect getDefect() {
		return this.defect;
	}

	public void setDefect(Defect defect) {
		this.defect = defect;
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


    @Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}


    @Column(name="DESCRIPTION", length=30)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    @Column(name="ORDER_NUMBER")
	public Integer getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}


    @Column(name="CODE", nullable=false, length=16)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defectRemedyType")
	public Set<RvwLvlFinding> getRvwLvlFindings() {
		return this.rvwLvlFindings;
	}

	public void setRvwLvlFindings(Set<RvwLvlFinding> rvwLvlFindings) {
		this.rvwLvlFindings = rvwLvlFindings;
	}

}
