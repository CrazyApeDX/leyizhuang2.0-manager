package com.ynyes.fitment.foundation.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

/**
 * 装饰公司实体模型
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
	
	// 信用金
	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal credit = new BigDecimal(0);
	
	// 冻结下单
	@Column(nullable = false)
	private Boolean frozen = false;
	
	// 冻结结束时间
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date frozenEndTime = new Date();
	
	/* 各个产品线价目表头id，实际上这种设计师不够灵活的，考虑到时间问题和二期即将开始的情况，可以临时使用这种形式 */
	
	@Column(nullable = false)
	private Long LsPriceHeaderId = 0l;
	
	@Column(nullable = false)
	private Long YrPriceHeaderId = 0l;
	
	@Column(nullable = false)
	private Long LyzPriceHeaderId = 0l;
	
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
	
	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public Boolean getFrozen() {
		return frozen;
	}

	public void setFrozen(Boolean frozen) {
		this.frozen = frozen;
	}

	public Date getFrozenEndTime() {
		return frozenEndTime;
	}

	public void setFrozenEndTime(Date frozenEndTime) {
		this.frozenEndTime = frozenEndTime;
	}

	public Long getLsPriceHeaderId() {
		return LsPriceHeaderId;
	}

	public void setLsPriceHeaderId(Long lsPriceHeaderId) {
		LsPriceHeaderId = lsPriceHeaderId;
	}

	public Long getYrPriceHeaderId() {
		return YrPriceHeaderId;
	}

	public void setYrPriceHeaderId(Long yrPriceHeaderId) {
		YrPriceHeaderId = yrPriceHeaderId;
	}

	public Long getLyzPriceHeaderId() {
		return LyzPriceHeaderId;
	}

	public void setLyzPriceHeaderId(Long lyzPriceHeaderId) {
		LyzPriceHeaderId = lyzPriceHeaderId;
	}
}
