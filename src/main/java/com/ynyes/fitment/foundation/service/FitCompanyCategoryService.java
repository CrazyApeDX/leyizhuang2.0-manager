package com.ynyes.fitment.foundation.service;

import java.util.List;

import com.ynyes.fitment.foundation.entity.FitCompanyCategory;

public interface FitCompanyCategoryService {
	
	FitCompanyCategory managerAdd(FitCompanyCategory category) throws Exception;
	
	List<FitCompanyCategory> findByCompanyIdOrderByCategorySortIdAsc(Long companyId) throws Exception;

	void deleteByCompanyId(Long companyId) throws Exception;
	
	List<FitCompanyCategory> findByCompanyIdAndCategoryParentIdOrderByIdAsc(Long companyId,Long categoryParentId) throws Exception;
}
