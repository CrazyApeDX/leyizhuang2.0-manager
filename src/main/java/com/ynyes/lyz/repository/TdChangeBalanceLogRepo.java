package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdChangeBalanceLog;

public interface TdChangeBalanceLogRepo
		extends JpaSpecificationExecutor<TdChangeBalanceLog>, PagingAndSortingRepository<TdChangeBalanceLog, Long> {

	
}
