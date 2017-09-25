package com.ynyes.lyz.entity.upstairs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * 上楼费设置实体模型
 * 
 * @author dengxiao
 */
@Entity
public class TdUpstairsSetting {

	public TdUpstairsSetting() {
		super();
	}

	public TdUpstairsSetting(Long sobIdCity) {
		super();
		this.sobIdCity = sobIdCity;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 城市sobid
	@Column(length = 10, nullable = false, unique = true)
	private Long sobIdCity;

	// 板材类商品SKU，多个以英文逗号隔开
	@Column(nullable = false)
	@Lob
	private String panelSkus = "";

	// 龙骨类商品SKU，多个以英文逗号隔开
	@Column(nullable = false)
	@Lob
	private String keelSkus = "";

	// 金属龙骨类商品SKU，多个以英文逗号隔开
	@Column(nullable = false)
	@Lob
	private String metalSkus = "";

	// 板材上楼费单位价（步梯）
	@Column(length = 10, scale = 2, nullable = false)
	private Double panelStepUnit = 0d;

	// 龙骨上楼费单位价（步梯）
	@Column(length = 10, scale = 2, nullable = false)
	private Double keelStepUnit = 0d;

	// 金属龙骨上楼费单位价（步梯）
	@Column(length = 10, scale = 2, nullable = false)
	private Double metalStepUnit = 0d;

	// 板材上楼费单位价（电梯）
	@Column(length = 10, scale = 2, nullable = false)
	private Double panelEleUnit = 0d;

	// 龙骨上楼费单位家（电梯）
	@Column(length = 10, scale = 2, nullable = false)
	private Double keelEleUnit = 0d;

	// 金属龙骨上楼费单位价（电梯）
	@Column(length = 10, scale = 2, nullable = false)
	private Double metalEleUnit = 0d;

	// 龙骨单位数量
	@Column(length = 5, nullable = false)
	private Long keelUnitNumber = 1l;

	// 金属龙骨单位数量
	@Column(length = 5, nullable = false)
	private Long metalUnitNumber = 1l;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSobIdCity() {
		return sobIdCity;
	}

	public void setSobIdCity(Long sobIdCity) {
		this.sobIdCity = sobIdCity;
	}

	public String getPanelSkus() {
		return panelSkus;
	}

	public void setPanelSkus(String panelSkus) {
		this.panelSkus = panelSkus;
	}

	public String getKeelSkus() {
		return keelSkus;
	}

	public void setKeelSkus(String keelSkus) {
		this.keelSkus = keelSkus;
	}

	public String getMetalSkus() {
		return metalSkus;
	}

	public void setMetalSkus(String metalSkus) {
		this.metalSkus = metalSkus;
	}

	public Double getPanelStepUnit() {
		return panelStepUnit;
	}

	public void setPanelStepUnit(Double panelStepUnit) {
		this.panelStepUnit = panelStepUnit;
	}

	public Double getKeelStepUnit() {
		return keelStepUnit;
	}

	public void setKeelStepUnit(Double keelStepUnit) {
		this.keelStepUnit = keelStepUnit;
	}

	public Double getMetalStepUnit() {
		return metalStepUnit;
	}

	public void setMetalStepUnit(Double metalStepUnit) {
		this.metalStepUnit = metalStepUnit;
	}

	public Double getPanelEleUnit() {
		return panelEleUnit;
	}

	public void setPanelEleUnit(Double panelEleUnit) {
		this.panelEleUnit = panelEleUnit;
	}

	public Double getKeelEleUnit() {
		return keelEleUnit;
	}

	public void setKeelEleUnit(Double keelEleUnit) {
		this.keelEleUnit = keelEleUnit;
	}

	public Double getMetalEleUnit() {
		return metalEleUnit;
	}

	public void setMetalEleUnit(Double metalEleUnit) {
		this.metalEleUnit = metalEleUnit;
	}

	public Long getKeelUnitNumber() {
		return keelUnitNumber;
	}

	public void setKeelUnitNumber(Long keelUnitNumber) {
		this.keelUnitNumber = keelUnitNumber;
	}

	public Long getMetalUnitNumber() {
		return metalUnitNumber;
	}

	public void setMetalUnitNumber(Long metalUnitNumber) {
		this.metalUnitNumber = metalUnitNumber;
	}

	@Override
	public String toString() {
		return "TdUpstairsSetting [id=" + id + ", sobIdCity=" + sobIdCity + ", panelSkus=" + panelSkus + ", keelSkus="
				+ keelSkus + ", metalSkus=" + metalSkus + ", panelStepUnit=" + panelStepUnit + ", keelStepUnit="
				+ keelStepUnit + ", metalStepUnit=" + metalStepUnit + ", panelEleUnit=" + panelEleUnit
				+ ", keelEleUnit=" + keelEleUnit + ", metalEleUnit=" + metalEleUnit + ", keelUnitNumber="
				+ keelUnitNumber + ", metalUnitNumber=" + metalUnitNumber + "]";
	}
}
