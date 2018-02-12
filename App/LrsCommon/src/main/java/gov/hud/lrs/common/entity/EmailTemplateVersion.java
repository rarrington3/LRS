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
@Table(name="EMAIL_TEMPLATE_VERSION", uniqueConstraints = @UniqueConstraint(columnNames={"EMAIL_TEMPLATE_ID", "VERSION_NUMBER"}))
@SuppressWarnings("serial")
public class EmailTemplateVersion implements java.io.Serializable {
	private String emailTemplateVersionId;
	private EmailTemplate emailTemplate;
	private String name;
	private String description;
	private int versionNumber;
	private char activeInd;
	private Date activatedTs;
	private String subject;
	private String body;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;

	public EmailTemplateVersion() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="EMAIL_TEMPLATE_VERSION_ID", unique=true, nullable=false, length=36)
	public String getEmailTemplateVersionId() {
		return this.emailTemplateVersionId;
	}

	public void setEmailTemplateVersionId(String emailTemplateVersionId) {
		this.emailTemplateVersionId = emailTemplateVersionId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EMAIL_TEMPLATE_ID", nullable=false)
	public EmailTemplate getEmailTemplate() {
		return this.emailTemplate;
	}

	public void setEmailTemplate(EmailTemplate emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

    
    @Column(name="NAME", nullable=false, length=50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
    @Column(name="DESCRIPTION", nullable=false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    
    @Column(name="VERSION_NUMBER", nullable=false)
	public int getVersionNumber() {
		return this.versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

    
    @Column(name="ACTIVE_IND", nullable=false, length=1)
	public char getActiveInd() {
		return this.activeInd;
	}

	public void setActiveInd(char activeInd) {
		this.activeInd = activeInd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ACTIVATED_TS", length=23)
	public Date getActivatedTs() {
		return this.activatedTs;
	}

	public void setActivatedTs(Date activatedTs) {
		this.activatedTs = activatedTs;
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
