package com.ynyes.lyz.interfaces.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdOrderInf;

public interface TdOrderInfRepo extends PagingAndSortingRepository<TdOrderInf, Long>, JpaSpecificationExecutor<TdOrderInf> 
{
	TdOrderInf findByOrderNumber(String orderNumber);
	
	// 查询未发送成功的订单信息
	List<TdOrderInf> findBySendFlag(Integer sendFlag);
	
	List<TdOrderInf> findBySendFlagAndSobId(Integer sendFlag, Long sobId);

	List<TdOrderInf> findBySendFlagOrSendFlagIsNull(Integer sendFlag);
}

