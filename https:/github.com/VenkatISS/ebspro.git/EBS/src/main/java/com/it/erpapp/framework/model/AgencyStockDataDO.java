package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AGENCY_STOCK_DATA")
public class AgencyStockDataDO {

	@Id
	@GeneratedValue
	private long id;
	private long ref_id;
	private int stock_flag;
	private String inv_no;
	private long trans_date;
	private int trans_type;
	private int prod_code;
	private long prod_id;
	private int fulls;
	private int empties;
	private int cs_fulls; 
	private int cs_emptys;
	private long cvo_id;
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
	public String getInv_no() {
		return inv_no;
	}
	public void setInv_no(String inv_no) {
		this.inv_no = inv_no;
	}
	public long getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(long trans_date) {
		this.trans_date = trans_date;
	}
	public int getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(int trans_type) {
		this.trans_type = trans_type;
	}
	public int getProd_code() {
		return prod_code;
	}
	public void setProd_code(int prod_code) {
		this.prod_code = prod_code;
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
	public long getCvo_id() {
		return cvo_id;
	}
	public void setCvo_id(long cvo_id) {
		this.cvo_id = cvo_id;
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
	public long getProd_id() {
		return prod_id;
	}
	public void setProd_id(long prod_id) {
		this.prod_id = prod_id;
	}
	public long getRef_id() {
		return ref_id;
	}
	public void setRef_id(long ref_id) {
		this.ref_id = ref_id;
	}
	public int getStock_flag() {
		return stock_flag;
	}
	public void setStock_flag(int stock_flag) {
		this.stock_flag = stock_flag;
	}
	public int getFulls() {
		return fulls;
	}
	public void setFulls(int fulls) {
		this.fulls = fulls;
	}
	public int getEmpties() {
		return empties;
	}
	public void setEmpties(int empties) {
		this.empties = empties;
	}

}
