package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 仓库实体类
 * 
 * @author dengxiao
 */

@Entity
public class TdStorage {

	public TdStorage() {
		super();
	}

	public TdStorage(Long id, String title, String cityName, Long cityId, String address, String remark) {
		super();
		this.id = id;
		this.title = title;
		this.cityName = cityName;
		this.cityId = cityId;
		this.address = address;
		this.remark = remark;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 名称
	@Column
	private String title;

	// 所在城市名
	@Column
	private String cityName;

	// 所在城市id
	@Column
	private Long cityId;

	// 地址
	@Column
	private String address;

	// 备注
	@Column
	private String remark;
	
	//排序
	@Column
	private Double sortId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	@Override
	public String toString() {
		return "TdStorage [id=" + id + ", title=" + title + ", cityName=" + cityName + ", cityId=" + cityId
				+ ", address=" + address + ", remark=" + remark + "]";
	}
}
