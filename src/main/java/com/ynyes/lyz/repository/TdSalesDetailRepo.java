package com.ynyes.lyz.repository;

import com.ynyes.lyz.entity.TdSalesDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * TdSalesDetail 虚拟数据库操作接口
 *
 * @author Sharon
 */

public interface TdSalesDetailRepo extends PagingAndSortingRepository<TdSalesDetail, Long>, JpaSpecificationExecutor<TdSalesDetail> {

    /**
     * 调用存储过程
     *
     * @return
     */
    @Query(value = "{call insertSalesDetail_initial(?1,?2,?3)}", nativeQuery = true)
    void callInsertSalesDetail(Date start, Date end, String username);

    /**
     * 查询下载的list
     *
     * @param begin       开始时间
     * @param end         结束时间
     * @param cityName    城市
     * @param diySiteCode 门店
     * @param roleDiyIds  权限门店
     * @return
     */
    @Query(value = " SELECT "
            + " 	og.id id, "
            + " 	o.diy_site_name diy_site_name, "
            + " 	o.main_order_number main_order_number, "
            + " 	o.order_number order_number, "
            + " 	o.order_time order_time, "
            + " 	di.begin_dt sales_time, "
            + " 	o.status_id status_id, "
            + " 	su.real_name seller_real_name, "
            + "	o.user_id, "
            + " 	u.real_name real_name, "
            + " 	IFNULL( "
            + " 		o.real_user_username, "
            + " 		o.username "
            + " 	) username, "
            + " 	og.sku sku, "
            + " 	og.goods_title goods_title, "
            + " 	og.quantity quantity, "
            + " 	CASE "
            + " WHEN IFNULL(o.dif_fee, 0) > 0  "
            + " THEN IFNULL(og.real_price,0) "
            + " ELSE "
            + " 	og.price "
            + " END AS price, "
            + " 	og.quantity * og.price total_price, "
            + " 	o.cash_coupon cash_coupon, "
            + " 	og.brand_title brand_title, "
            + " 	ppc.title goods_parent_type_title, "
            + " 	pc.title goods_type_title, "
            + " 	o.deliver_type_title deliver_type_title, "
            + " 	wh.wh_name wh_name, "
            + " 	u1.real_name deliver_real_name, "
            + " 	u1.username deliver_username, "
            + " 	o.shipping_name shipping_name, "
            + " 	o.shipping_phone shipping_phone, "
            + " 	o.shipping_address shipping_address, "
            + " 	o.remark remark, "
            + " 	diy.city city_name, "
            + " 	o.diy_site_code diy_site_code, "
            + " 	o.diy_site_id diy_id "
            + " FROM "
            + " 	td_order o "
            + " INNER JOIN td_order_goods og ON og.td_order_id = o.id "
            + " OR og.presented_list_id = o.id "
            + " OR og.gift_list_id = o.id "
            + " LEFT JOIN td_user u ON u.username = IFNULL( "
            + " 	o.real_user_username, "
            + " 	o.username "
            + " ) "
            + " LEFT JOIN td_product_category pc ON pc.id = ( "
            + " 	SELECT "
            + " 		a.category_id "
            + " 	FROM "
            + " 		td_goods a "
            + " 	WHERE "
            + " 		a.id = og.goods_id "
            + " ) "
            + " LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id "
            + " LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
            + " LEFT JOIN ( "
            + " 	SELECT "
            + " 		* "
            + " 	FROM "
            + " 		td_user "
            + " 	WHERE "
            + " 		user_type = 5 "
            + " ) u1 ON u1.op_user = di.driver "
            + " LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no "
            + " LEFT JOIN td_user su ON su.id = o.seller_id "
            + " LEFT JOIN td_diy_site diy ON o.diy_site_id = diy.id "
            + " WHERE "
            + " 	o.order_time >=?1 "
            + " AND o.order_time <=?2 "
            + " AND o.status_id NOT IN (1, 2, 7, 8) "
            + " AND diy.city LIKE ?3 "
            + " AND o.diy_site_code LIKE ?4 "
            + " AND o.diy_site_id IN ?5 "
            + " UNION ALL "
            + " 	SELECT "
            + " 		og.id id, "
            + " 		o.diy_site_name diy_site_name, "
            + " 		rn.return_number main_order_number, "
            + " 		rn.order_number order_number, "
            + " 		rn.order_time order_time, "
            + " 		rn.receive_time sales_time, "
            + " 		o.status_id status_id, "
            + " 		o.seller_real_name seller_real_name, "
            + "		o.user_id user_id, "
            + " 		u.real_name real_name, "
            + " 		rn.username username, "
            + " 		og.sku sku, "
            + " 		og.goods_title goods_title ,- og.quantity quantity, "
            + " 		og.return_unit_price price , "
            + "		- og.quantity * og.price total_price, "
            + " 		o.cash_coupon cash_coupon, "
            + " 		og.brand_title brand_title, "
            + " 		ppc.title goods_parent_type_title, "
            + " 		pc.title goods_type_title, "
            + " 		o.deliver_type_title deliver_type_title, "
            + " 		'' wh_name, "
            + " 		u1.real_name deliver_real_name, "
            + " 		u1.username deliver_username, "
            + " 		o.shipping_name shipping_name, "
            + " 		o.shipping_phone shipping_phone, "
            + " 		o.shipping_address shipping_address, "
            + " 		o.remark remark, "
            + " 		o.city city_name, "
            + " 		o.diy_site_code diy_site_code, "
            + " 		o.diy_site_id diy_id "
            + " 	FROM "
            + " 		td_return_note rn "
            + " 	INNER JOIN td_order o ON o.order_number = rn.order_number "
            + " 	AND o.status_id NOT IN (1, 2,6, 7, 8) "
            + " 	LEFT JOIN td_user u ON u.username = o.username "
            + " 	LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
            + " 	LEFT JOIN td_user u1 ON u1.op_user = rn.driver "
            + " 	LEFT JOIN td_order_goods og ON og.td_return_id = rn.id "
            + " 	LEFT JOIN td_goods g ON g.id = og.goods_id "
            + " 	LEFT JOIN td_product_category pc ON pc.id = g.category_id "
            + " 	LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id "
            + " 	LEFT JOIN td_diy_site diy ON o.diy_site_id = diy.id "
            + " 	WHERE "
            + " 		o.order_time >=?1 "
            + " 	AND o.order_time <=?2 "
            + " 	AND o.status_id NOT IN (1, 2, 7, 8) "
            + " 	AND diy.city LIKE ?3 "
            + " 	AND o.diy_site_code LIKE ?4 "
            + " 	AND o.diy_site_id IN ?5 "
            + "     AND  o.order_number is not NULL "
            + " 	ORDER BY "
            + " 		order_time DESC;", nativeQuery = true)
    List<TdSalesDetail> queryDownList(Date begin, Date end, String cityName, String diySiteCode, List<String> roleDiyIds);

