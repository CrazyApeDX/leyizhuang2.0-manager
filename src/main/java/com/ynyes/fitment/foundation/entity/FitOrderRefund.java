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
@Table(name = "FIT_ORDER_REFUND")
public class FitOrderRefund extends TableEntity {

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
	private Date refundTime;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date auditTime;

	@Column(nullable = false, updatable = false)
	private Double credit = 0d;

	@Column(nullable = false, updatable = false)
	private Long orderId;

	// 订单商品
	@OneToMany
	@JoinColumn(name = "FIT_ORDER_REFUND_ID")
	private List<FitOrderRefundGoods> orderGoodsList;

	@Column(length = 20, nullable = false, updatable = false)
	private String receiverName;

	@Column(length = 11, nullable = false, updatable = false)
	private String receiverMobile;

	@Column(length = 30, nullable = false, updatable = false)
	private String receiverAddress;

	@Column(length = 255, nullable = false, updatable = false)
	private String receiverAddressDetail;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeMobile() {
		return employeeMobile;
	}

	public void setEmployeeMobile(String employeeMobile) {
		this.employeeMobile = employeeMobile;
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

	public String getAuditorMobile() {
		return auditorMobile;
	}

	public void setAuditorMobile(String auditorMobile) {
		this.auditorMobile = auditorMobile;
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

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<FitOrderRefundGoods> getOrderGoodsList() {
		return orderGoodsList;
	}

	public void setOrderGoodsList(List<FitOrderRefundGoods> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverAddressDetail() {
		return receiverAddressDetail;
	}

	public void setReceiverAddressDetail(String receiverAddressDetail) {
		this.receiverAddressDetail = receiverAddressDetail;
	}
}
