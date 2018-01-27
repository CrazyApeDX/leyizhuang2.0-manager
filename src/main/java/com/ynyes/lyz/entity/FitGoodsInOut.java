package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 装饰公司出退货报表实体
 * @author Richard
 *
 */
@Entity
public class FitGoodsInOut {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//装饰公司名称
	@Column
	private String diySiteName;
	
	//主单号（订单显示XN开头，退单显示T开头）
	@Column
	private String mainOrderNumber;
	//分单号
	@Column
	private String orderNumber;
	//订单日期（下单日期）
	@Column
	private Date orderTime;
	//销售日期（订单为出货日期，退单为验货确认日期）
	@Column
	private Date salesTime;
	//订单状态
	@Column
	private Integer statusId;
	//导购姓名
	@Column
	private String sellerRealName;
	//客户名称
	@Column
	private String realUserRealName;
	//客户电话
	@Column
	private String realUserUsername;
	
	//客户编号
	@Column
	private String userId;
	//产品编号
	
	@Column
	private String sku;
	//产品名称
	@Column
	private String goodsTitle;
	//数量
	@Column
	private Long quantity;
	
	//零售单价
	@Column
	private Double price;
	
	//结算单价
	@Column
	private Double realPrice;
	
	/*//零售总价
	@Column
	private Double totalPrice;
	
	//结算总价
	@Column
	private Double totalRealPrice;*/
	
	//品牌类型
	@Column
	private String brandTitle;
	
	//商品父分类
	@Column
	private String goodsParentTypeTitle;
	
	//商品分类
	@Column
	private String goodsTypeTitle;
	
	//配送方式
	@Column
	private String deliverTypeTitle;
	//中转仓
	@Column
	private String whName;
	//配送人员
	@Column
	private String deliverRealName;
	//配送人员电话
	@Column
	private String deliverUsername;
	//收货人姓名
	@Column
	private String shippingName;
	//收货人电话
	@Column
	private String shippingPhone;
	//收货人地址
	@Column
	private String shippingAddress;
	//订单备注
	@Column
	private String remark;
	// 城市
	@Column
	private String cityName;
	// 销售经理
	@Column
	private String salesManager;
	
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
	public Date getSalesTime() {
		return salesTime;
	}
	public void setSalesTime(Date salesTime) {
		this.salesTime = salesTime;
	}
	
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getSellerRealName() {
		return sellerRealName;
	}
	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
	}
	public String getRealUserRealName() {
		return realUserRealName;
	}
	public void setRealUserRealName(String realUserRealName) {
		this.realUserRealName = realUserRealName;
	}
	public String getRealUserUsername() {
		return realUserUsername;
	}
	public void setRealUserUsername(String realUserUsername) {
		this.realUserUsername = realUserUsername;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public Double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}
	public String getBrandTitle() {
		return brandTitle;
	}
	public void setBrandTitle(String brandTitle) {
		this.brandTitle = brandTitle;
	}
	public String getGoodsParentTypeTitle() {
		return goodsParentTypeTitle;
	}
	public void setGoodsParentTypeTitle(String goodsParentTypeTitle) {
		this.goodsParentTypeTitle = goodsParentTypeTitle;
	}
	public String getGoodsTypeTitle() {
		return goodsTypeTitle;
	}
	public void setGoodsTypeTitle(String goodsTypeTitle) {
		this.goodsTypeTitle = goodsTypeTitle;
	}
	public String getDeliverTypeTitle() {
		return deliverTypeTitle;
	}
	public void setDeliverTypeTitle(String deliverTypeTitle) {
		this.deliverTypeTitle = deliverTypeTitle;
	}
	public String getWhName() {
		return whName;
	}
	public void setWhName(String whName) {
		this.whName = whName;
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
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public String getShippingPhone() {
		return shippingPhone;
	}
	public void setShippingPhone(String shippingPhone) {
		this.shippingPhone = shippingPhone;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getSalesManager() {
		return salesManager;
	}
	public void setSalesManager(String salesManager) {
		this.salesManager = salesManager;
	}
	@Override
	public String toString() {
		return "FitGoodsInOut [id=" + id + ", diySiteName=" + diySiteName + ", mainOrderNumber=" + mainOrderNumber
				+ ", orderNumber=" + orderNumber + ", orderTime=" + orderTime + ", salesTime=" + salesTime
				+ ", statusId=" + statusId + ", sellerRealName=" + sellerRealName + ", realUserRealName="
				+ realUserRealName + ", realUserUsername=" + realUserUsername + ", userId=" + userId + ", sku=" + sku
				+ ", goodsTitle=" + goodsTitle + ", quantity=" + quantity + ", price=" + price + ", realPrice="
				+ realPrice + ", brandTitle=" + brandTitle + ", goodsParentTypeTitle=" + goodsParentTypeTitle
				+ ", goodsTypeTitle=" + goodsTypeTitle + ", deliverTypeTitle=" + deliverTypeTitle + ", whName=" + whName
				+ ", deliverRealName=" + deliverRealName + ", deliverUsername=" + deliverUsername + ", shippingName="
				+ shippingName + ", shippingPhone=" + shippingPhone + ", shippingAddress=" + shippingAddress
				+ ", remark=" + remark + ", cityName=" + cityName + ", salesManager=" + salesManager + "]";
	}
	

}
