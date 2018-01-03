package com.ynyes.lyz.repository;

import com.ynyes.lyz.entity.report.TdAgencyFund;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * TdAgencyFund 虚拟数据库操作接口
 *
 * @author Sharon
 */

public interface TdAgencyFundRepo extends PagingAndSortingRepository<TdAgencyFund, Long>, JpaSpecificationExecutor<TdAgencyFund> {

    /**
     * 根据订单时间查询代收款报表数据
     *
     * @return
     */
    @Query(value = "SELECT o.id,o.diy_site_name,o.diy_site_phone,o.main_order_number,o.order_time,o.cash_balance_used,o.un_cash_balance_used,if(omr.order_number IS NULL,o.total_price,omr.payed+omr.owned) AS pay_price,omr.payed,omr.owned,u.real_name,u.username,o.shipping_name,o.shipping_phone,o.shipping_address,o.remark,o.cash_coupon,o.status_id,di.wh_no,o.total_price,o.delivery_date,o.delivery_detail_id,o.delivery_time"
            + " FROM td_order o "
            + " LEFT JOIN td_own_money_record omr ON omr.order_number=o.main_order_number"
            + " LEFT JOIN td_delivery_info_detail did ON did.sub_order_number=o.order_number"
            + " LEFT JOIN td_delivery_info di ON did.task_no = di.task_no"
            + " LEFT JOIN td_user u ON u.op_user=did.op_user"
            + " WHERE o.main_order_number IS NOT NULL AND order_time>=?1 AND order_time<=?2"
//			+ " GROUP BY o.main_order_number"
            , nativeQuery = true)
    List<TdAgencyFund> searchAllByTime(Date start, Date end);

    /**
     * 根据订单时间,门店查询代收款报表数据
     *
     * @return
     */
    @Query(value = "SELECT o.id,o.diy_site_name,o.diy_site_phone,o.main_order_number,o.order_time,o.cash_balance_used,o.un_cash_balance_used,if(omr.order_number IS NULL,o.total_price,omr.payed+omr.owned) AS pay_price,omr.payed,omr.owned,u.real_name,u.username,o.shipping_name,o.shipping_phone,o.shipping_address,o.remark,o.cash_coupon,o.status_id,di.wh_no,o.total_price,o.delivery_date,o.delivery_detail_id,o.delivery_time"
            + " FROM td_order o "
            + " LEFT JOIN td_own_money_record omr ON omr.order_number=o.main_order_number"
            + " LEFT JOIN td_delivery_info_detail did ON did.sub_order_number=o.order_number"
            + " LEFT JOIN td_delivery_info di ON did.task_no = di.task_no"
            + " LEFT JOIN td_user u ON u.op_user=did.op_user"
            + " WHERE   o.main_order_number IS NOT NULL AND"
            + " o.diy_site_code = ?1 AND order_time>=?2 AND order_time<=?3"
            + " GROUP BY o.main_order_number", nativeQuery = true)
    List<TdAgencyFund> searchAllbyDiyCodeAndTime(String diyCode, Date start, Date end);

    /**
     * 根据订单时间,城市查询代收款报表数据
     *
     * @return
     */
    @Query(value = "SELECT o.id,o.diy_site_name,o.diy_site_phone,o.main_order_number,o.order_time,o.cash_balance_used,o.un_cash_balance_used,if(omr.order_number IS NULL,o.total_price,omr.payed+omr.owned) AS pay_price,omr.payed,omr.owned,u.real_name,u.username,o.shipping_name,o.shipping_phone,o.shipping_address,o.remark,o.cash_coupon,o.status_id,di.wh_no,o.total_price,o.delivery_date,o.delivery_detail_id,o.delivery_time"
            + " FROM td_order o "
            + " LEFT JOIN td_own_money_record omr ON omr.order_number=o.main_order_number"
            + " LEFT JOIN td_delivery_info_detail did ON did.sub_order_number=o.order_number"
            + " LEFT JOIN td_delivery_info di ON did.task_no = di.task_no"
            + " LEFT JOIN td_user u ON u.op_user=did.op_user"
            + " WHERE   o.main_order_number IS NOT NULL AND"
            + " o.city = ?1 AND order_time>=?2 AND order_time<=?3"
            + " GROUP BY o.main_order_number", nativeQuery = true)
    List<TdAgencyFund> searchAllbyCityAndTime(String city, Date start, Date end);

