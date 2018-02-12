//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="LRS_GEN_LOOKUP")
@SuppressWarnings("serial")
public class LrsGenLookup implements java.io.Serializable {
	private LrsGenLookupId id;
	private String lookupDescription;
	private String addlLookupCategory;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Integer displayOrder;

	public LrsGenLookup() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="lookupEntity", column=@Column(name="LOOKUP_ENTITY", nullable=false, length=100) ), 
        @AttributeOverride(name="lookupField", column=@Column(name="LOOKUP_FIELD", nullable=false, length=100) ), 
        @AttributeOverride(name="lookupCode", column=@Column(name="LOOKUP_CODE", nullable=false, length=40) ) } )
	public LrsGenLookupId getId() {
		return this.id;
	}

	public void setId(LrsGenLookupId id) {
		this.id = id;
	}

    
    @Column(name="LOOKUP_DESCRIPTION", nullable=false, length=256)
	public String getLookupDescription() {
		return this.lookupDescription;
	}

	public void setLookupDescription(String lookupDescription) {
		this.lookupDescription = lookupDescription;
	}

    
    @Column(name="ADDL_LOOKUP_CATEGORY", length=100)
	public String getAddlLookupCategory() {
		return this.addlLookupCategory;
	}

	public void setAddlLookupCategory(String addlLookupCategory) {
		this.addlLookupCategory = addlLookupCategory;
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

    
    @Column(name="DISPLAY_ORDER")
	public Integer getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

}
