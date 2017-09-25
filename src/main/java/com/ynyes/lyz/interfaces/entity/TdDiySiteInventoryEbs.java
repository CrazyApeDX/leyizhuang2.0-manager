package com.ynyes.lyz.interfaces.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * ebs更改单点库存 实体类
 * @author Administrator
 *
 */
@Entity
public class TdDiySiteInventoryEbs extends TdInfBaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//分公司ID
	@Column
	private Long sobId;
	
	//事务唯一ID
	@Column
	private Long transId;
	
	//事务类型 出货单,退货单,盘点入库,盘点出库
	@Column(length = 50)
	private String transType;
	
	//门店事务编号
	@Column(length = 50)
	private String transNumber;
	
	//门店客户ID
	@Column
	private Long customerId;
	
	//门店客户编号
	@Column(length = 50)
	private String customerNumber;
	
	//门店编号(门店仓库)
	@Column
	private String diySiteCode;
	
	//事务时间 
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date shipDate;
	
	//物料编号,SKU
	@Column(length = 60)
	private String itemCode;
	
	//数量 正数入库，负数出库
	@Column
	private Long quantity;
	
	//
	@Column(length = 2)
	private String ebsToAppFlag;
	
	//
	@Column
	private String appErrorMessage;
	
	//
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	
	//
	@Column
	private Long lastUpdatedBy;
	
	//
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	
	//
	@Column
	private String attribute1;
	
	//
	@Column
	private String attribute2;
	
	//
	@Column
	private String attribute3;
	
	//
	@Column
	private String attribute4;
	
	//
	@Column
	private String attribute5;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getSobId()
	{
		return sobId;
	}

	public void setSobId(Long sobId)
	{
		this.sobId = sobId;
	}

	public Long getTransId()
	{
		return transId;
	}

	public void setTransId(Long transId)
	{
		this.transId = transId;
	}

	public String getTransType()
	{
		return transType;
	}

	public void setTransType(String transType)
	{
		this.transType = transType;
	}

	public String getTransNumber()
	{
		return transNumber;
	}

	public void setTransNumber(String transNumber)
	{
		this.transNumber = transNumber;
	}

	public Long getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(Long customerId)
	{
		this.customerId = customerId;
	}

	public String getCustomerNumber()
	{
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber)
	{
		this.customerNumber = customerNumber;
	}

	public String getDiySiteCode()
	{
		return diySiteCode;
	}

	public void setDiySiteCode(String diySiteCode)
	{
		this.diySiteCode = diySiteCode;
	}

	public Date getShipDate()
	{
		return shipDate;
	}

	public void setShipDate(Date shipDate)
	{
		this.shipDate = shipDate;
	}

	public String getItemCode()
	{
		return itemCode;
	}

	public void setItemCode(String itemCode)
	{
		this.itemCode = itemCode;
	}

	public Long getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Long quantity)
	{
		this.quantity = quantity;
	}

	public String getEbsToAppFlag()
	{
		return ebsToAppFlag;
	}

	public void setEbsToAppFlag(String ebsToAppFlag)
	{
		this.ebsToAppFlag = ebsToAppFlag;
	}

	public String getAppErrorMessage()
	{
		return appErrorMessage;
	}

	public void setAppErrorMessage(String appErrorMessage)
	{
		this.appErrorMessage = appErrorMessage;
	}

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public Long getLastUpdatedBy()
	{
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getAttribute1()
	{
		return attribute1;
	}

	public void setAttribute1(String attribute1)
	{
		this.attribute1 = attribute1;
	}

	public String getAttribute2()
	{
		return attribute2;
	}

	public void setAttribute2(String attribute2)
	{
		this.attribute2 = attribute2;
	}

	public String getAttribute3()
	{
		return attribute3;
	}

	public void setAttribute3(String attribute3)
	{
		this.attribute3 = attribute3;
	}

	public String getAttribute4()
	{
		return attribute4;
	}

	public void setAttribute4(String attribute4)
	{
		this.attribute4 = attribute4;
	}

	public String getAttribute5()
	{
		return attribute5;
	}

	public void setAttribute5(String attribute5)
	{
		this.attribute5 = attribute5;
	}

}
