package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 代收款报表
 * @author zp
 *
 */
@Entity
public class TdGathering {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	//门店名称
	@Column
	private String diySiteName;
	//门店电话
	@Column
	private String diySitePhone;
	//主单号
	@Column
	private String mainOrderNumber;
	//分单号
	@Column
	private String orderNumber;
	//品牌
	@Column
	private String brandTitle;
	//导购
	@Column
	private String sellerRealName;
	//订单时间
	@Column
	private Date orderTime;
	//配送方式
	@Column
	private String deliverTypeTitle;
	//可提现金额
	@Column(scale = 2)
	private Double cashBalanceUsed;
	//不可提现金额
	@Column(scale = 2)
	private Double unCashBalanceUsed;
	//现金卷额度
	@Column
	private Double cashCoupon;
	//使用产品券情况
	@Column
	private String productCoupon;
	//代收款金额
	@Column(scale = 2)
	private Double totalPrice;
	//实际代收款金额
	@Column(scale = 2)
	private Double payed;
	//支付方式
	@Column
	private String payTypeTitle;
	//第三方支付金额
	@Column(scale = 2)
	private Double otherPay;
	//欠款
	@Column(scale = 2)
	private Double owned;
	//配送人员
	@Column
	private String realName;
	//配送人电话
	@Column
	private String username;
	//收获人
	@Column
	private String shippingName;
	//收货人电话
	@Column
	private String shippingPhone;
	//收货人地址
	@Column
	private String shippingAddress;
	//备注信息
	@Column
	private String remark;
	
	//订单状态
	@Column
	private Long statusId;
	//仓库名称
	@Column
	private String whNo;
	//订单总金额
	@Column(scale = 2)
	private Double totalGoodsPrice;
	//预约配送日期
	@Column
	private String deliveryDate;
	//预约配送时间段
	@Column
	private Long deliveryDetailId;
	//实际配送时间
	@Column
	private Date deliveryTime;
	// 城市
	@Column
	private String cityName;
	// 配送门店编号
	@Column
	private String diySiteCode;
	//创建人
	private String createUsername;
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
	public String getDiySitePhone() {
		return diySitePhone;
	}
	public void setDiySitePhone(String diySitePhone) {
		this.diySitePhone = diySitePhone;
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
	public String getBrandTitle() {
		return brandTitle;
	}
	public void setBrandTitle(String brandTitle) {
		this.brandTitle = brandTitle;
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
	public Double getCashBalanceUsed() {
		return cashBalanceUsed;
	}
	public void setCashBalanceUsed(Double cashBalanceUsed) {
		this.cashBalanceUsed = cashBalanceUsed;
	}
	public Double getUnCashBalanceUsed() {
		return unCashBalanceUsed;
	}
	public void setUnCashBalanceUsed(Double unCashBalanceUsed) {
		this.unCashBalanceUsed = unCashBalanceUsed;
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
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getPayed() {
		return payed;
	}
	public void setPayed(Double payed) {
		this.payed = payed;
	}
	public String getPayTypeTitle() {
		return payTypeTitle;
	}
	public void setPayTypeTitle(String payTypeTitle) {
		this.payTypeTitle = payTypeTitle;
	}
	public Double getOtherPay() {
		return otherPay;
	}
	public void setOtherPay(Double otherPay) {
		this.otherPay = otherPay;
	}
	public Double getOwned() {
		return owned;
	}
	public void setOwned(Double owned) {
		this.owned = owned;
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
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public String getWhNo() {
		return whNo;
	}
	public void setWhNo(String whNo) {
		this.whNo = whNo;
	}
	public Double getTotalGoodsPrice() {
		return totalGoodsPrice;
	}
	public void setTotalGoodsPrice(Double totalGoodsPrice) {
		this.totalGoodsPrice = totalGoodsPrice;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Long getDeliveryDetailId() {
		return deliveryDetailId;
	}
	public void setDeliveryDetailId(Long deliveryDetailId) {
		this.deliveryDetailId = deliveryDetailId;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getDiySiteCode() {
		return diySiteCode;
	}
	public void setDiySiteCode(String diySiteCode) {
		this.diySiteCode = diySiteCode;
	}
	public String getDeliverTypeTitle() {
		return deliverTypeTitle;
	}
	public void setDeliverTypeTitle(String deliverTypeTitle) {
		this.deliverTypeTitle = deliverTypeTitle;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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
	public Long getDiyId() {
		return diyId;
	}
	public void setDiyId(Long diyId) {
		this.diyId = diyId;
	}
	
}
