package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 短信息实体类
 * 
 * @author dengxiao
 *
 */
@Entity
public class TdSms {

	// 短信息id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 所属账户名称
	@Column
	private String accountTitle;

	// 所属账户Id
	@Column
	private Long accountId;

	// 门店Id
	@Column
	private Long diyId;

	// 门店名称
	@Column
	private String diyTitle;

	// 发送人名称
	@Column
	private String sendName;

	// 发送时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendDate;

	// 接收人手机号
	@Column
	private String acceptTel;
	
	//内容
	@Column
	private String context;
	
	//短信类型（01代表积分，02代表派工，03代表服务）
	@Column
	private Long type;
	
	//短信状态（01-未发送；02-发送成功）
	@Column
	private Double statusId;

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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getDiyId() {
		return diyId;
	}

	public void setDiyId(Long diyId) {
		this.diyId = diyId;
	}

	public String getDiyTitle() {
		return diyTitle;
	}

	public void setDiyTitle(String diyTitle) {
		this.diyTitle = diyTitle;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getAcceptTel() {
		return acceptTel;
	}

	public void setAcceptTel(String acceptTel) {
		this.acceptTel = acceptTel;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Double getStatusId() {
		return statusId;
	}

	public void setStatusId(Double statusId) {
		this.statusId = statusId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
	
}

