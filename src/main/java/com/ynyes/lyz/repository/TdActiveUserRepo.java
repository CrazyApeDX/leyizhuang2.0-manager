package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdActiveUser;

/**
 *  活跃会员销量统计
 * 
 * @author
 *
 */

public interface TdActiveUserRepo extends
		PagingAndSortingRepository<TdActiveUser, Long>,
		JpaSpecificationExecutor<TdActiveUser> {

	
	@Query(value=" SELECT UUID() id, "
			+" 	active.city_name city_name, "
			+" 	active.diy_site_name diy_site_name, "
			+" 	active.seller_username seller_username, "
			+" 	active.seller_real_name seller_real_name, "
			+" 	CASE "
			+" WHEN active.sales_summary > 0 THEN "
			+" 		sum(1) "
			+" WHEN active.sales_summary < 0 THEN "
			+" 	sum(- 1) "
			+" ELSE "
			+" 	sum(0) "
			+" END sales_summary "
			+" FROM "
			+" 	( "
			+" 		SELECT "
			+" 			u.city_name city_name, "
			+" 			u.diy_name diy_site_name, "
			+" 			u.username seller_username, "
			+" 			u.real_name seller_real_name, "
			+" 			su.username username, "
			+" 			su.real_name real_name, "
			+" 			sum(su.sales_summary) sales_summary "
			+" 		FROM "
			+" 			td_user u "
			+" 		LEFT JOIN td_diy_site diy ON u.upper_diy_site_id = diy.id "
			+" 		LEFT JOIN td_sales_for_active_user su ON u.username = su.seller_username "
			+"		LEFT JOIN td_user u1 on su.username = u1.username "
			+" 		WHERE "
			+" 			u.user_type = 1 "
			+"      AND u1.identity_type=0 "
			+" 		AND su.brand='华润' "
			+" 		AND su.order_time >= ?1 "
			+" 		AND su.order_time <= ?2 "
			+" 		AND u.city_name LIKE ?3 "
			+" 		AND diy.store_code LIKE ?4  "
			+" 		AND diy.id in ?5 "
			+" 		GROUP BY "
			+" 			u.username, "
			+" 			su.username "
			+" 		ORDER BY "
			+" 			u.diy_name, "
			+" 			u.real_name "
			+" 	) active "
			+" GROUP BY seller_username ; ",nativeQuery=true)
	List<TdActiveUser> queryDownList(Date begin, Date end, String cityName, String diySiteCode,
			List<String> roleDiyIds);
}
