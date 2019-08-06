package com.it.erpapp.framework.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AGENCY_SERIAL_NOS")
public class AgencySerialNosDO {
	
	@Id
	@Column(name = "CREATED_BY", nullable = true )
	private long createdBy;
	
	@Column(name = "PR_SNO", nullable = false )
	private int prSno;
	
	@Column(name = "SI_SNO", nullable = false )
	private int siSno;
	
	@Column(name = "CS_SNO", nullable = false )
	private int csSno;
	
	@Column(name = "SR_SNO", nullable = false )
	private int srSno;

	@Column(name = "QT_SNO", nullable = false )
	private int qtSno;
	
	@Column(name = "DC_SNO", nullable = false )
	private int dcSno;
	
	@Column(name = "RCPTS_SNO", nullable = false )
	private int rcptsSno;
	
	@Column(name = "BT_SNO", nullable = false )
	private int btSno;
	
	@Column(name = "CN_SNO", nullable = false )
	private int cnSno;
	
	
	@Column(name = "DN_SNO", nullable = false )
	private int dnSno;
	
	@Column(name = "FY", nullable = false )
	private int fy;
	
	@Column(name = "PMTS_SNO", nullable = false )
	private int pmtsSno;
	
	
	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public int getPrSno() {
		return prSno;
	}

	public void setPrSno(int prSno) {
		this.prSno = prSno;
	}

	public int getSiSno() {
		return siSno;
	}
	
	public void setSiSno(int siSno) {
		this.siSno = siSno;
	}

	public void setCsSno(int csSno) {
		this.csSno = csSno;
	}

	
	public int getCsSno() {
		return csSno;
	}

	
	public int getSrSno() {
		return srSno;
	}

	public void setSrSno(int srSno) {
		this.srSno = srSno;
	}

	public int getQtSno() {
		return qtSno;
	}

	public void setQtSno(int qtSno) {
		this.qtSno = qtSno;
	}

	public int getDcSno() {
		return dcSno;
	}

	public void setDcSno(int dcSno) {
		this.dcSno = dcSno;
	}

	public int getRcptsSno() {
		return rcptsSno;
	}

	public void setRcptsSno(int rcptsSno) {
		this.rcptsSno = rcptsSno;
	}

	public int getBtSno() {
		return btSno;
	}

	public void setBtSno(int btSno) {
		this.btSno = btSno;
	}

	public int getCnSno() {
		return cnSno;
	}

	public void setCnSno(int cnSno) {
		this.cnSno = cnSno;
	}
	
	public int getDnSno() {
		return dnSno;
	}

	public void setDnSno(int dnSno) {
		this.dnSno = dnSno;
	}

	public int getFy() {
		return fy;
	}

	public void setFy(int fy) {
		this.fy = fy;
	}

	public int getPmtsSno() {
		return pmtsSno;
	}

	public void setPmtsSno(int pmtsSno) {
		this.pmtsSno = pmtsSno;
	}

}
