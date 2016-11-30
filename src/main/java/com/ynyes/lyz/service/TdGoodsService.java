package com.ynyes.lyz.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.ynyes.lyz.entity.TdBrand;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.repository.TdGoodsRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * TdGoods 服务类
 * 
 * @author lc
 * 
 */

@Service
@Transactional
public class TdGoodsService {

	@Autowired
	TdGoodsRepo repository;

	@Autowired
	TdProductCategoryService tdProductCategoryService;

	@Autowired
	TdArticleService tdArticleService;

	@Autowired
	TdPriceListService tdPriceListService;

	@Autowired
	TdPriceListItemService tdPriceListItemService;

	@Autowired
	TdGoodsGiftService tdGoodsGiftService;

	@Autowired
	TdGoodsCombinationService tdGoodsCombinationService;

	@Autowired
	TdPriceChangeLogService tdPriceChangeLogService;

	@Autowired
	private TdBrandService tdBrandService;
	
	/******** 功能部分 ***********/

	/**
	 * @author lc @注释：商品推荐、
	 */
	// 无类别限制
	public Boolean getrecommend(ModelMap map) {

		// 分类推荐
		map.addAttribute("type_recommend_page",
				this.findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(0, SiteMagConstant.pageSize));

		// 销量排行
		map.addAttribute("soldNumber_recommend_page",
				this.findByIsOnSaleTrueOrderBySoldNumberDesc(0, SiteMagConstant.pageSize));

		// 新品推荐
		map.addAttribute("new_recommend_page",
				this.findByIsNewTrueAndIsOnSaleTrueOrderByIdDesc(0, SiteMagConstant.pageSize));

		// 首页推荐商品
		map.addAttribute("index_recommend_page",
				this.findByIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(0, SiteMagConstant.pageSize));

		// 特价推荐商品
		map.addAttribute("specialPrice_recommend_page",
				this.findByIsSpecialPriceTrueAndIsOnSaleTrueOrderByIdDesc(0, SiteMagConstant.pageSize));

		// 热卖推荐
		map.addAttribute("hot_recommend_page",
				this.findByIsHotTrueAndIsOnSaleTrueOrderByIdDesc(0, SiteMagConstant.pageSize));

		return true;
	}

	// 有类别限制
	public Boolean getrecommendByCategory(Long catId, ModelMap map) {

		// 特定类型的分类推荐
		map.addAttribute("type_recommend_page", this.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(
				catId, 0, SiteMagConstant.pageSize));

		// 特定类型的销量排行
		map.addAttribute("soldNumber_recommend_page",
				this.findByCategoryIdAndIsOnSaleTrueOrderBySoldNumberDesc(catId, 0, SiteMagConstant.pageSize));

		// 特定类型的新品推荐
		map.addAttribute("new_recommend_page",
				this.findByCategoryIdAndisNewTrueAndIsOnSaleTrueOrderByIdDesc(catId, 0, SiteMagConstant.pageSize));

		// 特定类型的首页推荐商品
		map.addAttribute("index_recommend_page",
				this.findByCategoryIdAndIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(catId, 0,
						SiteMagConstant.pageSize));

		// 制定类别的特价推荐商品
		map.addAttribute("specialPrice_recommend_page", this
				.findByCategoryIdAndisSpecialPriceTrueAndIsOnSaleTrueOrderByIdDesc(catId, 0, SiteMagConstant.pageSize));

		// 特定类型的热卖推荐
		map.addAttribute("hot_recommend_page",
				this.findByCategoryIdAndisHotTrueAndIsOnSaleTrueOrderByIdDesc(catId, 0, SiteMagConstant.pageSize));

		return true;
	}

	/******** 功能部分 ***********/

