package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdInventoryLog;


/**
 * TdManagerLog 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdInventoryLogRepo extends
		PagingAndSortingRepository<TdInventoryLog, Long>,
		JpaSpecificationExecutor<TdInventoryLog> 
{
	
}
