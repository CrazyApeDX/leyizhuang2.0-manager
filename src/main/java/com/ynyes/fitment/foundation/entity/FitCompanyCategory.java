package com.ynyes.fitment.foundation.entity;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

@Entity
@Table(name = "FIT_COMPANY_CATEGORY")
public class FitCompanyCategory extends TableEntity {

	@Column(nullable = false)
	private Long companyId;
	
	@Column(length = 10, nullable = false)
	private String categoryTitle;
	
	@Column(nullable = false)
	private Long categoryId;
	
	@Column(nullable = false)
	private Long categoryParentId = 0l;
	
	@Column(nullable = false, scale = 2)
	private Double categorySortId;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryParentId() {
		return categoryParentId;
	}

	public void setCategoryParentId(Long categoryParentId) {
		this.categoryParentId = categoryParentId;
	}

	public Double getCategorySortId() {
		return categorySortId;
	}

	public void setCategorySortId(Double categorySortId) {
		this.categorySortId = categorySortId;
	}
}
