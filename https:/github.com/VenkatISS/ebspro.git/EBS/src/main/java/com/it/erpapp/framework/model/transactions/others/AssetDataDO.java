package com.it.erpapp.framework.model.transactions.others;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ASSETS_DATA")
public class AssetDataDO {

	@Id
	@GeneratedValue
	private long id;
	private long asset_tdate;
	private int asset_ttype;
	private String asset_desc;
	private int asset_ah;
	private int asset_mop;
	private long bank_id;
	private long staff_id;
	private String asset_value;
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
	public long getAsset_tdate() {
		return asset_tdate;
	}
	public void setAsset_tdate(long asset_tdate) {
		this.asset_tdate = asset_tdate;
	}
	public int getAsset_ttype() {
		return asset_ttype;
	}
	public void setAsset_ttype(int asset_ttype) {
		this.asset_ttype = asset_ttype;
	}
	public String getAsset_desc() {
		return asset_desc;
	}
	public void setAsset_desc(String asset_desc) {
		this.asset_desc = asset_desc;
	}
	public int getAsset_ah() {
		return asset_ah;
	}
	public void setAsset_ah(int asset_ah) {
		this.asset_ah = asset_ah;
	}
	public int getAsset_mop() {
		return asset_mop;
	}
	public void setAsset_mop(int asset_mop) {
		this.asset_mop = asset_mop;
	}
	public long getBank_id() {
		return bank_id;
	}
	public void setBank_id(long bank_id) {
		this.bank_id = bank_id;
	}
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}
	public String getAsset_value() {
		return asset_value;
	}
	public void setAsset_value(String asset_value) {
		this.asset_value = asset_value;
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
