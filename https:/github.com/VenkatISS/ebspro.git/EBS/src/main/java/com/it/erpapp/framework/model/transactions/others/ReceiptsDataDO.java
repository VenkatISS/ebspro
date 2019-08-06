package com.it.erpapp.framework.model.transactions.others;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RECEIPTS_DATA")
public class ReceiptsDataDO {
	@Id
	@GeneratedValue
	private long id;
	private long sr_no;
	private long rcp_date;
	private long rcvd_from;
	private String rcp_amount;
	private String cbal_amount;
	private String dbal_amount;
	private int payment_mode;
	private String instr_details;
	private long credited_bank;
	private long staff_id;
	private String narration;
	private int clearance_status;
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
	public long getRcp_date() {
		return rcp_date;
	}
	public void setRcp_date(long rcp_date) {
		this.rcp_date = rcp_date;
	}
	public long getRcvd_from() {
		return rcvd_from;
	}
	public void setRcvd_from(long rcvd_from) {
		this.rcvd_from = rcvd_from;
	}
	public String getRcp_amount() {
		return rcp_amount;
	}
	public void setRcp_amount(String rcp_amount) {
		this.rcp_amount = rcp_amount;
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
	public long getCredited_bank() {
		return credited_bank;
	}
	public void setCredited_bank(long credited_bank) {
		this.credited_bank = credited_bank;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public String getCbal_amount() {
		return cbal_amount;
	}
	public void setCbal_amount(String cbal_amount) {
		this.cbal_amount = cbal_amount;
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

}