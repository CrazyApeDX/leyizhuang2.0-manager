package com.ynyes.fitment.foundation.service.biz;

import java.util.List;

import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.foundation.entity.FitCartGoods;
import com.ynyes.fitment.foundation.entity.FitEmployee;

public interface BizCartGoodsService {

	List<FitCartGoods> loadEmployeeCart(Long employeeId) throws Exception;

	void addToCart(String params, FitEmployee employee) throws Exception;

	void clearCartWithId(Long id) throws Exception;

	Long getSelectedCounts(Long employeeId) throws Exception;

	FitCartGoods getCartGoodsByGoodsId(Long employeeId, Long goodsId) throws Exception;

	void changeCartGoodsQuantity(String operation, FitCartGoods cart, Long quantity, Long inventory) throws Exception;

	ClientResult deleteCartGoods(Long employeeId, Long goodsId) throws Exception;
}
