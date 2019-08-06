package com.it.erpapp.framework.model.transactions.sales;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="QUOTATIONS")
public class QuotationsDO {

	@Id
	@GeneratedValue
	private long id;
	private String sr_no;
	private long qtn_date;
	private long customer_id;
	private String customer_name;
	private long staff_id;
	private String qtn_amount;
	private String foot_notes;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;
	@Transient
	private List<QuotationDetailsDO> quotationItemsList = new ArrayList<QuotationDetailsDO>();

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getQtn_date() {
		return qtn_date;
	}
	public void setQtn_date(long qtn_date) {
		this.qtn_date = qtn_date;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
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
	public String getQtn_amount() {
		return qtn_amount;
	}
	public void setQtn_amount(String qtn_amount) {
		this.qtn_amount = qtn_amount;
	}
	public List<QuotationDetailsDO> getQuotationItemsList() {
		return quotationItemsList;
	}
	public void setQuotationItemsList(List<QuotationDetailsDO> quotationItemsList) {
		this.quotationItemsList = quotationItemsList;
	}
	public String getSr_no() {
		return sr_no;
	}
	public void setSr_no(String sr_no) {
		this.sr_no = sr_no;
	}
	public String getFoot_notes() {
		return foot_notes;
	}
	public void setFoot_notes(String foot_notes) {
		this.foot_notes = foot_notes;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

}