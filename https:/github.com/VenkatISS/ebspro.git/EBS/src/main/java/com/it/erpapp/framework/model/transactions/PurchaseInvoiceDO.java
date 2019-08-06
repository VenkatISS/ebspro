package com.it.erpapp.framework.model.transactions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CYLD_PURCHASE_INVS")
public class PurchaseInvoiceDO {

	@Id
	@GeneratedValue
	private long id;
	private long omc_id;
	private String inv_ref_no;
	private long inv_date;
	private long stk_recvd_date;
	private int prod_code;
	private int emr_or_delv;
	private String unit_price;
	private int quantity;
	private int cs_fulls;
	private int cs_emptys;
	private int ds_fulls;
	private int ds_emptys;
	private String gstp;
	private String other_charges;
	private String basic_amount;
	private String igst_amount;
	private String sgst_amount;
	private String cgst_amount;
	private String c_amount;
	private String vbal_amount;
	private int payment_status;
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
	public long getStk_recvd_date() {
		return stk_recvd_date;
	}
	public void setStk_recvd_date(long stk_recvd_date) {
		this.stk_recvd_date = stk_recvd_date;
	}
	public int getProd_code() {
		return prod_code;
	}
	public void setProd_code(int prod_code) {
		this.prod_code = prod_code;
	}
	public int getEmr_or_delv() {
		return emr_or_delv;
	}
	public void setEmr_or_delv(int emr_or_delv) {
		this.emr_or_delv = emr_or_delv;
	}
	public String getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(String unit_price) {
		this.unit_price = unit_price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getOther_charges() {
		return other_charges;
	}
	public void setOther_charges(String other_charges) {
		this.other_charges = other_charges;
	}
	public String getBasic_amount() {
		return basic_amount;
	}
	public void setBasic_amount(String basic_amount) {
		this.basic_amount = basic_amount;
	}
	public String getC_amount() {
		return c_amount;
	}
	public void setC_amount(String c_amount) {
		this.c_amount = c_amount;
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
	public String getGstp() {
		return gstp;
	}
	public void setGstp(String gstp) {
		this.gstp = gstp;
	}
	public String getIgst_amount() {
		return igst_amount;
	}
	public void setIgst_amount(String igst_amount) {
		this.igst_amount = igst_amount;
	}
	public String getSgst_amount() {
		return sgst_amount;
	}
	public void setSgst_amount(String sgst_amount) {
		this.sgst_amount = sgst_amount;
	}
	public String getCgst_amount() {
		return cgst_amount;
	}
	public void setCgst_amount(String cgst_amount) {
		this.cgst_amount = cgst_amount;
	}
	public long getOmc_id() {
		return omc_id;
	}
	public void setOmc_id(long omc_id) {
		this.omc_id = omc_id;
	}
	public String getVbal_amount() {
		return vbal_amount;
	}
	public void setVbal_amount(String vbal_amount) {
		this.vbal_amount = vbal_amount;
	}
	public int getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(int payment_status) {
		this.payment_status = payment_status;
	}
	public int getDs_emptys() {
		return ds_emptys;
	}
	public void setDs_emptys(int ds_emptys) {
		this.ds_emptys = ds_emptys;
	}
	public int getDs_fulls() {
		return ds_fulls;
	}
	public void setDs_fulls(int ds_fulls) {
		this.ds_fulls = ds_fulls;
	}
}
