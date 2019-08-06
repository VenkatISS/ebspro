package com.it.erpapp.framework.model.transactions.sales;

public class DEProductsDetailsViewDO {

	private long tx_id;
	private int tx_type;
	private long prod_code;
	private int ncquantity;
	private int quantity;
	private int c_stock;
	private int e_stock;
	private String tx_amount;
	
	public long getTx_id() {
		return tx_id;
	}
	public void setTx_id(long tx_id) {
		this.tx_id = tx_id;
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
	public int getC_stock() {
		return c_stock;
	}
	public void setC_stock(int c_stock) {
		this.c_stock = c_stock;
	}
	public int getTx_type() {
		return tx_type;
	}
	public void setTx_type(int tx_type) {
		this.tx_type = tx_type;
	}
	public int getE_stock() {
		return e_stock;
	}
	public void setE_stock(int e_stock) {
		this.e_stock = e_stock;
	}
	public int getNcquantity() {
		return ncquantity;
	}
	public void setNcquantity(int ncquantity) {
		this.ncquantity = ncquantity;
	}
	public String getTx_amount() {
		return tx_amount;
	}
	public void setTx_amount(String tx_amount) {
		this.tx_amount = tx_amount;
	}

}
