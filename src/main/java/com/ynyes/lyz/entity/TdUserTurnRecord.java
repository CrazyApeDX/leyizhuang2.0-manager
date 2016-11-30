package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 退款记录
 * @author Max
 *
 */
@Entity
public class TdUserTurnRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 会员号
	@Column
	private String username;
	
	// 记录单号
	@Column
	private String recordNumber;
	
	// 订单号
	@Column
	private String orderNumber;
	
	// 退货单号
	@Column
	private String turnNumber;
	
	// 退货方式  1 到店退货， 2 物流取货
	@Column
	private Long turnType;
	
	// 退款金额
	@Column(scale=2)
	private Double turnPrice;
	
	// 退会可提现金额
	@Column(scale = 2)
	private Double cashBalance;

	// 退回不可提现金额
	@Column(scale = 2)
	private Double unCashBalance;
	
	// 记录时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date recordTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(String turnNumber) {
		this.turnNumber = turnNumber;
	}

	public Long getTurnType() {
		return turnType;
	}

	public void setTurnType(Long turnType) {
		this.turnType = turnType;
	}

	public Double getTurnPrice() {
		return turnPrice;
	}

	public Double getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(Double cashBalance) {
		this.cashBalance = cashBalance;
	}

	public Double getUnCashBalance() {
		return unCashBalance;
	}

	public void setUnCashBalance(Double unCashBalance) {
		this.unCashBalance = unCashBalance;
	}

	public void setTurnPrice(Double turnPrice) {
		this.turnPrice = turnPrice;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	
	
}
