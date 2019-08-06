package com.it.erpapp.framework.model.vos;

public class StockReportDataVO {

	private int openingStockF;
	private int closingStockF;
	private int closingStockE;
	private int totalPurchases;
	private int totalPurchaseReturns;
	private int totalSales;
	private int totalSalesReturns;
	private int productCode;
	
	public int getOpeningStockF() {
		return openingStockF;
	}
	public void setOpeningStockF(int openingStockF) {
		this.openingStockF = openingStockF;
	}
	public int getClosingStockF() {
		return closingStockF;
	}
	public void setClosingStockF(int closingStockF) {
		this.closingStockF = closingStockF;
	}
	public int getClosingStockE() {
		return closingStockE;
	}
	public void setClosingStockE(int closingStockE) {
		this.closingStockE = closingStockE;
	}
	public int getTotalPurchases() {
		return totalPurchases;
	}
	public void setTotalPurchases(int totalPurchases) {
		this.totalPurchases = totalPurchases;
	}
	public int getTotalPurchaseReturns() {
		return totalPurchaseReturns;
	}
	public void setTotalPurchaseReturns(int totalPurchaseReturns) {
		this.totalPurchaseReturns = totalPurchaseReturns;
	}
	public int getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(int totalSales) {
		this.totalSales = totalSales;
	}
	public int getProductCode() {
		return productCode;
	}
	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}
	public int getTotalSalesReturns() {
		return totalSalesReturns;
	}
	public void setTotalSalesReturns(int totalSalesReturns) {
		this.totalSalesReturns = totalSalesReturns;
	}
}