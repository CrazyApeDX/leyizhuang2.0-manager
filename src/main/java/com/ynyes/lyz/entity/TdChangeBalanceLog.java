package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>标题：TdChangeBalanceLog.java</p>
 * <p>描述：管理员修改用户余额日志记录</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年8月29日上午11:56:08
 */
@Entity
public class TdChangeBalanceLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 被操作的用户名
	@Column
	private String username;

	// 被操作的用户的真实姓名
	@Column
	private String realName;
	
	// 被操作用户的门店名
	@Column
	private String diySiteTitle;
	
	// 操作管理员的用户名
	@Column
	private String manager;
	
	// 操作时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date operateTime;
	
	// 是否成功
	@Column
	private Boolean isSuccess;
	
	// 操作时IP地址
	@Column
	private String operateIp;
	
	// 备注
	@Column(length = 255)
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDiySiteTitle() {
		return diySiteTitle;
	}

	public void setDiySiteTitle(String diySiteTitle) {
		this.diySiteTitle = diySiteTitle;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getOperateIp() {
		return operateIp;
	}

	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
