package com.it.erpapp.framework.model.transactions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="RCTV_DATA")
public class RCTVDataDO {

	@Id
	@GeneratedValue
	private long id;
	private long inv_ref_no;
	private long inv_date;
	private int inv_type;
	private long customer_id;
	private long staff_id;
	private String qtn_amount;
	private long created_by;
	private long created_date;
	private long modified_by;
	private long modified_date;
	private int version;
	private int deleted;
	@Transient
	private List<RCTVARBDetailsDO> itemsList = new ArrayList<RCTVARBDetailsDO>();

}
