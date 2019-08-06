package com.it.erpapp.framework.model.transactions.sales;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="DELIVERY_CHALLANS")
public class DeliveryChallanDO {

	@Id
	@GeneratedValue
	private long id;
	private String sr_no;
	private long dc_date;
	private long dc_ref_date;	
	private long customer_id;
	private String delivery_mode;
	private long fleet_id;
	private long staff_id;
	private String dc_amount;
	private String inv_no;
	private String delivery_instructions;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;

	@Transient
	private List<DeliveryChallanDetailsDO> dcItemsList = new ArrayList<DeliveryChallanDetailsDO>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDc_date() {
		return dc_date;
	}
	public void setDc_date(long dc_date) {
		this.dc_date = dc_date;
	}
	public long getDc_ref_date() {
		return dc_ref_date;
	}
	public void setDc_ref_date(long dc_ref_date) {
		this.dc_ref_date = dc_ref_date;
	}	
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public String getDelivery_mode() {
		return delivery_mode;
	}
	public void setDelivery_mode(String delivery_mode) {
		this.delivery_mode = delivery_mode;
	}
	public long getFleet_id() {
		return fleet_id;
	}
	public void setFleet_id(long fleet_id) {
		this.fleet_id = fleet_id;
	}
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}
	public String getDelivery_instructions() {
		return delivery_instructions;
	}
	public void setDelivery_instructions(String delivery_instructions) {
		this.delivery_instructions = delivery_instructions;
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
	public List<DeliveryChallanDetailsDO> getDcItemsList() {
		return dcItemsList;
	}
	public void setDcItemsList(List<DeliveryChallanDetailsDO> dcItemsList) {
		this.dcItemsList = dcItemsList;
	}
	public String getDc_amount() {
		return dc_amount;
	}
	public void setDc_amount(String dc_amount) {
		this.dc_amount = dc_amount;
	}
	public String getInv_no() {
		return inv_no;
	}
	public void setInv_no(String inv_no) {
		this.inv_no = inv_no;
	}
	public String getSr_no() {
		return sr_no;
	}
	public void setSr_no(String sr_no) {
		this.sr_no = sr_no;
	}

}