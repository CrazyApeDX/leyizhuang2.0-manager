package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 促销活动实体类
 * 
 * @author
 */

@Entity
public class TdGoodsLimit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	//id(唯一标识)
	@Column
	private Long limitId;
	// 分公司ID
	@Column
	private Long sobId;

	// 客户id
	@Column
	private Long customerId;

	// 生效日期
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDate;

	// 失效日期
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishDate;

	// 客户编码
	@Column
	private String customerNumber;

	// 客户名称
	@Column
	private String customerName;

	// 物料ID
	@Column
	private Long inventoryItemId;

	// 物料编号
	@Column
	private String itemCode;

	// 物料描述
	@Column
	private String itemDescription;

	public Long getLimitId() {
		return limitId;
	}

	public void setLimitId(Long limitId) {
		this.limitId = limitId;
	}

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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
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

	public Long getInventoryItemId() {
		return inventoryItemId;
	}

	public void setInventoryItemId(Long inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

}
