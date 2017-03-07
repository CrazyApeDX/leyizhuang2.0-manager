package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 订单商品
 *
 * 记录了订单商品的相关信息
 * 
 * @author Sharon
 *
 */

@Entity
public class TdOrderGoods {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 商品ID
	@Column
	private Long goodsId;

	// 商品名称
	@Column
	private String goodsTitle;

	// 商品简介
	@Column
	private String goodsSubTitle;

	// 商品封面
	@Column
	private String goodsCoverImageUri;

	// 商品的SKU
	@Column
	private String sku;

	// 商品版本颜色
	@Column
	private String goodsColor;

	// 商品版本容量
	@Column
	private String goodsCapacity;

	// 商品版本名称
	@Column
	private String goodsVersion;

	// 商品销售方式
	@Column
	private Integer goodsSaleType;

	// 成交价
	@Column(scale = 2)
	private Double price;

	// 真实价格
	@Column
	private Double realPrice;

	// 商品数量
	@Column
	private Long quantity;

	// 发货数量
	@Column
	private Long deliveredQuantity;

	// 积分
	@Column
	private Long points;

	// 是否申请了退还该商品？
	@Column
	private Boolean isReturnApplied;

	// 是否已评价
	@Column
	private Boolean isCommented;

	// 评论ID
	@Column
	private Long commentId;

	// 商品品牌标题
	@Column
	private String brandTitle;

	// 商品的品牌id
	@Column
	private Long brandId;

	// 可退数量
	@Column
	private Long canReturnNumber;

	// 退款单价
	@Column
	private Double returnUnitPrice;

	// 使用产品券的数量，默认为0
	@Column
	private Long couponNumber;

	// 使用指定产品现金券的数量，默认为0
	@Column
	private Long cashNumber;

	// 退货数量
	@Column
	private Long returnNumber;

	// 促销赠品价格
	@Column
	private Double giftPrice;

	// 退货单号
	@Column
	private String returnNoteNumber;

	// 分单号
	@Column
	private String subOrderNumber;

	// 表示已选类型（目前：0. 代表商品；1. 代表产品券）
	@Column
	private Long type;

	// 是否是券
	@Column
	private Boolean isCoupon;

	// 调色包归属商品SKU zp
	@Column
	private String ownerGoodsSku;
	// 活动id(可以为多个) zp
	@Column
	private String activityId;

	// 券金额
	@Column
	private Double couponMoney;

	// 该商品运费单价
	@Column(scale = 2)
	private Double deliveryUnit;

	// 满减分摊金额
	@Column(scale = 2)
	private Double shareUnit;

	// 是否墙面辅料
	@Column(nullable = false)
	private Boolean isWallAccessory = false;

	public String getSubOrderNumber() {
		return subOrderNumber;
	}

	public void setSubOrderNumber(String subOrderNumber) {
		this.subOrderNumber = subOrderNumber;
	}

	public String getReturnNoteNumber() {
		return returnNoteNumber;
	}

	public void setReturnNoteNumber(String returnNoteNumber) {
		this.returnNoteNumber = returnNoteNumber;
	}

	public Boolean getIsCommented() {
		return isCommented;
	}

	public void setIsCommented(Boolean isCommented) {
		this.isCommented = isCommented;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getGoodsSubTitle() {
		return goodsSubTitle;
	}

	public void setGoodsSubTitle(String goodsSubTitle) {
		this.goodsSubTitle = goodsSubTitle;
	}

	public String getGoodsCoverImageUri() {
		return goodsCoverImageUri;
	}

	public void setGoodsCoverImageUri(String goodsCoverImageUri) {
		this.goodsCoverImageUri = goodsCoverImageUri;
	}

	public String getGoodsColor() {
		return goodsColor;
	}

	public void setGoodsColor(String goodsColor) {
		this.goodsColor = goodsColor;
	}

	public String getGoodsCapacity() {
		return goodsCapacity;
	}

	public void setGoodsCapacity(String goodsCapacity) {
		this.goodsCapacity = goodsCapacity;
	}

	public String getGoodsVersion() {
		return goodsVersion;
	}

	public void setGoodsVersion(String goodsVersion) {
		this.goodsVersion = goodsVersion;
	}

	public Integer getGoodsSaleType() {
		return goodsSaleType;
	}

	public void setGoodsSaleType(Integer goodsSaleType) {
		this.goodsSaleType = goodsSaleType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(Long deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	public Boolean getIsReturnApplied() {
		return isReturnApplied;
	}

	public void setIsReturnApplied(Boolean isReturnApplied) {
		this.isReturnApplied = isReturnApplied;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public Double getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}

	public Long getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(Long couponNumber) {
		this.couponNumber = couponNumber;
	}

	public Long getCanReturnNumber() {
		return canReturnNumber;
	}

	public void setCanReturnNumber(Long canReturnNumber) {
		this.canReturnNumber = canReturnNumber;
	}

	public Double getReturnUnitPrice() {
		return returnUnitPrice;
	}

	public void setReturnUnitPrice(Double returnUnitPrice) {
		this.returnUnitPrice = returnUnitPrice;
	}

	public Long getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(Long returnNumber) {
		this.returnNumber = returnNumber;
	}

	public Double getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(Double giftPrice) {
		this.giftPrice = giftPrice;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Boolean getIsCoupon() {
		return isCoupon;
	}

	public void setIsCoupon(Boolean isCoupon) {
		this.isCoupon = isCoupon;
	}

	public Long getCashNumber() {
		return cashNumber;
	}

	public void setCashNumber(Long cashNumber) {
		this.cashNumber = cashNumber;
	}

	public String getOwnerGoodsSku() {
		return ownerGoodsSku;
	}

	public void setOwnerGoodsSku(String ownerGoodsSku) {
		this.ownerGoodsSku = ownerGoodsSku;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Double getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(Double couponMoney) {
		this.couponMoney = couponMoney;
	}

	public Double getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(Double deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	public Double getShareUnit() {
		return shareUnit;
	}

	public void setShareUnit(Double shareUnit) {
		this.shareUnit = shareUnit;
	}

	public Boolean getIsWallAccessory() {
		return isWallAccessory;
	}

	public void setIsWallAccessory(Boolean isWallAccessory) {
		this.isWallAccessory = isWallAccessory;
	}

}
