package com.ynyes.fitment.foundation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity.OriginType;
import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitCompanyGoods;
import com.ynyes.fitment.foundation.repo.FitCompanyGoodsRepo;
import com.ynyes.fitment.foundation.service.FitCompanyGoodsService;
import com.ynyes.lyz.entity.TdGoods;

@Service
@Transactional
public class FitCompanyGoodsServiceImpl extends PageableService implements FitCompanyGoodsService {

	@Autowired
	private FitCompanyGoodsRepo fitCompanyGoodsRepo;

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
		if(null == companyId || null == categoryId){
			return null;
		}
		return this.fitCompanyGoodsRepo.findByCompanyIdAndCategoryIdOrderByGoodsSortIdAsc(companyId, categoryId);
	}

}
