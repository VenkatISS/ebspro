package com.it.erpapp.framework.model.pps;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SERVICES_GST")
public class ServicesGSTData {
	
	@Id
	@GeneratedValue
	private long id;
	private int service_product_id;
	private String gst_percent_applicable;
	private String gstp_applicable_amt;
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
	public int getService_product_id() {
		return service_product_id;
	}
	public void setService_product_id(int service_product_id) {
		this.service_product_id = service_product_id;
	}
	public String getGst_percent_applicable() {
		return gst_percent_applicable;
	}
	public void setGst_percent_applicable(String gst_percent_applicable) {
		this.gst_percent_applicable = gst_percent_applicable;
	}
	public String getGstp_applicable_amt() {
		return gstp_applicable_amt;
	}
	public void setGstp_applicable_amt(String gstp_applicable_amt) {
		this.gstp_applicable_amt = gstp_applicable_amt;
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