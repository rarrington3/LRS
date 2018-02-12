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
@Table(name="PERSONNEL_STATUS_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class PersonnelStatusRef implements java.io.Serializable {
	private String personnelStatusId;
	private String code;
	private String description;
	private Set<Personnel> personnels = new HashSet<Personnel>(0);

	public PersonnelStatusRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="PERSONNEL_STATUS_ID", unique=true, nullable=false, length=36)
	public String getPersonnelStatusId() {
		return this.personnelStatusId;
	}

	public void setPersonnelStatusId(String personnelStatusId) {
		this.personnelStatusId = personnelStatusId;
	}


    @Column(name="CODE", unique=true, nullable=false, length=1)
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="personnelStatusRef")
	public Set<Personnel> getPersonnels() {
		return this.personnels;
	}

	public void setPersonnels(Set<Personnel> personnels) {
		this.personnels = personnels;
	}

}
