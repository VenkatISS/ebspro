package com.it.erpapp.framework.model.vos;

public class StockReportVO {

	public int openingStockF;
	public int closingStockF;
	public int closingStockE;
	public int totalPurchases;
	public int totalPurchaseReturns;
	public int totalSales;
	public int totalSalesReturns;
	public int currentStock;
	public int emptyStock;
	public String productSelected;
	public String productCode;
	
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
	public int getTotalSalesReturns() {
		return totalSalesReturns;
	}
	public void setTotalSalesReturns(int totalSalesReturns) {
		this.totalSalesReturns = totalSalesReturns;
	}
	public int getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(int currentStock) {
		this.currentStock = currentStock;
	}
	public String getProductSelected() {
		return productSelected;
	}
	public void setProductSelected(String productSelected) {
		this.productSelected = productSelected;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getEmptyStock() {
		return emptyStock;
	}
	public void setEmptyStock(int emptyStock) {
		this.emptyStock = emptyStock;
	}
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

	
}