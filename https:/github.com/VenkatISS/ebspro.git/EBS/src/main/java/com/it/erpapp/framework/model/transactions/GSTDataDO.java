package com.it.erpapp.framework.model.transactions;

import java.math.BigDecimal;

public class GSTDataDO {

	private String rate;
	private String gstType;
	private int ttype;
	private BigDecimal igst;
	private BigDecimal cgst;
	private BigDecimal sgst;
	private BigDecimal taxable_value;
	
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public int getTtype() {
		return ttype;
	}
	public void setTtype(int ttype) {
		this.ttype = ttype;
	}
	public BigDecimal getIgst() {
		return igst;
	}
	public void setIgst(BigDecimal igst) {
		this.igst = igst;
	}
	public BigDecimal getCgst() {
		return cgst;
	}
	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}
	public BigDecimal getSgst() {
		return sgst;
	}
	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}
	public String getGstType() {
		return gstType;
	}
	public void setGstType(String gstType) {
		this.gstType = gstType;
	}
	public BigDecimal getTaxable_value() {
		return taxable_value;
	}
	public void setTaxable_value(BigDecimal taxable_value) {
		this.taxable_value = taxable_value;
	}
	
}