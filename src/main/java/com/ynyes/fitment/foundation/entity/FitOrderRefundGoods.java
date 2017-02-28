package com.ynyes.fitment.foundation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

@Entity
@Table(name = "FIT_ORDER_REFUND_GOODS")
public class FitOrderRefundGoods extends TableEntity {

	@Column(length = 20, nullable = false, updatable = false)
	private Long goodsId;

	@Column(length = 80, nullable = false, updatable = false)
	private String goodsTitle;

	@Column(length = 50, nullable = false, updatable = false)
	private String goodsSku;

	@Column(length = 100, nullable = false, updatable = false)
	private String goodsCoverImageUri;

	@Column(length = 5, nullable = false, updatable = false)
	private Long quantity;

	@Column(scale = 2, nullable = false, updatable = false)
	private Double price;

	@Column(scale = 2, nullable = false, updatable = false)
	private Double realPrice;

	@Column(scale = 2, nullable = false, updatable = false)
	private Double totalPrice;

	@Column(scale = 2, nullable = false, updatable = false)
	private Double realTotalPrice;

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

	public String getGoodsSku() {
		return goodsSku;
	}

	public void setGoodsSku(String goodsSku) {
		this.goodsSku = goodsSku;
	}

	public String getGoodsCoverImageUri() {
		return goodsCoverImageUri;
	}

	public void setGoodsCoverImageUri(String goodsCoverImageUri) {
		this.goodsCoverImageUri = goodsCoverImageUri;
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

	public Double getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getRealTotalPrice() {
		return realTotalPrice;
	}

	public void setRealTotalPrice(Double realTotalPrice) {
		this.realTotalPrice = realTotalPrice;
	}
}
