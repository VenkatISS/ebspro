package com.it.erpapp.framework.model.transactions.sales;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SALES_RETURN_DETAILS")
public class SalesReturnDetailsDO {
	@Id
	@GeneratedValue
	private long id;
	private long sr_ref_id;
	private int prod_code;
	private int rtn_qty;
	private String gstp;
	private int cs_fulls;
	private int cs_emptys;
	private int ds_fulls;
	private int ds_emptys;
	private String unit_rate;
	private String net_weight;
	private String amount;
	private long bank_id;
	private String igst_amount;
	private String cgst_amount;
	private String sgst_amount;
	private String aamount;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSr_ref_id() {
		return sr_ref_id;
	}
	public void setSr_ref_id(long sr_ref_id) {
		this.sr_ref_id = sr_ref_id;
	}
	public int getProd_code() {
		return prod_code;
	}
	public void setProd_code(int prod_code) {
		this.prod_code = prod_code;
	}
	public int getRtn_qty() {
		return rtn_qty;
	}
	public void setRtn_qty(int rtn_qty) {
		this.rtn_qty = rtn_qty;
	}
	public String getGstp() {
		return gstp;
	}
	public void setGstp(String gstp) {
		this.gstp = gstp;
	}
	public int getCs_fulls() {
		return cs_fulls;
	}
	public void setCs_fulls(int cs_fulls) {
		this.cs_fulls = cs_fulls;
	}
	public int getCs_emptys() {
		return cs_emptys;
	}
	public void setCs_emptys(int cs_emptys) {
		this.cs_emptys = cs_emptys;
	}
	public int getDs_fulls() {
		return ds_fulls;
	}
	public void setDs_fulls(int ds_fulls) {
		this.ds_fulls = ds_fulls;
	}
	public int getDs_emptys() {
		return ds_emptys;
	}
	public void setDs_emptys(int ds_emptys) {
		this.ds_emptys = ds_emptys;
	}
	public String getUnit_rate() {
		return unit_rate;
	}
	public void setUnit_rate(String unit_rate) {
		this.unit_rate = unit_rate;
	}
	public String getNet_weight() {
		return net_weight;
	}
	public void setNet_weight(String net_weight) {
		this.net_weight = net_weight;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getIgst_amount() {
		return igst_amount;
	}
	public void setIgst_amount(String igst_amount) {
		this.igst_amount = igst_amount;
	}
	public String getCgst_amount() {
		return cgst_amount;
	}
	public void setCgst_amount(String cgst_amount) {
		this.cgst_amount = cgst_amount;
	}
	public String getSgst_amount() {
		return sgst_amount;
	}
	public void setSgst_amount(String sgst_amount) {
		this.sgst_amount = sgst_amount;
	}
	public String getAamount() {
		return aamount;
	}
	public void setAamount(String aamount) {
		this.aamount = aamount;
	}
	public long getBank_id() {
		return bank_id;
	}
	public void setBank_id(long bank_id) {
		this.bank_id = bank_id;
	}
	
}
