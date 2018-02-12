package gov.hud.lrs.common.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MuleUniverseGetCaseDetailDTO implements Serializable {

	private String srvcr_mrtggee_id_inst;
	private String uwrtng_mrtggee_id_inst;
	private String erly_clm_ind;
	private String loan_type;
	private String case_number;
	private String erly_pmt_dflt_ind;
	private String orgntng_mrtggee_id_inst;
	private String endrs_dt;
	private String hldng_mrtggee_inst;

	public String getSrvcr_mrtggee_id_inst() {
		return srvcr_mrtggee_id_inst;
	}

	public void setSrvcr_mrtggee_id_inst(String srvcr_mrtggee_id_inst) {
		this.srvcr_mrtggee_id_inst = srvcr_mrtggee_id_inst;
	}

	public String getUwrtng_mrtggee_id_inst() {
		return uwrtng_mrtggee_id_inst;
	}

	public void setUwrtng_mrtggee_id_inst(String uwrtng_mrtggee_id_inst) {
		this.uwrtng_mrtggee_id_inst = uwrtng_mrtggee_id_inst;
	}

	public String getErly_clm_ind() {
		return erly_clm_ind;
	}

	public void setErly_clm_ind(String erly_clm_ind) {
		this.erly_clm_ind = erly_clm_ind;
	}

	public String getLoan_type() {
		return loan_type;
	}

	public void setLoan_type(String loan_type) {
		this.loan_type = loan_type;
	}

	public String getCase_number() {
		return case_number;
	}

	public void setCase_number(String case_number) {
		this.case_number = case_number;
	}

	public String getErly_pmt_dflt_ind() {
		return erly_pmt_dflt_ind;
	}

	public void setErly_pmt_dflt_ind(String erly_pmt_dflt_ind) {
		this.erly_pmt_dflt_ind = erly_pmt_dflt_ind;
	}

	public String getOrgntng_mrtggee_id_inst() {
		return orgntng_mrtggee_id_inst;
	}

	public void setOrgntng_mrtggee_id_inst(String orgntng_mrtggee_id_inst) {
		this.orgntng_mrtggee_id_inst = orgntng_mrtggee_id_inst;
	}

	public String getEndrs_dt() {
		return endrs_dt;
	}

	public void setEndrs_dt(String endrs_dt) {
		this.endrs_dt = endrs_dt;
	}

	public String getHldng_mrtggee_inst() {
		return hldng_mrtggee_inst;
	}

	public void setHldng_mrtggee_inst(String hldng_mrtggee_inst) {
		this.hldng_mrtggee_inst = hldng_mrtggee_inst;
	}
}
