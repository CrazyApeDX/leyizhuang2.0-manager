package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 咨询/投诉/回复类别实体类
 * 
 * @author dengxiao
 */

@Entity
public class TdUserSuggestionCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 分类名称
	@Column
	private String name;

	// 分类备注
	@Column
	private String remark;

	//是否使能
	@Column
	private Boolean isEnable;
	
	// 排序号
	@Column
	private Double sortId;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}
	
}
