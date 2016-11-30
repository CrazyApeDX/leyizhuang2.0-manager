package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdDeposit;

public interface TdDepositRepo extends PagingAndSortingRepository<TdDeposit, Long>, JpaSpecificationExecutor<TdDeposit>{

}
