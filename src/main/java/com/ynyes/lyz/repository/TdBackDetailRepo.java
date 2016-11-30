package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdBackDetail;

/**
 * TdBackMain  实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdBackDetailRepo extends PagingAndSortingRepository<TdBackDetail, Long>, JpaSpecificationExecutor<TdBackDetail> {
	
}
