package com.it.erpapp.framework.model.transactions.others;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PAYMENTS_DATA")
public class PaymentsDataDO {
	@Id
	@GeneratedValue
	private long id;
	private long sr_no;
	private long pymt_date;
	private long paid_to;
	private int tax_type;
	private String pymt_amount;
	private String vbal_amount;
	private String dbal_amount;
	private int payment_mode;
	private String instr_details;
	private long debited_bank;
	private String bank_charges;
	private long staff_id;
	private String narration;
	private int clearance_status;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;
	private int cust_ven;	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPymt_date() {
		return pymt_date;
	}
	public void setPymt_date(long pymt_date) {
		this.pymt_date = pymt_date;
	}
	public long getPaid_to() {
		return paid_to;
	}
	public void setPaid_to(long paid_to) {
		this.paid_to = paid_to;
	}
	public int getTax_type() {
		return tax_type;
	}
	public void setTax_type(int tax_type) {
		this.tax_type = tax_type;
	}	
	public String getPymt_amount() {
		return pymt_amount;
	}
	public void setPymt_amount(String pymt_amount) {
		this.pymt_amount = pymt_amount;
	}
	public int getPayment_mode() {
		return payment_mode;
	}
	public void setPayment_mode(int payment_mode) {
		this.payment_mode = payment_mode;
	}
	public String getInstr_details() {
		return instr_details;
	}
	public void setInstr_details(String instr_details) {
		this.instr_details = instr_details;
	}
	public long getDebited_bank() {
		return debited_bank;
	}
	public void setDebited_bank(long debited_bank) {
		this.debited_bank = debited_bank;
	}
	public String getBank_charges() {
		return bank_charges;
	}
	public void setBank_charges(String bank_charges) {
		this.bank_charges = bank_charges;
	}
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
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
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public String getVbal_amount() {
		return vbal_amount;
	}
	public void setVbal_amount(String vbal_amount) {
		this.vbal_amount = vbal_amount;
	}
	public int getClearance_status() {
		return clearance_status;
	}
	public void setClearance_status(int clearance_status) {
		this.clearance_status = clearance_status;
	}
	public long getSr_no() {
		return sr_no;
	}
	public void setSr_no(long sr_no) {
		this.sr_no = sr_no;
	}
	public String getDbal_amount() {
		return dbal_amount;
	}
	public void setDbal_amount(String dbal_amount) {
		this.dbal_amount = dbal_amount;
	}
	public int getCust_ven() {
		return cust_ven;
	}
	public void setCust_ven(int cust_ven) {
		this.cust_ven = cust_ven;
	}
}