package com.ynyes.fitment.foundation.entity.client;

import java.util.List;

import com.ynyes.fitment.core.entity.client.ClientEntity;

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
}
