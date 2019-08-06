package com.it.erpapp.framework.model.transactions;

public class PartyLedgerDO {

	private long tranxDate;
	private long tranxId;
	private String displayId;
	private int tranxType;
	private int amtType;
	private String amount;
	private String balAmount;
	private long createdDate;
	private int paymentTerms;
	private String discount;
	
	
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public long getTranxDate() {
		return tranxDate;
	}
	public void setTranxDate(long tranxDate) {
		this.tranxDate = tranxDate;
	}
	public long getTranxId() {
		return tranxId;
	}
	public void setTranxId(long tranxId) {
		this.tranxId = tranxId;
	}
	public int getTranxType() {
		return tranxType;
	}
	public void setTranxType(int tranxType) {
		this.tranxType = tranxType;
	}
	public int getAmtType() {
		return amtType;
	}
	public void setAmtType(int amtType) {
		this.amtType = amtType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBalAmount() {
		return balAmount;
	}
	public void setBalAmount(String balAmount) {
		this.balAmount = balAmount;
	}
	public long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}
	public String getDisplayId() {
		return displayId;
	}
	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}
	public int getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(int paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

}
