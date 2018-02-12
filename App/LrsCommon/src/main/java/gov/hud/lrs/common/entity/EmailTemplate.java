//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="EMAIL_TEMPLATE", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class EmailTemplate implements java.io.Serializable {
	private String emailTemplateId;
	private String code;
	private String description;
	private Set<Email> emails = new HashSet<Email>(0);
	private Set<EmailTemplateVersion> emailTemplateVersions = new HashSet<EmailTemplateVersion>(0);
	private Set<EmailTemplateVariable> emailTemplateVariables = new HashSet<EmailTemplateVariable>(0);

	public EmailTemplate() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="EMAIL_TEMPLATE_ID", unique=true, nullable=false, length=36)
	public String getEmailTemplateId() {
		return this.emailTemplateId;
	}

	public void setEmailTemplateId(String emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}


    @Column(name="CODE", unique=true, nullable=false, length=50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


    @Column(name="DESCRIPTION", nullable=false, length=50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="emailTemplate")
	public Set<Email> getEmails() {
		return this.emails;
	}

	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="emailTemplate")
	public Set<EmailTemplateVersion> getEmailTemplateVersions() {
		return this.emailTemplateVersions;
	}

	public void setEmailTemplateVersions(Set<EmailTemplateVersion> emailTemplateVersions) {
		this.emailTemplateVersions = emailTemplateVersions;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="emailTemplate")
	public Set<EmailTemplateVariable> getEmailTemplateVariables() {
		return this.emailTemplateVariables;
	}

	public void setEmailTemplateVariables(Set<EmailTemplateVariable> emailTemplateVariables) {
		this.emailTemplateVariables = emailTemplateVariables;
	}

}
