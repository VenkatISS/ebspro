package com.it.erpapp.framework.model.transactions.sales;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="COM_SALES_INVOICES")
public class COMSalesInvoiceDO {

	@Id
	@GeneratedValue
	private long id;
	private String sr_no;
	private long si_date;
	private long customer_id;
	private int payment_terms;
	private int payment_status;
	private String cash_amount;
	private String si_amount;
	private String cbal_amount;
	private String dbal_amount;
	private String discount;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;
	@Transient
	private List<COMSalesInvoiceDetailsDO> detailsList = new ArrayList<COMSalesInvoiceDetailsDO>();

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSi_date() {
		return si_date;
	}
	public void setSi_date(long si_date) {
		this.si_date = si_date;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public int getPayment_terms() {
		return payment_terms;
	}
	public void setPayment_terms(int payment_terms) {
		this.payment_terms = payment_terms;
	}
	public String getSi_amount() {
		return si_amount;
	}
	public void setSi_amount(String si_amount) {
		this.si_amount = si_amount;
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
	public List<COMSalesInvoiceDetailsDO> getDetailsList() {
		return detailsList;
	}
	public void setDetailsList(List<COMSalesInvoiceDetailsDO> detailsList) {
		this.detailsList = detailsList;
	}
	public String getCash_amount() {
		return cash_amount;
	}
	public void setCash_amount(String cash_amount) {
		this.cash_amount = cash_amount;
	}
	public String getSr_no() {
		return sr_no;
	}
	public void setSr_no(String string) {
		this.sr_no = string;
	}
	public String getCbal_amount() {
		return cbal_amount;
	}
	public void setCbal_amount(String cbal_amount) {
		this.cbal_amount = cbal_amount;
	}
	public int getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(int payment_status) {
		this.payment_status = payment_status;
	}
	public String getDbal_amount() {
		return dbal_amount;
	}
	public void setDbal_amount(String dbal_amount) {
		this.dbal_amount = dbal_amount;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}

}