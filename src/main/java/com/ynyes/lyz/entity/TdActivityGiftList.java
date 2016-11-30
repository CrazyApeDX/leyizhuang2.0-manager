package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 促销活动实体类
 * 
 * @author
 */

@Entity
public class TdActivityGiftList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 赠品ID
	@Column
	private Long goodsId;
	
	// 赠品名称
	@Column
	private String goodsTitle;
	
	// 赠品价格
	@Column(scale=2)
	private Double goodsPrice;
	
	// 赠品缩略图
	@Column
	private String coverImageUri;
	
	// 排序号
	@Column
	private Double sortId;
	
	//品牌标题
	@Column
	private String brandTitle;
	
	//品牌id
	@Column
	private Long brandId;
	
	// 数量
	@Column
	private Long number;
	
	//商品的SKU
	@Column
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getCoverImageUri() {
		return coverImageUri;
	}

	public void setCoverImageUri(String coverImageUri) {
		this.coverImageUri = coverImageUri;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getBrandTitle() {
		return brandTitle;
	}

	public void setBrandTitle(String brandTitle) {
		this.brandTitle = brandTitle;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
