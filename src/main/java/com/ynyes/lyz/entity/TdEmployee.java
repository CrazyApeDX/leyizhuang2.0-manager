package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 员工信息表
 * 
 * @author dengxiao
 */

@Entity
public class TdEmployee {

	// 员工Id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 职务（1代表销顾，2代表店长，3代表店主）
	@Column
	private Long positionType;

	// 归属门店名称
	@Column
	private String diyName;

	// 所属门店ID
	@Column
	private Long upperDiySiteId;

	// 归属区域名称
	@Column
	private String cityName;

	// 归属区域Id
	@Column
	private Long regionId;

	// 电话号码
	@Column
	private String phone;

	// 真实姓名
	@Column
	private String realName;

	// 是否启用
	@Column
	private Boolean isEnable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPositionType() {
		return positionType;
	}

	public void setPositionType(Long positionType) {
		this.positionType = positionType;
	}

	public String getDiyName() {
		return diyName;
	}

	public void setDiyName(String diyName) {
		this.diyName = diyName;
	}

	public Long getUpperDiySiteId() {
		return upperDiySiteId;
	}

	public void setUpperDiySiteId(Long upperDiySiteId) {
		this.upperDiySiteId = upperDiySiteId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	
}
