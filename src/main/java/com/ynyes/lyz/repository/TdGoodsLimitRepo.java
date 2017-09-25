package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdGoodsLimit;

public interface TdGoodsLimitRepo
		extends PagingAndSortingRepository<TdGoodsLimit, Long>, JpaSpecificationExecutor<TdGoodsLimit> 
{
	TdGoodsLimit findByLimitId(Long limitId);
}

