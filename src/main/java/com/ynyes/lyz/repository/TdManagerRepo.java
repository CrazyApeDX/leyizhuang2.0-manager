package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdManager;

/**
 * TdManager 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdManagerRepo extends
		PagingAndSortingRepository<TdManager, Long>,
		JpaSpecificationExecutor<TdManager> 
{
    TdManager findByUsernameAndIsEnableTrue(String username);
    
    Page<TdManager> findByUsernameContainingOrRealNameContainingOrderByIdDesc(String keywords1, String keywords2,
			Pageable page);
    
    List<TdManager> findByRoleId(Long roleId);
}
