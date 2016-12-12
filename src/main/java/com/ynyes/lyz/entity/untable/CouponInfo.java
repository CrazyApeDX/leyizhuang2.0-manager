package com.ynyes.lyz.entity.untable;

/**
 * <p>标题：CouponInfo.java</p>
 * <p>描述：退券信息</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年8月30日上午10:58:46
 */
public class CouponInfo {

	// 商品id
	private Long goodsId;
	
	// 商品标题
	private String goodsTitle;
	
	private String sku;
	
	// 商品封面图案
	private String goodsCoverImageUri;
	
	// 交易价格
	private Double price;
	
	// 可退数量
	private Long quantity;
	
	// 交易数量
	private Long tradeQuantity;
	
	// 总价格
	private Double totalPrice;
	
	// 交易时商品的价值
	private Double tradePrice;
	
	// 交易时商品总价值
	private Double tradeTotal;
	
	// 退货单价
	private Double returnUnit;
	
	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(Double tradePrice) {
		this.tradePrice = tradePrice;
	}

	public Double getReturnUnit() {
		return returnUnit;
	}

	public void setReturnUnit(Double returnUnit) {
		this.returnUnit = returnUnit;
	}

	public Double getTradeTotal() {
		tradeTotal = tradePrice * tradeQuantity;
		return tradeTotal;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getGoodsCoverImageUri() {
		return goodsCoverImageUri;
	}

	public void setGoodsCoverImageUri(String goodsCoverImageUri) {
		this.goodsCoverImageUri = goodsCoverImageUri;
	}

	public void setTradeTotal(Double tradeTotal) {
		this.tradeTotal = tradeTotal;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getTradeQuantity() {
		return tradeQuantity;
	}

	public void setTradeQuantity(Long tradeQuantity) {
		this.tradeQuantity = tradeQuantity;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
}
