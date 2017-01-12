package com.ynyes.fitment.foundation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

@Entity
@Table(name = "FIT_COMPANY_GOODS")
public class FitCompanyGoods extends TableEntity {

	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long goodsId;
	
	@Column(length = 80, nullable = false)
	private String goodsTitle;
	
	@Column(length = 30, nullable = false)
	private String goodsSku;
	
	@Column(length = 80, nullable = false)
	private String goodsCoverImageUri = "/images/null.png";
	
	@Column(nullable = false)
	private Long categoryId;
	
	@Column(nullable = false)
	private Double goodsSortId = 99.9d;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}
	
	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Double getGoodsSortId() {
		return goodsSortId;
	}

	public void setGoodsSortId(Double goodsSortId) {
		this.goodsSortId = goodsSortId;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}
