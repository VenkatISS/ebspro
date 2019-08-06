package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CASH_TRANS_ENUM")
public class CashTransEnumDO {
	@Id
	private int id;
	private String trans_module;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTrans_module() {
		return trans_module;
	}
	public void setTrans_module(String trans_module) {
		this.trans_module = trans_module;
	}
}
