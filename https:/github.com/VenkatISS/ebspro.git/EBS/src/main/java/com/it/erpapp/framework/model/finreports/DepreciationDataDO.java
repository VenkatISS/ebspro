package com.it.erpapp.framework.model.finreports;

public class DepreciationDataDO {

	private long id;
	private int mh;
	private String basic_amount;
	private long inv_date;
	private int dtype;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getMh() {
		return mh;
	}
	public void setMh(int mh) {
		this.mh = mh;
	}
	public String getBasic_amount() {
		return basic_amount;
	}
	public void setBasic_amount(String basic_amount) {
		this.basic_amount = basic_amount;
	}
	public long getInv_date() {
		return inv_date;
	}
	public void setInv_date(long inv_date) {
		this.inv_date = inv_date;
	}
	public int getDtype() {
		return dtype;
	}
	public void setDtype(int dtype) {
		this.dtype = dtype;
	}
}
