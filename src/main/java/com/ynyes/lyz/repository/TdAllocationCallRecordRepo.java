package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdAllocationCallRecord;

public interface TdAllocationCallRecordRepo
		extends PagingAndSortingRepository<TdAllocationCallRecord, Long>, JpaSpecificationExecutor<TdAllocationCallRecord> {

	List<TdAllocationCallRecord> findByType(Integer type);
}
