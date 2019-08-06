package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GST_PAYMENTS_DATA")
public class GSTPaymentsDataDO {
	
	@Id
	@GeneratedValue
	private long id;
	private int month;
	private int year;
	private long set_off_date;
	private long pay_off_date;
	private int status;
	private String creditors_igst;
	private String creditors_sgst;
	private String creditors_cgst;
	private String liability_igst;
	private String liability_sgst;
	private String liability_cgst;
	private String cash_sgst;
	private String cash_cgst;
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
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public long getSet_off_date() {
		return set_off_date;
	}
	public void setSet_off_date(long set_off_date) {
		this.set_off_date = set_off_date;
	}
	public long getPay_off_date() {
		return pay_off_date;
	}
	public void setPay_off_date(long pay_off_date) {
		this.pay_off_date = pay_off_date;
	}	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreditors_igst() {
		return creditors_igst;
	}
	public void setCreditors_igst(String creditors_igst) {
		this.creditors_igst = creditors_igst;
	}
	public String getCreditors_sgst() {
		return creditors_sgst;
	}
	public void setCreditors_sgst(String creditors_sgst) {
		this.creditors_sgst = creditors_sgst;
	}
	public String getCreditors_cgst() {
		return creditors_cgst;
	}
	public void setCreditors_cgst(String creditors_cgst) {
		this.creditors_cgst = creditors_cgst;
	}
	public String getLiability_igst() {
		return liability_igst;
	}
	public void setLiability_igst(String liability_igst) {
		this.liability_igst = liability_igst;
	}
	public String getLiability_sgst() {
		return liability_sgst;
	}
	public void setLiability_sgst(String liability_sgst) {
		this.liability_sgst = liability_sgst;
	}
	public String getLiability_cgst() {
		return liability_cgst;
	}
	public void setLiability_cgst(String liability_cgst) {
		this.liability_cgst = liability_cgst;
	}
	public String getCash_sgst() {
		return cash_sgst;
	}
	public void setCash_sgst(String cash_sgst) {
		this.cash_sgst = cash_sgst;
	}
	public String getCash_cgst() {
		return cash_cgst;
	}
	public void setCash_cgst(String cash_cgst) {
		this.cash_cgst = cash_cgst;
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

}
