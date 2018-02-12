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
@Table(name="LOAN_SELECTION_STATUS_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class LoanSelectionStatusRef implements java.io.Serializable {
	private String loanSelectionStatusId;
	private String code;
	private String description;
	private Set<LoanSelection> loanSelections = new HashSet<LoanSelection>(0);

	public LoanSelectionStatusRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="LOAN_SELECTION_STATUS_ID", unique=true, nullable=false, length=36)
	public String getLoanSelectionStatusId() {
		return this.loanSelectionStatusId;
	}

	public void setLoanSelectionStatusId(String loanSelectionStatusId) {
		this.loanSelectionStatusId = loanSelectionStatusId;
	}


    @Column(name="CODE", unique=true, nullable=false, length=30)
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="loanSelectionStatusRef")
	public Set<LoanSelection> getLoanSelections() {
		return this.loanSelections;
	}

	public void setLoanSelections(Set<LoanSelection> loanSelections) {
		this.loanSelections = loanSelections;
	}

}
