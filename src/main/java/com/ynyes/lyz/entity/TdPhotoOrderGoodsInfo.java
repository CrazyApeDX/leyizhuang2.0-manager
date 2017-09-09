package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 拍照下单商品类
 * 
 * @author panjie
 *		created on 2017/09/06
 */
@Entity
@Table(name = "TD_PHOTO_ORDER_GOODS_INFO")
public class TdPhotoOrderGoodsInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 订单id(nullable = false)
	@Column(length = 20, nullable = false)
	private Long photoOrderId;
	
	// 商品id(nullable = false)
	@Column(length = 20, nullable = false)
	private Long goodsId;
	
	// 所属用户名(nullable = false)
	@Column(length = 225, nullable = false)
	private String goodsName;
	
	// 商品数量
	@Column(nullable = false)
	private Integer quantity;
	
	// 销售价
	@Column(nullable = false, scale = 2)
	private Double salePrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPhotoOrderId() {
		return photoOrderId;
	}

	public void setPhotoOrderId(Long photoOrderId) {
		this.photoOrderId = photoOrderId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	
	
}
