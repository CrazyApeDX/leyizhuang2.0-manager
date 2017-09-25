package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author yanle 统计活跃会员使用的销量数据
 *
 */
@Entity
public class TdActiveUser {

	@Id
	@GenericGenerator(name= "paymentableGenerator",strategy = "uuid")
	private String id;

	// 城市
	@Column
	private String cityName;

	// 门店名称
	@Column
	private String diySiteName;

	// 销顾电话
	@Column
	private String sellerUsername;

	// 销顾姓名
	@Column
	private String sellerRealName;

	// 销量汇总
	@Column
	private Double salesSummary;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDiySiteName() {
		return diySiteName;
	}

	public void setDiySiteName(String diySiteName) {
		this.diySiteName = diySiteName;
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

	public Double getSalesSummary() {
		return salesSummary;
	}

	public void setSalesSummary(Double salesSummary) {
		this.salesSummary = salesSummary;
	}

	@Override
	public String toString() {
		return "TdActiveUser [id=" + id + ", cityName=" + cityName + ", diySiteName=" + diySiteName
				+ ", sellerUsername=" + sellerUsername + ", sellerRealName=" + sellerRealName + ", salesSummary="
				+ salesSummary + "]";
	}
	
	

}
