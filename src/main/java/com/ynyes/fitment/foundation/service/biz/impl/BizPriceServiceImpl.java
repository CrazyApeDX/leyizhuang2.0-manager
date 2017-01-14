package com.ynyes.fitment.foundation.service.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitPriceLine;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitPriceLineService;
import com.ynyes.fitment.foundation.service.biz.BizPriceService;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.service.TdGoodsService;

@Service
@Transactional
public class BizPriceServiceImpl implements BizPriceService {

	@Autowired
	private FitCompanyService fitCompanyService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private FitPriceLineService fitPriceLineService;

	@Override
	public FitPriceLine getFitPriceLineByCompanyIdAndGoodsId(Long companyId, Long goodsId) throws Exception {

		FitCompany company = this.fitCompanyService.findOne(companyId);
		if (null == company) {
			return null;
		} else {
			TdGoods goods = tdGoodsService.findOne(goodsId);
			Long headerId = null;
			if ("HR".equalsIgnoreCase(goods.getProductFlag())) {
				headerId = company.getLsPriceHeaderId();
			} else if ("LYZ".equalsIgnoreCase(goods.getProductFlag())) {
				headerId = company.getLyzPriceHeaderId();
			} else if ("YR".equalsIgnoreCase(goods.getProductFlag())) {
				headerId = company.getYrPriceHeaderId();
			}
			return this.fitPriceLineService.findByHeaderIdAndGoodsId(headerId, goodsId);
		}
	}

	@Override
	public Double getPriceByCompanyIdAndGoodsId(Long companyId, Long goodsId) throws Exception {
		FitPriceLine line = this.getFitPriceLineByCompanyIdAndGoodsId(companyId, goodsId);
		if (null == line) {
			return null;
		} else {
			return line.getPrice();
		}
	}

	@Override
	public Double getRealPriceByCompanyIdAndGoodsId(Long companyId, Long goodsId) throws Exception {
		FitPriceLine line = this.getFitPriceLineByCompanyIdAndGoodsId(companyId, goodsId);
		if (null == line) {
			return null;
		} else {
			return line.getRealPrice();
		}
	}

}
