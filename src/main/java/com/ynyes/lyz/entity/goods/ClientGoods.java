package com.ynyes.lyz.entity.goods;

public class ClientGoods {

	private Long goodsId;
	private Long brandId;
	private String brandTitle;
	private String goodsTitle;
	private String goodsSku;
	private String goodsCoverImageUri;
	private Boolean isColorful;
	private Long inventory;
	private Boolean isPromotion;
	private Double salePrice;
	
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
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
	public Boolean getIsColorful() {
		return isColorful;
	}
	public void setIsColorful(Boolean isColorful) {
		this.isColorful = isColorful;
	}
	public Long getInventory() {
		return inventory;
	}
	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}
	public Boolean getIsPromotion() {
		return isPromotion;
	}
	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
}
