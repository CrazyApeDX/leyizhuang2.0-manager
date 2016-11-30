package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 调色包实体类
 * 
 * @author dengxiao
 */

@Entity
public class TdColorPackage {

	// id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 调色包名称
	@Column
	private String name;

	// 调色包编号
	@Column
	private String number;

	// 调色包图片
	@Column
	private String imageUri;

	// 库存
	@Column
	private Long inventory;

	// 当前销售价格
	@Column(scale = 2)
	private Double salePrice;

	// 当前进货价格
	@Column(scale = 2)
	private Double stockPrice;

	// 购买数量
	@Column
	private Long quantity;
	
	//总价
	@Column
	private Double totalPrice;

	// 归属： 1 华润商品 2 乐意装商品
	@Column
	private Long belongTo;

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public Long getInventory() {
		return inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(Double stockPrice) {
		this.stockPrice = stockPrice;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(Long belongTo) {
		this.belongTo = belongTo;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}
}
