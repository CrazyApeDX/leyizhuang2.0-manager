package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户提现申请实体类
 * 
 * @author dengxiao
 */

@Entity
public class TdDeposit {

	public TdDeposit() {
		super();
	}

	public TdDeposit(String username, Long userId, Double money, Boolean isAgree, Boolean isRemit, Date createTime,
			Date agreeTime, Date remitTime, String reason, String remark) {
		super();
		this.username = username;
		this.userId = userId;
		this.money = money;
		this.isAgree = isAgree;
		this.isRemit = isRemit;
		this.createTime = createTime;
		this.agreeTime = agreeTime;
		this.remitTime = remitTime;
		this.reason = reason;
		this.remark = remark;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 用户名
	@Column
	private String username;

	// 用户id
	@Column
	private Long userId;
	
	// 体现单号
	private String number;

	// 提现金额
	@Column(scale = 2)
	private Double money;

	// 是否同意提现
	@Column
	private Boolean isAgree;

	// 是否已经打款
	@Column
	private Boolean isRemit;

	// 提现时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	// 处理（是否同意）时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date agreeTime;

	// 打款时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date remitTime;

	// 拒绝打款原因
	@Column
	private String reason;

	// 备注
	@Column
	private String remark;

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

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Boolean getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Boolean isAgree) {
		this.isAgree = isAgree;
	}

	public Boolean getIsRemit() {
		return isRemit;
	}

	public void setIsRemit(Boolean isRemit) {
		this.isRemit = isRemit;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getAgreeTime() {
		return agreeTime;
	}

	public void setAgreeTime(Date agreeTime) {
		this.agreeTime = agreeTime;
	}

	public Date getRemitTime() {
		return remitTime;
	}

	public void setRemitTime(Date remitTime) {
		this.remitTime = remitTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "TdDeposit [id=" + id + ", username=" + username + ", userId=" + userId + ", number=" + number
				+ ", money=" + money + ", isAgree=" + isAgree + ", isRemit=" + isRemit + ", createTime=" + createTime
				+ ", agreeTime=" + agreeTime + ", remitTime=" + remitTime + ", reason=" + reason + ", remark=" + remark
				+ "]";
	}
}
