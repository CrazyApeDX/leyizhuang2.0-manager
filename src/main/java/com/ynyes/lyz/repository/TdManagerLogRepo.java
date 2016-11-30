package com.ynyes.lyz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdManagerLog;


/**
 * TdManagerLog 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdManagerLogRepo extends
		PagingAndSortingRepository<TdManagerLog, Long>,
		JpaSpecificationExecutor<TdManagerLog> 
{
    Page<TdManagerLog> findByActionTypeIgnoreCaseOrderByCreateTimeDesc(String action, Pageable page);
}
