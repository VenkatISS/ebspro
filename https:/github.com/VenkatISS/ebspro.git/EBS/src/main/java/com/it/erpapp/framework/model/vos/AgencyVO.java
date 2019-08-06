package com.it.erpapp.framework.model.vos;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACTIVE_AGENCIES_VIEW")
public class AgencyVO {

	@Id
	private long agency_code;
	private String email_id;
	private long created_date;
	private long modified_date;
	private String agency_name;
	private String agency_mobile;
	private String office_landline;
	private String address;
	private int agency_st_or_ut;
	private int agency_oc;
	private int ppship;
	private String pan_no;
	private String tin_no;
	private String gstin_no;
	private String reg_no;
    private String pinNumber;
	private String contact_person;
	private long effective_date;
	private long dayend_date;
	private long last_trxndate;
    private int xl_upload_status;
	private long si_sno;
	private long cs_sno;
	private int is_ftl;
	private int image_status;
	private int offer_price_acceptable;
	
	public long getAgency_code() {
		return agency_code;
	}
	public void setAgency_code(long agency_code) {
		this.agency_code = agency_code;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public long getCreated_date() {
		return created_date;
	}
	public void setCreated_date(long created_date) {
		this.created_date = created_date;
	}
	public long getModified_date() {
		return modified_date;
	}
	public String getOffice_landline() {
		return office_landline;
	}
	public void setOffice_landline(String office_landline) {
		this.office_landline = office_landline;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPan_no() {
		return pan_no;
	}
	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}
	public String getTin_no() {
		return tin_no;
	}
	public void setTin_no(String tin_no) {
		this.tin_no = tin_no;
	}
	public String getReg_no() {
		return reg_no;
	}
	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}
	public String getContact_person() {
		return contact_person;
	}
	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}
	public void setModified_date(long modified_date) {
		this.modified_date = modified_date;
	}
	public String getAgency_name() {
		return agency_name;
	}
	public void setAgency_name(String agency_name) {
		this.agency_name = agency_name;
	}
	public String getAgency_mobile() {
		return agency_mobile;
	}
	public void setAgency_mobile(String agency_mobile) {
		this.agency_mobile = agency_mobile;
	}
	public int getAgency_st_or_ut() {
		return agency_st_or_ut;
	}
	public void setAgency_st_or_ut(int agency_st_or_ut) {
		this.agency_st_or_ut = agency_st_or_ut;
	}
	public int getAgency_oc() {
		return agency_oc;
	}
	public void setAgency_oc(int agency_oc) {
		this.agency_oc = agency_oc;
	}
	public String getGstin_no() {
		return gstin_no;
	}
	public void setGstin_no(String gstin_no) {
		this.gstin_no = gstin_no;
	}
	public long getDayend_date() {
		return dayend_date;
	}
	public void setDayend_date(long dayend_date) {
		this.dayend_date = dayend_date;
	}	
	public long getEffective_date() {
		return effective_date;
	}
	public void setEffective_date(long effective_date) {
		this.effective_date = effective_date;
	}
	public int getPpship() {
		return ppship;
	}
	public void setPpship(int ppship) {
		this.ppship = ppship;
	}
	public int getIs_ftl() {
		return is_ftl;
	}
	public void setIs_ftl(int is_ftl) {
		this.is_ftl = is_ftl;
	}
	public String getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}
	public long getLast_trxndate() {
		return last_trxndate;
	}
	public void setLast_trxndate(long last_trxndate) {
		this.last_trxndate = last_trxndate;
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
	
}