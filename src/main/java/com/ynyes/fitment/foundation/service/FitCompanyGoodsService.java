package com.ynyes.fitment.foundation.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.foundation.entity.FitCompanyGoods;
import com.ynyes.lyz.entity.TdGoods;

public interface FitCompanyGoodsService {

	Page<FitCompanyGoods> findByCompanyIdOrderByGoodsSortIdAsc(Long companyId, Integer page, Integer size) throws Exception;
	
	Page<FitCompanyGoods> findAll(Integer page, Integer size) throws Exception;
	
	FitCompanyGoods managerAddCompanyGoods(TdGoods goods, Long companyId) throws Exception;
	
	Boolean validateRepeatByCompanyIdAndGoodsId(Long companyId, Long goodsId) throws Exception;
	
	void delete(Long id) throws Exception;
	
	List<FitCompanyGoods> findByCompanyIdAndCategoryIdOrderByGoodsSortIdAsc(Long companyId, Long categoryId)
			throws Exception;
}
