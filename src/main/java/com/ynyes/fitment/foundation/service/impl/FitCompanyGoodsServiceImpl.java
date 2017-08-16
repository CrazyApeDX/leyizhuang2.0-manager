package com.ynyes.fitment.foundation.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity.OriginType;
import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitCompanyGoods;
import com.ynyes.fitment.foundation.entity.FitPriceLine;
import com.ynyes.fitment.foundation.repo.FitCompanyGoodsRepo;
import com.ynyes.fitment.foundation.repo.FitCompanyRepo;
import com.ynyes.fitment.foundation.repo.FitPriceLineRepo;
import com.ynyes.fitment.foundation.service.FitCompanyGoodsService;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdGoodsService;

@Service
@Transactional
public class FitCompanyGoodsServiceImpl extends PageableService implements FitCompanyGoodsService {

	@Autowired
	private FitCompanyGoodsRepo fitCompanyGoodsRepo;

	@Autowired
	private FitCompanyRepo fitCompanyRepo;

	@Autowired
	private FitPriceLineRepo fitPriceLineRepo;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	private TdCityService tdCityService;

	@Override
	public Page<FitCompanyGoods> findByCompanyIdOrderByGoodsSortIdAsc(Long companyId, Integer page, Integer size)
			throws Exception {
		if (null == companyId) {
			return null;
		}
		return this.fitCompanyGoodsRepo.findByCompanyIdOrderByGoodsSortIdAsc(companyId, this.initPage(page, size));
	}

	@Override
	public Page<FitCompanyGoods> findAll(Integer page, Integer size) throws Exception {
		return this.fitCompanyGoodsRepo.findAll(this.initPage(page, size));
	}

	@Override
	public FitCompanyGoods managerAddCompanyGoods(TdGoods goods, Long companyId) throws Exception {
		if (null == goods || null == companyId) {
			return null;
		} else {
			FitCompanyGoods companyGoods = new FitCompanyGoods();
			companyGoods.setCompanyId(companyId);
			companyGoods.setGoodsId(goods.getId());
			companyGoods.setGoodsTitle(goods.getTitle());
			companyGoods.setGoodsSku(goods.getCode());
			companyGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
			companyGoods.setCategoryId(goods.getCategoryId());
			companyGoods.setGoodsSortId(null == goods.getSortId() ? 99.9d : goods.getSortId());
			companyGoods.setCreateOrigin(OriginType.ADD);
			companyGoods.setBrandId(goods.getBrandId());
			companyGoods.setBrandTitle(goods.getBrandTitle());
			return this.fitCompanyGoodsRepo.save(companyGoods);
		}
	}

	@Override
	public Boolean validateRepeatByCompanyIdAndGoodsId(Long companyId, Long goodsId) throws Exception {
		if (null == companyId || null == goodsId) {
			return true;
		} else {
			Long count = this.fitCompanyGoodsRepo.countByCompanyIdAndGoodsId(companyId, goodsId);
			return (count > 0);
		}
	}

	@Override
	public void delete(Long id) throws Exception {
		if (null != id) {
			this.fitCompanyGoodsRepo.delete(id);
		}
	}

	@Override
	public List<FitCompanyGoods> findByCompanyIdAndCategoryIdOrderByGoodsSortIdAsc(Long companyId, Long categoryId)
			throws Exception {
		if (null == companyId || null == categoryId) {
			return null;
		}
		return this.fitCompanyGoodsRepo.findByCompanyIdAndCategoryIdOrderByGoodsSortIdAsc(companyId, categoryId);
	}

	@Override
	public List<FitCompanyGoods> findByCompanyIdAndKeywords(Long companyId, String keywords) {
		if (null == companyId || StringUtils.isEmpty(keywords)) {
			return null;
		}
		return this.fitCompanyGoodsRepo.findByCompanyIdAndKeywords(companyId, keywords);
	}

	@Override
	public void initCompanyGoodsByPriceLine(Long companyId) throws Exception {
		FitCompany company = this.fitCompanyRepo.findOne(companyId);
		this.initCompanyGoods(companyId, company.getLsPriceHeaderId());
		this.initCompanyGoods(companyId, company.getLyzPriceHeaderId());
		this.initCompanyGoods(companyId, company.getYrPriceHeaderId());
		this.initCompanyGoods(companyId, company.getXqPriceHeaderId());
	}

	private void initCompanyGoods(Long companyId, Long priceHeaderId) throws Exception {
		List<FitPriceLine> priceLineList = this.fitPriceLineRepo.findByHeaderId(priceHeaderId);
		if (null != priceLineList && priceLineList.size() > 0) {
			for (FitPriceLine line : priceLineList) {
				if (line.getGoodsSku().equals("SJJZF-40") || line.getGoodsSku().equals("DTDQ01-8MM")) {
					System.out.println("进入了");
				}
				Long goodsId = line.getGoodsId();
				// if (!this.validateRepeatByCompanyIdAndGoodsId(companyId,
				// goodsId)) {
				FitCompanyGoods companyGoods = this.findByCompanyIdAndGoodsId(companyId, goodsId);
				companyGoods = null == companyGoods ? new FitCompanyGoods() : companyGoods;
				TdGoods goods = this.tdGoodsService.findOne(goodsId);
				companyGoods.setCompanyId(companyId);
				companyGoods.setGoodsId(goods.getId());
				companyGoods.setGoodsTitle(goods.getTitle());
				companyGoods.setGoodsSku(goods.getCode());
				companyGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
				companyGoods.setCategoryId(goods.getCategoryId());
				companyGoods.setGoodsSortId(goods.getSortId());
				companyGoods.setBrandId(goods.getBrandId());
				companyGoods.setBrandTitle(goods.getBrandTitle());
				this.fitCompanyGoodsRepo.save(companyGoods);
			}
			// }
		}

	}

	@Override
	public FitCompanyGoods findByCompanyIdAndGoodsId(Long companyId, Long goodsId) throws Exception {
		if (null == companyId || null == goodsId) {
			return null;
		}
		return this.fitCompanyGoodsRepo.findByCompanyIdAndGoodsId(companyId, goodsId);
	}

	@Override
	public void initInventoryByCompanyId(Long companyId) throws Exception {
		FitCompany company = fitCompanyRepo.findOne(companyId);
		List<FitCompanyGoods> goodsList = this.fitCompanyGoodsRepo.findByCompanyId(companyId);
		if (null != goodsList && goodsList.size() > 0) {
			for (FitCompanyGoods companyGoods : goodsList) {
				Long goodsId = companyGoods.getGoodsId();
				TdGoods goods = tdGoodsService.findOne(goodsId);
				TdDiySiteInventory inventory = tdDiySiteInventoryService
						.findByGoodsIdAndRegionIdAndDiySiteIdIsNull(goodsId, company.getSobId());
				if (null == inventory) {
					inventory = new TdDiySiteInventory();
					inventory.setRegionId(company.getSobId());
					inventory.setRegionName(tdCityService.findBySobIdCity(company.getSobId()).getCityName());
					inventory.setGoodsId(goods.getId());
					inventory.setGoodsTitle(goods.getTitle());
					inventory.setGoodsCode(goods.getCode());
					inventory.setInventory(0L);
					inventory.setCategoryId(goods.getCategoryId());
					inventory.setCategoryTitle(goods.getCategoryTitle());
					inventory.setCategoryIdTree(goods.getCategoryIdTree());
					this.tdDiySiteInventoryService.save(inventory);
				}
			}
		}
	}

}
