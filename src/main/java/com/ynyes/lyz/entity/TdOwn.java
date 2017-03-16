package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author yanle 欠款报表实体
 *
 */
@Entity
public class TdOwn {

	@Id
	@GenericGenerator(name= "uuidGenerator",strategy = "uuid")
	private String id;

	// 门店名称
	@Column
	private String diySiteName;

	// 分单号
	@Column
	private String mainOrderNumber;

	// 订单状态
	@Column
	private Integer statusId;

	// 订单日期（下单日期）
	@Column
	private Date orderTime;

	// 销顾姓名
	@Column
	private String sellerRealName;

	// 销顾电话
	@Column
	private String sellerUsername;
	
	//客户编号
	@Column
	private Long userId;
	
	// 客户电话
	@Column
	private String username;

	// 客户名称
	@Column
	private String realUserRealName;
	

	// 欠款金额
	@Column
	private Double owned;

	// 出货仓库
	@Column
	private String whName;

	// 配送员姓名
	@Column
	private String duRealName;

	// 配送员电话
	@Column
	private String duUsername;

	// 收获地址
	@Column
	private String shippingAddress;

	// 订单备注
	@Column
	private String remark;

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

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealUserRealName() {
		return realUserRealName;
	}

	public void setRealUserRealName(String realUserRealName) {
		this.realUserRealName = realUserRealName;
	}

	public Double getOwned() {
		return owned;
	}

	public void setOwned(Double owned) {
		this.owned = owned;
	}

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public String getDuRealName() {
		return duRealName;
	}

	public void setDuRealName(String duRealName) {
		this.duRealName = duRealName;
	}

	public String getDuUsername() {
		return duUsername;
	}

	public void setDuUsername(String duUsername) {
		this.duUsername = duUsername;
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
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TdOwn [id=" + id + ", diySiteName=" + diySiteName + ", mainOrderNumber=" + mainOrderNumber
				+ ", statusId=" + statusId + ", orderTime=" + orderTime + ", sellerRealName=" + sellerRealName
				+ ", sellerUsername=" + sellerUsername + ", userId=" + userId + ", username=" + username
				+ ", realUserRealName=" + realUserRealName + ", owned=" + owned + ", whName=" + whName + ", duRealName="
				+ duRealName + ", duUsername=" + duUsername + ", shippingAddress=" + shippingAddress + ", remark="
				+ remark + "]";
	}

	

}
