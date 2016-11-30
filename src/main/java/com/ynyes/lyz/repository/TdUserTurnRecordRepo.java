package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdUserTurnRecord;

/**
 * 
 * @author Max
 *
 */
public interface TdUserTurnRecordRepo extends
			PagingAndSortingRepository<TdUserTurnRecord, Long>,
			JpaSpecificationExecutor<TdUserTurnRecord>{

	
}
