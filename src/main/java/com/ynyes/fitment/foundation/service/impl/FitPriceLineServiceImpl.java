package com.ynyes.fitment.foundation.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitPriceLine;
import com.ynyes.fitment.foundation.repo.FitPriceLineRepo;
import com.ynyes.fitment.foundation.service.FitPriceLineService;

@Service
@Transactional
public class FitPriceLineServiceImpl extends PageableService implements FitPriceLineService {

	@Autowired
	private FitPriceLineRepo fitPriceLineRepo;

	@Override
	public Page<FitPriceLine> findByHeaderId(Long headerId, Integer page, Integer size) throws Exception {
		if (null == headerId) {
			return null;
		}
		return this.fitPriceLineRepo.findByHeaderId(headerId, this.initPage(page, size));
	}

	@Override
	public FitPriceLine findByHeaderIdAndGoodsId(Long headerId, Long goodsId) throws Exception {
		if (null == headerId || null == goodsId) {
			return null;
		}
		Date now = new Date();
		return this.fitPriceLineRepo.findByHeaderIdAndGoodsIdAndStartTimeBeforeAndEndTimeAfter(headerId, goodsId, now,
				now);
	}

	@Override
	public FitPriceLine findByEbsId(Long ebsId) {
		if (null == ebsId) {
			return null;
		} else {
			return this.fitPriceLineRepo.findByEbsId(ebsId);
		}
	}

	@Override
	public FitPriceLine save(FitPriceLine line) {
		if (null == line) {
			return null;
		} else {
			return this.fitPriceLineRepo.save(line);
		}
	}

}
