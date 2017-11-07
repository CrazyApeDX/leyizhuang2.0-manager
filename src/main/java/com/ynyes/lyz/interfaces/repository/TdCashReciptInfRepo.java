package com.ynyes.lyz.interfaces.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.entity.TdOrderInf;

public interface TdCashReciptInfRepo
		extends PagingAndSortingRepository<TdCashReciptInf, Long>, JpaSpecificationExecutor<TdCashReciptInf> {
	List<TdCashReciptInf> findByOrderHeaderId(Long headerId);

	List<TdCashReciptInf> findByOrderNumber(String orderNumber);

	// 查询未成功的充值记录
	List<TdCashReciptInf> findByReceiptTypeAndSendFlag(String reciptType, Integer flag);

	List<TdCashReciptInf> findBySendFlag(Integer flag);

	TdCashReciptInf findByOrderNumberAndReceiptClassAndReceiptType(String orderNumber, String receiptClass,
			String receiptType);

	List<TdCashReciptInf> findBySendFlagOrSendFlagIsNull(Integer sendFlag);
	
	@Query(value=" SELECT * "
			+" FROM "
			+" 	td_cash_recipt_inf "
			+" WHERE "
			+" 	init_date >= ?1 "
			+" AND ( "
			+" 	send_flag IS NULL "
			+" 	OR send_flag = ?2 "
			+" ) "
			+" ORDER BY "
			+" 	init_date DESC ; ",nativeQuery=true)
	List<TdCashReciptInf> findFailedCashReciptList(Date beginDateFormat,Integer sendFlag );
}
