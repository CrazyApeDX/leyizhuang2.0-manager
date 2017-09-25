package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdSmsAccount;

public interface TdSmsAccountRepo
		extends PagingAndSortingRepository<TdSmsAccount, Long>, JpaSpecificationExecutor<TdSmsAccount> {

}
