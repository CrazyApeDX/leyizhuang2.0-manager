package com.ynyes.lyz.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdGathering;

/**
 * TdAgencyFund 虚拟数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdGatheringRepo extends PagingAndSortingRepository<TdGathering, Long>, JpaSpecificationExecutor<TdGathering> {
	
	
	/**
	 * 调用存储过程
	 * @return
	 */
	@Query(value = "{call insertGathering_initial(?1,?2,?3)}",nativeQuery = true)
	void callInsertGathering(Date start,Date end,String username);
	
}
