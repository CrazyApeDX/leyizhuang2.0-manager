package com.ynyes.fitment.foundation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

@Entity
@Table(name = "FIT_ORDER_GOODS")
public class FitOrderGoods extends TableEntity {

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

	@Column(length = 3, nullable = false)
	private Long brandId;

	@Column(length = 10, nullable = false)
	private String brandTitle;

	public Long getGoodsId() {
		return goodsId;
	}

	public FitOrderGoods setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
		return this;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public FitOrderGoods setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
		return this;
	}

	public String getGoodsSku() {
		return goodsSku;
	}

	public FitOrderGoods setGoodsSku(String goodsSku) {
		this.goodsSku = goodsSku;
		return this;
	}

	public String getGoodsCoverImageUri() {
		return goodsCoverImageUri;
	}

	public FitOrderGoods setGoodsCoverImageUri(String goodsCoverImageUri) {
		this.goodsCoverImageUri = goodsCoverImageUri;
		return this;
	}

	public Long getQuantity() {
		return quantity;
	}

	public FitOrderGoods setQuantity(Long quantity) {
		this.quantity = quantity;
		return this;
	}

	public Double getPrice() {
		return price;
	}

	public FitOrderGoods setPrice(Double price) {
		this.price = price;
		return this;
	}

	public Double getRealPrice() {
		return realPrice;
	}

	public FitOrderGoods setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
		return this;
	}

	public Double getTotalPrice() {
		this.totalPrice = this.price * this.quantity;
		return totalPrice;
	}

	public FitOrderGoods setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
		return this;
	}

	public Double getRealTotalPrice() {
		this.realTotalPrice = this.realPrice * this.quantity;
		return realTotalPrice;
	}

	public FitOrderGoods setRealTotalPrice(Double realTotalPrice) {
		this.realTotalPrice = realTotalPrice;
		return this;
	}

	public Long getBrandId() {
		return brandId;
	}

	public FitOrderGoods setBrandId(Long brandId) {
		this.brandId = brandId;
		return this;
	}

	public String getBrandTitle() {
		return brandTitle;
	}

	public FitOrderGoods setBrandTitle(String brandTitle) {
		this.brandTitle = brandTitle;
		return this;
	}

	public FitOrderGoods init(FitCartGoods cartGoods) {
		this.setGoodsId(cartGoods.getGoodsId()).setGoodsTitle(cartGoods.getGoodsTitle())
				.setGoodsSku(cartGoods.getGoodsSku()).setGoodsCoverImageUri(cartGoods.getImageUri())
				.setPrice(cartGoods.getPrice()).setQuantity(cartGoods.getQuantity())
				.setRealPrice(cartGoods.getRealPrice()).setBrandId(cartGoods.getBrandId())
				.setBrandTitle(cartGoods.getBrandTitle());
		this.getTotalPrice();
		this.getRealTotalPrice();
		return this;
	}
}
