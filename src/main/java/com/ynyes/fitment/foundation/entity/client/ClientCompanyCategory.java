package com.ynyes.fitment.foundation.entity.client;

import java.util.ArrayList;
import java.util.List;

import com.ynyes.fitment.core.entity.client.ClientEntity;
import com.ynyes.fitment.foundation.entity.FitCompanyCategory;

public class ClientCompanyCategory extends ClientEntity {

	private String title;
	
	private Long categoryId;
	
	private List<ClientCompanyCategory> children = new ArrayList<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<ClientCompanyCategory> getChildren() {
		return children;
	}

	public void setChildren(List<ClientCompanyCategory> children) {
		this.children = children;
	}
	
	public ClientCompanyCategory init(FitCompanyCategory category) {
		this.setTitle(category.getCategoryTitle());
		this.setCategoryId(category.getCategoryId());
		return this;
	}
}
