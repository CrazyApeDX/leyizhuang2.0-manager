package com.ynyes.lyz.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.ynyes.lyz.util.CountUtil;

/**
 * 订单
 *
 * 记录了订单详情
 * 
 * @author Sharon
 *
 */

@Entity
public class TdOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 订单号(分单号)
	@Column(unique = true)
	private String orderNumber;

	// 订单商品
	@OneToMany
	@JoinColumn(name = "tdOrderId")
	private List<TdOrderGoods> orderGoodsList;

	// 用户id
	@Column
	private Long userId;

	// 拆分的收货地址分为：城市 + 区 + 街道 + 详细地址
	// 省
	@Column
	private String province;

	// 城市
	@Column
	private String city;

	// 区
	@Column
	private String disctrict;

	// 街道
	@Column
	private String subdistrict;

	// 详细地址
	@Column
	private String detailAddress;

	// 收货地址
	@Column
	private String shippingAddress;

	// 收货人
	@Column
	private String shippingName;

	// 收货电话
	@Column
	private String shippingPhone;

	// 邮政编码
	@Column
	private String postalCode;

	// 支付方式
	@Column
	private Long payTypeId;

	// 支付方式名称
	@Column
	private String payTypeTitle;

	// 支付方式手续费
	@Column(scale = 2)
	private Double payTypeFee;

	// 配送方式名称
	@Column
	private String deliverTypeTitle;

	// 配送费用
	@Column(scale = 2)
	private Double deliverFee;

	// 配送门店id
	@Column
	private Long diySiteId;

	// 配送门店编码
	@Column
	private String diySiteCode;

	// 配送门店名称
	@Column
	private String diySiteName;

	// 配送门店联系电话
	@Column
	private String diySitePhone;

	// 物流备注
	@Column
	private String remarkInfo;

	// 下单时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderTime;

	// 取消时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cancelTime;

	// 确认时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date checkTime;

	// 付款时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date payTime;

	// 配送时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deliveryTime;

	// 发货时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;

	// 收货时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date receiveTime;

	// 评价时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date commentTime;

	// 完成时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date finishTime;

	// 取消申请时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cancelApplyTime;

	// 退款时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date refundTime;

	// 订单状态 1:待审核 2:待付款 3:待出库 4:待签收 5: 待评价 6: 已完成 7: 已取消 8:用户删除 9:退货中 10：退货确认
	// 11：退货取消 12: 退货完成
	@Column
	private Long statusId;

	// 订单类型
	@Column
	private Long typeId;

	// 是否取消订单
	@Column
	private Boolean isCancel;

	// 是否退款
	@Column
	private Boolean isRefund;

	// 退款金额
	@Column(scale = 2)
	private Double refund;

	// 退款详细
	@Column
	private String handleDetail;

	// 用户
	@Column
	private String username;

	// 配送人
	@Column
	private String distributionPerson;

	// 收款人
	@Column
	private String moneyReceivePerson;

	// 商品总金额
	@Column(scale = 2)
	private Double totalGoodsPrice;

	// 使用可提现预存款金额
	@Column(scale = 2)
	private Double cashBalanceUsed;

	// 使用不可提现预存款金额
	@Column(scale = 2)
	private Double unCashBalanceUsed;

	// 订单总金额
	@Column(scale = 2)
	private Double totalPrice;

	// 实收款
	@Column(scale = 2)
	private Double actualPay;

	// 排序号
	@Column
	private Double sortId;

	// 是否在线付款
	@Column
	private Boolean isOnlinePay;

	// 配送日期(yyyy-MM-dd)
	@Column
	private String deliveryDate;

	// 配送时间段
	@Column
	private Long deliveryDetailId;

	// 订单备注
	@Column
	private String remark;

	// 是否参加了促销
	@Column
	private Boolean isPromotion;

	// 使用现金券额度
	@Column
	private Double cashCoupon;

	// 使用产品券情况
	@Column
	private String productCoupon;

	// 使用产品券id，多个之间以","分割
	@Column
	private String productCouponId;

	// 使用现金券的id，多个之间以","分割
	@Column
	private String cashCouponId;

	// 促销活动赠送列表
	@OneToMany
	@JoinColumn(name = "presentedListId")
	private List<TdOrderGoods> presentedList;

	// 小辅料赠送列表
	@OneToMany
	@JoinColumn(name = "giftListId")
	private List<TdOrderGoods> giftGoodsList;

	// 品牌名称
	@Column
	private String brandTitle;

	// 品牌id
	@Column
	private Long brandId;

	// 使用优惠券限额
	@Column(scale = 2)
	private Double limitCash;

	// 签收图片地址
	@Column
	private String photo;

	// 是否是券
	@Column
	private Boolean isCoupon;

	// 主单号
	@Column
	private String mainOrderNumber;

	// 实际支付预存款总额
	@Column(scale = 2)
	private Double allActualPay;

	// 实际总单金额
	@Column(scale = 2)
	private Double allTotalPay;

	// 销售顾问的id
	@Column
	private Long sellerId;

	// 销售顾问的用户名
	@Column
	private String sellerUsername;

	// 销售顾问的真实姓名
	@Column
	private String sellerRealName;

	// 是否使用预存款
	@Column
	private Boolean isUsedBalance;

	// 订单用户是否绑定导购
	@Column
	private Boolean haveSeller;

	// 是否是代下单
	@Column
	private Boolean isSellerOrder;

	// 真实用户名
	@Column
	private String realUserUsername;

	// 真实用户id
	@Column
	private Long realUserId;

	// 真实用户的真实姓名
	@Column
	private String realUserRealName;

	// 第三方支付的金额
	@Column
	private Double otherPay;

	// POS支付的金额
	@Column
	private Double posPay;

	// 现金支付的金额
	@Column
	private Double cashPay;

	// 新增：2016-08-25收款方式其他，只有门店能够使用
	@Column
	private Double backOtherPay;

	// 有效时间(超过有效时间未支付将重新计算价格)
	@Column
	private Date validTime;
	// 其他收入(不参与其他计算 只影响代收金额) zp
	@Column
	private Double otherIncome;

	// 购买的优惠券使用情况
	@Column
	private String buyCouponId;

	// 促销减少的金额
	@Column(scale = 2)
	private Double activitySubPrice = 0d;

	// 是否以一口价的形式收取运费
	@Column
	private Boolean isFixedDeliveryFee;

	// 应收运费
	@Column(scale = 2)
	private Double receivableFee;

	// 分单占主单的比例
	@Column(scale = 2)
	private Double point;

	// 上楼方式
	@Column(length = 10)
	private String upstairsType = "不上楼";

	// 楼层
	@Column(length = 2)
	private Long floor = 1l;

	// 上楼费
	@Column(length = 10, scale = 2)
	private Double upstairsFee = 0d;

	// 已收上楼费（预存款）
	@Column(length = 10, scale = 2)
	private Double upstairsBalancePayed = 0d;

	// 已收上楼费（第三方）
	@Column(length = 10, scale = 2)
	private Double upstairsOtherPayed = 0d;

	// 剩余上楼费
	@Column(length = 10, scale = 2)
	private Double upstairsLeftFee = 0d;

	// 公司承担的运费
	@Column(length = 10, scale = 2)
	private Double companyDeliveryFee = 0d;

	// 装饰公司使用的信用额度
	@Column(scale = 2, nullable = false)
	private Double credit = 0d;

	// 装饰公司使用的赞助金
	@Column(scale = 2, nullable = false)
	private Double promotionMoneyPayed = 0d;

	// 经销总价
	@Column(scale = 2)
	private Double jxTotalPrice = 0d;

	// 产品券优惠券金额
	@Column(scale = 2)
	private Double proCouponFee;

	// 调色费
	@Column(scale = 2)
	private Double colorFee = 0d;

	// 会员差价金额
	@Column(scale = 2)
	private Double difFee = 0d;

	// 显示在界面上的剩余金额
	@Column(scale = 2)
	private Double notPayedFee = 0d;

	// 收货人是否是主家
	@Column
	private Boolean receiverIsMember = Boolean.FALSE;

	// 传递给wms的应收
	@Column(scale = 2)
	private Double mockReceivable;

	// 传递给WMS的代收，同时也是订单实际的代收
	@Column(scale = 2)
	private Double agencyRefund = 0d;

	// 拍照下单标志
	@Column
	private Boolean isPhotoOrder = Boolean.FALSE;
	
	//使用钱包金额
	@Column(scale = 2)
	private Double walletMoney = 0d;
	
	@Column(nullable = false, scale = 2)
	private Double alipayMoney = 0d;

	public Double getRefund() {
		return refund;
	}

	public void setRefund(Double refund) {
		this.refund = refund;
	}

	public Boolean getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Boolean isCancel) {
		this.isCancel = isCancel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHandleDetail() {
		return handleDetail;
	}

	public void setHandleDetail(String handleDetail) {
		this.handleDetail = handleDetail;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<TdOrderGoods> getOrderGoodsList() {
		return orderGoodsList;
	}

	public void setOrderGoodsList(List<TdOrderGoods> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
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

	public Long getPayTypeId() {
		return payTypeId;
	}

	public void setPayTypeId(Long payTypeId) {
		this.payTypeId = payTypeId;
	}

	public String getPayTypeTitle() {
		return payTypeTitle;
	}

	public void setPayTypeTitle(String payTypeTitle) {
		this.payTypeTitle = payTypeTitle;
	}

	public Double getPayTypeFee() {
		return payTypeFee;
	}

	public void setPayTypeFee(Double payTypeFee) {
		this.payTypeFee = payTypeFee;
	}

	public String getDeliverTypeTitle() {
		return deliverTypeTitle;
	}

	public void setDeliverTypeTitle(String deliverTypeTitle) {
		this.deliverTypeTitle = deliverTypeTitle;
	}

	public Double getDeliverFee() {
		return deliverFee;
	}

	public void setDeliverFee(Double deliverFee) {
		this.deliverFee = deliverFee;
	}

	public String getRemarkInfo() {
		return remarkInfo;
	}

	public void setRemarkInfo(String remarkInfo) {
		this.remarkInfo = remarkInfo;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
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

	public String getDistributionPerson() {
		return distributionPerson;
	}

	public void setDistributionPerson(String distributionPerson) {
		this.distributionPerson = distributionPerson;
	}

	public String getMoneyReceivePerson() {
		return moneyReceivePerson;
	}

	public void setMoneyReceivePerson(String moneyReceivePerson) {
		this.moneyReceivePerson = moneyReceivePerson;
	}

	public Double getTotalGoodsPrice() {
		return totalGoodsPrice;
	}

	public void setTotalGoodsPrice(Double totalGoodsPrice) {
		this.totalGoodsPrice = totalGoodsPrice;
	}

	public Double getTotalPrice() {
		BigDecimal bg = new BigDecimal(totalPrice);
		totalPrice = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		bg = null;
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Boolean getIsOnlinePay() {
		return isOnlinePay;
	}

	public void setIsOnlinePay(Boolean isOnlinePay) {
		this.isOnlinePay = isOnlinePay;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Boolean getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Boolean isRefund) {
		this.isRefund = isRefund;
	}

	public Date getCancelApplyTime() {
		return cancelApplyTime;
	}

	public void setCancelApplyTime(Date cancelApplyTime) {
		this.cancelApplyTime = cancelApplyTime;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Long getDiySiteId() {
		return diySiteId;
	}

	public void setDiySiteId(Long diySiteId) {
		this.diySiteId = diySiteId;
	}

	public String getDiySiteName() {
		return diySiteName;
	}

	public void setDiySiteName(String diySiteName) {
		this.diySiteName = diySiteName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Boolean getIsPromotion() {
		return isPromotion;
	}

	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
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

	public List<TdOrderGoods> getPresentedList() {
		return presentedList;
	}

	public void setPresentedList(List<TdOrderGoods> presentedList) {
		this.presentedList = presentedList;
	}

	public List<TdOrderGoods> getGiftGoodsList() {
		return giftGoodsList;
	}

	public void setGiftGoodsList(List<TdOrderGoods> giftGoodsList) {
		this.giftGoodsList = giftGoodsList;
	}

	public String getProductCouponId() {
		return productCouponId;
	}

	public void setProductCouponId(String productCouponId) {
		this.productCouponId = productCouponId;
	}

	public String getCashCouponId() {
		return cashCouponId;
	}

	public void setCashCouponId(String cashCouponId) {
		this.cashCouponId = cashCouponId;
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

	public Double getActualPay() {
		return actualPay;
	}

	public void setActualPay(Double actualPay) {
		this.actualPay = actualPay;
	}

	public String getDiySitePhone() {
		return diySitePhone;
	}

	public void setDiySitePhone(String diySitePhone) {
		this.diySitePhone = diySitePhone;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getLimitCash() {
		return limitCash;
	}

	public void setLimitCash(Double limitCash) {
		this.limitCash = limitCash;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDisctrict() {
		return disctrict;
	}

	public void setDisctrict(String disctrict) {
		this.disctrict = disctrict;
	}

	public String getSubdistrict() {
		return subdistrict;
	}

	public void setSubdistrict(String subdistrict) {
		this.subdistrict = subdistrict;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getDiySiteCode() {
		return diySiteCode;
	}

	public void setDiySiteCode(String diySiteCode) {
		this.diySiteCode = diySiteCode;
	}

	public Boolean getIsCoupon() {
		return isCoupon;
	}

	public void setIsCoupon(Boolean isCoupon) {
		this.isCoupon = isCoupon;
	}

	public String getMainOrderNumber() {
		return mainOrderNumber;
	}

	public void setMainOrderNumber(String mainOrderNumber) {
		this.mainOrderNumber = mainOrderNumber;
	}

	public Double getAllTotalPay() {
		return allTotalPay;
	}

	public void setAllTotalPay(Double allTotalPay) {
		this.allTotalPay = allTotalPay;
	}

	public Double getAllActualPay() {
		return allActualPay;
	}

	public void setAllActualPay(Double allActualPay) {
		this.allActualPay = allActualPay;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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

	public Boolean getIsUsedBalance() {
		return isUsedBalance;
	}

	public void setIsUsedBalance(Boolean isUsedBalance) {
		this.isUsedBalance = isUsedBalance;
	}

	public Boolean getHaveSeller() {
		return haveSeller;
	}

	public void setHaveSeller(Boolean haveSeller) {
		this.haveSeller = haveSeller;
	}

	public Boolean getIsSellerOrder() {
		return isSellerOrder;
	}

	public void setIsSellerOrder(Boolean isSellerOrder) {
		this.isSellerOrder = isSellerOrder;
	}

	public String getRealUserUsername() {
		return realUserUsername;
	}

	public void setRealUserUsername(String realUserUsername) {
		this.realUserUsername = realUserUsername;
	}

	public Long getRealUserId() {
		if (this.realUserId == null) {
			return this.userId;
		}
		return realUserId;
	}

	public void setRealUserId(Long realUserId) {
		this.realUserId = realUserId;
	}

	public String getRealUserRealName() {
		return realUserRealName;
	}

	public void setRealUserRealName(String realUserRealName) {
		this.realUserRealName = realUserRealName;
	}

	public Double getOtherPay() {
		return otherPay;
	}

	public void setOtherPay(Double otherPay) {
		this.otherPay = otherPay;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Double getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(Double otherIncome) {
		this.otherIncome = otherIncome;
	}

	public String getBuyCouponId() {
		return buyCouponId;
	}

	public void setBuyCouponId(String buyCouponId) {
		this.buyCouponId = buyCouponId;
	}

	public Double getActivitySubPrice() {
		return activitySubPrice;
	}

	public void setActivitySubPrice(Double activitySubPrice) {
		this.activitySubPrice = activitySubPrice;
	}

	public Double getPosPay() {
		return posPay;
	}

	public void setPosPay(Double posPay) {
		this.posPay = posPay;
	}

	public Double getCashPay() {
		return cashPay;
	}

	public void setCashPay(Double cashPay) {
		this.cashPay = cashPay;
	}

	public Double getBackOtherPay() {
		return backOtherPay;
	}

	public void setBackOtherPay(Double backOtherPay) {
		this.backOtherPay = backOtherPay;
	}

	public Boolean getIsFixedDeliveryFee() {
		return isFixedDeliveryFee;
	}

	public void setIsFixedDeliveryFee(Boolean isFixedDeliveryFee) {
		this.isFixedDeliveryFee = isFixedDeliveryFee;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}

	public Double getReceivableFee() {
		return receivableFee;
	}

	public void setReceivableFee(Double receivableFee) {
		this.receivableFee = receivableFee;
	}

	public String getUpstairsType() {
		return upstairsType;
	}

	public void setUpstairsType(String upstairsType) {
		this.upstairsType = upstairsType;
	}

	public Long getFloor() {
		return floor;
	}

	public void setFloor(Long floor) {
		this.floor = floor;
	}

	public Double getUpstairsFee() {
		return upstairsFee;
	}

	public void setUpstairsFee(Double upstairsFee) {
		this.upstairsFee = upstairsFee;
	}

	public Double getUpstairsBalancePayed() {
		return upstairsBalancePayed;
	}

	public void setUpstairsBalancePayed(Double upstairsBalancePayed) {
		this.upstairsBalancePayed = upstairsBalancePayed;
	}

	public Double getUpstairsOtherPayed() {
		return upstairsOtherPayed;
	}

	public void setUpstairsOtherPayed(Double upstairsOtherPayed) {
		this.upstairsOtherPayed = upstairsOtherPayed;
	}

	public Boolean getIsPhotoOrder() {
		return isPhotoOrder;
	}

	public void setIsPhotoOrder(Boolean isPhotoOrder) {
		this.isPhotoOrder = isPhotoOrder;
	}

	public Double getUpstairsLeftFee() {
		this.upstairsLeftFee = this.upstairsFee - this.upstairsBalancePayed - this.upstairsOtherPayed;
		this.upstairsLeftFee = new BigDecimal(this.upstairsLeftFee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return upstairsLeftFee;
	}

	public void setUpstairsLeftFee(Double upstairsLeftFee) {
		this.upstairsLeftFee = upstairsLeftFee;
	}

	public Double getCompanyDeliveryFee() {
		return companyDeliveryFee;
	}

	public void setCompanyDeliveryFee(Double companyDeliveryFee) {
		this.companyDeliveryFee = companyDeliveryFee;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Double getJxTotalPrice() {
		return jxTotalPrice;
	}

	public void setJxTotalPrice(Double jxTotalPrice) {
		this.jxTotalPrice = jxTotalPrice;
	}

	public Double getColorFee() {
		return colorFee;
	}

	public void setColorFee(Double colorFee) {
		this.colorFee = colorFee;
	}

	public Double getDifFee() {
		return difFee;
	}

	public void setDifFee(Double difFee) {
		this.difFee = difFee;
	}

	public Double getNotPayedFee() {
		double allFee = this.getAllFee();
		double allDiscount = this.getAllDiscount();
		notPayedFee = CountUtil.sub(allFee, allDiscount);
		return notPayedFee;
	}

	public void setNotPayedFee(Double notPayedFee) {
		this.notPayedFee = notPayedFee;
	}

	public Double getProCouponFee() {
		return proCouponFee;
	}

	public void setProCouponFee(Double proCouponFee) {
		this.proCouponFee = proCouponFee;
	}

	public Boolean getReceiverIsMember() {
		return receiverIsMember;
	}

	public void setReceiverIsMember(Boolean receiverIsMember) {
		this.receiverIsMember = receiverIsMember;
	}

	public Double getPromotionMoneyPayed() {
		return promotionMoneyPayed;
	}

	public void setPromotionMoneyPayed(Double promotionMoneyPayed) {
		this.promotionMoneyPayed = promotionMoneyPayed;
	}

	public Double getAgencyRefund() {
		return agencyRefund;
	}

	public void setAgencyRefund(Double agencyRefund) {
		this.agencyRefund = agencyRefund;
	}

	public Double getMockReceivable() {
		return mockReceivable;
	}

	public void setMockReceivable(Double mockReceivable) {
		this.mockReceivable = mockReceivable;
	}

	private double getAllFee() {
		totalGoodsPrice = null == totalGoodsPrice ? 0d : totalGoodsPrice;
		deliverFee = null == deliverFee ? 0d : deliverFee;
		upstairsFee = null == upstairsFee ? 0d : upstairsFee;
		colorFee = null == colorFee ? 0d : colorFee;
		return CountUtil.add(totalGoodsPrice, deliverFee, upstairsFee, colorFee);
	}

	private double getAllDiscount() {
		activitySubPrice = null == activitySubPrice ? 0d : activitySubPrice;
		cashCoupon = null == cashCoupon ? 0d : cashCoupon;
		proCouponFee = null == proCouponFee ? 0d : proCouponFee;
		difFee = null == difFee ? 0d : difFee;
		cashBalanceUsed = null == cashBalanceUsed ? 0d : cashBalanceUsed;
		unCashBalanceUsed = null == unCashBalanceUsed ? 0d : unCashBalanceUsed;
		otherPay = null == otherPay ? 0d : otherPay;
		return CountUtil.add(activitySubPrice, cashCoupon, proCouponFee, difFee, cashBalanceUsed, unCashBalanceUsed,
				otherPay);
	}

	public Double getWalletMoney() {
		return walletMoney;
	}

	public void setWalletMoney(Double walletMoney) {
		this.walletMoney = walletMoney;
	}

	/**
	 * 获取真实的应收款金额，该方法只对于主单有效（分单缺失部分信息，且所涉及字段只用于WMS）
	 * 
	 * @return 应收款金额
	 * @author dengxiao
	 */
	public double countActualReceivable() {
		return this.getNotPayedFee();
	}

	/**
	 * 获取传递给WMS的应收款金额，该方法只对于主单有效（分单缺失部分信息，且所涉及字段只用于WMS）
	 * 
	 * @return 计算后的传递给WMS的应收款
	 * @author dengxiao
	 */
	public double countMockReceivable() {
		double result;
		double actualReceivable = this.countActualReceivable();
		if (actualReceivable == 0d) {
			result = 0d;
		} else {
			if (this.receiverIsMember) {
				result = this.getAllFee();
			} else {
				result = this.countActualReceivable();
			}
		}
		this.mockReceivable = result;
		return this.mockReceivable;
	}

	public Double getAlipayMoney() {
		return alipayMoney;
	}

	public void setAlipayMoney(Double alipayMoney) {
		this.alipayMoney = alipayMoney;
	}

	/**
	 * 计算代收金额的方法：如果是代下单的情况，代收金额为导购自己填写的，如果不是代下单的情况，则值为传递给WMS的应收金额
	 * 该方法只对于主单有效（分单缺失部分信息，且所涉及字段只用于WMS）
	 * @return 通过各种条件计算完毕的代收金额
	 * @author dengxiao
	 */
	public double countAgencyRefund() {
		if (!this.isSellerOrder) {
			this.agencyRefund = this.countMockReceivable();
		}
		return CountUtil.HALF_UP_SCALE_2(this.agencyRefund);
	}
}
