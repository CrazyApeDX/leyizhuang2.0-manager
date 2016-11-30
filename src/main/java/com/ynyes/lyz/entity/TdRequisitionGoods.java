package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 订单商品
 *
 * 记录了订单商品的相关信息
 * 
 * @author Sharon
 *
 */

@Entity
public class TdRequisitionGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 商品编码
    @Column
    private String goodsCode;
    
    // 商品名称
    @Column
    private String goodsTitle;
    
    // 成交价
    @Column(scale=2)
    private Double price;
    
    // 商品数量
    @Column
    private Long quantity;
    
    // 原单号
    @Column
    private String orderNumber;
    
    // 分单号
    @Column
    private String subOrderNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getSubOrderNumber() {
		return subOrderNumber;
	}

	public void setSubOrderNumber(String subOrderNumber) {
		this.subOrderNumber = subOrderNumber;
	}
    
}
