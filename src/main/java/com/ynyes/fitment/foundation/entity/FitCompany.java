package com.ynyes.fitment.foundation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

//@Entity
//@Table(name = "FIT_EMPLOYEE")
public class FitCompany extends TableEntity {

	private static final long serialVersionUID = 1L;

	@Column(length = 15, nullable = false, unique = true)
	private String name;

	@Column(length = 15, nullable = false, unique = true)
	private String code;

	@Column(length = 10, nullable = false)
	private Long sobId;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getSobId() {
		return sobId;
	}

	public void setSobId(Long sobId) {
		this.sobId = sobId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
