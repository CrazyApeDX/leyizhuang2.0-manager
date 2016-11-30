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
 * 库存日志实体类
 * 
 * @author
 */

@Entity
public class TdInventoryLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 活动名称
//	@Column(unique = true)
//	private String name;
	
	// 库存改变时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	// 物品id
	@Column
	private Long goodsId;
	
	// 物品名称
	@Column
	private String goodsTitle;
	
	//判断是否是管理员改变库存：    true ：管理员 ,false ：购买改变库存
	@Column(nullable = false)
	private Boolean isManager;
	
	// 库存修改账号
	@Column
	private String username;

	// 物品总数
	@Column
	private Long totalNumber;

	// 物品剩余数量
	@Column
	private Long leftNumber;
	
	// 变化数量
	@Column
	private Long changeNumber;

	// 消费的城市名字
	@Column
	private String cityName;
	
	// 消费的城市id
	@Column
	private String cityId;
	
	// 消费的门店名称
	@Column
	private String siteName;
	
	// 消费的城市id
	@Column
	private String siteId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public Long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Long totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Long getLeftNumber() {
		return leftNumber;
	}

	public void setLeftNumber(Long leftNumber) {
		this.leftNumber = leftNumber;
	}

	public Long getChangeNumber() {
		return changeNumber;
	}

	public void setChangeNumber(Long changeNumber) {
		this.changeNumber = changeNumber;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	

	
}
