package com.ynyes.fitment.foundation.service.impl;

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
		return this.fitPriceLineRepo.findByHeaderId(headerId, this.initPage(page, size));
	}

}
