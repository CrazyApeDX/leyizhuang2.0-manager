package com.ynyes.fitment.foundation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

@Entity
@Table(name = "FIT_CART_GOODS")
public class FitCartGoods extends TableEntity {

	@Column(nullable = false)
	private Long employeeId;
	
	@Column(nullable = false)
	private Long goodsId;
	
	@Column(nullable = false)
	private String goodsTitle;
	
	@Column(length = 30, nullable = false)
	private String goodsSku;
	
	@Column(length = 80, nullable = false)
	private String imageUri;
	
	@Column(nullable = false)
	private Long quantity = 1l;
	
	@Column(scale = 2, nullable = false)
	private Double price = 0d;
	
	@Column(scale = 2, nullable = false)
	private Double totalPrice;

	public Long getEmployeeId() {
		return employeeId;
	}

	public FitCartGoods setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public FitCartGoods setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
		return this;
	}

	public String getGoodsSku() {
		return goodsSku;
	}

	public FitCartGoods setGoodsSku(String goodsSku) {
		this.goodsSku = goodsSku;
		return this;
	}

	public String getImageUri() {
		return imageUri;
	}

	public FitCartGoods setImageUri(String imageUri) {
		this.imageUri = imageUri;
		return this;
	}

	public Long getQuantity() {
		return quantity;
	}

	public FitCartGoods setQuantity(Long quantity) {
		this.quantity = quantity;
		return this;
	}

	public Double getPrice() {
		return price;
	}

	public FitCartGoods setPrice(Double price) {
		this.price = price;
		return this;
	}

	public Double getTotalPrice() {
		this.totalPrice = this.price * this.quantity;
		return totalPrice;
	}

	public FitCartGoods setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
		return this;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public FitCartGoods setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
		return this;
	}
}
