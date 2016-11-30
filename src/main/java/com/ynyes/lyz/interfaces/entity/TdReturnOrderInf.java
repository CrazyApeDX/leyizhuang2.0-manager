package com.ynyes.lyz.interfaces.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class TdReturnOrderInf extends TdInfBaseEntity 
{
	//分公司ID
	@Column
	private Long sobId;
	
	//销退头ID (APP唯一)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long rtHeaderId;
	
	//销退订单号（T）
	@Column
	private String returnNumber;
	
	//退单日期
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date returnDate;
	
	//退单标识（“Y” 整单退，“N” 部分退）
	@Column(length = 2)
	private String rtFullFlag;
	
	//原订单头ID
	@Column
	private Long orderHeaderId;
	
	//原销售单号（分单）
	@Column
	private String orderNumber;
	
	//APP单据产品类型 HR华润,LYZ乐易装,YR莹润
	@Column(length = 20)
	private String prodectType;
	
	//门店编码 
	@Column(length = 50)
	private String diySiteCode;
	
	//退款类型（支付宝,微信,银联,现金）
	@Column(length = 50)
	private String refundType;
	
	//审核日期 确认日期
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date auditDate;
	
	//退款金额
	@Column
	private Double refundAmount;
	
	//通用预存款使用退回账户金额
	@Column
	private Double prepayAmt;
	
	@Column
	private String status;
	
	// 用户id
	@Column(length = 15)
	private Long userid;
	
	// 用户名
	@Column(length = 50)
	private String username;
	
	// 用户手机号
	@Column(length = 50)
	private String userphone;
	
	// 订单类型
	@Column(length = 10)
	private Integer orderTypeId;
	
	//预留字段1
	@Column
	private String attribute1;
	
	//预留字段2
	@Column
	private String attribute2;
	
	//预留字段3
	@Column
	private String attribute3;
	
	//预留字段4
	@Column
	private String attribute4;
	
	//预留字段5
	@Column
	private String attribute5;

	// 是否退券
	@Column
	private Character couponFlag;
	
	public Long getSobId() {
		return sobId;
	}

	public void setSobId(Long sobId) {
		this.sobId = sobId;
	}

	public Long getRtHeaderId() {
		return rtHeaderId;
	}

	public void setRtHeaderId(Long rtHeaderId) {
		this.rtHeaderId = rtHeaderId;
	}

	public String getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(String returnNumber) {
		this.returnNumber = returnNumber;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getRtFullFlag() {
		return rtFullFlag;
	}

	public void setRtFullFlag(String rtFullFlag) {
		this.rtFullFlag = rtFullFlag;
	}

	public Long getOrderHeaderId() {
		return orderHeaderId;
	}

	public void setOrderHeaderId(Long orderHeaderId) {
		this.orderHeaderId = orderHeaderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getProdectType() {
		return prodectType;
	}

	public void setProdectType(String prodectType) {
		this.prodectType = prodectType;
	}

	public String getDiySiteCode() {
		return diySiteCode;
	}

	public void setDiySiteCode(String diySiteCode) {
		this.diySiteCode = diySiteCode;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Double getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(Double prepayAmt) {
		this.prepayAmt = prepayAmt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

	public Integer getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	
	public Character getCouponFlag() {
		return couponFlag;
	}

	public void setCouponFlag(Character couponFlag) {
		this.couponFlag = couponFlag;
	}

	@Override
	public String toString() {
		return "TdReturnOrderInf [sobId=" + sobId + ", rtHeaderId=" + rtHeaderId + ", returnNumber=" + returnNumber
				+ ", returnDate=" + returnDate + ", rtFullFlag=" + rtFullFlag + ", orderHeaderId=" + orderHeaderId
				+ ", orderNumber=" + orderNumber + ", prodectType=" + prodectType + ", diySiteCode=" + diySiteCode
				+ ", refundType=" + refundType + ", auditDate=" + auditDate + ", refundAmount=" + refundAmount
				+ ", prepayAmt=" + prepayAmt + ", status=" + status + ", userid=" + userid + ", username=" + username
				+ ", userphone=" + userphone + ", orderTypeId=" + orderTypeId + ", attribute1=" + attribute1
				+ ", attribute2=" + attribute2 + ", attribute3=" + attribute3 + ", attribute4=" + attribute4
				+ ", attribute5=" + attribute5 + "]";
	}

}
