package com.it.erpapp.framework.model.transactions.sales;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SALES_RETURN_DATA")
public class SalesReturnDO {
	@Id
	@GeneratedValue
	private long id;
	private long sr_no;
	private String inv_ref_no;
	private long inv_date;
	private long cvo_id;
	private String inv_amt;
	private String cbal_amt;
	private String dbal_amt;
	private int payment_terms;
	private String narration;
	private int clearance_status;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;
	@Transient
	private List<SalesReturnDetailsDO> detailsList = new ArrayList<SalesReturnDetailsDO>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getInv_ref_no() {
		return inv_ref_no;
	}
	public void setInv_ref_no(String inv_ref_no) {
		this.inv_ref_no = inv_ref_no;
	}
	public long getInv_date() {
		return inv_date;
	}
	public void setInv_date(long inv_date) {
		this.inv_date = inv_date;
	}
	public long getCvo_id() {
		return cvo_id;
	}
	public void setCvo_id(long cvo_id) {
		this.cvo_id = cvo_id;
	}
	public String getInv_amt() {
		return inv_amt;
	}
	public void setInv_amt(String inv_amt) {
		this.inv_amt = inv_amt;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
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
	public List<SalesReturnDetailsDO> getDetailsList() {
		return detailsList;
	}
	public void setDetailsList(List<SalesReturnDetailsDO> detailsList) {
		this.detailsList = detailsList;
	}
	public String getCbal_amt() {
		return cbal_amt;
	}
	public void setCbal_amt(String cbal_amt) {
		this.cbal_amt = cbal_amt;
	}
	public int getClearance_status() {
		return clearance_status;
	}
	public void setClearance_status(int clearance_status) {
		this.clearance_status = clearance_status;
	}
	public long getSr_no() {
		return sr_no;
	}
	public void setSr_no(long sr_no) {
		this.sr_no = sr_no;
	}
	public String getDbal_amt() {
		return dbal_amt;
	}
	public void setDbal_amt(String dbal_amt) {
		this.dbal_amt = dbal_amt;
	}
	public int getPayment_terms() {
		return payment_terms;
	}
	public void setPayment_terms(int payment_terms) {
		this.payment_terms = payment_terms;
	}
}