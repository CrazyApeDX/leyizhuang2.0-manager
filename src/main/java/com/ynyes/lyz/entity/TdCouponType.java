package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 优惠券类型
 * 
 * @author Sharon
 *
 */

@Entity
public class TdCouponType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 名称
	@Column
	private String title;

	/*
	 * 更改分类：1. 通用现金券；2. 指定商品现金券；3. 产品券
	 * 
	 * @author dengxiao
	 */
	@Column
	private Long categoryId;

	/*
	 * 可使用商品id（针对于通用 现金券，其值为null）
	 * 
	 * @author dengxiao
	 */
	@Column
	private Long goodsId;

	// 金额
	@Column(scale = 2)
	private Double price;

	/*
	 * 针对于产品券，所显示的产品的图片
	 */
	@Column
	private String picUri;

	// 描述
	@Column
	private String description;

	// 排序号
	@Column
	private Long sortId;

	// 有效期限（天）
	@Column
	private Long totalDays;

	/*
	 * 开始时间
	 * 
	 * @author dengxiao
	 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date beginDate;

	/*
	 * 结束时间
	 * 
	 * @author dengxiao
	 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date finishDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPicUri() {
		return picUri;
	}

	public void setPicUri(String picUri) {
		this.picUri = picUri;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public Long getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Long totalDays) {
		this.totalDays = totalDays;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
}
