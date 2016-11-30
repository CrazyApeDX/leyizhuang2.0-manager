package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdSms;

public interface TdSmsRepo extends PagingAndSortingRepository<TdSms, Long>, JpaSpecificationExecutor<TdSms> {

}
