package com.ynyes.fitment.foundation.service;

import java.util.List;

import com.ynyes.fitment.foundation.entity.FitSalesManager;

public interface FitSalesManagerService {
	
	List<FitSalesManager> findByCityCode(String cityCode);

}
