package com.ynyes.lyz.interfaces.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdOrderCouponInf;

public interface TdOrderCouponInfRepo
		extends PagingAndSortingRepository<TdOrderCouponInf, Long>, JpaSpecificationExecutor<TdOrderCouponInf> 
{
	List<TdOrderCouponInf> findByOrderHeaderId(Long headerId);
}

