package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author yanle 
 * 历史销量表
 *
 */
@Entity
public class TdSalesForContinuousBuy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 城市
	@Column
	private String cityName;

	// 门店名称
	@Column
	private String diySiteName;

	// 销量类型
	@Column
	private String sType;

	// 主单号（订单显示XN开头，退单显示T开头）
	@Column
	private String mainOrderNumber;

	// 分单号
	@Column
	private String orderNumber;

	// 订单日期（下单日期）
	@Column
	private Date orderTime;
	// 客户电话
	@Column
	private String username;
	// 客户名称
	@Column 
	private String realName;
	
	//客户类型
	
	@Column
	private Boolean identityType;

	// 销顾电话
	@Column
	private String sellerUsername;

	// 导购姓名
	@Column
	private String sellerRealName;

	// 产品编号
	@Column
	private String sku;

	// 产品名称
	@Column
	private String goodsTitle;

	// 是否赠品
	@Column
	private String isGift;
	
	//商品单价
	@Column
	private Double goodsPrice;
	
	//商品数量
	@Column
	private Long goodsQuantity;
	
	//商品总价
	@Column
	private Double goodsTotalPrice;
	
	//产品现金券单价
	@Column
	private Double cashCouponPrice;
	
	
	//产品现金券数量
	@Column
	private Long cashCouponQuantity;
	
	//产品现金券总额
	@Column
	private Double cashCouponTotalPrice;
	
	//产品券单价
	@Column
	private Double productCouponPrice;
	
	//产品券数量
	@Column
	private Long productCouponQuantity;
	
	//产品券总额
	@Column
	private Double productCouponTotalPrice;
	
	@Column
	private Double salesSummary;
	
	@Column
	private Integer monthStr;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDiySiteName() {
		return diySiteName;
	}

	public void setDiySiteName(String diySiteName) {
		this.diySiteName = diySiteName;
	}

	

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

	public String getMainOrderNumber() {
		return mainOrderNumber;
	}

	public void setMainOrderNumber(String mainOrderNumber) {
		this.mainOrderNumber = mainOrderNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Boolean getIdentityType() {
		return identityType;
	}

	public void setIdentityType(Boolean identityType) {
		this.identityType = identityType;
	}

	public String getSellerUsername() {
		return sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}

	public String getSellerRealName() {
		return sellerRealName;
	}

	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getIsGift() {
		return isGift;
	}

	public void setIsGift(String isGift) {
		this.isGift = isGift;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Long getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(Long goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	public Double getGoodsTotalPrice() {
		return goodsTotalPrice;
	}

	public void setGoodsTotalPrice(Double goodsTotalPrice) {
		this.goodsTotalPrice = goodsTotalPrice;
	}

	public Double getCashCouponPrice() {
		return cashCouponPrice;
	}

	public void setCashCouponPrice(Double cashCouponPrice) {
		this.cashCouponPrice = cashCouponPrice;
	}

	public Long getCashCouponQuantity() {
		return cashCouponQuantity;
	}

	public void setCashCouponQuantity(Long cashCouponQuantity) {
		this.cashCouponQuantity = cashCouponQuantity;
	}

	public Double getCashCouponTotalPrice() {
		return cashCouponTotalPrice;
	}

	public void setCashCouponTotalPrice(Double cashCouponTotalPrice) {
		this.cashCouponTotalPrice = cashCouponTotalPrice;
	}

	public Double getProductCouponPrice() {
		return productCouponPrice;
	}

	public void setProductCouponPrice(Double productCouponPrice) {
		this.productCouponPrice = productCouponPrice;
	}

	public Long getProductCouponQuantity() {
		return productCouponQuantity;
	}

	public void setProductCouponQuantity(Long productCouponQuantity) {
		this.productCouponQuantity = productCouponQuantity;
	}

	public Double getProductCouponTotalPrice() {
		return productCouponTotalPrice;
	}

	public void setProductCouponTotalPrice(Double productCouponTotalPrice) {
		this.productCouponTotalPrice = productCouponTotalPrice;
	}

	public Double getSalesSummary() {
		return salesSummary;
	}

	public void setSalesSummary(Double salesSummary) {
		this.salesSummary = salesSummary;
	}
	
	public Integer getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(Integer monthStr) {
		this.monthStr = monthStr;
	}

	@Override
	public String toString() {
		return "TdSalesForContinuousBuy [id=" + id + ", cityName=" + cityName + ", diySiteName=" + diySiteName
				+ ", sType=" + sType + ", mainOrderNumber=" + mainOrderNumber + ", orderNumber=" + orderNumber
				+ ", orderTime=" + orderTime + ", username=" + username + ", realName=" + realName + ", identityType="
				+ identityType + ", sellerUsername=" + sellerUsername + ", sellerRealName=" + sellerRealName + ", sku="
				+ sku + ", goodsTitle=" + goodsTitle + ", isGift=" + isGift + ", goodsPrice=" + goodsPrice
				+ ", goodsQuantity=" + goodsQuantity + ", goodsTotalPrice=" + goodsTotalPrice + ", cashCouponPrice="
				+ cashCouponPrice + ", cashCouponQuantity=" + cashCouponQuantity + ", cashCouponTotalPrice="
				+ cashCouponTotalPrice + ", productCouponPrice=" + productCouponPrice + ", productCouponQuantity="
				+ productCouponQuantity + ", productCouponTotalPrice=" + productCouponTotalPrice + ", salesSummary="
				+ salesSummary + ", monthStr=" + monthStr + "]";
	}

	
	
	
	
}
