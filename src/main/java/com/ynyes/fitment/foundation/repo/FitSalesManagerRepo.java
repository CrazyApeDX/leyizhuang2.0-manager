package com.ynyes.fitment.foundation.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitSalesManager;

@Repository
public interface FitSalesManagerRepo extends ApplicationRepo<FitSalesManager>{
	
	List<FitSalesManager> findByCityCode(String cityCode);

}