	/******** 商品筛选查找部分 ***********/
	/**
	 * @author lc
	 * @注释：按一定条件查找商品
	 */
	// 查找所有商品
	public Page<TdGoods> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findAll(pageRequest);
	}
	// 查找所有商品
	public List<TdGoods> findAll() {
		return (List<TdGoods>) repository.findAll();
	}

	// 查找所有商品
	public Page<TdGoods> findAllOrderById(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository.findAll(pageRequest);
	}

	// 查找所有商品按序号排序
	public Page<TdGoods> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}

	// 查找所有商品按序号排序
	public Page<TdGoods> findByIsGiftOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findByIsGiftTrueAndIsOnSaleTrue(pageRequest);
	}

	// 查找所有上架商品按序号排序
	public Page<TdGoods> findByIsOnSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findByIsOnSaleTrue(pageRequest);
	}

	// 查找所有上架商品
	public Page<TdGoods> findAllAndIsOnSaleTrue(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsOnSaleTrue(pageRequest);
	}

	// 查找前十个id的商品并按销量排序
	public List<TdGoods> findTop10ByIsOnSaleTrueOrderBySoldNumberDesc() {
		return repository.findTop10ByIsOnSaleTrueOrderBySoldNumberDesc();
	}

	//
	public List<TdGoods> findByIdAndIsOnSaleTrue(Iterable<Long> ids) {
		return repository.findByIdAndIsOnSaleTrue(ids);
	}

	// 查找返现金额不为零的上架商品
	public Page<TdGoods> findByReturnPriceNotZeroAndIsOnSaleTrue(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByReturnPriceNotAndIsOnSaleTrue(0.0, pageRequest);
	}

	// 搜索返现金额不为零的上架商品
	public Page<TdGoods> findByReturnPriceNotZeroAndSearchAndIsOnSaleTrue(int page, int size, String keywords) {
		if (null == keywords) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByReturnPriceNotAndTitleContainingAndIsOnSaleTrue(0.0, keywords, pageRequest);
	}

	// 按上架时间排序
	public Page<TdGoods> findByIsOnSaleTrueOrderByOnSaleTimeDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByIsOnSaleTrueOrderByOnSaleTimeDesc(pageRequest);
	}

	// 按销量排序
	public Page<TdGoods> findByIsOnSaleTrueOrderBySoldNumberDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "soldNumber"));

		return repository.findByIsOnSaleTrue(pageRequest);
	}

	// 根据产品id查找上架商品
	public List<TdGoods> findByProductIdAndIsOnSaleTrue(Long productId) {
		if (null == productId) {
			return null;
		}

		return repository.findByProductIdAndIsOnSaleTrue(productId);
	}

	/**
	 * @author lc
	 * @注释：搜索商品
	 */
	// 痛殴关键字搜索所有商品并按序号排序
	public Page<TdGoods> searchAndOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByTitleContainingOrSubTitleContainingOrDetailContainingOrCodeContainingOrderBySortIdAsc(
				keywords, keywords, keywords, keywords, pageRequest);
	}

	// 痛殴关键字搜索上架商品并按序号排序
	public Page<TdGoods> searchAndIsOnSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByTitleContainingOrSubTitleContainingOrDetailContainingAndIsOnSaleTrueOrderBySortIdAsc(
				keywords, keywords, keywords, pageRequest);
	}

	// 搜索特定类别的所有商品并按序号排序
	public Page<TdGoods> searchAndFindByCategoryIdOrderBySortIdAsc(String keywords, Long categoryId, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		if (categoryId == -1) {
			return repository
					.findByCategoryIdIsNullAndTitleContainingOrCategoryIdIsNullAndSubTitleContainingOrCategoryIdIsNullAndDetailContainingOrCategoryIdIsNullAndCodeContainingOrderBySortIdAsc(
							keywords, keywords, keywords, keywords, pageRequest);
		}

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingOrCategoryIdTreeContainingAndSubTitleContainingOrCategoryIdTreeContainingAndDetailContainingOrCategoryIdTreeContainingAndCodeContainingOrderBySortIdAsc(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	// 搜索特定类别的上架商品并按序号排序
	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleTrueOrderBySortIdAsc(String keywords, Long categoryId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndDetailContainingAndIsOnSaleTrueOrderBySortIdAsc(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> searchGoods(String keywords, int page, int size) {
		if (null == keywords) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository
				.findByTitleContainingAndIsOnSaleTrueOrSubTitleContainingAndIsOnSaleTrueOrParamValueCollectContainingAndIsOnSaleTrueOrDetailContainingAndIsOnSaleTrue(
						keywords, keywords, keywords, keywords, pageRequest);
	}

	/**
	 * @author lc @注释：特定类型的商品推荐(包含热卖、新品、首页推荐、分类推荐、特价)
	 */
	// 特定类型的分类推荐
	public Page<TdGoods> findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(Long catId, int page,
			int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(catStr,
				pageRequest);
	}

	// 特定类型的销量排行
	public Page<TdGoods> findByCategoryIdAndIsOnSaleTrueOrderBySoldNumberDesc(Long catId, int page, int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySoldNumberDesc(catStr, pageRequest);
	}

	// 特定类型的新品推荐
	public Page<TdGoods> findByCategoryIdAndIsOnSaleTrueOrderByOnSaleTimeDesc(Long catId, int page, int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrueOrderByOnSaleTimeDesc(catStr, pageRequest);
	}

	// 特定类型的首页推荐商品
	public Page<TdGoods> findByCategoryIdAndIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(Long catId, int page,
			int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(catStr,
				pageRequest);
	}

	// 特定类型的新品推荐商品
	public Page<TdGoods> findByCategoryIdAndisNewTrueAndIsOnSaleTrueOrderByIdDesc(Long catId, int page, int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsNewTrueAndIsOnSaleTrueOrderByIdDesc(catStr, pageRequest);
	}

	// 特定类型的特价推荐商品
	public Page<TdGoods> findByCategoryIdAndisSpecialPriceTrueAndIsOnSaleTrueOrderByIdDesc(Long catId, int page,
			int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsSpecialPriceTrueAndIsOnSaleTrueOrderByIdDesc(catStr,
				pageRequest);
	}

	// 特定类型的热卖推荐商品
	public Page<TdGoods> findByCategoryIdAndisHotTrueAndIsOnSaleTrueOrderByIdDesc(Long catId, int page, int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsHotTrueAndIsOnSaleTrueOrderByIdDesc(catStr, pageRequest);
	}

	/**
	 * @author lc @注释：商品推荐(包含热卖、新品、首页推荐、分类推荐、特价)
	 */
	// 查找分类推荐上架商品按id排序
	public Page<TdGoods> findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(pageRequest);
	}

	// 查找新品推荐上架商品按id排序
	public Page<TdGoods> findByIsNewTrueAndIsOnSaleTrueOrderByIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsNewTrueAndIsOnSaleTrueOrderByIdDesc(pageRequest);
	}

	// 查找首页推荐上架商品按id排序
	public Page<TdGoods> findByIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(pageRequest);
	}

	// 查找特价推荐上架商品按id排序
	public Page<TdGoods> findByIsSpecialPriceTrueAndIsOnSaleTrueOrderByIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsSpecialPriceTrueAndIsOnSaleTrueOrderByIdDesc(pageRequest);
	}

	// 查找特价推荐上架商品按id排序
	public Page<TdGoods> findByIsHotTrueAndIsOnSaleTrueOrderByIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsHotTrueAndIsOnSaleTrueOrderByIdDesc(pageRequest);
	}

	// 查找制定类别的所有商品
	public Page<TdGoods> findByCategoryIdAndIsOnSaleTrue(Long catId, int page, int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrue(catStr, pageRequest);
	}

	// 查找制定类别的所有商品并按序号排序
	public Page<TdGoods> findByCategoryIdTreeContainingOrderBySortIdAsc(Long catId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		if (catId == -1) {
			return repository.findByCategoryIdIsNullOrderBySortIdAsc(pageRequest);
		}

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingOrderBySortIdAsc(catIdStr, pageRequest);
	}

	// 查找制定类别的上架商品并按序号排序
	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySortIdAsc(Long catId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySortIdAsc(catIdStr, pageRequest);
	}

	/**
	 * @author MDJ 接口查询
	 */
	// 通过物料id查询
	public TdGoods findByinventoryItemId(Long inventoryItemId) {
		if (inventoryItemId == null) {
			return null;
		}
		return repository.findByinventoryItemId(inventoryItemId);
	}

	/**
	 * @author MDJ 接口查询
	 */
	// 通过物料id查询
	public List<TdGoods> findByInvCategoryId(Long invCategoryId) {
		if (invCategoryId == null) {
			return null;
		}
		return repository.findByInvCategoryId(invCategoryId);
	}

	// 查找空的category
	public List<TdGoods> findByCategoryIdIsNull() {
		return repository.findByCategoryIdIsNull();
	}

	/**
	 * 搜索商品
	 * 
	 * @param keywords
	 *            关键字
	 * @param page
	 *            页号
	 * @param size
	 *            页大小
	 * @param sortName
	 *            排序字段名
	 * @param sd
	 *            排序方向
	 * @return
	 */
	public Page<TdGoods> searchGoods(String keywords, int page, int size, String sortName, Direction dir) {
		if (null == keywords || null == sortName) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size, new Sort(dir, sortName));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleTrueOrSubTitleContainingAndIsOnSaleTrueOrParamValueCollectContainingAndIsOnSaleTrueOrDetailContainingAndIsOnSaleTrue(
						keywords, keywords, keywords, keywords, pageRequest);
	}

	/**
	 * @author lc
	 * @注释：列表页商品筛选
	 */
	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(
			long catId, double priceLow, double priceHigh, List<String> paramValueList, Pageable pageRequest) {

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(
			long catId, long brandId, double priceLow, double priceHigh, List<String> paramValueList,
			Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrue(long catId,
			List<String> paramValueList, Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrue(long catId,
			long brandId, List<String> paramValueList, Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(long catId, double priceLow,
			double priceHigh, List<String> paramValueList, Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(long catId,
			long brandId, double priceLow, double priceHigh, List<String> paramValueList, Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrue(long catId, List<String> paramValueList,
			Pageable pageRequest) {
		String paramStr = "%";

		if (null != paramValueList) {
			for (int i = 0; i < paramValueList.size(); i++) {
				String value = paramValueList.get(i);
				if (!"".equals(value)) {
					paramStr += value;
					paramStr += "%";
				}
			}
		}

		return repository.findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue("[" + catId + "]",
				paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrue(long catId, long brandId,
			List<String> paramValueList, Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", brandId, paramStr, pageRequest);
	}

	/**** 列表页商品筛选 ******/

	/**
	 * @author lc
	 * @注释：后台商品删选
	 */
	public Page<TdGoods> findByIsOnSaleTrueAndFlashSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsOnSaleTrueAndIsFlashSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsOnSaleTrueAndIsFlashSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsOnSaleFalseAndIsFlashSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsOnSaleFalseOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsOnSaleFalse(pageRequest);
	}

	public Page<TdGoods> searchAndIsOnSaleFalseOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleFalseOrSubTitleContainingIgnoreCaseAndIsOnSaleFalseOrDetailContainingIgnoreCaseAndIsOnSaleFalse(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsFlashSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsFlashSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsFlashSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsFlashSaleTrueOrSubTitleContainingIgnoreCaseAndIsFlashSaleTrueOrDetailContainingIgnoreCaseAndIsFlashSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrderBySortIdAsc(Long catId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsFlashSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleTrueAndIsFlashSaleTrueOrderBySortIdAsc(String keywords,
			Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(Long catId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsFlashSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(String keywords,
			Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseOrderBySortIdAsc(Long catId, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleFalse(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleFalseOrderBySortIdAsc(String keywords, Long categoryId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleFalseOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsFlashSaleTrueOrderBySortIdAsc(Long catId, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsFlashSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsFlashSaleTrueOrderBySortIdAsc(String keywords, Long categoryId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsFlashSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsFlashSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsOnSaleTrueAndGroupSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsOnSaleTrueAndIsGroupSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsOnSaleTrueAndIsGroupSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsOnSaleFalseAndIsGroupSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsGroupSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsGroupSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsGroupSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsGroupSaleTrueOrSubTitleContainingIgnoreCaseAndIsGroupSaleTrueOrDetailContainingIgnoreCaseAndIsGroupSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrderBySortIdAsc(Long catId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleTrueAndIsGroupSaleTrueOrderBySortIdAsc(String keywords,
			Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(Long catId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsGroupSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(String keywords,
			Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsGroupSaleTrueOrderBySortIdAsc(Long catId, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsGroupSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsGroupSaleTrueOrderBySortIdAsc(String keywords, Long categoryId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsGroupSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	/**** 后台商品删选 ****/

	/**** 团购和秒杀 ****/
	/**
	 * @author lc
	 * @注释：所有秒杀商品
	 */
	public Page<TdGoods> findByFlashSaleAllOrderByFlashSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsFlashSaleTrueAndIsOnSaleTrueOrderByFlashSaleStartTimeAsc(pageRequest);
	}

	/**
	 * 所有团购
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByGroupSaleAllOrderByGroupSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsGroupSaleTrueAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(pageRequest);
	}

	/**
	 * 正在团购商品
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByGroupSalingOrderByGroupSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStopTimeAfterAndGroupSaleStartTimeBeforeOrderByGroupSaleStartTimeAsc(
						new Date(), new Date(), pageRequest);
	}

	/**
	 * 正在秒杀商品
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByFlashSalingOrderByFlashSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeAfterAndFlashSaleStartTimeBeforeOrderByFlashSaleStartTimeAsc(
						new Date(), new Date(), pageRequest);
	}

	/**
	 * 已结束团购
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByGroupSaleEndedOrderByGroupSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStopTimeBeforeOrderByGroupSaleStartTimeAsc(
				new Date(), pageRequest);
	}

	/**
	 * 已结束秒杀
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByFlashSaleEndedOrderByFlashSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeBeforeOrderByFlashSaleStartTimeAsc(
				new Date(), pageRequest);
	}

	/**
	 * 即将开始团购
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByGroupSaleGoingToStartOrderByGroupSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStartTimeAfterOrderByGroupSaleStartTimeAsc(
				new Date(), pageRequest);
	}

	/**
	 * 即将开始秒杀
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByFlashSaleGoingToStartOrderByFlashSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeAfterOrderByFlashSaleStartTimeAsc(
				new Date(), pageRequest);
	}

	/******** 商品信息查找部分 ***********/

	/**
	 * 判断该商品是否正在进行秒杀
	 * 
	 * @param tdGoods
	 * @return
	 */
	public boolean isFlashSaleTrue(TdGoods tdGoods) {
		if (null == tdGoods) {
			return false;
		}

		Date curr = new Date();

		if (null != tdGoods.getIsFlashSale() && tdGoods.getIsFlashSale() && null != tdGoods.getFlashSaleStartTime()
				&& tdGoods.getFlashSaleStartTime().before(curr) && null != tdGoods.getFlashSaleStopTime()
				&& tdGoods.getFlashSaleStopTime().after(curr) && null != tdGoods.getFlashSaleLeftNumber()
				&& tdGoods.getFlashSaleLeftNumber().compareTo(0L) > 0) {
			return true;
		}

		return false;
	}

	/**
	 * 计算实时秒杀价
	 * 
	 * @param goods
	 * @return
	 */
	public Double getFlashPrice(TdGoods goods) {
		if (null == goods) {
			return null;
		}

		Double flashPrice = null;
		Date curr = new Date();

		if (null != goods.getIsFlashSale() && null != goods.getFlashSaleStartTime()
				&& null != goods.getFlashSaleStopTime() && null != goods.getFlashSalePrice() && goods.getIsFlashSale()
				&& goods.getFlashSaleStopTime().after(curr) && goods.getFlashSaleStartTime().before(curr)) {
			// 剩余毫秒数
			long ts = goods.getFlashSaleStopTime().getTime() - curr.getTime();
			// 总共毫秒数
			long allts = goods.getFlashSaleStopTime().getTime() - goods.getFlashSaleStartTime().getTime();

			flashPrice = goods.getFlashSalePrice() * ts / allts;

			BigDecimal b = new BigDecimal(flashPrice);

			flashPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		return flashPrice;
	}

	/**
	 * 判断该商品是否正在进行团购
	 * 
	 * @param tdGoods
	 * @return
	 */
	public boolean isGroupSaleTrue(TdGoods tdGoods) {
		if (null == tdGoods) {
			return false;
		}

		Date curr = new Date();

		if (null != tdGoods.getIsGroupSale() && tdGoods.getIsGroupSale() && null != tdGoods.getGroupSaleStartTime()
				&& tdGoods.getGroupSaleStartTime().before(curr) && null != tdGoods.getGroupSaleStopTime()
				&& tdGoods.getGroupSaleStopTime().after(curr) && null != tdGoods.getGroupSaleLeftNumber()
				&& tdGoods.getGroupSaleLeftNumber().compareTo(0L) > 0) {
			return true;
		}

		return false;
	}

	/**** 团购和秒杀 ****/

	/**
	 * 删除
	 * 
	 * @param id
	 *            菜单项ID
	 */
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	/**
	 * 删除
	 * 
	 * @param e
	 *            菜单项
	 */
	public void delete(TdGoods e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            ID
	 * @return
	 */
	public TdGoods findOne(Long id) {
		if (null == id) {
			return null;
		}

		return repository.findOne(id);
	}

	/**
	 * 保存类型
	 * 
	 * @param e
	 * @return
	 */
	public TdGoods save(TdGoods e, String manager) {
		if (null == e) {
			return null;
		}

		if (e.getMarketPrice() == null) {
			e.setMarketPrice(10.0);
		}
		// 保存分类名称
		if (null != e.getCategoryId()) {
			TdProductCategory cat = tdProductCategoryService.findOne(e.getCategoryId());
			e.setCategoryTitle(cat.getTitle());
			e.setCategoryIdTree(cat.getParentTree());
		}

		// 保存销售价
		if (null == e.getReturnPrice()) {
			e.setReturnPrice(0.0);
		}

		if (null == e.getOutFactoryPrice()) {
			e.setOutFactoryPrice(0.0);
		}

		e.setSalePrice(e.getReturnPrice() + e.getOutFactoryPrice());

		// 创建时间
		if (null == e.getCreateTime()) {
			e.setCreateTime(new Date());
		}

		// 上架时间
		if (null == e.getOnSaleTime() && e.getIsOnSale()) {
			e.setOnSaleTime(new Date());
		}

		if (null == e.getId()) {
			if (null != e.getGiftList() && e.getGiftList().size() > 0) {
				e.setTotalGift(e.getGiftList().size());
			}

			if (null != e.getCombList() && e.getCombList().size() > 0) {
				e.setTotalComb(e.getCombList().size());
			}
		}

		// 保存品牌名
		Long brandId = e.getBrandId();
		if (null != brandId) {
			TdBrand brand = tdBrandService.findOne(brandId);
			if (null != brand) {
				e.setBrandTitle(brand.getTitle());
			}
		}

		e = repository.save(e);

		return e;
	}

	/**
	 * 查找所有首页推荐的商品
	 * 
	 * @author dengxiao
	 */
	public List<TdGoods> findByIsRecommendIndexTrueAndIsOnSaleTrueOrderBySortIdAsc() {
		return repository.findByIsRecommendIndexTrueAndIsOnSaleTrueOrderBySortIdAsc();
	}

	/**
	 * 根据分类id查找其下所有的商品（不分页）
	 * 
	 * @author dengxiao
	 */
	public List<TdGoods> findByCategoryIdAndIsOnSaleTrueOrderBySortIdAsc(Long categoryId) {
		if (null == categoryId) {
			return null;
		}
		return repository.findByCategoryIdAndIsOnSaleTrueOrderBySortIdAsc(categoryId);
	}

	/**
	 * //更新商品类别信息，查找该类别所有。 zhangji
	 * 
	 * @author Zhangji
	 * @param categoryId
	 * @return
	 */
	public List<TdGoods> findByCategoryIdOrderBySortIdAsc(Long categoryId) {
		if (null == categoryId) {
			return null;
		}
		return repository.findByCategoryIdOrderBySortIdAsc(categoryId);
	}

	/**
	 * 根据指定的sku查找商品
	 * 
	 * @author dengxiao
	 */
	public TdGoods findByCode(String code) {
		if (null == code) {
			return null;
		}
		return repository.findByCode(code);
	}
	public TdGoods findByCodeAndStatus(String code,Long status)
	{
		if (null == code || status == null)
		{
			return null;
		}
		return repository.findByCodeAndInventoryItemStatus(code, status);
	}

	/**
	 * 根据关键词模糊查询商品，其涉及到商品的名称，商品的标题，商品的副标题，商品的sku，最后按照sortId（排序号）正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdGoods> searchGoodsOrderBySortIdAsc(String keywords,String cateId) {
		if (null == keywords) {
			return null;
		}
		return repository
				.findByNameContainingOrTitleContainingOrSubTitleContainingOrCodeContainingOrCategoryTitleContainingOrderBySortIdAsc(
						keywords,cateId);
	}

	/**
	 * 根据关键词模糊查询商品，其涉及到商品的名称，商品的标题，商品的副标题，商品的sku，最后按照sortId（排序号）倒序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdGoods> searchGoodsOrderBySortIdDesc(String keywords,String cateId) {
		if (null == keywords) {
			return null;
		}
		return repository
				.findByNameContainingOrTitleContainingOrSubTitleContainingOrCodeContainingOrCategoryTitleContainingOrderBySortIdDesc(
						keywords,cateId);
	}

	/**
	 * 根据关键词模糊查找商品，按照价格正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdGoods> searchGoodsOrderBySalePriceAsc(String keywords, Long priceListId,String cateId) {
		if (null == keywords || null == priceListId) {
			return null;
		}
		return repository.searchGoodsOrderBySalePriceAsc(keywords, priceListId,cateId);
	}

	/**
	 * 根据关键词模糊查找商品，按照价格反序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdGoods> searchGoodsOrderBySalePriceDesc(String keywords, Long priceListId,String cateId) {
		if (null == keywords || null == priceListId) {
			return null;
		}
		return repository.searchGoodsOrderBySalePriceDesc(keywords, priceListId,cateId);
	}

	/**
	 * 根据关键词模糊查询商品，按照销量正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdGoods> searchGoodsOrderBySoldNumberAsc(String keywords,String cateId) {
		if (null == keywords) {
			return null;
		}
		return repository
				.findByNameContainingOrTitleContainingOrSubTitleContainingOrCodeContainingOrCategoryTitleContainingOrderBySoldNumberAsc(
						keywords,cateId);
	}

	/**
	 * 根据关键词模糊查询商品，按照销量正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdGoods> searchGoodsOrderBySoldNumberDesc(String keywords,String cateId) {
		if (null == keywords) {
			return null;
		}
		return repository
				.findByNameContainingOrTitleContainingOrSubTitleContainingOrCodeContainingOrCategoryTitleContainingOrderBySoldNumberDesc(
						keywords,cateId);
	}

	public List<TdGoods> searchGoods(String keywords) {
		if (null == keywords) {
			return null;
		}
		return repository.findByTitleContainingOrSubTitleContainingOrCodeContainingOrderBySortIdDesc(keywords, keywords,
				keywords);
	}

	public List<TdGoods> findByIsColorPackageTrueOrderBySortIdAsc() {
		return repository.findByIsColorPackageTrueOrderBySortIdAsc();
	}

	/**
	 * 查询所有券商品的分类id
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午10:06:13
	 */
	public List<Long> findByCouponGoodsCategoryId() {
		return repository.findByCouponGoodsCategoryId();
	}

	/**
	 * 查找所有的券商品
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午10:11:26
	 */
	public List<TdGoods> findByIsCouponTrue() {
		return repository.findByIsCouponTrue();
	}

	/**
	 * 查找指定商品id的分类id集合
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午10:17:33
	 */
	public List<Long> findCategoryIdByIds(List<Long> ids) {
		if (null == ids || ids.size() == 0) {
			return null;
		}
		return repository.findCategoryIdByIds(ids);
	}

	/**
	 * 根据分类id查找指定分类下的所有优惠券商品（不分页）
	 * 
	 * @author dengxiao
	 */
	public List<TdGoods> findByCategoryIdAndIsOnSaleTrueAndIsCouponTrueOrderBySortIdAsc(Long categoryId) {
		if (null == categoryId) {
			return null;
		}
		return repository.findByCategoryIdAndIsOnSaleTrueAndIsCouponTrueOrderBySortIdAsc(categoryId);
	}

	/**
	 * 根据分类id查找指定分类下的所有非优惠券商品（不分页）
	 * 
	 * @author dengxiao
	 */
	public List<TdGoods> findByCategoryIdAndIsOnSaleTrueAndIsCouponNotTrueOrderBySortIdAsc(Long categoryId) {
		if (null == categoryId) {
			return null;
		}
		return repository.findByCategoryIdAndIsOnSaleTrueAndIsCouponFalseOrCategoryIdAndIsOnSaleTrueAndIsCouponIsNullOrderBySortIdAsc(
						categoryId, categoryId);
	}
	
	public List<TdGoods> findGoodsByCategoryIdWithoutUnSale(Long categoryId, Long diySiteId) {
		if (null == categoryId || null == diySiteId) {
			return null;
		}
		return repository.findGoodsByCategoryIdWithoutUnSale(categoryId, diySiteId);
	}
	
	public List<TdGoods> findBySobId(Long sobId)
	{
		return repository.findBySobId(sobId);
	}
	
	/**
	 * app 商品搜索
	 * @param keywords 搜索关键字
	 * @param sortFiled 排序字段
	 * @param rule 正序反序
	 * @param categoryTitle 一级类别
	 * @return 结果集
	 * @author zp
	 */
	public List<TdGoods> searchGoodsList(String keywords,String sortFiled,String rule,List<String> categoryTitle){
		Criteria<TdGoods> c = new Criteria<TdGoods>();
		//查询条件
		if(StringUtils.isNotBlank(keywords)){
			c.add(Restrictions.or(Restrictions.like("name", keywords, true),Restrictions.like("title", keywords, true),
					Restrictions.like("subTitle", keywords, true),Restrictions.like("code", keywords, true),
					Restrictions.like("categoryTitle", keywords, true)));
		}
		if(categoryTitle!=null && categoryTitle.size()>0){
			c.add(Restrictions.in("categoryTitle",categoryTitle,true));
		}
		c.add(Restrictions.eq("isOnSale", true, true));
		//排序
		if("0".equals(sortFiled)){
			if("0".equals(rule)){
				c.setOrderByAsc("sortId");
			}else{
				c.setOrderByDesc("sortId");
			}
		}else if("1".equals(sortFiled)){
			if("0".equals(rule)){
				c.setOrderByAsc("sortId");
			}else{
				c.setOrderByDesc("sortId");
			}
		}else if("2".equals(sortFiled)){
			if("0".equals(rule)){
				c.setOrderByAsc("soldNumber");
			}else{
				c.setOrderByDesc("soldNumber");
			}
		}
		return repository.findAll(c);
		
	}
	
	/**
	 * 后代 商品查询
	 * @param keywords 关键字
	 * @param brandId 品牌id
	 * @param categoryId 类别id
	 * @param page 页数
	 * @param size 行数
	 * @param isOnsale 商品上下架
	 * @return 分页结果集
	 * @author zp
	 */
	public Page<TdGoods> searchGoodsList(String keywords,Long brandId,Long categoryId,int page,int size,Boolean isOnSale){
		//分页加排序
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		Criteria<TdGoods> c = new Criteria<TdGoods>();
		//查询条件
		if(StringUtils.isNotBlank(keywords)){
			c.add(Restrictions.or(Restrictions.like("name", keywords, true),Restrictions.like("title", keywords, true),
					Restrictions.like("subTitle", keywords, true),Restrictions.like("code", keywords, true),
					Restrictions.like("categoryTitle", keywords, true)));
		}
		if(categoryId!=null){
			if(categoryId==-1L){
				c.add(Restrictions.isNull("categoryIdTree"));
			}else{
				String catIdStr = "[" + categoryId + "]";
				c.add(Restrictions.like("categoryIdTree",catIdStr,true));
			}
			
		}
		if(brandId!=null){
			c.add(Restrictions.eq("brandId",brandId,true));
		}
		if(isOnSale!=null){
			c.add(Restrictions.eq("isOnSale", isOnSale, true));
		}
		return repository.findAll(c,pageRequest);
	}
	
	public List<TdGoods> searchGoodsByKeywordsAndBrand(String keywords,Long brandId){
		Criteria<TdGoods> c = new Criteria<TdGoods>();
		//查询条件
		if(StringUtils.isNotBlank(keywords)){
			c.add(Restrictions.or(Restrictions.like("title", keywords, true),Restrictions.like("subTitle", keywords, true),
					Restrictions.like("code", keywords, true)));
		}
		if(brandId!=null){
			c.add(Restrictions.eq("brandId", brandId, true));
		}
		return repository.findAll(c);
	}
	
	// 查找所有商品按序号排序
	public Page<TdGoods> queryAllOrderBySortIdAsc(List<Long> priceListIdList,List<Long> categoryIdList,String keywords,int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.queryAllOrderBySortIdAsc(priceListIdList,categoryIdList,keywords,pageRequest);
	}
	
	/**
	 *优惠卷商品查询
	 * @param cityId 城市id
	 * @param brandId 品牌id
	 * @param keywords 关键字
	 * @author zp
	 */
	public List<TdGoods> queryCouponGooddsOrderBySortIdAsc(Long cityId,Long brandId,String keywords){
		return repository.queryCouponGooddsOrderBySortIdAsc(cityId, brandId, keywords);
	}
	
	/**
	 * 优惠卷模板商品查询
	 * @param cityId 城市id
	 * @param keywords 关键字
	 * @author zp
	 */
	public List<TdGoods> queryCouponGooddsOrderBySortIdAsc(Long cityId,String keywords){
		return repository.queryCouponGooddsOrderBySortIdAsc(cityId, keywords);
	}
	
	public Page<TdGoods> findByCodeContainingOrTitleContainingOrSubTitleContaining(String keywords, int page, int size) {
		if (null == keywords) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByCodeContainingOrTitleContainingOrSubTitleContaining(keywords, keywords, keywords, pageRequest);
	}
	
	public List<TdGoods> findByCodeContaining(String keywords) {
		if (null == keywords) {
			return null;
		}
		return repository.findByCodeContaining(keywords);
	}
}
