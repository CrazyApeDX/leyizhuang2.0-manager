package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 指定产品优惠券模板类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月20日下午4:01:13
 */

@Entity
public class TdCouponModule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 模板名称
	@Column(length = 255)
	private String title;

	// 城市编号
	@Column
	private Long cityId;

	// 城市名称
	@Column
	private String cityTitle;

	// 模板类型（0.代表是商品；1. 代表是券）
	@Column
	private Long type;

	// 商品的id
	@Column
	private Long goodsId;

	// 商品名称
	@Column
	private String goodsTitle;

	// 商品的图片
	@Column
	private String picUri;

	// 商品的SKU
	@Column
	private String sku;

	// 商品的品牌id
	@Column
	private Long brandId;

	// 商品的品牌名称
	@Column
	private String brandTitle;

	// 价值
	@Column(scale = 2)
	private Double price;

	// 排序号
	@Column(scale = 2)
	private Double sortId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityTitle() {
		return cityTitle;
	}

	public void setCityTitle(String cityTitle) {
		this.cityTitle = cityTitle;
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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getBrandTitle() {
		return brandTitle;
	}

	public void setBrandTitle(String brandTitle) {
		this.brandTitle = brandTitle;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public String getPicUri() {
		return picUri;
	}

	public void setPicUri(String picUri) {
		this.picUri = picUri;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
}
