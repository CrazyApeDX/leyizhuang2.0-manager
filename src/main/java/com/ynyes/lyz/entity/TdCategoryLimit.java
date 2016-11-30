package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 城市限购实体类
 * 
 * @author DengXiao
 */

@Entity
public class TdCategoryLimit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 地区id
	@Column
	private Long sobId;

	// 类型编号
	@Column
	private Long categoryId;

	// 类型名称
	@Column
	private String title;

	// 父类型
	@Column
	private Long parentId;

	// 父类型列表
	@Column
	private String parentTree;

	// 层级
	@Column
	private Long layerCount;

	// EBS物料类别id
	@Column
	private Long invCategoryId;

	// 排序号
	@Column
	private Double sortId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSobId() {
		return sobId;
	}

	public void setSobId(Long sobId) {
		this.sobId = sobId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentTree() {
		return parentTree;
	}

	public void setParentTree(String parentTree) {
		this.parentTree = parentTree;
	}

	public Long getLayerCount() {
		return layerCount;
	}

	public void setLayerCount(Long layerCount) {
		this.layerCount = layerCount;
	}

	public Long getInvCategoryId() {
		return invCategoryId;
	}

	public void setInvCategoryId(Long invCategoryId) {
		this.invCategoryId = invCategoryId;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

}
