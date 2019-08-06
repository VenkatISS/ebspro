package com.it.erpapp.framework.model.transactions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RCTV_ARB_DETAILS")
public class RCTVARBDetailsDO {

	@Id
	@GeneratedValue
	private long id;
	private long qtn_id;
	private int prod_code;
	private int quantity;
	private String vatp;
	private String unit_rate;
	private String disc_unit_rate;
	private String basic_amount;
	private String vat_amount;
	private String prod_amount;
	private String foot_notes;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getQtn_id() {
		return qtn_id;
	}
	public void setQtn_id(long qtn_id) {
		this.qtn_id = qtn_id;
	}
	public int getProd_code() {
		return prod_code;
	}
	public void setProd_code(int prod_code) {
		this.prod_code = prod_code;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getVatp() {
		return vatp;
	}
	public void setVatp(String vatp) {
		this.vatp = vatp;
	}
	public String getUnit_rate() {
		return unit_rate;
	}
	public void setUnit_rate(String unit_rate) {
		this.unit_rate = unit_rate;
	}
	public String getDisc_unit_rate() {
		return disc_unit_rate;
	}
	public void setDisc_unit_rate(String disc_unit_rate) {
		this.disc_unit_rate = disc_unit_rate;
	}
	public String getBasic_amount() {
		return basic_amount;
	}
	public void setBasic_amount(String basic_amount) {
		this.basic_amount = basic_amount;
	}
	public String getVat_amount() {
		return vat_amount;
	}
	public void setVat_amount(String vat_amount) {
		this.vat_amount = vat_amount;
	}
	public String getProd_amount() {
		return prod_amount;
	}
	public void setProd_amount(String prod_amount) {
		this.prod_amount = prod_amount;
	}
	public String getFoot_notes() {
		return foot_notes;
	}
	public void setFoot_notes(String foot_notes) {
		this.foot_notes = foot_notes;
	}

	
}
