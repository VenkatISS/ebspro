package com.it.erpapp.framework.model.transactions.others;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CREDIT_NOTES_DETAILS")
public class CreditNoteDetailsDO {
	@Id
	@GeneratedValue
	private long id;
	private long note_ref_id;
	private long prod_code;
	private String gstp;
	private String taxable_amount;
	private String igst_amount;
	private String cgst_amount;
	private String sgst_amount;
	private String credit_amount;
	private long bank_id;
	private int nreasons;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNote_ref_id() {
		return note_ref_id;
	}
	public void setNote_ref_id(long note_ref_id) {
		this.note_ref_id = note_ref_id;
	}
	public long getProd_code() {
		return prod_code;
	}
	public void setProd_code(long prod_code) {
		this.prod_code = prod_code;
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
	public String getIgst_amount() {
		return igst_amount;
	}
	public void setIgst_amount(String igst_amount) {
		this.igst_amount = igst_amount;
	}
	public String getCgst_amount() {
		return cgst_amount;
	}
	public void setCgst_amount(String cgst_amount) {
		this.cgst_amount = cgst_amount;
	}
	public String getSgst_amount() {
		return sgst_amount;
	}
	public void setSgst_amount(String sgst_amount) {
		this.sgst_amount = sgst_amount;
	}
	public String getCredit_amount() {
		return credit_amount;
	}
	public void setCredit_amount(String credit_amount) {
		this.credit_amount = credit_amount;
	}
	public long getBank_id() {
		return bank_id;
	}
	public void setBank_id(long bank_id) {
		this.bank_id = bank_id;
	}
	public int getNreasons() {
		return nreasons;
	}
	public void setNreasons(int nreasons) {
		this.nreasons = nreasons;
	}
	
}
