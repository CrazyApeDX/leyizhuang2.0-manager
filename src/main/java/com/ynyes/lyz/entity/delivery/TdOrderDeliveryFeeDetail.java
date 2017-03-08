package com.ynyes.lyz.entity.delivery;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "td_order_delivery_fee_detail")
public class TdOrderDeliveryFeeDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 主单号
	@Column(length = 100, nullable = false)
	private String mainOrderNumber;

	// 门店id
	@Column(length = 10, nullable = false)
	private Long diySiteId;

	// 门店名称
	@Column(length = 50)
	private String diySiteName;

	// 下单时间
	@Column
	private Date orderTime;

	// 封车时间
	@Column
	private Date sendTime;

	// 订单状态
	@Column(length = 10)
	private Integer statusId;

	// 销顾电话
	@Column(length = 20)
	private String sellerUsername;

	// 销顾姓名
	@Column(length = 50)
	private String sellerRealName;

	// 客户电话
	@Column(length = 20)
	private String username;

	// 客户姓名
	@Column(length = 50)
	private String userRealName;

	// 整单大桶漆配送费
	@Column(length = 100, nullable = false)
	private Double bucketsOfPaintFee;

	// 整单硝基漆配送费
	@Column(length = 100, nullable = false)
	private Double nitrolacquerFee;
	
	// 小桶漆/木器漆配送费
	@Column(length = 100, nullable = false)
	private Double carpentryPaintFee;

	// 4kg以下漆类配送费
	@Column(length = 100, nullable = false)
	private Double belowFourKiloFee;

	// 墙面辅料金额
	@Column(length = 100, nullable = false)
	private Double wallAccessories;

	// 优惠前客户承担运费金额
	@Column(length = 100, nullable = false)
	private Double consumerDeliveryFee;

	// 优惠前公司承担运费金额
	@Column(length = 100, nullable = false)
	private Double companyDeliveryFee;
	
	//运费总额不足20客户补差价
	@Column(length = 100, nullable = false)
	private Double consumerDeliveryFeeAdjust;
	
	//运费不足20华润补差价
	@Column(length = 100,nullable = false)
	private Double companyDeliveryFeeAdjust;

	// 客户运费打折金额
	@Column(length = 100, nullable = false)
	private Double consumerDeliveryFeeDiscount;

	// 华润公司运费打折金额
	@Column(length = 100, nullable = false)
	private Double companyDeliveryFeeDiscount;

	// 客户购辅料减客户运费金额
	@Column(length = 100, nullable = false)
	private Double consumerDeliveryFeeReduce;

	// 客户购购辅料减华润公司运费金额
	@Column(length = 100, nullable = false)
	private Double companyDeliveryFeeReduce;

	// 实收客户配送费
	@Column(length = 100, nullable = false)
	private Double consumerDeliveryFeeFinal;

	// 实收华润配送费
	@Column(length = 100, nullable = false)
	private Double companyDeliveryFeeFinal;
	
	//客户最终承担运费是否被后台修改过
	@Column(nullable =false)
	private Boolean isCustomerDeliveryFeeModified = false;
	
	//客户最终承担运费后台修改之前的值
	@Column(length =10,nullable=false)
	private Double customerDeliveryFeeBeforeModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMainOrderNumber() {
		return mainOrderNumber;
	}

	public void setMainOrderNumber(String mainOrderNumber) {
		this.mainOrderNumber = mainOrderNumber;
	}

	

	public Long getDiySiteId() {
		return diySiteId;
	}

	public void setDiySiteId(Long diySiteId) {
		this.diySiteId = diySiteId;
	}

	public String getDiySiteName() {
		return diySiteName;
	}

	public void setDiySiteName(String diySiteName) {
		this.diySiteName = diySiteName;
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

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	

	public String getSellerUsername() {
		return sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}

	public String getSellerRealName() {
		return sellerRealName;
	}

	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
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

	public Double getBucketsOfPaintFee() {
		return bucketsOfPaintFee;
	}

	public void setBucketsOfPaintFee(Double bucketsOfPaintFee) {
		this.bucketsOfPaintFee = bucketsOfPaintFee;
	}

	public Double getNitrolacquerFee() {
		return nitrolacquerFee;
	}

	public void setNitrolacquerFee(Double nitrolacquerFee) {
		this.nitrolacquerFee = nitrolacquerFee;
	}

	public Double getCarpentryPaintFee() {
		return carpentryPaintFee;
	}

	public void setCarpentryPaintFee(Double carpentryPaintFee) {
		this.carpentryPaintFee = carpentryPaintFee;
	}

	public Double getBelowFourKiloFee() {
		return belowFourKiloFee;
	}

	public void setBelowFourKiloFee(Double belowFourKiloFee) {
		this.belowFourKiloFee = belowFourKiloFee;
	}

	public Double getWallAccessories() {
		return wallAccessories;
	}

	public void setWallAccessories(Double wallAccessories) {
		this.wallAccessories = wallAccessories;
	}

	public Double getConsumerDeliveryFee() {
		return consumerDeliveryFee;
	}

	public void setConsumerDeliveryFee(Double consumerDeliveryFee) {
		this.consumerDeliveryFee = consumerDeliveryFee;
	}

	public Double getCompanyDeliveryFee() {
		return 	companyDeliveryFee;
	}

	public void setCompanyDeliveryFee(Double companyDeliveryFee) {
		this.companyDeliveryFee = companyDeliveryFee;
	}

	public Double getConsumerDeliveryFeeDiscount() {
		return consumerDeliveryFeeDiscount;
	}

	public void setConsumerDeliveryFeeDiscount(Double consumerDeliveryFeeDiscount) {
		this.consumerDeliveryFeeDiscount = consumerDeliveryFeeDiscount;
	}

	public Double getCompanyDeliveryFeeDiscount() {
		return companyDeliveryFeeDiscount;
	}

	public void setCompanyDeliveryFeeDiscount(Double companyDeliveryFeeDiscount) {
		this.companyDeliveryFeeDiscount = companyDeliveryFeeDiscount;
	}

	public Double getConsumerDeliveryFeeReduce() {
		return consumerDeliveryFeeReduce;
	}

	public void setConsumerDeliveryFeeReduce(Double consumerDeliveryFeeReduce) {
		this.consumerDeliveryFeeReduce = consumerDeliveryFeeReduce;
	}

	public Double getCompanyDeliveryFeeReduce() {
		return companyDeliveryFeeReduce;
	}

	public void setCompanyDeliveryFeeReduce(Double companyDeliveryFeeReduce) {
		this.companyDeliveryFeeReduce = companyDeliveryFeeReduce;
	}

	public Double getConsumerDeliveryFeeFinal() {
		return consumerDeliveryFeeFinal;
	}

	public void setConsumerDeliveryFeeFinal(Double consumerDeliveryFeeFinal) {
		this.consumerDeliveryFeeFinal = consumerDeliveryFeeFinal;
	}

	public Double getCompanyDeliveryFeeFinal() {
		return companyDeliveryFeeFinal;
	}

	public void setCompanyDeliveryFeeFinal(Double companyDeliveryFeeFinal) {
		this.companyDeliveryFeeFinal = companyDeliveryFeeFinal;
	}
	
	public Double getConsumerDeliveryFeeAdjust() {
		return consumerDeliveryFeeAdjust;
	}

	public void setConsumerDeliveryFeeAdjust(Double consumerDeliveryFeeAdjust) {
		this.consumerDeliveryFeeAdjust = consumerDeliveryFeeAdjust;
	}

	public Boolean getIsCustomerDeliveryFeeModified() {
		return isCustomerDeliveryFeeModified;
	}

	public void setIsCustomerDeliveryFeeModified(Boolean isCustomerDeliveryFeeModified) {
		this.isCustomerDeliveryFeeModified = isCustomerDeliveryFeeModified;
	}

	
	public Double getCustomerDeliveryFeeBeforeModified() {
		return customerDeliveryFeeBeforeModified;
	}

	public void setCustomerDeliveryFeeBeforeModified(Double customerDeliveryFeeBeforeModified) {
		this.customerDeliveryFeeBeforeModified = customerDeliveryFeeBeforeModified;
	}
	
	

	public Double getCompanyDeliveryFeeAdjust() {
		return companyDeliveryFeeAdjust;
	}

	public void setCompanyDeliveryFeeAdjust(Double companyDeliveryFeeAdjust) {
		this.companyDeliveryFeeAdjust = companyDeliveryFeeAdjust;
	}

	@Override
	public String toString() {
		return "TdOrderDeliveryFeeDetail [id=" + id + ", mainOrderNumber=" + mainOrderNumber + ", diySiteId="
				+ diySiteId + ", diySiteName=" + diySiteName + ", orderTime=" + orderTime + ", sendTime=" + sendTime
				+ ", statusId=" + statusId + ", sellerUsername=" + sellerUsername + ", sellerRealName=" + sellerRealName
				+ ", username=" + username + ", userRealName=" + userRealName + ", bucketsOfPaintFee="
				+ bucketsOfPaintFee + ", nitrolacquerFee=" + nitrolacquerFee + ", carpentryPaintFee="
				+ carpentryPaintFee + ", belowFourKiloFee=" + belowFourKiloFee + ", wallAccessories=" + wallAccessories
				+ ", consumerDeliveryFee=" + consumerDeliveryFee + ", companyDeliveryFee=" + companyDeliveryFee
				+ ", consumerDeliveryFeeAdjust=" + consumerDeliveryFeeAdjust + ", companyDeliveryFeeAdjust="
				+ companyDeliveryFeeAdjust + ", consumerDeliveryFeeDiscount=" + consumerDeliveryFeeDiscount
				+ ", companyDeliveryFeeDiscount=" + companyDeliveryFeeDiscount + ", consumerDeliveryFeeReduce="
				+ consumerDeliveryFeeReduce + ", companyDeliveryFeeReduce=" + companyDeliveryFeeReduce
				+ ", consumerDeliveryFeeFinal=" + consumerDeliveryFeeFinal + ", companyDeliveryFeeFinal="
				+ companyDeliveryFeeFinal + ", isCustomerDeliveryFeeModified=" + isCustomerDeliveryFeeModified
				+ ", customerDeliveryFeeBeforeModified=" + customerDeliveryFeeBeforeModified + "]";
	}

	
}