    @Query(value = " SELECT "
            + " 	CASE o.diy_site_name "
            + " WHEN '泸州分销仓库' THEN "
            + " 	'泸州市' "
            + " WHEN '绵阳分销仓库' THEN "
            + " 	'绵阳市' "
            + " WHEN '华阳分销仓' THEN "
            + " 	'龙泉' "
            + " WHEN '龙泉分销仓' THEN "
            + " 	'龙泉' "
            + " WHEN '南充分销仓库' THEN "
            + " 	'南充市' "
            + " WHEN '郫县分销仓库' THEN "
            + " 	'郫县' "
            + " ELSE "
            + " 	o.city "
            + " END AS city, "
            + "  o.diy_site_code, "
            + "  CASE o.diy_site_name WHEN '华阳分销仓' THEN '龙泉分销仓' ELSE o.diy_site_name END AS diy_site_name, "
            + "  IFNULL( "
            + " 	o.main_order_number, "
            + " 	o.order_number "
            + " ) order_number, "
            + "  o.order_time, "
            + "  DATE_FORMAT(o.order_time, '%Y-%m-%d') order_time_format, "
            + "  o.pay_type_title, "
            + "  o.deliver_type_title, "
            + "  o.pay_time, "
            + "  o.status_id, "
            + "  sum( "
            + " 	IFNULL(o.total_goods_price, 0) "
            + " ) total_goods_price, "
            + "  sum(IFNULL(o.deliver_fee, 0)) deliver_fee, "
            + "  sum( "
            + " 	IFNULL(o.total_goods_price, 0) "
            + " ) + sum(IFNULL(o.deliver_fee, 0)) AS total_price, "
            + "  CASE "
            + " WHEN o.status_id >= 3 THEN "
            + " 	IFNULL(d.due, 0) "
            + " ELSE "
            + " 	SUM(IFNULL(o.not_payed_fee, 0)) "
            + " END AS not_payed_fee, "
            + "  o.username, "
            + "  o.seller_real_name, "
            + "  o.seller_username, "
            + "  o.real_user_real_name, "
            + "  o.real_user_username "
            + " FROM "
            + " 	td_order o "
            + " LEFT JOIN td_order_data d ON o.main_order_number = d.main_order_number "
            + " WHERE "
            + " 	o.diy_site_code LIKE '%FX%' "
            + " AND o.status_id NOT IN (7, 8) "
            +"  AND o.order_number is NOT NULL"
            + " AND o.order_time >= ?1 "
            + " AND o.order_time < ?2 "
            + " AND o.city LIKE ?3 "
            + " AND o.diy_site_code LIKE ?4  "
            + " AND o.diy_site_id IN ?5 "
            + " GROUP BY "
            + " 	IFNULL( "
            + " 		o.main_order_number, "
            + " 		o.order_number "
            + " 	); ", nativeQuery = true)
    List<Object> queryStoreSalesDownList(Date begin, Date end, String cityName, String diyCode, List<String> roleDiyIds);
}
