package com.it.erpapp.framework.model.transactions;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RECORDS_DELETED")
public class DeletedRecordsDO {

/*	@Id
	@GeneratedValue
	private long id;*/
	private long cylinder_purchases_id;
	private long arb_purchases_id;
	private long other_purchases_id;
	private long purchase_return_id;
	private long dom_sales_id;
	private long com_sales_id;
	private long arb_sales_id;
	private long quotations_id;
	private long delivery_challan_id;
	private long sales_return_id;
	private long ncdbc_id;
	private long rc_id;
	private long tv_id;
	private long receipts_id;
	private long payments_id;
	private long bank_transactions_id;
	private long credit_note_id;
	private long debit_note_id;

	@Id
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;

/*	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}*/

	public long getCylinder_purchases_id() {
		return cylinder_purchases_id;
	}
	public void setCylinder_purchases_id(long cylinder_purchases_id) {
		this.cylinder_purchases_id = cylinder_purchases_id;
	}
	public long getArb_purchases_id() {
		return arb_purchases_id;
	}
	public void setArb_purchases_id(long arb_purchases_id) {
		this.arb_purchases_id = arb_purchases_id;
	}
	public long getOther_purchases_id() {
		return other_purchases_id;
	}
	public void setOther_purchases_id(long other_purchases_id) {
		this.other_purchases_id = other_purchases_id;
	}
	public long getPurchase_return_id() {
		return purchase_return_id;
	}
	public void setPurchase_return_id(long purchase_return_id) {
		this.purchase_return_id = purchase_return_id;
	}
	public long getDom_sales_id() {
		return dom_sales_id;
	}
	public void setDom_sales_id(long dom_sales_id) {
		this.dom_sales_id = dom_sales_id;
	}
	public long getCom_sales_id() {
		return com_sales_id;
	}
	public void setCom_sales_id(long com_sales_id) {
		this.com_sales_id = com_sales_id;
	}
	public long getArb_sales_id() {
		return arb_sales_id;
	}
	public void setArb_sales_id(long arb_sales_id) {
		this.arb_sales_id = arb_sales_id;
	}
	public long getQuotations_id() {
		return quotations_id;
	}
	public void setQuotations_id(long quotations_id) {
		this.quotations_id = quotations_id;
	}
	public long getDelivery_challan_id() {
		return delivery_challan_id;
	}
	public void setDelivery_challan_id(long delivery_challan_id) {
		this.delivery_challan_id = delivery_challan_id;
	}
	public long getSales_return_id() {
		return sales_return_id;
	}
	public void setSales_return_id(long sales_return_id) {
		this.sales_return_id = sales_return_id;
	}
	public long getNcdbc_id() {
		return ncdbc_id;
	}
	public void setNcdbc_id(long ncdbc_id) {
		this.ncdbc_id = ncdbc_id;
	}
	public long getRc_id() {
		return rc_id;
	}
	public void setRc_id(long rc_id) {
		this.rc_id = rc_id;
	}
	public long getTv_id() {
		return tv_id;
	}
	public void setTv_id(long tv_id) {
		this.tv_id = tv_id;
	}
	public long getReceipts_id() {
		return receipts_id;
	}
	public void setReceipts_id(long receipts_id) {
		this.receipts_id = receipts_id;
	}
	public long getPayments_id() {
		return payments_id;
	}
	public void setPayments_id(long payments_id) {
		this.payments_id = payments_id;
	}
	public long getCredit_note_id() {
		return credit_note_id;
	}
	public void setCredit_note_id(long credit_note_id) {
		this.credit_note_id = credit_note_id;
	}
	public long getDebit_note_id() {
		return debit_note_id;
	}
	public void setDebit_note_id(long debit_note_id) {
		this.debit_note_id = debit_note_id;
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
	public long getBank_transactions_id() {
		return bank_transactions_id;
	}
	public void setBank_transactions_id(long bank_transactions_id) {
		this.bank_transactions_id = bank_transactions_id;
	}
	
}
