package com.ynyes.fitment.foundation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitCartGoods;
import com.ynyes.fitment.foundation.repo.FitCartGoodsRepo;

@Service
@Transactional
public class FitCartGoodsServiceImpl implements com.ynyes.fitment.foundation.service.FitCartGoodsService {

	@Autowired
	private FitCartGoodsRepo fitCartGoodsRepo;

	@Override
	public FitCartGoods save(FitCartGoods cartGoods) throws Exception {
		if (null == cartGoods) {
			return null;
		}
		return this.fitCartGoodsRepo.save(cartGoods);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		if (null != id) {
			this.fitCartGoodsRepo.delete(id);
		}
	}

	@Override
	public List<FitCartGoods> findByEmployeeId(Long employeeId) throws Exception {
		if (null == employeeId) {
			return null;
		}
		return this.fitCartGoodsRepo.findByEmployeeId(employeeId);
	}

	@Override
	public FitCartGoods findByEmployeeIdAndGoodsId(Long employeeId, Long goodsId) throws Exception {
		if (null == employeeId || null == goodsId) {
			return null;
		}
		return this.fitCartGoodsRepo.findByEmployeeIdAndGoodsId(employeeId, goodsId);
	}

	@Override
	public Long countByEmployeeId(Long employeeId) throws Exception {
		if (null == employeeId) {
			return null;
		}
		return this.fitCartGoodsRepo.countByEmployeeId(employeeId);
	}

	@Override
	public void deleteByGoodsIdAndEmployeeId(Long goodsId, Long employeeId) throws Exception {
		if (null != goodsId && null != employeeId) {
			this.fitCartGoodsRepo.deleteByGoodsIdAndEmployeeId(goodsId, employeeId);
		}
	}

	@Override
	public Double getTotalCartGoodsPriceByEmployeeId(Long employeeId) throws Exception {
		if (null == employeeId) {
			return null;
		}
		return this.fitCartGoodsRepo.getTotalCartGoodsPriceByEmployeeId(employeeId);
	}

}
