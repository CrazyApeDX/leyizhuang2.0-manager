package com.ynyes.lyz.interfaces.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdOrderReceiveInf;

public interface TdOrderReceiveInfRepo
		extends PagingAndSortingRepository<TdOrderReceiveInf, Long>, JpaSpecificationExecutor<TdOrderReceiveInf> 
{
	List<TdOrderReceiveInf> findByOrderNumber(String orderNumber);
}

