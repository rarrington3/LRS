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
@Table(name="PRODUCT_DETAIL_REF", uniqueConstraints = @UniqueConstraint(columnNames="PROD_TYPE"))
@SuppressWarnings("serial")
public class ProductDetailRef implements java.io.Serializable {
	private String productDetailId;
	private ProductTypeRef productTypeRef;
	private String description;
	private String prodType;

	public ProductDetailRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="PRODUCT_DETAIL_ID", unique=true, nullable=false, length=36)
	public String getProductDetailId() {
		return this.productDetailId;
	}

	public void setProductDetailId(String productDetailId) {
		this.productDetailId = productDetailId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCT_TYPE_ID", nullable=false)
	public ProductTypeRef getProductTypeRef() {
		return this.productTypeRef;
	}

	public void setProductTypeRef(ProductTypeRef productTypeRef) {
		this.productTypeRef = productTypeRef;
	}


    @Column(name="DESCRIPTION", nullable=false, length=50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    @Column(name="PROD_TYPE", unique=true, nullable=false, length=3)
	public String getProdType() {
		return this.prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

}
