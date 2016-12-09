package com.ynyes.lyz.interfaces.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TdOrderGoodsInf extends TdInfBaseEntity {

	//订单头ID
	@Column
	private Long orderHeaderId;

	//订单行ID（APP唯一）
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderLineId;

	//APP商品ID
	@Column
	private Long goodsId;

	//APP商lumn
	@Column
	private String goodsTitle;

	//APP商品简介
	@Column
	private String goodsSubTitle;

	//商品的SKU（EBS传给APP的产品编号）
	@Column
	private String sku;

	//商品数量
	@Column
	private Long quantity;

	//经销价
	//2.要货单：B2B HR产品经销价批发给经销门店时传经销价
	//4.销售订单：B2C 经销会员下HR产品订单时传经销价 
	//其它情况不传价格
	@Column(scale = 2)
	private Double jxPrice;

	//零售价
	//①会员下单传零售价，
	//②直营门店下LYZ和YR产品传零售价
	//其它情况不传价格
	@Column
	private Double lsPrice;

	//赠品标识 Y 赠品,N 非赠品
	@Column(length = 2)
	private String giftFlag;

	//活动标识 (APP活动标识ID组合,多个活动标识ID用,分隔开组合)
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

	public Long getOrderLineId() {
		return orderLineId;
	}

	public void setOrderLineId(Long orderLineId) {
		this.orderLineId = orderLineId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getGoodsSubTitle() {
		return goodsSubTitle;
	}

	public void setGoodsSubTitle(String goodsSubTitle) {
		this.goodsSubTitle = goodsSubTitle;
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

	public Double getJxPrice() {
		return jxPrice;
	}

	public void setJxPrice(Double jxPrice) {
		this.jxPrice = jxPrice;
	}

	public Double getLsPrice() {
		return lsPrice;
	}

	public void setLsPrice(Double lsPrice) {
		this.lsPrice = lsPrice;
	}

	public String getGiftFlag() {
		return giftFlag;
	}

	public void setGiftFlag(String giftFlag) {
		this.giftFlag = giftFlag;
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
		return "TdOrderGoodsInf [orderHeaderId=" + orderHeaderId + ", orderLineId=" + orderLineId + ", goodsId="
				+ goodsId + ", goodsTitle=" + goodsTitle + ", goodsSubTitle=" + goodsSubTitle + ", sku=" + sku
				+ ", quantity=" + quantity + ", jxPrice=" + jxPrice + ", lsPrice=" + lsPrice + ", giftFlag=" + giftFlag
				+ ", promotion=" + promotion + ", attribute1=" + attribute1 + ", attribute2=" + attribute2
				+ ", attribute3=" + attribute3 + ", attribute4=" + attribute4 + ", attribute5=" + attribute5 + "]";
	}
}
