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
	@Query(value = "select og.id id,o.diy_site_name diy_site_name,o.main_order_number main_order_number,o.order_number order_number,o.order_time order_time, "
			+ "di.begin_dt sales_time,o.status_id status_id,su.real_name seller_real_name,u.real_name real_name,IFNULL(o.real_user_username,o.username) "
			+ " username,og.sku sku,og.goods_title goods_title,og.quantity quantity,og.price price,og.quantity*og.price total_price,o.cash_coupon cash_coupon, "
			+ "og.brand_title brand_title,ppc.title goods_parent_type_title,pc.title goods_type_title,o.deliver_type_title deliver_type_title,wh.wh_name wh_name, "
			+ "u1.real_name deliver_real_name,u1.username deliver_username,o.shipping_name shipping_name,o.shipping_phone shipping_phone,o.shipping_address "
			+ " shipping_address,o.remark remark,o.city city_name,o.diy_site_code diy_site_code,o.diy_site_id diy_id "
			+ " from td_order o "
			+ " INNER JOIN td_order_goods og on og.td_order_id=o.id or og.presented_list_id=o.id or og.gift_list_id=o.id "
			+ " LEFT JOIN td_user u on u.username=IFNULL(o.real_user_username,o.username) "
			+ " LEFT JOIN td_product_category pc on pc.id=(select a.category_id from td_goods a where a.id=og.goods_id) "
			+ " left join td_product_category ppc on ppc.id=pc.parent_id "
			+ " left join td_delivery_info di on di.order_number=o.main_order_number "
			+ " left join (select * from td_user where user_type=5) u1 on u1.op_user=di.driver "
			+ " left join td_ware_house wh on wh.wh_number=di.wh_no "
			+ " left join td_user su on su.id=o.seller_id "
			+ "  where o.order_time>=?1 and o.order_time<=?2 and o.status_id not in(1,2,7,8) and o.city like ?3 and o.diy_site_code like ?4 and o.diy_site_id in ?5 "
			+ "union ALL "
			+ "select og.id id,o.diy_site_name diy_site_name,rn.return_number main_order_number,rn.order_number order_number,rn.order_time order_time,"
			+ "rn.receive_time sales_time,o.status_id status_id,o.seller_real_name seller_real_name,u.real_name real_name,rn.username username,og.sku sku,"
			+ "og.goods_title goods_title,-og.quantity quantity,og.price,-og.quantity*og.price total_price,o.cash_coupon cash_coupon,"
			+ "og.brand_title brand_title,ppc.title  goods_parent_type_title,pc.title  goods_type_title,o.deliver_type_title deliver_type_title,"
			+ "'' wh_name,u1.real_name deliver_real_name,u1.username deliver_username,o.shipping_name shipping_name,o.shipping_phone shipping_phone,"
			+ "o.shipping_address shipping_address,o.remark remark,o.city city_name,o.diy_site_code diy_site_code,o.diy_site_id diy_id "
			+ "from td_return_note rn "
			+ "INNER JOIN td_order o on o.order_number=rn.order_number and o.status_id not in(1,2,7,8) "
			+ "LEFT JOIN td_user u on u.username=o.username "
			+ "left join td_delivery_info di on di.order_number=o.main_order_number "
			+ "left join td_user u1 on u1.op_user=rn.driver "
			+ "LEFT JOIN td_order_goods og on og.td_return_id=rn.id "
			+ "left join td_goods g on g.id=og.goods_id "
			+ "left JOIN td_product_category pc on pc.id=g.category_id "
			+ "left join td_product_category ppc on ppc.id=pc.parent_id "
			+ " where o.order_time>=?1 and o.order_time<=?2 and o.status_id not in(1,2,7,8) and o.city like ?3 and o.diy_site_code like ?4 and o.diy_site_id in ?5 "
			+ "order by order_time desc;",nativeQuery = true)
	List<TdSalesDetail> queryDownList(Date begin,Date end,String cityName,String diySiteCode,List<String> roleDiyIds);
}
