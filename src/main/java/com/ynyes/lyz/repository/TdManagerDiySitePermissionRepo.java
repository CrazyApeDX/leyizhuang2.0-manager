package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdManagerDiySitePermission;

/**
 * TdManagerDiySitePermission 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdManagerDiySitePermissionRepo extends
		PagingAndSortingRepository<TdManagerDiySitePermission, Long>,
		JpaSpecificationExecutor<TdManagerDiySitePermission> 
{

}
