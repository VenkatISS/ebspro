package com.it.erpapp.framework.model.pps;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REFILL_PRICES_DATA")
public class RefillPriceDataDO {

	@Id
	@GeneratedValue
	private long id;
	private int prod_code;
	private String base_price;
	private String sgst_price;
	private String cgst_price;
	private String rsp;
	private String offer_price;
	private String op_base_price;
	private int montha;
	private int yeara;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreated_by() {
		return created_by;
	}

	public void setCreated_by(long created_by) {
		this.created_by = created_by;
	}

	public long getCreated_date() {
		return created_date;
	}

	public void setCreated_date(long created_date) {
		this.created_date = created_date;
	}

	public long getModified_by() {
		return modified_by;
	}

	public void setModified_by(long modified_by) {
		this.modified_by = modified_by;
	}

	public long getModified_date() {
		return modified_date;
	}

	public void setModified_date(long modified_date) {
		this.modified_date = modified_date;
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

	public int getProd_code() {
		return prod_code;
	}

	public void setProd_code(int prod_code) {
		this.prod_code = prod_code;
	}

	public String getBase_price() {
		return base_price;
	}

	public void setBase_price(String base_price) {
		this.base_price = base_price;
	}

	public String getRsp() {
		return rsp;
	}

	public void setRsp(String rsp) {
		this.rsp = rsp;
	}

	public String getOffer_price() {
		return offer_price;
	}

	public void setOffer_price(String offer_price) {
		this.offer_price = offer_price;
	}

	public int getMontha() {
		return montha;
	}

	public void setMontha(int montha) {
		this.montha = montha;
	}

	public int getYeara() {
		return yeara;
	}

	public void setYeara(int yeara) {
		this.yeara = yeara;
	}

	public String getSgst_price() {
		return sgst_price;
	}

	public void setSgst_price(String sgst_price) {
		this.sgst_price = sgst_price;
	}

	public String getCgst_price() {
		return cgst_price;
	}

	public void setCgst_price(String cgst_price) {
		this.cgst_price = cgst_price;
	}

	public String getOp_base_price() {
		return op_base_price;
	}

	public void setOp_base_price(String op_base_price) {
		this.op_base_price = op_base_price;
	}


}
