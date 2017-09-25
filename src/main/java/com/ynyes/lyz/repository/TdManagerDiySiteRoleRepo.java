package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdManagerDiySiteRole;
import java.lang.String;
import java.util.List;

/**
 * TdManagerDiySiteRole 实体数据库操作接口
 * 
 * @author Shawn
 *
 */

public interface TdManagerDiySiteRoleRepo extends
		PagingAndSortingRepository<TdManagerDiySiteRole, Long>,
		JpaSpecificationExecutor<TdManagerDiySiteRole> 
{
	/**
	 * 根据角色名查询
	 * @param title 角色名
	 * @return 结果集
	 * @author zp
	 */
	List<TdManagerDiySiteRole> findByTitle(String title);
}
