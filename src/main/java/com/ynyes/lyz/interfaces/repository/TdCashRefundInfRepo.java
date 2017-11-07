package com.ynyes.lyz.interfaces.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;

public interface TdCashRefundInfRepo extends PagingAndSortingRepository<TdCashRefundInf, Long>, JpaSpecificationExecutor<TdCashRefundInf> 
{
//	List<TdReturnOrderInf> findByorderHeaderId(Long headerId);
	List<TdCashRefundInf> findByOrderHeaderId(Long orderHeaderId);
	
	List<TdCashRefundInf> findByReturnNumber(String returnNumber);
	
	// 查询传递失败的提现记录
	List<TdCashRefundInf> findByRefundTypeAndSendFlag(String type, Integer flag);

	List<TdCashRefundInf> findBySendFlagOrSendFlagIsNull(Integer sendFlag);
	
	@Query(value=" SELECT * "
			+" FROM "
			+" 	td_cash_refund_inf "
			+" WHERE "
			+" 	init_date >= ?1 "
			+" AND ( "
			+" 	send_flag IS NULL "
			+" 	OR send_flag = ?2 "
			+" ) "
			+" ORDER BY "
			+" 	init_date DESC ; ",nativeQuery=true)
	List<TdCashRefundInf> findFailedCashRefundList(Date beginDateFormat,Integer sendFlag );
}

