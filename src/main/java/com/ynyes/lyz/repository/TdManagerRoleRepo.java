package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdManagerRole;

/**
 * TdManagerRole 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdManagerRoleRepo extends
		PagingAndSortingRepository<TdManagerRole, Long>,
		JpaSpecificationExecutor<TdManagerRole> 
{
	List<TdManagerRole> findByTitle(String roleTitle);
	/**
	 * 查询超级管理员货非超级管理员
	 * @param isys 是否是超级管理员
	 * @return结果列表
	 * @author zp
	 */
	List<TdManagerRole> findByIsSys(Boolean isys);
}
