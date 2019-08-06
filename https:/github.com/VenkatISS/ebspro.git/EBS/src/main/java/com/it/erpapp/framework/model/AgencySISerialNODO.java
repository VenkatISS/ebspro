package com.it.erpapp.framework.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AGENCY_SI_SERIAL")
public class AgencySISerialNODO {

	@Id
	@Column(name = "CREATED_BY", nullable = true )
	private long createdBy;

	@Column(name = "SR_NO", nullable = false )
	private int serialNumber;

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	
}
