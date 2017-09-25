package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdSalesDetail;

/**
 * TdSalesDetail 虚拟数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdSalesDetailRepo extends PagingAndSortingRepository<TdSalesDetail, Long>, JpaSpecificationExecutor<TdSalesDetail> {
	
	/**
	 * 调用存储过程
	 * @return
	 */
	@Query(value = "{call insertSalesDetail_initial(?1,?2,?3)}",nativeQuery = true)
	void callInsertSalesDetail(Date start,Date end,String username);
	
	/**
	 * 查询下载的list
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param cityName 城市
	 * @param diySiteCode 门店
	 * @param roleDiyIds 权限门店
	 * @return
	 */
	@Query(value =" SELECT "
			+" 	og.id id, "
			+" 	o.diy_site_name diy_site_name, "
			+" 	o.main_order_number main_order_number, "
			+" 	o.order_number order_number, "
			+" 	o.order_time order_time, "
			+" 	di.begin_dt sales_time, "
			+" 	o.status_id status_id, "
			+" 	su.real_name seller_real_name, "
			+"	o.user_id, "
			+" 	u.real_name real_name, "
			+" 	IFNULL( "
			+" 		o.real_user_username, "
			+" 		o.username "
			+" 	) username, "
			+" 	og.sku sku, "
			+" 	og.goods_title goods_title, "
			+" 	og.quantity quantity, "
			+" 	CASE "
			+" WHEN IFNULL(o.dif_fee, 0) > 0  "
			+" THEN IFNULL(og.real_price,0) "
			+" ELSE "
			+" 	og.price "
			+" END AS price, "
			+" 	og.quantity * og.price total_price, "
			+" 	o.cash_coupon cash_coupon, "
			+" 	og.brand_title brand_title, "
			+" 	ppc.title goods_parent_type_title, "
			+" 	pc.title goods_type_title, "
			+" 	o.deliver_type_title deliver_type_title, "
			+" 	wh.wh_name wh_name, "
			+" 	u1.real_name deliver_real_name, "
			+" 	u1.username deliver_username, "
			+" 	o.shipping_name shipping_name, "
			+" 	o.shipping_phone shipping_phone, "
			+" 	o.shipping_address shipping_address, "
			+" 	o.remark remark, "
			+" 	diy.city city_name, "
			+" 	o.diy_site_code diy_site_code, "
			+" 	o.diy_site_id diy_id "
			+" FROM "
			+" 	td_order o "
			+" INNER JOIN td_order_goods og ON og.td_order_id = o.id "
			+" OR og.presented_list_id = o.id "
			+" OR og.gift_list_id = o.id "
			+" LEFT JOIN td_user u ON u.username = IFNULL( "
			+" 	o.real_user_username, "
			+" 	o.username "
			+" ) "
			+" LEFT JOIN td_product_category pc ON pc.id = ( "
			+" 	SELECT "
			+" 		a.category_id "
			+" 	FROM "
			+" 		td_goods a "
			+" 	WHERE "
			+" 		a.id = og.goods_id "
			+" ) "
			+" LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id "
			+" LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
			+" LEFT JOIN ( "
			+" 	SELECT "
			+" 		* "
			+" 	FROM "
			+" 		td_user "
			+" 	WHERE "
			+" 		user_type = 5 "
			+" ) u1 ON u1.op_user = di.driver "
			+" LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no "
			+" LEFT JOIN td_user su ON su.id = o.seller_id "
			+" LEFT JOIN td_diy_site diy ON o.diy_site_id = diy.id "
			+" WHERE "
			+" 	o.order_time >=?1 "
			+" AND o.order_time <=?2 "
			+" AND o.status_id NOT IN (1, 2, 7, 8) "
			+" AND diy.city LIKE ?3 "
			+" AND o.diy_site_code LIKE ?4 "
			+" AND o.diy_site_id IN ?5 "
			+" UNION ALL "
			+" 	SELECT "
			+" 		og.id id, "
			+" 		o.diy_site_name diy_site_name, "
			+" 		rn.return_number main_order_number, "
			+" 		rn.order_number order_number, "
			+" 		rn.order_time order_time, "
			+" 		rn.receive_time sales_time, "
			+" 		o.status_id status_id, "
			+" 		o.seller_real_name seller_real_name, "
			+"		o.user_id user_id, "	
			+" 		u.real_name real_name, "
			+" 		rn.username username, "
			+" 		og.sku sku, "
			+" 		og.goods_title goods_title ,- og.quantity quantity, "
			+" 		og.return_unit_price price , "
			+"		- og.quantity * og.price total_price, "
			+" 		o.cash_coupon cash_coupon, "
			+" 		og.brand_title brand_title, "
			+" 		ppc.title goods_parent_type_title, "
			+" 		pc.title goods_type_title, "
			+" 		o.deliver_type_title deliver_type_title, "
			+" 		'' wh_name, "
			+" 		u1.real_name deliver_real_name, "
			+" 		u1.username deliver_username, "
			+" 		o.shipping_name shipping_name, "
			+" 		o.shipping_phone shipping_phone, "
			+" 		o.shipping_address shipping_address, "
			+" 		o.remark remark, "
			+" 		o.city city_name, "
			+" 		o.diy_site_code diy_site_code, "
			+" 		o.diy_site_id diy_id "
			+" 	FROM "
			+" 		td_return_note rn "
			+" 	INNER JOIN td_order o ON o.order_number = rn.order_number "
			+" 	AND o.status_id NOT IN (1, 2,6, 7, 8) "
			+" 	LEFT JOIN td_user u ON u.username = o.username "
			+" 	LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
			+" 	LEFT JOIN td_user u1 ON u1.op_user = rn.driver "
			+" 	LEFT JOIN td_order_goods og ON og.td_return_id = rn.id "
			+" 	LEFT JOIN td_goods g ON g.id = og.goods_id "
			+" 	LEFT JOIN td_product_category pc ON pc.id = g.category_id "
			+" 	LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id "
			+" 	LEFT JOIN td_diy_site diy ON o.diy_site_id = diy.id "
			+" 	WHERE "
			+" 		o.order_time >=?1 "
			+" 	AND o.order_time <=?2 "
			+" 	AND o.status_id NOT IN (1, 2, 7, 8) "
			+" 	AND diy.city LIKE ?3 "
			+" 	AND o.diy_site_code LIKE ?4 "
			+" 	AND o.diy_site_id IN ?5 "
			+" 	ORDER BY "
			+" 		order_time DESC;",nativeQuery = true)
	List<TdSalesDetail> queryDownList(Date begin,Date end,String cityName,String diySiteCode,List<String> roleDiyIds);
}
