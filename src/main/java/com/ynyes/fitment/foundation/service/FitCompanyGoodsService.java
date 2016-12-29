package com.ynyes.fitment.foundation.service;

import java.util.List;

import com.ynyes.fitment.foundation.entity.client.ClientCompanyGoods;

public interface FitCompanyGoodsService {

	List<ClientCompanyGoods> getGoodsByCategoryIdAndComapnyId(Long categoryId, Long companyId) throws Exception;
}
