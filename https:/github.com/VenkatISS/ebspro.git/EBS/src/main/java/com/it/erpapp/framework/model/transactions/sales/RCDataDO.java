package com.it.erpapp.framework.model.transactions.sales;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RC_DATA")
public class RCDataDO {

	@Id
	@GeneratedValue
	private long id;
	private String sr_no;
	private long rc_date;
	private String customer_no;
	private long staff_id;
	private String rc_amount;
	private int prod_code;
	private int no_of_cyl;
	private int no_of_reg;
	private int reg_stock;
	private int dreg_stock;
	private String cyl_deposit;
	private String reg_deposit;
	private String dgcc_amount;
	private int rc_charges_type;
	private String admin_charges;
	private String igst_amount;
	private String cgst_amount;
	private String sgst_amount;
	private String scgst_amount;
	private String ssgst_amount;
	private String payment_terms;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;
	
	private String op_basic_price;
	private String op_taxable_amt;	
	private String op_total_amt;
	private String op_igst_amt;
	private String op_sgst_amt;
	private String op_cgst_amt;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRc_date() {
		return rc_date;
	}
	public void setRc_date(long rc_date) {
		this.rc_date = rc_date;
	}
	public String getCustomer_no() {
		return customer_no;
	}
	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}
	public String getRc_amount() {
		return rc_amount;
	}
	public void setRc_amount(String rc_amount) {
		this.rc_amount = rc_amount;
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
	public String getDgcc_amount() {
		return dgcc_amount;
	}
	public void setDgcc_amount(String dgcc_amount) {
		this.dgcc_amount = dgcc_amount;
	}
	public String getAdmin_charges() {
		return admin_charges;
	}
	public void setAdmin_charges(String admin_charges) {
		this.admin_charges = admin_charges;
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
	public String getScgst_amount() {
		return scgst_amount;
	}
	public void setScgst_amount(String scgst_amount) {
		this.scgst_amount = scgst_amount;
	}
	public String getSsgst_amount() {
		return ssgst_amount;
	}
	public void setSsgst_amount(String ssgst_amount) {
		this.ssgst_amount = ssgst_amount;
	}
	public String getSr_no() {
		return sr_no;
	}
	public void setSr_no(String sr_no) {
		this.sr_no = sr_no;
	}
	public int getDreg_stock() {
		return dreg_stock;
	}
	public void setDreg_stock(int dreg_stock) {
		this.dreg_stock = dreg_stock;
	}
	public int getRc_charges_type() {
		return rc_charges_type;
	}
	public void setRc_charges_type(int rc_charges_type) {
		this.rc_charges_type = rc_charges_type;
	}
	public String getOp_basic_price() {
		return op_basic_price;
	}
	public void setOp_basic_price(String op_basic_price) {
		this.op_basic_price = op_basic_price;
	}
	public String getOp_taxable_amt() {
		return op_taxable_amt;
	}
	public void setOp_taxable_amt(String op_taxable_amt) {
		this.op_taxable_amt = op_taxable_amt;
	}
	public String getOp_total_amt() {
		return op_total_amt;
	}
	public void setOp_total_amt(String op_total_amt) {
		this.op_total_amt = op_total_amt;
	}
	public String getOp_igst_amt() {
		return op_igst_amt;
	}
	public void setOp_igst_amt(String op_igst_amt) {
		this.op_igst_amt = op_igst_amt;
	}
	public String getOp_sgst_amt() {
		return op_sgst_amt;
	}
	public void setOp_sgst_amt(String op_sgst_amt) {
		this.op_sgst_amt = op_sgst_amt;
	}
	public String getOp_cgst_amt() {
		return op_cgst_amt;
	}
	public void setOp_cgst_amt(String op_cgst_amt) {
		this.op_cgst_amt = op_cgst_amt;
	}

}