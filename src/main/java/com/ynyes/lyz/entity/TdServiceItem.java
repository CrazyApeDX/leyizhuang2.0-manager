package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 商城服务
 * 
 * @author Sharon
 *
 */

@Entity
public class TdServiceItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 服务名称
	@Column
	private String title;

	// 服务logo
	@Column
	private String logo;

	// 是否启用
	@Column
	private Boolean isEnable;

	/**
	 * @author lc
	 * @注释：添加服务类型判断 如果为true则是商品服务否则为整个商城服务
	 */
	@Column
	private Boolean isGoodsService;

	// 排序数字
	@Column
	private Double sortId;

	// 服务描述
	@Column
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Boolean getIsGoodsService() {
		return isGoodsService;
	}

	public void setIsGoodsService(Boolean isGoodsService) {
		this.isGoodsService = isGoodsService;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
