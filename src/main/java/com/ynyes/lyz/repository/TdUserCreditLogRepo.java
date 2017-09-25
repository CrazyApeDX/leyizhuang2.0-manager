package com.ynyes.lyz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ynyes.lyz.entity.user.TdUserCreditLog;

@Repository
public interface TdUserCreditLogRepo
		extends PagingAndSortingRepository<TdUserCreditLog, Long>, JpaSpecificationExecutor<TdUserCreditLog> {

	Page<TdUserCreditLog> findBySellerIdOrderByChangeTimeDesc(Long sellerId, Pageable page);
}
