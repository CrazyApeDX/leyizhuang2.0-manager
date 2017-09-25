package com.ynyes.lyz.interfaces.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TdEbsInfLog extends TdInfBaseEntity
{
	public static final Integer SEND_SUCCEED = 0; //发送成功
	public static final Integer SEND_FAILURE = 1; //发送失败
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//错误信息
	@Column
	private String errorInfo;
	
	//ebs接口表名
	@Column(length = 20)
	private String tableName;
	
	//单号
	@Column
	private String orderNumber;
	
	//所传数据的唯一标识
	@Column
	private String sendId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getErrorInfo()
	{
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo)
	{
		this.errorInfo = errorInfo;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getOrderNumber()
	{
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	@Override
	public String toString() {
		return "TdEbsInfLog [id=" + id + ", errorInfo=" + errorInfo + ", tableName=" + tableName + ", orderNumber="
				+ orderNumber + ", sendId=" + sendId + "]";
	} 
	
}