    /**
     * 调用存储过程
     *
     * @return
     */
    @Query(value = "{call insertAgencyFund_initial(?1,?2,?3)}", nativeQuery = true)
    void callInsertAgencyFund(Date start, Date end, String username);


    @Query(value = " SELECT "
            + "  UUID() id, "
            + " 	diy.city city_name, "
            + " 	o.diy_site_name diy_site_name, "
            + "  o.main_order_number main_order_number, "
            + " 	o.status_id status_id, "
            + " 	o.seller_real_name seller_real_name, "
            + " 	o.seller_username seller_username, "
            + " 	o.order_time order_time, "
            + " 	o.real_user_real_name real_user_real_name, "
            + " 	o.real_user_username real_user_username, "
            + " 	SUM(o.total_price) - SUM(o.other_pay) pay_price, "
            + "  owd.money pay_money, "
            + " 	owd.pos pay_pos, "
            + " 	wh.wh_name wh_name, "
            + " 	du.real_name delivery_name, "
            + " 	du.username delivery_phone, "
            + " 	o.shipping_name shipping_name, "
            + " 	o.shipping_phone shipping_phone, "
            + " 	o.shipping_address shipping_address, "
            + " 	o.delivery_date delivery_date, "
            + " 	o.delivery_detail_id delivery_detail_id, "
            + " 	o.delivery_time delivery_time, "
            + "	o.remark remark, "
            + "  o.user_id user_id, "
            + "  owd.is_enable is_enable, "
            + "  owd.ispassed is_passed "
            + " FROM "
            + " 	td_order o "
            + " LEFT JOIN td_diy_site diy ON o.diy_site_id = diy.id "
            + " LEFT JOIN td_own_money_record owd ON o.main_order_number = owd.order_number "
            + " LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
            + " LEFT JOIN td_user du ON du.op_user = di.driver "
            + " LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no "
            + " WHERE "
            + " wh.wh_number LIKE ?6 "
            + " AND o.deliver_type_title = '送货上门' "
            + " AND o.status_id IN(4,5, 6, 9, 10, 12) "
            + " AND owd.create_time >= ?1 "
            + " AND owd.create_time <= ?2 "
            + " AND diy.city LIKE ?3 "
            + " AND diy.store_code LIKE ?4 "
            + " AND diy.id IN ?5 "
            + " GROUP BY o.main_order_number "
            + " ORDER BY order_time DESC; ", nativeQuery = true)
    List<TdAgencyFund> queryDownList(Date begin, Date end, String cityName, String diySiteCode, List<String> roleDiyIds, String warehouse);

    @Query(value = " SELECT DISTINCT "
            + " 	diy.city city, "
            + " 	m.c_wh_name wh_name, "
            + " 	diy.title diy_site_name, "
            + " 	CASE diy.is_direct "
            + " WHEN 1 THEN "
            + " 	'直营' "
            + " ELSE "
            + " 	'加盟' "
            + " END AS store_property, "
            + "  CASE d.receiver_is_member "
            + " WHEN 1 THEN "
            + " 	'主家' "
            + " ELSE "
            + " 	'会员' "
            + " END AS receiver_type, "
            + "  o.main_order_number main_order_number, "
            + "  m.c_out_no c_out_no, "
            + "  o.send_time, "
            + "  o.shipping_address, "
            + "  o.seller_real_name seller_name, "
            + "  de.real_name delivery_real_name, "
            + "  d.agency_refund agency_refund, "
            + "  d.delivery_cash delivery_cash, "
            + "  d.delivery_pos delivery_pos, "
            + "  d.remark remark, "
            + "  CASE owd.ispassed "
            + " WHEN 1 THEN "
            + " 	'是' "
            + " ELSE "
            + " 	'否' "
            + " END AS is_passed, "
            + "  d.redundant redundant, "
            + " IFNULL(d.delivery_cash,0)+ IFNULL(d.delivery_pos,0) - IFNULL(d.redundant,0) AS redundant_company, "
            + " o.remark order_remark "
            + " FROM "
            + " 	td_order o "
            + " LEFT JOIN td_order_data d ON o.main_order_number = d.main_order_number "
            + " LEFT JOIN tbw_out_m m ON o.main_order_number = m.c_value1 "
            + " AND m.c_value3 = '已装车' "
            + " LEFT JOIN td_diy_site diy ON o.diy_site_id = diy.id "
            + " LEFT JOIN td_delivery_info di ON o.main_order_number = di.order_number "
            + " LEFT JOIN td_own_money_record owd ON o.main_order_number = owd.order_number "
            + " LEFT JOIN ( "
            + " 	SELECT "
            + " 		* "
            + " 	FROM "
            + " 		td_user "
            + " 	WHERE "
            + " 		user_type = 5 "
            + " ) de ON de.op_user = di.driver "
            + " WHERE "
            + " 	o.status_id > 3 "
            + " AND o.status_id NOT IN (7, 8) "
            + " AND di.end_dt >= ?1 "
            + " AND di.end_dt < ?2 "
            + " AND diy.city LIKE ?3 "
            + " AND diy.store_code LIKE ?4 "
            + " AND diy.id IN ?5 "
            + " AND m.c_wh_no LIKE ?6 GROUP BY o.main_order_number; ", nativeQuery = true)
    List<Object> queryDeliveryDownList(Date begin, Date end, String cityName, String diyCode, List<String> roleDiyIds, String warehouse);

