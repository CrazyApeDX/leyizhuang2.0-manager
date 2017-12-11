package com.ynyes.lyz.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * @title 预存款提现信息
 * @describe 
 * @author Generation Road
 * @date 2017年12月7日
 */
@Entity
public class TdCashWithdraw implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6748752614759668531L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//客户id
	@Column
	private Long userId;
	
	//客户电话
	@Column
	private String username;
	
	// 客户姓名
	@Column
	private String realName;
	
	// 收款人姓名
	@Column
	private String payeeName;
		
	// 收款银行卡号
	@Column
	private String payeeCardNumber;		
	
	// 收款银行
	@Column
	private String cashBank;
	
	// 转出金额
	@Column(length = 10, scale = 2)
	private Double amount;
	
	// 提现单号
	@Column
	private String withdrawNumber;	
	
	// 备注
	@Column
	private String remarks;
	
	// 第三方交易单号
	@Column
	private String transactionNumber;
	
	// 状态
	@Column
	private String status;
	
	// 创建时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;	
	
	// 修改时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeCardNumber() {
		return payeeCardNumber;
	}

	public void setPayeeCardNumber(String payeeCardNumber) {
		this.payeeCardNumber = payeeCardNumber;
	}

	public String getCashBank() {
		return cashBank;
	}

	public void setCashBank(String cashBank) {
		this.cashBank = cashBank;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getWithdrawNumber() {
		return withdrawNumber;
	}

	public void setWithdrawNumber(String withdrawNumber) {
		this.withdrawNumber = withdrawNumber;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}	
	
}