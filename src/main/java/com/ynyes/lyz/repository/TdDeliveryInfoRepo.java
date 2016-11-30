package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdDeliveryInfo;

public interface TdDeliveryInfoRepo
		extends PagingAndSortingRepository<TdDeliveryInfo, Long>, JpaSpecificationExecutor<TdDeliveryInfo> {
	
	TdDeliveryInfo findByTaskNo(String taskNo);
	
	List<TdDeliveryInfo> findDistinctTaskNoByTaskNo(String taskNo);
	
	List<TdDeliveryInfo> findByOpUser(String opUser);
	
	List<TdDeliveryInfo> findDistinctTaskNoByDriver(String driver);
	
	List<TdDeliveryInfo> findByOrderNumberOrderByBeginDtDesc(String orderNumber);
}
