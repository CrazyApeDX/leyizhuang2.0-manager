package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdStorage;


/**
 * TdStorage 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdStorageRepo extends
		PagingAndSortingRepository<TdStorage, Long>,
		JpaSpecificationExecutor<TdStorage> 
{
    TdStorage findByTitle(String title);
}
