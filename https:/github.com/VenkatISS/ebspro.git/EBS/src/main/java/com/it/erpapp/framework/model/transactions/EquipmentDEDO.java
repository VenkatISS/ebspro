package com.it.erpapp.framework.model.transactions;

public class EquipmentDEDO {

	private String name;
	private int openingStock;
	private int closingStock;
	private int purchasedQuantity;
	private int saleQuantity;
	public int getOpeningStock() {
		return openingStock;
	}
	public void setOpeningStock(int openingStock) {
		this.openingStock = openingStock;
	}
	public int getClosingStock() {
		return closingStock;
	}
	public void setClosingStock(int closingStock) {
		this.closingStock = closingStock;
	}
	public int getPurchasedQuantity() {
		return purchasedQuantity;
	}
	public void setPurchasedQuantity(int purchasedQuantity) {
		this.purchasedQuantity = purchasedQuantity;
	}
	public int getSaleQuantity() {
		return saleQuantity;
	}
	public void setSaleQuantity(int saleQuantity) {
		this.saleQuantity = saleQuantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
