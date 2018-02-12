//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity.ext;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="lender_lookup")
@SuppressWarnings("serial")
public class LenderLookup implements java.io.Serializable {
	private String srvcrMrtgeNbr;
	private String mortgageeId;
	private Character insuranctType;
	private String MAddrLine1;
	private String MAddrLine2;
	private String MCity;
	private String MState;
	private String MZip1;
	private String MZip2;
	private String institutionName;
	private Date dateLastUpdt;
	private Character theFiller;

	public LenderLookup() {
	}

    @Id 
    
    @Column(name="srvcr_mrtge_nbr", unique=true, nullable=false, length=5)
	public String getSrvcrMrtgeNbr() {
		return this.srvcrMrtgeNbr;
	}

	public void setSrvcrMrtgeNbr(String srvcrMrtgeNbr) {
		this.srvcrMrtgeNbr = srvcrMrtgeNbr;
	}

    
    @Column(name="mortgagee_id", nullable=false, length=10)
	public String getMortgageeId() {
		return this.mortgageeId;
	}

	public void setMortgageeId(String mortgageeId) {
		this.mortgageeId = mortgageeId;
	}

    
    @Column(name="insuranct_type", length=1)
	public Character getInsuranctType() {
		return this.insuranctType;
	}

	public void setInsuranctType(Character insuranctType) {
		this.insuranctType = insuranctType;
	}

    
    @Column(name="m_addr_line1", length=30)
	public String getMAddrLine1() {
		return this.MAddrLine1;
	}

	public void setMAddrLine1(String MAddrLine1) {
		this.MAddrLine1 = MAddrLine1;
	}

    
    @Column(name="m_addr_line2", length=30)
	public String getMAddrLine2() {
		return this.MAddrLine2;
	}

	public void setMAddrLine2(String MAddrLine2) {
		this.MAddrLine2 = MAddrLine2;
	}

    
    @Column(name="m_city", length=19)
	public String getMCity() {
		return this.MCity;
	}

	public void setMCity(String MCity) {
		this.MCity = MCity;
	}

    
    @Column(name="m_state", length=2)
	public String getMState() {
		return this.MState;
	}

	public void setMState(String MState) {
		this.MState = MState;
	}

    
    @Column(name="m_zip1", length=5)
	public String getMZip1() {
		return this.MZip1;
	}

	public void setMZip1(String MZip1) {
		this.MZip1 = MZip1;
	}

    
    @Column(name="m_zip2", length=4)
	public String getMZip2() {
		return this.MZip2;
	}

	public void setMZip2(String MZip2) {
		this.MZip2 = MZip2;
	}

    
    @Column(name="institution_name", length=40)
	public String getInstitutionName() {
		return this.institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_last_updt", length=23)
	public Date getDateLastUpdt() {
		return this.dateLastUpdt;
	}

	public void setDateLastUpdt(Date dateLastUpdt) {
		this.dateLastUpdt = dateLastUpdt;
	}

    
    @Column(name="the_filler", length=1)
	public Character getTheFiller() {
		return this.theFiller;
	}

	public void setTheFiller(Character theFiller) {
		this.theFiller = theFiller;
	}

}
