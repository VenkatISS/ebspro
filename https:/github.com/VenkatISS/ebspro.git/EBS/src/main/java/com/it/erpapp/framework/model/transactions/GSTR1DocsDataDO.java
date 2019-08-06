package com.it.erpapp.framework.model.transactions;

public class GSTR1DocsDataDO {
	private String sr_no_from;
	private String sr_no_to;
	private long total_no;
	private long cancelled;
	private int doc_type;
	
	public String getSr_no_from() {
		return sr_no_from;
	}
	public void setSr_no_from(String sr_no_from) {
		this.sr_no_from = sr_no_from;
	}
	public String getSr_no_to() {
		return sr_no_to;
	}
	public void setSr_no_to(String sr_no_to) {
		this.sr_no_to = sr_no_to;
	}
	public long getTotal_no() {
		return total_no;
	}
	public void setTotal_no(long total_no) {
		this.total_no = total_no;
	}
	public long getCancelled() {
		return cancelled;
	}
	public void setCancelled(long cancelled) {
		this.cancelled = cancelled;
	}
	public int getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(int doc_type) {
		this.doc_type = doc_type;
	}

}
