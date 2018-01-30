package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class TdReconciliation{
	@Id
	@GenericGenerator(name= "paymentableGenerator",strategy = "uuid")
	private String id;
	//下单时间
	@Column
	private Date orderTime;
	//会员id
	@Column
	private Long userId;
	//会员姓名
	@Column
	private String realName;
	//主单号
	@Column
	private String mainOrderNumber;
	//配送方式
	@Column
	private String deliverTypeTitle;
	//门店名称
	@Column
	private String diySiteName;
	//门店类型
	@Column
	private String diySiteType;
	//导购姓名
	@Column
	private String sellerRealName;
	// 商品总金额
	@Column
	private Double totalGoodsPrice = 0d;
	// 会员折扣
	@Column
	private Double memberDiscount = 0d;
	// 满减金额
	@Column
	private Double activitySub = 0d;
	// 产品券扣减金额
	@Column
	private Double proCouponFee = 0d;
	// 现金券扣减金额
	@Column
	private Double cashCouponFee = 0d;
	// 预存款使用额
	@Column
	private Double balanceUsed = 0d;
	// 支付宝支付金额
	@Column
	private Double aliPay = 0d;
	// 微信支付金额
	@Column
	private Double wechatPay = 0d;
	// 银联支付金额
	@Column
	private Double unionPay = 0d;
	// 运费
	@Column
	private Double deliveryFee = 0d;
	// 应付金额
	@Column
	private Double leftPrice = 0d;
	// 配送收款现金
	@Column
	private Double deliveryCash = 0d;
	// 配送收款POS
	@Column
	private Double deliveryPos = 0d;
	// 导购收款现金
	@Column
	private Double sellerCash = 0d;
	// 导购收款POS
	@Column
	private Double sellerPos = 0d;
	// 导购收款其他
	@Column
	private Double sellerOther = 0d;
	// 收款之后的欠款金额
	@Column
	private Double due = 0d;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMainOrderNumber() {
		return mainOrderNumber;
	}
	public void setMainOrderNumber(String mainOrderNumber) {
		this.mainOrderNumber = mainOrderNumber;
	}
	public String getDeliverTypeTitle() {
		return deliverTypeTitle;
	}
	public void setDeliverTypeTitle(String deliverTypeTitle) {
		this.deliverTypeTitle = deliverTypeTitle;
	}
	public String getDiySiteName() {
		return diySiteName;
	}
	public void setDiySiteName(String diySiteName) {
		this.diySiteName = diySiteName;
	}
	public String getDiySiteType() {
		return diySiteType;
	}
	public void setDiySiteType(String diySiteType) {
		this.diySiteType = diySiteType;
	}
	public String getSellerRealName() {
		return sellerRealName;
	}
	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
	}
	public Double getTotalGoodsPrice() {
		return totalGoodsPrice;
	}
	public void setTotalGoodsPrice(Double totalGoodsPrice) {
		this.totalGoodsPrice = totalGoodsPrice;
	}
	public Double getMemberDiscount() {
		return memberDiscount;
	}
	public void setMemberDiscount(Double memberDiscount) {
		this.memberDiscount = memberDiscount;
	}
	public Double getActivitySub() {
		return activitySub;
	}
	public void setActivitySub(Double activitySub) {
		this.activitySub = activitySub;
	}
	public Double getProCouponFee() {
		return proCouponFee;
	}
	public void setProCouponFee(Double proCouponFee) {
		this.proCouponFee = proCouponFee;
	}
	public Double getCashCouponFee() {
		return cashCouponFee;
	}
	public void setCashCouponFee(Double cashCouponFee) {
		this.cashCouponFee = cashCouponFee;
	}
	public Double getBalanceUsed() {
		return balanceUsed;
	}
	public void setBalanceUsed(Double balanceUsed) {
		this.balanceUsed = balanceUsed;
	}
	public Double getAliPay() {
		return aliPay;
	}
	public void setAliPay(Double aliPay) {
		this.aliPay = aliPay;
	}
	public Double getWechatPay() {
		return wechatPay;
	}
	public void setWechatPay(Double wechatPay) {
		this.wechatPay = wechatPay;
	}
	public Double getUnionPay() {
		return unionPay;
	}
	public void setUnionPay(Double unionPay) {
		this.unionPay = unionPay;
	}
	public Double getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public Double getLeftPrice() {
		return leftPrice;
	}
	public void setLeftPrice(Double leftPrice) {
		this.leftPrice = leftPrice;
	}
	public Double getDeliveryCash() {
		return deliveryCash;
	}
	public void setDeliveryCash(Double deliveryCash) {
		this.deliveryCash = deliveryCash;
	}
	public Double getDeliveryPos() {
		return deliveryPos;
	}
	public void setDeliveryPos(Double deliveryPos) {
		this.deliveryPos = deliveryPos;
	}
	public Double getSellerCash() {
		return sellerCash;
	}
	public void setSellerCash(Double sellerCash) {
		this.sellerCash = sellerCash;
	}
	public Double getSellerPos() {
		return sellerPos;
	}
	public void setSellerPos(Double sellerPos) {
		this.sellerPos = sellerPos;
	}
	public Double getSellerOther() {
		return sellerOther;
	}
	public void setSellerOther(Double sellerOther) {
		this.sellerOther = sellerOther;
	}
	public Double getDue() {
		return due;
	}
	public void setDue(Double due) {
		this.due = due;
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
	

}
