package com.it.erpapp.framework.model.transactions.others;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CREDIT_NOTES_DATA")
public class CreditNoteDO {
	@Id
	@GeneratedValue
	private long id;
	private long ref_no;
	private long note_date;
/*	private long ref_date;*/
	private int note_type;
	private long cvo_id;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;
	@Transient
	private List<CreditNoteDetailsDO> detailsList = new ArrayList<CreditNoteDetailsDO>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRef_no() {
		return ref_no;
	}
	public void setRef_no(long ref_no) {
		this.ref_no = ref_no;
	}
	public long getNote_date() {
		return note_date;
	}
	public void setNote_date(long note_date) {
		this.note_date = note_date;
	}
/*	public long getRef_date() {
		return ref_date;
	}
	public void setRef_date(long ref_date) {
		this.ref_date = ref_date;
	}	*/
	public int getNote_type() {
		return note_type;
	}
	public void setNote_type(int note_type) {
		this.note_type = note_type;
	}
	public long getCvo_id() {
		return cvo_id;
	}
	public void setCvo_id(long cvo_id) {
		this.cvo_id = cvo_id;
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
	public List<CreditNoteDetailsDO> getDetailsList() {
		return detailsList;
	}
	public void setDetailsList(List<CreditNoteDetailsDO> detailsList) {
		this.detailsList = detailsList;
	}

	
}
