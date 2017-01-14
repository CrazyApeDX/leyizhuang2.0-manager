package com.ynyes.fitment.foundation.service.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.biz.BizInventoryService;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdGoodsService;

@Service
@Transactional
public class BizInventoryServiceImpl implements BizInventoryService {

	@Autowired
	private FitCompanyService fitCompanyService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Override
	public Long getCityInventoryByGoodsId(Long companyId, Long goodsId) throws Exception {
		if (null == companyId || null == goodsId) {
			return null;
		}
		FitCompany company = this.fitCompanyService.findOne(companyId);
		if (null == company) {
			return null;
		}
		TdGoods goods = this.tdGoodsService.findOne(goodsId);
		if (null == goods) {
			return null;
		}
		TdDiySiteInventory inventory = this.tdDiySiteInventoryService
				.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(goods.getCode(), company.getId());
		return null == inventory ? null : inventory.getInventory();
	}
}
