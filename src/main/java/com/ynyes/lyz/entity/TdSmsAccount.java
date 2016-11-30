package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 短信账户实体类
 * 
 * @author dengxiao
 */
@Entity
public class TdSmsAccount {

	// 短信账户Id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 账户名称
	@Column
	private String accountTitle;

	// 短信账户账号
	@Column
	private String encode;

	// 短信账户密码
	@Column
	private String enpass;

	// 企业下用户名
	@Column
	private String userName;

	// 排序号
	@Column
	private Double sortId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getEnpass() {
		return enpass;
	}

	public void setEnpass(String enpass) {
		this.enpass = enpass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

}
