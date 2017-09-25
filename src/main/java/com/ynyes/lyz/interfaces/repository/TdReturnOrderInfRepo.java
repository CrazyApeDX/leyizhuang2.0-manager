package com.ynyes.lyz.interfaces.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdReturnOrderInf;

public interface TdReturnOrderInfRepo
		extends PagingAndSortingRepository<TdReturnOrderInf, Long>, JpaSpecificationExecutor<TdReturnOrderInf> 
{
	List<TdReturnOrderInf> findByOrderHeaderId(Long headerId);
	
//	TdReturnOrderInf findByReturnNumber(String returnNumber);
	
	List<TdReturnOrderInf> findByReturnNumber(String returnNumber);
	
	List<TdReturnOrderInf> findByOrderNumber(String orderNumber);
	
	List<TdReturnOrderInf> findBySobIdAndSendFlag(Long sobId, Integer sendFlag);
	
	@Query(value=" SELECT "
			+" 	* "
			+" FROM "
			+" 	td_return_order_inf "
			+" WHERE "
			+" 	init_date >= ?2 "
			+" AND ( "
			+" 	send_flag IS NULL "
			+" 	OR send_flag = ?1 "
			+" ) "
			+" ORDER BY "
			+" 	init_date DESC ; ",nativeQuery=true)
	List<TdReturnOrderInf> findFailedOrderList(Integer sendFlag,Date initDate);
}

