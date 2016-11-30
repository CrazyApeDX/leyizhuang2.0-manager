package com.ynyes.lyz.entity.delivery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "td_delivery_fee_head")
public class TdDeliveryFeeHead {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 10, nullable = false)
	private Long sobId;
	
	@Column(length = 20, nullable = false)
	private Long goodsId;
	
	@Column(length = 40, nullable = false)
	private String goodsTitle;
	
	@Column(length = 20, nullable = false)
	private String goodsSku;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSobId() {
		return sobId;
	}

	public void setSobId(Long sobId) {
		this.sobId = sobId;
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

	public String getGoodsSku() {
		return goodsSku;
	}

	public void setGoodsSku(String goodsSku) {
		this.goodsSku = goodsSku;
	}

	@Override
	public String toString() {
		return "TdDeliveryFeeHeader [id=" + id + ", sobId=" + sobId + ", goodsId=" + goodsId + ", goodsTitle="
				+ goodsTitle + ", goodsSku=" + goodsSku + "]";
	}
}
