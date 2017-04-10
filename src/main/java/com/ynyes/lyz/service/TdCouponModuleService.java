package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdCouponModule;
import com.ynyes.lyz.repository.TdCouponModuleRepo;

/**
 * 指定产品优惠券模板服务类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月20日下午4:11:05
 */

@Service
@Transactional
public class TdCouponModuleService {

	@Autowired
	private TdCouponModuleRepo repository;

	public TdCouponModule save(TdCouponModule e) {
		if (null == e) {
			return e;
		}
		return repository.save(e);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdCouponModule findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdCouponModule> findAll() {
		return (List<TdCouponModule>) repository.findAll();
	}

	public Page<TdCouponModule> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findAll(pageRequest);
	}

	/**
	 * 根据城市查找优惠券模板
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月20日下午4:56:39
	 */
	public Page<TdCouponModule> findByCityIdOrderBySortIdAsc(Long cityId, int page, int size) {
		if (null == cityId) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByCityIdOrderBySortIdAsc(cityId, pageRequest);
	}

	/**
	 * 根据城市和关键字查找模板
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月20日下午5:04:11
	 */
	public Page<TdCouponModule> findByCityIdAndTitleContainingOrCityIdAndGoodsTitleContainingOrCityIdAndSkuContainingOrderBySortIdAsc(
			Long cityId, String keywords, int page, int size) {
		if (null == cityId || null == keywords) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository
				.findByCityIdAndTitleContainingOrCityIdAndGoodsTitleContainingOrCityIdAndSkuContainingOrderBySortIdAsc(
						cityId, keywords, cityId, keywords, cityId, keywords, pageRequest);
	}

	/**
	 * 根据关键词查找模板
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月20日下午5:06:20
	 * @param
	 */
	public Page<TdCouponModule> findByTitleContainingOrGoodsTitleContainingOrSkuContainingOrderBySortIdAsc(
			String keywords, int page, int size) {
		if (null == keywords) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByTitleContainingOrGoodsTitleContainingOrSkuContainingOrderBySortIdAsc(keywords, keywords,
				keywords, pageRequest);
	}

	/**
	 * 根据商品id和城市sobid查找模板
	 * 
	 * @author DengXiao
	 */
	public TdCouponModule findByGoodsIdAndCityId(Long goodsId, Long cityId) {
		if (null == goodsId || null == cityId) {
			return null;
		}
		return repository.findByGoodsIdAndCityId(goodsId, cityId);
	}

	public TdCouponModule findByGoodsIdAndCityIdAndType(Long goodsId, Long cityId, Long type) {
		if (null == goodsId || null == cityId || null == type) {
			return null;
		}
		return repository.findByGoodsIdAndCityIdAndType(goodsId, cityId, type);
	}

	public TdCouponModule findByGoodsIdAndCityTitle(Long goodsId, String cityTitle) {
		if (null == goodsId || null == cityTitle) {
			return null;
		}
		return repository.findByGoodsIdAndCityTitle(goodsId, cityTitle);
	}
}
