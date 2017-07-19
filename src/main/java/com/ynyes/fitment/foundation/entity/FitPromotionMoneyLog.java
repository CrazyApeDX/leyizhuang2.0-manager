package com.ynyes.fitment.foundation.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.ynyes.fitment.core.constant.CreditChangeType;
import com.ynyes.fitment.core.constant.CreditOperator;
import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

@Entity
@Table(name = "FIT_PROMOTION_MONEY_LOG")
public class FitPromotionMoneyLog extends TableEntity {

	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");

	// 变更前余额
	@Column(scale = 2, nullable = false, updatable = false)
	private Double beforeChange;

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

	// 操作人类型
	@Column(length = 10, nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private CreditOperator operatorType;

	// 操作人id
	@Column(nullable = false, updatable = false)
	private Long operatorId;
	
	// 操作人账号
	@Column(nullable = false, updatable = false)
	private String operatorUserName;

	// 操作描述
	@Column(length = 255, updatable = false, nullable = false)
	private String remark = "";

	// 相关装饰公司名
	@Column(nullable = false, updatable = false)
	private String companyTitle;

	// 相关装饰公司ID
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
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

	public Double getBeforeChange() {
		return beforeChange;
	}

	public FitPromotionMoneyLog setBeforeChange(Double beforeChange) {
		this.beforeChange = beforeChange;
		return this;
	}

	public Double getAfterChange() {
		return afterChange;
	}

	public FitPromotionMoneyLog setAfterChange(Double afterChange) {
		this.afterChange = afterChange;
		if (null == this.afterChangePromotion) {
			this.afterChangePromotion = 0d;
		}
		this.totalBalance = this.afterChangePromotion + this.afterChange;
		return this;
	}

	public Double getMoney() {
		return money;
	}

	public FitPromotionMoneyLog setMoney(Double money) {
		this.money = money;
		return this;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public FitPromotionMoneyLog setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
		return this;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public FitPromotionMoneyLog setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
		return this;
	}

	public String getType() {
		return type;
	}

	public FitPromotionMoneyLog setType(String type) {
		this.type = type;
		return this;
	}

	public CreditOperator getOperatorType() {
		return operatorType;
	}

	public FitPromotionMoneyLog setOperatorType(CreditOperator operatorType) {
		this.operatorType = operatorType;
		return this;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public FitPromotionMoneyLog setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public FitPromotionMoneyLog setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public String getCompanyTitle() {
		return companyTitle;
	}

	public FitPromotionMoneyLog setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
		return this;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public FitPromotionMoneyLog setCompanyId(Long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String initManagerOperateNumber() {
		StringBuffer buffer = new StringBuffer("MO").append(FORMATTER.format(new Date()))
				.append((int) (Math.random() * 900) + 100);
		return buffer.toString();
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public FitPromotionMoneyLog setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
		return this;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public FitPromotionMoneyLog setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
		return this;
	}

	public String getWrittenRemarks() {
		return writtenRemarks;
	}

	public FitPromotionMoneyLog setWrittenRemarks(String writtenRemarks) {
		this.writtenRemarks = writtenRemarks;
		return this;
	}

	public String getCity() {
		return city;
	}

	public FitPromotionMoneyLog setCity(String city) {
		this.city = city;
		return this;
	}

	public Double getAfterChangePromotion() {
		return afterChangePromotion;
	}

	public FitPromotionMoneyLog setAfterChangePromotion(Double afterChangePromotion) {
		this.afterChangePromotion = afterChangePromotion;
		if (null == this.afterChange) {
			this.afterChange = 0d;
		}
		this.totalBalance = this.afterChangePromotion + this.afterChange;
		return this;
	}

	public Double getTotalBalance() {
		return totalBalance;
	}

	public FitPromotionMoneyLog setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
		return this;
	}
	
	public String getOperatorUserName() {
		return operatorUserName;
	}

	public void setOperatorUserName(String operatorUserName) {
		this.operatorUserName = operatorUserName;
	}
}
