package com.it.erpapp.framework.model.transactions;

public class GSTR1CDNDataDO {

	private String id;
	private long note_date;
	private int note_type;
	private long cvo_id;
	private String ref_no;
	private int nreasons;
	private String doc_type;
	private String credit_or_debit_amount;
	private String gstp;
	private String taxable_amount;
	private long  receipt_date;
	public String getId() {
		return id;
	}
	public void setId(String string) {
		this.id = string;
	}
	public long getNote_date() {
		return note_date;
	}
	public void setNote_date(long note_date) {
		this.note_date = note_date;
	}
	
	public long getCvo_id() {
		return cvo_id;
	}
	public void setCvo_id(long cvo_id) {
		this.cvo_id = cvo_id;
	}
	public String getRef_no() {
		return ref_no;
	}
	public void setRef_no(String ref_no) {
		this.ref_no = ref_no;
	}
	public int getNreasons() {
		return nreasons;
	}
	public void setNreasons(int nreasons) {
		this.nreasons = nreasons;
	}
	
	public String getGstp() {
		return gstp;
	}
	public void setGstp(String gstp) {
		this.gstp = gstp;
	}
	public String getTaxable_amount() {
		return taxable_amount;
	}
	public void setTaxable_amount(String taxable_amount) {
		this.taxable_amount = taxable_amount;
	}
	public String getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}
	public int getNote_type() {
		return note_type;
	}
	public void setNote_type(int note_type) {
		this.note_type = note_type;
	}
	public String getCredit_or_debit_amount() {
		return credit_or_debit_amount;
	}
	public void setCredit_or_debit_amount(String credit_or_debit_amount) {
		this.credit_or_debit_amount = credit_or_debit_amount;
	}
	public long getReceipt_date() {
		return receipt_date;
	}
	public void setReceipt_date(long receipt_date) {
		this.receipt_date = receipt_date;
	}
	
}
