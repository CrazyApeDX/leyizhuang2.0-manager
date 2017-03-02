package com.ynyes.lyz.interfaces.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class TdOrderInf extends TdInfBaseEntity {
	
	//分公司ID
	@Column
	private Long sobId;

	//销售单头ID (APP唯一)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long headerId;

	//销售订单号(分单号)
	@Column
	private String orderNumber;

	//订单日期
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderDate;

	//APP主单号
	@Column
	private String mainOrderNumber;

	//APP单据产品类型 HR华润,LYZ乐易装,YR莹润
	@Column
	private String productType;

	//1.要货单：B2B HR产品直接“无价”批发给“直营门店”
	//2.要货单：B2B HR产品“经销价”批发给“经销门店”
	//3.销售订单：B2C LYZ和YR产品直接“零售价”销售给门店陈列
	//4.销售订单：B2C HR,LYZ和YR产品直接“零售价”销售给会员
	@Column
	private Integer orderTypeId;

	//APP会员ID（APP是唯一）
	@Column
	private Long userid;

	//APP会员名称（姓名）
	@Column
	private String username;

	//APP会员电话(账号)
	@Column
	private String userphone;

	//APP门店ID 
	@Column
	private Long diySiteId;

	//门店编码 
	@Column
	private String diySiteCode;

	//门店名称 
	@Column
	private String diySiteName;

	//门店联系电话
	@Column
	private String diySitePhone;
	
	//导购电话
	@Column(length = 20)
	private String sellerPhone;
	
	//导购姓名
	@Column(length = 100)
	private String sellerName;

	//省
	@Column
	private String province;

	//城市
	@Column
	private String city;

	//区
	@Column
	private String disctrict;

	//收货人
	@Column
	private String shippingName;

	//收货电话
	@Column
	private String shippingPhone;

	//配送方式名称
	@Column
	private String deliverTypeTitle;

	//是否线上支付
	@Column
	private String isonlinepay;

	//支付类型（支付宝,微信,银联）
	@Column
	private String payType;

	//付款日期(点结算时的时间)
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date payDate;

	//支付金额
	@Column(scale=2)
	private Double payAmt;

	//预存款使用金额
	@Column(scale=2)
	private Double prepayAmt;
	
	//应支付的金额
	@Column
	private Double recAmt;
	
	//预留字段1
	@Column
	private String attribute1;
	
	//预留字段2
	@Column
	private String attribute2;
	
	//预留字段3
	@Column
	private String attribute3;
	
	//预留字段4
	@Column
	private String attribute4;
	
	//预留字段5
	@Column
	private String attribute5;
	
	// 新增字段：是否使用产品券
	@Column
	private Character couponFlag;
	
	// 新增字段：运费
	@Column(scale = 2)
	private Double deliveryFee;
	
	// 新增字段：信用金
	@Column(scale = 2, nullable = false)
	private Double creditAmt = 0d;
	
	// 新增字段： 华润公司承担运费
	@Column(scale = 2, nullable = false)
	private Double companyDeliveryFee = 0d;

	public Long getSobId() {
		return sobId;
	}

	public void setSobId(Long sobId) {
		this.sobId = sobId;
	}

	public Long getHeaderId() {
		return headerId;
	}

	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getMainOrderNumber() {
		return mainOrderNumber;
	}

	public void setMainOrderNumber(String mainOrderNumber) {
		this.mainOrderNumber = mainOrderNumber;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

	public Long getDiySiteId() {
		return diySiteId;
	}

	public void setDiySiteId(Long diySiteId) {
		this.diySiteId = diySiteId;
	}

	public String getDiySiteCode() {
		return diySiteCode;
	}

	public void setDiySiteCode(String diySiteCode) {
		this.diySiteCode = diySiteCode;
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

	public String getDeliverTypeTitle() {
		return deliverTypeTitle;
	}

	public void setDeliverTypeTitle(String deliverTypeTitle) {
		this.deliverTypeTitle = deliverTypeTitle;
	}

	public String getIsonlinepay() {
		return isonlinepay;
	}

	public void setIsonlinepay(String isonlinepay) {
		this.isonlinepay = isonlinepay;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Double getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(Double payAmt) {
		this.payAmt = payAmt;
	}

	public Double getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(Double prepayAmt) {
		this.prepayAmt = prepayAmt;
	}
	
	public Double getRecAmt() {
		return recAmt;
	}

	public void setRecAmt(Double recAmt) {
		this.recAmt = recAmt;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public Character getCouponFlag() {
		return couponFlag;
	}

	public void setCouponFlag(Character couponFlag) {
		this.couponFlag = couponFlag;
	}

	public Double getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Double getCreditAmt() {
		return creditAmt;
	}

	public void setCreditAmt(Double creditAmt) {
		this.creditAmt = creditAmt;
	}
	
	

	public Double getCompanyDeliveryFee() {
		return companyDeliveryFee;
	}

	public void setCompanyDeliveryFee(Double companyDeliveryFee) {
		this.companyDeliveryFee = companyDeliveryFee;
	}
	
	

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	@Override
	public String toString() {
		return "TdOrderInf [sobId=" + sobId + ", headerId=" + headerId + ", orderNumber=" + orderNumber + ", orderDate="
				+ orderDate + ", mainOrderNumber=" + mainOrderNumber + ", productType=" + productType + ", orderTypeId="
				+ orderTypeId + ", userid=" + userid + ", username=" + username + ", userphone=" + userphone
				+ ", diySiteId=" + diySiteId + ", diySiteCode=" + diySiteCode + ", diySiteName=" + diySiteName
				+ ", diySitePhone=" + diySitePhone + ", sellerPhone=" + sellerPhone + ", sellerName=" + sellerName
				+ ", province=" + province + ", city=" + city + ", disctrict=" + disctrict + ", shippingName="
				+ shippingName + ", shippingPhone=" + shippingPhone + ", deliverTypeTitle=" + deliverTypeTitle
				+ ", isonlinepay=" + isonlinepay + ", payType=" + payType + ", payDate=" + payDate + ", payAmt="
				+ payAmt + ", prepayAmt=" + prepayAmt + ", recAmt=" + recAmt + ", attribute1=" + attribute1
				+ ", attribute2=" + attribute2 + ", attribute3=" + attribute3 + ", attribute4=" + attribute4
				+ ", attribute5=" + attribute5 + ", couponFlag=" + couponFlag + ", deliveryFee=" + deliveryFee
				+ ", creditAmt=" + creditAmt + ", companyDeliveryFee=" + companyDeliveryFee + "]";
	}

	
}
