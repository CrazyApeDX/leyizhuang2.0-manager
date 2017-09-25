package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author yanle
 * 
 *         收款报表实体类
 *
 */

@Entity
public class TdReceipt {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	// 门店名称
	@Column
	private String diySiteName;

	// 收款类型
	@Column
	private String receiptType;

	// 主单号
	@Column
	private String mainOrderNumber;

	// 分单号
	@Column
	private String orderNumber;
	
	//会员编号,取td_user表中的id值
	@Column
	private Long userId;
	
	// 会员姓名
	@Column
	private String realUserRealName;

	// 用户名
	@Column
	private String username;

	// 导购
	@Column
	private String sellerRealName;

	// 订单时间
	@Column
	private Date orderTime;

	// 配送方式
	@Column
	private String deliverTypeTitle;

	// 收款时间&退款时间
	@Column
	private String receiptTime;

	// 预存款
	@Column(scale = 2)
	private Double actualPay;

	// 第三方支付
	@Column(scale = 2)
	private Double otherPay;

	// 门店现金
	@Column(scale = 2)
	private Double diyCash;

	// 门店POS
	@Column(scale = 2)
	private Double diyPos;
	
	//门店其他
	@Column(scale = 2)
	private Double diyOther;
	
	// 汇总
	@Column(scale = 2)
	private Double summary;

	// 创建人
	@Column
	private String createUsername;

	// 城市
	@Column
	private String cityName;

	// 配送门店编号
	@Column
	private String diySiteCode;

	// 门店id
	@Column
	private Long diyId;
	
	//POS刷卡流水号
    @Column
    private Long serialNumber;
	
	private Date realPayTime;

	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	

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

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
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

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getDeliverTypeTitle() {
		return deliverTypeTitle;
	}

	public void setDeliverTypeTitle(String deliverTypeTitle) {
		this.deliverTypeTitle = deliverTypeTitle;
	}

	public String getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
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

	public Double getDiyCash() {
		return diyCash;
	}

	public void setDiyCash(Double diyCash) {
		this.diyCash = diyCash;
	}

	public Double getDiyPos() {
		return diyPos;
	}

	public void setDiyPos(Double diyPos) {
		this.diyPos = diyPos;
	}
	
	
	
	public Double getDiyOther() {
		return diyOther;
	}

	public void setDiyOther(Double diyOther) {
		this.diyOther = diyOther;
	}

	public Double getSummary() {
		return summary;
	}

	public void setSummary(Double summary) {
		this.summary = summary;
	}
	
	

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDiySiteCode() {
		return diySiteCode;
	}

	public void setDiySiteCode(String diySiteCode) {
		this.diySiteCode = diySiteCode;
	}

	public Long getDiyId() {
		return diyId;
	}

	public void setDiyId(Long diyId) {
		this.diyId = diyId;
	}
	
	

	public Date getRealPayTime() {
		return realPayTime;
	}

	public void setRealPayTime(Date realPayTime) {
		this.realPayTime = realPayTime;
	}
	
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public String toString() {
		return "TdReceipt [id=" + id + ", diySiteName=" + diySiteName + ", receiptType=" + receiptType
				+ ", mainOrderNumber=" + mainOrderNumber + ", orderNumber=" + orderNumber + ", userId=" + userId
				+ ", realUserRealName=" + realUserRealName + ", username=" + username + ", sellerRealName="
				+ sellerRealName + ", orderTime=" + orderTime + ", deliverTypeTitle=" + deliverTypeTitle
				+ ", receiptTime=" + receiptTime + ", actualPay=" + actualPay + ", otherPay=" + otherPay + ", diyCash="
				+ diyCash + ", diyPos=" + diyPos + ", diyOther=" + diyOther + ", summary=" + summary
				+ ", createUsername=" + createUsername + ", cityName=" + cityName + ", diySiteCode=" + diySiteCode
				+ ", diyId=" + diyId + ", realPayTime=" + realPayTime + "]";
	}

	

	
	

	

	

}
