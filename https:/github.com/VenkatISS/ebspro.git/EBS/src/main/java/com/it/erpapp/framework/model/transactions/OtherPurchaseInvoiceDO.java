package com.it.erpapp.framework.model.transactions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OTHER_PURCHASE_INVS")
public class OtherPurchaseInvoiceDO {

	@Id
	@GeneratedValue
	private long id;
	private String inv_ref_no;
	private long inv_date;
	private long vendor_id;
	private int payment_status;
	private int reverse_charge;
	private String pname;
	private int taxable;
	private String hsn_code;
	private String gstp;
	private int mh;
	private int ch;
	private int ah;
	private int quantity;
	private int uom;
	private String unit_rate;
	private String basic_amount;
	private String igst_amount;
	private String sgst_amount;
	private String cgst_amount;
	private String p_amount;
	private String vbal_amount;
	private String dbal_amount;
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
	public long getVendor_id() {
		return vendor_id;
	}
	public void setVendor_id(long vendor_id) {
		this.vendor_id = vendor_id;
	}
	public int getReverse_charge() {
		return reverse_charge;
	}
	public void setReverse_charge(int reverse_charge) {
		this.reverse_charge = reverse_charge;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getTaxable() {
		return taxable;
	}
	public void setTaxable(int taxable) {
		this.taxable = taxable;
	}
	public String getHsn_code() {
		return hsn_code;
	}
	public void setHsn_code(String hsn_code) {
		this.hsn_code = hsn_code;
	}
	public String getGstp() {
		return gstp;
	}
	public void setGstp(String gstp) {
		this.gstp = gstp;
	}
	public int getMh() {
		return mh;
	}
	public void setMh(int mh) {
		this.mh = mh;
	}
	public int getAh() {
		return ah;
	}
	public void setAh(int ah) {
		this.ah = ah;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getUom() {
		return uom;
	}
	public void setUom(int uom) {
		this.uom = uom;
	}
	public String getUnit_rate() {
		return unit_rate;
	}
	public void setUnit_rate(String unit_rate) {
		this.unit_rate = unit_rate;
	}
	public String getBasic_amount() {
		return basic_amount;
	}
	public void setBasic_amount(String basic_amount) {
		this.basic_amount = basic_amount;
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
	public String getP_amount() {
		return p_amount;
	}
	public void setP_amount(String p_amount) {
		this.p_amount = p_amount;
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
	public int getCh() {
		return ch;
	}
	public void setCh(int ch) {
		this.ch = ch;
	}
	public String getDbal_amount() {
		return dbal_amount;
	}
	public void setDbal_amount(String dbal_amount) {
		this.dbal_amount = dbal_amount;
	}
}
