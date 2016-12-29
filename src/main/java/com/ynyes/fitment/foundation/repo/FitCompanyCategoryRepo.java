package com.ynyes.fitment.foundation.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitCompanyCategory;

@Repository
public interface FitCompanyCategoryRepo extends ApplicationRepo<FitCompanyCategory> {

	List<FitCompanyCategory> findByCompanyIdOrderByCategorySortIdAsc(Long companyId) throws Exception;

	List<FitCompanyCategory> findByCompanyIdAndCategoryParentIdOrderByIdAsc(Long companyId, Long caategoryParentId)
			throws Exception;
}
