package com.ynyes.fitment.foundation.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;

@Repository
public interface FitCreditChangeLogRepo extends ApplicationRepo<FitCreditChangeLog> {

	List<FitCreditChangeLog> findByCompanyIdOrderByChangeTimeDesc(Long companyId) throws Exception;
	
	Page<FitCreditChangeLog> findByCompanyIdOrderByChangeTimeDesc(Long companyId, Pageable page) throws Exception;
	
	@Query(value="select * from"
			+ " fit_credit_change_log"
			+ " where type like %?6%"
			+ " and city like %?3%"
			+ " and company_code like %?4%"
			+ " and change_time >= ?1"
			+ " and change_time <= ?2"
			+ " and (company_title like %?5%"
			+ " or company_code like %?5%"
			+ " or reference_number like %?5%)"
			+ " ORDER BY id desc",nativeQuery=true)
		List<FitCreditChangeLog> queryDownList(String begindata, String enddata, String city, String companyCode,
				String keywords, String type);
	
	@Query(value="select * from"
			+ " fit_credit_change_log"
			+ " where type like %?6%"
			+ " and distinguish = 2"
			+ " and city like %?3%"
			+ " and company_code like %?4%"
			+ " and change_time >= ?1"
			+ " and change_time <= ?2"
			+ " and (company_title like %?5%"
			+ " or company_code like %?5%"
			+ " or reference_number like %?5%)"
			+ " ORDER BY id desc",nativeQuery=true)
		List<FitCreditChangeLog> queryWalletDownList(String begindata, String enddata, String city, String companyCode,
				String keywords, String type);
}
