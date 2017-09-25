package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdLyzParameter;


/**
 * TdLyzParameter 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdLyzParameterRepo extends
		PagingAndSortingRepository<TdLyzParameter, Long>,
		JpaSpecificationExecutor<TdLyzParameter> 
{
    TdLyzParameter findByCategoryId(Long categoryId);
}
