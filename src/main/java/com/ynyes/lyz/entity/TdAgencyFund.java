package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 代收款报表（1.1）
 * @author zp
 *
 */
@Entity
public class TdAgencyFund {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	//门店名称
	@Column
	private String diySiteName;
	
	//主单号
	@Column
	private String mainOrderNumber;
	//订单状态
	@Column
	private Long statusId;
	//订单时间
	@Column
	private Date orderTime;
	//代收款金额
	@Column(scale = 2)
	private Double payPrice;
	
	//仓库名称（1.1修改字段）
	@Column
	private String whName;
	//配送人员(1.1修改字段)
	@Column
	private String deliveryName;
	//配送人电话(1.1修改字段)
	@Column
	private String deliveryPhone;
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
	
	private Boolean isEnable;
	
	private Boolean isPassed;
	
	//-------------------1.1版本添加字段---------------------
	//归属导购姓名
	@Column
	private String sellerRealName;
	// 归属销顾电话（推荐人电话）
	@Column
	private String sellerUsername;
	//客户编号,取td_user表中的id值
	@Column
	private Long userId;
	//客户姓名
	@Column
	private String realUserRealName;
	// 客户电话
	@Column
	private String realUserUsername;
	// 收款现金
	@Column
	private Double payMoney;
	// 收款pos
	@Column
	private Double payPos;
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Double getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
	
	public String getWhName() {
		return whName;
	}
	public void setWhName(String whName) {
		this.whName = whName;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
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
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getSellerRealName() {
		return sellerRealName;
	}
	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
	}
	public String getSellerUsername() {
		return sellerUsername;
	}
	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
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
	public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	public Double getPayPos() {
		return payPos;
	}
	public void setPayPos(Double payPos) {
		this.payPos = payPos;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public Boolean getIsPassed() {
		return isPassed;
	}
	public void setIsPassed(Boolean isPassed) {
		this.isPassed = isPassed;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	
	
	
	
}
