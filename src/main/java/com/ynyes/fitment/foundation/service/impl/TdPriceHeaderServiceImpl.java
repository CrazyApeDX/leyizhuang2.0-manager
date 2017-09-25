package com.ynyes.fitment.foundation.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitPriceHeader;
import com.ynyes.fitment.foundation.repo.FitPriceHeaderRepo;
import com.ynyes.fitment.foundation.service.FitPriceHeaderService;

@Service
@Transactional
public class TdPriceHeaderServiceImpl extends PageableService implements FitPriceHeaderService {

	@Autowired
	private FitPriceHeaderRepo fitPriceHeaderRepo;

	@Override
	public FitPriceHeader findOne(Long id) throws Exception {
		if (null == id) {
			return null;
		}
		return this.fitPriceHeaderRepo.findOne(id);
	}

	@Override
	public List<FitPriceHeader> findActivePriceHeaderByProductType(String productType) throws Exception {
		return this.fitPriceHeaderRepo.findActivePriceHeaderByPriceType(productType, new Date());
	}

	@Override
	public Page<FitPriceHeader> findAll(Integer page, Integer size) throws Exception {
		return this.fitPriceHeaderRepo.findAll(this.initPage(page, size));
	}

	@Override
	public FitPriceHeader save(FitPriceHeader header) {
		if (null == header) {
			return null;
		} else {
			return this.fitPriceHeaderRepo.save(header);
		}
	}

	@Override
	public FitPriceHeader findByEbsId(Long ebsId) {
		if (null == ebsId) {
			return null;
		} else {
			return this.fitPriceHeaderRepo.findByEbsId(ebsId);
		}
	}

}
