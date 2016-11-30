package com.ynyes.lyz.interfaces.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TdReturnGoodsInf extends TdInfBaseEntity
{
	//订单头ID
	@Column
	private Long rtHeaderId;
	
	//订单行ID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long rtLineId;
	
	//商品的SKU（EBS传给APP的产品编号）
	@Column
	private String sku;
	
	//商品退货数量
	@Column
	private Long quantity;
	
	//经销平摊价
	//2.要货单：B2B HR产品经销价批发给经销门店时传经销平摊价
	//4.销售订单：B2C 经销会员下HR产品订单时传经销平摊价
	//其它情况不传价格
	@Column
	private Double jxPrice;
	
	// 平摊价
	@Column
	private Double lsSharePrice;
	
	//零售平摊价
	//①会员下单传零售平摊价，
	//②直营门店下LYZ和YR产品传零售平摊价
	//其它情况不传价格
	@Column
	private Double lsPrice;
	
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

	public Long getRtLineId() {
		return rtLineId;
	}

	public void setRtLineId(Long rtLineId) {
		this.rtLineId = rtLineId;
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
	
	public Double getLsSharePrice() {
		return lsSharePrice;
	}

	public void setLsSharePrice(Double lsSharePrice) {
		this.lsSharePrice = lsSharePrice;
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
		return "TdReturnGoodsInf [rtHeaderId=" + rtHeaderId + ", rtLineId=" + rtLineId + ", sku=" + sku + ", quantity="
				+ quantity + ", jxPrice=" + jxPrice + ", lsPrice=" + lsPrice + ", attribute1=" + attribute1
				+ ", attribute2=" + attribute2 + ", attribute3=" + attribute3 + ", attribute4=" + attribute4
				+ ", attribute5=" + attribute5 + "]";
	}
	
}
