package com.it.erpapp.framework.model.transactions;

public class GSTR1HSNDataDO {
	
	private String hsncode;
	private long prod_code;
	private int uqc;
	private int prod_type;
	private int total_quantity;
	private String total_value;
	private String taxable_value;
	private String igst_amount;
	private String cgst_amount;
	private String sgst_amount;
	public String getHsncode() {
		return hsncode;
	}
	public void setHsncode(String hsncode) {
		this.hsncode = hsncode;
	}
	public long getProd_code() {
		return prod_code;
	}
	public void setProd_code(long prod_code) {
		this.prod_code = prod_code;
	}
	public int getProd_type() {
		return prod_type;
	}
	public void setProd_type(int prod_type) {
		this.prod_type = prod_type;
	}
	public int getUqc() {
		return uqc;
	}
	public void setUqc(int uqc) {
		this.uqc = uqc;
	}
	public int getTotal_quantity() {
		return total_quantity;
	}
	public void setTotal_quantity(int total_quantity) {
		this.total_quantity = total_quantity;
	}
	public String getTotal_value() {
		return total_value;
	}
	public void setTotal_value(String total_value) {
		this.total_value = total_value;
	}
	public String getTaxable_value() {
		return taxable_value;
	}
	public void setTaxable_value(String taxable_value) {
		this.taxable_value = taxable_value;
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

}
