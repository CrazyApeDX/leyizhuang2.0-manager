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
@Table(name = "td_delivery_fee_change_log")
public class TdDeliveryFeeChangeLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String manager;
	
	@Column(nullable = false)
	private String orderNumber;
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date operationTime;
	
	@Column(nullable = false, scale = 2)
	private Double oldFee;
	
	@Column(nullable = false, scale = 2)
	private Double newFee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public Double getOldFee() {
		return oldFee;
	}

	public void setOldFee(Double oldFee) {
		this.oldFee = oldFee;
	}

	public Double getNewFee() {
		return newFee;
	}

	public void setNewFee(Double newFee) {
		this.newFee = newFee;
	}

	@Override
	public String toString() {
		return "TdDeliveryFeeChangeLog [id=" + id + ", manager=" + manager + ", orderNumber=" + orderNumber
				+ ", operationTime=" + operationTime + ", oldFee=" + oldFee + ", newFee=" + newFee + "]";
	}
	
	
}
