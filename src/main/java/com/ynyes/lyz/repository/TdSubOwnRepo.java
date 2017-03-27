package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdSubOwn;

/**
 *  欠款报表
 * 
 * @author yanle
 *
 */

public interface TdSubOwnRepo extends
		PagingAndSortingRepository<TdSubOwn, Long>,
		JpaSpecificationExecutor<TdSubOwn> {


	@Query(value=" SELECT UUID() id, "
			+ "	o.order_number order_number, "
			+ "	IFNULL(o.total_price,0) total_price , "
			+ "	IFNULL(o.other_pay,0) other_pay, "
			+ " case when (owd.is_enable is true and owd.ispassed is true) "
			+ " then IFNULL(owd.money,0) "
			+ " else 0 "
			+ " end as money, "
			+ " case when (owd.is_enable is true and owd.ispassed is true) "
			+ " then IFNULL(owd.pos,0) "
			+ " else 0 "
			+ " end as pos, "
			+ "	IFNULL(owd.back_money,0) back_money, "
			+ "	IFNULL(owd.back_pos,0) back_pos, "
			+ "	IFNULL(owd.back_other,0) back_other "
			+ " FROM "
			+ "	td_order o "
			+ " LEFT JOIN td_own_money_record owd ON o.main_order_number = owd.order_number "
			+ " where o.main_order_number = ?1 ; ",nativeQuery=true)
	List<TdSubOwn> queryDownListDetail(String orderNumber);
}
