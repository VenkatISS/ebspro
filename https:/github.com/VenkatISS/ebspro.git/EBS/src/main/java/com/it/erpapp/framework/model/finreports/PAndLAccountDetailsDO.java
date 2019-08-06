package com.it.erpapp.framework.model.finreports;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PROFIT_AND_LOSS_ACCOUNTS_DETAILS")
public class PAndLAccountDetailsDO {

	@Id
	@GeneratedValue
	private long id;
	private long pl_id;
	private String osv_amt = "0.00";
	private String csv_amt = "0.00";
	private String pv_amt = "0.00";
	private String sv_amt = "0.00";
	private String oi_amt = "0.00";
	private String gp_amt = "0.00";
	private String fo_amt = "0.00";
	private String fi_amt = "0.00";
	private String uc_amt = "0.00";
	private String il_amt = "0.00";
	private String sp_amt = "0.00";
	private String tc_amt = "0.00";
	private String ms_amt = "0.00";
	private String rn_amt = "0.00";
	private String bp_amt = "0.00";
	private String sw_amt = "0.00";
	private String bc_amt = "0.00";
	private String np_amt = "0.00";
	private String dp_amt = "0.00";
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPl_id() {
		return pl_id;
	}
	public void setPl_id(long pl_id) {
		this.pl_id = pl_id;
	}
	public String getFo_amt() {
		return fo_amt;
	}
	public void setFo_amt(String fo_amt) {
		this.fo_amt = fo_amt;
	}
	public String getFi_amt() {
		return fi_amt;
	}
	public void setFi_amt(String fi_amt) {
		this.fi_amt = fi_amt;
	}
	public String getUc_amt() {
		return uc_amt;
	}
	public void setUc_amt(String uc_amt) {
		this.uc_amt = uc_amt;
	}
	public String getIl_amt() {
		return il_amt;
	}
	public void setIl_amt(String il_amt) {
		this.il_amt = il_amt;
	}
	public String getSp_amt() {
		return sp_amt;
	}
	public void setSp_amt(String sp_amt) {
		this.sp_amt = sp_amt;
	}
	public String getMs_amt() {
		return ms_amt;
	}
	public void setMs_amt(String ms_amt) {
		this.ms_amt = ms_amt;
	}
	public String getTc_amt() {
		return tc_amt;
	}
	public void setTc_amt(String tc_amt) {
		this.tc_amt = tc_amt;
	}
	public String getRn_amt() {
		return rn_amt;
	}
	public void setRn_amt(String rn_amt) {
		this.rn_amt = rn_amt;
	}
	public String getBp_amt() {
		return bp_amt;
	}
	public void setBp_amt(String bp_amt) {
		this.bp_amt = bp_amt;
	}
	public String getSw_amt() {
		return sw_amt;
	}
	public void setSw_amt(String sw_amt) {
		this.sw_amt = sw_amt;
	}
	public String getBc_amt() {
		return bc_amt;
	}
	public void setBc_amt(String bc_amt) {
		this.bc_amt = bc_amt;
	}
	public String getOsv_amt() {
		return osv_amt;
	}
	public void setOsv_amt(String osv_amt) {
		this.osv_amt = osv_amt;
	}
	public String getCsv_amt() {
		return csv_amt;
	}
	public void setCsv_amt(String csv_amt) {
		this.csv_amt = csv_amt;
	}
	public String getPv_amt() {
		return pv_amt;
	}
	public void setPv_amt(String pv_amt) {
		this.pv_amt = pv_amt;
	}
	public String getSv_amt() {
		return sv_amt;
	}
	public void setSv_amt(String sv_amt) {
		this.sv_amt = sv_amt;
	}
	public String getOi_amt() {
		return oi_amt;
	}
	public void setOi_amt(String oi_amt) {
		this.oi_amt = oi_amt;
	}
	public String getGp_amt() {
		return gp_amt;
	}
	public void setGp_amt(String gp_amt) {
		this.gp_amt = gp_amt;
	}
	public String getNp_amt() {
		return np_amt;
	}
	public void setNp_amt(String np_amt) {
		this.np_amt = np_amt;
	}
	public String getDp_amt() {
		return dp_amt;
	}
	public void setDp_amt(String dp_amt) {
		this.dp_amt = dp_amt;
	}

}
