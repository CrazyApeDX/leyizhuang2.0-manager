package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdWareHouse;

/**
 * 仓库
 * @author 华仔
 *
 */
public interface TdWareHouseRepo extends PagingAndSortingRepository<TdWareHouse, Long>, JpaSpecificationExecutor<TdWareHouse>{
	
	List<TdWareHouse> findBywhNumberOrderBySortIdAsc(String whNumber);

}
