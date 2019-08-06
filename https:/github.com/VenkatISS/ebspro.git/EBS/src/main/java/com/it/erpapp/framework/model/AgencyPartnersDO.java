package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AGENCYS_PARTNERS")
public class AgencyPartnersDO {

	@Id
	@GeneratedValue
	private long id;
	private String partner_fname;
	private String partner_mname;
	private String partner_lname;
	private String email_id;
	private String partner_mobile;
	private String opening_balance;
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
	public String getPartner_fname() {
		return partner_fname;
	}
	public void setPartner_fname(String partner_fname) {
		this.partner_fname = partner_fname;
	}
	public String getPartner_mname() {
		return partner_mname;
	}
	public void setPartner_mname(String partner_mname) {
		this.partner_mname = partner_mname;
	}
	public String getPartner_lname() {
		return partner_lname;
	}
	public void setPartner_lname(String partner_lname) {
		this.partner_lname = partner_lname;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getPartner_mobile() {
		return partner_mobile;
	}
	public void setPartner_mobile(String partner_mobile) {
		this.partner_mobile = partner_mobile;
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
	public String getOpening_balance() {
		return opening_balance;
	}
	public void setOpening_balance(String opening_balance) {
		this.opening_balance = opening_balance;
	}
	
}
