package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.GarmentFranchisorReport;

/**
 *  加盟商对账报表
 * 
 * @author
 *
 */

public interface TdGarmentFranchisorReportRepo extends
		PagingAndSortingRepository<GarmentFranchisorReport, Long>,
		JpaSpecificationExecutor<GarmentFranchisorReport> {

	
	@Query(value=" SELECT "
			+"	UUID() id, "
			+" 	diy.city city_name, "
			+" 	o.diy_site_name diy_site_name, "
			+" 	wh.wh_name wh_name, "
			+" 	du.real_name delivery_name, "
			+" 	du.username delivery_phone, "
			+"	o.user_id user_id, "
			+" 	o.real_user_real_name real_user_real_name, "
			+" 	o.real_user_username real_user_username, "
			+" 	o.seller_real_name seller_real_name, "
			+" 	o.main_order_number main_order_number, "
			+" 	o.order_number order_number, "
			+" 	o.order_time order_time, "
			+" 	o.delivery_time delivery_time, "
			+" 	o.status_id status_id, "
			+" 	IFNULL(o.cash_balance_used, 0) + IFNULL(o.un_cash_balance_used, 0) actual_pay, "
			+" 	o.other_pay other_pay, "
			+" 	0 cash_pay, "
			+" 	o.cash_coupon cash_coupon, "
			+"	o.pay_type_id pay_type_id "
			+" FROM "
			+" 	td_order o "
			+" LEFT JOIN td_diy_site diy ON o.diy_site_id = diy.id "
			+" LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
			+" LEFT JOIN td_user du ON du.op_user = di.driver "
			+" LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no "
			+" WHERE "
			+" 	o.deliver_type_title = '送货上门' "
			+" AND diy.is_direct = 0 "
			+" AND o.status_id NOT IN (7, 8) "
			+" AND o.send_time >= ?1 "
			+" AND o.send_time <= ?2 "
			+" AND diy.city LIKE ?3 "
			+" AND diy.store_code LIKE ?4 "
			+" AND diy.id IN ?5 "
			+" UNION ALL "
			+" 	SELECT "
			+"		UUID() id, "
			+" 		diy.city city_name, "
			+" 		o.diy_site_name diy_site_name, "
			+" 		wh.wh_name wh_name, "
			+" 		du.real_name delivery_name, "
			+" 		du.username delivery_phone, "
			+"		o.user_id user_id, "
			+" 		o.real_user_real_name real_user_real_name, "
			+" 		o.real_user_username real_user_username, "
			+" 		o.seller_real_name seller_real_name, "
			+" 		rn.return_number main_order_number, "
			+" 		o.order_number order_number, "
			+" 		rn.order_time order_time, "
			+" 		o.delivery_time delivery_time, "
			+" 		rn.status_id, "
			+" 		IFNULL(roi.prepay_amt, 0) actual_pay, "
			+" 		0 other_pay, "
			+" 		IFNULL(ri.amount, 0) cash_pay, "
			+" 		o.cash_coupon cash_coupon, "
			+"		o.pay_type_id pay_type_id "
			+" 	FROM "
			+" 		td_return_note rn "
			+" 	LEFT JOIN td_order o ON o.order_number = rn.order_number "
			+" 	LEFT JOIN td_diy_site diy ON rn.diy_site_id = diy.id "
			+" 	LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
			+" 	LEFT JOIN td_user du ON du.op_user = di.driver "
			+" 	LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no "
			+" 	LEFT JOIN td_cash_refund_inf ri ON rn.return_number = ri.return_number "
			+" 	LEFT JOIN td_return_order_inf roi ON rn.return_number = roi.return_number "
			+" 	WHERE "
			+" 		o.deliver_type_title = '送货上门' "
			+" 	AND diy.is_direct = 0 "
			+" 	AND rn.status_id = 5 "
			+" 	AND rn.remark_info != '用户取消订单，退货' "
			+" 	AND rn.return_time >= ?1 "
			+" 	AND rn.return_time <= ?2 "
			+" 	AND diy.city LIKE ?3 "
			+" 	AND diy.store_code like ?4 "
			+" 	AND diy.id IN ?5 order by order_time DESC ",nativeQuery=true)
	List<GarmentFranchisorReport> queryDownList(Date begin, Date end, String cityName, String diySiteCode,
			List<String> roleDiyIds);
}
