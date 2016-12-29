package com.ynyes.fitment.foundation.service;

import java.util.List;

import com.ynyes.fitment.foundation.entity.client.ClientCompanyCategory;

public interface FitCompanyCategoryService {

	List<ClientCompanyCategory> getSaleableCategoryTreeByCompanyId(Long companyId) throws Exception;
}
