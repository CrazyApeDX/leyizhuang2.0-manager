package com.ynyes.lyz.interfaces.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdEbsInfLog;

public interface TdEbsInfLogRepo extends PagingAndSortingRepository<TdEbsInfLog, Long>,JpaSpecificationExecutor<TdEbsInfLog>
{

}
