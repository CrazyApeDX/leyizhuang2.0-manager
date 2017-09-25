package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TD_DIY_SITE_PRICE")
public class TdDiySitePrice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private Long sobId;
	
	@Column(nullable = false)
	private Long assignId;
	
	@Column(nullable = false)
	private Long listHeaderId;
	
	@Column(length = 240, nullable = false)
	private String name;
	
	@Column(length = 150, nullable = false)
	private String priceType;
	
	@Column(length = 150, nullable = false)
	private String priceTypeDesc;
	
	@Column(length = 150, nullable = false)
	private String storeCode;
	
	@Column(length = 150, nullable = false)
	private String custTypeCode;
	
	@Column(nullable = false)
	private Long customerId;
	
	@Column(length = 30, nullable = false)
	private String customerNumber;
	
	@Column(length = 100)
	private String customerName;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDateActive;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDateActive;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date saveTime;
	
	@Column(length = 100)
	private String attribute1;
	
	@Column(length = 100)
	private String attribute2;
	
	@Column(length = 100)
	private String attribute3;
	
	@Column(length = 100)
	private String attribute4;
	
	@Column(length = 100)
	private String attribute5;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSobId() {
		return sobId;
	}

	public void setSobId(Long sobId) {
		this.sobId = sobId;
	}

	public Long getAssignId() {
		return assignId;
	}

	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}

	public Long getListHeaderId() {
		return listHeaderId;
	}

	public void setListHeaderId(Long listHeaderId) {
		this.listHeaderId = listHeaderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getCustTypeCode() {
		return custTypeCode;
	}

	public void setCustTypeCode(String custTypeCode) {
		this.custTypeCode = custTypeCode;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
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

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getPriceTypeDesc() {
		return priceTypeDesc;
	}

	public void setPriceTypeDesc(String priceTypeDesc) {
		this.priceTypeDesc = priceTypeDesc;
	}

	public Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	@Override
	public String toString() {
		return "TdDiySitePrice [id=" + id + ", sobId=" + sobId + ", assignId=" + assignId + ", listHeaderId="
				+ listHeaderId + ", name=" + name + ", priceType=" + priceType + ", priceTypeDesc=" + priceTypeDesc
				+ ", storeCode=" + storeCode + ", custTypeCode=" + custTypeCode + ", customerId=" + customerId
				+ ", customerNumber=" + customerNumber + ", customerName=" + customerName + ", startDateActive="
				+ startDateActive + ", endDateActive=" + endDateActive + ", saveTime=" + saveTime + ", attribute1="
				+ attribute1 + ", attribute2=" + attribute2 + ", attribute3=" + attribute3 + ", attribute4="
				+ attribute4 + ", attribute5=" + attribute5 + "]";
	}
}
