package com.ynyes.lyz.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 门店调拨实体类
 * 
 */

@Entity
public class TdAllocation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String number;

	@Column
	private Long cityId;

	@Column
	private String cityName;

	@Column
	private Long allocationFrom;

	@Column
	private String allocationFromName;

	@Column
	private Long allocationTo;

	@Column
	private String allocationToName;

	@Column
	private Integer status;

	@Column
	private String comment;

	@Column
	private String createdBy;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdTime;

	@Column
	private String updatedBy;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedTime;

	@Transient
	private List<TdAllocationDetail> details;

	@Transient
	private List<TdAllocationTrail> trails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getAllocationFrom() {
		return allocationFrom;
	}

	public void setAllocationFrom(Long allocationFrom) {
		this.allocationFrom = allocationFrom;
	}

	public Long getAllocationTo() {
		return allocationTo;
	}

	public void setAllocationTo(Long allocationTo) {
		this.allocationTo = allocationTo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public List<TdAllocationDetail> getDetails() {
		return details;
	}

	public void setDetails(List<TdAllocationDetail> details) {
		this.details = details;
	}

	public List<TdAllocationTrail> getTrails() {
		return trails;
	}

	public void setTrails(List<TdAllocationTrail> trails) {
		this.trails = trails;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAllocationFromName() {
		return allocationFromName;
	}

	public void setAllocationFromName(String allocationFromName) {
		this.allocationFromName = allocationFromName;
	}

	public String getAllocationToName() {
		return allocationToName;
	}

	public void setAllocationToName(String allocationToName) {
		this.allocationToName = allocationToName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStatusDisplay() {
		return AllocationTypeEnum.getName(this.status);
	}

	@Override
	public String toString() {
		return "TdAllocation [id=" + id + ", number=" + number + ", cityId=" + cityId + ", cityName=" + cityName
				+ ", allocationFrom=" + allocationFrom + ", allocationFromName=" + allocationFromName + ", allocationTo="
				+ allocationTo + ", allocationToName=" + allocationToName + ", status=" + status + ", comment=" + comment
				+ ", createdBy=" + createdBy + ", createdTime=" + createdTime + ", updatedBy=" + updatedBy + ", updatedTime="
				+ updatedTime + "]";
	}
}
