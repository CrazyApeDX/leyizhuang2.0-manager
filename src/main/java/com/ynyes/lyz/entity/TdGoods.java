package com.ynyes.lyz.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 商品信息表
 * 
 * @author Sharon
 *
 */

@Entity
public class TdGoods {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 产品Id
	@Column
	private Long productId;

	// 是否为小辅料
	@Column
	private Boolean isGift;

	// 商品名称
	@Column
	private String name;

	// 商品标题
	@Column
	private String title;

	// 副标题
	@Column
	private String subTitle;

	// 封面图片
	@Column
	private String coverImageUri;

	// 封面图片宽度
	@Column
	private Double coverImageWidth;

	// 封面图片高度
	@Column
	private Double coverImageHeight;

	// 封面图片阿里云地址
	@Column
	private String coverImageUrl;

	// 封面图片阿里云路径
	@Column
	private String coverImagePath;

	// 视频
	@Column
	private String videoUri;

	// 轮播展示图片，多张图片以,隔开
	@Column
	private String showPictures;

	// 促销
	@Column
	private String promotion;

	// 评价平均分
	@Column
	private Double averagePoints;

	// 商品配置
	@Column
	private String configuration;

	// 商品服务
	@Column
	private String service;

	// 配送区域
	@Column
	private String deliveryArea;

	// 商品详情
	@Column
	private String detail;

	// 商品售后服务
	@Column
	private String afterMarketService;

	// 是否上架
	@Column
	private Boolean isOnSale;

	// 是否首页推荐
	@Column
	private Boolean isRecommendIndex;

	// 是否分类推荐
	@Column
	private Boolean isRecommendType;

	// 是否热销
	@Column
	private Boolean isHot;

	// 是否新品
	@Column
	private Boolean isNew;

	// 是否特价
	@Column
	private Boolean isSpecialPrice;

	// 库存递减时机 分为下订单递减、支付完成递减、发货递减等
	@Column
	private Long numberDecType;

	// 商品类型
	@Column
	private Long categoryId;

	// 商品类型名称
	@Column
	private String categoryTitle;

	// 商品所有类型
	@Column
	private String categoryIdTree;

	// 商品参数
	@OneToMany
	@JoinColumn(name = "goodsId")
	private List<TdGoodsParameter> paramList;

	// 参数值，用于搜索
	@Column
	private String paramValueCollect;

	// 筛选参数一值
	@Column
	private String selectOneValue;

	// 筛选参数二值
	@Column
	private String selectTwoValue;

	// 筛选参数三值
	@Column
	private String selectThreeValue;

	// // 筛选参数四值
	// @Column
	// private String selectFourValue;

	// 销售方式
	@Column
	private String saleType;

	// 成本价
	@Column(scale = 2)
	private Double costPrice;

	// 市场价
	@Column(nullable = false, scale = 2)
	private Double marketPrice;

	// 销售价
	@Column(nullable = false, scale = 2)
	private Double salePrice;

	// 出厂价
	@Column(nullable = false, scale = 2)
	private Double outFactoryPrice;

	// 包含价值，销售靓号时会包含话费
	@Column(scale = 2)
	private Double includePrice;

	// 组合销售时的价格
	@Column(scale = 2)
	private Double combPrice;

	// 仓库ID
	@Column
	private Long warehouseId;

	// 仓库名
	@Column
	private String warehouseTitle;

	// 库存数量
	@Column
	private Long leftNumber;

	// 库存信息
	@Column
	private Long storehouseId;

	// 该版本的已售数量
	@Column
	private Long soldNumber;

	// 商品价格单位
	@Column
	private String priceUnit;

	// 上架时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date onSaleTime;

	// 创建日期
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	// 商品标签
	@Column
	private String tags;

	// 赠品
	@OneToMany
	@JoinColumn(name = "ownerGoodsId")
	private List<TdGoodsGift> giftList;

	// 赠品数量
	@Column
	private Integer totalGift;

