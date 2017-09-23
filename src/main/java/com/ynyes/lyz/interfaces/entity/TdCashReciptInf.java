package com.ynyes.lyz.interfaces.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class TdCashReciptInf extends TdInfBaseEntity{
	
	public static final Integer CASHRECEIPTTYPE_DIYSITE = 0;
	public static final Integer CASHRECEIPTTYOE_DELIVER = 1;
	
	public static final String RECEIPT_ROLE_DIYSITE			= "门店";
	public static final String RECEIPT_ROLE_DELIVER			= "配送员";
	
	public static final String RECEIPT_TYPE_ALIPAY			= "支付宝";
	public static final String RECEIPT_TYPE_WECHAT			= "微信";
	public static final String RECEIPT_TYPE_UNION			= "银联";
	public static final String RECEIPT_TYPE_DIYSITE_CASH		= "门店现金";
	public static final String RECEIPT_TYPE_DIYSITE_POS		= "门店POS";
	// 新增“门店其他”
	public static final String RECEIPT_TYPE_DIYSITE_OHTER = "门店其他";
	public static final String RECEIPT_TYPE_DELIVER_CASH	= "配送现金";
	public static final String RECEIPT_TYPE_DELIVER_POS		= "配送POS";
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");
	
	//分公司id
	@Column
	private Long sobId;
	
	//APP的唯一ID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long receiptId;
	
	//收款编号
	@Column
	private String receiptNumber;
	
	//app会员ID（APP是唯一）
	@Column
	private Long userid;
	
	//app会员名称（会员姓名）
	@Column
	private String username;
	
	//app会员电话
	@Column
	private String userphone;
	
	//门店编码 
	@Column
	private String diySiteCode;
	
	//订单,电子券,预收款
	@Column
	private String receiptClass;
	
	//销售单头ID (代收款对应拆分后的订单ID)
	@Column
	private Long orderHeaderId;
	
	//销售订单号
	@Column
	private String orderNumber;
	
	//1.电子券类型,订单产品类型 HR华润,LYZ乐易装,YR莹润,
	//2.PREPAY预收款
	@Column
	private String productType;
	
	//收款类型（支付宝,微信,银联,门店现金,门店POS，配送现金,配送POS）
	@Column
	private String receiptType;
	
	//收款日期
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date receiptDate;
	
	//收款金额
	@Column
	private Double amount;
	
	//配送单的收款人角色（配送员，门店）
	@Column(length = 20)
	private String receiptRole;
	
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

	public Long getSobId() {
		return sobId;
	}

	public void setSobId(Long sobId) {
		this.sobId = sobId;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
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

	public String getReceiptClass() {
		return receiptClass;
	}

	public void setReceiptClass(String receiptClass) {
		this.receiptClass = receiptClass;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getReceiptRole() {
		return receiptRole;
	}

	public void setReceiptRole(String receiptRole) {
		this.receiptRole = receiptRole;
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
		return "TdCashReciptInf [sobId=" + sobId + ", receiptId=" + receiptId + ", receiptNumber=" + receiptNumber
				+ ", userid=" + userid + ", username=" + username + ", userphone=" + userphone + ", diySiteCode="
				+ diySiteCode + ", receiptClass=" + receiptClass + ", orderHeaderId=" + orderHeaderId + ", orderNumber="
				+ orderNumber + ", productType=" + productType + ", receiptType=" + receiptType + ", receiptDate="
				+ receiptDate + ", amount=" + amount + ", attribute1=" + attribute1 + ", attribute2=" + attribute2
				+ ", attribute3=" + attribute3 + ", attribute4=" + attribute4 + ", attribute5=" + attribute5 + "]";
	}
	
	public static String createReceiptNumber() {
		Date now = new Date();
		String middle = SDF.format(now);

		int i = (int) (Math.random() * 900) + 100;
		return "RC" + middle + i;
	}
	
}
