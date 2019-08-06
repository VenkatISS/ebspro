package com.it.erpapp.framework.model.finreports;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BALANCE_SHEET_DETAILS")
public class BalanceSheetDetailsDO {

	@Id
	@GeneratedValue
	private long id;
	private long bs_id;
	private String caob_amt = "0.00";
	private String cani_amt = "0.00";
	private String canw_amt = "0.00";
	private String cacb_amt = "0.00";
	private String plnp_amt = "0.00";
	private String catb_amt = "0.00";
	private String faob_amt = "0.00";
	private String fanp_amt = "0.00";
	private String fans_amt = "0.00";
	private String fadp_amt = "0.00";
	private String facb_amt = "0.00";
	private String cacs_amt = "0.00";
	private String clsc_amt = "0.00";
	private String sltb_amt = "0.00";
	private String ulfr_amt = "0.00";
	private String prov_amt = "0.00";
	private String casd_amt = "0.00";
	private String cala_amt = "0.00";
	private String cach_amt = "0.00";
	private String cars_amt = "0.00";
	private String cadp_amt = "0.00";
	private String cabc_amt = "0.00";
	private String fytl_amt = "0.00";
	private String fyta_amt = "0.00";

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getBs_id() {
		return bs_id;
	}
	public void setBs_id(long bs_id) {
		this.bs_id = bs_id;
	}
	public String getCaob_amt() {
		return caob_amt;
	}
	public void setCaob_amt(String caob_amt) {
		this.caob_amt = caob_amt;
	}
	public String getCani_amt() {
		return cani_amt;
	}
	public void setCani_amt(String cani_amt) {
		this.cani_amt = cani_amt;
	}
	public String getCanw_amt() {
		return canw_amt;
	}
	public void setCanw_amt(String canw_amt) {
		this.canw_amt = canw_amt;
	}
	public String getCacb_amt() {
		return cacb_amt;
	}
	public void setCacb_amt(String cacb_amt) {
		this.cacb_amt = cacb_amt;
	}
	public String getPlnp_amt() {
		return plnp_amt;
	}
	public void setPlnp_amt(String plnp_amt) {
		this.plnp_amt = plnp_amt;
	}
	public String getCatb_amt() {
		return catb_amt;
	}
	public void setCatb_amt(String catb_amt) {
		this.catb_amt = catb_amt;
	}
	public String getFaob_amt() {
		return faob_amt;
	}
	public void setFaob_amt(String faob_amt) {
		this.faob_amt = faob_amt;
	}
	public String getFanp_amt() {
		return fanp_amt;
	}
	public void setFanp_amt(String fanp_amt) {
		this.fanp_amt = fanp_amt;
	}
	public String getFans_amt() {
		return fans_amt;
	}
	public void setFans_amt(String fans_amt) {
		this.fans_amt = fans_amt;
	}
	public String getFadp_amt() {
		return fadp_amt;
	}
	public void setFadp_amt(String fadp_amt) {
		this.fadp_amt = fadp_amt;
	}
	public String getFacb_amt() {
		return facb_amt;
	}
	public void setFacb_amt(String facb_amt) {
		this.facb_amt = facb_amt;
	}
	public String getCacs_amt() {
		return cacs_amt;
	}
	public void setCacs_amt(String cacs_amt) {
		this.cacs_amt = cacs_amt;
	}
	public String getClsc_amt() {
		return clsc_amt;
	}
	public void setClsc_amt(String clsc_amt) {
		this.clsc_amt = clsc_amt;
	}
	public String getSltb_amt() {
		return sltb_amt;
	}
	public void setSltb_amt(String sltb_amt) {
		this.sltb_amt = sltb_amt;
	}
	public String getUlfr_amt() {
		return ulfr_amt;
	}
	public void setUlfr_amt(String ulfr_amt) {
		this.ulfr_amt = ulfr_amt;
	}
	public String getCasd_amt() {
		return casd_amt;
	}
	public void setCasd_amt(String casd_amt) {
		this.casd_amt = casd_amt;
	}
	public String getCala_amt() {
		return cala_amt;
	}
	public void setCala_amt(String cala_amt) {
		this.cala_amt = cala_amt;
	}
	public String getCach_amt() {
		return cach_amt;
	}
	public void setCach_amt(String cach_amt) {
		this.cach_amt = cach_amt;
	}
	public String getCars_amt() {
		return cars_amt;
	}
	public void setCars_amt(String cars_amt) {
		this.cars_amt = cars_amt;
	}
	public String getCadp_amt() {
		return cadp_amt;
	}
	public void setCadp_amt(String cadp_amt) {
		this.cadp_amt = cadp_amt;
	}
	public String getCabc_amt() {
		return cabc_amt;
	}
	public void setCabc_amt(String cabc_amt) {
		this.cabc_amt = cabc_amt;
	}
	public String getFytl_amt() {
		return fytl_amt;
	}
	public void setFytl_amt(String fytl_amt) {
		this.fytl_amt = fytl_amt;
	}
	public String getFyta_amt() {
		return fyta_amt;
	}
	public void setFyta_amt(String fyta_amt) {
		this.fyta_amt = fyta_amt;
	}
	public String getProv_amt() {
		return prov_amt;
	}
	public void setProv_amt(String prov_amt) {
		this.prov_amt = prov_amt;
	}
	
}

