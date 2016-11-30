package com.ynyes.lyz.entity.delivery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>标题：TdDeliveryFeeLine.java</p>
 * <p>描述：</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年11月17日上午9:38:13
 */
@Entity
@Table(name = "td_delivery_fee_line")
public class TdDeliveryFeeLine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 20, nullable = false)
	private Long headId;
	
	@Column(length = 20, nullable = false)
	private Long minNumber;
	
	@Column(length = 20, nullable = false)
	private Long maxNumber;
	
	@Column(length = 20, scale = 2, nullable = false)
	private Double unit;
	
	@Column(length = 20, scale = 2, nullable = true)
	private Double fixedPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHeadId() {
		return headId;
	}

	public void setHeadId(Long headId) {
		this.headId = headId;
	}

	public Long getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(Long minNumber) {
		this.minNumber = minNumber;
	}

	public Long getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Long maxNumber) {
		this.maxNumber = maxNumber;
	}

	public Double getUnit() {
		return unit;
	}

	public void setUnit(Double unit) {
		this.unit = unit;
	}

	public Double getFixedPrice() {
		return fixedPrice;
	}

	public void setFixedPrice(Double fixedPrice) {
		this.fixedPrice = fixedPrice;
	}

	@Override
	public String toString() {
		return "TdDeliveryFeeLine [id=" + id + ", minNumber=" + minNumber + ", maxNumber=" + maxNumber + ", unit="
				+ unit + ", fixedPrice=" + fixedPrice + "]";
	}
}