    @Query(value = " SELECT "
            + "   DISTINCT "
            + "   diy.city              city, "
            + "   diy.title             store_name, "
            + "   CASE diy.is_direct "
            + "   WHEN 1 "
            + "     THEN '直营' "
            + "   ELSE '加盟' "
            + "   END AS                store_property, "
            + "   o.real_user_real_name real_name, "
            + "   o.username            username, "
            + "   o.main_order_number   main_order_number, "
            + "   m.c_out_no            c_out_no, "
            + "   o.seller_real_name    seller_real_name, "
            + "   od.total_goods_price  total_goods_price, "
            + "   od.member_discount    member_discount, "
            + "   od.activity_sub       activity_sub_price, "
            + "   od.pro_coupon_fee     pro_coupon_fee, "
            + "   od.cash_coupon_fee    cash_coupon_fee, "
            + "   od.balance_used       balance_used, "
            + "   od.online_pay         online_pay, "
            + "   od.delivery_fee       delivery_fee, "
            + "   od.left_price         left_price, "
            + "   od.agency_refund      agency_refund, "
            + "   od.delivery_cash      delivery_cash, "
            + "   od.delivery_pos       delivery_pos, "
            + "   od.remark             remark, "
            + "   od.seller_cash        seller_cash, "
            + "   od.seller_pos         seller_pos, "
            + "   od.seller_other       seller_other, "
            + "   od.redundant          redundant, "
            + "   od.due                due, "
            + "   od.refund_balance     refund_balance, "
            + "   CASE od.receiver_is_member "
            + "   WHEN 1 "
            + "     THEN '主家' "
            + "   ELSE '会员' "
            + "   END AS                receiver_type, "
            + "   o.shipping_name       shipping_name, "
            + "   o.shipping_phone      shipping_phone, "
            + "   o.shipping_address    shipping_address, "
            + "   m.c_wh_name           c_wh_name, "
            + "   de.real_name          delivery_real_name "
            + " FROM td_order o LEFT JOIN td_diy_site diy ON o.diy_site_id = diy.id "
            + "   LEFT JOIN tbw_out_m m ON o.main_order_number = m.c_value1 AND m.c_value3 = '已装车' "
            + "   LEFT JOIN td_order_data od ON o.main_order_number = od.main_order_number "
            + "   LEFT JOIN td_delivery_info di ON o.main_order_number = di.order_number "
            + "   LEFT JOIN (SELECT * "
            + "              FROM td_user "
            + "              WHERE user_type = 5) de ON de.op_user = di.driver "
            + " WHERE o.status_id > 3 "
            + "       AND o.status_id NOT IN (7, 8) "
            + "       AND di.end_dt >= ?1 "
            + "       AND di.end_dt < ?2 "
            + "       AND diy.city LIKE ?3 "
            + "       AND diy.store_code LIKE ?4 "
            + "       AND diy.id IN ?5 ", nativeQuery = true)
    List<Object> queryStoreDownList(Date begin, Date end, String cityName, String diyCode, List<String> roleDiyIds);
}
