package com.it.erpapp.framework.model.transactions.sales;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ARB_SALES_INVOICES_DETAILS")
public class ARBSalesInvoiceDetailsDO {

	@Id
	@GeneratedValue
	private long id;
	private long arbsi_id;
	private long prod_code;
	private int quantity;
	private int c_stock;
	private int d_stock;
	private String unit_rate;
	private String disc_unit_rate;
	private String gstp;
	private long bank_id;
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
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getArbsi_id() {
		return arbsi_id;
	}
	public void setArbsi_id(long arbsi_id) {
		this.arbsi_id = arbsi_id;
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
	public void setIgst_amount(String igst_amount) {
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
	public int getC_stock() {
		return c_stock;
	}
	public void setC_stock(int c_stock) {
		this.c_stock = c_stock;
	}
	public int getD_stock() {
		return d_stock;
	}
	public void setD_stock(int d_stock) {
		this.d_stock = d_stock;
	}
	public long getBank_id() {
		return bank_id;
	}
	public void setBank_id(long bank_id) {
		this.bank_id = bank_id;
	}
	public String getOp_basic_price() {
		return op_basic_price;
	}
	public void setOp_basic_price(String op_basic_price) {
		this.op_basic_price = op_basic_price;
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
	
}
