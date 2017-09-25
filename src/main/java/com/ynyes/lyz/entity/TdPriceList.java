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

/**
 * 价目表实体类
 * 
 * @author dengxiao
 */
@Entity
public class TdPriceList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 价目表名称
	@Column
	private String name;
	
	//所属城市id
	@Column
	private Long cityId;
	
	//所属城市名称  zhangji
	@Column
	private String cityName;
	
	//价目表详情 zhangji
	@OneToMany
	@JoinColumn(name = "masterPriceListId")
	private List<TdPriceListItem> priceItemList;
	
	@Column
	private String priceListNumber;
	
	//商品总数 zhangji
	@Column
	private Integer totalItem;

	//创建者名称
	@Column
	private String username;
	
	// 排序号
	@Column
	private Double sortId;

//接口新增数据------------------------------
	//价目表ID
	@Column
	private Long listHeaderId;
	
	//分公司ID
	@Column
	private Long sobId;
	
	// 价格生效开始时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDateActive;
	
	// 价格生效开始时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDateActive;
	
	// 是否有效
	@Column
	private String activeFlag;
	
	// 描述
	@Column
	private String description;
	
	//价目表类型（1. 经销JX【进货价】；2. 零售LS（华润的产品）【虚拟价】3. 乐易装LYZ（包括乐易装和莹润）【会员价】）
	@Column
	private String priceType;
	
	//价目表类型描述
	private String priceTypeDesc;

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getPriceTypeDesc() {
		return priceTypeDesc;
	}

	public void setPriceTypeDesc(String priceTypeDesc) {
		this.priceTypeDesc = priceTypeDesc;
	}

	public Long getListHeaderId() {
		return listHeaderId;
	}

	public void setListHeaderId(Long listHeaderId) {
		this.listHeaderId = listHeaderId;
	}

	public Long getSobId() {
		return sobId;
	}

	public void setSobId(Long sobId) {
		this.sobId = sobId;
	}

	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
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
	
	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}
	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getcityName() {
		return cityName;
	}

	public void setcityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<TdPriceListItem> getPriceItemList() {
		return priceItemList;
	}

	public void setPriceItemList(List<TdPriceListItem> priceItemList) {
		this.priceItemList = priceItemList;
	}

	public Integer getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}

	public String getPriceListNumber() {
		return priceListNumber;
	}

	public void setPriceListNumber(String priceListNumber) {
		this.priceListNumber = priceListNumber;
	}

	@Override
	public String toString() {
		return "TdPriceList [id=" + id + ", name=" + name + ", cityId=" + cityId + ", username=" + username
				+ ", sortId=" + sortId + "]";
	}
}
