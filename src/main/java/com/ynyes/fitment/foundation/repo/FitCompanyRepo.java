package com.ynyes.fitment.foundation.repo;

import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitCompany;

@Repository
public interface FitCompanyRepo extends ApplicationRepo<FitCompany>  {

	FitCompany findByName(String name) throws Exception;
	
	FitCompany findByCode(String code) throws Exception;
	
	Long countByNameAndIdNot(String name, Long id) throws Exception;
	
	Long countByCodeAndIdNot(String code, Long id) throws Exception;
}
