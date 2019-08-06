package com.it.erpapp.framework.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACCOUNT_ACTIVATION")
public class AccountActivationDO {
	
	public long getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(long agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	@Id
	@Column(name = "AGENCY_CODE", nullable = false )
	private long agencyCode;
	
	
	@Column(name = "ACTIVATION_CODE", nullable = false )
	private String activationCode;
	
	@Column(name = "REQUEST_TYPE", nullable = false )	
	private int requestType;

	@Column(name = "CREATED_DATE", nullable = true )
	private long createdDate;


}
