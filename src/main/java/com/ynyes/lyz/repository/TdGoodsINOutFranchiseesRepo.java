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
 * @author Richard
 *
 */

public interface TdGoodsINOutFranchiseesRepo extends
		PagingAndSortingRepository<TdGoodsInOutFranchisees, Long>,
		JpaSpecificationExecutor<TdGoodsInOutFranchisees> {

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
	 *
	 */
	@Query(value =" SELECT "
            +"   og.id                        id, "
            +"   o.diy_site_name              diy_site_name, "
            +"   o.main_order_number          main_order_number, "
            +"   o.order_number               order_number, "
            +"   o.order_time                 order_time, "
            +"   di.end_dt                    sales_time, "
            +"   o.status_id                  status_id, "
            +"   o.seller_real_name           seller_real_name, "
            +"   o.real_user_real_name        real_name, "
            +"   o.username                   username, "
            +"   og.sku                       sku, "
            +"   og.goods_title               goods_title, "
            +"   og.quantity                  quantity, "
            +"   CASE WHEN IFNULL(o.dif_fee, 0) > 0 "
            +"     THEN "
            +"       IFNULL(og.real_price, 0) "
            +"   ELSE og.price "
            +"   END AS                       price, "
            //+"   IFNULL(og.jx_price, 0)       jx_price, "
			+"  CASE WHEN og.jx_dif > 0 "
			+"  THEN IFNULL(og.jx_price,0) "
			+"	ELSE IFNULL(og.price,0) "
			+"  END AS                       jx_price, "
            +"   sum(IFNULL(oci.quantity, 0)) product_coupon_quantity, "
            +"   og.brand_title               brand_title, "
            +"   ppc.title                    goods_parent_type_title, "
            +"   pc.title                     goods_type_title, "
            +"   o.deliver_type_title         deliver_type_title, "
            +"   wh.wh_name                   wh_name, "
            +"   u.real_name                  deliver_real_name, "
            +"   u.username                   deliver_username, "
            +"   o.shipping_name              shipping_name, "
            +"   o.shipping_phone             shipping_phone, "
            +"   o.shipping_address           shipping_address, "
            +"   o.remark                     remark, "
            +"   o.city                       city_name, "
            +"   o.diy_site_code              diy_site_code, "
            +"   o.diy_site_id                diy_id, "
            +"   o.user_id                    user_id, "
            +"   IFNULL(ogi.gift_flag, 'Y')   gift_flag, "
            +"   diy.is_direct                is_direct "
            +" FROM td_order o INNER JOIN td_order_goods og ON o.id = og.td_order_id OR o.id = og.presented_list_id "
            +"   INNER JOIN td_order_inf oi ON o.order_number = oi.order_number "
            +"   LEFT JOIN td_order_goods_inf ogi "
            +"     ON oi.header_id = ogi.order_header_id AND ogi.gift_flag = 'N' AND og.td_order_id IS NOT NULL AND og.sku = ogi.sku "
            +"   LEFT JOIN td_order_coupon_inf oci "
            +"     ON oci.order_header_id = oi.header_id AND oci.sku = ogi.sku AND oci.coupon_type_id IN (1, 4) AND ogi.gift_flag = 'N' "
            +"   LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
            +"   LEFT JOIN td_product_category pc ON pc.id = ( "
            +"     SELECT a.category_id "
            +"     FROM td_goods a "
            +"     WHERE "
            +"       a.id = og.goods_id "
            +"   ) "
            +"   LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id "
            +"   LEFT JOIN td_user u ON u.op_user = di.driver AND u.user_type = 5 "
            +"   LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no "
            +"   LEFT JOIN td_diy_site diy on o.diy_site_id = diy.id "
            +" WHERE "
            +"   o.deliver_type_title = '送货上门' "
            +"   AND o.is_coupon IS NULL "
            +"   AND o.status_id > 3 "
            +"   AND o.status_id NOT IN (7, 8) "
            +"   AND di.end_dt >= ?1 "
            +"   AND di.end_dt <= ?2 "
            +"   AND o.city LIKE ?3 "
            +"   AND o.diy_site_code LIKE ?4 "
            +"   AND o.diy_site_id IN ?5 "
            +" GROUP BY order_number,sku,gift_flag "

            +"  UNION ALL"
            +" SELECT "
            +"   og.id                        id, "
            +"   o.diy_site_name              diy_site_name, "
            +"   o.main_order_number          main_order_number, "
            +"   o.order_number               order_number, "
            +"   o.order_time                 order_time, "
            +"   o.send_time                  sales_time, "
            +"   o.status_id                  status_id, "
            +"   o.seller_real_name           seller_real_name, "
            +"   o.real_user_real_name        real_name, "
            +"   o.username                   username, "
            +"   og.sku                       sku, "
            +"   og.goods_title               goods_title, "
            +"   og.quantity                  quantity, "
            +"   CASE WHEN IFNULL(o.dif_fee, 0) > 0 "
            +"     THEN "
            +"       IFNULL(og.real_price, 0) "
            +"   ELSE og.price "
            +"   END AS                       price, "
            //+"   IFNULL(og.jx_price, 0)       jx_price, "
			+"  CASE WHEN og.jx_dif > 0 "
			+"  THEN IFNULL(og.jx_price,0) "
			+"	ELSE IFNULL(og.price,0) "
			+"  END AS                       jx_price, "
            +"   sum(IFNULL(oci.quantity, 0)) product_coupon_quantity, "
            +"   og.brand_title               brand_title, "
            +"   ppc.title                    goods_parent_type_title, "
            +"   pc.title                     goods_type_title, "
            +"   o.deliver_type_title         deliver_type_title, "
            +"   ''                   wh_name, "
            +"   ''                  deliver_real_name, "
            +"   ''                   deliver_username, "
            +"   o.shipping_name              shipping_name, "
            +"   o.shipping_phone             shipping_phone, "
            +"   o.shipping_address           shipping_address, "
            +"   o.remark                     remark, "
            +"   o.city                       city_name, "
            +"   o.diy_site_code              diy_site_code, "
            +"   o.diy_site_id                diy_id, "
            +"   o.user_id                    user_id, "
            +"   IFNULL(ogi.gift_flag, 'Y')   gift_flag, "
            +"   diy.is_direct                is_direct "
            +" FROM td_order o INNER JOIN td_order_goods og ON o.id = og.td_order_id OR o.id = og.presented_list_id "
            +"   INNER JOIN td_order_inf oi ON o.order_number = oi.order_number "
            +"   LEFT JOIN td_order_goods_inf ogi "
            +"     ON oi.header_id = ogi.order_header_id AND ogi.gift_flag = 'N' AND og.td_order_id IS NOT NULL AND og.sku = ogi.sku "
            +"   LEFT JOIN td_order_coupon_inf oci "
            +"     ON oci.order_header_id = oi.header_id AND oci.sku = ogi.sku AND oci.coupon_type_id IN (1, 4) AND ogi.gift_flag = 'N' "
            +"   LEFT JOIN td_product_category pc ON pc.id = ( "
            +"     SELECT a.category_id "
            +"     FROM td_goods a "
            +"     WHERE "
            +"       a.id = og.goods_id "
            +"   ) "
            +"   LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id "
            +"   LEFT JOIN td_diy_site diy on o.diy_site_id = diy.id "
            +" WHERE "
            +"   o.deliver_type_title = '门店自提' "
            +"   AND o.is_coupon IS NULL "
            +"   AND o.status_id > 3 "
            +"   AND o.status_id NOT IN (7, 8) "
            +"   AND o.send_time >= ?1 "
            +"   AND o.send_time <= ?2 "
            +"   AND o.city LIKE ?3 "
            +"   AND o.diy_site_code LIKE ?4 "
            +"   AND o.diy_site_id IN ?5 "
            +" GROUP BY order_number,sku,gift_flag"

            +" UNION ALL "
            +" SELECT "
            +"   og.id                         id, "
            +"   o.diy_site_name               diy_site_name, "
            +"   rn.return_number              main_order_number, "
            +"   rn.order_number               order_number, "
            +"   rn.order_time                 order_time, "
            +"   rm.c_end_dt                   sales_time, "
            +"   rn.status_id                  status_id, "
            +"   o.seller_real_name            seller_real_name, "
            +"   o.real_user_real_name         real_name, "
            +"   rn.username                   username, "
            +"   og.sku                        sku, "
            +"   og.goods_title                goods_title, "
            +"   -og.quantity                  quantity, "
            +"   og.return_unit_price          price, "
            +"   IFNULL(og.jx_price, 0)        jx_price, "
            +"   sum(IFNULL(-rci.quantity, 0)) product_coupon_quantity, "
            +"   og.brand_title                brand_title, "
            +"   ppc.title                     goods_parent_type_title, "
            +"   pc.title                      goods_type_title, "
            +"   o.deliver_type_title          deliver_type_title, "
            +"   wh.wh_name                    wh_name, "
            +"   u1.real_name                  deliver_real_name, "
            +"   u1.username                   deliver_username, "
            +"   o.shipping_name               shipping_name, "
            +"   o.shipping_phone              shipping_phone, "
            +"   o.shipping_address            shipping_address, "
            +"   rn.remark_info                remark, "
            +"   o.city                        city_name, "
            +"   o.diy_site_code               diy_site_code, "
            +"   o.diy_site_id                 diy_id, "
            +"   o.user_id                     user_id, "
            +"   'N'                           gift_flag, "
            +"   diy.is_direct                is_direct "
            +" FROM "
            +"   td_return_note rn "
            +"   INNER JOIN td_order o ON o.order_number = rn.order_number "
            +"   INNER JOIN td_order_goods og ON rn.id = og.td_return_id "
            +"   INNER JOIN td_return_order_inf ri ON rn.return_number = ri.return_number "
            +"   LEFT JOIN td_return_goods_inf rgi "
            +"     ON ri.rt_header_id = rgi.rt_header_id AND og.sku = rgi.sku "
            +"   LEFT JOIN td_return_coupon_inf rci "
            +"     ON rci.rt_header_id = ri.rt_header_id AND rci.sku = rgi.sku AND rci.coupon_type_id IN (1, 4) "
            +"   LEFT JOIN td_tbw_back_recm rm ON rn.return_number = rm.c_po_no "
            +"   LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
            +"   LEFT JOIN td_user u1 ON u1.op_user = rn.driver "
            +"   LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no "
            +"   LEFT JOIN td_product_category pc ON pc.id = ( "
            +"   SELECT a.category_id "
            +"   FROM td_goods a "
            +"   WHERE "
            +"     a.id = og.goods_id "
            +" ) "
            +"   LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id "
            +"   LEFT JOIN td_diy_site diy on o.diy_site_id = diy.id "
            +" WHERE "
            +"   o.deliver_type_title = '送货上门' "
            +"   AND o.is_coupon IS NULL "
            +"   AND rm.c_end_dt >= ?1 "
            +"   AND rm.c_end_dt <= ?2 "
            +"   AND o.status_id NOT IN (1, 2, 7, 8) "
            +"   AND o.city LIKE ?3 "
            +"   AND o.diy_site_code LIKE ?4 "
            +"   AND o.diy_site_id IN ?5 "
            +" GROUP BY order_number, sku, gift_flag "

            +" UNION ALL "
            +" SELECT "
            +"   og.id                         id, "
            +"   o.diy_site_name               diy_site_name, "
            +"   rn.return_number              main_order_number, "
            +"   rn.order_number               order_number, "
            +"   rn.order_time                 order_time, "
            +"   rn.order_time                 sales_time, "
            +"   rn.status_id                  status_id, "
            +"   o.seller_real_name            seller_real_name, "
            +"   o.real_user_real_name         real_name, "
            +"   rn.username                   username, "
            +"   og.sku                        sku, "
            +"   og.goods_title                goods_title, "
            +"   -og.quantity                  quantity, "
            +"   CASE "
            +"   WHEN IFNULL(o.dif_fee, 0) > 0 "
            +"     THEN "
            +"       IFNULL(og.real_price, 0) "
            +"   ELSE "
            +"     og.price "
            +"   END AS                        price, "
            //+"   IFNULL(og.jx_price, 0)        jx_price, "
            +"  CASE WHEN og.jx_dif > 0 "
            +"  THEN IFNULL(og.jx_price,0) "
            +"	ELSE IFNULL(og.price,0) "
            +"  END AS                       jx_price, "
            +"   sum(IFNULL(-oci.quantity, 0)) product_coupon_quantity, "
            +"   og.brand_title                brand_title, "
            +"   ppc.title                     goods_parent_type_title, "
            +"   pc.title                      goods_type_title, "
            +"   o.deliver_type_title          deliver_type_title, "
            +"   wh.wh_name                    wh_name, "
            +"   u1.real_name                  deliver_real_name, "
            +"   u1.username                   deliver_username, "
            +"   o.shipping_name               shipping_name, "
            +"   o.shipping_phone              shipping_phone, "
            +"   o.shipping_address            shipping_address, "
            +"   rn.remark_info                remark, "
            +"   o.city                        city_name, "
            +"   o.diy_site_code               diy_site_code, "
            +"   o.diy_site_id                 diy_id, "
            +"   o.user_id                     user_id, "
            +"   'N'                           gift_flag, "
            +"   diy.is_direct                is_direct "
            +" FROM "
            +"   td_return_note rn "
            +"   INNER JOIN td_order o ON o.order_number = rn.order_number "
            +"   INNER JOIN td_order_goods og ON ( "
            +"   og.td_order_id = o.id "
            +"   OR og.presented_list_id = o.id "
            +"   ) "
            +"   INNER JOIN td_order_inf oi ON o.order_number = oi.order_number "
            +"   LEFT JOIN td_order_goods_inf ogi "
            +"     ON oi.header_id = ogi.order_header_id AND ogi.gift_flag = 'N' AND og.td_order_id IS NOT NULL AND og.sku = ogi.sku "
            +"   LEFT JOIN td_order_coupon_inf oci "
            +"     ON oci.order_header_id = oi.header_id AND oci.sku = ogi.sku AND oci.coupon_type_id IN (1, 4) AND ogi.gift_flag = 'N' "
            +"   LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number "
            +"   LEFT JOIN td_user u1 ON u1.op_user = rn.driver "
            +"   LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no "
            +"   LEFT JOIN td_product_category pc ON pc.id = ( "
            +"   SELECT a.category_id "
            +"   FROM td_goods a "
            +"   WHERE "
            +"     a.id = og.goods_id "
            +" ) "
            +"   LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id "
            +"   LEFT JOIN td_diy_site diy on o.diy_site_id = diy.id "
            +" WHERE "
            +"   o.deliver_type_title = '送货上门' "
            +"   AND o.is_coupon IS NULL "
            +"   AND rn.remark_info = '拒签退货' "
            +"   AND rn.order_time >= ?1 "
            +"   AND rn.order_time <= ?2 "
            +"   AND o.status_id NOT IN (1, 2, 7, 8) "
            +"   AND o.city LIKE ?3 "
            +"   AND o.diy_site_code LIKE ?4 "
            +"   AND o.diy_site_id IN ?5 "
            +" GROUP BY order_number, sku, gift_flag "

            +" UNION ALL "
            +" SELECT "
            +"   og.id                         id, "
            +"   o.diy_site_name               diy_site_name, "
            +"   rn.return_number              main_order_number, "
            +"   rn.order_number               order_number, "
            +"   rn.order_time                 order_time, "
            +"   IFNULL( "
            +"       rn.receive_time, "
            +"       rn.recv_time "
            +"   )                             sales_time, "
            +"   rn.status_id                  status_id, "
            +"   o.seller_real_name            seller_real_name, "
            +"   o.real_user_real_name         real_name, "
            +"   rn.username                   username, "
            +"   og.sku                        sku, "
            +"   og.goods_title                goods_title, "
            +"   -og.quantity                  quantity, "
            +"   og.return_unit_price          price, "
            +"   IFNULL(og.jx_price, 0)        jx_price, "
            +"   sum(IFNULL(-rci.quantity, 0)) product_coupon_quantity, "
            +"   og.brand_title                brand_title, "
            +"   ppc.title                     goods_parent_type_title, "
            +"   pc.title                      goods_type_title, "
            +"   o.deliver_type_title          deliver_type_title, "
            +"   ''                            wh_name, "
            +"   ''                            deliver_real_name, "
            +"   ''                            deliver_username, "
            +"   o.shipping_name               shipping_name, "
            +"   o.shipping_phone              shipping_phone, "
            +"   o.shipping_address            shipping_address, "
            +"   o.remark                      remark, "
            +"   o.city                        city_name, "
            +"   o.diy_site_code               diy_site_code, "
            +"   o.diy_site_id                 diy_id, "
            +"   o.user_id                     user_id, "
            +"   'N'                           gift_flag, "
            +"   diy.is_direct                is_direct "
            +" FROM "
            +"   td_return_note rn "
            +"   INNER JOIN td_order o ON o.order_number = rn.order_number "
            +"   INNER JOIN td_order_goods og ON rn.id = og.td_return_id "
            +"   INNER JOIN td_return_order_inf ri ON rn.return_number = ri.return_number "
            +"   LEFT JOIN td_return_goods_inf rgi "
            +"     ON ri.rt_header_id = rgi.rt_header_id AND og.sku = rgi.sku "
            +"   LEFT JOIN td_return_coupon_inf rci "
            +"     ON rci.rt_header_id = ri.rt_header_id AND rci.sku = rgi.sku AND rci.coupon_type_id IN (1, 4) "
            +"   LEFT JOIN td_product_category pc ON pc.id = ( "
            +"   SELECT a.category_id "
            +"   FROM td_goods a "
            +"   WHERE "
            +"     a.id = og.goods_id "
            +" ) "
            +"   LEFT JOIN td_product_category ppc ON ppc.id = pc.parent_id "
            +"   LEFT JOIN td_diy_site diy on o.diy_site_id = diy.id "
            +" WHERE "
            +"   o.deliver_type_title = '门店自提' "
            +"   AND o.is_coupon IS NULL "
            +"   AND ( "
            +"     ( "
            +"       rn.receive_time >= ?1 "
            +"       AND rn.receive_time <= ?2 "
            +"     ) "
            +"     OR ( "
            +"       rn.recv_time >= ?1 "
            +"       AND rn.recv_time <= ?2 "
            +"     ) "
            +"   ) "
            +"   AND o.status_id NOT IN (1, 2, 7, 8) "
            +"   AND o.city LIKE ?3 "
            +"   AND o.diy_site_code LIKE ?4 "
            +"   AND o.diy_site_id IN ?5 "
            +" GROUP BY order_number, sku, gift_flag "
            +" ORDER BY sales_time DESC; ", nativeQuery = true)
	List<TdGoodsInOutFranchisees> queryDownList(Date begin, Date end, String cityName,
			String diySiteCode, List<String> roleDiyIds);


}
