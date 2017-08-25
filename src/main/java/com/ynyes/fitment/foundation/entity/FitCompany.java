package com.ynyes.fitment.foundation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

/**
 * 装饰公司实体模型
 * 
 * @author dengxiao
 */
@Entity
@Table(name = "FIT_COMPANY")
public class FitCompany extends TableEntity {

	// 装饰公司名称
	@Column(length = 15, nullable = false, unique = true)
	private String name;

	// 装饰公司编码
	@Column(length = 15, nullable = false, unique = true)
	private String code;

	// EBS提供的SOB_ID
	@Column(length = 10, nullable = false)
	private Long sobId;

	// 额度
	@Column(scale = 2, nullable = false)
	private Double creditLimit = 0d;

	// 信用金
	@Column(scale = 2, nullable = false)
	private Double credit = 0d;

	// 赞助金
	@Column(scale = 2, nullable = false)
	private Double promotionMoney = 0d;
	
	// 赞助金
	@Column(scale = 2, nullable = false)
	private Double walletMoney = 0d;

	// 还款日
	@Column(length = 2, nullable = false)
	private Integer day = 1;

	// 冻结下单
	@Column(nullable = false)
	private Boolean frozen = false;

	/* 各个产品线价目表头id，实际上这种设计师不够灵活的，考虑到时间问题和二期即将开始的情况，可以临时使用这种形式 */

	@Column(nullable = false)
	private Long lsPriceHeaderId = 0l;

	@Column(nullable = false)
	private Long yrPriceHeaderId = 0l;

	@Column(nullable = false)
	private Long lyzPriceHeaderId = 0l;

	@Column(nullable = false)
	private Long xqPriceHeaderId = 0l;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getSobId() {
		return sobId;
	}

	public void setSobId(Long sobId) {
		this.sobId = sobId;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Boolean getFrozen() {
		return frozen;
	}

	public void setFrozen(Boolean frozen) {
		this.frozen = frozen;
	}

	public Long getLsPriceHeaderId() {
		return lsPriceHeaderId;
	}

	public void setLsPriceHeaderId(Long lsPriceHeaderId) {
		this.lsPriceHeaderId = lsPriceHeaderId;
	}

	public Long getYrPriceHeaderId() {
		return yrPriceHeaderId;
	}

	public void setYrPriceHeaderId(Long yrPriceHeaderId) {
		this.yrPriceHeaderId = yrPriceHeaderId;
	}

	public Long getLyzPriceHeaderId() {
		return lyzPriceHeaderId;
	}

	public void setLyzPriceHeaderId(Long lyzPriceHeaderId) {
		this.lyzPriceHeaderId = lyzPriceHeaderId;
	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Long getXqPriceHeaderId() {
		return xqPriceHeaderId;
	}

	public void setXqPriceHeaderId(Long xqPriceHeaderId) {
		this.xqPriceHeaderId = xqPriceHeaderId;
	}

	public Double getPromotionMoney() {
		return promotionMoney;
	}

	public void setPromotionMoney(Double promotionMoney) {
		this.promotionMoney = promotionMoney;
	}

	public Double getWalletMoney() {
		return walletMoney;
	}

	public void setWalletMoney(Double walletMoney) {
		this.walletMoney = walletMoney;
	}
}
