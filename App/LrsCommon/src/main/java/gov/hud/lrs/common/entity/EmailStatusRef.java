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
@Table(name="EMAIL_STATUS_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class EmailStatusRef implements java.io.Serializable {
	private String emailStatusId;
	private String code;
	private String description;
	private Set<Email> emails = new HashSet<Email>(0);

	public EmailStatusRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="EMAIL_STATUS_ID", unique=true, nullable=false, length=36)
	public String getEmailStatusId() {
		return this.emailStatusId;
	}

	public void setEmailStatusId(String emailStatusId) {
		this.emailStatusId = emailStatusId;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="emailStatusRef")
	public Set<Email> getEmails() {
		return this.emails;
	}

	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}

}
