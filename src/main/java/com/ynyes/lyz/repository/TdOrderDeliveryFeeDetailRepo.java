package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.delivery.TdOrderDeliveryFeeDetail;

public interface TdOrderDeliveryFeeDetailRepo extends PagingAndSortingRepository<TdOrderDeliveryFeeDetail, Long>, JpaSpecificationExecutor<TdOrderDeliveryFeeDetail> {

	TdOrderDeliveryFeeDetail findByMainOrderNumber(String mainOrderNumber);
	
	@Query(value=" SELECT odd.id id, "
			+" 	odd.diy_site_name diy_site_name, "
			+" 	odd.diy_site_id diy_site_id, "
			+" 	odd.main_order_number main_order_number, "
			+" 	odd.order_time order_time, "
			+" 	od.send_time send_time, "
			+" 	od.status_id status_id, "
			+"  odd.seller_username seller_username, "
			+" 	odd.seller_real_name seller_real_name, "
			+" 	odd.username username, "
			+" 	odd.user_real_name user_real_name, "
			+" 	odd.buckets_of_paint_fee buckets_of_paint_fee, "
			+" 	odd.nitrolacquer_fee nitrolacquer_fee, "
			+" 	odd.carpentry_paint_fee carpentry_paint_fee, "
			+" 	odd.below_four_kilo_fee below_four_kilo_fee, "
			+" 	odd.wall_accessories wall_accessories, "
			+" 	odd.consumer_delivery_fee consumer_delivery_fee, "
			+" 	odd.company_delivery_fee company_delivery_fee, "
			+" 	odd.company_delivery_fee_adjust company_delivery_fee_adjust, "
			+" 	odd.consumer_delivery_fee_discount consumer_delivery_fee_discount, "
			+" 	odd.company_delivery_fee_discount company_delivery_fee_discount, "
			+" 	odd.consumer_delivery_fee_reduce consumer_delivery_fee_reduce, "
			+" 	odd.company_delivery_fee_reduce company_delivery_fee_reduce, "
			+" 	odd.is_customer_delivery_fee_modified is_customer_delivery_fee_modified, "
			+" 	odd.customer_delivery_fee_before_modified customer_delivery_fee_before_modified, "
			+" 	odd.consumer_delivery_fee_final consumer_delivery_fee_final, "
			+" 	odd.company_delivery_fee_final company_delivery_fee_final "
			+" FROM "
			+" 	td_order_delivery_fee_detail odd "
			+" INNER JOIN td_order od ON odd.main_order_number = od.main_order_number "
			+" INNER JOIN td_diy_site diy ON od.diy_site_id = diy.id "
			+" WHERE "
			+" 	od.order_number LIKE '%YF%' "
			+" AND od.deliver_type_title = '送货上门' "
			+" AND od.status_id NOT IN (1, 2, 3, 7, 8) "
			+" AND od.send_time >= ?1 "
			+" AND od.send_time <= ?2 "
			+" AND diy.city LIKE ?3 "
			+" AND od.diy_site_code LIKE ?4 "
			+" AND diy.id IN ?5 "
			+" ORDER BY "
			+" 	od.send_time DESC; ",nativeQuery=true)
	List<TdOrderDeliveryFeeDetail> queryDownList(Date begin, Date end, String cityName, String diySiteCode,
			List<String> roleDiyIds);
	
	
}
