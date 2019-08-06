package com.it.erpapp.framework.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT_CATEGORY_DATA")
public class ProductCategoryDO {

	@Id
	private int id;
	private String cat_code;
	private String cat_name;
	private String cat_desc;
	private int cat_type;
	private int deleted;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCat_code() {
		return cat_code;
	}
	public void setCat_code(String cat_code) {
		this.cat_code = cat_code;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public String getCat_desc() {
		return cat_desc;
	}
	public void setCat_desc(String cat_desc) {
		this.cat_desc = cat_desc;
	}
	public int getCat_type() {
		return cat_type;
	}
	public void setCat_type(int cat_type) {
		this.cat_type = cat_type;
	}
	
	
}
