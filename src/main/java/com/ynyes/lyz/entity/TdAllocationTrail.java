package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 门店调拨轨迹实体类
 * 
 */

@Entity
public class TdAllocationTrail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private Long allocationId;

	@Column
	private String operation;

	@Column
	private String operatedBy;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date operatedTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(Long allocationId) {
		this.allocationId = allocationId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperatedBy() {
		return operatedBy;
	}

	public void setOperatedBy(String operatedBy) {
		this.operatedBy = operatedBy;
	}

	public Date getOperatedTime() {
		return operatedTime;
	}

	public void setOperatedTime(Date operatedTime) {
		this.operatedTime = operatedTime;
	}

	@Override
	public String toString() {
		return "TdAllocationTrail [id=" + id + ", allocationId=" + allocationId + ", operation=" + operation + ", operatedBy="
				+ operatedBy + ", operatedTime=" + operatedTime + "]";
	}
}
