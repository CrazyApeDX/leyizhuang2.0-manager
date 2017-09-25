package com.ynyes.fitment.foundation.service.biz;

import com.ynyes.fitment.foundation.entity.FitPriceLine;

public interface BizPriceService {

	FitPriceLine getFitPriceLineByCompanyIdAndGoodsId(Long companyId, Long goodsId) throws Exception;
	
	Double getPriceByCompanyIdAndGoodsId(Long companyId, Long goodsId) throws Exception;
	
	Double getRealPriceByCompanyIdAndGoodsId(Long companyId, Long goodsId) throws Exception;
}
