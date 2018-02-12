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
@Table(name="EMAIL")
@SuppressWarnings("serial")
public class Email implements java.io.Serializable {
	private String emailId;
	private EmailStatusRef emailStatusRef;
	private EmailTemplate emailTemplate;
	private short tries;
	private Date sentTs;
	private String toAddress;
	private String ccAddress;
	private String subject;
	private String body;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;

	public Email() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="EMAIL_ID", unique=true, nullable=false, length=36)
	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EMAIL_STATUS_ID", nullable=false)
	public EmailStatusRef getEmailStatusRef() {
		return this.emailStatusRef;
	}

	public void setEmailStatusRef(EmailStatusRef emailStatusRef) {
		this.emailStatusRef = emailStatusRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EMAIL_TEMPLATE_ID", nullable=false)
	public EmailTemplate getEmailTemplate() {
		return this.emailTemplate;
	}

	public void setEmailTemplate(EmailTemplate emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

    
    @Column(name="TRIES", nullable=false)
	public short getTries() {
		return this.tries;
	}

	public void setTries(short tries) {
		this.tries = tries;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="SENT_TS", length=23)
	public Date getSentTs() {
		return this.sentTs;
	}

	public void setSentTs(Date sentTs) {
		this.sentTs = sentTs;
	}

    
    @Column(name="TO_ADDRESS", nullable=false, length=200)
	public String getToAddress() {
		return this.toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

    
    @Column(name="CC_ADDRESS", length=200)
	public String getCcAddress() {
		return this.ccAddress;
	}

	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

    
    @Column(name="SUBJECT", nullable=false, length=200)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

    
    @Column(name="BODY", nullable=false)
	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

    
    @Column(name="CREATED_BY", nullable=false, length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", nullable=false, length=23)
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

}
