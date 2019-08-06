package com.it.erpapp.framework.model.vos;

public class DEReportVO {

	public int openingStockF;
	public int openingStockE;
	public int closingStockF;
	public int closingStockE;
	public int totalPurchases;
	public int totalPurchaseReturns;
	public int totalSalesReturns;
	public int totalSales;
	public String totalPurchasesAmount = "0.00";
	public String totalPurchaseReturnsAmount = "0.00";
	public String totalSalesReturnsAmount = "0.00";
	public String totalSalesAmount = "0.00";
	public int totalNCSales;
	public String productSelected;
	public String productCode;
	
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
	public int getOpeningStockE() {
		return openingStockE;
	}
	public void setOpeningStockE(int openingStockE) {
		this.openingStockE = openingStockE;
	}
	public int getTotalNCSales() {
		return totalNCSales;
	}
	public void setTotalNCSales(int totalNCSales) {
		this.totalNCSales = totalNCSales;
	}
	public int getTotalSalesReturns() {
		return totalSalesReturns;
	}
	public void setTotalSalesReturns(int totalSalesReturns) {
		this.totalSalesReturns = totalSalesReturns;
	}
	public String getTotalPurchasesAmount() {
		return totalPurchasesAmount;
	}
	public void setTotalPurchasesAmount(String totalPurchasesAmount) {
		this.totalPurchasesAmount = totalPurchasesAmount;
	}
	public String getTotalPurchaseReturnsAmount() {
		return totalPurchaseReturnsAmount;
	}
	public void setTotalPurchaseReturnsAmount(String totalPurchaseReturnsAmount) {
		this.totalPurchaseReturnsAmount = totalPurchaseReturnsAmount;
	}
	public String getTotalSalesReturnsAmount() {
		return totalSalesReturnsAmount;
	}
	public void setTotalSalesReturnsAmount(String totalSalesReturnsAmount) {
		this.totalSalesReturnsAmount = totalSalesReturnsAmount;
	}
	public String getTotalSalesAmount() {
		return totalSalesAmount;
	}
	public void setTotalSalesAmount(String totalSalesAmount) {
		this.totalSalesAmount = totalSalesAmount;
	}
	
}