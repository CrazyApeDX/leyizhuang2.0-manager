package com.ynyes.fitment.foundation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitOrderRefundGoods;
import com.ynyes.fitment.foundation.repo.FitOrderRefundGoodsRepo;
import com.ynyes.fitment.foundation.service.FitOrderRefundGoodsService;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.repository.TdGoodsRepo;

@Service
@Transactional
public class FitOrderRefundGoodsServiceImpl implements FitOrderRefundGoodsService {

	@Autowired
	private FitOrderRefundGoodsRepo fitOrderRefundGoodsRepo;

	@Autowired
	private TdGoodsRepo tdGoodsRepo;

	@Override
	public FitOrderRefundGoods save(FitOrderRefundGoods orderRefundGoods) throws Exception {
		if (null == orderRefundGoods) {
			return null;
		}
		return this.fitOrderRefundGoodsRepo.save(orderRefundGoods);
	}

	@Override
	public FitOrderRefundGoods init(Long goodsId, Long quantity, Double unit) throws Exception {
		FitOrderRefundGoods orderRefundGoods = new FitOrderRefundGoods();

		TdGoods goods = this.tdGoodsRepo.findOne(goodsId);

		orderRefundGoods.setGoodsId(goods.getId());
		orderRefundGoods.setGoodsTitle(goods.getTitle());
		orderRefundGoods.setGoodsSku(goods.getCode());
		orderRefundGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
		orderRefundGoods.setQuantity(quantity);
		orderRefundGoods.setPrice(unit);
		orderRefundGoods.setRealPrice(unit);
		orderRefundGoods.setTotalPrice(orderRefundGoods.getPrice() * orderRefundGoods.getQuantity());
		orderRefundGoods.setRealTotalPrice(orderRefundGoods.getRealPrice() * orderRefundGoods.getQuantity());
		return this.fitOrderRefundGoodsRepo.save(orderRefundGoods);
	}

}
