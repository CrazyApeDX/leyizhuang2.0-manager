package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdOrderGoods;


/**
 * TdOrderGoods 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdOrderGoodsRepo extends
		PagingAndSortingRepository<TdOrderGoods, Long>,
		JpaSpecificationExecutor<TdOrderGoods> 
{
	
	@Query(value=" SELECT "
			+" 	og.* "
			+" FROM "
			+" 	td_order o "
			+" INNER JOIN td_order_goods og ON o.id = og.td_order_id "
			+" WHERE "
			+" 	o.main_order_number = ?1; ",nativeQuery=true)
	List<TdOrderGoods> findByMainOrderNumber(String mainOrderNumber);
}
