package com.ynyes.fitment.foundation.entity.client;

import com.ynyes.fitment.core.entity.client.ClientEntity;

public class ClientGoods extends ClientEntity {

	private Long id;
	
	private String title;
	
	private String sku;
	
	private Double price;
	
	private Long inventory;

	public Long getId() {
		return id;
	}

	public ClientGoods setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public ClientGoods setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getSku() {
		return sku;
	}

	public ClientGoods setSku(String sku) {
		this.sku = sku;
		return this;
	}

	public Double getPrice() {
		return price;
	}

	public ClientGoods setPrice(Double price) {
		this.price = price;
		return this;
	}

	public Long getInventory() {
		return inventory;
	}

	public ClientGoods setInventory(Long inventory) {
		this.inventory = inventory;
		return this;
	}
}
