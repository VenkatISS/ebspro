package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GST_PAYMENT_DETAILS")
public class GSTPaymentDetailsDO {
	@Id
	@GeneratedValue
	private long id;
	private long payment_id;
	private int month;
	private int year;
	private int pstatus;	
	private long pdate;
	private int tax_type;
	private String gst_amount;
	private String total_gst_amount;
	private String dt_gst_amount;
	private String ds_total_gst_amount;	
	private String final_gst_amount;
	private String incometax_amount;
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
	public long getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(long payment_id) {
		this.payment_id = payment_id;
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
	public int getPstatus() {
		return pstatus;
	}
	public void setPstatus(int pstatus) {
		this.pstatus = pstatus;
	}
	public long getPdate() {
		return pdate;
	}
	public void setPdate(long pdate) {
		this.pdate = pdate;
	}
	public int getTax_type() {
		return tax_type;
	}
	public void setTax_type(int tax_type) {
		this.tax_type = tax_type;
	}
	public String getGst_amount() {
		return gst_amount;
	}
	public void setGst_amount(String gst_amount) {
		this.gst_amount = gst_amount;
	}
	public String getTotal_gst_amount() {
		return total_gst_amount;
	}
	public void setTotal_gst_amount(String total_gst_amount) {
		this.total_gst_amount = total_gst_amount;
	}	
	public String getDt_gst_amount() {
		return dt_gst_amount;
	}
	public void setDt_gst_amount(String dt_gst_amount) {
		this.dt_gst_amount = dt_gst_amount;
	}
	public String getFinal_gst_amount() {
		return final_gst_amount;
	}
	public void setFinal_gst_amount(String final_gst_amount) {
		this.final_gst_amount = final_gst_amount;
	}	
	public String getIncometax_amount() {
		return incometax_amount;
	}
	public void setIncometax_amount(String incometax_amount) {
		this.incometax_amount = incometax_amount;
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
	public String getDs_total_gst_amount() {
		return ds_total_gst_amount;
	}
	public void setDs_total_gst_amount(String ds_total_gst_amount) {
		this.ds_total_gst_amount = ds_total_gst_amount;
	}

}