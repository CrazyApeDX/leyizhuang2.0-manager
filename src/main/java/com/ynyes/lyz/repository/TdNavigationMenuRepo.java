package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdNavigationMenu;

/**
 * TdNavigationMenu 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdNavigationMenuRepo extends
		PagingAndSortingRepository<TdNavigationMenu, Long>,
		JpaSpecificationExecutor<TdNavigationMenu> 
{
    // 通过父类型查找
    List<TdNavigationMenu> findByParentIdAndIsEnableTrueOrderBySortIdAsc(Long parentId);
    
    List<TdNavigationMenu> findByIsEnableTrueOrderBySortIdAsc();
}
