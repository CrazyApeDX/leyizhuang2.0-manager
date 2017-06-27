package com.ynyes.lyz.interfaces.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class TdCashRefundInf extends TdInfBaseEntity {
	// 分公司id
	@Column
	private Long sobId;

	// APP的唯一ID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long refundId;

	// 退款编号
	@Column
	private String refundNumber;

	// app会员ID（APP是唯一）
	@Column
	private Long userid;

	// app会员名称（会员姓名）
	@Column
	private String username;

	// app会员电话
	@Column
	private String userphone;

	// 门店编码
	@Column
	private String diySiteCode;

	// 订单,电子券,预收款
	@Column
	private String refundClass;

	// 销售单头ID (代收款对应拆分后的订单ID)
	@Column
	private Long rtHeaderId;

	// 销售订单号
	@Column
	private String returnNumber;

	// 原销售订单头ID
	@Column
	@Indexed
	private Long orderHeaderId;

	// 1.电子券类型,订单产品类型 HR华润,LYZ乐易装,YR莹润,
	// 2.PREPAY预收款
	@Column
	private String productType;

	// 退款类型（支付宝,微信,银联,门店现金,门店POS，配送现金,配送POS）
	@Column
	private String refundType;

	// 退款日期
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date refundDate;

	// 退款金额
	@Column
	private Double amount;

	// 订单退款，电子券退款，订单收款冲销
	@Column
	private String description;

	// 预留字段1
	@Column
	private String attribute1;

	// 预留字段2
	@Column
	private String attribute2;

	// 预留字段3
	@Column
	private String attribute3;

	// 预留字段4
	@Column
	private String attribute4;

	// 预留字段5
	@Column
	private String attribute5;

	public Long getSobId() {
		return sobId;
	}

	public void setSobId(Long sobId) {
		this.sobId = sobId;
	}

	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public String getRefundNumber() {
		return refundNumber;
	}

	public void setRefundNumber(String refundNumber) {
		this.refundNumber = refundNumber;
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

	public String getDiySiteCode() {
		return diySiteCode;
	}

	public void setDiySiteCode(String diySiteCode) {
		this.diySiteCode = diySiteCode;
	}

	public String getRefundClass() {
		return refundClass;
	}

	public void setRefundClass(String refundClass) {
		this.refundClass = refundClass;
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

	public Long getOrderHeaderId() {
		return orderHeaderId;
	}

	public void setOrderHeaderId(Long orderHeaderId) {
		this.orderHeaderId = orderHeaderId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		return "TdCashRefundInf [sobId=" + sobId + ", refundId=" + refundId + ", refundNumber=" + refundNumber
				+ ", userid=" + userid + ", username=" + username + ", userphone=" + userphone + ", diySiteCode="
				+ diySiteCode + ", refundClass=" + refundClass + ", rtHeaderId=" + rtHeaderId + ", returnNumber="
				+ returnNumber + ", orderHeaderId=" + orderHeaderId + ", productType=" + productType + ", refundType="
				+ refundType + ", refundDate=" + refundDate + ", amount=" + amount + ", description=" + description
				+ ", attribute1=" + attribute1 + ", attribute2=" + attribute2 + ", attribute3=" + attribute3
				+ ", attribute4=" + attribute4 + ", attribute5=" + attribute5 + "]";
	}

}
