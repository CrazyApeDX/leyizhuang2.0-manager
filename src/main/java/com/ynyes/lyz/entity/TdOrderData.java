package com.ynyes.lyz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ynyes.lyz.util.CountUtil;

@Entity
@Table(name = "TD_ORDER_DATA")
public class TdOrderData implements Serializable {

	// 自增主键
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 收货人是否是主家
	@Column
	private Boolean receiverIsMember = Boolean.FALSE;

	// 订单归属用户名
	@Column
	private String username;

	// 主单号
	@Column
	private String mainOrderNumber;

	// 门店ID
	@Column
	private Long storeId;

	// 门店标题
	@Column
	private String storeTitle;

	// 门店编码
	@Column
	private String storeCode;

	// 导购ID
	@Column
	private Long sellerId;

	// 导购姓名
	@Column
	private String sellerName;

	// 导购电话号码
	@Column
	private String sellerPhone;

	// 出货单号
	@Column
	private String shipmentNumber;

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

	// 线上支付金额
	@Column
	private Double onlinePay = 0d;

	// 运费
	@Column
	private Double deliveryFee = 0d;

	// 应付金额
	@Column
	private Double leftPrice = 0d;

	// 代收金额
	@Column
	private Double agencyRefund = 0d;

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

	// 多余的收款金额
	@Column
	private Double redundant = 0d;

	// 收款之后的欠款金额
	@Column
	private Double due = 0d;

	// 归还会员的金额
	@Column
	private Double refundBalance = 0d;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getReceiverIsMember() {
		return receiverIsMember;
	}

	public void setReceiverIsMember(Boolean receiverIsMember) {
		this.receiverIsMember = receiverIsMember;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMainOrderNumber() {
		return mainOrderNumber;
	}

	public void setMainOrderNumber(String mainOrderNumber) {
		this.mainOrderNumber = mainOrderNumber;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreTitle() {
		return storeTitle;
	}

	public void setStoreTitle(String storeTitle) {
		this.storeTitle = storeTitle;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getShipmentNumber() {
		return shipmentNumber;
	}

	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
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

	public Double getOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(Double onlinePay) {
		this.onlinePay = onlinePay;
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

	public Double getAgencyRefund() {
		return agencyRefund;
	}

	public void setAgencyRefund(Double agencyRefund) {
		this.agencyRefund = agencyRefund;
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

	public Double getRedundant() {
		return redundant;
	}

	public void setRedundant(Double redundant) {
		this.redundant = redundant;
	}

	public Double getDue() {
		return due;
	}

	public void setDue(Double due) {
		this.due = due;
	}

	public Double getRefundBalance() {
		return refundBalance;
	}

	public void setRefundBalance(Double refundBalance) {
		this.refundBalance = refundBalance;
	}

	@Override
	public String toString() {
		return "TdOrderData [id=" + id + ", receiverIsMember=" + receiverIsMember + ", username=" + username
				+ ", mainOrderNumber=" + mainOrderNumber + ", storeId=" + storeId + ", storeTitle=" + storeTitle
				+ ", storeCode=" + storeCode + ", sellerId=" + sellerId + ", sellerName=" + sellerName
				+ ", sellerPhone=" + sellerPhone + ", shipmentNumber=" + shipmentNumber + ", totalGoodsPrice="
				+ totalGoodsPrice + ", memberDiscount=" + memberDiscount + ", activitySub=" + activitySub
				+ ", proCouponFee=" + proCouponFee + ", cashCouponFee=" + cashCouponFee + ", balanceUsed=" + balanceUsed
				+ ", onlinePay=" + onlinePay + ", deliveryFee=" + deliveryFee + ", leftPrice=" + leftPrice
				+ ", agencyRefund=" + agencyRefund + ", deliveryCash=" + deliveryCash + ", deliveryPos=" + deliveryPos
				+ ", sellerCash=" + sellerCash + ", sellerPos=" + sellerPos + ", sellerOther=" + sellerOther
				+ ", redundant=" + redundant + ", due=" + due + ", refundBalance=" + refundBalance + "]";
	}
	
	public double countDue() {
		double due = CountUtil.sub(this.leftPrice, this.deliveryCash, this.deliveryPos, this.sellerCash, this.sellerPos, this.sellerOther);
		this.due = due < 0 ? 0d : due;
		return this.due;
	}

}
