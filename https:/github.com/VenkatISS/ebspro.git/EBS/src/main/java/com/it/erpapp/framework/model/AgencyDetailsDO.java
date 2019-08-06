package com.it.erpapp.framework.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AGENCY_DETAILS")
public class AgencyDetailsDO {

	@Column(name = "AGENCY_NAME", nullable = false )
	private String dealerName;
	
	@Column(name = "AGENCY_MOBILE", nullable = false )
	private String dealerMobile;
	
	@Column(name = "AGENCY_ST_OR_UT", nullable = false )
	private int state;
	
	@Column(name = "AGENCY_OC", nullable = false )	
	private int oilCompany;
	
	@Column(name = "PINNUMBER", nullable = true )
	private String pinNumber;

	@Column(name = "PPSHIP", nullable = false )	
	private int ppship;
	
	@Column(name = "OFFICE_LANDLINE", nullable = true )	
	private String officeLandline;
	
	@Column(name = "ADDRESS", nullable = true )	
	private String address;
	
	@Column(name = "PAN_NO", nullable = true )	
	private String pan;
	
	@Column(name = "TIN_NO", nullable = true )	
	private String tin;
	
	@Column(name = "GSTIN_NO", nullable = true )	
	private String gstin;
	
	@Column(name = "REG_NO", nullable = true )	
	private String reg;
	
	@Column(name = "CONTACT_PERSON", nullable = true )	
	private String contactPerson;

	@Column(name = "EFFECTIVE_DATE", nullable = true )	
	private long effectiveDate;
	
	@Column(name = "DAYEND_DATE", nullable = true )	
	private long dayendDate;
	
	@Column(name = "LAST_TRXNDATE", nullable = true )	
	private long lastTrxnDate;

	@Column(name = "SI_SNO", nullable = true )	
	private long si_sno;
	
	@Column(name = "CS_SNO", nullable = true )	
	private long cs_sno;
	
	@Column(name = "FTL_FY", nullable = true )	
	private int ftl_fy;
	
	@Column(name = "IMAGE_STATUS", nullable = false )
	private int image_status;
	
	@Column(name = "XL_UPLOAD_STATUS", nullable = true )
    private int xl_upload_status;
	
	@Column(name = "OFFER_PRICE_ACCEPTABLE", nullable = false )
	private int offer_price_acceptable;
	
	
	@Id
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
	


	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerMobile() {
		return dealerMobile;
	}

	public void setDealerMobile(String dealerMobile) {
		this.dealerMobile = dealerMobile;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getOilCompany() {
		return oilCompany;
	}

	public void setOilCompany(int oilCompany) {
		this.oilCompany = oilCompany;
	}

	public String getOfficeLandline() {
		return officeLandline;
	}

	public void setOfficeLandline(String officeLandline) {
		this.officeLandline = officeLandline;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
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

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public long getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(long effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public long getDayendDate() {
		return dayendDate;
	}

	public void setDayendDate(long dayendDate) {
		this.dayendDate = dayendDate;
	}

	public long getLastTrxnDate() {
		return lastTrxnDate;
	}

	public void setLastTrxnDate(long lastTrxnDate) {
		this.lastTrxnDate = lastTrxnDate;
	}	
	
	public int getPpship() {
		return ppship;
	}

	public void setPpship(int ppship) {
		this.ppship = ppship;
	}

	public String getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}

	public long getSi_sno() {
		return si_sno;
	}

	public void setSi_sno(long si_sno) {
		this.si_sno = si_sno;
	}

	public long getCs_sno() {
		return cs_sno;
	}

	public void setCs_sno(long cs_sno) {
		this.cs_sno = cs_sno;
	}

	public int getFtl_fy() {
		return ftl_fy;
	}

	public void setFtl_fy(int ftl_fy) {
		this.ftl_fy = ftl_fy;
	}

	public int getImage_status() {
		return image_status;
	}

	public void setImage_status(int image_status) {
		this.image_status = image_status;
	}

	public int getXl_upload_status() {
		return xl_upload_status;
	}

	public void setXl_upload_status(int xl_upload_status) {
		this.xl_upload_status = xl_upload_status;
	}

	public int getOffer_price_acceptable() {
		return offer_price_acceptable;
	}

	public void setOffer_price_acceptable(int offer_price_acceptable) {
		this.offer_price_acceptable = offer_price_acceptable;
	}

//	request.getParameter("btbInvCounter"),request.getParameter("btcInvCounter")
	
}