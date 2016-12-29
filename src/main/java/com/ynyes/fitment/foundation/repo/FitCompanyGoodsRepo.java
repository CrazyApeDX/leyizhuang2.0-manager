package com.ynyes.fitment.foundation.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitCompanyGoods;

@Repository
public interface FitCompanyGoodsRepo extends ApplicationRepo<FitCompanyGoods> {

	List<FitCompanyGoods> findByCompanyIdAndCategoryIdOrderByGoodsSortIdAsc(Long companyId, Long categoryId)
			throws Exception;
}
