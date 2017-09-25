package com.ynyes.lyz.interfaces.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ynyes.lyz.interfaces.entity.TdInfBaseEntity;

@Entity
public class TdReturnCouponInf extends TdInfBaseEntity
{
	//退货券头id
	@Column
	private Long rtHeaderId;
	
	//券行id（app唯一）
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long lineId;
	
	//券类型（1.产品券，2.产品现金券，3.通用现金券，4.电子产品券，5.电子现金券）
	@Column
	private Integer couponTypeId;
	
	//券对应的产品SKU
	@Column(length = 50)
	private String sku;
	
	//券的退回数量
	@Column
	private Long quantity;
	
	//使用面值价格
	//（1.只有产品券无价格面值，
	//2.通用现金券使用金额超过购买产品应收总金额，通用现金券金额传计算后的面值价格）
	@Column
	private Double price;
	
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

	public Long getRtHeaderId() {
		return rtHeaderId;
	}

	public void setRtHeaderId(Long rtHeaderId) {
		this.rtHeaderId = rtHeaderId;
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
		return "TdReturnCouponInf [rtHeaderId=" + rtHeaderId + ", lineId=" + lineId + ", couponTypeId=" + couponTypeId
				+ ", sku=" + sku + ", quantity=" + quantity + ", price=" + price + ", attribute1=" + attribute1
				+ ", attribute2=" + attribute2 + ", attribute3=" + attribute3 + ", attribute4=" + attribute4
				+ ", attribute5=" + attribute5 + "]";
	}
	
}
