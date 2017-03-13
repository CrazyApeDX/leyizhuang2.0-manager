package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdReserveOrder;

/**
 *  未提货报表Dao层
 * 
 * @author yanle
 *
 */

public interface TdReserveOrderRepo extends
		PagingAndSortingRepository<TdReserveOrder, Long>,
		JpaSpecificationExecutor<TdReserveOrder> {


	/**
	 * 查询未提货报表下载的list
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param cityName
	 *            城市
	 * @param diySiteCode
	 *            门店
	 * @param roleDiyIds
	 *            权限门店
	 * @return
	 */
	@Query(value =" SELECT "
			+" 	round(round(rand(),4)*1000000) id, "
			+" 	diy.city city, "
			+"  '产品券' reserve_type, "
			+" 	coupon.diy_site_tital diy_site_name, "
			+" 	coupon.get_time get_time, "
			+"	u.id user_id, "
			+" 	u.real_name real_user_real_name, "
			+" 	coupon.username username, "
			+" 	coupon.seller_real_name seller_real_name, "
			+" 	coupon.sku sku, "
			+" 	sum(1) quantity, "
			+" 	IFNULL(coupon.buy_price,0) buy_price, "
			+" 	coupon.coupon_order_number coupon_order_number, "
			+"  0 total_price "
			+" FROM "
			+" 	td_coupon coupon "
			+" LEFT JOIN td_user u ON coupon.username = u.username "
			+" LEFT JOIN td_diy_site diy ON coupon.diy_site_tital = diy.title "
			+" WHERE "
			+" 	coupon.type_category_id = 3 "
			+" AND coupon.is_out_date != 1 "
			+" AND coupon.is_used != 1 "
			+" AND ( "
			+" 	coupon.type_description LIKE '%CRM' "
			+" 	OR get_time >= '2016-09-01' "
			+" ) "
			+" AND coupon.username NOT IN ( "
			+" 	'12345678910', "
			+" 	'13408698552' "
			+" ) "
			+" AND coupon.get_time <= NOW() "
			+" AND diy.city LIKE ?1 "
			+" AND diy.store_code LIKE ?2 "
			+" AND diy.id IN ?3 "
			+" GROUP BY "
			+" 	coupon.coupon_order_number, "
			+"	coupon.username, "
			+" 	coupon.sku, "
			+" 	coupon.buy_price "
			+" union all "
			+" SELECT "
			+" 	round(round(rand(),4)*1000000) id, "
			+" 	diy.city city, "
			+" 	'自提单' AS reserve_type, "
			+" 	diy.title diy_site_name, "
			+"	owd.pay_time get_time, "
			+"	o.user_id user_id, "
			+" 	o.real_user_real_name real_user_real_name, "
			+" 	o.real_user_username username, "
			+" 	o.seller_real_name seller_real_name, "
			+" 	og.sku sku, "
			+" 	og.quantity quantity, "
			+" 	CASE "
			+" WHEN og.presented_list_id IS NOT NULL THEN "
			+" 	0 "
			+" ELSE "
			+" 	og.price "
			+" END buy_price, "
			+"  o.order_number coupon_order_number, "
			+"  0 total_price "
			+" FROM "
			+" 	td_order o "
			+" LEFT JOIN td_diy_site diy ON o.diy_site_id = diy.id, "
			+"  td_own_money_record owd, "
			+"  td_order_goods og "
			+" WHERE "
			+" 	o.main_order_number = owd.order_number "
			+" AND ( "
			+" 	o.id = og.td_order_id "
			+" 	OR o.id = og.presented_list_id "
			+" ) "
			+" AND deliver_type_title = '门店自提' "
			+" AND o.status_id = 3 "
			+" AND ( "
			+" 	owd.pay_time IS NOT NULL "
			+" 	OR owd.create_time IS NOT NULL "
			+" ) "
			+" AND diy.city LIKE ?1 "
			+" AND diy.store_code LIKE ?2 "
			+" AND diy.id IN ?3 "
			+" order by get_time ; ",nativeQuery=true)
	List<TdReserveOrder> queryDownList(String cityName,
			String diySiteCode, List<String> roleDiyIds);
	
	
}
