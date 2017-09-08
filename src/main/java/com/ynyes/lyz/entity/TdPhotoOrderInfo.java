package com.ynyes.lyz.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 拍照下单信息类
 * 
 * @author dengxiao
 *		created on 2017/08/29
 */
@Entity
@Table(name = "TD_PHOTO_ORDER_INFO")
public class TdPhotoOrderInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 创建时间
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd mm:hh:ss")
	private Date createTime = Calendar.getInstance().getTime();

	// 开始处理时间
	@DateTimeFormat(pattern = "yyyy-MM-dd mm:hh:ss")
	private Date startActionTime;

	// 完成处理时间
	@DateTimeFormat(pattern = "yyyy-MM-dd mm:hh:ss")
	private Date finishTime;

	// 所属用户id
	@Column(length = 20, nullable = false)
	private Long userId;

	// 所属用户名
	@Column(length = 20, nullable = false)
	private String username;

	// 所属用户姓名
	@Column(length = 20, nullable = false)
	private String userRealName;

	// 处理人id
	@Column
	private Long managerId;

	// 处理人用户名
	@Column(length = 30)
	private String managerUsername;

	// 处理人姓名
	@Column(length = 30)
	private String managerRealName;

	// 照片地址
	@Column(length = 225, nullable = false, unique = true)
	private String photoUri;

	// 收货地址ID
	@Column(length = 20, nullable = false)
	private Long shippingAddressId;

	// 状态
	@Column(length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status = Status.WAITING;
	
	// 备注
	@Column(length = 1000)
	private String remark;
	
	// 收货人姓名
	@Column(length = 20)
	private String receiverName;

	// 收货人移动电话
	@Column(length = 50)
	private String receiverMobile;
	
	// 市
	@Column(length = 225)
	private String city;
	
	// 区
	@Column(length = 225)
	private String disctrict;
	
	// 街道
	@Column(length = 225)
	private String subdistrict;
	
	// 详细地址
	@Column(length = 225)
	private String detailAddress;
	
	// 订单号
	@Column(length = 225)
	private String orderNumber;
	
	public static enum Status {
		WAITING, ACTIONING, FINISHING
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartActionTime() {
		return startActionTime;
	}

	public void setStartActionTime(Date startActionTime) {
		this.startActionTime = startActionTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public String getManagerUsername() {
		return managerUsername;
	}

	public void setManagerUsername(String managerUsername) {
		this.managerUsername = managerUsername;
	}

	public String getManagerRealName() {
		return managerRealName;
	}

	public void setManagerRealName(String managerRealName) {
		this.managerRealName = managerRealName;
	}

	public String getPhotoUri() {
		return photoUri;
	}

	public void setPhotoUri(String photoUri) {
		this.photoUri = photoUri;
	}

	public Long getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
}
