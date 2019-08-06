package com.it.erpapp.framework.model.transactions.sales;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TV_DATA")
public class TVDataDO {

	@Id
	@GeneratedValue
	private long id;
	private String sr_no;
	private long tv_date;
	private String customer_no;
	private String gst_no;
	private long staff_id;
	private int tv_cat;
	private String tv_amount;
	private int prod_code;
	private int r_prod_code;
	private int no_of_cyl;
	private int no_of_reg;
	private int cs_fulls;
	private int cs_emptys;
	private int reg_stock;
	private int ds_fulls;
	private int ds_emptys;
	private int dreg_stock;
	private String cyl_deposit;
	private String reg_deposit;
	private String imp_amount;
	private int tv_charges_type;
	private String admin_charges;
	private String igst_amount;
	private String cgst_amount;
	private String sgst_amount;
	private String payment_terms;
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

	public long getTv_date() {
		return tv_date;
	}

	public void setTv_date(long tv_date) {
		this.tv_date = tv_date;
	}

	public String getCustomer_no() {
		return customer_no;
	}

	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}

	public String getGst_no() {
		return gst_no;
	}

	public void setGst_no(String gst_no) {
		this.gst_no = gst_no;
	}

	public long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}

	public String getTv_amount() {
		return tv_amount;
	}

	public void setTv_amount(String tv_amount) {
		this.tv_amount = tv_amount;
	}

	public int getProd_code() {
		return prod_code;
	}

	public void setProd_code(int prod_code) {
		this.prod_code = prod_code;
	}

	public int getNo_of_cyl() {
		return no_of_cyl;
	}

	public void setNo_of_cyl(int no_of_cyl) {
		this.no_of_cyl = no_of_cyl;
	}

	public String getCyl_deposit() {
		return cyl_deposit;
	}

	public void setCyl_deposit(String cyl_deposit) {
		this.cyl_deposit = cyl_deposit;
	}

	public String getReg_deposit() {
		return reg_deposit;
	}

	public void setReg_deposit(String reg_deposit) {
		this.reg_deposit = reg_deposit;
	}

	public String getAdmin_charges() {
		return admin_charges;
	}

	public void setAdmin_charges(String admin_charges) {
		this.admin_charges = admin_charges;
	}

	public String getPayment_terms() {
		return payment_terms;
	}

	public void setPayment_terms(String payment_terms) {
		this.payment_terms = payment_terms;
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

	public String getIgst_amount() {
		return igst_amount;
	}

	public void setIgst_amount(String igst_amount) {
		this.igst_amount = igst_amount;
	}

	public String getCgst_amount() {
		return cgst_amount;
	}

	public void setCgst_amount(String cgst_amount) {
		this.cgst_amount = cgst_amount;
	}

	public String getSgst_amount() {
		return sgst_amount;
	}

	public void setSgst_amount(String sgst_amount) {
		this.sgst_amount = sgst_amount;
	}

	public int getTv_cat() {
		return tv_cat;
	}

	public void setTv_cat(int tv_cat) {
		this.tv_cat = tv_cat;
	}

	public String getImp_amount() {
		return imp_amount;
	}

	public void setImp_amount(String imp_amount) {
		this.imp_amount = imp_amount;
	}

	public int getNo_of_reg() {
		return no_of_reg;
	}

	public void setNo_of_reg(int no_of_reg) {
		this.no_of_reg = no_of_reg;
	}

	public int getReg_stock() {
		return reg_stock;
	}

	public void setReg_stock(int reg_stock) {
		this.reg_stock = reg_stock;
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

	public int getR_prod_code() {
		return r_prod_code;
	}

	public void setR_prod_code(int r_prod_code) {
		this.r_prod_code = r_prod_code;
	}

	public String getSr_no() {
		return sr_no;
	}

	public void setSr_no(String sr_no) {
		this.sr_no = sr_no;
	}

	public int getDs_fulls() {
		return ds_fulls;
	}

	public void setDs_fulls(int ds_fulls) {
		this.ds_fulls = ds_fulls;
	}

	public int getDs_emptys() {
		return ds_emptys;
	}

	public void setDs_emptys(int ds_emptys) {
		this.ds_emptys = ds_emptys;
	}

	public int getDreg_stock() {
		return dreg_stock;
	}

	public void setDreg_stock(int dreg_stock) {
		this.dreg_stock = dreg_stock;
	}

	public int getTv_charges_type() {
		return tv_charges_type;
	}

	public void setTv_charges_type(int tv_charges_type) {
		this.tv_charges_type = tv_charges_type;
	}

}