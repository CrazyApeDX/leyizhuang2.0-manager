package com.ynyes.fitment.foundation.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;
import com.ynyes.lyz.util.MD5;

@Entity
@Table(name = "FIT_EMPLOYEE")
public class FitEmployee extends TableEntity {

	private static final long serialVersionUID = 1L;

	// 员工账号，不可编辑，即系统自生成的UUID
	@Column(length = 32, nullable = false, updatable = false, unique = true)
	private String account = UUID.randomUUID().toString();

	// 员工密码
	@Column(length = 32, nullable = false)
	private String password;

	// 员工手机号码
	@Column(length = 11, nullable = false, unique = true)
	private String mobile;

	// 员工姓名
	@Column(length = 5, nullable = false)
	private String name = "员工";

	// 账号注册时间
	@Column(nullable = false, updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerTime = new Date();

	// 上一次登录时间
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime = new Date();

	// 是否被冻结，默认未冻结
	@Column(nullable = false)
	private Boolean frozen = false;

	// 冻结结束时间
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date frozenEndTime = new Date();

	// 主账号id，默认情况下都是主账号，主账号ID为0
	@Column(nullable = false)
	private Long mainId = 0l;

	// 是否是主账号，实际上与mainId字段意义重复
	@Column(nullable = false)
	private Boolean isMain = true;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {

		this.password = MD5.md5(password, 32);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Boolean getFrozen() {
		return frozen;
	}

	public void setFrozen(Boolean frozen) {
		this.frozen = frozen;
	}

	public Date getFrozenEndTime() {
		return frozenEndTime;
	}

	public void setFrozenEndTime(Date frozenEndTime) {
		this.frozenEndTime = frozenEndTime;
	}

	public Long getMainId() {
		return mainId;
	}

	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}

	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
