package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 门店
 * 
 * @author Sharon
 *
 */

@Entity
public class TdDiySite {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 门店名称
	@Column
	private String title;

	// 门店地址
	@Column
	private String address;

	// 付款方式
	@Column
	private String payType;

	// 营业时间
	@Column
	private String openTimeSpan;

	// 客服电话
	@Column
	private String serviceTele;

	// 投诉电话
	@Column
	private String complainTele;

	// 门店省份
	@Column
	private String province;

	// 门店城市行政区划id
	@Column
	private Long disctrictId;

	// 门店城市名称
	@Column
	private String city;
	
	// 门店城市id
	@Column
	private Long cityId;

	// 门店城市Id(sobID)
	@Column
	private Long regionId;

	// 是否启用
	@Column
	private Boolean isEnable;

	// 排序数字
	@Column
	private Double sortId;

	// 经度
	@Column
	private Double longitude;

	// 纬度
	@Column
	private Double latitude;

	// 描述说明
	@Column
	private String info;

	// 图片地址
	@Column
	private String imageUri;

	// 轮播展示图片，多张图片以,隔开
	@Column
	private String showPictures;

	// 客服qq
	@Column
	private String qq;

	// 是否为旗舰店
	@Column
	private Boolean isFlagShip;

	// 门店所使用的价目表编号
	@Column
	private Long priceListId;
	
	//门店使用的进货表编号
	@Column
	private Long stockListId;

	// 是否是直营门店
	@Column
	private Boolean isDirect;

	// 门店属性（0. 直营门店；1. 加盟门店；2. 虚拟门店；3. 第三方；4. 装修公司）
	@Column
	private Long status;

	// 行政街道名
	@Column
	private String subDisctrictName;

	// 行政街道ID
	@Column
	private Long subDisctrictId;
	
	// 接口新加数据    <--------------
	
	//客户id
	@Column
	private Long customerId;
	//客户编码
	@Column
	private String customerNumber;
	//类型名称（经销商，直营）
	@Column
	private String custTypeName;
	//门店编码
	@Column
	private String storeCode;
	//区域编码
	@Column
	private String deptCode;
	//区域描述
	@Column
	private String deptDesc;
	//价目表名
	@Column
	private String priceListName;
	//是否开启门店送货上门的配送方式
	@Column
	private Boolean isHomeDelivery;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getStockListId() {
		return stockListId;
	}

	public void setStockListId(Long stockListId) {
		this.stockListId = stockListId;
	}

	public String getPriceListName() {
		return priceListName;
	}

	public void setPriceListName(String priceListName) {
		this.priceListName = priceListName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public String getCustTypeName() {
		return custTypeName;
	}

	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
	}

	public String getSubDisctrictName() {
		return subDisctrictName;
	}

	public void setSubDisctrictName(String subDisctrictName) {
		this.subDisctrictName = subDisctrictName;
	}

	public Long getSubDisctrictId() {
		return subDisctrictId;
	}

	public void setSubDisctrictId(Long subDisctrictId) {
		this.subDisctrictId = subDisctrictId;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsFlagShip() {
		return isFlagShip;
	}

	public void setIsFlagShip(Boolean isFlagShip) {
		this.isFlagShip = isFlagShip;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOpenTimeSpan() {
		return openTimeSpan;
	}

	public void setOpenTimeSpan(String openTimeSpan) {
		this.openTimeSpan = openTimeSpan;
	}

	public String getServiceTele() {
		return serviceTele;
	}

	public void setServiceTele(String serviceTele) {
		this.serviceTele = serviceTele;
	}

	public String getComplainTele() {
		return complainTele;
	}

	public void setComplainTele(String complainTele) {
		this.complainTele = complainTele;
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

	public Long getDisctrictId() {
		return disctrictId;
	}

	public void setDisctrictId(Long disctrictId) {
		this.disctrictId = disctrictId;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getShowPictures() {
		return showPictures;
	}

	public void setShowPictures(String showPictures) {
		this.showPictures = showPictures;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Long getPriceListId() {
		return priceListId;
	}

	public void setPriceListId(Long priceListId) {
		this.priceListId = priceListId;
	}

	public Boolean getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(Boolean isDirect) {
		this.isDirect = isDirect;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Boolean getIsHomeDelivery() {
		return isHomeDelivery;
	}

	public void setIsHomeDelivery(Boolean isHomeDelivery) {
		this.isHomeDelivery = isHomeDelivery;
	}
	
	
}
