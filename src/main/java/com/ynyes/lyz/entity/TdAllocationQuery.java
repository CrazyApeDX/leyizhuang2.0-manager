package com.ynyes.lyz.entity;

import java.util.Date;
import java.util.List;

/**
 * 门店调拨查询参数类
 * 
 */

public class TdAllocationQuery {

	private String number;

	private Long cityId;

	private Integer allocationType;

	private Long diySiteId;

	private List<Long> diySiteIds;

	private Integer status;

	private Date startTime;

	private Date endTime;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Integer getAllocationType() {
		return allocationType;
	}

	public void setAllocationType(Integer allocationType) {
		this.allocationType = allocationType;
	}

	public Long getDiySiteId() {
		return diySiteId;
	}

	public void setDiySiteId(Long diySiteId) {
		this.diySiteId = diySiteId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<Long> getDiySiteIds() {
		return diySiteIds;
	}

	public void setDiySiteIds(List<Long> diySiteIds) {
		this.diySiteIds = diySiteIds;
	}

	@Override
	public String toString() {
		return "TdAllocationQuery [number=" + number + ", cityId=" + cityId + ", allocationType=" + allocationType
				+ ", diySiteId=" + diySiteId + ", status=" + status + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
}
