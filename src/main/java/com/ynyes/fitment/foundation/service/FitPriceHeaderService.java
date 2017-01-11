package com.ynyes.fitment.foundation.service;

import java.util.List;

import com.ynyes.fitment.foundation.entity.FitPriceHeader;

public interface FitPriceHeaderService {

	List<FitPriceHeader> findActivePriceHeaderByProductType(String productType) throws Exception;
}
