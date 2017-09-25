package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdUserLevel;

/**
 * TdUserLevel 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdUserLevelRepo extends
		PagingAndSortingRepository<TdUserLevel, Long>,
		JpaSpecificationExecutor<TdUserLevel> 
{
    TdUserLevel findByLevelIdAndIsEnableTrue(Long levelId);
    
    TdUserLevel findByLevelId(Long levelId);
    
    TdUserLevel findByLevelIdAndIdNot(Long levelId, Long id);
    
    TdUserLevel findByTitle(String title);
    
    TdUserLevel findByTitleAndIdNot(String title, Long id);
    
    List<TdUserLevel> findByIsEnableTrueOrderByLevelIdAsc();
}
