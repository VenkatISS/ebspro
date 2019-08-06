package com.it.erpapp.framework.model.transactions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AGENCY_BOM_ITEMS")
public class BOMItemsDO {

	@Id
	@GeneratedValue
	private long id;
	private long bom_id;
	private String prod_code;
	private int qty;
	private String deposit_req;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProd_code() {
		return prod_code;
	}
	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getDeposit_req() {
		return deposit_req;
	}
	public void setDeposit_req(String deposit_req) {
		this.deposit_req = deposit_req;
	}
	public long getBom_id() {
		return bom_id;
	}
	public void setBom_id(long bom_id) {
		this.bom_id = bom_id;
	}

	
}