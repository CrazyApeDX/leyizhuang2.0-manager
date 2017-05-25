package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 充值/提现记录实体类
 * 
 * @author dengxiao
 */

@Entity
public class TdBalanceLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 所属用户
	@Column
	private Long userId;

	// 所属用户名
	@Column
	private String username;

	// 金额 变化金额
	@Column(scale = 2)
	private Double money;

	// 类型（0. 代表充值；1. 代表提现; 2. 管理员修改; 3. 代表支付消费; 4.订单退款； 5. 经销差价返还；6.经销差价扣除）
	@Column
	private Long type;

	// 生成时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	// 完成时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date finishTime;

	// 是否成功
	@Column
	private Boolean isSuccess;

	// 修改原因
	@Column
	private String reason;

	// 成功后的用户余额
	@Column(scale = 2)
	private Double balance;

	// 管理员改变预存款的类型(0: balance 1: cashBalance 2:unCashBalance 3: 退款可提现余额；4.
	// 退款不可提现余额; 5. 消费返还经销差价；6. 取消订单扣除经销差价；7. 退货扣除经销差价）
	@Column
	private Long balanceType;

	// 操作人员
	@Column
	private String operator;

	// ip
	@Column
	private String operatorIp;

	// 使用订单号 (分单号) zp
	@Column
	private String orderNumber;

	// 预存款的类型名称
	@Transient
	private String balanceTypeName;

	// 门店id
	@Column
	private Long diySiteId;

	// 归属区域Id
	@Column
	private Long cityId;

	// 变动后可提现余额剩余
	@Column(scale = 2, nullable = true, length = 20)
	private Double cashLeft;

	// 变动后不可提现余额剩余
	@Column(scale = 2, nullable = true, length = 20)
	private Double unCashLeft;

	@Column(scale = 2, nullable = true, length = 20)
	private Double allLeft;

	// 到账时间
	@Column
	private String transferTime;

	// 商户订单号
	@Column
	private String userOrderNumber;

	@Column
	private String detailReason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorIp() {
		return operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}

	public Long getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Long balanceType) {
		this.balanceType = balanceType;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getBalanceTypeName() {
		if (this.balanceType == 0L) {
			return "总余额";
		} else if (this.balanceType == 1L || this.balanceType == 3L) {
			return "可提现余额";
		} else if (this.balanceType == 2L || this.balanceType == 4L) {
			return "不可提现余额";
		}
		return "";
	}

	public Long getDiySiteId() {
		return diySiteId;
	}

	public void setDiySiteId(Long diySiteId) {
		this.diySiteId = diySiteId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	// （type:0. 代表充值；1. 代表提现; 2. 管理员修改; 3. 代表支付消费; 4.订单退款）
	public String getTypeName() {
		if (this.type == 0L) {
			return "充值";
		} else if (this.type == 1L) {
			return "提现";
		} else if (this.type == 2L) {
			return "管理员修改";
		} else if (this.type == 3L) {
			return "支付";
		} else if (this.type == 4L) {
			return "退款";
		}
		return "";
	}

	public Double getCashLeft() {
		return cashLeft;
	}

	public void setCashLeft(Double cashLeft) {
		this.cashLeft = cashLeft;
	}

	public Double getUnCashLeft() {
		return unCashLeft;
	}

	public void setUnCashLeft(Double unCashLeft) {
		this.unCashLeft = unCashLeft;
	}

	public Double getAllLeft() {
		return allLeft;
	}

	public void setAllLeft(Double allLeft) {
		this.allLeft = allLeft;
	}

	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}

	public String getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}

	public String getUserOrderNumber() {
		return userOrderNumber;
	}

	public void setUserOrderNumber(String userOrderNumber) {
		this.userOrderNumber = userOrderNumber;
	}

	public String getDetailReason() {
		return detailReason;
	}

	public void setDetailReason(String detailReason) {
		this.detailReason = detailReason;
	}

}
