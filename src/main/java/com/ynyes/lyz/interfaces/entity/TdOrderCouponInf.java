package com.ynyes.lyz.interfaces.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TdOrderCouponInf extends TdInfBaseEntity {
	
	//订单头ID
	@Column
	private Long orderHeaderId;
	
	//券行ID（APP唯一）
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long lineId;
	
	// 券类型（1.产品券，2.产品现金券，3.通用现金券，4.电子产品券，5.电子现金券） 
	@Column
	private Integer couponTypeId;
	
	//券对应的产品SKU
	@Column
	private String sku;
	
	//券的使用数量
	@Column
	private Long quantity;
	
	//使用面值价格
	//1.只有产品券无价格面值，
	//2.通用现金券使用金额超过购买产品应收总金额，通用现金券金额传计算后的面值价格）
	@Column
	private Double price;
	
	//是否历史券 Y,N
	@Column(length = 2)
	private String historyFlag;
	
	//活动标识 (活动中给会员使用的券,多个活动标识ID用,分隔开组合)
	@Column
	private String promotion;
	
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
	

	public Long getOrderHeaderId() {
		return orderHeaderId;
	}

	public void setOrderHeaderId(Long orderHeaderId) {
		this.orderHeaderId = orderHeaderId;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public Integer getCouponTypeId() {
		return couponTypeId;
	}

	public void setCouponTypeId(Integer couponTypeId) {
		this.couponTypeId = couponTypeId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getHistoryFlag() {
		return historyFlag;
	}

	public void setHistoryFlag(String historyFlag) {
		this.historyFlag = historyFlag;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
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

	@Override
	public String toString() {
		return "TdOrderCouponInf [orderHeaderId=" + orderHeaderId + ", lineId=" + lineId + ", couponTypeId="
				+ couponTypeId + ", sku=" + sku + ", quantity=" + quantity + ", price=" + price + ", historyFlag="
				+ historyFlag + ", promotion=" + promotion + ", attribute1=" + attribute1 + ", attribute2=" + attribute2
				+ ", attribute3=" + attribute3 + ", attribute4=" + attribute4 + ", attribute5=" + attribute5 + "]";
	}
}
