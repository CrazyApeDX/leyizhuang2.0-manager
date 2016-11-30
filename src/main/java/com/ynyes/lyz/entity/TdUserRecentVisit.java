package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 用户最近访问记录
 * 
 * @author Sharon
 *
 */

@Entity
public class TdUserRecentVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 用户名
    @Column
    private String username;
    
    //用户id
    @Column
    private Long userId;
    
    // 商品Id
    @Column
    private Long goodsId;
    
    //商品SKU
    @Column
    private String sku; 
    
    // 商品标题
    @Column
    private String goodsTitle;
    
    // 商品封面图片
    @Column
    private String goodsCoverImageUri;
    
    // 商品价格
    @Column
    private Double goodsSalePrice;
    
    // 访问时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date visitTime;
    
    // 排序号
    @Column
    private Double sortId;

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

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGoodsId() {
        return goodsId;
    }

    public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public Double getSortId() {
        return sortId;
    }

    public void setSortId(Double sortId) {
        this.sortId = sortId;
    }

    public String getGoodsCoverImageUri() {
        return goodsCoverImageUri;
    }

    public void setGoodsCoverImageUri(String goodsCoverImageUri) {
        this.goodsCoverImageUri = goodsCoverImageUri;
    }

    public Double getGoodsSalePrice() {
        return goodsSalePrice;
    }

    public void setGoodsSalePrice(Double goodsSalePrice) {
        this.goodsSalePrice = goodsSalePrice;
    }
}
