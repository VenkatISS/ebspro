package com.it.erpapp.framework.model.transactions.sales;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DEProductsKey implements Serializable {

	private long id;
	private int tx_type;
	public long getId() {
		return id;
	}
	public void setTx_id(long id) {
		this.id = id;
	}
	public int getTx_type() {
		return tx_type;
	}
	public void setTx_type(int tx_type) {
		this.tx_type = tx_type;
	}
}
