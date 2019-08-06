package com.it.erpapp.framework.model.transactions.sales;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

public class DEProductsViewDO {

	private long id;
	private int tx_type;
	private long tx_date;
	private long created_date;
	
	@Transient
	private List<DEProductsDetailsViewDO> detailsList = new ArrayList<DEProductsDetailsViewDO>();

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTx_date() {
		return tx_date;
	}
	public void setTx_date(long tx_date) {
		this.tx_date = tx_date;
	}
	public long getCreated_date() {
		return created_date;
	}
	public void setCreated_date(long created_date) {
		this.created_date = created_date;
	}
	public List<DEProductsDetailsViewDO> getDetailsList() {
		return detailsList;
	}
	public void setDetailsList(List<DEProductsDetailsViewDO> detailsList) {
		this.detailsList = detailsList;
	}
	public int getTx_type() {
		return tx_type;
	}
	public void setTx_type(int tx_type) {
		this.tx_type = tx_type;
	}

}
