package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 商品组合
 * 
 * @author Sharon
 *
 */

@Entity
public class TdGoodsCombination {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 商品ID
	@Column
	private Long goodsId;
	
	// 商品名称
	@Column
	private String goodsTitle;
	
	// 商品价格
	@Column(scale=2)
	private Double goodsPrice;
	
	// 组合价
    @Column(scale=2)
    private Double currentPrice;
	
	// 商品缩略图
	@Column
	private String coverImageUri;
	
	// 排序号
	@Column
	private Double sortId;
	
	// 数量
	@Column
	private Long number;
	

    public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCoverImageUri() {
        return coverImageUri;
    }

    public void setCoverImageUri(String coverImageUri) {
        this.coverImageUri = coverImageUri;
    }

    public Double getSortId() {
        return sortId;
    }

    public void setSortId(Double sortId) {
        this.sortId = sortId;
    }

    
}
