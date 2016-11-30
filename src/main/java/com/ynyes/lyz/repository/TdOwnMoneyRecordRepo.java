package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdOwnMoneyRecord;

/**
 * TdOwnMoneyRecord 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdOwnMoneyRecordRepo extends
		PagingAndSortingRepository<TdOwnMoneyRecord, Long>,
		JpaSpecificationExecutor<TdOwnMoneyRecord> 
{
	List<TdOwnMoneyRecord> findByOrderNumberIgnoreCase(String orderNumber);
	
	Page<TdOwnMoneyRecord> findByDiyCodeAndIsOwnIsNullOrderByIdDesc(String diyCode ,Pageable page);
	
	Page<TdOwnMoneyRecord> findByIsEnableAndIsOwnIsNullOrderByIdDesc(Boolean isEnale,Pageable page);
	
	Page<TdOwnMoneyRecord> findByIsPayedAndIsOwnIsNullOrderByIdDesc(Boolean isPayed,Pageable page);
	
	Page<TdOwnMoneyRecord> findByDiyCodeAndIsEnableAndIsOwnIsNullOrderByIdDesc(String diyCode,Boolean isEnable ,Pageable page);
	Page<TdOwnMoneyRecord> findByDiyCodeAndIsPayedAndIsOwnIsNullOrderByIdDesc(String diyCode,Boolean isPayed ,Pageable page);
	
	Page<TdOwnMoneyRecord> findByIsOwnIsNull(Pageable page);
	
	List<TdOwnMoneyRecord> findByOrderNumberAndIsOwnAndIsPayed(String mainOrderNumber, Boolean isOwn, Boolean isPayed);
}
