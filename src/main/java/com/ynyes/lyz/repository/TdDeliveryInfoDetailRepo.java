package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdDeliveryInfoDetail;

public interface TdDeliveryInfoDetailRepo
		extends PagingAndSortingRepository<TdDeliveryInfoDetail, Long>, JpaSpecificationExecutor<TdDeliveryInfoDetail> {
	
	List<TdDeliveryInfoDetail> findByTaskNo(String taskNo);
	
	List<TdDeliveryInfoDetail> findByOpUser(String opUser);
	
	List<TdDeliveryInfoDetail> findDistinctSubOrderNumberByTaskNoIn(List<String> taskNoList);
	
	List<TdDeliveryInfoDetail> findBySubOrderNumber(String subOrderNumber);
	
}
