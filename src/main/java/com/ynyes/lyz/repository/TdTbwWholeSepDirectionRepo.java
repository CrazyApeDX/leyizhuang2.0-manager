package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdTbwWholeSepDirection;

/**
 * 整转零接口 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdTbwWholeSepDirectionRepo extends PagingAndSortingRepository<TdTbwWholeSepDirection, Long>, JpaSpecificationExecutor<TdTbwWholeSepDirection>
{
	TdTbwWholeSepDirection findByCOwnerNoAndCDirectNoAndCDirectIdAndCTaskType(String COwnerNo,String CDirectNo,Long CDirectId,String CTaskType);
}
