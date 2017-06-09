package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.FitGoodsInOut;

/**
 * 装饰公司出退货报表 DAO
 * 
 * @author Richard
 *
 */

public interface FITGoodsINOutRepo extends
		PagingAndSortingRepository<FitGoodsInOut, Long>,
		JpaSpecificationExecutor<FitGoodsInOut> {

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
	@Query(value =" SELECT"
			+" 	og.id id, "
			+"	o.diy_site_name diy_site_name, "
			+" 	o.main_order_number main_order_number, "
			+" 	o.order_number order_number, "
			+" 	o.order_time order_time, "
			+" 	o.send_time sales_time, "
			+" 	o.status_id status_id, "
			+" 	o.seller_real_name seller_real_name, "
			+" 	o.real_user_username real_user_username, "
			+" 	o.real_user_real_name real_user_real_name, "
			+" 	o.real_user_username user_id, "
			+" 	og.sku sku, "
			+" 	og.goods_title, "
			+" 	og.quantity quantity, "
			+" 	og.price price, "
			+" 	og.real_price real_price, "
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
			+"	o.city city_name "
			+" FROM "
			+" 	td_order o "
			+" LEFT JOIN td_order_goods og ON o.id = og.td_order_id "
			+" LEFT JOIN td_product_category pc ON pc.id = ( "
			+" 	SELECT "
			+" 		g.category_id "
			+" 	FROM "
			+" 		td_goods g "
			+" 	WHERE "
			+" 		g.id = og.goods_id "
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
			+" WHERE "
			+" 	o.order_number LIKE '%FIT%' "
			+" AND o.order_time >= ?1 "
			+" AND o.order_time <= ?2 "
			+" AND o.city LIKE ?3 "
			+" AND o.diy_site_code LIKE ?4 ; ", nativeQuery = true)
	List<FitGoodsInOut> queryDownList(Date begin, Date end, String cityName,
			String code);


}