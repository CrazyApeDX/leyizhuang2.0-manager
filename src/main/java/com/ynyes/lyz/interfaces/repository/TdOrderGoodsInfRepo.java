package com.ynyes.lyz.interfaces.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdOrderGoodsInf;

public interface TdOrderGoodsInfRepo
		extends PagingAndSortingRepository<TdOrderGoodsInf, Long>, JpaSpecificationExecutor<TdOrderGoodsInf> 
{
	List<TdOrderGoodsInf> findByOrderHeaderId(Long headerId);
	
	List<TdOrderGoodsInf> findBySendFlag(Integer sendFlag);
}

