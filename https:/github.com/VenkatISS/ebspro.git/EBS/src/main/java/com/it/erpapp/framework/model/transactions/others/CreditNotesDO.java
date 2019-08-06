package com.it.erpapp.framework.model.transactions.others;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CREDIT_NOTES")
public class CreditNotesDO {
	@Id
	@GeneratedValue
	private long id;
	private String sid;
	private String ref_no;
	private long ref_date;
	private long note_date;
	private int credit_note_type;
	private long cvo_id;
	private long prod_code;
	private String gstp;
	private String taxable_amount;
	private String igst_amount;
	private String cgst_amount;
	private String sgst_amount;
	private String credit_amount;
	private String cbal_amount;
	private String dbal_amount;
	private long bank_id;
	private int nreasons;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRef_no() {
		return ref_no;
	}
	public void setRef_no(String ref_no) {
		this.ref_no = ref_no;
	}
	public long getNote_date() {
		return note_date;
	}
	public void setNote_date(long note_date) {
		this.note_date = note_date;
	}
	public int getCredit_note_type() {
		return credit_note_type;
	}
	public void setCredit_note_type(int credit_note_type) {
		this.credit_note_type = credit_note_type;
	}
	public long getCvo_id() {
		return cvo_id;
	}
	public void setCvo_id(long cvo_id) {
		this.cvo_id = cvo_id;
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
	public long getCreated_by() {
		return created_by;
	}
	public void setCreated_by(long created_by) {
		this.created_by = created_by;
	}
	public long getCreated_date() {
		return created_date;
	}
	public void setCreated_date(long created_date) {
		this.created_date = created_date;
	}
	public long getModified_by() {
		return modified_by;
	}
	public void setModified_by(long modified_by) {
		this.modified_by = modified_by;
	}
	public long getModified_date() {
		return modified_date;
	}
	public void setModified_date(long modified_date) {
		this.modified_date = modified_date;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String string) {
		this.sid = string;
	}
	public String getCbal_amount() {
		return cbal_amount;
	}
	public void setCbal_amount(String cbal_amount) {
		this.cbal_amount = cbal_amount;
	}
	public String getDbal_amount() {
		return dbal_amount;
	}
	public void setDbal_amount(String dbal_amount) {
		this.dbal_amount = dbal_amount;
	}
	public long getRef_date() {
		return ref_date;
	}
	public void setRef_date(long ref_date) {
		this.ref_date = ref_date;
	}
	
}