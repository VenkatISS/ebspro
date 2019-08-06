package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CVO_DATA")
public class CVODataDO {

	@Id
	@GeneratedValue
	private long id;
	private int cvo_cat;
	private String cvo_name;
	private String cvo_address;
	private String cvo_contact;
	private int is_gst_reg;
	private String cvo_tin;
	private String cvo_email;
	private String cvo_pan;
	private String obal;
	private String cbal;
	private String ebal;
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

	public int getCvo_cat() {
		return cvo_cat;
	}

	public void setCvo_cat(int cvo_cat) {
		this.cvo_cat = cvo_cat;
	}

	public String getCvo_name() {
		return cvo_name;
	}

	public void setCvo_name(String cvo_name) {
		this.cvo_name = cvo_name;
	}

	public String getCvo_contact() {
		return cvo_contact;
	}

	public void setCvo_contact(String cvo_contact) {
		this.cvo_contact = cvo_contact;
	}

	public String getCvo_tin() {
		return cvo_tin;
	}

	public void setCvo_tin(String cvo_tin) {
		this.cvo_tin = cvo_tin;
	}

	public String getCvo_email() {
		return cvo_email;
	}

	public void setCvo_email(String cvo_email) {
		this.cvo_email = cvo_email;
	}

	public String getCvo_pan() {
		return cvo_pan;
	}

	public void setCvo_pan(String cvo_pan) {
		this.cvo_pan = cvo_pan;
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

	public String getCvo_address() {
		return cvo_address;
	}

	public void setCvo_address(String cvo_address) {
		this.cvo_address = cvo_address;
	}

	public int getIs_gst_reg() {
		return is_gst_reg;
	}

	public void setIs_gst_reg(int is_gst_reg) {
		this.is_gst_reg = is_gst_reg;
	}

	public String getObal() {
		return obal;
	}

	public void setObal(String obal) {
		this.obal = obal;
	}

	public String getCbal() {
		return cbal;
	}

	public void setCbal(String cbal) {
		this.cbal = cbal;
	}

	public String getEbal() {
		return ebal;
	}

	public void setEbal(String ebal) {
		this.ebal = ebal;
	}

	
}
