package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AGENCY_CASH_DATA")
public class AgencyCashDataDO {
	
	@Id
	@GeneratedValue
	private long id;
	private long t_date;
	private String inv_no;
	private String cash_amount;
	private int trans_type;
	private String cash_balance;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;
	private String discount;
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getT_date() {
		return t_date;
	}
	public void setT_date(long t_date) {
		this.t_date = t_date;
	}
	public String getInv_no() {
		return inv_no;
	}
	public void setInv_no(String inv_no) {
		this.inv_no = inv_no;
	}
	public String getCash_amount() {
		return cash_amount;
	}
	public void setCash_amount(String cash_amount) {
		this.cash_amount = cash_amount;
	}
	public int getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(int trans_type) {
		this.trans_type = trans_type;
	}
	public String getCash_balance() {
		return cash_balance;
	}
	public void setCash_balance(String cash_balance) {
		this.cash_balance = cash_balance;
	}
	public long getCreated_by() {
		return created_by;
	}
	public void setCreated_by(long created_by) {
		this.created_by = created_by;
	}
	
	

}
