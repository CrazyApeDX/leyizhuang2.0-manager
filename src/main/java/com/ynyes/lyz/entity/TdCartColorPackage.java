package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 已选调色包
 * 
 * @auhor dengxiao
 */
@Entity
public class TdCartColorPackage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 归属用户名
	@Column
	private String username;

	// 调色包商品的id
	@Column
	private Long goodsId;
	
	//调色包商品名
	@Column
	private String goodsTitle;

	// 调色包id
	@Column
	private Long colorPackageId;

	// 调色包编号（sku）
	@Column
	private String number;

	// 调色包图片
	@Column
	private String imageUri;

	// 当前销售价格
	@Column(scale = 2)
	private Double salePrice;
	
	//实际销售价格
	@Column
	private Double realPrice;

	// 数量
	@Column(scale = 2)
	private Long quantity;

	// 总价
	@Column
	private Double totalPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getColorPackageId() {
		return colorPackageId;
	}

	public void setColorPackageId(Long colorPackageId) {
		this.colorPackageId = colorPackageId;
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

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
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

	public Double getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
}
