package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdCartColorPackage;

public interface TdCartColorPackageRepo
		extends PagingAndSortingRepository<TdCartColorPackage, Long>, JpaSpecificationExecutor<TdCartColorPackage> {
	List<TdCartColorPackage> findByUsername(String username);
}
