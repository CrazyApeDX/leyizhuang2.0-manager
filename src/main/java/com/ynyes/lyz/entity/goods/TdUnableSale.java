package com.ynyes.lyz.entity.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>标题：TdUnableSale.java</p>
 * <p>描述：禁止销售实体模型</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年10月20日上午9:54:36
 */
@Entity
@Table(name = "TD_UNABLE_SALE")
public class TdUnableSale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "DIY_SITE_ID", length = 20, nullable = false)
	private Long diySiteId;
	
	@Column(name = "DIY_SITE_CODE", length = 10, nullable = false)
	private String diySiteCode;
	
	@Column(name = "GOODS_ID", length = 20, nullable = false)
	private Long goodsId;
	
	@Column(name = "GOODS_SKU", length = 20, nullable = false)
	private String goodsSku;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiySiteId() {
		return diySiteId;
	}

	public void setDiySiteId(Long diySiteId) {
		this.diySiteId = diySiteId;
	}

	public String getDiySiteCode() {
		return diySiteCode;
	}

	public void setDiySiteCode(String diySiteCode) {
		this.diySiteCode = diySiteCode;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsSku() {
		return goodsSku;
	}

	public void setGoodsSku(String goodsSku) {
		this.goodsSku = goodsSku;
	}

	@Override
	public String toString() {
		return "TdUnableSale [id=" + id + ", diySiteId=" + diySiteId + ", diySiteCode=" + diySiteCode + ", goodsId="
				+ goodsId + ", goodsSku=" + goodsSku + "]";
	}
}
