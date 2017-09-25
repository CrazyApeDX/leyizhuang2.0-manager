package com.ynyes.fitment.foundation.entity.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ynyes.fitment.core.entity.client.ClientEntity;
import com.ynyes.fitment.foundation.entity.FitCompanyCategory;

public class ClientCategory extends ClientEntity {

	private Long id;

	private String title;

	private List<ClientCategory> children;

	public Long getId() {
		return id;
	}

	public ClientCategory setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public ClientCategory setTitle(String title) {
		this.title = title;
		return this;
	}

	public List<ClientCategory> getChildren() {
		return children;
	}

	public ClientCategory setChildren(List<ClientCategory> children) {
		this.children = children;
		return this;
	}

	public ClientCategory init(FitCompanyCategory category, List<FitCompanyCategory> categories) {
		List<ClientCategory> children = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(categories)) {
			for (FitCompanyCategory fitCompanyCategory : categories) {
				if (null != fitCompanyCategory) {
					children.add(new ClientCategory().setId(fitCompanyCategory.getCategoryId())
							.setTitle(fitCompanyCategory.getCategoryTitle()));
				}
			}
		}
		return this.setId(category.getCategoryId()).setTitle(title).setTitle(category.getCategoryTitle())
				.setChildren(children);
	}

}
