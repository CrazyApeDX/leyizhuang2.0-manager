package com.ynyes.lyz.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdReturnReport;


/**
 * TdReturnReport 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdReturnReportRepo extends PagingAndSortingRepository<TdReturnReport, Long>, JpaSpecificationExecutor<TdReturnReport> {
	
	/**
	 * 调用存储过程
	 * @return
	 */
	@Query(value = "{call insertReturnReport_initial(?1,?2,?3)}",nativeQuery = true)
	void callInsertReturnReport(Date start,Date end,String username);
}
