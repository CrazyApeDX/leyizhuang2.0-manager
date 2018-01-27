package com.ynyes.fitment.foundation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.ynyes.fitment.core.entity.ApplicationEntity;
import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

@Entity
@Table(name = "FIT_SALES_MANAGER")
public class FitSalesManager extends TableEntity{
	// 姓名
	@Column(length = 50, nullable = false)
	private String name;
	
	// 手机号码
	@Column(length = 20, nullable = false)
	private String phone;
	
	// 城市编码
	@Column(length = 10, nullable = false)
	private String cityCode;
	
	// 是否有效
	@Column(length = 1, nullable = false)
	private Boolean isEnable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

}
