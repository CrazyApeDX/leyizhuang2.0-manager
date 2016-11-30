package com.ynyes.lyz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ynyes.lyz.entity.TdDeliveryFeeChangeLog;

@Repository
public interface TdDeliveryFeeChangeLogRepo extends PagingAndSortingRepository<TdDeliveryFeeChangeLog, Long>,
		JpaSpecificationExecutor<TdDeliveryFeeChangeLog> {

	@Query("select log from TdDeliveryFeeChangeLog log where log.manager like :keywords or log.orderNumber like :keywords order by log.operationTime desc")
	Page<TdDeliveryFeeChangeLog> findByKeywords(@Param("keywords") String keywords, Pageable pageRequest);

}
