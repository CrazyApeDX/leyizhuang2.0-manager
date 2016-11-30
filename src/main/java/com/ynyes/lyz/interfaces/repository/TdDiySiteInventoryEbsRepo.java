package com.ynyes.lyz.interfaces.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdDiySiteInventoryEbs;

public interface TdDiySiteInventoryEbsRepo extends PagingAndSortingRepository<TdDiySiteInventoryEbs, Long> ,JpaSpecificationExecutor<TdDiySiteInventoryEbs>
{
	TdDiySiteInventoryEbs findByTransId(Long transId);
}
