package com.ynyes.fitment.foundation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity.OriginType;
import com.ynyes.fitment.foundation.entity.FitCompanyCategory;
import com.ynyes.fitment.foundation.repo.FitCompanyCategoryRepo;
import com.ynyes.fitment.foundation.service.FitCompanyCategoryService;

@Service
@Transactional
public class FitCompanyCategoryServiceImpl implements FitCompanyCategoryService {

	@Autowired
	private FitCompanyCategoryRepo fitCompanyCategoryRepo;
	
	@Override
	public FitCompanyCategory managerAdd(FitCompanyCategory category) throws Exception {
		if (null == category) {
			return null;
		} else {
			category.setCreateOrigin(OriginType.ADD);
			return this.fitCompanyCategoryRepo.save(category);
		}
	}

	@Override
	public List<FitCompanyCategory> findByCompanyIdOrderByCategorySortIdAsc(Long companyId) throws Exception {
		if (null == companyId) {
			return null;
		}
		return this.fitCompanyCategoryRepo.findByCompanyIdOrderByCategorySortIdAsc(companyId);
	}

	@Override
	public void deleteByCompanyId(Long companyId) throws Exception {
		if (null != companyId) {
			this.fitCompanyCategoryRepo.deleteByCompanyId(companyId);
		}
	}
	
	public List<FitCompanyCategory> findByCompanyIdAndCategoryParentIdOrderByIdAsc(Long companyId,Long categoryParentId) throws Exception{
		if(null == companyId || null == categoryParentId ){
			return null;
		}
		return this.fitCompanyCategoryRepo.findByCompanyIdAndCategoryParentIdOrderByIdAsc(companyId,categoryParentId);
	}
}
