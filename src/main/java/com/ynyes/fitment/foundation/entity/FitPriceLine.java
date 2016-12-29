package com.ynyes.fitment.foundation.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

@Entity
@Table(name = "FIT_PRICE_LINE")
public class FitPriceLine extends TableEntity {

	@Column(nullable = false)
	private Long headerId;
	
	@Column(nullable = false)
	private Long goodsId;
	
	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal price = new BigDecimal(1);
	
	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal realPrice = new BigDecimal(1);
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	@Column(nullable = false, unique = true)
	private Long ebsId;
	
	@Column(nullable = false, unique = true)
	private String ebsNumber;

	public Long getHeaderId() {
		return headerId;
	}

	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getEbsId() {
		return ebsId;
	}

	public void setEbsId(Long ebsId) {
		this.ebsId = ebsId;
	}

	public String getEbsNumber() {
		return ebsNumber;
	}

	public void setEbsNumber(String ebsNumber) {
		this.ebsNumber = ebsNumber;
	}
}
