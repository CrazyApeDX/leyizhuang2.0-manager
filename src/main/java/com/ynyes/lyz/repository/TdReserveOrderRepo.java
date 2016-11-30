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
			+" 	UUID() id, "
			+" 	diy.city city, "
			+" 	coupon.diy_site_tital diy_site_name, "
			+" 	coupon.get_time get_time, "
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
			+" 	coupon.buy_price; ",nativeQuery=true)
	List<TdReserveOrder> queryDownList(String cityName,
			String diySiteCode, List<String> roleDiyIds);
	
	
}
