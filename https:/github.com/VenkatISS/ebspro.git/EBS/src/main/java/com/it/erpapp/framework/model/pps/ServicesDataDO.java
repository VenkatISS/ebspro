package com.it.erpapp.framework.model.pps;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SERVICES_DATA")
public class ServicesDataDO {

	@Id
	@GeneratedValue
	private long id;
	private int prod_code;
	private String prod_charges;
	private String gst_amt;
	private String sac_code;
	private long effective_date;
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

	public int getProd_code() {
		return prod_code;
	}

	public void setProd_code(int prod_code) {
		this.prod_code = prod_code;
	}

	public String getProd_charges() {
		return prod_charges;
	}

	public void setProd_charges(String prod_charges) {
		this.prod_charges = prod_charges;
	}

	public long getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(long effective_date) {
		this.effective_date = effective_date;
	}

	public String getGst_amt() {
		return gst_amt;
	}

	public void setGst_amt(String gst_amt) {
		this.gst_amt = gst_amt;
	}

	public String getSac_code() {
		return sac_code;
	}

	public void setSac_code(String sac_code) {
		this.sac_code = sac_code;
	}

	
}
