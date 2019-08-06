package com.it.erpapp.framework.model.transactions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="AGENCY_BOM")
public class AgencyBOMDO {

	@Id
	@GeneratedValue
	private long id;
	private String bom_name;
	private int bom_type;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;
	@Transient
	private List<BOMItemsDO> bomItemsSet = new ArrayList<BOMItemsDO>(0);
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBom_name() {
		return bom_name;
	}
	public void setBom_name(String bom_name) {
		this.bom_name = bom_name;
	}
	public int getBom_type() {
		return bom_type;
	}
	public void setBom_type(int bom_type) {
		this.bom_type = bom_type;
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
	public List<BOMItemsDO> getBomItemsSet() {
		return bomItemsSet;
	}
	public void setBomItemsSet(List<BOMItemsDO> bomItemsSet) {
		this.bomItemsSet = bomItemsSet;
	}

}
