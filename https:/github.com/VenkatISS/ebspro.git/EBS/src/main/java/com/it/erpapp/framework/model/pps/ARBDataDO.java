package com.it.erpapp.framework.model.pps;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ARB_DATA")
public class ARBDataDO {

	@Id
	@GeneratedValue
	private long id;
	private int prod_code;
	private String prod_brand;
	private String prod_name;
	private int units;
	private String vatp;
	private String gstp;
	private String csteh_no;
	private String prod_mrp;
	private String prod_offer_price;
	private int opening_stock;
	private int current_stock;
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

	public String getProd_brand() {
		return prod_brand;
	}

	public void setProd_brand(String prod_brand) {
		this.prod_brand = prod_brand;
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

	public String getProd_mrp() {
		return prod_mrp;
	}

	public void setProd_mrp(String prod_mrp) {
		this.prod_mrp = prod_mrp;
	}

	public String getProd_offer_price() {
		return prod_offer_price;
	}

	public void setProd_offer_price(String prod_offer_price) {
		this.prod_offer_price = prod_offer_price;
	}

	public int getOpening_stock() {
		return opening_stock;
	}

	public void setOpening_stock(int opening_stock) {
		this.opening_stock = opening_stock;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public long getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(long effective_date) {
		this.effective_date = effective_date;
	}

	public int getCurrent_stock() {
		return current_stock;
	}

	public void setCurrent_stock(int current_stock) {
		this.current_stock = current_stock;
	}
	

}
