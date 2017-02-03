package com.ynyes.fitment.foundation.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

/**
 * 装饰公司员工实体模型，分为主账号二级账号
 * 二级账号只能选择商品，生成订单
 * 主账号审核二级账号的订单使得订单生效
 * @author dengxiao
 */
@Entity
@Table(name = "FIT_EMPLOYEE")
public class FitEmployee extends TableEntity {

	// 装饰公司id
	@Column(nullable = false)
	private Long companyId;
	
	// 装饰公司名
	@Column(length = 15, nullable = false)
	private String companyTitle;

	// 员工账号，不可编辑，即系统自生成的UUID
	@Column(length = 36, nullable = false, updatable = false, unique = true)
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
	
	// 城市名
	@Column(length = 10, nullable = false)
	private String cityTitle;

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

	// 是否是主账号，实际上与mainId字段意义重复
	@Column(nullable = false)
	private Boolean isMain = true;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyTitle() {
		return companyTitle;
	}

	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}

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
		this.password = password;
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
	
	public String getCityTitle() {
		return cityTitle;
	}

	public void setCityTitle(String cityTitle) {
		this.cityTitle = cityTitle;
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

	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}
}
