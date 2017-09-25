package com.ynyes.lyz.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TD_USER_CREDIT_LOG")
public class TdUserCreditLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(scale = 2, nullable = false, updatable = false)
	private Double brforeChange;

	@Column(scale = 2, nullable = false, updatable = false)
	private Double afterChange;

	@Column(scale = 2, nullable = false, updatable = false)
	private Double changeValue;

	@Column(nullable = false, updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date changeTime;

	@Column(length = 30, nullable = false, updatable = false)
	private String referenceNumber;

	@Column(length = 10, nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private CreditChangeType type;

	@Column(length = 20, nullable = false, updatable = false)
	private Long sellerId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getBrforeChange() {
		return brforeChange;
	}

	public void setBrforeChange(Double brforeChange) {
		this.brforeChange = brforeChange;
	}

	public Double getAfterChange() {
		return afterChange;
	}

	public void setAfterChange(Double afterChange) {
		this.afterChange = afterChange;
	}

	public Double getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(Double changeValue) {
		this.changeValue = changeValue;
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

	public CreditChangeType getType() {
		return type;
	}

	public void setType(CreditChangeType type) {
		this.type = type;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
}
