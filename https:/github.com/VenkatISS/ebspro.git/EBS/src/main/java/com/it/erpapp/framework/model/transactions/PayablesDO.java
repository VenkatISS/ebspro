package com.it.erpapp.framework.model.transactions;

public class PayablesDO {

	private long id;
	private int invType;
	private int status;
	private String invAmount;
	private long createdOn;
	private long invDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getInvType() {
		return invType;
	}
	public void setInvType(int invType) {
		this.invType = invType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}
	public String getInvAmount() {
		return invAmount;
	}
	public void setInvAmount(String invAmount) {
		this.invAmount = invAmount;
	}
	public long getInvDate() {
		return invDate;
	}
	public void setInvDate(long invDate) {
		this.invDate = invDate;
	}

}
