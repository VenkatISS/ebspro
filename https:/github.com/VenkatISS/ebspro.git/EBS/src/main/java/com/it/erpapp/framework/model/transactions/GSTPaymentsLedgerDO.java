package com.it.erpapp.framework.model.transactions;

public class GSTPaymentsLedgerDO {
	private long tranxDate;
	private long tranxId;
	private int tranxType;
	private int pStatus;
	private int taxType;
	private String gstAmount;
	private String balGSTAmount;
	private long createdDate;

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
	public int getpStatus() {
		return pStatus;
	}
	public void setpStatus(int pStatus) {
		this.pStatus = pStatus;
	}
	public int getTaxType() {
		return taxType;
	}
	public void setTaxType(int taxType) {
		this.taxType = taxType;
	}
	public String getGstAmount() {
		return gstAmount;
	}
	public void setGstAmount(String gstAmount) {
		this.gstAmount = gstAmount;
	}
	public String getBalGSTAmount() {
		return balGSTAmount;
	}
	public void setBalGSTAmount(String balGSTAmount) {
		this.balGSTAmount = balGSTAmount;
	}
	public long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}
}
