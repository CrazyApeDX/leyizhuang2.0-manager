package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 加盟商对账报表
 * @author lenovo
 *
 */
@Entity
public class GarmentFranchisorReport {
	
	
	
	@Id
	@GenericGenerator(name= "paymentableGenerator",strategy = "uuid")
	private String id;
	
	@Column
	private String cityName;
	
	
	@Column
	private String diySiteName;
	
	@Column
	private String whName;
	
	@Column
	private String deliveryName;
	
	@Column
	private String deliveryPhone;
	
	@Column
	private String realUserRealName;
	
	@Column
	private String realUserUsername;
	
	@Column
	private String sellerRealName;
	
	@Column
	private String mainOrderNumber;
	
	@Column
	private String orderNumber;
	
	@Column
	private Date orderTime;
	
	@Column
	private Date deliveryTime;
	
	@Column
	private Integer statusId;
	
	@Column
	private Double actualPay;
	
	@Column
	private Double otherPay;
	
	@Column
	private Double cashPay;
	
	@Column
	private Double cashCoupon;
	
	@Column
	private Integer payTypeId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDiySiteName() {
		return diySiteName;
	}

	public void setDiySiteName(String diySiteName) {
		this.diySiteName = diySiteName;
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

	public String getSellerRealName() {
		return sellerRealName;
	}

	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
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

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Double getActualPay() {
		return actualPay;
	}

	public void setActualPay(Double actualPay) {
		this.actualPay = actualPay;
	}

	public Double getOtherPay() {
		return otherPay;
	}

	public void setOtherPay(Double otherPay) {
		this.otherPay = otherPay;
	}

	public Double getCashPay() {
		return cashPay;
	}

	public void setCashPay(Double cashPay) {
		this.cashPay = cashPay;
	}

	public Double getCashCoupon() {
		return cashCoupon;
	}

	public void setCashCoupon(Double cashCoupon) {
		this.cashCoupon = cashCoupon;
	}
	

	public Integer getPayTypeId() {
		return payTypeId;
	}

	public void setPayTypeId(Integer payTypeId) {
		this.payTypeId = payTypeId;
	}

	@Override
	public String toString() {
		return "GarmentFranchisorReport [id=" + id + ", cityName=" + cityName + ", diySiteName=" + diySiteName
				+ ", whName=" + whName + ", deliveryName=" + deliveryName + ", deliveryPhone=" + deliveryPhone
				+ ", realUserRealName=" + realUserRealName + ", realUserUsername=" + realUserUsername
				+ ", sellerRealName=" + sellerRealName + ", mainOrderNumber=" + mainOrderNumber + ", orderNumber="
				+ orderNumber + ", orderTime=" + orderTime + ", deliveryTime=" + deliveryTime + ", statusId=" + statusId
				+ ", actualPay=" + actualPay + ", otherPay=" + otherPay + ", cashPay=" + cashPay + ", cashCoupon="
				+ cashCoupon + "]";
	}

}
