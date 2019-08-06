package com.it.erpapp.framework.model.transactions.sales;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "PURCHASE_RETURN_DATA")
public class PurchaseReturnDO {
	@Id
	@GeneratedValue
	private long id;
	private String sid;
	private String inv_ref_no;
	private long inv_date;
	private long inv_ref_date;
	private long cvo_id;
	private String inv_amt;
	private String vbal_amount;
	private String dbal_amount;
	private String narration;
	private int clearance_status;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;

	@Transient
	private List<PurchaseReturnDetailsDO> detailsList = new ArrayList<PurchaseReturnDetailsDO>();
	
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
	public long getInv_ref_date() {
		return inv_ref_date;
	}
	public void setInv_ref_date(long inv_ref_date) {
		this.inv_ref_date = inv_ref_date;
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
	public List<PurchaseReturnDetailsDO> getDetailsList() {
		return detailsList;
	}
	public void setDetailsList(List<PurchaseReturnDetailsDO> detailsList) {
		this.detailsList = detailsList;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getVbal_amount() {
		return vbal_amount;
	}
	public void setVbal_amount(String vbal_amount) {
		this.vbal_amount = vbal_amount;
	}
	public int getClearance_status() {
		return clearance_status;
	}
	public void setClearance_status(int clearance_status) {
		this.clearance_status = clearance_status;
	}
	public String getDbal_amount() {
		return dbal_amount;
	}
	public void setDbal_amount(String dbal_amount) {
		this.dbal_amount = dbal_amount;
	}
}