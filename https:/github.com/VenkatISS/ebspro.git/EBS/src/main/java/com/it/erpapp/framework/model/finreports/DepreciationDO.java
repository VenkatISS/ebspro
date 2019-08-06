package com.it.erpapp.framework.model.finreports;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DEPRECIATION_REPORTS")
public class DepreciationDO {

	@Id
	@GeneratedValue
	private long id;
	private int fyv;
	private String o_amt;
	private String p_amt;
	private String c_amt;
	private String d_amt;
	private int status;
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
	public int getFyv() {
		return fyv;
	}
	public void setFyv(int fyv) {
		this.fyv = fyv;
	}
	public String getO_amt() {
		return o_amt;
	}
	public void setO_amt(String o_amt) {
		this.o_amt = o_amt;
	}
	public String getC_amt() {
		return c_amt;
	}
	public void setC_amt(String c_amt) {
		this.c_amt = c_amt;
	}
	public String getD_amt() {
		return d_amt;
	}
	public void setD_amt(String d_amt) {
		this.d_amt = d_amt;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public String getP_amt() {
		return p_amt;
	}
	public void setP_amt(String p_amt) {
		this.p_amt = p_amt;
	}
}
