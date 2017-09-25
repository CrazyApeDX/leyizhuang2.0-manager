package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 城市实体类
 * 
 * @author Administrator
 */

@Entity
public class TdCity {

	// 城市Id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 省份
	@Column
	private String province;
	
	//EBS传递下来的唯一标示，本用作分公司id，赋值到此处
	@Column
	private Long sobIdCity;

	// 所属分公司id
	@Column
	private Long companyId;

	// 城市名
	@Column
	private String cityName;

	// 是否开通配送服务
	@Column
	private Boolean citySend;

	// 上级城市
	@Column
	private String parentCityName;

	// 上级城市是否开通配送业务
	@Column
	private Boolean parentCitySend;

	// 价目表Id
	@Column
	private Long priceListId;

	// 短信息账户Id
	@Column
	private Long smsAccountId;

	// 配送起始时间（小时）
	@Column
	private Long beginHour;
	
	//配送起始时间（分）
	@Column
	private Long beginMinute;

	// 配送结束时间（小时）
	@Column
	private Long finishHour;

	//配送结束时间（分）
	@Column
	private Long finishMinute;
	
	// 延迟时间（小时）
	@Column
	private Long delayHour;

	// 排序号
	@Column
	private Double sortId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Boolean getCitySend() {
		return citySend;
	}

	public void setCitySend(Boolean citySend) {
		this.citySend = citySend;
	}

	public String getParentCityName() {
		return parentCityName;
	}

	public void setParentCityName(String parentCityName) {
		this.parentCityName = parentCityName;
	}

	public Boolean getParentCitySend() {
		return parentCitySend;
	}

	public void setParentCitySend(Boolean parentCitySend) {
		this.parentCitySend = parentCitySend;
	}

	public Long getPriceListId() {
		return priceListId;
	}

	public void setPriceListId(Long priceListId) {
		this.priceListId = priceListId;
	}

	public Long getSmsAccountId() {
		return smsAccountId;
	}

	public void setSmsAccountId(Long smsAccountId) {
		this.smsAccountId = smsAccountId;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}
	public Long getBeginHour() {
		return beginHour;
	}

	public void setBeginHour(Long beginHour) {
		this.beginHour = beginHour;
	}

	public Long getBeginMinute() {
		return beginMinute;
	}

	public void setBeginMinute(Long beginMinute) {
		this.beginMinute = beginMinute;
	}

	public Long getFinishHour() {
		return finishHour;
	}

	public void setFinishHour(Long finishHour) {
		this.finishHour = finishHour;
	}

	public Long getFinishMinute() {
		return finishMinute;
	}

	public void setFinishMinute(Long finishMinute) {
		this.finishMinute = finishMinute;
	}

	public Long getDelayHour() {
		return delayHour;
	}

	public void setDelayHour(Long delayHour) {
		this.delayHour = delayHour;
	}

	public Long getSobIdCity() {
		return sobIdCity;
	}

	public void setSobIdCity(Long sobIdCity) {
		this.sobIdCity = sobIdCity;
	}

	@Override
	public String toString() {
		return "TdCity [id=" + id + ", province=" + province + ", companyId=" + companyId + ", cityName=" + cityName
				+ ", citySend=" + citySend + ", parentCityName=" + parentCityName + ", parentCitySend=" + parentCitySend
				+ ", priceListId=" + priceListId + ", smsAccountId=" + smsAccountId + ", beginHour=" + beginHour
				+ ", beginMinute=" + beginMinute + ", finishHour=" + finishHour + ", finishMinute=" + finishMinute
				+ ", delayHour=" + delayHour + ", sortId=" + sortId + "]";
	}
}
