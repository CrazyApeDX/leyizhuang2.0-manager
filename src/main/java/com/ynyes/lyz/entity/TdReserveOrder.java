package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author yanle 未提货报表
 *
 */
@Entity
public class TdReserveOrder {

	@Id
	@GenericGenerator(name="uuidGenerator",strategy="uuid")
	@GeneratedValue(generator="uuidGenerator")
	private String id;
	
	//未提货类型
	
	@Column
	private String reserveType;

	// 门店名称
	@Column
	private String diySiteName;
	
	//城市
	@Column
	private String city;

	// 确认日期
	@Column
	private Date getTime;
	
	//客户编号
	@Column
	private Long userId;
	
	// 客户名称
	@Column
	private String realUserRealName;

	// 客户电话
	@Column
	private String username;

	// 销顾姓名
	@Column
	private String sellerRealName;

	// 商品编码
	@Column
	private String sku;

	// 商品数量
	@Column
	private Integer quantity;

	// 购买时单价
	@Column
	private Double buyPrice;

	// 总额
	@Column
	private Double totalPrice;

	// 券订单号
	@Column
	private String couponOrderNumber;

	

	

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

	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	public String getRealUserRealName() {
		return realUserRealName;
	}

	public void setRealUserRealName(String realUserRealName) {
		this.realUserRealName = realUserRealName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSellerRealName() {
		return sellerRealName;
	}

	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCouponOrderNumber() {
		return couponOrderNumber;
	}

	public void setCouponOrderNumber(String couponOrderNumber) {
		this.couponOrderNumber = couponOrderNumber;
	}
	
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	

	public String getReserveType() {
		return reserveType;
	}

	public void setReserveType(String reserveType) {
		this.reserveType = reserveType;
	}
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TdReserveOrder [id=" + id + ", reserveType=" + reserveType + ", diySiteName=" + diySiteName + ", city="
				+ city + ", getTime=" + getTime + ", userId=" + userId + ", realUserRealName=" + realUserRealName
				+ ", username=" + username + ", sellerRealName=" + sellerRealName + ", sku=" + sku + ", quantity="
				+ quantity + ", buyPrice=" + buyPrice + ", totalPrice=" + totalPrice + ", couponOrderNumber="
				+ couponOrderNumber + "]";
	}

	
}
