package com.ynyes.fitment.foundation.service;

import com.ynyes.fitment.foundation.entity.FitOrderRefundGoods;

public interface FitOrderRefundGoodsService {

	FitOrderRefundGoods save(FitOrderRefundGoods orderRefundGoods) throws Exception;

	FitOrderRefundGoods init(Long goodsId, Long quantity, Double unit) throws Exception;
}
