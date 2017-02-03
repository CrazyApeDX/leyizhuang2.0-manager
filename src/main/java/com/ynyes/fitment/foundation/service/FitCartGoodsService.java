package com.ynyes.fitment.foundation.service;

import java.util.List;

import com.ynyes.fitment.foundation.entity.FitCartGoods;

public interface FitCartGoodsService {

	FitCartGoods save(FitCartGoods cartGoods) throws Exception;
	
	void deleteById(Long id) throws Exception;
	
	List<FitCartGoods> findByEmployeeId(Long employeeId) throws Exception;
	
	FitCartGoods findByEmployeeIdAndGoodsId(Long employeeId, Long goodsId) throws Exception;
	
	Long countByEmployeeId(Long employeeId) throws Exception;
	
	void deleteByGoodsIdAndEmployeeId(Long goodsId, Long employeeId) throws Exception;
	
	Double getTotalCartGoodsPriceByEmployeeId(Long employeeId) throws Exception;
}
