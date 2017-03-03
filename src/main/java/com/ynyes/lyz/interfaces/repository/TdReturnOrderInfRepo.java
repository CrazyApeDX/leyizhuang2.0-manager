package com.ynyes.lyz.interfaces.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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

	List<TdReturnOrderInf> findBySendFlagOrSendFlagIsNull(Integer sendFlag);
}

