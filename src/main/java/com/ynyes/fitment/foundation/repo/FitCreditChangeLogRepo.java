package com.ynyes.fitment.foundation.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;

@Repository
public interface FitCreditChangeLogRepo extends ApplicationRepo<FitCreditChangeLog> {

	List<FitCreditChangeLog> findByCompanyIdOrderByChangeTimeDesc(Long companyId) throws Exception;
	
	Page<FitCreditChangeLog> findByCompanyIdOrderByChangeTimeDesc(Long companyId, Pageable page) throws Exception;
	
}
