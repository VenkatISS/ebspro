package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CREDIT_LIMITS_DATA")
public class CreditLimitsDataDO {

	@Id
	@GeneratedValue
	private long id;
	private long cust_id;
	private String credit_limit;
	private int credit_days;
	private int cc_type;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;
	
	private String disc_19kg_ndne;
	private String disc_19kg_cutting_gas;
	private String disc_35kg_ndne;
	private String disc_47_5kg_ndne;
	private String disc_450kg_sumo;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCust_id() {
		return cust_id;
	}
	public void setCust_id(long cust_id) {
		this.cust_id = cust_id;
	}
	public String getCredit_limit() {
		return credit_limit;
	}
	public void setCredit_limit(String credit_limit) {
		this.credit_limit = credit_limit;
	}
	public int getCredit_days() {
		return credit_days;
	}
	public void setCredit_days(int credit_days) {
		this.credit_days = credit_days;
	}
	public int getCc_type() {
		return cc_type;
	}
	public void setCc_type(int cc_type) {
		this.cc_type = cc_type;
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
	public String getDisc_19kg_ndne() {
		return disc_19kg_ndne;
	}
	public void setDisc_19kg_ndne(String disc_19kg_ndne) {
		this.disc_19kg_ndne = disc_19kg_ndne;
	}
	public String getDisc_19kg_cutting_gas() {
		return disc_19kg_cutting_gas;
	}
	public void setDisc_19kg_cutting_gas(String disc_19kg_cutting_gas) {
		this.disc_19kg_cutting_gas = disc_19kg_cutting_gas;
	}
	public String getDisc_35kg_ndne() {
		return disc_35kg_ndne;
	}
	public void setDisc_35kg_ndne(String disc_35kg_ndne) {
		this.disc_35kg_ndne = disc_35kg_ndne;
	}
	public String getDisc_47_5kg_ndne() {
		return disc_47_5kg_ndne;
	}
	public void setDisc_47_5kg_ndne(String disc_47_5kg_ndne) {
		this.disc_47_5kg_ndne = disc_47_5kg_ndne;
	}
	public String getDisc_450kg_sumo() {
		return disc_450kg_sumo;
	}
	public void setDisc_450kg_sumo(String disc_450kg_sumo) {
		this.disc_450kg_sumo = disc_450kg_sumo;
	}

	
	
	
}