	// 商品组合
	@OneToMany
	@JoinColumn(name = "ownerGoodsId")
	private List<TdGoodsCombination> combList;

	// 商品库存，关联门店
	@OneToMany
	@JoinColumn(name = "ownerGoodsId")
	private List<TdDiySiteInventory> inventoryList;

	// 组合数量
	private Integer totalComb;

	// 排序号
	@Column
	private Double sortId;

	// 是否支持限时抢购
	@Column
	private Boolean isFlashSale;

	// 抢拍展示图
	@Column
	private String flashSaleImage;

	// 限时抢购开始时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date flashSaleStartTime;

	// 限时抢购结束时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date flashSaleStopTime;

	// 限时抢购价格
	@Column(scale = 2)
	private Double flashSalePrice;

	// 限时抢购成交价格
	@Column(scale = 2)
	private Double flashSaleTransactionPrice;

	// 限时抢购剩余数量
	@Column
	private Long flashSaleLeftNumber;

	// 限时抢购已售数量
	@Column
	private Long flashSaleSoldNumber;

	// 是否支持团购
	@Column
	private Boolean isGroupSale;

	// 团购开始时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date groupSaleStartTime;

	// 团购结束时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date groupSaleStopTime;

	// 团购价格
	@Column(scale = 2)
	private Double groupSalePrice;

	// 团购展示图
	@Column
	private String groupSaleImage;

	// 团购剩余数量
	@Column
	private Long groupSaleLeftNumber;

	// 团购已售数量
	@Column
	private Long groupSaleSoldNumber;

	// 品牌
	@Column
	private String brandTitle;

	// 品牌ID
	@Column
	private Long brandId;

	// 品牌Flag
	@Column
	private String productFlag;

	// 商品返现金额
	@Column(scale = 2)
	private Double returnPrice;

	// 赠送积分
	@Column
	private Long returnPoints;

	// 购买积分限额
	@Column
	private Long pointLimited;

	// SEO标题
	@Column
	private String seoTitle;

	// SEO关键字
	@Column
	private String seoKeywords;

	// SEO描述
	@Column
	private String seoDescription;

	// 可购买会员等级限制
	@Column
	private Long userLevelLimit;

	// 评论数
	@Column
	private Long totalComments;

	// 关注数
	@Column
	private Long totalCollects;

	// 商品编码（sku）
	@Column(unique = true)
	private String code;

	// 是否是调色包
	@Column
	private Boolean isColorPackage;

	// 是否调色产品
	@Column
	private Boolean isColorful;

	// 可调色的调色包SKU编号（多个调色包以","分割）
	@Column
	private String colorPackageSku;

	// 归属：0 未知 1 华润商品 2 乐意装商品 3银润
	@Column
	private Long belongTo;

	// 接口添加数据------------》

	// 物料id（唯一标识）
	@Column
	private Long inventoryItemId;

	// 库存分类ID
	@Column
	private Long invCategoryId;

	// 品牌分类ID
	@Column
	private Long bradCategoryId;

	// 物理分类ID
	@Column
	private Long productCategoryId;

	// 物料类型名称
	@Column
	private String itemTypeName;

	// 物料类型CODE
	@Column
	private String itemTypeCode;

	// 物料状态 0 失效，1 有效
	@Column
	private Long inventoryItemStatus;

	// 产品条码
	@Column
	private String itemBarcode;

	// 团购价格
	@Column(scale = 2)
	private Double attribute1;

	// 单位名称
	@Column
	private String unitName;

	// 是否是券
	@Column
	private Boolean isCoupon;
	
	@Column(nullable=false)
	private Boolean isWallAccessory;
	
	

	public List<TdDiySiteInventory> getInventoryList() {
		return inventoryList;
	}

