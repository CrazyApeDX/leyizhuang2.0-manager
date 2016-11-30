package com.ynyes.lyz.interfaces.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdReturnGoodsInf;

public interface TdReturnGoodsInfRepo
		extends PagingAndSortingRepository<TdReturnGoodsInf, Long>, JpaSpecificationExecutor<TdReturnGoodsInf> 
{
	List<TdReturnGoodsInf> findByRtHeaderId(Long rtHeaderId);
}

