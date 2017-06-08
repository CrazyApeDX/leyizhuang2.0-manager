package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdAllocationTrail;

public interface TdAllocationTrailRepo
		extends PagingAndSortingRepository<TdAllocationTrail, Long>, JpaSpecificationExecutor<TdAllocationTrail> {
	/**
	 * 根据调拨单ID查询轨迹
	 * 
	 * @return
	 */
	List<TdAllocationTrail> findByAllocationId(Long allocationId);
}