	public void setInventoryList(List<TdDiySiteInventory> inventoryList) {
		this.inventoryList = inventoryList;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getInventoryItemId() {
		return inventoryItemId;
	}

	public void setInventoryItemId(Long inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}

	public Double getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(Double attribute1) {
		this.attribute1 = attribute1;
	}

	public Long getInvCategoryId() {
		return invCategoryId;
	}

	public void setInvCategoryId(Long invCategoryId) {
		this.invCategoryId = invCategoryId;
	}

	public Long getBradCategoryId() {
		return bradCategoryId;
	}

	public void setBradCategoryId(Long bradCategoryId) {
		this.bradCategoryId = bradCategoryId;
	}

	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public Long getInventoryItemStatus() {
		return inventoryItemStatus;
	}

	public void setInventoryItemStatus(Long inventoryItemStatus) {
		this.inventoryItemStatus = inventoryItemStatus;
	}

	public String getItemBarcode() {
		return itemBarcode;
	}

	public void setItemBarcode(String itemBarcode) {
		this.itemBarcode = itemBarcode;
	}

	public Boolean getIsGift() {
		return isGift;
	}

	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getCoverImageUri() {
		return coverImageUri;
	}

	public void setCoverImageUri(String coverImageUri) {
		this.coverImageUri = coverImageUri;
	}

	public Double getCoverImageWidth() {
		return coverImageWidth;
	}

	public void setCoverImageWidth(Double coverImageWidth) {
		this.coverImageWidth = coverImageWidth;
	}

	public Double getCoverImageHeight() {
		return coverImageHeight;
	}

	public void setCoverImageHeight(Double coverImageHeight) {
		this.coverImageHeight = coverImageHeight;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getCoverImagePath() {
		return coverImagePath;
	}

	public void setCoverImagePath(String coverImagePath) {
		this.coverImagePath = coverImagePath;
	}

	public String getVideoUri() {
		return videoUri;
	}

	public void setVideoUri(String videoUri) {
		this.videoUri = videoUri;
	}

	public String getShowPictures() {
		return showPictures;
	}

	public void setShowPictures(String showPictures) {
		this.showPictures = showPictures;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public Double getAveragePoints() {
		return averagePoints;
	}

	public void setAveragePoints(Double averagePoints) {
		this.averagePoints = averagePoints;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getDeliveryArea() {
		return deliveryArea;
	}

	public void setDeliveryArea(String deliveryArea) {
		this.deliveryArea = deliveryArea;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAfterMarketService() {
		return afterMarketService;
	}

	public void setAfterMarketService(String afterMarketService) {
		this.afterMarketService = afterMarketService;
	}

	public Boolean getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(Boolean isOnSale) {
		this.isOnSale = isOnSale;
	}

	public Boolean getIsRecommendIndex() {
		return isRecommendIndex;
	}

	public void setIsRecommendIndex(Boolean isRecommendIndex) {
		this.isRecommendIndex = isRecommendIndex;
	}

	public Boolean getIsRecommendType() {
		return isRecommendType;
	}

	public void setIsRecommendType(Boolean isRecommendType) {
		this.isRecommendType = isRecommendType;
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public Boolean getIsSpecialPrice() {
		return isSpecialPrice;
	}

	public void setIsSpecialPrice(Boolean isSpecialPrice) {
		this.isSpecialPrice = isSpecialPrice;
	}

	public Long getNumberDecType() {
		return numberDecType;
	}

	public void setNumberDecType(Long numberDecType) {
		this.numberDecType = numberDecType;
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

	public List<TdGoodsParameter> getParamList() {
		return paramList;
	}

	public void setParamList(List<TdGoodsParameter> paramList) {
		this.paramList = paramList;
	}

	public String getParamValueCollect() {
		return paramValueCollect;
	}

	public void setParamValueCollect(String paramValueCollect) {
		this.paramValueCollect = paramValueCollect;
	}

	public String getSelectOneValue() {
		return selectOneValue;
	}

	public void setSelectOneValue(String selectOneValue) {
		this.selectOneValue = selectOneValue;
	}

	public String getSelectTwoValue() {
		return selectTwoValue;
	}

	public void setSelectTwoValue(String selectTwoValue) {
		this.selectTwoValue = selectTwoValue;
	}

	public String getSelectThreeValue() {
		return selectThreeValue;
	}

	public void setSelectThreeValue(String selectThreeValue) {
		this.selectThreeValue = selectThreeValue;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getOutFactoryPrice() {
		return outFactoryPrice;
	}

	public void setOutFactoryPrice(Double outFactoryPrice) {
		this.outFactoryPrice = outFactoryPrice;
	}

	public Double getIncludePrice() {
		return includePrice;
	}

	public void setIncludePrice(Double includePrice) {
		this.includePrice = includePrice;
	}

	public Double getCombPrice() {
		return combPrice;
	}

	public void setCombPrice(Double combPrice) {
		this.combPrice = combPrice;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseTitle() {
		return warehouseTitle;
	}

	public void setWarehouseTitle(String warehouseTitle) {
		this.warehouseTitle = warehouseTitle;
	}

	public Long getLeftNumber() {
		return leftNumber;
	}

	public void setLeftNumber(Long leftNumber) {
		this.leftNumber = leftNumber;
	}

	public Long getStorehouseId() {
		return storehouseId;
	}

	public void setStorehouseId(Long storehouseId) {
		this.storehouseId = storehouseId;
	}

	public Long getSoldNumber() {
		return soldNumber;
	}

	public void setSoldNumber(Long soldNumber) {
		this.soldNumber = soldNumber;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public Date getOnSaleTime() {
		return onSaleTime;
	}

	public void setOnSaleTime(Date onSaleTime) {
		this.onSaleTime = onSaleTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<TdGoodsGift> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<TdGoodsGift> giftList) {
		this.giftList = giftList;
	}

	public Integer getTotalGift() {
		return totalGift;
	}

	public void setTotalGift(Integer totalGift) {
		this.totalGift = totalGift;
	}

	public List<TdGoodsCombination> getCombList() {
		return combList;
	}

	public void setCombList(List<TdGoodsCombination> combList) {
		this.combList = combList;
	}

	public Integer getTotalComb() {
		return totalComb;
	}

	public void setTotalComb(Integer totalComb) {
		this.totalComb = totalComb;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Boolean getIsFlashSale() {
		return isFlashSale;
	}

	public void setIsFlashSale(Boolean isFlashSale) {
		this.isFlashSale = isFlashSale;
	}

	public String getFlashSaleImage() {
		return flashSaleImage;
	}

	public void setFlashSaleImage(String flashSaleImage) {
		this.flashSaleImage = flashSaleImage;
	}

	public Date getFlashSaleStartTime() {
		return flashSaleStartTime;
	}

	public void setFlashSaleStartTime(Date flashSaleStartTime) {
		this.flashSaleStartTime = flashSaleStartTime;
	}

	public Date getFlashSaleStopTime() {
		return flashSaleStopTime;
	}

	public void setFlashSaleStopTime(Date flashSaleStopTime) {
		this.flashSaleStopTime = flashSaleStopTime;
	}

	public Double getFlashSalePrice() {
		return flashSalePrice;
	}

	public void setFlashSalePrice(Double flashSalePrice) {
		this.flashSalePrice = flashSalePrice;
	}

	public Double getFlashSaleTransactionPrice() {
		return flashSaleTransactionPrice;
	}

	public void setFlashSaleTransactionPrice(Double flashSaleTransactionPrice) {
		this.flashSaleTransactionPrice = flashSaleTransactionPrice;
	}

	public Long getFlashSaleLeftNumber() {
		return flashSaleLeftNumber;
	}

	public void setFlashSaleLeftNumber(Long flashSaleLeftNumber) {
		this.flashSaleLeftNumber = flashSaleLeftNumber;
	}

	public Long getFlashSaleSoldNumber() {
		return flashSaleSoldNumber;
	}

	public void setFlashSaleSoldNumber(Long flashSaleSoldNumber) {
		this.flashSaleSoldNumber = flashSaleSoldNumber;
	}

	public Boolean getIsGroupSale() {
		return isGroupSale;
	}

	public void setIsGroupSale(Boolean isGroupSale) {
		this.isGroupSale = isGroupSale;
	}

	public Date getGroupSaleStartTime() {
		return groupSaleStartTime;
	}

	public void setGroupSaleStartTime(Date groupSaleStartTime) {
		this.groupSaleStartTime = groupSaleStartTime;
	}

	public Date getGroupSaleStopTime() {
		return groupSaleStopTime;
	}

	public void setGroupSaleStopTime(Date groupSaleStopTime) {
		this.groupSaleStopTime = groupSaleStopTime;
	}

	public Double getGroupSalePrice() {
		return groupSalePrice;
	}

	public void setGroupSalePrice(Double groupSalePrice) {
		this.groupSalePrice = groupSalePrice;
	}

	public String getGroupSaleImage() {
		return groupSaleImage;
	}

	public void setGroupSaleImage(String groupSaleImage) {
		this.groupSaleImage = groupSaleImage;
	}

	public Long getGroupSaleLeftNumber() {
		return groupSaleLeftNumber;
	}

	public void setGroupSaleLeftNumber(Long groupSaleLeftNumber) {
		this.groupSaleLeftNumber = groupSaleLeftNumber;
	}

	public Long getGroupSaleSoldNumber() {
		return groupSaleSoldNumber;
	}

	public void setGroupSaleSoldNumber(Long groupSaleSoldNumber) {
		this.groupSaleSoldNumber = groupSaleSoldNumber;
	}

	public String getBrandTitle() {
		return brandTitle;
	}

	public void setBrandTitle(String brandTitle) {
		this.brandTitle = brandTitle;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Double getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(Double returnPrice) {
		this.returnPrice = returnPrice;
	}

	public Long getReturnPoints() {
		return returnPoints;
	}

	public void setReturnPoints(Long returnPoints) {
		this.returnPoints = returnPoints;
	}

	public Long getPointLimited() {
		return pointLimited;
	}

	public void setPointLimited(Long pointLimited) {
		this.pointLimited = pointLimited;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public Long getUserLevelLimit() {
		return userLevelLimit;
	}

	public void setUserLevelLimit(Long userLevelLimit) {
		this.userLevelLimit = userLevelLimit;
	}

	public Long getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(Long totalComments) {
		this.totalComments = totalComments;
	}

	public Long getTotalCollects() {
		return totalCollects;
	}

	public void setTotalCollects(Long totalCollects) {
		this.totalCollects = totalCollects;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsColorPackage() {
		return isColorPackage;
	}

	public void setIsColorPackage(Boolean isColorPackage) {
		this.isColorPackage = isColorPackage;
	}

	public Boolean getIsColorful() {
		return isColorful;
	}

	public void setIsColorful(Boolean isColorful) {
		this.isColorful = isColorful;
	}

	public String getColorPackageSku() {
		return colorPackageSku;
	}

	public void setColorPackageSku(String colorPackageSku) {
		this.colorPackageSku = colorPackageSku;
	}

	public Long getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(Long belongTo) {
		this.belongTo = belongTo;
	}

	public String getProductFlag() {
		return productFlag;
	}

	public void setProductFlag(String productFlag) {
		this.productFlag = productFlag;
	}

	public Boolean getIsCoupon() {
		return isCoupon;
	}

	public void setIsCoupon(Boolean isCoupon) {
		this.isCoupon = isCoupon;
	}

	public Boolean getIsWallAccessory() {
		return isWallAccessory;
	}

	public void setIsWallAccessory(Boolean isWallAccessory) {
		this.isWallAccessory = isWallAccessory;
	}
	
	
}
