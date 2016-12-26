package com.ynyes.fitment.foundation.entity.client;

import com.ynyes.fitment.core.entity.client.ClientEntity;
import com.ynyes.fitment.foundation.entity.FitEmployee;

public class ClientEmployee extends ClientEntity {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String mobile;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public ClientEmployee init(FitEmployee employee) {
		this.setId(employee.getId());
		this.setMobile(employee.getMobile());
		this.setName(employee.getName());
		return this;
	}
}
