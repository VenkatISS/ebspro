package com.it.erpapp.framework.model.transactions;

public class GSTR1DataDO {
	private long customer_id;
	private String inv_no;
	private long inv_date;
	private String inv_amount;
	private int quantity;
	private String unit_rate;
	private String gstp;
	private String taxable_value;
	private int inv_type;
	private long created_date; 
	
	public String getInv_no() {
		return inv_no;
	}
	public void setInv_no(String string) {
		this.inv_no = string;
	}
	public long getInv_date() {
		return inv_date;
	}
	public void setInv_date(long inv_date) {
		this.inv_date = inv_date;
	}
	public String getInv_amount() {
		return inv_amount;
	}
	public void setInv_amount(String inv_amount) {
		this.inv_amount = inv_amount;
	}
	public String getGstp() {
		return gstp;
	}
	public void setGstp(String gstp) {
		this.gstp = gstp;
	}
	public String getTaxable_value() {
		return taxable_value;
	}
	public void setTaxable_value(String taxable_value) {
		this.taxable_value = taxable_value;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnit_rate() {
		return unit_rate;
	}
	public void setUnit_rate(String unit_rate) {
		this.unit_rate = unit_rate;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public int getInv_type() {
		return inv_type;
	}
	public void setInv_type(int inv_type) {
		this.inv_type = inv_type;
	}
	public long getCreated_date() {
		return created_date;
	}
	public void setCreated_date(long created_date) {
		this.created_date = created_date;
	}
	
}
