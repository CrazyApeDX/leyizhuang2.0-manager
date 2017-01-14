package com.ynyes.fitment.foundation.service.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	 * auth yanle
	 */
	@Override
	public List<ClientCategory> getCategoryTree(Long companyId) throws Exception {
		if(null == companyId){
			return null;
		}
		
		//设置一级分类的集合
		List<FitCompanyCategory> categoryList = fitCompanyCategoryService.findByCompanyIdOrderByCategorySortIdAsc(companyId);
		for (int i = 0; i < categoryList.size(); i++) {
			if(categoryList.get(i).getCategoryParentId()==0){
				categoryList.remove(i);
			}
		}
		
		//将原始的FitCompanyCategory集合转化为客户端需要的ClientCategory集合
		List<ClientCategory> clientCategoryList = new ArrayList<ClientCategory>();
		
		for (FitCompanyCategory fitCompanyCategory : categoryList) {
			ClientCategory clientCategory = new ClientCategory();
			clientCategory.setId(fitCompanyCategory.getCategoryId());
			clientCategory.setTitle(fitCompanyCategory.getCategoryTitle());
			if(null == fitCompanyCategory.getCategoryParentId() || fitCompanyCategory.getCategoryParentId()==0 ){
				List<FitCompanyCategory> subCategoryList = fitCompanyCategoryService.findByCompanyIdAndCategoryParentIdOrderByIdAsc(fitCompanyCategory.getCompanyId(),fitCompanyCategory.getCategoryParentId());
				List<ClientCategory> subClientCategoryList = new ArrayList<ClientCategory>();
				for (FitCompanyCategory subFitCompanyCategory : subCategoryList) {
					ClientCategory subClientCategory = new ClientCategory();
					subClientCategory.setId(subFitCompanyCategory.getCategoryId());
					subClientCategory.setTitle(subFitCompanyCategory.getCategoryTitle());
					subClientCategoryList.add(subClientCategory);
				}
				clientCategory.setChildren(subClientCategoryList);
			}
			clientCategoryList.add(clientCategory);
		}
		
		return clientCategoryList;
	}
	
	/**
	 * 获取客户端商品展示零售价
	 * auth yanle
	 */
	@Override
	public List<ClientGoods> getGoodsByCategoryId(Long categoryId,Long companyId) throws Exception {
		List<FitCompanyGoods> fitGoodsList = fitCompanyGoodsService.findByCompanyIdAndCategoryIdOrderByGoodsSortIdAsc(companyId,categoryId);
		List<ClientGoods> clientGoodsList = new ArrayList<ClientGoods>();
		for (FitCompanyGoods fitCompanyGoods : fitGoodsList) {
			ClientGoods clientGoods = new ClientGoods();
			clientGoods.setId(fitCompanyGoods.getGoodsId());
			clientGoods.setSku(fitCompanyGoods.getGoodsSku());
			clientGoods.setTitle(fitCompanyGoods.getGoodsTitle());
			Long inventory = bizInventoryService.getCityInventoryByGoodsId(companyId,fitCompanyGoods.getGoodsId());
			clientGoods.setInventory(inventory);
			Double price = bizPriceService.getPriceByCompanyIdAndGoodsId(companyId,fitCompanyGoods.getGoodsId());
			clientGoods.setPrice(price);
			clientGoodsList.add(clientGoods);
		}
		
		return clientGoodsList;
	}
	

}
