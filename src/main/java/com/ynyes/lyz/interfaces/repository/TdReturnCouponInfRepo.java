package com.ynyes.lyz.interfaces.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdReturnCouponInf;

public interface TdReturnCouponInfRepo
		extends PagingAndSortingRepository<TdReturnCouponInf, Long>, JpaSpecificationExecutor<TdReturnCouponInf> 
{
	List<TdReturnCouponInf> findByRtHeaderId(Long rtHeaderId);
}

