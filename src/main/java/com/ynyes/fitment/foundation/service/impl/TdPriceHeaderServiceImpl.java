package com.ynyes.fitment.foundation.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitPriceHeader;
import com.ynyes.fitment.foundation.repo.FitPriceHeaderRepo;
import com.ynyes.fitment.foundation.service.FitPriceHeaderService;

@Service
@Transactional
public class TdPriceHeaderServiceImpl implements FitPriceHeaderService {

	@Autowired
	private FitPriceHeaderRepo fitPriceHeaderRepo;
	
	@Override
	public List<FitPriceHeader> findActivePriceHeaderByProductType(String productType) throws Exception {
		return this.fitPriceHeaderRepo.findActivePriceHeaderByPriceType(productType, new Date());
	}

}
