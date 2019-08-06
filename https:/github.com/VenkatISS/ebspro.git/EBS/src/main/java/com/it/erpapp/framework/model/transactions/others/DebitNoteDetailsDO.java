package com.it.erpapp.framework.model.transactions.others;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEBIT_NOTES_DETAILS")
public class DebitNoteDetailsDO {
	@Id
	@GeneratedValue
	private long id;
	private long note_ref_id;
	private long prod_code;
	private String namount;
	private long bank_id;
	private String nnotes;
	private String nreasons;
	
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
	public String getNamount() {
		return namount;
	}
	public void setNamount(String namount) {
		this.namount = namount;
	}
	public long getBank_id() {
		return bank_id;
	}
	public void setBank_id(long bank_id) {
		this.bank_id = bank_id;
	}
	public String getNnotes() {
		return nnotes;
	}
	public void setNnotes(String nnotes) {
		this.nnotes = nnotes;
	}
	public String getNreasons() {
		return nreasons;
	}
	public void setNreasons(String nreasons) {
		this.nreasons = nreasons;
	}

	
}
