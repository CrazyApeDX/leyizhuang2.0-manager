package com.ynyes.fitment.foundation.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.foundation.entity.FitPriceHeader;

public interface FitPriceHeaderService {

	List<FitPriceHeader> findActivePriceHeaderByProductType(String productType) throws Exception;
	
	Page<FitPriceHeader> findAll(Integer page, Integer size) throws Exception;
}
