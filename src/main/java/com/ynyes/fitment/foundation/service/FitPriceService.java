package com.ynyes.fitment.foundation.service;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitPriceHeader;
import com.ynyes.fitment.foundation.entity.FitPriceLine;
import com.ynyes.lyz.entity.TdGoods;

public interface FitPriceService {

	FitPriceHeader getPriceHeaderByCompanyAndGoodsBrand(FitCompany company, String brandTitle) throws Exception;
	
	FitPriceLine getPriceLineByHeaderIdAndGoodsId(Long headerId, Long goodsId) throws Exception;
	
	Boolean validatePriceEffective(FitPriceLine priceLine) throws Exception;
	
	FitPriceLine getGoodsPrice(Long companyId, Long goodsId) throws Exception;
	
	FitPriceLine getGoodsPrice(FitCompany company, Long goodsId) throws Exception;
	
	FitPriceLine getGoodsPrice(Long companyId, TdGoods goods) throws Exception;
	
	FitPriceLine getGoodsPrice(FitCompany company, TdGoods goods) throws Exception;
}
