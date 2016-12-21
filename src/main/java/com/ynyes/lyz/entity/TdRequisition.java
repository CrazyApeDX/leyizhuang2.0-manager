package com.ynyes.lyz.entity;

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



//要货单

@Entity
public class TdRequisition {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 门店名称
	@Column
	private String diySiteTitle;
	
	// 门店编码
	@Column
	private Long diySiteId;
	
	// 客户姓名(用户名)
	@Column
	private String customerName;
	
	// 客户编码(用户id)
	@Column
	private Long customerId;
	
	// 原单号（主订单号）
	@Column
	private String orderNumber;
	
	// 总金额
	@Column
	private Double totalPrice;
	
	// 代收金额
	@Column
	private Double leftPrice;
	
	// 送货时间
	@Column
    private String deliveryTime;
	
	// 收货人(姓名)
	@Column
	private String receiveName;
	
	// 收货人地址
	@Column
	private String receiveAddress;
	
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
	
	// 收货人电话
	@Column
	private String receivePhone;
	
    // 订单商品
    @OneToMany
    @JoinColumn(name="TdRequisitionId")
    private List<TdRequisitionGoods> requisiteGoodsList;
    
    // 下单时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    // 要货单类型 1： 要货单  2：要货单退单 3： 要货单订单
    @Column
    private Long typeId;
    
    // 订单备注
    @Column
    private String remarkInfo;
    
    // 门店电话
    @Column
    private String diySiteTel;
    
    // 门店编码
    @Column
    private String diyCode;
    
    // 销售顾问名字
    @Column
    private String sellerRealName;
    
    // 销顾电话
    @Column(length = 20)
    private String sellerTel;
    
    // 商品总数
    @Column
    private Integer goodsQuantity;
    
    // 上楼费总额
    @Column
    private Double upstairsAll = 0d;
    
    // 剩余上楼费
    @Column
    private Double upstairsLeft = 0d;
    
	public String getSellerRealName() {
		return sellerRealName;
	}

	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
	}

	public String getDiyCode() {
		return diyCode;
	}

	public void setDiyCode(String diyCode) {
		this.diyCode = diyCode;
	}

	public Double getLeftPrice() {
		return leftPrice;
	}

	public void setLeftPrice(Double leftPrice) {
		this.leftPrice = leftPrice;
	}

	public String getDiySiteTel() {
		return diySiteTel;
	}

	public void setDiySiteTel(String diySiteTel) {
		this.diySiteTel = diySiteTel;
	}

	public String getRemarkInfo() {
		return remarkInfo;
	}

	public void setRemarkInfo(String remarkInfo) {
		this.remarkInfo = remarkInfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDiySiteTitle() {
		return diySiteTitle;
	}

	public void setDiySiteTitle(String diySiteTitle) {
		this.diySiteTitle = diySiteTitle;
	}

	public Long getDiySiteId() {
		return diySiteId;
	}

	public void setDiySiteId(Long diySiteId) {
		this.diySiteId = diySiteId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public List<TdRequisitionGoods> getRequisiteGoodsList() {
		return requisiteGoodsList;
	}

	public void setRequisiteGoodsList(List<TdRequisitionGoods> requisiteGoodsList) {
		this.requisiteGoodsList = requisiteGoodsList;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
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

	public String getSellerTel() {
		return sellerTel;
	}

	public void setSellerTel(String sellerTel) {
		this.sellerTel = sellerTel;
	}

	public Integer getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(Integer goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	public Double getUpstairsAll() {
		return upstairsAll;
	}

	public void setUpstairsAll(Double upstairsAll) {
		this.upstairsAll = upstairsAll;
	}

	public Double getUpstairsLeft() {
		return upstairsLeft;
	}

	public void setUpstairsLeft(Double upstairsLeft) {
		this.upstairsLeft = upstairsLeft;
	}

	@Override
	public String toString() {
		return "TdRequisition [id=" + id + ", diySiteTitle=" + diySiteTitle + ", diySiteId=" + diySiteId
				+ ", customerName=" + customerName + ", customerId=" + customerId + ", orderNumber=" + orderNumber
				+ ", totalPrice=" + totalPrice + ", leftPrice=" + leftPrice + ", deliveryTime=" + deliveryTime
				+ ", receiveName=" + receiveName + ", receiveAddress=" + receiveAddress + ", province=" + province
				+ ", city=" + city + ", disctrict=" + disctrict + ", subdistrict=" + subdistrict + ", detailAddress="
				+ detailAddress + ", receivePhone=" + receivePhone + ", requisiteGoodsList=" + requisiteGoodsList
				+ ", orderTime=" + orderTime + ", typeId=" + typeId + ", remarkInfo=" + remarkInfo + ", diySiteTel="
				+ diySiteTel + ", diyCode=" + diyCode + ", sellerRealName=" + sellerRealName + ", sellerTel="
				+ sellerTel + ", goodsQuantity=" + goodsQuantity + ", upstairsAll=" + upstairsAll + ", upstairsLeft="
				+ upstairsLeft + "]";
	}
}
