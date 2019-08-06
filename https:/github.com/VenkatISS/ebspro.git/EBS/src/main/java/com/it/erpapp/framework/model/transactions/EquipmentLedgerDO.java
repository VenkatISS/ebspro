package com.it.erpapp.framework.model.transactions;

public class EquipmentLedgerDO {

	private long id;
	private String invoiceId;
	private long tranxDate;
	private long tranxId;
	private int ttype;
	private int quantity;
	private long partyId; //Vendor or Customer or Other
	private int balance;
	private long createdDate;
	private float discount;
	
	
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTranxDate() {
		return tranxDate;
	}
	public void setTranxDate(long tranxDate) {
		this.tranxDate = tranxDate;
	}
	public int getTtype() {
		return ttype;
	}
	public void setTtype(int ttype) {
		this.ttype = ttype;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getPartyId() {
		return partyId;
	}
	public void setPartyId(long partyId) {
		this.partyId = partyId;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public long getTranxId() {
		return tranxId;
	}
	public void setTranxId(long tranxId) {
		this.tranxId = tranxId;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	
}
