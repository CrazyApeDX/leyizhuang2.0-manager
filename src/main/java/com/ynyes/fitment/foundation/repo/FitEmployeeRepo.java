package com.ynyes.fitment.foundation.repo;

import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitEmployee;

@Repository
public interface FitEmployeeRepo extends ApplicationRepo<FitEmployee> {

	FitEmployee findByMobileAndPassword(String mobile, String password) throws Exception;
	
	Long countByMobileAndIdNot(String mobile, Long id) throws Exception;
}
