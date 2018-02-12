package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="LENDER")
@SuppressWarnings("serial")
public class Lender implements java.io.Serializable {

	private String lenderId;
	private String name;
	private String email;
	private String secondaryEmail;
	private char activeInd;
	private char dummyInd;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;

	public Lender() {
	}

    @Id
    @Column(name="LENDER_ID", unique=true, nullable=false, length=5)
	public String getLenderId() {
		return lenderId;
	}

	public void setLenderId(String lenderId) {
		this.lenderId = lenderId;
	}

    @Column(name="NAME", nullable=false, length=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Column(name="EMAIL", length=100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    @Column(name="SECONDARY_EMAIL", length=100)
	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	@Column(name = "ACTIVE_IND", nullable = false, length = 1)
	public char getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(char activeInd) {
		this.activeInd = activeInd;
	}

	@Column(name = "DUMMY_IND", nullable = false, length = 1)
	public char getDummyInd() {
		return dummyInd;
	}

	public void setDummyInd(char dummyInd) {
		this.dummyInd = dummyInd;
	}

    @Column(name="CREATED_BY", nullable=false, length=6)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", nullable=false, length=23)
	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

    @Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

}