package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdAllocationDetail;

public interface TdAllocationDetailRepo
		extends PagingAndSortingRepository<TdAllocationDetail, Long>, JpaSpecificationExecutor<TdAllocationDetail> {

	/**
	 * 根据调拨单ID查询商品明细
	 * 
	 * @return
	 */
	List<TdAllocationDetail> findByAllocationId(Long allocationId);

	/**
	 * 根据调拨单ID删除商品明细
	 */
	void deleteByAllocationId(Long allocationId);

}
