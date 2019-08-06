package com.it.erpapp.framework.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="STATUTORY_DATA")
public class StatutoryDataDO {

	@Id
	@GeneratedValue
	private long id;
	private int item_type;
	private String item_ref_no;
	private Date valid_upto;
	private int remind_before;
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
	public int getItem_type() {
		return item_type;
	}
	public void setItem_type(int item_type) {
		this.item_type = item_type;
	}
	public String getItem_ref_no() {
		return item_ref_no;
	}
	public void setItem_ref_no(String item_ref_no) {
		this.item_ref_no = item_ref_no;
	}
	public Date getValid_upto() {
		return valid_upto;
	}
	public void setValid_upto(Date valid_upto) {
		this.valid_upto = valid_upto;
	}
	public int getRemind_before() {
		return remind_before;
	}
	public void setRemind_before(int remind_before) {
		this.remind_before = remind_before;
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
	
	
	
}
