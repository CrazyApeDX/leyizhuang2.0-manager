package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户关注表
 * 
 * @author Sharon
 *
 */

@Entity
public class TdUserCollect {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 用户名
	@Column
	private String username;

	// 商品Id
	@Column
	private Long goodsId;

	// 商品标题
	@Column
	private String goodsTitle;

	// 商品封面图片
	@Column
	private String goodsCoverImageUri;

	// OSS图片Key
	@Column
	private String goodsCoverImageUrl;
	
	// OSS图片访问路径
	@Column
	private String goodsCoverImagePath;
	
	// 收藏时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date collectTime;

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

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
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
	
	public String getGoodsCoverImageUrl() {
		return goodsCoverImageUrl;
	}

	public void setGoodsCoverImageUrl(String goodsCoverImageUrl) {
		this.goodsCoverImageUrl = goodsCoverImageUrl;
	}

	public String getGoodsCoverImagePath() {
		return goodsCoverImagePath;
	}

	public void setGoodsCoverImagePath(String goodsCoverImagePath) {
		this.goodsCoverImagePath = goodsCoverImagePath;
	}

	@Override
	public String toString() {
		return "TdUserCollect [id=" + id + ", username=" + username + ", goodsId=" + goodsId + ", goodsTitle="
				+ goodsTitle + ", goodsCoverImageUri=" + goodsCoverImageUri + ", goodsCoverImageUrl="
				+ goodsCoverImageUrl + ", goodsCoverImagePath=" + goodsCoverImagePath + ", collectTime=" + collectTime
				+ ", sortId=" + sortId + "]";
	}
}
