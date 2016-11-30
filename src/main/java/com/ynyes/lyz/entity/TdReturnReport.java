package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 退货报表
 * @author zp
 *
 */
@Entity
public class TdReturnReport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	//门店名称
	@Column
	private String diySiteName;
	//订单号
	@Column
	private String orderNumber;
	//退货单号
	@Column
	private String returnNumber;
	//退货单状态
	@Column
	private Long statusId;
	//品牌
	@Column
	private String brandTitle;
	//商品类别
	@Column
	private String categoryTitle;
	//导购
	@Column
	private String sellerRealName;
	//订单日期
	@Column
	private Date orderTime;
	//退货日期
	@Column
	private Date cancelTime;
	//客户名称
	@Column
	private String realName;
	//客户电话
	@Column
	private String username;
	//产品编号
	@Column
	private String sku;
	//产品名称
	@Column
	private String goodsTitle;
	//退货数量
	@Column
	private Long quantity;
	//退货单价
	@Column
	private Double price;
	//退货金额
	@Column
	private Double turnPrice;
	//退现金卷金额
	@Column
	private Double cashCoupon;
	//退产品卷金额
	@Column
	private String productCoupon;
	//客户备注
	@Column
	private String remarkInfo;
	//中转仓
	@Column
	private String whNo;
	//配送人员
	@Column
	private String deliverRealName;
	//配送人电话
	@Column
	private String deliverUsername;
	//退货地址
	@Column
	private String shippingAddress;
	//门店code
	@Column
	private String diySiteCode;
	//城市名称
	@Column
	private String cityName;
	//创建人
	private String createUsername;
	//配送方式
	@Column
	private String deliverTypeTitle;
	//门店id
	@Column
	private Long diyId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDiySiteName() {
		return diySiteName;
	}
	public void setDiySiteName(String diySiteName) {
		this.diySiteName = diySiteName;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getReturnNumber() {
		return returnNumber;
	}
	public void setReturnNumber(String returnNumber) {
		this.returnNumber = returnNumber;
	}
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public String getBrandTitle() {
		return brandTitle;
	}
	public void setBrandTitle(String brandTitle) {
		this.brandTitle = brandTitle;
	}
	public String getCategoryTitle() {
		return categoryTitle;
	}
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
	public String getSellerRealName() {
		return sellerRealName;
	}
	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Double getTurnPrice() {
		return turnPrice;
	}
	public void setTurnPrice(Double turnPrice) {
		this.turnPrice = turnPrice;
	}
	public Double getCashCoupon() {
		return cashCoupon;
	}
	public void setCashCoupon(Double cashCoupon) {
		this.cashCoupon = cashCoupon;
	}
	public String getProductCoupon() {
		return productCoupon;
	}
	public void setProductCoupon(String productCoupon) {
		this.productCoupon = productCoupon;
	}
	public String getRemarkInfo() {
		return remarkInfo;
	}
	public void setRemarkInfo(String remarkInfo) {
		this.remarkInfo = remarkInfo;
	}
	public String getWhNo() {
		return whNo;
	}
	public void setWhNo(String whNo) {
		this.whNo = whNo;
	}
	public String getDeliverRealName() {
		return deliverRealName;
	}
	public void setDeliverRealName(String deliverRealName) {
		this.deliverRealName = deliverRealName;
	}
	public String getDeliverUsername() {
		return deliverUsername;
	}
	public void setDeliverUsername(String deliverUsername) {
		this.deliverUsername = deliverUsername;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getDiySiteCode() {
		return diySiteCode;
	}
	public void setDiySiteCode(String diySiteCode) {
		this.diySiteCode = diySiteCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCreateUsername() {
		return createUsername;
	}
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	public String getDeliverTypeTitle() {
		return deliverTypeTitle;
	}
	public void setDeliverTypeTitle(String deliverTypeTitle) {
		this.deliverTypeTitle = deliverTypeTitle;
	}
	public Long getDiyId() {
		return diyId;
	}
	public void setDiyId(Long diyId) {
		this.diyId = diyId;
	}
	
}
