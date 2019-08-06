package com.it.erpapp.framework.model.finreports;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PA_REPORTS")
public class PAReportDO {

	@Id
	@GeneratedValue
	private long id;
	private int msv;
	private int ysv;
	private String gross_profit;
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
	public int getMsv() {
		return msv;
	}
	public void setMsv(int msv) {
		this.msv = msv;
	}
	public int getYsv() {
		return ysv;
	}
	public void setYsv(int ysv) {
		this.ysv = ysv;
	}
	public String getGross_profit() {
		return gross_profit;
	}
	public void setGross_profit(String gross_profit) {
		this.gross_profit = gross_profit;
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

	
}
