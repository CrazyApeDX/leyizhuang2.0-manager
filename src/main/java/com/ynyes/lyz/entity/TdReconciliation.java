package com.ynyes.lyz.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TdReconciliation{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
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
	

}
