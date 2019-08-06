package com.it.erpapp.framework.model.transactions.sales;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NCDBCRCTV_INVOICES_DETAILS")
public class NCDBCInvoiceDetailsDO {

	@Id
	@GeneratedValue
	private long id;
	private long inv_id;
	private long prod_code;
	private int c_stock;
	private int e_stock;
	private int dc_stock;
	private int de_stock;
	private int quantity;
	private String unit_rate;
	private String disc_unit_rate;
	private String basic_price;
	private String gstp;
	private String deposit_amount;
	private String sgst_amount;
	private String cgst_amount;
	private String product_amount;
	
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
	public long getInv_id() {
		return inv_id;
	}
	public void setInv_id(long inv_id) {
		this.inv_id = inv_id;
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
	public String getBasic_price() {
		return basic_price;
	}
	public void setBasic_price(String basic_price) {
		this.basic_price = basic_price;
	}
	public String getGstp() {
		return gstp;
	}
	public void setGstp(String gstp) {
		this.gstp = gstp;
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
	public String getProduct_amount() {
		return product_amount;
	}
	public void setProduct_amount(String product_amount) {
		this.product_amount = product_amount;
	}
	public String getDeposit_amount() {
		return deposit_amount;
	}
	public void setDeposit_amount(String deposit_amount) {
		this.deposit_amount = deposit_amount;
	}
	public int getC_stock() {
		return c_stock;
	}
	public void setC_stock(int c_stock) {
		this.c_stock = c_stock;
	}
	public int getE_stock() {
		return e_stock;
	}
	public void setE_stock(int e_stock) {
		this.e_stock = e_stock;
	}
	public int getDc_stock() {
		return dc_stock;
	}
	public void setDc_stock(int dc_stock) {
		this.dc_stock = dc_stock;
	}
	public int getDe_stock() {
		return de_stock;
	}
	public void setDe_stock(int de_stock) {
		this.de_stock = de_stock;
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
