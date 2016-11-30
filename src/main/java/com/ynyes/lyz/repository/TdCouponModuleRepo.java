package com.ynyes.lyz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdCouponModule;

/**
 * 指定商品现金券模板仓库类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月20日下午4:09:30
 */

public interface TdCouponModuleRepo
		extends PagingAndSortingRepository<TdCouponModule, Long>, JpaSpecificationExecutor<TdCouponModule> {

	/**
	 * 根据城市查找优惠券模板
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月20日下午4:56:39
	 */
	Page<TdCouponModule> findByCityIdOrderBySortIdAsc(Long cityId, Pageable page);

	/**
	 * 根据城市和关键字查找模板
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月20日下午5:04:11
	 */
	Page<TdCouponModule> findByCityIdAndTitleContainingOrCityIdAndGoodsTitleContainingOrCityIdAndSkuContainingOrderBySortIdAsc(
			Long cityId1, String keywords1, Long city2, String keywords2, Long city3, String keywords3, Pageable page);

	/**
	 * 根据关键词查找模板
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月20日下午5:06:20
	 * @param
	 */
	Page<TdCouponModule> findByTitleContainingOrGoodsTitleContainingOrSkuContainingOrderBySortIdAsc(String keywords1,
			String keywords2, String keywords3, Pageable page);

	/**
	 * 根据商品id和城市sobid查找模板
	 * 
	 * @author DengXiao
	 */
	TdCouponModule findByGoodsIdAndCityId(Long goodsId, Long cityId);

	/**
	 * 根据商品id和城市sobid和模板类型查找模板
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月29日下午7:13:21
	 */
	TdCouponModule findByGoodsIdAndCityIdAndType(Long goodsId, Long cityId, Long type);

}
