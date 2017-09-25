package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdCompany;

public interface TdCompanyRepo
		extends PagingAndSortingRepository<TdCompany, Long>, JpaSpecificationExecutor<TdCompany> {
	
}
