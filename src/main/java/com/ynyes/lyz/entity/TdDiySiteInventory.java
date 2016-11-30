package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 门店库存实体类
 * 
 * @author dengxiao
 */

@Entity
public class TdDiySiteInventory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 城市编码 由ebs给的
	@Column
	private Long regionId;
	
	// 城市名
	@Column
	private String regionName;
	
	// 门店id
	@Column
	private Long diySiteId;

	// 门店名称
	@Column
	private String diySiteName;
	
	// 门店编号
	@Column
	private String diyCode;

	// 商品id
	@Column
	private Long goodsId;

	// 商品名称
	@Column
	private String goodsTitle;

	// 商品SKU
	@Column
	private String goodsCode;

	// 库存量(可售)
	@Column
	private Long inventory;

	// 商品类型
	@Column
	private Long categoryId;

	// 商品类型名称
	@Column
	private String categoryTitle;

	// 商品所有类型
	@Column
	private String categoryIdTree;
	
	// 管理员id
	@Column
	private Long managerId;
	
	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getCategoryIdTree() {
		return categoryIdTree;
	}

	public void setCategoryIdTree(String categoryIdTree) {
		this.categoryIdTree = categoryIdTree;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiySiteId() {
		return diySiteId;
	}

	public void setDiySiteId(Long diySiteId) {
		this.diySiteId = diySiteId;
	}

	public String getDiySiteName() {
		return diySiteName;
	}

	public void setDiySiteName(String diySiteName) {
		this.diySiteName = diySiteName;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public Long getInventory() {
		return inventory==null?0L:inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	public String getDiyCode() {
		return diyCode;
	}

	public void setDiyCode(String diyCode) {
		this.diyCode = diyCode;
	}

	@Override
	public String toString()
	{
		return "TdDiySiteInventory [id=" + id + ", regionId=" + regionId + ", regionName=" + regionName + ", diySiteId="
		        + diySiteId + ", diySiteName=" + diySiteName + ", diyCode=" + diyCode + ", goodsId=" + goodsId
		        + ", goodsTitle=" + goodsTitle + ", goodsCode=" + goodsCode + ", inventory=" + inventory
		        + ", categoryId=" + categoryId + ", categoryTitle=" + categoryTitle + ", categoryIdTree="
		        + categoryIdTree + ", managerId=" + managerId + "]";
	}
	
}
