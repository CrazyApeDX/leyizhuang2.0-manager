package com.ynyes.fitment.foundation.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

@Entity
@Table(name = "FIT_ORDER_CANCEL")
public class FitOrderCancel extends TableEntity {

	@Column(length = 20, nullable = false, updatable = false)
	private Long employeeId;

	@Column(length = 30, nullable = false, updatable = false)
	private String employeeMobile;

	@Column(length = 30, nullable = false, updatable = false)
	private String employeeName;

	@Column(length = 20, updatable = false)
	private Long auditorId;

	@Column(length = 30, updatable = false)
	private String auditorMobile;

	@Column(length = 30, updatable = false)
	private String auditorName;

	@Column(length = 20, nullable = false, updatable = false)
	private Long companyId;

	@Column(length = 30, nullable = false, updatable = false)
	private String companyName;

	@Column(length = 30, nullable = false, updatable = false)
	private String orderNumber;

	@Column(length = 15, nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private AuditStatus status = AuditStatus.WAIT_AUDIT;

	@Column(nullable = false, updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cancelTime;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date auditTime;

	// 订单商品
	@OneToMany
	@JoinColumn(name = "FIT_ORDER_CANCEL_ID")
	private List<FitOrderCancelGoods> orderGoodsList;

	@Column(nullable = false, updatable = false)
	private Long orderId;

	@Column(nullable = false ,updatable = false)
	private Double credit = 0d;
	
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public AuditStatus getStatus() {
		return status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getEmployeeMobile() {
		return employeeMobile;
	}

	public void setEmployeeMobile(String employeeMobile) {
		this.employeeMobile = employeeMobile;
	}

	public String getAuditorMobile() {
		return auditorMobile;
	}

	public void setAuditorMobile(String auditorMobile) {
		this.auditorMobile = auditorMobile;
	}

	public List<FitOrderCancelGoods> getOrderGoodsList() {
		return orderGoodsList;
	}

	public void setOrderGoodsList(List<FitOrderCancelGoods> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	@Override
	public String toString() {
		return "FitOrderCancel [employeeId=" + employeeId + ", employeeMobile=" + employeeMobile + ", employeeName="
				+ employeeName + ", auditorId=" + auditorId + ", auditorMobile=" + auditorMobile + ", auditorName="
				+ auditorName + ", companyId=" + companyId + ", companyName=" + companyName + ", orderNumber="
				+ orderNumber + ", status=" + status + ", cancelTime=" + cancelTime + ", auditTime=" + auditTime
				+ ", orderGoodsList=" + orderGoodsList + ", orderId=" + orderId + ", credit=" + credit + "]";
	}

}
