package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 门店库存核减记录表
 * 
 * @author DengXiao
 */

@Entity
public class TdDiySiteInventoryLog {
	
	public static final String CHANGETYPE_DIYSITE_ADD			= "门店要货";
	public static final String CHANGETYPE_DIYSITE_SUB			= "门店退货";
	public static final String CHANGETYPE_USER_SUB				= "自提单发货";
	public static final String CHANGETYPE_USER_ADD				= "自提单退货";
	public static final String CHANGETYPE_DELIVERY_SUB			= "配送出货";
	public static final String CHANGETYPE_DELIVERY_ADD			= "配送退货";
	public static final String CHANGETYPE_INVENTORY_OVERAGE		= "库存盘盈";
	public static final String CHANGETYPE_INVENTORY_LOSE		= "库存盘亏";
	public static final String CHANGETYPE_CITY_ADD				= "城市入货";
	public static final String CHANGETYPE_CITY_SUB				= "城市退货";
	public static final String CHANGETYPE_CITY_DO_ADD			= "城市调拨入库";
	public static final String CHANGETYPE_CITY_DO_SUB			= "城市调拨出库";
	public static final String CHANGETYPE_CITY_YC_ADD			= "城市报溢";
	public static final String CHANGETYPE_CITY_BS_SUB			= "城市报损";
	public static final String CHANGETYPE_CITY_CG_SUB			= "城市采购退货";
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 门店名称
	@Column
	private String diySiteTitle;
	
	// 城市名
	@Column
	private String regionName;
	
	// 城市编码 由ebs给的
	@Column
	private Long regionId;
	// 门店id
	@Column
	private Long diySiteId;

	// 商品id
	@Column
	private Long goodsId;

	// 商品名称
	@Column
	private String goodsTitle;

	// 商品SKU
	@Column
	private String goodsSku;
	
	// 变更数量
	@Column
	private Long changeValue;

	// 变更时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date changeDate;

	// 描述
	@Column(length = 255)
	private String description;
	
	@Column
	private String orderNumber;
	
	@Column
	private String manager;
	
	@Column
	private Long afterChange;
	
	@Column(length = 50)
	private String changeType;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDiySiteTitle()
	{
		return diySiteTitle;
	}

	public void setDiySiteTitle(String diySiteTitle)
	{
		this.diySiteTitle = diySiteTitle;
	}

	public String getRegionName()
	{
		return regionName;
	}

	public void setRegionName(String regionName)
	{
		this.regionName = regionName;
	}

	public Long getRegionId()
	{
		return regionId;
	}

	public void setRegionId(Long regionId)
	{
		this.regionId = regionId;
	}

	public Long getDiySiteId()
	{
		return diySiteId;
	}

	public void setDiySiteId(Long diySiteId)
	{
		this.diySiteId = diySiteId;
	}

	public Long getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(Long goodsId)
	{
		this.goodsId = goodsId;
	}

	public String getGoodsTitle()
	{
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle)
	{
		this.goodsTitle = goodsTitle;
	}

	public String getGoodsSku()
	{
		return goodsSku;
	}

	public void setGoodsSku(String goodsSku)
	{
		this.goodsSku = goodsSku;
	}

	public Long getChangeValue()
	{
		return changeValue;
	}

	public void setChangeValue(Long changeValue)
	{
		this.changeValue = changeValue;
	}

	public Date getChangeDate()
	{
		return changeDate;
	}

	public void setChangeDate(Date changeDate)
	{
		this.changeDate = changeDate;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getOrderNumber()
	{
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public String getManager()
	{
		return manager;
	}

	public void setManager(String manager)
	{
		this.manager = manager;
	}

	public Long getAfterChange()
	{
		return afterChange;
	}

	public void setAfterChange(Long afterChange)
	{
		this.afterChange = afterChange;
	}

	public String getChangeType()
	{
		return changeType;
	}

	public void setChangeType(String changeType)
	{
		this.changeType = changeType;
	}

	@Override
	public String toString()
	{
		return "TdDiySiteInventoryLog [id=" + id + ", diySiteTitle=" + diySiteTitle + ", regionName=" + regionName
		        + ", regionId=" + regionId + ", diySiteId=" + diySiteId + ", goodsId=" + goodsId + ", goodsTitle="
		        + goodsTitle + ", goodsSku=" + goodsSku + ", changeValue=" + changeValue + ", changeDate=" + changeDate
		        + ", description=" + description + ", orderNumber=" + orderNumber + ", manager=" + manager
		        + ", afterChange=" + afterChange + ", changeType=" + changeType + "]";
	}
}
