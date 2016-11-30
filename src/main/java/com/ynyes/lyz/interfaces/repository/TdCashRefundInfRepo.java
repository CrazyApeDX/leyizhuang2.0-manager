package com.ynyes.lyz.interfaces.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;

public interface TdCashRefundInfRepo extends PagingAndSortingRepository<TdCashRefundInf, Long>, JpaSpecificationExecutor<TdCashRefundInf> 
{
//	List<TdReturnOrderInf> findByorderHeaderId(Long headerId);
	List<TdCashRefundInf> findByOrderHeaderId(Long orderHeaderId);
	
	List<TdCashRefundInf> findByReturnNumber(String returnNumber);
	
	// 查询传递失败的提现记录
	List<TdCashRefundInf> findByRefundTypeAndSendFlag(String type, Integer flag);
}

