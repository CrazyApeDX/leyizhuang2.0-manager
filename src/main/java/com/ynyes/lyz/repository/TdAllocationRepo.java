package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdAllocation;

public interface TdAllocationRepo extends PagingAndSortingRepository<TdAllocation, Long>, JpaSpecificationExecutor<TdAllocation> {

	List<TdAllocation> findByAllocationFromOrderByStatusAsc(Integer allocationFrom);
}
