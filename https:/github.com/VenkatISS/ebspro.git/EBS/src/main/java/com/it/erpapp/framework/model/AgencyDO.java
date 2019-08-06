package com.it.erpapp.framework.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AGENCYS")
public class AgencyDO {

	@Id
	@Column(name = "AGENCY_CODE", nullable = false )
	private long agencyCode;
	
	@Column(name = "EMAIL_ID", nullable = false )
	private String emailId;
	
	@Column(name = "PWD", nullable = false )
	private String passCode;
	
	@Column(name = "STATUS", nullable = false )	
	private int status;
	
	@Column(name = "IS_FTL", nullable = false )	
	private int isFirstTimeLogin;

	@Column(name = "CREATED_BY", nullable = true )
	private long createdBy;
	
	@Column(name = "CREATED_DATE", nullable = false )
	private long createdDate;
	
		@Column(name = "MODIFIED_BY", nullable = true )
	private long modifiedBy;
	
	@Column(name = "MODIFIED_DATE", nullable = true)
	private long modifiedDate;
	
	@Column(name = "VERSION", nullable = false )
	private int version;
	
	@Column(name = "DELETED", nullable = false )
	private int deleted;

	public long getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(long agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassCode() {
		return passCode;
	}

	public void setPassCode(String passCode) {
		this.passCode = passCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getIsFirstTimeLogin() {
		return isFirstTimeLogin;
	}

	public void setIsFirstTimeLogin(int isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	public long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public long getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(long modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
}