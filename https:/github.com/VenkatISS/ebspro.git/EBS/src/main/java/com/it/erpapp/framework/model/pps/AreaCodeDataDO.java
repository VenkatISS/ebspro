package com.it.erpapp.framework.model.pps;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AREA_CODE_DATA")
public class AreaCodeDataDO {

	@Id
	@GeneratedValue
	private long id;
	private String area_code;
	private String area_name;
	private String transport_charges;
	private int one_way_dist;
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

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getTransport_charges() {
		return transport_charges;
	}

	public void setTransport_charges(String transport_charges) {
		this.transport_charges = transport_charges;
	}

	public int getOne_way_dist() {
		return one_way_dist;
	}

	public void setOne_way_dist(int one_way_dist) {
		this.one_way_dist = one_way_dist;
	}

	public long getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(long effective_date) {
		this.effective_date = effective_date;
	}

	
}
