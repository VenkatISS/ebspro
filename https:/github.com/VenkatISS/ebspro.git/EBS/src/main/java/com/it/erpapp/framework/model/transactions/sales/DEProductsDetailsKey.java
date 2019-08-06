package com.it.erpapp.framework.model.transactions.sales;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DEProductsDetailsKey implements Serializable {

	private long tx_id;
	private int tx_type;
	public long getTx_id() {
		return tx_id;
	}
	public void setTx_id(long tx_id) {
		this.tx_id = tx_id;
	}
	public int getTx_type() {
		return tx_type;
	}
	public void setTx_type(int tx_type) {
		this.tx_type = tx_type;
	}
}
