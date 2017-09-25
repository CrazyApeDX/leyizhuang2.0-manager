package com.ynyes.fitment.foundation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

@Entity
@Table(name = "FIT_PRICE_HEADER")
public class FitPriceHeader extends TableEntity {

	@Column(length = 15, nullable = false, unique = true)
	private String title;
	
	@Column(length = 10, nullable = false)
	private Long sobId;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	@Column(nullable = false)
	private Boolean isEnable = true;
	
	@Column(length = 5, nullable = false)
	private String priceType;
	
	@Column(nullable = false, unique = true)
	private Long ebsId;

	@Column
	private String ebsNumber;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getSobId() {
		return sobId;
	}

	public void setSobId(Long sobId) {
		this.sobId = sobId;
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

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
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
