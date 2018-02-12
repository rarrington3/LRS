//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="EMAIL_TEMPLATE_VARIABLE", uniqueConstraints = @UniqueConstraint(columnNames={"EMAIL_TEMPLATE_ID", "NAME"}))
@SuppressWarnings("serial")
public class EmailTemplateVariable implements java.io.Serializable {
	private String emailTemplateVariableId;
	private EmailTemplate emailTemplate;
	private String name;
	private String token;

	public EmailTemplateVariable() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="EMAIL_TEMPLATE_VARIABLE_ID", unique=true, nullable=false, length=36)
	public String getEmailTemplateVariableId() {
		return this.emailTemplateVariableId;
	}

	public void setEmailTemplateVariableId(String emailTemplateVariableId) {
		this.emailTemplateVariableId = emailTemplateVariableId;
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


    @Column(name="TOKEN", nullable=false, length=50)
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
