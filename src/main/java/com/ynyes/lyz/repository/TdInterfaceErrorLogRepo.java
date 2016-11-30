package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdInterfaceErrorLog;

public interface TdInterfaceErrorLogRepo extends PagingAndSortingRepository<TdInterfaceErrorLog, Long>, JpaSpecificationExecutor<TdInterfaceErrorLog> {
}
