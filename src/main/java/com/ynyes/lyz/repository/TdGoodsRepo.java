package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.goods.ClientGoodsResult;

/**
 * TdGoods 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdGoodsRepo extends PagingAndSortingRepository<TdGoods, Long>, JpaSpecificationExecutor<TdGoods> {

	// 接口 - 物料id
	TdGoods findByinventoryItemId(Long inventoryItemId);

	// 接口 - 物料类别
	List<TdGoods> findByInvCategoryId(Long invCategoryId);

	List<TdGoods> findByCategoryIdIsNull();

	Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrue(String categoryId, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(String categoryId,
			Pageable page);

	Page<TdGoods> findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(Pageable page);

	Page<TdGoods> findByIsNewTrueAndIsOnSaleTrueOrderByIdDesc(Pageable page);

	Page<TdGoods> findByIsSpecialPriceTrueAndIsOnSaleTrueOrderByIdDesc(Pageable page);

	Page<TdGoods> findByIsHotTrueAndIsOnSaleTrueOrderByIdDesc(Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(String categoryId,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsNewTrueAndIsOnSaleTrueOrderByIdDesc(String categoryId,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsSpecialPriceTrueAndIsOnSaleTrueOrderByIdDesc(String categoryId,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsHotTrueAndIsOnSaleTrueOrderByIdDesc(String categoryId,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySoldNumberDesc(String categoryId, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueOrderByOnSaleTimeDesc(String categoryId, Pageable page);

	Page<TdGoods> findByIsOnSaleTrueOrderByOnSaleTimeDesc(Pageable page);

	List<TdGoods> findTop10ByIsOnSaleTrueOrderBySoldNumberDesc();

	Page<TdGoods> findByIsOnSaleTrue(Pageable page);

	Page<TdGoods> findByIsGiftTrueAndIsOnSaleTrue(Pageable page);

	List<TdGoods> findByIdAndIsOnSaleTrue(Iterable<Long> ids);

	Page<TdGoods> findByCategoryIdTreeContainingOrderBySortIdAsc(String catId, Pageable page);

	Page<TdGoods> findByCategoryIdIsNullOrderBySortIdAsc(Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySortIdAsc(String catId, Pageable page);

	Page<TdGoods> findByTitleContainingOrSubTitleContainingOrDetailContainingOrderBySortIdAsc(String keywords1,
			String keywords2, String keywords3, Pageable page);

	Page<TdGoods> findByTitleContainingOrSubTitleContainingOrDetailContainingOrCodeContainingOrderBySortIdAsc(
			String keywords1, String keywords2, String keywords3, String keywords4, Pageable page);

	Page<TdGoods> findByTitleContainingOrSubTitleContainingOrDetailContainingAndIsOnSaleTrueOrderBySortIdAsc(
			String keywords1, String keywords2, String keywords3, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingOrCategoryIdTreeContainingAndSubTitleContainingOrCategoryIdTreeContainingAndDetailContainingOrCategoryIdTreeContainingAndCodeContainingOrderBySortIdAsc(
			String catId1, String keywords1, String catId2, String keywords2, String catId3, String keywords3,
			String catId4, String keyword4, Pageable page);

	Page<TdGoods> findByCategoryIdIsNullAndTitleContainingOrCategoryIdIsNullAndSubTitleContainingOrCategoryIdIsNullAndDetailContainingOrCategoryIdIsNullAndCodeContainingOrderBySortIdAsc(
			String keywords1, String keywords2, String keywords3, String keyword4, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndDetailContainingAndIsOnSaleTrueOrderBySortIdAsc(
			String catId1, String keywords1, String catId2, String keywords2, String catId3, String keywords3,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
			String categoryId, Long leftNumber, Double priceLow, Double priceHigh, String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
			String categoryId, Long brandId, Long leftNumber, Double priceLow, Double priceHigh, String paramStr,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
			String categoryId, Long leftNumber, String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
			String categoryId, Long brandId, Long leftNumber, String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
			String categoryId, Double priceLow, Double priceHigh, String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
			String categoryId, Long brandId, Double priceLow, Double priceHigh, String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId,
			String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId,
			Long brandId, String paramStr, Pageable page);

	Page<TdGoods> findByIsGroupSaleTrueAndGroupSaleStopTimeAfterAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(
			Date current, Pageable page);

	Page<TdGoods> findByIsGroupSaleTrueAndGroupSaleStopTimeAfterAndGroupSaleStartTimeBeforeAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(
			Date current, Date current1, Pageable page);

	Page<TdGoods> findByIsGroupSaleTrueAndGroupSaleStopTimeAfterAndGroupSaleStartTimeAfterAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(
			Date current, Date current1, Pageable page);

	Page<TdGoods> findByIsGroupSaleTrueAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(Pageable page);

	Page<TdGoods> findByTitleContainingAndIsOnSaleTrueOrSubTitleContainingAndIsOnSaleTrueOrParamValueCollectContainingAndIsOnSaleTrueOrDetailContainingAndIsOnSaleTrue(
			String key1, String key2, String key3, String key4, Pageable page);

	Page<TdGoods> findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(Long categoryId, Pageable page);// zhangji

	List<TdGoods> findByProductIdAndIsOnSaleTrue(Long productId);

	Page<TdGoods> findByReturnPriceNotAndIsOnSaleTrue(double returnPrice, Pageable page);

	Page<TdGoods> findByReturnPriceNotAndTitleContainingAndIsOnSaleTrue(double returnPrice, String keywords,
			Pageable page);

	// 全部秒杀
	Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueOrderByFlashSaleStartTimeAsc(Pageable page);

	// 正在秒杀
	Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeAfterAndFlashSaleStartTimeBeforeOrderByFlashSaleStartTimeAsc(
			Date current, Date current1, Pageable page);

	// 正在团购
	Page<TdGoods> findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStopTimeAfterAndGroupSaleStartTimeBeforeOrderByGroupSaleStartTimeAsc(
			Date current, Date current1, Pageable page);

	// 已经结束团购
	Page<TdGoods> findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStopTimeBeforeOrderByGroupSaleStartTimeAsc(
			Date current, Pageable page);

	// 已经结束秒杀
	Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeBeforeOrderByFlashSaleStartTimeAsc(
			Date current, Pageable page);

	// 即将开始团购
	Page<TdGoods> findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStartTimeAfterOrderByGroupSaleStartTimeAsc(
			Date current, Pageable page);

	// 即将开始秒杀
	Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeAfterOrderByFlashSaleStartTimeAsc(
			Date current, Pageable page);

	Page<TdGoods> findByTitleContainingIgnoreCaseAndIsOnSaleTrueOrSubTitleContainingAndIsOnSaleTrueOrParamValueCollectContainingAndIsOnSaleTrueOrDetailContainingAndIsOnSaleTrue(
			String key1, String key2, String key3, String key4, Pageable page);

	/**
	 * @author lc
	 * @注释：后台商品删选
	 */
	Page<TdGoods> findByIsOnSaleTrueAndIsFlashSaleTrue(Pageable page);

	Page<TdGoods> findByIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(Pageable page);

	Page<TdGoods> findByIsFlashSaleTrue(Pageable page);

	Page<TdGoods> findByIsOnSaleFalse(Pageable page);

	Page<TdGoods> findByIsOnSaleFalseAndIsFlashSaleTrue(Pageable page);

	Page<TdGoods> findByCategoryIdTreeContaining(String catId, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsFlashSaleTrue(String catId, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsFlashSaleTrue(String catId, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalse(String catId, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsFlashSaleTrue(String catId, Pageable page);

	Page<TdGoods> findByTitleContainingIgnoreCaseOrSubTitleContainingIgnoreCaseOrDetailContaining(String keywords1,
			String keywords2, String keywords3, Pageable page);

	Page<TdGoods> findByTitleContainingIgnoreCaseAndIsFlashSaleTrueOrSubTitleContainingIgnoreCaseAndIsFlashSaleTrueOrDetailContainingIgnoreCaseAndIsFlashSaleTrue(
			String keywords1, String keywords2, String keywords3, Pageable page);

	Page<TdGoods> findByTitleContainingIgnoreCaseAndIsOnSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleTrue(
			String keywords1, String keywords2, String keywords3, Pageable page);

	Page<TdGoods> findByTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrue(
			String keywords1, String keywords2, String keywords3, Pageable page);

	Page<TdGoods> findByTitleContainingIgnoreCaseAndIsOnSaleFalseOrSubTitleContainingIgnoreCaseAndIsOnSaleFalseOrDetailContainingIgnoreCaseAndIsOnSaleFalse(
			String keywords1, String keywords2, String keywords3, Pageable page);

	Page<TdGoods> findByTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrue(
			String keywords1, String keywords2, String keywords3, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseOrCategoryIdTreeContainingAndDetailContainingIgnoreCase(
			String catId1, String keywords1, String catId2, String keywords2, String catId3, String keywords3,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsFlashSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsFlashSaleTrue(
			String catId1, String keywords1, String catId2, String keywords2, String catId3, String keywords3,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrue(
			String catId1, String keywords1, String catId2, String keywords2, String catId3, String keywords3,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrue(
			String catId1, String keywords1, String catId2, String keywords2, String catId3, String keywords3,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleFalseOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrue(
			String catId1, String keywords1, String catId2, String keywords2, String catId3, String keywords3,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrue(
			String catId1, String keywords1, String catId2, String keywords2, String catId3, String keywords3,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeIgnoreCaseAndIsOnSaleTrue(
			String categoryId, Long leftNumber, Double priceLow, Double priceHigh, String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeIgnoreCaseAndIsOnSaleTrue(
			String categoryId, Long brandId, Long leftNumber, Double priceLow, Double priceHigh, String paramStr,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeIgnoreCaseAndIsOnSaleTrue(
			String categoryId, Long leftNumber, String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeIgnoreCaseAndIsOnSaleTrue(
			String categoryId, Long brandId, Long leftNumber, String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeIgnoreCaseAndIsOnSaleTrue(
			String categoryId, Double priceLow, Double priceHigh, String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeIgnoreCaseAndIsOnSaleTrue(
			String categoryId, Long brandId, Double priceLow, Double priceHigh, String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndParamValueCollectLikeIgnoreCaseAndIsOnSaleTrue(String categoryId,
			String paramStr, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeIgnoreCaseAndIsOnSaleTrue(
			String categoryId, Long brandId, String paramStr, Pageable page);

	// 通过时间筛选
	Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(Date startTime,
			Pageable page);

	Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeOrderBySortIdAsc(Date startTime,
			Pageable page);

	Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeAndFlashSaleStopTimeAfterOrderBySortIdAsc(
			Date startTime, Date stopTime, Pageable page);

	List<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(Date startTime);

	// 正在秒杀 限定开始时间
	Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeAfterAndFlashSaleStartTimeBeforeAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(
			Date current, Date current1, Date startTime, Pageable page);

	// 即将开始秒杀，限定开始时刻
	Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeAfterAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(
			Date current, Date startTime, Pageable page);

	// 已经结束秒杀，限定开始时刻
	Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeBeforeAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(
			Date current, Date startTime, Pageable page);

	Page<TdGoods> findByTitleContainingIgnoreCaseAndIsOnSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleTrueOrParamValueCollectContainingIgnoreCaseAndIsOnSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleTrue(
			String key1, String key2, String key3, String key4, Pageable page);

	Page<TdGoods> findByIsOnSaleTrueAndIsGroupSaleTrue(Pageable page);

	Page<TdGoods> findByIsGroupSaleTrue(Pageable page);

	Page<TdGoods> findByIsOnSaleFalseAndIsGroupSaleTrue(Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsGroupSaleTrue(String catId, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(String catId, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsGroupSaleTrue(String catId, Pageable page);

	Page<TdGoods> findByTitleContainingIgnoreCaseAndIsGroupSaleTrueOrSubTitleContainingIgnoreCaseAndIsGroupSaleTrueOrDetailContainingIgnoreCaseAndIsGroupSaleTrue(
			String keywords1, String keywords2, String keywords3, Pageable page);

	Page<TdGoods> findByTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrue(
			String keywords1, String keywords2, String keywords3, Pageable page);

	Page<TdGoods> findByTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrue(
			String keywords1, String keywords2, String keywords3, Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsGroupSaleTrue(
			String catId1, String keywords1, String catId2, String keywords2, String catId3, String keywords3,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrue(
			String catId1, String keywords1, String catId2, String keywords2, String catId3, String keywords3,
			Pageable page);

	Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrue(
			String catId1, String keywords1, String catId2, String keywords2, String catId3, String keywords3,
			Pageable page);

	/**
	 * 查找首页推荐的所有商品
	 * 
	 * @author dengxiao
	 */
	List<TdGoods> findByIsRecommendIndexTrueAndIsOnSaleTrueOrderBySortIdAsc();

	/**
	 * 根据分类id查找指定分类下的所有商品（不分页）
	 * 
	 * @author dengxiao
	 */
	List<TdGoods> findByCategoryIdAndIsOnSaleTrueOrderBySortIdAsc(Long categoryId);

	/**
	 * 根据分类id查找指定分类下的所有优惠券商品（不分页）
	 * 
	 * @author dengxiao
	 */
	List<TdGoods> findByCategoryIdAndIsOnSaleTrueAndIsCouponTrueOrderBySortIdAsc(Long categoryId);

	/**
	 * 根据分类id查找指定分类下的所有非优惠券商品（不分页）
	 * 
	 * @author dengxiao
	 */
	List<TdGoods> findByCategoryIdAndIsOnSaleTrueAndIsCouponFalseOrCategoryIdAndIsOnSaleTrueAndIsCouponIsNullOrderBySortIdAsc(
			Long categoryId1, Long categoryId2);

	@Query("select g from TdGoods g where g.categoryId = :categoryId and g.isOnSale = true and (g.isCoupon = false or g.isCoupon is null) order by g.sortId asc")
	List<TdGoods> findByUnCouponGoodsByCategoryIdOrderBySortIdAsc(@Param("categoryId") Long categoryId)
			throws Exception;

	// 更新商品类别信息，查找该类别所有。 zhangji
	List<TdGoods> findByCategoryIdOrderBySortIdAsc(Long categoryId);

	/**
	 * 根据指定的商品sku查找商品
	 * 
	 * @author dengxiao
	 */
	TdGoods findByCode(String code);

	/**
	 * 根据sku查找有效商品
	 * 
	 * @param code
	 * @param status
	 * @return
	 */
	TdGoods findByCodeAndInventoryItemStatus(String code, Long status);

	/**
	 * 根据关键词模糊查询商品，其涉及到商品的名称，商品的标题，商品的副标题，商品的sku，商品的分类名称，最后按照sortId（排序号）正序排序
	 * 增加商品isCoupon=false
	 * 
	 * @author dengxiao
	 */
	@Query("select g from TdGoods g where " + "g.name like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.title like %?1 and ifnull(g.isCoupon,0)=0 or " + "g.subTitle like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.code like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.categoryTitle like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.categoryIdTree like %?2% and ifnull(g.isCoupon,0)=0 " + "order by g.sortId asc")
	List<TdGoods> findByNameContainingOrTitleContainingOrSubTitleContainingOrCodeContainingOrCategoryTitleContainingOrderBySortIdAsc(
			String keywords1, String keywords2);

	/**
	 * 根据关键词模糊查询商品，其涉及到商品的名称，商品的标题，商品的副标题，商品的sku，商品的分类名称，最后按照sortId（排序号）倒序排序
	 * 增加商品isCoupon=false
	 * 
	 * @author dengxiao
	 */
	@Query("select g from TdGoods g where " + "g.name like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.title like %?1 and ifnull(g.isCoupon,0)=0 or " + "g.subTitle like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.code like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.categoryTitle like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.categoryIdTree like %?2% and ifnull(g.isCoupon,0)=0 " + "order by g.sortId desc")
	List<TdGoods> findByNameContainingOrTitleContainingOrSubTitleContainingOrCodeContainingOrCategoryTitleContainingOrderBySortIdDesc(
			String keywords1, String keywords2);

	/**
	 * 根据关键词模糊查找商品，按照价格正序排序 增加商品isCoupon=false
	 * 
	 * @author dengxiao
	 */
	@Query("select g from TdGoods g , TdProductCategory c , TdPriceListItem p where "
			+ "g.name like %?1 and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 or "
			+ "g.title like %?1 and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 or "
			+ "g.subTitle like %?1 and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 or "
			+ "g.code like %?1 and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 or "
			+ "c.title like %?1 and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 or "
			+ "g.categoryIdTree like %?3% and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 "
			+ "order by p.salePrice asc")
	List<TdGoods> searchGoodsOrderBySalePriceAsc(String keywords, Long priceListId, String keywords2);

	/**
	 * 根据关键词模糊查询商品，按照价格反序排序 增加商品isCoupon=false
	 * 
	 * @author dengxiao
	 */
	@Query("select g from TdGoods g , TdProductCategory c , TdPriceListItem p where "
			+ "g.name like %?1 and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 or "
			+ "g.title like %?1 and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 or "
			+ "g.subTitle like %?1 and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 or "
			+ "g.code like %?1 and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 or "
			+ "c.title like %?1 and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 or "
			+ "g.categoryIdTree like %?3% and g.categoryId = c.id and g.id = p.goodsId and p.priceListId = ?2 and ifnull(g.isCoupon,0)=0 "
			+ "order by p.salePrice desc")
	List<TdGoods> searchGoodsOrderBySalePriceDesc(String keywords, Long priceListId, String keywords2);

	/**
	 * 根据关键词模糊查询商品，按照销量正序排序 增加商品isCoupon=false
	 * 
	 * @author dengxiao
	 */
	@Query("select g from TdGoods g where " + "g.name like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.title like %?1 and ifnull(g.isCoupon,0)=0 or " + "g.subTitle like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.code like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.categoryTitle like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.categoryIdTree like %?2% and ifnull(g.isCoupon,0)=0 " + "order by g.soldNumber asc")
	List<TdGoods> findByNameContainingOrTitleContainingOrSubTitleContainingOrCodeContainingOrCategoryTitleContainingOrderBySoldNumberAsc(
			String keywords1, String keywords2);

	/**
	 * 根据关键词模糊查询商品，按照销量反序排序 增加商品isCoupon=false
	 * 
	 * @author dengxiao
	 */
	@Query("select g from TdGoods g where " + "g.name like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.title like %?1 and ifnull(g.isCoupon,0)=0 or " + "g.subTitle like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.code like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.categoryTitle like %?1 and ifnull(g.isCoupon,0)=0 or "
			+ "g.categoryIdTree like %?2% and ifnull(g.isCoupon,0)=0 " + "order by g.soldNumber desc")
	List<TdGoods> findByNameContainingOrTitleContainingOrSubTitleContainingOrCodeContainingOrCategoryTitleContainingOrderBySoldNumberDesc(
			String keywords1, String keywords2);

	// Max
	List<TdGoods> findByTitleContainingOrSubTitleContainingOrCodeContainingOrderBySortIdDesc(String keywords1,
			String keywords2, String Keywords3);

	List<TdGoods> findByIsColorPackageTrueOrderBySortIdAsc();

	/**
	 * 查询所有券商品的分类id
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午10:06:13
	 */
	@Query("select g.categoryId from TdGoods g where g.isCoupon is true group by g.categoryId")
	List<Long> findByCouponGoodsCategoryId();

	/**
	 * 查找所有的券商品
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午10:11:26
	 */
	List<TdGoods> findByIsCouponTrue();

	/**
	 * 查找指定商品id的分类id集合
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午10:17:33
	 */
	@Query("select g.categoryId from TdGoods g where g.id in ?1 group by g.categoryId")
	List<Long> findCategoryIdByIds(List<Long> ids);

	@Query("select g from TdGoods g , " + "TdPriceListItem pi, " + "TdPriceList pl "
			+ "where (( pi.endDateActive > SYSDATE() AND pi.startDateActive < SYSDATE()) "
			+ "OR (pi.endDateActive is null AND pi.startDateActive < SYSDATE()) ) and "
			+ "(( pl.endDateActive > SYSDATE() AND pl.startDateActive < SYSDATE()) "
			+ "OR (pl.endDateActive is null AND pl.startDateActive < SYSDATE()) ) and g.code = pi.itemNum and pi.priceListId=pl.listHeaderId and pl.cityId= ?1 ")
	List<TdGoods> findBySobId(Long sobId);

	@Query("select distinct new com.ynyes.lyz.entity.TdGoods(g.id,g.title,g.coverImageUri,"
			+ "g.categoryTitle,g.saleType,g.salePrice,g.code,g.onSaleTime) "
			+ "from TdGoods g,TdPriceListItem pli where "
			+ "g.inventoryItemId=pli.goodsId and "
			+ "g.categoryId in ?2 and pli.priceListId in ?1 and "
			+ "(g.title like %?3% or g.subTitle like %?3% or g.detail like %?3% or g.code like %?3% ) "
			+ "and pli.startDateActive<=SYSDATE() and (pli.endDateActive>=SYSDATE() "
			+ "or pli.endDateActive is null ) and g.isOnSale =1 and (g.isCoupon=0 or g.isCoupon is null)")
	Page<TdGoods> queryAllOrderBySortIdAsc(List<Long> priceListIdList, List<Long> categoryIdList, String keywords,
			Pageable page);

	@Query("select g from TdDiySiteInventory i, TdGoods g"
			+ " where i.goodsId=g.id and i.inventory>0"
			+ " and i.diySiteId=?1"
			+ " group by g.code")
	Page<TdGoods> queryAllByDiySiteId(Long diySiteId, Pageable page);
	
	@Query("select g from TdDiySiteInventory i, TdGoods g"
			+ " where i.goodsId=g.id and i.inventory>0"
			+ " and i.diySiteId=?1 and g.categoryId=?2"
			+ " group by g.code")
	Page<TdGoods> queryAllByDiySiteIdAndCategoryId(Long diySiteId, Long categoryId, Pageable page);
	
	@Query("select g from TdDiySiteInventory i, TdGoods g"
			+ " where i.goodsId=g.id and i.inventory>0"
			+ " and i.diySiteId=?1 and (g.title like %?2% or g.code like %?2% )"
			+ " group by g.code")
	Page<TdGoods> queryAllByDiySiteIdAndKeywords(Long diySiteId, String keywords, Pageable page);
	
	@Query("select g from TdDiySiteInventory i, TdGoods g"
			+ " where i.goodsId=g.id and i.inventory>0"
			+ " and i.diySiteId=?1 and g.categoryId=?2 and (g.title like %?3% or g.code like %?3% )"
			+ " group by g.code")
	Page<TdGoods> queryAllByDiySiteIdAndCategoryIdAndKeywords(Long diySiteId, Long categoryId, String keywords, Pageable page);

	/**
	 * 优惠卷商品查询
	 * 
	 * @param cityId
	 *            城市id
	 * @param brandId
	 *            品牌id
	 * @param keywords
	 *            关键字
	 * @author zp
	 */
	@Query("select g from TdGoods g,TdPriceListItem pli,TdPriceList pl where "
			+ "g.brandId = ?2 and g.inventoryItemId=pli.goodsId and pli.priceListId =pl.listHeaderId and pl.cityId=?1 and pl.activeFlag = 'Y' "
			+ "and (g.title like %?3% or g.subTitle like %?3% or g.detail like %?3% or g.code like %?3% ) "
			+ "and pli.startDateActive<=SYSDATE() and (pli.endDateActive>=SYSDATE() "
			+ "or pli.endDateActive is null ) and g.isOnSale =1 and (g.isCoupon=0 or g.isCoupon is null)")
	List<TdGoods> queryCouponGooddsOrderBySortIdAsc(Long cityId, Long brandId, String keywords);

	/**
	 * 优惠卷模板商品查询
	 * 
	 * @param cityId
	 *            城市id
	 * @param keywords
	 *            关键字
	 * @author zp
	 */
	@Query("select g from TdGoods g,TdPriceListItem pli,TdPriceList pl where "
			+ "g.inventoryItemId=pli.goodsId and pli.priceListId =pl.listHeaderId and pl.cityId=?1 "
			+ "and (g.title like %?2% or g.subTitle like %?2% or g.detail like %?2% or g.code like %?2% ) "
			+ "and pli.startDateActive<=SYSDATE() and (pli.endDateActive>=SYSDATE() "
			+ "or pli.endDateActive is null ) and g.isOnSale =1 and (g.isCoupon=0 or g.isCoupon is null)")
	List<TdGoods> queryCouponGooddsOrderBySortIdAsc(Long cityId, String keywords);

	Page<TdGoods> findByCodeContainingOrTitleContainingOrSubTitleContaining(String keywords1, String keywords2,
			String keywords3, Pageable page);
	
	Page<TdGoods> findByCodeContainingOrTitleContainingOrderBySortIdAsc(String keywords1, String keywords2,
			Pageable page);
	Page<TdGoods> findByCodeContainingOrTitleContainingAndIsOnSaleIsTrueOrderBySortIdAsc(String keywords1, String keywords2,
			Pageable page);

	@Query("select goods from TdGoods goods where goods.categoryId = :categoryId and goods.id not in "
			+ "(select un.goodsId from TdUnableSale un where un.diySiteId = :diySiteId) order by sortId asc")
	List<TdGoods> findGoodsByCategoryIdWithoutUnSale(@Param("categoryId") Long categoryId,
			@Param("diySiteId") Long diySiteId);

	List<TdGoods> findByCodeContaining(String keywords);
	
	@Query("select goods.id as goodsId, goods.title as goodsTitle, goods.code as goodsSku, "
			+ "goods.brandTitle as brandTitle, goods.brandId as brandId, "
			+ "goods.isColorful as isColorful, inventory.inventory as inventory "
			+ "from TdGoods goods, TdDiySiteInventory inventory where "
			+ "goods.categoryId = :categoryId and goods.isOnSale = true and "
			+ "goods.id = inventory.goodsId and inventory.regionId = :sobId and "
			+ "inventory.diyCode is null and inventory.inventory > 0")
	List<ClientGoodsResult> findGoodsByInventoryByCategoryId(@Param("categoryId") Long categoryId, @Param("sobId") Long sobId);
	
	@Query(value = "SELECT * from td_goods where title like :keywords1 or sub_title like :keywords1 or code like :keywords1 limit 20", nativeQuery = true)
	List<TdGoods> getPhotoOrderGoods(@Param("keywords1")String keywords);
	
}
