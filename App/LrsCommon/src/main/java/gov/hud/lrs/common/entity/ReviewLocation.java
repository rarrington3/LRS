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
@Table(name="REVIEW_LOCATION")
@SuppressWarnings("serial")
public class ReviewLocation implements java.io.Serializable {
	private String reviewLocationId;
	private BinderDeliveryLocationRef binderDeliveryLocationRef;
	private Personnel ownerPersonnel;
	private String locationName;
	private String contactName;
	private String contactPhoneNumber;
	private String mailingAddress;
	private String mailingCity;
	private String mailingState;
	private String mailingZip;
	private Integer committedMonthlyCapacity;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private String phoneExtension;
	private Date activationDate;
	private Date deactivationDate;
	private String mailingAddress2;
	private Set<ReviewLevel> reviewLevels = new HashSet<ReviewLevel>(0);
	private Set<Batch> batches = new HashSet<Batch>(0);
	private Set<LenderMonitoringSelectionRequest> lenderMonitoringSelectionRequests = new HashSet<LenderMonitoringSelectionRequest>(0);
	private Set<Indemnification> indemnifications = new HashSet<Indemnification>(0);
	private Set<ManualSelectionRequest> manualSelectionRequests = new HashSet<ManualSelectionRequest>(0);
	private Set<LocationAssignmentType> locationAssignmentTypes = new HashSet<LocationAssignmentType>(0);
	private Set<RvwLocationReason> rvwLocationReasons = new HashSet<RvwLocationReason>(0);
	private Set<Personnel> personnels = new HashSet<Personnel>(0);
	private Set<LoanSelectionPending> loanSelectionPendings = new HashSet<LoanSelectionPending>(0);

	public ReviewLocation() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="REVIEW_LOCATION_ID", unique=true, nullable=false, length=36)
	public String getReviewLocationId() {
		return this.reviewLocationId;
	}

	public void setReviewLocationId(String reviewLocationId) {
		this.reviewLocationId = reviewLocationId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="BINDER_DELIVERY_LOCATION_ID")
	public BinderDeliveryLocationRef getBinderDeliveryLocationRef() {
		return this.binderDeliveryLocationRef;
	}

	public void setBinderDeliveryLocationRef(BinderDeliveryLocationRef binderDeliveryLocationRef) {
		this.binderDeliveryLocationRef = binderDeliveryLocationRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="OWNER_PERSONNEL_ID")
	public Personnel getOwnerPersonnel() {
		return this.ownerPersonnel;
	}

	public void setOwnerPersonnel(Personnel ownerPersonnel) {
		this.ownerPersonnel = ownerPersonnel;
	}

    
    @Column(name="LOCATION_NAME", nullable=false, length=16)
	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

    
    @Column(name="CONTACT_NAME", length=100)
	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

    
    @Column(name="CONTACT_PHONE_NUMBER", length=32)
	public String getContactPhoneNumber() {
		return this.contactPhoneNumber;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

    
    @Column(name="MAILING_ADDRESS", length=100)
	public String getMailingAddress() {
		return this.mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

    
    @Column(name="MAILING_CITY", length=100)
	public String getMailingCity() {
		return this.mailingCity;
	}

	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}

    
    @Column(name="MAILING_STATE", length=16)
	public String getMailingState() {
		return this.mailingState;
	}

	public void setMailingState(String mailingState) {
		this.mailingState = mailingState;
	}

    
    @Column(name="MAILING_ZIP", length=16)
	public String getMailingZip() {
		return this.mailingZip;
	}

	public void setMailingZip(String mailingZip) {
		this.mailingZip = mailingZip;
	}

    
    @Column(name="COMMITTED_MONTHLY_CAPACITY")
	public Integer getCommittedMonthlyCapacity() {
		return this.committedMonthlyCapacity;
	}

	public void setCommittedMonthlyCapacity(Integer committedMonthlyCapacity) {
		this.committedMonthlyCapacity = committedMonthlyCapacity;
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

    
    @Column(name="PHONE_EXTENSION", length=4)
	public String getPhoneExtension() {
		return this.phoneExtension;
	}

	public void setPhoneExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ACTIVATION_DATE", length=23)
	public Date getActivationDate() {
		return this.activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DEACTIVATION_DATE", length=23)
	public Date getDeactivationDate() {
		return this.deactivationDate;
	}

	public void setDeactivationDate(Date deactivationDate) {
		this.deactivationDate = deactivationDate;
	}

    
    @Column(name="MAILING_ADDRESS_2", length=100)
	public String getMailingAddress2() {
		return this.mailingAddress2;
	}

	public void setMailingAddress2(String mailingAddress2) {
		this.mailingAddress2 = mailingAddress2;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLocation")
	public Set<ReviewLevel> getReviewLevels() {
		return this.reviewLevels;
	}

	public void setReviewLevels(Set<ReviewLevel> reviewLevels) {
		this.reviewLevels = reviewLevels;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLocation")
	public Set<Batch> getBatches() {
		return this.batches;
	}

	public void setBatches(Set<Batch> batches) {
		this.batches = batches;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLocation")
	public Set<LenderMonitoringSelectionRequest> getLenderMonitoringSelectionRequests() {
		return this.lenderMonitoringSelectionRequests;
	}

	public void setLenderMonitoringSelectionRequests(Set<LenderMonitoringSelectionRequest> lenderMonitoringSelectionRequests) {
		this.lenderMonitoringSelectionRequests = lenderMonitoringSelectionRequests;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLocation")
	public Set<Indemnification> getIndemnifications() {
		return this.indemnifications;
	}

	public void setIndemnifications(Set<Indemnification> indemnifications) {
		this.indemnifications = indemnifications;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLocation")
	public Set<ManualSelectionRequest> getManualSelectionRequests() {
		return this.manualSelectionRequests;
	}

	public void setManualSelectionRequests(Set<ManualSelectionRequest> manualSelectionRequests) {
		this.manualSelectionRequests = manualSelectionRequests;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLocation")
	public Set<LocationAssignmentType> getLocationAssignmentTypes() {
		return this.locationAssignmentTypes;
	}

	public void setLocationAssignmentTypes(Set<LocationAssignmentType> locationAssignmentTypes) {
		this.locationAssignmentTypes = locationAssignmentTypes;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLocation")
	public Set<RvwLocationReason> getRvwLocationReasons() {
		return this.rvwLocationReasons;
	}

	public void setRvwLocationReasons(Set<RvwLocationReason> rvwLocationReasons) {
		this.rvwLocationReasons = rvwLocationReasons;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLocation")
	public Set<Personnel> getPersonnels() {
		return this.personnels;
	}

	public void setPersonnels(Set<Personnel> personnels) {
		this.personnels = personnels;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLocation")
	public Set<LoanSelectionPending> getLoanSelectionPendings() {
		return this.loanSelectionPendings;
	}

	public void setLoanSelectionPendings(Set<LoanSelectionPending> loanSelectionPendings) {
		this.loanSelectionPendings = loanSelectionPendings;
	}

}
