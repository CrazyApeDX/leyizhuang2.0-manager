package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 门店调拨商品明细实体类
 * 
 */

@Entity
public class TdAllocationDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private Long allocationId;

	@Column
	private Long goodId;

	@Column
	private String goodTitle;

	@Column
	private String goodSku;

	@Column
	private Long num;

	@Column
	private Long realNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(Long allocationId) {
		this.allocationId = allocationId;
	}

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public String getGoodTitle() {
		return goodTitle;
	}

	public void setGoodTitle(String goodTitle) {
		this.goodTitle = goodTitle;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Long getRealNum() {
		return realNum;
	}

	public void setRealNum(Long realNum) {
		this.realNum = realNum;
	}

	public String getGoodSku() {
		return goodSku;
	}

	public void setGoodSku(String goodSku) {
		this.goodSku = goodSku;
	}

	@Override
	public String toString() {
		return "TdAllocationDetail [id=" + id + ", allocationId=" + allocationId + ", goodId=" + goodId + ", goodTitle="
				+ goodTitle + ", goodSku=" + goodSku + ", num=" + num + ", realNum=" + realNum + "]";
	}
}
