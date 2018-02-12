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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="PRODUCT_TYPE_REF")
@SuppressWarnings("serial")
public class ProductTypeRef implements java.io.Serializable {
	private String productTypeId;
	private String productTypeCd;
	private String description;
	private Set<LoanSelectionPending> loanSelectionPendings = new HashSet<LoanSelectionPending>(0);
	private Set<Review> reviews = new HashSet<Review>(0);
	private Set<LoanSelection> loanSelections = new HashSet<LoanSelection>(0);
	private Set<ProductDetailRef> productDetailRefs = new HashSet<ProductDetailRef>(0);

	public ProductTypeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="PRODUCT_TYPE_ID", unique=true, nullable=false, length=36)
	public String getProductTypeId() {
		return this.productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}


    @Column(name="PRODUCT_TYPE_CD", nullable=false, length=5)
	public String getProductTypeCd() {
		return this.productTypeCd;
	}

	public void setProductTypeCd(String productTypeCd) {
		this.productTypeCd = productTypeCd;
	}


    @Column(name="DESCRIPTION", nullable=false, length=50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="productTypeRef")
	public Set<LoanSelectionPending> getLoanSelectionPendings() {
		return this.loanSelectionPendings;
	}

	public void setLoanSelectionPendings(Set<LoanSelectionPending> loanSelectionPendings) {
		this.loanSelectionPendings = loanSelectionPendings;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="productTypeRef")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="productTypeRef")
	public Set<LoanSelection> getLoanSelections() {
		return this.loanSelections;
	}

	public void setLoanSelections(Set<LoanSelection> loanSelections) {
		this.loanSelections = loanSelections;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="productTypeRef")
	public Set<ProductDetailRef> getProductDetailRefs() {
		return this.productDetailRefs;
	}

	public void setProductDetailRefs(Set<ProductDetailRef> productDetailRefs) {
		this.productDetailRefs = productDetailRefs;
	}

}
