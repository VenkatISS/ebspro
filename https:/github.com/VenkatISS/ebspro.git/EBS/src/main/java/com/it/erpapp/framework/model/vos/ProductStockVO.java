package com.it.erpapp.framework.model.vos;

public class ProductStockVO {

	private int quantity;
	private int ttype;
	private int currentStock;
	private int emptyStock;
	private long createdDate;
	private int openingStock;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTtype() {
		return ttype;
	}
	public void setTtype(int ttype) {
		this.ttype = ttype;
	}
	public int getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(int currentStock) {
		this.currentStock = currentStock;
	}
	public int getOpeningStock() {
		return openingStock;
	}
	public void setOpeningStock(int openingStock) {
		this.openingStock = openingStock;
	}
	public int getEmptyStock() {
		return emptyStock;
	}
	public void setEmptyStock(int emptyStock) {
		this.emptyStock = emptyStock;
	}
	public long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}
}