package com.ynyes.lyz.entity.report;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 配送考核报表
 * @author lenovo
 *
 */
@Entity
public class DeliveryCheckReport {
	
	
	
	@Id
	@GenericGenerator(name= "paymentableGenerator",strategy = "uuid")
	private String id;
	
	@Column
	private String whName;
	
	@Column
	private String distributionName;
	
	@Column
	private String distributionPhone;
	
	@Column
	private String mainOrderNumber;
	
	@Column
	private String diySiteName;
	
	@Column
	private String sellerRealName;
	
	@Column
	private String shippingAddress;
	
	@Column
	private Date orderTime;
	
	@Column
	private Date sendTime;
	
	@Column
	private Double agencyFund;
	
	@Column
	private Date operationTime;
	
	@Column
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public String getDistributionName() {
		return distributionName;
	}

	public void setDistributionName(String distributionName) {
		this.distributionName = distributionName;
	}

	public String getDistributionPhone() {
		return distributionPhone;
	}

	public void setDistributionPhone(String distributionPhone) {
		this.distributionPhone = distributionPhone;
	}

	public String getMainOrderNumber() {
		return mainOrderNumber;
	}

	public void setMainOrderNumber(String mainOrderNumber) {
		this.mainOrderNumber = mainOrderNumber;
	}

	public String getDiySiteName() {
		return diySiteName;
	}

	public void setDiySiteName(String diySiteName) {
		this.diySiteName = diySiteName;
	}

	public String getSellerRealName() {
		return sellerRealName;
	}

	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Double getAgencyFund() {
		return agencyFund;
	}

	public void setAgencyFund(Double agencyFund) {
		this.agencyFund = agencyFund;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "DeliveryCheckReport [id=" + id + ", whName=" + whName + ", distributionName=" + distributionName
				+ ", distributionPhone=" + distributionPhone + ", mainOrderNumber=" + mainOrderNumber + ", diySiteName="
				+ diySiteName + ", sellerRealName=" + sellerRealName + ", shippingAddress=" + shippingAddress
				+ ", orderTime=" + orderTime + ", sendTime=" + sendTime + ", agencyFund=" + agencyFund
				+ ", operationTime=" + operationTime + ", remark=" + remark + "]";
	}

	
	
	
	
	
	
}
