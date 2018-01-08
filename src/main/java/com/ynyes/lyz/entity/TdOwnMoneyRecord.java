package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 代收款记录
 * 
 * @author Sharon
 *
 */

@Entity
public class TdOwnMoneyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    // 用户名
    @Column
    private String username;
    
    // 订单号(主单号)
    @Column
    private String orderNumber;
    
    // 已付 (配送员收款)
    @Column
    private Double payed;
    
    // 欠款 
    @Column
    private Double owned;
    
    // 创建时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    // 排序号
    @Column
    private Long sortId;

    // 是否审批通过
    @Column
    private Boolean isEnable;
    
    // 是否还清
    @Column
    private Boolean isPayed;
    
    // 门店编码
    @Column
    private String diyCode;
    
    // 是否审核 0:不同过，1：通过
    @Column
    private Boolean ispassed;
    
    //是否是欠款记录 （ 后面添加的字段 默认为true表示为欠款记录） zp
    @Column
    private Boolean isOwn;
    //收款现金（配送员）
    @Column
    private Double money;
    //收款pos（配送员）
    @Column
    private Double pos;
    //还款现金（门店）
    @Column
    private Double backMoney;
    //还款pos（门店）
    @Column
    private Double backPos;
    //POS刷卡流水号
    @Column
    private String serialNumber;
    
    // 新增：2016-08-25增加还款方式其他（门店）
    @Column
    private Double backOther;
    
    //收款时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    
    //还款时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date ownTime;
    
    // 实际收款时间
    @Column
    @Temporal(TemporalType.DATE)
    private Date realPayTime;
    

	public Boolean getIspassed() {
		return ispassed;
	}

	public void setIspassed(Boolean ispassed) {
		this.ispassed = ispassed;
	}

	public String getDiyCode() {
		return diyCode;
	}

	public void setDiyCode(String diyCode) {
		this.diyCode = diyCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getPayed() {
		return payed;
	}

	public void setPayed(Double payed) {
		this.payed = payed;
	}

	public Double getOwned() {
		return owned;
	}

	public void setOwned(Double owned) {
		this.owned = owned;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Boolean getIsPayed() {
		return isPayed;
	}

	public void setIsPayed(Boolean isPayed) {
		this.isPayed = isPayed;
	}

	public Boolean getIsOwn() {
		return isOwn;
	}

	public void setIsOwn(Boolean isOwn) {
		this.isOwn = isOwn;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getPos() {
		return pos;
	}

	public void setPos(Double pos) {
		this.pos = pos;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Double getBackMoney() {
		return backMoney;
	}

	public void setBackMoney(Double backMoney) {
		this.backMoney = backMoney;
	}

	public Double getBackPos() {
		return backPos;
	}

	public void setBackPos(Double backPos) {
		this.backPos = backPos;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getOwnTime() {
		return ownTime;
	}

	public void setOwnTime(Date ownTime) {
		this.ownTime = ownTime;
	}

	public Double getBackOther() {
		return backOther;
	}

	public void setBackOther(Double backOther) {
		this.backOther = backOther;
	}

	public Date getRealPayTime() {
		return realPayTime;
	}

	public void setRealPayTime(Date realPayTime) {
		this.realPayTime = realPayTime;
	}
	
}
