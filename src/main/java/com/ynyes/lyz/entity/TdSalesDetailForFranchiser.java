package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 销售明细报表for加盟商
 * 
 * @author zp
 *
 */
@Entity
public class TdSalesDetailForFranchiser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	// 门店名称
	@Column
	private String diySiteName;
	// 主单号
	@Column
	private String mainOrderNumber;
	// 分单号
	@Column
	private String orderNumber;
	// 下单时间
	@Column
	private Date orderTime;
	// 订单状态
	@Column
	private Long statusId;
	// 导购姓名
	@Column
	private String sellerRealName;
	// 客户编号，取td_user表中的id值
	@Column
	private Long userId;
	// 客户名称
	@Column
	private String realName;
	// 客户电话
	@Column
	private String username;
	// 产品编号
	@Column
	private String sku;
	// 产品名称
	@Column
	private String goodsTitle;
	// 数量
	@Column
	private Long quantity;
	// 单价
	@Column
	private Double price;
	// 总价
	@Column
	private Double totalPrice;
	// 现金卷
	@Column
	private Double cashCoupon;
	// 品牌类型
	@Column
	private String brandTitle;
	// 商品父类型
	@Column
	private String goodsParentTypeTitle;
	// 商品类型
	@Column
	private String goodsTypeTitle;
	// 配送方式
	@Column
	private String deliverTypeTitle;
	// 中转仓
	@Column
	private String whName;
	// 配送人员
	@Column
	private String deliverRealName;
	// 配送人员电话
	@Column
	private String deliverUsername;
	// 收货人姓名
	@Column
	private String shippingName;
	// 收货人电话
	@Column
	private String shippingPhone;
	// 收货人地址
	@Column
	private String shippingAddress;
	// 订单备注
	@Column
	private String remark;
	// 城市
	@Column
	private String cityName;
	// 配送门店id
	@Column
	private String diySiteCode;
	// 门店id
	@Column
	private Long diyId;

	// 经销单价
	@Column(scale = 2)
	private Double jxPrice = 0d;

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

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getSellerRealName() {
		return sellerRealName;
	}

	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
	}

	public String getDeliverTypeTitle() {
		return deliverTypeTitle;
	}

	public void setDeliverTypeTitle(String deliverTypeTitle) {
		this.deliverTypeTitle = deliverTypeTitle;
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

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getDiyId() {
		return diyId;
	}

	public void setDiyId(Long diyId) {
		this.diyId = diyId;
	}

	public String getShippingPhone() {
		return shippingPhone;
	}

	public void setShippingPhone(String shippingPhone) {
		this.shippingPhone = shippingPhone;
	}


	public Double getCashCoupon() {
		return cashCoupon;
	}

	public void setCashCoupon(Double cashCoupon) {
		this.cashCoupon = cashCoupon;
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

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getJxPrice() {
		return jxPrice;
	}

	public void setJxPrice(Double jxPrice) {
		this.jxPrice = jxPrice;
	}
	
	

}
