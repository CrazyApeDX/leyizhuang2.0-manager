package com.ynyes.fitment.foundation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitOrderCancelGoods;
import com.ynyes.fitment.foundation.repo.FitOrderCancelGoodsRepo;
import com.ynyes.fitment.foundation.service.FitOrderCancelGoodsService;

@Service
@Transactional
public class FitOrderCancelGoodsServiceImpl implements FitOrderCancelGoodsService {

	@Autowired
	private FitOrderCancelGoodsRepo repository;
	
	@Override
	public FitOrderCancelGoods save(FitOrderCancelGoods goods) throws Exception {
		if (null == goods) {
			return null;
		}
		return repository.save(goods);
	}

}
