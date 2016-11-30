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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 促销活动小辅料实体类
 * 
 * @author
 */

@Entity
public class TdActivityGift {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 活动名称
	@Column
	private String name;

	// 赠送小辅料的名字
	@Column
	private String giftName;

	// 是否可用
	@Column
	private Boolean isUseable;

	// 活动创建时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creatTime;

	// 活动开始时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginTime;

	// 活动结束时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	// 排序号
	@Column
	private Double sortId;

	// 类别id
	@Column
	private Long categoryId;

	// 类别名字
	@Column
	private String categoryName;

	// 满足购买改类别赠送的数量
	@Column
	private Long buyNumber;

	// 赠品
	@OneToMany
	@JoinColumn(name = "ownerActivityGiftId")
	private List<TdActivityGiftList> giftList;

	// 赠品数量
	@Column
	private Integer totalGift;

	// 城市id
	@Column
	private Long cityId;

	// 门店id（多个以","分割）
	@Column
	private String diySiteIds;

	// 赠送类型（0代表商品，1代表小辅料）
	@Column
	private Long giftType;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGiftName() {
		return giftName;
	}

	public Boolean getIsUseable() {
		return isUseable;
	}

	public void setIsUseable(Boolean isUseable) {
		this.isUseable = isUseable;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(Long buyNumber) {
		this.buyNumber = buyNumber;
	}

	public List<TdActivityGiftList> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<TdActivityGiftList> giftList) {
		this.giftList = giftList;
	}

	public Integer getTotalGift() {
		return totalGift;
	}

	public void setTotalGift(Integer totalGift) {
		this.totalGift = totalGift;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getDiySiteIds() {
		return diySiteIds;
	}

	public void setDiySiteIds(String diySiteIds) {
		this.diySiteIds = diySiteIds;
	}

	public Long getGiftType() {
		return giftType;
	}

	public void setGiftType(Long giftType) {
		this.giftType = giftType;
	}

}
