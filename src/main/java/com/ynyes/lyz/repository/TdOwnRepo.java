package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdOwn;
import com.ynyes.lyz.entity.TdSubOwn;

/**
 *  欠款报表
 * 
 * @author yanle
 *
 */

public interface TdOwnRepo extends
		PagingAndSortingRepository<TdOwn, Long>,
		JpaSpecificationExecutor<TdOwn> {


	/**
	 * 查询欠款报表下载的list
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
	@Query(value =" SELECT DISTINCT "
			+" 	UUID() id, "
			+" 	o.diy_site_name, "
			+" 	o.main_order_number main_order_number, "
			+" 	'13' status_id, "
			+" 	o.order_time order_time, "
			+" 	o.seller_real_name seller_real_name, "
			+" 	o.seller_username seller_username, "
			+"	o.user_id user_id, "
			+" 	o.username username, "
			+" 	o.real_user_real_name real_user_real_name, "
			+" 	IFNULL(sum(o.total_price), 0) - IFNULL(sum(o.other_pay), 0) - IFNULL(omr.money, 0) - IFNULL(omr.pos, 0) - IFNULL(omr.back_money, 0) - IFNULL(omr.back_pos, 0) - IFNULL(omr.back_other, 0) owned, "
			+" 	wh.wh_name wh_name, "
			+" 	du.real_name du_real_name, "
			+" 	du.username du_username, "
			+" 	o.shipping_address shipping_address, "
			+" 	o.remark remark "
			+" FROM "
			+" 	td_order o "
			+" LEFT JOIN td_own_money_record omr ON omr.order_number = o.main_order_number "
			+" LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
			+" LEFT JOIN td_user du ON du.op_user = di.driver "
			+" LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no "
			+" WHERE "
			+" 	o.send_time >= ?1 "
			+" AND o.send_time <= ?2  "
			+" AND o.deliver_type_title = '送货上门'  "
			+" AND o.city LIKE ?3 "
			+" AND o.total_price - o.other_pay > 0  "
			+" AND o.diy_site_code LIKE ?4   "
			+" AND o.diy_site_id IN ?5   "
			+" GROUP BY "
			+" 	main_order_number "
			+" ORDER BY "
			+" 	order_time DESC; ",nativeQuery=true)
	List<TdOwn> queryDownList(Date begin, Date end, String cityName,
			String diySiteCode, List<String> roleDiyIds);
	
		
	@Query(value=" SELECT UUID() id, "
			+ "	o.order_number order_number, "
			+ "	IFNULL(o.total_price,0) total_price , "
			+ "	IFNULL(o.other_pay,0) other_pay, "
			+ "	IFNULL(owd.money,0) money, "
			+ "	IFNULL(owd.pos,0) pos, "
			+ "	IFNULL(owd.back_money,0) back_money, "
			+ "	IFNULL(owd.back_pos,0) back_pos, "
			+ "	IFNULL(owd.back_other,0) back_other "
			+ " FROM "
			+ "	td_order o "
			+ " LEFT JOIN td_own_money_record owd ON o.main_order_number = owd.order_number "
			+ " where o.main_order_number = ?1 ; ",nativeQuery=true)
	List<TdSubOwn> queryDownListDetail(String orderNumber);
}
