package com.ynyes.fitment.foundation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitCompany;

@Repository
public interface FitCompanyRepo extends ApplicationRepo<FitCompany>  {

	FitCompany findByName(String name) throws Exception;
	
	FitCompany findByCode(String code);
	
	Long countByNameAndIdNot(String name, Long id) throws Exception;
	
	Long countByCodeAndIdNot(String code, Long id) throws Exception;
	
	
	@Query(value="select * from fit_company where sob_id in ?1 ;",nativeQuery=true)
	List<FitCompany> findFitCompanyBySobId(List<Long> sobIdList);
	
	List<FitCompany> findBySobId(Long sobId);
}
