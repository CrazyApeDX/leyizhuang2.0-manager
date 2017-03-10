package com.ynyes.fitment.foundation.service.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitCompanyCategory;
import com.ynyes.fitment.foundation.entity.FitCompanyGoods;
import com.ynyes.fitment.foundation.entity.client.ClientCategory;
import com.ynyes.fitment.foundation.entity.client.ClientGoods;
import com.ynyes.fitment.foundation.service.FitCompanyCategoryService;
import com.ynyes.fitment.foundation.service.FitCompanyGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizInventoryService;
import com.ynyes.fitment.foundation.service.biz.BizPriceService;

@Service
@Transactional
public class BizGoodsServiceImpl implements BizGoodsService {

	@Autowired
	FitCompanyCategoryService fitCompanyCategoryService;

	@Autowired
	FitCompanyGoodsService fitCompanyGoodsService;

	@Autowired
	BizInventoryService bizInventoryService;

	@Autowired
	BizPriceService bizPriceService;

	/**
	 * 获取客户端商品分类树
	 * 
	 * @author yanle
	 */
	@Override
	public List<ClientCategory> getCategoryTree(Long companyId) throws Exception {
		if (null == companyId) {
			return null;
		}

		/*
		 * 这里存在两个有问题的写法 1. 能直接查出来一级分类就直接查出来，不要查所有的再遍历去找一级分类，这个对于IO，和内存是不必要的操作 2.
		 * 在遍历对比的代码中，categoryList.get(i).getCategoryParentId() ==
		 * 0，因为当前的ID的类型为Long而不是long 包装类型的比较一定要用equals()，如果一定要使用==，可以把这个例子改成：
		 * categoryList.get(i).getCategoryParentId().longValue == 0l -->
		 * long类型的比较也要使用long类型来比较
		 */

		// 设置一级分类的集合
		// List<FitCompanyCategory> categoryList =
		// fitCompanyCategoryService.findByCompanyIdOrderByCategorySortIdAsc(companyId);
		// for (int i = 0; i < categoryList.size(); i++) {
		// if (categoryList.get(i).getCategoryParentId() == 0) {
		// categoryList.remove(i);
		// }
		// }

		List<FitCompanyCategory> categoryList = this.fitCompanyCategoryService
				.findByCompanyIdAndCategoryParentIdOrderByIdAsc(companyId, 0l);

		// 将原始的FitCompanyCategory集合转化为客户端需要的ClientCategory集合
		List<ClientCategory> clientCategoryList = new ArrayList<ClientCategory>();

		/*
		 * 尽量不要写重复的代码 还要考虑精简代码
		 */
		for (FitCompanyCategory fitCompanyCategory : categoryList) {
			List<FitCompanyCategory> subCompanyCategory = this.fitCompanyCategoryService
					.findByCompanyIdAndCategoryParentIdOrderByIdAsc(companyId, fitCompanyCategory.getCategoryId());
			clientCategoryList.add(new ClientCategory().init(fitCompanyCategory, subCompanyCategory));
			// ClientCategory clientCategory = new ClientCategory();
			// clientCategory.setId(fitCompanyCategory.getCategoryId());
			// clientCategory.setTitle(fitCompanyCategory.getCategoryTitle());
			// if (null == fitCompanyCategory.getCategoryParentId() ||
			// fitCompanyCategory.getCategoryParentId() == 0) {
			// List<FitCompanyCategory> subCategoryList =
			// fitCompanyCategoryService
			// .findByCompanyIdAndCategoryParentIdOrderByIdAsc(fitCompanyCategory.getCompanyId(),
			// fitCompanyCategory.getCategoryParentId());
			// List<ClientCategory> subClientCategoryList = new
			// ArrayList<ClientCategory>();
			// for (FitCompanyCategory subFitCompanyCategory : subCategoryList)
			// {
			// ClientCategory subClientCategory = new ClientCategory();
			// subClientCategory.setId(subFitCompanyCategory.getCategoryId());
			// subClientCategory.setTitle(subFitCompanyCategory.getCategoryTitle());
			// subClientCategoryList.add(subClientCategory);
			// }
			// clientCategory.setChildren(subClientCategoryList);
			// }
			// clientCategoryList.add(clientCategory);
		}
		return clientCategoryList;
	}

	/**
	 * 获取客户端商品展示零售价 auth yanle
	 */
	@Override
	public List<ClientGoods> getGoodsByCategoryId(Long categoryId, Long companyId) throws Exception {
		List<FitCompanyGoods> fitGoodsList = fitCompanyGoodsService
				.findByCompanyIdAndCategoryIdOrderByGoodsSortIdAsc(companyId, categoryId);
		List<ClientGoods> clientGoodsList = new ArrayList<ClientGoods>();
		for (FitCompanyGoods fitCompanyGoods : fitGoodsList) {
			Long inventory = bizInventoryService.getCityInventoryByGoodsId(companyId, fitCompanyGoods.getGoodsId());
			Double price = bizPriceService.getPriceByCompanyIdAndGoodsId(companyId, fitCompanyGoods.getGoodsId());
			if (null == inventory || inventory.equals(0l)) {
				// do nothing!
			} else if (null == price || price.equals(0d)) {
				// do nothing!
			} else {
				clientGoodsList.add(new ClientGoods().init(fitCompanyGoods, price, inventory));
			}
		}

		// for (FitCompanyGoods fitCompanyGoods : fitGoodsList) {
		// ClientGoods clientGoods = new ClientGoods();
		// clientGoods.setId(fitCompanyGoods.getGoodsId());
		// clientGoods.setSku(fitCompanyGoods.getGoodsSku());
		// clientGoods.setTitle(fitCompanyGoods.getGoodsTitle());
		// Long inventory =
		// bizInventoryService.getCityInventoryByGoodsId(companyId,
		// fitCompanyGoods.getGoodsId());
		// clientGoods.setInventory(inventory);
		// Double price =
		// bizPriceService.getPriceByCompanyIdAndGoodsId(companyId,
		// fitCompanyGoods.getGoodsId());
		// clientGoods.setPrice(price);
		// clientGoodsList.add(clientGoods);
		// }
		return clientGoodsList;
	}

	@Override
	public List<ClientGoods> getGoodsByCompanyIdAndKeywords(Long companyId, String keywords) throws Exception {
		List<FitCompanyGoods> goodsList = this.fitCompanyGoodsService.findByCompanyIdAndKeywords(companyId, keywords);
		List<ClientGoods> clientGoodsList = new ArrayList<ClientGoods>();
		if (null != goodsList && goodsList.size() > 0) {
			for (FitCompanyGoods fitCompanyGoods : goodsList) {
				Long inventory = bizInventoryService.getCityInventoryByGoodsId(companyId, fitCompanyGoods.getGoodsId());
				Double price = bizPriceService.getPriceByCompanyIdAndGoodsId(companyId, fitCompanyGoods.getGoodsId());
				if (null == inventory || inventory.equals(0l)) {
					// do nothing!
				} else if (null == price || price.equals(0d)) {
					// do nothing!
				} else {
					clientGoodsList.add(new ClientGoods().init(fitCompanyGoods, price, inventory));
				}
			}
		}
		return clientGoodsList;
	}

}
