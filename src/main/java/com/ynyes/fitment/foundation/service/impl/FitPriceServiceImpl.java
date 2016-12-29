package com.ynyes.fitment.foundation.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.exception.InvalidPriceException;
import com.ynyes.fitment.core.exception.UndefinedConditionException;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitPriceHeader;
import com.ynyes.fitment.foundation.entity.FitPriceLine;
import com.ynyes.fitment.foundation.repo.FitCompanyRepo;
import com.ynyes.fitment.foundation.repo.FitPriceHeaderRepo;
import com.ynyes.fitment.foundation.repo.FitPriceLineRepo;
import com.ynyes.fitment.foundation.service.FitPriceService;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.repository.TdGoodsRepo;

@Service
@Transactional
public class FitPriceServiceImpl implements FitPriceService {

	@Autowired
	private FitPriceHeaderRepo fitPriceHeaderRepo;

	@Autowired
	private FitPriceLineRepo fitPriceLineRepo;

	@Autowired
	private FitCompanyRepo fitCompanyRepo;

	@Autowired
	private TdGoodsRepo tdGoodsRepo;

	@Override
	public FitPriceHeader getPriceHeaderByCompanyAndGoodsBrand(FitCompany company, String brandTitle) throws Exception {
		Long priceHeaderId;
		switch (brandTitle) {
		case "华润":
			priceHeaderId = company.getLsPriceHeaderId();
			break;
		case "莹润":
			priceHeaderId = company.getYrPriceHeaderId();
			break;
		case "乐易装":
			priceHeaderId = company.getLyzPriceHeaderId();
			break;
		default:
			throw new UndefinedConditionException("商品品牌信息缺失");
		}
		FitPriceHeader priceHeader = fitPriceHeaderRepo.findOne(priceHeaderId);
		if (null == priceHeader) {
			throw new InvalidPriceException(brandTitle + "产品价目表信息缺失，请联系管理员");
		} else {
			Date now = new Date();
			if (priceHeader.getStartTime().getTime() > now.getTime()) {
				throw new InvalidPriceException(brandTitle + "产品价目表还未生效，请联系管理员");
			} else if (priceHeader.getEndTime().getTime() < now.getTime()) {
				throw new InvalidPriceException(brandTitle + "产品价目表已经过期，请联系管理员");
			} else {
				return priceHeader;
			}
		}
	}

	@Override
	public FitPriceLine getPriceLineByHeaderIdAndGoodsId(Long headerId, Long goodsId) throws Exception {
		FitPriceLine priceLine = fitPriceLineRepo.findByHeaderIdAndGoodsId(headerId, goodsId);
		// if (null == priceLine) {
		// throw new InvalidPriceException("商品(ID:" + goodsId +
		// ")价目表信息缺失，请联系管理员");
		// } else {
		// Date now = new Date();
		// if (priceLine.getStartTime().getTime() > now.getTime()) {
		// throw new InvalidPriceException("商品(ID:" + goodsId +
		// ")价目表还未生效，请联系管理员");
		// } else if (priceLine.getEndTime().getTime() < now.getTime()) {
		// throw new InvalidPriceException("商品(ID:" + goodsId +
		// ")价目表已经过期，请联系管理员");
		// } else {
		return priceLine;
		// }
		// }
	}

	@Override
	public Boolean validatePriceEffective(FitPriceLine priceLine) throws Exception {
		if (null == priceLine) {
			return false;
		} else {
			Date now = new Date();
			if (priceLine.getStartTime().getTime() > now.getTime()) {
				return false;
			} else if (priceLine.getEndTime().getTime() < now.getTime()) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public FitPriceLine getGoodsPrice(Long companyId, Long goodsId) throws Exception {
		FitCompany company = fitCompanyRepo.findOne(companyId);
		TdGoods goods = tdGoodsRepo.findOne(goodsId);
		FitPriceHeader priceHeader = this.getPriceHeaderByCompanyAndGoodsBrand(company, goods.getBrandTitle());
		FitPriceLine priceLine = this.getPriceLineByHeaderIdAndGoodsId(priceHeader.getId(), goodsId);
		return priceLine;
	}

	@Override
	public FitPriceLine getGoodsPrice(FitCompany company, Long goodsId) throws Exception {
		TdGoods goods = tdGoodsRepo.findOne(goodsId);
		FitPriceHeader priceHeader = this.getPriceHeaderByCompanyAndGoodsBrand(company, goods.getBrandTitle());
		FitPriceLine priceLine = this.getPriceLineByHeaderIdAndGoodsId(priceHeader.getId(), goodsId);
		return priceLine;
	}

	@Override
	public FitPriceLine getGoodsPrice(Long companyId, TdGoods goods) throws Exception {
		FitCompany company = fitCompanyRepo.findOne(companyId);
		FitPriceHeader priceHeader = this.getPriceHeaderByCompanyAndGoodsBrand(company, goods.getBrandTitle());
		FitPriceLine priceLine = this.getPriceLineByHeaderIdAndGoodsId(priceHeader.getId(), goods.getId());
		return priceLine;
	}

	@Override
	public FitPriceLine getGoodsPrice(FitCompany company, TdGoods goods) throws Exception {
		FitPriceHeader priceHeader = this.getPriceHeaderByCompanyAndGoodsBrand(company, goods.getBrandTitle());
		FitPriceLine priceLine = this.getPriceLineByHeaderIdAndGoodsId(priceHeader.getId(), goods.getId());
		return priceLine;
	}

}
