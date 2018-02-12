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
@Table(name="REVIEW_PROCESS_EXCEPTION_TYPE_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class ReviewProcessExceptionTypeRef implements java.io.Serializable {
	private String reviewProcessExceptionTypeId;
	private String code;
	private String description;
	private Set<ReviewProcessException> reviewProcessExceptions = new HashSet<ReviewProcessException>(0);

	public ReviewProcessExceptionTypeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="REVIEW_PROCESS_EXCEPTION_TYPE_ID", unique=true, nullable=false, length=36)
	public String getReviewProcessExceptionTypeId() {
		return this.reviewProcessExceptionTypeId;
	}

	public void setReviewProcessExceptionTypeId(String reviewProcessExceptionTypeId) {
		this.reviewProcessExceptionTypeId = reviewProcessExceptionTypeId;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewProcessExceptionTypeRef")
	public Set<ReviewProcessException> getReviewProcessExceptions() {
		return this.reviewProcessExceptions;
	}

	public void setReviewProcessExceptions(Set<ReviewProcessException> reviewProcessExceptions) {
		this.reviewProcessExceptions = reviewProcessExceptions;
	}

}
