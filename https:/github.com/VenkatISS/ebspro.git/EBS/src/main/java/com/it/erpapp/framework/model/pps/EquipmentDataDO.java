package com.it.erpapp.framework.model.pps;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EQUIPMENT_DATA")
public class EquipmentDataDO {

	@Id
	@GeneratedValue
	private long id;
	private int prod_code;
	private int units;
	private String vatp;
	private String gstp;
	private String csteh_no;
	private String security_deposit;
	private int os_fulls;
	private int os_emptys;
	private int cs_fulls;
	private int cs_emptys;
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

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public String getVatp() {
		return vatp;
	}

	public void setVatp(String vatp) {
		this.vatp = vatp;
	}

	public String getGstp() {
		return gstp;
	}

	public void setGstp(String gstp) {
		this.gstp = gstp;
	}

	public String getCsteh_no() {
		return csteh_no;
	}

	public void setCsteh_no(String csteh_no) {
		this.csteh_no = csteh_no;
	}

	public String getSecurity_deposit() {
		return security_deposit;
	}

	public void setSecurity_deposit(String security_deposit) {
		this.security_deposit = security_deposit;
	}

	public int getOs_fulls() {
		return os_fulls;
	}

	public void setOs_fulls(int os_fulls) {
		this.os_fulls = os_fulls;
	}

	public int getOs_emptys() {
		return os_emptys;
	}

	public void setOs_emptys(int os_emptys) {
		this.os_emptys = os_emptys;
	}

	public int getCs_fulls() {
		return cs_fulls;
	}

	public void setCs_fulls(int cs_fulls) {
		this.cs_fulls = cs_fulls;
	}

	public int getCs_emptys() {
		return cs_emptys;
	}

	public void setCs_emptys(int cs_emptys) {
		this.cs_emptys = cs_emptys;
	}

	public long getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(long effective_date) {
		this.effective_date = effective_date;
	}

	
}
