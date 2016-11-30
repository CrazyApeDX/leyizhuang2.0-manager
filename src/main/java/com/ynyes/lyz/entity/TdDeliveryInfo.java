package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 物流信息实体类
 * 
 * @author
 */

@Entity
public class TdDeliveryInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 任务编号
	@Column
	private String taskNo;

	// 仓库编号
	@Column
	private String whNo;

	// 开始时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDt;

	// 结束时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDt;

	// 操作状态(初始、作业中、完成、结案)
	@Column
	private String opStatus;

	// 作业人员
	@Column
	private String opUser;

	// 修改人员
	@Column
	private String modifiedUserno;

	// 委托业主
	@Column
	private String ownerNo;
	
	// 分单号（订单号）
	@Column
	private String orderNumber;
	
	// 送货员
	@Column
	private String driver;
	
	// 城市id
	@Column
	private Long cCompanyId;
	
	// 任务类型
	@Column(length = 20)
	private String cTaskType;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getWhNo() {
		return whNo;
	}

	public void setWhNo(String whNo) {
		this.whNo = whNo;
	}

	public Date getBeginDt() {
		return beginDt;
	}

	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public String getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(String opStatus) {
		this.opStatus = opStatus;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public String getModifiedUserno() {
		return modifiedUserno;
	}

	public void setModifiedUserno(String modifiedUserno) {
		this.modifiedUserno = modifiedUserno;
	}

	public String getOwnerNo() {
		return ownerNo;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	public Long getcCompanyId() {
		return cCompanyId;
	}

	public void setcCompanyId(Long cCompanyId) {
		this.cCompanyId = cCompanyId;
	}

	public String getcTaskType() {
		return cTaskType;
	}

	public void setcTaskType(String cTaskType) {
		this.cTaskType = cTaskType;
	}

	@Override
	public String toString() {
		return "TdDeliveryInfo [id=" + id + ", taskNo=" + taskNo + ", whNo=" + whNo + ", beginDt=" + beginDt
				+ ", endDt=" + endDt + ", opStatus=" + opStatus + ", opUser=" + opUser + ", modifiedUserno="
				+ modifiedUserno + ", ownerNo=" + ownerNo + ", orderNumber=" + orderNumber + ", driver=" + driver
				+ ", cCompanyId=" + cCompanyId + ", cTaskType=" + cTaskType + "]";
	}
	
}
