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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="PERSONNEL", uniqueConstraints = @UniqueConstraint(columnNames="LOGIN_CREDENTIAL"))
@SuppressWarnings("serial")
public class Personnel implements java.io.Serializable {
	private String personnelId;
	private Personnel vettingPersonnel;
	private Personnel reportsToPersonnel;
	private PersonnelStatusRef personnelStatusRef;
	private ReviewLocation reviewLocation;
	private String emailAddress;
	private String firstName;
	private String middleName;
	private String lastName;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private String loginCredential;
	private char availabilityInd;
	private int reviewerCapacity;
	private Set<PersonnelAssignmentType> personnelAssignmentTypes = new HashSet<PersonnelAssignmentType>(0);
	private Set<PersonnelUnavailability> personnelUnavailabilities = new HashSet<PersonnelUnavailability>(0);
	private Set<BatchPersonnel> batchPersonnels = new HashSet<BatchPersonnel>(0);

	public Personnel() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="PERSONNEL_ID", unique=true, nullable=false, length=36)
	public String getPersonnelId() {
		return this.personnelId;
	}

	public void setPersonnelId(String personnelId) {
		this.personnelId = personnelId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="VETTING_PERSONNEL_ID")
	public Personnel getVettingPersonnel() {
		return this.vettingPersonnel;
	}

	public void setVettingPersonnel(Personnel vettingPersonnel) {
		this.vettingPersonnel = vettingPersonnel;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REPORTS_TO_PERSONNEL_ID")
	public Personnel getReportsToPersonnel() {
		return this.reportsToPersonnel;
	}

	public void setReportsToPersonnel(Personnel reportsToPersonnel) {
		this.reportsToPersonnel = reportsToPersonnel;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PERSONNEL_STATUS_ID", nullable=false)
	public PersonnelStatusRef getPersonnelStatusRef() {
		return this.personnelStatusRef;
	}

	public void setPersonnelStatusRef(PersonnelStatusRef personnelStatusRef) {
		this.personnelStatusRef = personnelStatusRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LOCATION_ID")
	public ReviewLocation getReviewLocation() {
		return this.reviewLocation;
	}

	public void setReviewLocation(ReviewLocation reviewLocation) {
		this.reviewLocation = reviewLocation;
	}


    @Column(name="EMAIL_ADDRESS")
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


    @Column(name="FIRST_NAME", length=100)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    @Column(name="MIDDLE_NAME", length=100)
	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


    @Column(name="LAST_NAME", length=100)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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


    @Column(name="LOGIN_CREDENTIAL", unique=true, length=32)
	public String getLoginCredential() {
		return this.loginCredential;
	}

	public void setLoginCredential(String loginCredential) {
		this.loginCredential = loginCredential;
	}


    @Column(name="AVAILABILITY_IND", nullable=false, length=1)
	public char getAvailabilityInd() {
		return this.availabilityInd;
	}

	public void setAvailabilityInd(char availabilityInd) {
		this.availabilityInd = availabilityInd;
	}


    @Column(name="REVIEWER_CAPACITY", nullable=false)
	public int getReviewerCapacity() {
		return this.reviewerCapacity;
	}

	public void setReviewerCapacity(int reviewerCapacity) {
		this.reviewerCapacity = reviewerCapacity;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="personnel")
	public Set<PersonnelAssignmentType> getPersonnelAssignmentTypes() {
		return this.personnelAssignmentTypes;
	}

	public void setPersonnelAssignmentTypes(Set<PersonnelAssignmentType> personnelAssignmentTypes) {
		this.personnelAssignmentTypes = personnelAssignmentTypes;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="personnel")
	public Set<PersonnelUnavailability> getPersonnelUnavailabilities() {
		return this.personnelUnavailabilities;
	}

	public void setPersonnelUnavailabilities(Set<PersonnelUnavailability> personnelUnavailabilities) {
		this.personnelUnavailabilities = personnelUnavailabilities;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="personnel")
	public Set<BatchPersonnel> getBatchPersonnels() {
		return this.batchPersonnels;
	}

	public void setBatchPersonnels(Set<BatchPersonnel> batchPersonnels) {
		this.batchPersonnels = batchPersonnels;
	}

}
