package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdDeliveryType;


/**
 * TdDeliveryType 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdDeliveryTypeRepo extends
		PagingAndSortingRepository<TdDeliveryType, Long>,
		JpaSpecificationExecutor<TdDeliveryType> 
{
    Page<TdDeliveryType> findByTitleContainingOrderBySortIdAsc(String keywords, Pageable page);
    
    List<TdDeliveryType> findByIsEnableTrue();
    
}
