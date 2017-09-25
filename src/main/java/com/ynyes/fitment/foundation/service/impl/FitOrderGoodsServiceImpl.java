package com.ynyes.fitment.foundation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitOrderGoods;
import com.ynyes.fitment.foundation.repo.FitOrderGoodsRepo;
import com.ynyes.fitment.foundation.service.FitOrderGoodsService;

@Service
@Transactional
public class FitOrderGoodsServiceImpl implements FitOrderGoodsService {

	@Autowired
	private FitOrderGoodsRepo fitOrderGoodsRepo;
	
	@Override
	public FitOrderGoods save(FitOrderGoods orderGoods) throws Exception {
		if (null == orderGoods) {
			return null;
		}
		return this.fitOrderGoodsRepo.save(orderGoods);
	}

}
