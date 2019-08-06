package com.it.erpapp.framework.model.transactions.others;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BANK_TRANS_DATA")
public class BankTranxsDataDO {
	@Id
	@GeneratedValue
	private long id;
	private long sr_no;
	private long ref_id;
	private long bt_date;
	private long bank_id;
	private String trans_amount;
	private String trans_charges;
	private int trans_type;
	private int trans_mode;
	private String instr_details;
	private int acc_head;
	private String bank_acb;
	private String dbank_acb;
//	private String dsbank_acb;
	private String narration;
	private int btflag;
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
	public long getBt_date() {
		return bt_date;
	}
	public void setBt_date(long bt_date) {
		this.bt_date = bt_date;
	}
	public long getBank_id() {
		return bank_id;
	}
	public void setBank_id(long bank_id) {
		this.bank_id = bank_id;
	}
	public String getTrans_amount() {
		return trans_amount;
	}
	public void setTrans_amount(String trans_amount) {
		this.trans_amount = trans_amount;
	}
	public String getTrans_charges() {
		return trans_charges;
	}
	public void setTrans_charges(String trans_charges) {
		this.trans_charges = trans_charges;
	}
	public int getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(int trans_type) {
		this.trans_type = trans_type;
	}
	public int getTrans_mode() {
		return trans_mode;
	}
	public void setTrans_mode(int trans_mode) {
		this.trans_mode = trans_mode;
	}
	public String getInstr_details() {
		return instr_details;
	}
	public void setInstr_details(String instr_details) {
		this.instr_details = instr_details;
	}
	public int getAcc_head() {
		return acc_head;
	}
	public void setAcc_head(int acc_head) {
		this.acc_head = acc_head;
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
	public String getBank_acb() {
		return bank_acb;
	}
	public void setBank_acb(String bank_acb) {
		this.bank_acb = bank_acb;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public long getSr_no() {
		return sr_no;
	}
	public void setSr_no(long sr_no) {
		this.sr_no = sr_no;
	}
	public String getDbank_acb() {
		return dbank_acb;
	}
	public void setDbank_acb(String dbank_acb) {
		this.dbank_acb = dbank_acb;
	}
	public int getBtflag() {
		return btflag;
	}
	public void setBtflag(int btflag) {
		this.btflag = btflag;
	}
	public long getRef_id() {
		return ref_id;
	}
	public void setRef_id(long ref_id) {
		this.ref_id = ref_id;
	}
}