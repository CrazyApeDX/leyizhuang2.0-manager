package com.ynyes.fitment.foundation.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "FIT_COMPANY_REPORT")
public class FitCompanyReport {
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");

	@Id
	@GenericGenerator(name= "uuidGenerator",strategy = "uuid")
	private String id;
	
	// 变更后信用余额
	@Column(scale = 2, nullable = false, updatable = false)
	private Double afterChange;

	// 变更额度
	@Column(scale = 2, nullable = false, updatable = false)
	private Double money;

	// 变更时间
	@Column(nullable = false, updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date changeTime;
	
	// 涉及单号
	@Column(length = 30, updatable = false, nullable = false)
	private String referenceNumber;

	// 变更类型
	@Column(length = 20, nullable = false, updatable = false)
	private String type;

	// 操作人账号
	@Column(nullable = false, updatable = false)
	private String operatorUserName;

	// 操作描述
	@Column(length = 255, updatable = false, nullable = false)
	private String remark;

	// 相关装饰公司名
	@Column(nullable = false, updatable = false)
	private String companyTitle;
	
	// 相关装饰公司编码
	@Column(nullable = false, updatable = false)
	private String companyCode;
	
	// 到账日期
	@Column(nullable = false, updatable = false)
	private String arrivalTime;
	
	//手写备注
	@Column(nullable = false, updatable = false)
	private String writtenRemarks;
	
	//城市
	@Column(nullable = false, updatable = false)
	private String city;
	
	// 变更后现金返利余额
	@Column(scale = 2, nullable = false, updatable = false)
	private Double afterChangePromotion;
	
	// 总余额
	@Column(scale = 2, nullable = false, updatable = false)
	private Double totalBalance;

	public Double getAfterChange() {
		return afterChange;
	}

	public void setAfterChange(Double afterChange) {
		this.afterChange = afterChange;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperatorUserName() {
		return operatorUserName;
	}

	public void setOperatorUserName(String operatorUserName) {
		this.operatorUserName = operatorUserName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyTitle() {
		return companyTitle;
	}

	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getWrittenRemarks() {
		return writtenRemarks;
	}

	public void setWrittenRemarks(String writtenRemarks) {
		this.writtenRemarks = writtenRemarks;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getAfterChangePromotion() {
		return afterChangePromotion;
	}

	public void setAfterChangePromotion(Double afterChangePromotion) {
		this.afterChangePromotion = afterChangePromotion;
	}

	public Double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}
	
}
