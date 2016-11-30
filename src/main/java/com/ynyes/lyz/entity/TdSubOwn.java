package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author yanle 欠款报表分单明细
 *
 */
@Entity
public class TdSubOwn {

	@Id
	@GenericGenerator(name= "paymentableGenerator",strategy = "uuid")
	private String id;

	// 分单号
	@Column
	private String orderNumber;

	// 分单总金额
	@Column
	private Double totalPrice;

	// 分单第三方支付金额
	@Column
	private Double otherPay;

	// 分单配送收现金
	@Column
	private Double money;

	// 分单配送收pos
	@Column
	private Double pos;

	// 分单门店收现金
	@Column
	private Double backMoney;

	// 分单门店收pos
	@Column
	private Double backPos;

	// 分单门店收其它
	@Column
	private Double backOther;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getOtherPay() {
		return otherPay;
	}

	public void setOtherPay(Double otherPay) {
		this.otherPay = otherPay;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getPos() {
		return pos;
	}

	public void setPos(Double pos) {
		this.pos = pos;
	}

	public Double getBackMoney() {
		return backMoney;
	}

	public void setBackMoney(Double backMoney) {
		this.backMoney = backMoney;
	}

	public Double getBackPos() {
		return backPos;
	}

	public void setBackPos(Double backPos) {
		this.backPos = backPos;
	}

	public Double getBackOther() {
		return backOther;
	}

	public void setBackOther(Double backOther) {
		this.backOther = backOther;
	}

	@Override
	public String toString() {
		return "TdSubOwn [id=" + id + ", orderNumber=" + orderNumber + ", totalPrice=" + totalPrice + ", otherPay="
				+ otherPay + ", money=" + money + ", pos=" + pos + ", backMoney=" + backMoney + ", backPos=" + backPos
				+ ", backOther=" + backOther + "]";
	}

}
