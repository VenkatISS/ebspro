package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CVO_BALANCE_DATA")
public class AgencyCVOBalanceDataDO {

	@Id
	@GeneratedValue
	private long id;
	private long ref_id;
	private int cvoflag;
	private String inv_ref_no;
	private long inv_date;
	private int trans_type;
	private int cvo_cat;
	private long cvo_refid;
	private String amount;
	private String cbal_amount;
	private String discount;
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
	public long getRef_id() {
		return ref_id;
	}
	public void setRef_id(long ref_id) {
		this.ref_id = ref_id;
	}
	public int getCvoflag() {
		return cvoflag;
	}
	public void setCvoflag(int cvoflag) {
		this.cvoflag = cvoflag;
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
	public int getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(int trans_type) {
		this.trans_type = trans_type;
	}
	public int getCvo_cat() {
		return cvo_cat;
	}
	public void setCvo_cat(int cvo_cat) {
		this.cvo_cat = cvo_cat;
	}
	public long getCvo_refid() {
		return cvo_refid;
	}
	public void setCvo_refid(long cvo_refid) {
		this.cvo_refid = cvo_refid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCbal_amount() {
		return cbal_amount;
	}
	public void setCbal_amount(String cbal_amount) {
		this.cbal_amount = cbal_amount;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
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
