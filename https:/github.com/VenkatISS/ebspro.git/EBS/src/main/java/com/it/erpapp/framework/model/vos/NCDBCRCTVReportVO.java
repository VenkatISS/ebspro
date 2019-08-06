package com.it.erpapp.framework.model.vos;

public class NCDBCRCTVReportVO {

	public long tranxDate;
	public long prodCode;
	public int connectionType;
	public int nocs;
	public int ncs;
	public int nrs;
	public String totalSecurityDeposit;
	public long getTranxDate() {
		return tranxDate;
	}
	public void setTranxDate(long tranxDate) {
		this.tranxDate = tranxDate;
	}
	public long getProdCode() {
		return prodCode;
	}
	public void setProdCode(long prodCode) {
		this.prodCode = prodCode;
	}
	public int getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(int connectionType) {
		this.connectionType = connectionType;
	}
	public int getNocs() {
		return nocs;
	}
	public void setNocs(int nocs) {
		this.nocs = nocs;
	}
	public int getNcs() {
		return ncs;
	}
	public void setNcs(int ncs) {
		this.ncs = ncs;
	}
	public int getNrs() {
		return nrs;
	}
	public void setNrs(int nrs) {
		this.nrs = nrs;
	}
	public String getTotalSecurityDeposit() {
		return totalSecurityDeposit;
	}
	public void setTotalSecurityDeposit(String totalSecurityDeposit) {
		this.totalSecurityDeposit = totalSecurityDeposit;
	}
}