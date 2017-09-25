package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.delivery.TdOrderDeliveryFeeDetailStatement;

public interface TdOrderDeliveryFeeDetailStatementRepo extends PagingAndSortingRepository<TdOrderDeliveryFeeDetailStatement, Long>, JpaSpecificationExecutor<TdOrderDeliveryFeeDetailStatement> {

	
	@Query(value="SELECT"
			+"	DISTINCT"
			+"	odd.id id,"
			+"	odd.diy_site_name diy_site_name,"
			+"	odd.diy_site_id diy_site_id,"
			+"	odd.main_order_number main_order_number,"
			+"	odd.order_time order_time,"
			+"	od.send_time send_time,"
			+"	od.status_id status_id,"
			+"	odd.seller_real_name seller_real_name,"
			+"	odd.user_real_name user_real_name,"
			+"	odd.username username,"
			+"	odd.buckets_of_paint_fee buckets_of_paint_fee,"
			+"	odd.nitrolacquer_fee nitrolacquer_fee,"
			+"	odd.carpentry_paint_fee carpentry_paint_fee,"
			+"	odd.below_four_kilo_fee below_four_kilo_fee,"
			+"	sum("
			+"		IFNULL(od.total_goods_price, 0) - IFNULL(od.cash_balance_used, 0) - IFNULL(od.activity_sub_price, 0)"
			+"	) order_total_money,"
			+"	odd.consumer_delivery_fee_final consumer_delivery_fee_final,"
			+"	odd.company_delivery_fee company_delivery_fee,"
			+"	odd.company_delivery_fee_discount company_delivery_fee_discount,"
			+"	odd.company_delivery_fee_final company_delivery_fee_final"
			+" FROM"
			+"	td_order_delivery_fee_detail odd"
			+" INNER JOIN td_order od ON odd.main_order_number = od.main_order_number"
			+" AND od.order_number NOT LIKE '%YF%'"
			+" INNER JOIN td_diy_site diy ON od.diy_site_id = diy.id"
			+" WHERE"
			+"  od.deliver_type_title = '送货上门'"
			+" AND od.status_id NOT IN (1, 2, 3, 7, 8)"
			+" AND od.send_time >= ?1"
			+" AND od.send_time <= ?2"
			+" AND diy.city LIKE ?3"
			+" AND od.diy_site_code LIKE ?4"
			+" AND diy.id IN ?5"
			+" GROUP BY"
			+"  odd.main_order_number"
			+" ORDER BY"
			+"  od.order_time DESC;",nativeQuery=true)
	List<TdOrderDeliveryFeeDetailStatement> queryDownOrderList(Date begin, Date end, String cityName, String diySiteCode,
			List<String> roleDiyIds);
	
	
}
