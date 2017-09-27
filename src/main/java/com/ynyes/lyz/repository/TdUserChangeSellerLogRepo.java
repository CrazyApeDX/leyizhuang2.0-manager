package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.user.TdUserChangeSellerLog;

public interface TdUserChangeSellerLogRepo extends PagingAndSortingRepository<TdUserChangeSellerLog, Long>, JpaSpecificationExecutor<TdUserChangeSellerLog> {

	
}
