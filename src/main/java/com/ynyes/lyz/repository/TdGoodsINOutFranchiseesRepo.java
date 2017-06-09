package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdGoodsInOut;
import com.ynyes.lyz.entity.TdGoodsInOutFranchisees;

/**
 * TdGoodsINOutFranchiseesRepo 加盟商进出货明细表
 * 
 * @author
 *
 */

public interface TdGoodsINOutFranchiseesRepo extends
		PagingAndSortingRepository<TdGoodsInOutFranchisees, Long>,
		JpaSpecificationExecutor<TdGoodsInOutFranchisees> {

	/**
	 * 调用存储过程
	 * 
	 * @return
	 */
	@Query(value = "{call insert_goods_in_out_initial(?1,?2,?3)}", nativeQuery = true)
	void callinsertGoodsInOutInitial(Date start, Date end, String username);

	/**
	 * 查询下载的list
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
	@Query(value = "SELECT"
			+"		og.id id,"
			+"		o.diy_site_name diy_site_name,"
			+"		o.main_order_number main_order_number,"
			+"		o.order_number order_number,"
			+"		o.order_time order_time,"
			+"		o.send_time sales_time,"
			+"		o.status_id status_id,"
			+"		su.real_name seller_real_name,"
			+"		u.real_name real_name,"
			+"		IFNULL("
			+"			o.real_user_username,"
			+"			o.username"
			+"		) username,"
			+"		og.sku sku,"
			+"		og.goods_title goods_title,"
			+"		og.quantity quantity,"
			+"		og.price price,"
			+"		og.quantity * og.price total_price,"
			+"		IFNULL(og.jx_price, 0) jx_price,"
			+"		IFNULL(og.jx_price, 0) * og.quantity jx_total_price,"
			+"		o.cash_coupon cash_coupon,"
			+"		og.brand_title brand_title,"
			+"		ppc.title goods_parent_type_title,"
			+"		pc.title goods_type_title,"
			+"		o.deliver_type_title deliver_type_title,"
			+"		wh.wh_name wh_name,"
			+"		u1.real_name deliver_real_name,"
			+"		u1.username deliver_username,"
			+"		o.shipping_name shipping_name,"
			+"		o.shipping_phone shipping_phone,"
			+"		o.shipping_address shipping_address,"
			+"		o.remark remark,"
			+"		o.city city_name,"
			+"		o.diy_site_code diy_site_code,"
			+"		o.diy_site_id diy_id,"
			+"		o.user_id user_id"
			+"	FROM"
			+"		td_order o"
			+"	INNER JOIN td_order_goods og ON og.td_order_id = o.id"
			+""
			+"	INNER JOIN td_diy_site ds ON ds.id = o.diy_site_id"
			+""
			+"	OR og.presented_list_id = o.id"
			+"	OR og.gift_list_id = o.id"
			+"	LEFT JOIN td_user u ON u.username = IFNULL("
			+"		o.real_user_username,"
			+"		o.username"
			+"		)"
			+"	LEFT JOIN td_product_category pc ON pc.id = ("
			+"	SELECT"
			+"		a.category_id"
			+"	FROM"
			+"		td_goods a"
			+"	WHERE"
			+"		a.id = og.goods_id"
			+")"
			+"	LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id"
			+"	LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number"
			+"	LEFT JOIN ("
			+"	SELECT"
			+"		*"
			+"	FROM"
			+"		td_user"
			+"	WHERE"
			+"		user_type = 5"
			+"		) u1 ON u1.op_user = di.driver"
			+"	LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no"
			+"	LEFT JOIN td_user su ON su.id = o.seller_id"
			+"	WHERE"
			+"		o.deliver_type_title = '送货上门'"
			+"	AND di.end_dt >= ?1"
			+"	AND di.end_dt <= ?2"
			+"	AND o.status_id NOT IN (1, 2, 7, 8)"
			+"	AND o.city LIKE ?3"
			+"	AND o.diy_site_code LIKE ?4 "
			+"  AND o.diy_site_id IN ?5"
			+"	AND ds.is_direct = 0"
			+"	UNION ALL"
			+"	SELECT"
			+"		og.id id,"
			+"		o.diy_site_name diy_site_name,"
			+"		rn.return_number main_order_number,"
			+"		rn.order_number order_number,"
			+"		rn.order_time order_time,"
			+"		IFNULL("
			+"			rn.receive_time,"
			+"			rn.recv_time"
			+"		) sales_time,"
			+"		o.status_id status_id,"
			+"		o.seller_real_name seller_real_name,"
			+"		u.real_name real_name,"
			+"		rn.username username,"
			+"		og.sku sku,"
			+"		og.goods_title goods_title,"
			+"		- og.quantity quantity,"
			+"		og.price,"
			+"		- og.quantity * og.price total_price,"
			+""
			+"		IFNULL(og.jx_price, 0) jx_price,"
			+"		IFNULL(og.jx_price, 0) * og.quantity jx_total_price,"
			+""
			+"		o.cash_coupon cash_coupon,"
			+"		og.brand_title brand_title,"
			+"		"
			+"		ppc.title goods_parent_type_title,"
			+"		pc.title goods_type_title,"
			+"		o.deliver_type_title deliver_type_title,"
			+"		wh.wh_name wh_name,"
			+"		u1.real_name deliver_real_name,"
			+"		u1.username deliver_username,"
			+"		o.shipping_name shipping_name,"
			+"		o.shipping_phone shipping_phone,"
			+"		o.shipping_address shipping_address,"
			+"		o.remark remark,"
			+"		o.city city_name,"
			+"		o.diy_site_code diy_site_code,"
			+"		o.diy_site_id diy_id,"
			+"		o.user_id user_id"
			+"	FROM"
			+"		td_return_note rn"
			+"	INNER JOIN td_order o ON o.order_number = rn.order_number"
			+"	"
			+"	INNER JOIN td_diy_site ds ON ds.id = rn.diy_site_id"
			+""
			+"	AND o.status_id NOT IN (1, 2, 7, 8)"
			+"	LEFT JOIN td_user u ON u.username = o.username"
			+"	LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number"
			+"	LEFT JOIN td_user u1 ON u1.op_user = rn.driver"
			+"	LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no"
			+"	LEFT JOIN td_order_goods og ON og.td_return_id = rn.id"
			+"	LEFT JOIN td_goods g ON g.id = og.goods_id"
			+"	LEFT JOIN td_product_category pc ON pc.id = g.category_id"
			+"	LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id"
			+"	WHERE"
			+"		o.deliver_type_title = '送货上门'"
			+"	AND ("
			+"		("
			+"			rn.receive_time >= ?1 "
			+"			AND rn.receive_time <= ?2 "
			+"		)"
			+"		OR ("
			+"			rn.recv_time >= ?1"
			+"			AND rn.recv_time <= ?2"
			+"		)"
			+"	)"
			+"	AND o.status_id NOT IN (1, 2, 7, 8)"
			+"	AND o.city LIKE ?3 "
			+"	AND o.diy_site_code LIKE ?4 "
			+"	AND o.diy_site_id IN ?5"
			+"	"
			+"	AND ds.is_direct = 0"
			+"	ORDER BY"
			+"		sales_time DESC;", nativeQuery = true)
	List<TdGoodsInOutFranchisees> queryDownList(Date begin, Date end, String cityName,
			String diySiteCode, List<String> roleDiyIds);

	/**
	 * 查询自提单下载的list
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
			+"	og.id id, "
			+"	o.diy_site_name diy_site_name, "
			+"	o.main_order_number main_order_number, "
			+"	o.order_number order_number, "
			+"	o.order_time order_time, "
			+"	o.send_time sales_time, "
			+"	o.status_id status_id, "
			+"	su.real_name seller_real_name, "
			+"	o.user_id user_id, "
			+"	u.real_name real_name, "
			+"	IFNULL("
			+"		o.real_user_username, "
			+"		o.username"
			+"	) username, "
			+"	og.sku sku, "
			+"	og.goods_title goods_title, "
			+"	og.quantity quantity, "
			+"  case when presented_list_id is null && gift_list_id is null "
			+"  then  price "
			+"  else 0 "
			+"  end price, "
			+"	og.quantity * og.price total_price, "
			+"	o.cash_coupon cash_coupon, "
			+"	og.brand_title brand_title, "
			+"	ppc.title goods_parent_type_title, "
			+"	pc.title goods_type_title, "
			+"	o.deliver_type_title deliver_type_title, "
			+"	d.title wh_name, "
			+"	su.real_name deliver_real_name, "
			+"	su.username deliver_username, "
			+"	o.shipping_name shipping_name, "
			+"	o.shipping_phone shipping_phone, "
			+"	o.shipping_address shipping_address, "
			+"	o.remark remark, "
			+"	o.city city_name, "
			+"	o.diy_site_code diy_site_code, "
			+"	o.diy_site_id diy_id "
			+" FROM "
			+"	td_order o "
			+" INNER JOIN td_order_goods og ON og.td_order_id = o.id "
			+" OR og.presented_list_id = o.id " 
			+" OR og.gift_list_id = o.id "
			+" LEFT JOIN td_user u ON u.username = IFNULL("
			+"	o.real_user_username, "
			+"	o.username "
			+") "
			+" LEFT JOIN td_product_category pc ON pc.id = ( "
			+"	SELECT "
			+"		a.category_id"
			+"	FROM "
			+"		td_goods a "
			+"	WHERE "
			+"		a.id = og.goods_id"
			+" ) "
			+" LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id "
			+" LEFT JOIN td_user su ON su.id = o.seller_id "
			+" LEFT JOIN td_diy_site d ON o.diy_site_id = d.id "
			+" WHERE "
			+"	o.deliver_type_title = '门店自提'"
			+" AND o.send_time >= ?1"
			+" AND o.send_time <= ?2"
			+" AND o.status_id >= 4"
			+" AND o.status_id NOT IN (1, 2, 7, 8)"
			+" AND d.city LIKE ?3"
			+" AND o.diy_site_code LIKE ?4"
			+" AND o.diy_site_id IN ?5"
			+" UNION ALL "
			+"	SELECT"
			+"		og.id id,"
			+"		o.diy_site_name diy_site_name,"
			+"		rn.return_number main_order_number,"
			+"		rn.order_number order_number,"
			+"		rn.order_time order_time,"
			+"		IFNULL("
			+"			rn.receive_time,"
			+"			rn.recv_time"
			+"		) sales_time,"
			+"		o.status_id status_id,"
			+"		o.seller_real_name seller_real_name,"
			+"		o.user_id user_id, "	
			+"		u.real_name real_name,"
			+"		rn.username username,"
			+"		og.sku sku,"
			+"		og.goods_title goods_title ,- og.quantity quantity,"
			+"		og.price ,- og.quantity * og.price total_price,"
			+"		o.cash_coupon cash_coupon,"
			+"		og.brand_title brand_title,"
			+"		ppc.title goods_parent_type_title,"
			+"		pc.title goods_type_title,"
			+"		o.deliver_type_title deliver_type_title,"
			+"		d.title wh_name,"
			+"		o.seller_real_name deliver_real_name,"
			+"		o.seller_username deliver_username,"
			+"		o.shipping_name shipping_name,"
			+"		o.shipping_phone shipping_phone,"
			+"		o.shipping_address shipping_address,"
			+"		o.remark remark,"
			+"		d.city city_name,"
			+"		o.diy_site_code diy_site_code,"
			+"		o.diy_site_id diy_id"
			+"	FROM "
			+"		td_return_note rn"
			+"	INNER JOIN td_order o ON o.order_number = rn.order_number"
			+"	LEFT JOIN td_user u ON u.username = o.username"
			+"	LEFT JOIN td_order_goods og ON og.td_return_id = rn.id"
			+"	LEFT JOIN td_goods g ON g.id = og.goods_id"
			+"	LEFT JOIN td_product_category pc ON pc.id = g.category_id"
			+"	LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id"
			+"	LEFT JOIN td_diy_site d ON o.diy_site_id = d.id"
			+"	WHERE "
			+"		rn.deliver_type_title = '门店自提'"
			+"	AND rn.receive_time >= ?1"
			+"	AND rn.receive_time <= ?2"
			+"	AND rn.status_id >= 4"
			+"	AND o.status_id NOT IN (1, 2, 7, 8)"
			+"	AND d.city LIKE ?3"
			+"	AND o.diy_site_code LIKE ?4"
			+"	AND o.diy_site_id IN ?5"
			+"	ORDER BY"
			+" sales_time DESC;",nativeQuery=true
	)
	List<TdGoodsInOut> queryDownListTake(Date begin, Date end, String cityName,
			String diySiteCode, List<String> roleDiyIds);
}
