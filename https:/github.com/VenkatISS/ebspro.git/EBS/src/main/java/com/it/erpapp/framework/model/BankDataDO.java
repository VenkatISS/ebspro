package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BANK_DATA")
public class BankDataDO {

	@Id
	@GeneratedValue
	private long id;
	private String bank_code;
	private String bank_name;
	private String bank_acc_no;
	private String bank_branch;
	private String bank_ifsc_code;
	private String acc_ob;
	private String acc_cb;
	private String bank_addr;
	private String od_and_loan_acceptable_bal;
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

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_acc_no() {
		return bank_acc_no;
	}

	public void setBank_acc_no(String bank_acc_no) {
		this.bank_acc_no = bank_acc_no;
	}

	public String getBank_branch() {
		return bank_branch;
	}

	public void setBank_branch(String bank_branch) {
		this.bank_branch = bank_branch;
	}

	public String getBank_ifsc_code() {
		return bank_ifsc_code;
	}

	public void setBank_ifsc_code(String bank_ifsc_code) {
		this.bank_ifsc_code = bank_ifsc_code;
	}

	public String getAcc_ob() {
		return acc_ob;
	}

	public void setAcc_ob(String acc_ob) {
		this.acc_ob = acc_ob;
	}

	public String getBank_addr() {
		return bank_addr;
	}

	public void setBank_addr(String bank_addr) {
		this.bank_addr = bank_addr;
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

	public String getAcc_cb() {
		return acc_cb;
	}

	public void setAcc_cb(String acc_cb) {
		this.acc_cb = acc_cb;
	}

	public String getOd_and_loan_acceptable_bal() {
		return od_and_loan_acceptable_bal;
	}

	public void setOd_and_loan_acceptable_bal(String od_and_loan_acceptable_bal) {
		this.od_and_loan_acceptable_bal = od_and_loan_acceptable_bal;
	}

}
