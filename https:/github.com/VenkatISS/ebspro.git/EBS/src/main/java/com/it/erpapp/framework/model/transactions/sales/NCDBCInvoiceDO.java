package com.it.erpapp.framework.model.transactions.sales;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="NCDBCRCTV_INVOICES")
public class NCDBCInvoiceDO {

	@Id
	@GeneratedValue
	private long id;
	private String sr_no;
	private long bom_id;
	private int conn_type;
	private long inv_date;
	private String customer_no;
	private long staff_id;
	private int no_of_conns;
	private String cash_amount;
	private String online_amount;
	private String dep_amount;
	private String inv_amount;
	private long bank_id;
	private String discount;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;

	@Transient
	private List<NCDBCInvoiceDetailsDO> detailsList = new ArrayList<NCDBCInvoiceDetailsDO>();

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getBom_id() {
		return bom_id;
	}
	public void setBom_id(long bom_id) {
		this.bom_id = bom_id;
	}
	public int getConn_type() {
		return conn_type;
	}
	public void setConn_type(int conn_type) {
		this.conn_type = conn_type;
	}
	public long getInv_date() {
		return inv_date;
	}
	public void setInv_date(long inv_date) {
		this.inv_date = inv_date;
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
	public int getNo_of_conns() {
		return no_of_conns;
	}
	public void setNo_of_conns(int no_of_conns) {
		this.no_of_conns = no_of_conns;
	}
	public String getCash_amount() {
		return cash_amount;
	}
	public void setCash_amount(String cash_amount) {
		this.cash_amount = cash_amount;
	}
	public String getOnline_amount() {
		return online_amount;
	}
	public void setOnline_amount(String online_amount) {
		this.online_amount = online_amount;
	}
	public String getInv_amount() {
		return inv_amount;
	}
	public void setInv_amount(String inv_amount) {
		this.inv_amount = inv_amount;
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
	public List<NCDBCInvoiceDetailsDO> getDetailsList() {
		return detailsList;
	}
	public void setDetailsList(List<NCDBCInvoiceDetailsDO> detailsList) {
		this.detailsList = detailsList;
	}
	public String getDep_amount() {
		return dep_amount;
	}
	public void setDep_amount(String dep_amount) {
		this.dep_amount = dep_amount;
	}
	public String getSr_no() {
		return sr_no;
	}
	public void setSr_no(String string) {
		this.sr_no = string;
	}
	public long getBank_id() {
		return bank_id;
	}
	public void setBank_id(long bank_id) {
		this.bank_id = bank_id;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
}