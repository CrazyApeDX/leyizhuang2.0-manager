package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdDeliveryInfo;

public interface TdDeliveryInfoRepo
		extends PagingAndSortingRepository<TdDeliveryInfo, Long>, JpaSpecificationExecutor<TdDeliveryInfo> {
	
	TdDeliveryInfo findByTaskNo(String taskNo);
	
	List<TdDeliveryInfo> findDistinctTaskNoByTaskNo(String taskNo);
	
	List<TdDeliveryInfo> findByOpUser(String opUser);
	
	List<TdDeliveryInfo> findDistinctTaskNoByDriver(String driver);
	
	List<TdDeliveryInfo> findByOrderNumberOrderByBeginDtDesc(String orderNumber);
	
	@Query(value=" SELECT "
			+" 	IFNULL(b.sub_order_number,'NULL') sub_order_number "
			+" FROM "
			+" 	td_delivery_info a "
			+" JOIN td_delivery_info_detail b ON a.task_no = b.task_no "
			+" WHERE "
			+" 	a.driver = ?1 ; ",nativeQuery=true)
	List<String> findSubOrderNumberByOpUser(String opUser);
}
