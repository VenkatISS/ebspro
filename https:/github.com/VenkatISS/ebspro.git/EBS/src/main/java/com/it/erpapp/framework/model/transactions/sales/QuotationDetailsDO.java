package com.it.erpapp.framework.model.transactions.sales;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="QUOTATION_DETAILS")
public class QuotationDetailsDO {

	@Id
	@GeneratedValue
	private long id;
	private long qtn_id;
	private long prod_code;
	private int quantity;
	private String vatp;
	private String unit_rate;
	private String disc_unit_rate;
	private String basic_amount;
	private String igst_amount;
	private String sgst_amount;
	private String cgst_amount;
	private String prod_amount;
//	private String foot_notes;
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
	public long getProd_code() {
		return prod_code;
	}
	public void setProd_code(long prod_code) {
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
	public String getProd_amount() {
		return prod_amount;
	}
	public void setProd_amount(String prod_amount) {
		this.prod_amount = prod_amount;
	}
	public String getSgst_amount() {
		return sgst_amount;
	}
	public void setSgst_amount(String sgst_amount) {
		this.sgst_amount = sgst_amount;
	}
	public String getCgst_amount() {
		return cgst_amount;
	}
	public void setCgst_amount(String cgst_amount) {
		this.cgst_amount = cgst_amount;
	}
	public String getIgst_amount() {
		return igst_amount;
	}
	public void setIgst_amount(String igst_amount) {
		this.igst_amount = igst_amount;
	}

	
}
