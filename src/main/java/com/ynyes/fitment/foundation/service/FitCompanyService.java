package com.ynyes.fitment.foundation.service;

import com.ynyes.fitment.foundation.entity.FitCompany;

public interface FitCompanyService {
	
	FitCompany findOne(Long companyId) throws Exception;
}
