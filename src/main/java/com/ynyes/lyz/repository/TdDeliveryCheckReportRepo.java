package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.DeliveryCheckReport;

/**
 *  配送考核报表数据仓库
 * 
 * @author
 *
 */

public interface TdDeliveryCheckReportRepo extends
		PagingAndSortingRepository<DeliveryCheckReport, Long>,
		JpaSpecificationExecutor<DeliveryCheckReport> {

	
	@Query(value=" SELECT DISTINCT "
			+" 	UUID() id, "
			+" 	wh.wh_name wh_name, "
			+" 	u.real_name distribution_name, "
			+" 	u.username distribution_phone, "
			+" 	o.main_order_number main_order_number, "
			+" 	o.diy_site_name diy_site_name, "
			+" 	o.seller_real_name seller_real_name, "
			+" 	o.shipping_address shipping_address, "
			+" 	o.order_time order_time, "
			+" 	o.send_time send_time, "
			+" 	sum(o.total_price - o.other_pay) agency_fund, "
			+"	o.remark remark, "
			+" CASE "
			+" WHEN o.status_id=12 AND rn.remark_info ='拒签退货' "
			+" THEN rn.order_time "
			+" WHEN sum(o.total_price) - sum(o.other_pay) = 0 THEN "
			+" o.delivery_time "
			+" WHEN sum(o.total_price) - sum(o.other_pay) > 0 THEN "
			+" owd.create_time "
			+" ELSE NULL "
			+" END operation_time "
			+" FROM "
			+" 	td_order o "
			+" LEFT JOIN td_own_money_record owd ON o.main_order_number = owd.order_number "
			+" LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
			+" LEFT JOIN ( "
			+" 	SELECT "
			+" 		* "
			+" 	FROM "
			+" 		td_user "
			+" 	WHERE "
			+" 		user_type = 5 "
			+" ) u ON u.op_user = di.driver "
			+" LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no "
			+" LEFT JOIN td_diy_site diy ON o.diy_site_code = diy.store_code "	
			+" LEFT JOIN td_return_note rn ON o.order_number = rn.order_number	"
			+" WHERE "
			+" 	diy.city LIKE ?3 "
			+" AND o.deliver_type_title = '送货上门' "
			+" AND o.status_id >= 4 "
			+" AND o.status_id NOT IN (7, 8) "
			+" AND o.send_time >= ?1 "
			+" AND o.send_time <= ?2 "
			+" AND o.diy_site_code LIKE ?4 "
			+" AND o.diy_site_id IN ?5 "
			+" GROUP BY o.main_order_number"
			+" ORDER BY o.send_time ",nativeQuery=true)
	List<DeliveryCheckReport> queryDownList(Date begin, Date end, String cityName, String diySiteCode,
			List<String> roleDiyIds);
}
