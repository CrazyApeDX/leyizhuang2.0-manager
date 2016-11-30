package com.ynyes.lyz.interfaces.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class TdOrderReceiveInf  extends TdInfBaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//分公司ID
	@Column
	private Long sobId;
	
	//销售单头ID (APP唯一)
	@Column
	private Long headerId;
	
	//销售订单号(分单号)
	@Column
	private String orderNumber;
	
	//收货时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date receiveDate;
	
	//配送方式名称（送货上门，门店自提）
	@Column
	private String deliverTypeTitle;
	
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

	public Long getHeaderId() {
		return headerId;
	}

	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getDeliverTypeTitle() {
		return deliverTypeTitle;
	}

	public void setDeliverTypeTitle(String deliverTypeTitle) {
		this.deliverTypeTitle = deliverTypeTitle;
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
	
}
