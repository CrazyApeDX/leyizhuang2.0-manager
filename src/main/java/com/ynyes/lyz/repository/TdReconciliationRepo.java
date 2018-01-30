package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdReconciliation;

public interface TdReconciliationRepo extends PagingAndSortingRepository<TdReconciliation, Long>,
	JpaSpecificationExecutor<TdReconciliation>{

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
	@Query(value = "    SELECT * FROM (SELECT      "
			+" 	  UUID() id,    "
			+" 	  o.pay_time order_time,    "
			+"    o.user_id user_id,     "
			+"    o.real_user_real_name real_name,    "
			+"    od.main_order_number main_order_number,    "
			+"    o.deliver_type_title deliver_type_title,    "
			+"    ds.title diy_site_name,    "
			+"   (CASE WHEN ds.cust_type_name = '直营' AND locate('FX', ds.store_code) THEN '分销' WHEN ds.cust_type_name = '经销商' THEN '加盟' ELSE '直营' END) diy_site_type,    "
			+"    o.seller_real_name seller_real_name,    "
			+"    od.total_goods_price total_goods_price,    "
			+"    od.member_discount member_discount,    "
			+"    od.activity_sub activity_sub,    "
			+"    IF(od.pro_coupon_fee=0, od.pro_coupon_fee, -1*od.pro_coupon_fee) pro_coupon_fee,    "
			+"    IF(od.cash_coupon_fee=0, od.cash_coupon_fee, -1*od.cash_coupon_fee) cash_coupon_fee,    "
			+"    od.balance_used balance_used,    "
			+"    (CASE WHEN o.pay_type_id = 3 THEN od.online_pay ELSE 0 END) ali_pay,    "
			+"    (CASE WHEN o.pay_type_id = 4 THEN od.online_pay ELSE 0 END) wechat_pay,    "
			+"    (CASE WHEN o.pay_type_id = 5 THEN od.online_pay ELSE 0 END) union_pay,    "
			+"    od.delivery_fee delivery_fee,    "
			+"    od.left_price left_price,    "
			+"    od.delivery_cash delivery_cash,    "
			+"    od.delivery_pos delivery_pos,    "
			+"    od.seller_cash seller_cash,    "
			+"    od.seller_pos seller_pos,    "
			+"    od.seller_other seller_other,    "
			+"    od.due due,    "
			+"    wh.wh_name wh_name,    "
			+"    u1.real_name deliver_real_name,     "
			+"    u1.username deliver_username,    "
			+"    o.shipping_name shipping_name,    "
			+"    o.shipping_phone shipping_phone,    "
			+"    o.shipping_address shipping_address,    "
			+"    o.remark remark    "
			+"    FROM td_order_data od    "
			+"    LEFT JOIN td_order o ON od.main_order_number = o.main_order_number     "
			+"    LEFT JOIN td_diy_site ds ON ds.id = o.diy_site_id    "
			+"    LEFT JOIN td_delivery_info di ON di.order_number = o.main_order_number     "
			+"    LEFT JOIN (SELECT real_name, username, op_user FROM td_user WHERE user_type = 5 ) u1 ON u1.op_user = di.driver     "
			+"    LEFT JOIN td_ware_house wh ON wh.wh_number = di.wh_no    "
			+"    WHERE     "
			+"    o.order_time >= ?1     "
			+"    AND o.order_time <= ?2    "
			+"    AND o.status_id NOT IN (1, 2, 8)     "
			+"    AND o.city LIKE ?3     "
			+"    AND o.diy_site_code LIKE ?4     "
			+"    AND o.diy_site_id IN ?5     "
			+"    GROUP BY main_order_number    "
			+"    UNION ALL    "
			+"    SELECT      "
			+" 	  UUID() id,    "
			+"    (CASE WHEN r.remark_info = '拒签退货' OR r.remark_info = '用户取消订单，退货' THEN r.order_time ELSE cr.finish_time END) order_time,    "
			+"    u.id user_id,    "
			+"    u.real_name real_name,    "
			+"    r.return_number main_order_number,    "
			+"    r.deliver_type_title deliver_type_title,    "
			+"    ds.title diy_site_name,    "
			+"    (CASE WHEN ds.cust_type_name = '直营' AND locate('FX', ds.store_code) THEN '分销' WHEN ds.cust_type_name = '经销商' THEN '加盟' ELSE '直营' END) diy_site_type,    "
			+"    r.seller_real_name seller_real_name,    "
			+"    (CASE WHEN locate('YF', r.order_number) THEN 0 ELSE r.turn_price END) total_goods_price,    "
			+"    0 member_discount,    "
			+"    0 activity_sub,    "
			+"    rcp.ps pro_coupon_fee,    "
			+"    rsp.ps cash_coupon_fee,    "
			+"    IF(l.money=0, l.money, -1*l.money) balance_used,    "
			+"    IF((CASE WHEN cr.type_title = '支付宝' THEN cr.money ELSE 0 END)=0, (CASE WHEN cr.type_title = '支付宝' THEN cr.money ELSE 0 END), -1*(CASE WHEN cr.type_title = '支付宝' THEN cr.money ELSE 0 END)) ali_pay,    "
			+"    IF((CASE WHEN cr.type_title = '微信' OR cr.type_title = '微信支付' THEN cr.money ELSE 0 END)=0, (CASE WHEN cr.type_title = '微信' OR cr.type_title = '微信支付' THEN cr.money ELSE 0 END), -1*(CASE WHEN cr.type_title = '微信' OR cr.type_title = '微信支付' THEN cr.money ELSE 0 END)) wechat_pay,    "
			+"    IF((CASE WHEN cr.type_title = '银行卡' THEN cr.money ELSE 0 END)=0, (CASE WHEN cr.type_title = '银行卡' THEN cr.money ELSE 0 END), -1*(CASE WHEN cr.type_title = '银行卡' THEN cr.money ELSE 0 END)) union_pay,    "
			+"    IF((CASE WHEN locate('YF', r.order_number) THEN r.turn_price ELSE 0 END)=0, (CASE WHEN locate('YF', r.order_number) THEN r.turn_price ELSE 0 END), -1*(CASE WHEN locate('YF', r.order_number) THEN r.turn_price ELSE 0 END)) delivery_fee,    "
			+"    0 left_price,    "
			+"    0 delivery_cash,    "
			+"    0 delivery_pos,    "
			+"    IF((CASE WHEN cr.type_title = '门店现金' THEN cr.money ELSE 0 END)=0, (CASE WHEN cr.type_title = '门店现金' THEN cr.money ELSE 0 END), -1*(CASE WHEN cr.type_title = '门店现金' THEN cr.money ELSE 0 END)) seller_cash,    "
			+"    0 seller_pos,    "
			+"    0 seller_other,    "
			+"    0 due,    "
			+"    wh.wh_name wh_name,    "
			+"    u1.real_name deliver_real_name,     "
			+"    u1.username deliver_username,    "
			+"    o.shipping_name shipping_name,    "
			+"    o.shipping_phone shipping_phone,    "
			+"    o.shipping_address shipping_address,    "
			+"    o.remark remark    "
			+"    FROM td_return_note r    "
			+"    LEFT JOIN td_order o ON r.order_number = o.order_number    "
			+"    LEFT JOIN td_cash_return_note cr ON cr.return_note_number = r.return_number    "
			+"    LEFT JOIN td_user u ON u.username = r.username AND u.user_type = 0    "
			+"    LEFT JOIN td_diy_site ds ON ds.id = r.diy_site_id    "
			+"    LEFT JOIN td_back_main bm ON bm.po_no = r.return_number    "
			+"    LEFT JOIN td_ware_house wh ON wh.wh_number = bm.wh_no    "
			+"    LEFT JOIN td_user u1 ON u1.op_user = r.driver AND u.user_type = 5    "
			+"    LEFT JOIN (SELECT order_number, SUM(money) money FROM td_balance_log WHERE balance_type = 4 GROUP BY order_number) l ON l.order_number = r.order_number    "
			+"    LEFT JOIN td_return_order_inf rf ON rf.return_number = r.return_number    "
			+"    LEFT JOIN (SELECT rc.rt_header_id, SUM(rg.ls_share_price) ps     "
			+"    FROM td_return_coupon_inf rc    "
			+"    LEFT JOIN td_return_goods_inf rg ON rg.rt_header_id = rc.rt_header_id    "
			+"    WHERE rc.coupon_type_id IN (1,4) GROUP BY rc.rt_header_id) rcp ON rcp.rt_header_id = rf.rt_header_id    "
			+"    LEFT JOIN (SELECT rt_header_id, SUM(price) ps FROM td_return_coupon_inf WHERE coupon_type_id IN (3,5) GROUP BY rt_header_id) rsp ON rsp.rt_header_id = rf.rt_header_id    "
			+"    WHERE     "
			+"    o.order_time >= ?1     "
			+"    AND o.order_time <= ?2    "
			+"    AND r.status_id = 5     "
			+"    AND o.status_id NOT IN (1, 2, 8)     "
			+"    AND o.city LIKE ?3     "
			+"    AND o.diy_site_code LIKE ?4     "
			+"    AND o.diy_site_id IN ?5     "
			+"    GROUP BY main_order_number) t    "
			+"    ORDER BY t.order_time DESC;    ", nativeQuery = true)
	List<TdReconciliation> queryDownList(Date begin, Date end, String cityName,
			String diySiteCode, List<String> roleDiyIds);
	
}
