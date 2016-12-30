package com.ynyes.fitment.foundation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitCompanyGoods;
import com.ynyes.fitment.foundation.entity.FitPriceLine;
import com.ynyes.fitment.foundation.entity.client.ClientCompanyGoods;
import com.ynyes.fitment.foundation.repo.FitCompanyGoodsRepo;
import com.ynyes.fitment.foundation.service.FitCompanyGoodsService;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitPriceService;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.service.TdDiySiteInventoryService;

@Service
@Transactional
public class FitCompanyGoodsServiceImpl implements FitCompanyGoodsService {

	@Autowired
	private FitCompanyGoodsRepo fitCompanyGoodsRepo;

	@Autowired
	private FitPriceService fitPriceService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	private FitCompanyService fitCompanyService;

	@Override
	public List<ClientCompanyGoods> getGoodsByCategoryIdAndComapnyId(Long categoryId, Long companyId) throws Exception {
		FitCompany company = fitCompanyService.findOne(companyId);

		List<ClientCompanyGoods> goodsList = new ArrayList<>();
		List<FitCompanyGoods> fitCompanyGoodsList = fitCompanyGoodsRepo
				.findByCompanyIdAndCategoryIdOrderByGoodsSortIdAsc(companyId, categoryId);
		if (!CollectionUtils.isEmpty(fitCompanyGoodsList)) {
			for (FitCompanyGoods goods : fitCompanyGoodsList) {

				FitPriceLine line = fitPriceService.getGoodsPrice(company, goods.getId());
				TdDiySiteInventory inventory = tdDiySiteInventoryService
						.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(goods.getGoodsSku(), company.getSobId());

				ClientCompanyGoods client = new ClientCompanyGoods();
				client.init(goods, inventory, line);
				goodsList.add(client);
			}
		}
		return goodsList;
	}

}
