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
 * 促销活动实体类
 * 
 * @author
 */

@Entity
public class TdActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 活动名称
	@Column(unique = true)
	private String name;

	// 是否属于首页推荐活动
	@Column
	private Boolean isCommendIndex;

	// 活动开始时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDate;

	// 活动结束时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishDate;

	// 排序号
	@Column
	private Double sortId;

	// 参加活动商品及其数量（规则以id_numbers代表一对数据，多对数据之前以","分割）
	@Column
	private String goodsNumber;

	// 赠送的礼品的id及其数量（规则以id_numbers代表一对数据，多对数据之前以","分割）
	@Column
	private String giftNumber;

	// 城市id
	@Column
	private Long cityId;

	// 城市名称
	@Column
	private String cityName;

	// 门店名称
	@Column
	private String siteName;

	// 是否是小辅料赠送活动
	@Column
	private Boolean isPresented;

	// 门店id（多个以","分割）
	@Column
	private String diySiteIds;

	// 参加活动的商品的名称（多个以","分割）
	@Column
	private String goodsNames;

	// 赠送的礼品的名称（多个以","分割）
	@Column
	private String giftNames;

	// 参加活动商品个数
	@Column
	private Integer totalGoods;
	// 参加活动商品个数
	@Column
	private Integer totalGift;

	@Column
	private Integer totalDiySite;

	// 赠品
	@OneToMany
	@JoinColumn(name = "ownerActivityId")
	private List<TdGoodsGift> giftList;

	// 商品
	@OneToMany
	@JoinColumn(name = "ownerActivityId")
	private List<TdGoodsCombination> combList;

	// 商品
	@OneToMany
	@JoinColumn(name = "ownerActivityId")
	private List<TdDiySiteList> siteList;

	// 赠送类型（目前：0. 代表赠送的商品；1. 代表赠送的产品券）
	@Column
	private Long giftType;

	// 活动图片
	@Column
	private String activityImg;

	// 最低购买量
	@Column
	private Long totalNumber;

	// 满足金额
	@Column(scale = 2)
	private Double totalPrice;

	// 是否是组合促销（true代表是组合促销，false代表是阶梯促销）
	@Column
	private Boolean isCombo;

	// 是否是满金额(true 代表是满金额条件，false代表不是满金额条件)
	@Column
	private Boolean isEnoughMoney;

	// 立减金额
	@Column(scale = 2)
	private Double subPrice;
	
	//促销对象
	@Column
	private Integer activityTarget;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public List<TdDiySiteList> getSiteList() {
		return siteList;
	}

	public void setSiteList(List<TdDiySiteList> siteList) {
		this.siteList = siteList;
	}

	public Integer getTotalDiySite() {
		return totalDiySite;
	}

	public void setTotalDiySite(Integer totalDiySite) {
		this.totalDiySite = totalDiySite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsCommendIndex() {
		return isCommendIndex;
	}

	public void setIsCommendIndex(Boolean isCommendIndex) {
		this.isCommendIndex = isCommendIndex;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getGiftNumber() {
		return giftNumber;
	}

	public void setGiftNumber(String giftNumber) {
		this.giftNumber = giftNumber;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Boolean getIsPresented() {
		return isPresented;
	}

	public void setIsPresented(Boolean isPresented) {
		this.isPresented = isPresented;
	}

	public String getDiySiteIds() {
		return diySiteIds;
	}

	public void setDiySiteIds(String diySiteIds) {
		this.diySiteIds = diySiteIds;
	}

	public String getGoodsNames() {
		return goodsNames;
	}

	public void setGoodsNames(String goodsNames) {
		this.goodsNames = goodsNames;
	}

	public String getGiftNames() {
		return giftNames;
	}

	public void setGiftNames(String giftNames) {
		this.giftNames = giftNames;
	}

	public Integer getTotalGoods() {
		return totalGoods;
	}

	public void setTotalGoods(Integer totalGoods) {
		this.totalGoods = totalGoods;
	}

	public Integer getTotalGift() {
		return totalGift;
	}

	public void setTotalGift(Integer totalGift) {
		this.totalGift = totalGift;
	}

	public List<TdGoodsGift> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<TdGoodsGift> giftList) {
		this.giftList = giftList;
	}

	public List<TdGoodsCombination> getCombList() {
		return combList;
	}

	public void setCombList(List<TdGoodsCombination> combList) {
		this.combList = combList;
	}

	public Long getGiftType() {
		return giftType;
	}

	public void setGiftType(Long giftType) {
		this.giftType = giftType;
	}

	public String getActivityImg() {
		return activityImg;
	}

	public void setActivityImg(String activityImg) {
		this.activityImg = activityImg;
	}

	public Long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Long totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Boolean getIsCombo() {
		return isCombo;
	}

	public void setIsCombo(Boolean isCombo) {
		this.isCombo = isCombo;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Boolean getIsEnoughMoney() {
		return isEnoughMoney;
	}

	public void setIsEnoughMoney(Boolean isEnoughMoney) {
		this.isEnoughMoney = isEnoughMoney;
	}

	public Double getSubPrice() {
		return subPrice;
	}

	public void setSubPrice(Double subPrice) {
		this.subPrice = subPrice;
	}

	@Override
	public String toString() {
		return "TdActivity [id=" + id + ", name=" + name + ", isCommendIndex=" + isCommendIndex + ", beginDate="
				+ beginDate + ", finishDate=" + finishDate + ", sortId=" + sortId + ", goodsNumber=" + goodsNumber
				+ ", giftNumber=" + giftNumber + ", cityId=" + cityId + ", cityName=" + cityName + ", siteName="
				+ siteName + ", isPresented=" + isPresented + ", diySiteIds=" + diySiteIds + ", goodsNames="
				+ goodsNames + ", giftNames=" + giftNames + ", totalGoods=" + totalGoods + ", totalGift=" + totalGift
				+ ", totalDiySite=" + totalDiySite + ", giftList=" + giftList + ", combList=" + combList + ", siteList="
				+ siteList + ", giftType=" + giftType + ", activityImg=" + activityImg + "]";
	}

}
