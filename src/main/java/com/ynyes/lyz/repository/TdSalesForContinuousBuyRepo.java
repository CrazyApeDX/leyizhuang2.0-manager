package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.ynyes.lyz.entity.TdSalesForContinuousBuy;

/**
 *  销量报表
 * 
 * @author
 *
 */

public interface TdSalesForContinuousBuyRepo extends
		PagingAndSortingRepository<TdSalesForContinuousBuy, Long>,
		JpaSpecificationExecutor<TdSalesForContinuousBuy> {
	
	@Query(value=" SELECT * "
			+" FROM "
			+" 	td_sales_for_continuous_buy sb "
			+" LEFT JOIN td_diy_site diy ON sb.diy_site_name = diy.title "
			+" WHERE "
			+" 	sb.month_str >=?1 "
			+" AND sb.month_str <=?2 "
			+" AND sb.city_name LIKE ?3 "
			+" AND diy.store_code LIKE ?4 "
			+" AND diy.id IN ?5 "
			+" GROUP BY sb.username,sb.diy_site_name,sb.seller_username ;",nativeQuery=true)
	List<TdSalesForContinuousBuy> queryDownList( int begin, int end, String cityName, String diySiteCode, List<String> roleDiyIds);
	
	
	/*@Query(value=" SELECT "
			+" 	sb.id id, "
			+" 	sb.city_name city_name, "
			+" 	sb.diy_site_name diy_site_name, "
			+" 	sb.s_type s_type, "
			+" 	sb.main_order_number main_order_number, "
			+" 	sb.order_number order_number, "
			+" 	sb.order_time order_time, "
			+" 	sb.username username, "
			+" 	sb.real_name real_name, "
			+" 	sb.identity_type identity_type, "
			+" 	sb.seller_username seller_username, "
			+" 	sb.seller_real_name seller_real_name, "
			+" 	sb.sku sku, "
			+" 	sb.goods_title goods_title, "
			+" 	sb.is_gift is_gift, "
			+" 	sb.goods_price goods_price, "
			+" 	sb.goods_quantity goods_quantity, "
			+" 	sb.goods_total_price goods_total_price, "
			+" 	sb.cash_coupon_price cash_coupon_price, "
			+" 	sb.cash_coupon_quantity cash_coupon_quantity, "
			+" 	sb.cash_coupon_total_price cash_coupon_total_price, "
			+" 	sb.product_coupon_price product_coupon_price, "
			+" 	sb.product_coupon_quantity product_coupon_quantity, "
			+" 	sb.product_coupon_total_price product_coupon_total_price, "
			+" 	SUM(sb.sales_summary) sales_summary, "
			+" 	sb.month_str month_str "
			+" FROM "
			+" 	td_sales_for_continuous_buy sb "
			+" WHERE "
			+" 	sb.diy_site_name = ?1 "
			+" AND sb.username = ?2 "
			+" AND sb.seller_username = ?3 "
			+" AND sb.month_str = ?4 "
			+" GROUP BY "
			+" sb.username ",nativeQuery=true)
	int retriveSale(String diySiteName, String username, String sellerUsername, String monthStr);*/
	
	@Query(value=" SELECT "
			+" sum(sb.sales_summary) "
			+" FROM "
			+" 	td_sales_for_continuous_buy sb "
			+" WHERE "
			+" 	sb.diy_site_name = ?1 "
			+" AND sb.username = ?2 "
			+" AND sb.seller_username = ?3 "
			+" AND sb.month_str = ?4 "
			+" GROUP BY "
			+" sb.username ",nativeQuery=true)
	int retriveSale(String diySiteName, String username, String sellerUsername, String monthStr);

	
	

}
