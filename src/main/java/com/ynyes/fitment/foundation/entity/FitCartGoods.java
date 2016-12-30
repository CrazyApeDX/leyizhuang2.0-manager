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

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsSku() {
		return goodsSku;
	}

	public void setGoodsSku(String goodsSku) {
		this.goodsSku = goodsSku;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
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

	public Double getTotalPrice() {
		this.totalPrice = this.price * this.quantity;
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
