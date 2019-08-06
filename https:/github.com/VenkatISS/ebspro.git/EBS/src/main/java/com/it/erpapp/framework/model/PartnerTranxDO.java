package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AGENCYS_PARTNERS_TRANXS")
public class PartnerTranxDO {

	@Id
	@GeneratedValue
	private long id;
	private long partner_id;
	private long bank_id;
	private long tranx_date;
	private int tranx_type;
	private String tranx_amount;
	private String remarks;
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
	public long getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(long partner_id) {
		this.partner_id = partner_id;
	}
	public long getBank_id() {
		return bank_id;
	}
	public void setBank_id(long bank_id) {
		this.bank_id = bank_id;
	}
	public long getTranx_date() {
		return tranx_date;
	}
	public void setTranx_date(long tranx_date) {
		this.tranx_date = tranx_date;
	}
	public int getTranx_type() {
		return tranx_type;
	}
	public void setTranx_type(int tranx_type) {
		this.tranx_type = tranx_type;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getTranx_amount() {
		return tranx_amount;
	}
	public void setTranx_amount(String tranx_amount) {
		this.tranx_amount = tranx_amount;
	}

}
