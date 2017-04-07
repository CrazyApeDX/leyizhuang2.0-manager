package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdOwnMoneyRecord;

/**
 * TdOwnMoneyRecord 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdOwnMoneyRecordRepo extends
		PagingAndSortingRepository<TdOwnMoneyRecord, Long>,
		JpaSpecificationExecutor<TdOwnMoneyRecord> 
{
	List<TdOwnMoneyRecord> findByOrderNumberIgnoreCase(String orderNumber);
	
	Page<TdOwnMoneyRecord> findByDiyCodeAndIsOwnIsNullOrderByIdDesc(String diyCode ,Pageable page);
	
	Page<TdOwnMoneyRecord> findByIsEnableAndIsOwnIsNullOrderByIdDesc(Boolean isEnale,Pageable page);
	
	Page<TdOwnMoneyRecord> findByIsPayedAndIsOwnIsNullOrderByIdDesc(Boolean isPayed,Pageable page);
	
	Page<TdOwnMoneyRecord> findByDiyCodeAndIsEnableAndIsOwnIsNullOrderByIdDesc(String diyCode,Boolean isEnable ,Pageable page);
	Page<TdOwnMoneyRecord> findByDiyCodeAndIsPayedAndIsOwnIsNullOrderByIdDesc(String diyCode,Boolean isPayed ,Pageable page);
	
	Page<TdOwnMoneyRecord> findByIsOwnIsNull(Pageable page);
	
	List<TdOwnMoneyRecord> findByOrderNumberAndIsOwnAndIsPayed(String mainOrderNumber, Boolean isOwn, Boolean isPayed);
	
	@Query(value=" SELECT "
			+" 	IFNULL(dd.order_number ,'NULL') order_number  "
			+" FROM "
			+" 	( "
			+" 		SELECT DISTINCT "
			+" 			owd.order_number order_number, "
			+" 			owd.id id, "
			+" 			owd.create_time create_time, "
			+" 			IFNULL(owd.payed, 0) payed, "
			+" 			IFNULL(owd.money, 0) money, "
			+" 			IFNULL(owd.pos, 0) pos, "
			+" 			IFNULL(owd.back_money, 0) back_money, "
			+" 			IFNULL(owd.back_pos, 0) back_pos, "
			+" 			IFNULL(owd.back_other, 0) back_other, "
			+" 			IFNULL(cash.amount, 0) amount "
			+" 		FROM "
			+" 			td_own_money_record owd "
			+" 		LEFT JOIN td_diy_site diy ON diy.store_code = owd.diy_code "
			+" 		LEFT JOIN ( "
			+" 			SELECT "
			+" 				ri.init_date init_date, "
			+" 				sum(ri.amount) amount, "
			+" 				o1.main_order_number main_order_number "
			+" 			FROM "
			+" 				td_cash_recipt_inf ri "
			+" 			LEFT JOIN td_order o1 ON ri.order_number = o1.order_number "
			+" 			WHERE "
			+" 				ri.init_date >= ?1  "
			+" 			AND ri.receipt_type IN ( "
			+" 				'门店现金', "
			+" 				'门店POS', "
			+" 				'门店其他', "
			+" 				'配送现金', "
			+" 				'配送POS' "
			+" 			) "
			+" 			GROUP BY "
			+" 				o1.main_order_number "
			+" 		) cash ON owd.order_number = cash.main_order_number "
			+" 		WHERE "
			+" 			( "
			+" 				owd.create_time >= ?1 "
			+" 				OR cash.init_date >= ?1 "
			+" 			) "
			+" 		AND diy.city = '成都市' "
			+" 		AND owd.owned = 0 "
			+" 	) dd "
			+" WHERE "
			+" 	dd.amount != dd.payed "
			+" AND (dd.payed - dd.amount >= 0.1); ",nativeQuery=true)
	List<String> findMissedOrderNumbers(Date beginDate);

	List<TdOwnMoneyRecord> findByOrderNumberIn(List<String> missedOrderNumber);
}
