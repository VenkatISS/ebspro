package com.it.erpapp.framework.model.transactions.sales;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DOM_SALES_INVOICES_DETAILS")
public class DOMSalesInvoiceDetailsDO {

	@Id
	@GeneratedValue
	private long id;
	private long dsi_id;
	private int prod_code;
	private int quantity;
	private String unit_rate;
	private String disc_unit_rate;
	private int pre_cyls;
	private int psv_cyls;
	private int cs_fulls;
	private int cs_emptys;
	private int ds_fulls;
	private int ds_emptys;
	private long staff_id;
	private long ac_id;
	private String gstp;
	private String igst_amount;	
	private String sgst_amount;
	private String cgst_amount;
	private String sale_amount;
	
	private String op_basic_price;
	private String op_taxable_amt;	
	private String op_total_amt;
	private String op_igst_amt;
	private String op_sgst_amt;
	private String op_cgst_amt;
	
	private long bank_id;
	private String amount_rcvd_online;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDsi_id() {
		return dsi_id;
	}
	public void setDsi_id(long dsi_id) {
		this.dsi_id = dsi_id;
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
	public int getPre_cyls() {
		return pre_cyls;
	}
	public void setPre_cyls(int pre_cyls) {
		this.pre_cyls = pre_cyls;
	}
	public int getPsv_cyls() {
		return psv_cyls;
	}
	public void setPsv_cyls(int psv_cyls) {
		this.psv_cyls = psv_cyls;
	}
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}
	public long getAc_id() {
		return ac_id;
	}
	public void setAc_id(long ac_id) {
		this.ac_id = ac_id;
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
	public String getSale_amount() {
		return sale_amount;
	}
	public void setSale_amount(String sale_amount) {
		this.sale_amount = sale_amount;
	}
	public String getGstp() {
		return gstp;
	}
	public void setGstp(String gstp) {
		this.gstp = gstp;
	}
	public String getIgst_amount() {
		return igst_amount;
	}
	public void setIgst_amount(String igst_amount){
		this.igst_amount = igst_amount;
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
	public long getBank_id() {
		return bank_id;
	}
	public void setBank_id(long bank_id) {
		this.bank_id = bank_id;
	}
	public String getAmount_rcvd_online() {
		return amount_rcvd_online;
	}
	public void setAmount_rcvd_online(String amount_rcvd_online) {
		this.amount_rcvd_online = amount_rcvd_online;
	}
	public String getOp_taxable_amt() {
		return op_taxable_amt;
	}
	public void setOp_taxable_amt(String op_taxable_amt) {
		this.op_taxable_amt = op_taxable_amt;
	}
	public String getOp_total_amt() {
		return op_total_amt;
	}
	public void setOp_total_amt(String op_total_amt) {
		this.op_total_amt = op_total_amt;
	}
	public String getOp_igst_amt() {
		return op_igst_amt;
	}
	public void setOp_igst_amt(String op_igst_amt) {
		this.op_igst_amt = op_igst_amt;
	}
	public String getOp_sgst_amt() {
		return op_sgst_amt;
	}
	public void setOp_sgst_amt(String op_sgst_amt) {
		this.op_sgst_amt = op_sgst_amt;
	}
	public String getOp_cgst_amt() {
		return op_cgst_amt;
	}
	public void setOp_cgst_amt(String op_cgst_amt) {
		this.op_cgst_amt = op_cgst_amt;
	}
	public String getOp_basic_price() {
		return op_basic_price;
	}
	public void setOp_basic_price(String op_basic_price) {
		this.op_basic_price = op_basic_price;
	}

}
