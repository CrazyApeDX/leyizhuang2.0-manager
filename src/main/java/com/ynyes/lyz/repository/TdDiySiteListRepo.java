package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteList;

/**
 * TdDiySite 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdDiySiteListRepo
		extends PagingAndSortingRepository<TdDiySiteList, Long>, JpaSpecificationExecutor<TdDiySite> 
{

}
