-- 出退货明细表
DROP PROCEDURE IF EXISTS insert_goods_in_out_initial;
CREATE PROCEDURE insert_goods_in_out_initial(IN startTime DATETIME,IN endTime DATETIME,IN v_username VARCHAR(20))
BEGIN  
    -- 删除重复数据
delete from td_goods_in_out where create_username=v_username;
			-- 查询数据到td_goods_in_out
INSERT into td_goods_in_out(diy_site_name,main_order_number,order_number,status_id,order_time,sales_time,real_name,username,brand_title,category_title,seller_real_name,deliver_type_title,sku,goods_title,quantity,price,total_price,wh_no,deliver_real_name,deliver_username,shipping_address,remark_info,city_name,diy_site_code,create_username,diy_id) 
select o.diy_site_name,o.main_order_number,o.order_number,o.status_id,o.order_time,di.begin_dt sales_time,u.real_name,o.username,og.brand_title,pc.title category_title,o.seller_real_name,o.deliver_type_title,og.sku,og.goods_title,og.quantity,og.price,og.quantity*og.price total_price,di.wh_no,u1.real_name,u1.username,o.shipping_address,o.remark,o.city city_name,o.diy_site_code,v_username create_username,o.diy_site_id
from td_order o 
INNER JOIN td_order_goods og on og.td_order_id=o.id or og.presented_list_id=o.id or og.gift_list_id=o.id
LEFT JOIN td_user u on u.username=o.username 
LEFT JOIN td_product_category pc on pc.id=(select a.category_id from td_goods a where a.id=og.goods_id)
left join td_delivery_info di on di.order_number=o.main_order_number
left join (select * from td_user where user_type=5) u1 on u1.op_user=di.driver
where o.order_time>=startTime and o.order_time<=endTime and o.status_id not in(1,2,3,7,8)
union ALL
select o.diy_site_name,rn.return_number main_order_number,rn.order_number,o.status_id,rn.order_time,rn.receive_time sales_time,u.real_name,o.username,og.brand_title,pc.title category_title,o.seller_real_name,o.deliver_type_title,og.sku,og.goods_title,-og.quantity,og.price,-og.quantity*og.price total_price,'' wh_no,u1.real_name,u1.username,o.shipping_address,o.remark,o.city city_name,o.diy_site_code,v_username create_username,rn.diy_site_id
from td_return_note rn
INNER JOIN td_order o on o.order_number=rn.order_number and o.status_id not in(1,2,3,7,8)
LEFT JOIN td_user u on u.username=o.username
left join td_delivery_info di on di.order_number=o.main_order_number
left join td_user u1 on u1.op_user=rn.driver
LEFT JOIN td_order_goods og on og.td_return_id=rn.id
left join td_goods g on g.id=og.goods_id
left JOIN td_product_category pc on pc.id=g.category_id
where rn.order_time>=startTime and rn.order_time<=endTime;
END;

-- 代收款报表
DROP PROCEDURE IF EXISTS insertAgencyFund_initial;
CREATE PROCEDURE insertAgencyFund_initial(IN startTime DATETIME,IN endTime DATETIME,IN v_username VARCHAR(20))
BEGIN  
    -- 删除重复数据
delete from td_agency_fund where create_username=v_username;
			-- 查询数据到agencyfund
INSERT into td_agency_fund(diy_site_name,diy_site_phone,main_order_number,order_time,cash_balance_used,un_cash_balance_used,pay_price,payed,owned,delivery_name,delivery_phone,shipping_name,shipping_phone,shipping_address,remark,cash_coupon,status_id,wh_name,total_price,delivery_date,delivery_detail_id,delivery_time,city_name,diy_site_code,create_username,deliver_type_title,diy_id,seller_phone,seller_name,user_phone,user_name,pay_money,pay_pos) 
select o.diy_site_name,o.diy_site_phone,o.main_order_number,o.order_time,sum(o.cash_balance_used) cash_balance_used,sum(o.un_cash_balance_used) un_cash_balance_used,o.all_total_pay as pay_price,omr.payed,omr.owned,du.real_name delivery_name,du.username delivery_phone,o.shipping_name,o.shipping_phone,o.shipping_address,o.remark,o.cash_coupon,o.status_id,wh.wh_name,o.all_actual_pay+o.all_total_pay+sum(o.cash_balance_used)+sum(o.un_cash_balance_used)+o.cash_coupon+o.product_coupon total_goods_price,o.delivery_date,o.delivery_detail_id,o.delivery_time,o.city,o.diy_site_code,v_username create_username,o.deliver_type_title,o.diy_site_id,o.seller_username seller_phone,o.seller_real_name seller_name,o.real_user_username user_phone,o.real_user_real_name user_name,IFNULL(omr.money,0)+IFNULL(omr.back_money,0) pay_money,IFNULL(omr.pos,0)+IFNULL(omr.back_pos,0) pay_pos  from td_order o
left JOIN td_own_money_record omr on omr.order_number=o.main_order_number
left join td_delivery_info di on di.order_number=o.main_order_number
left join td_user du on du.op_user=di.driver
left join td_ware_house wh on wh.wh_number=di.wh_no
where   o.status_id in(5,6,9,10,12) and order_time>=startTime and order_time<=endTime and o.deliver_type_title <> "门店自提"
GROUP BY o.main_order_number;
END

-- 收款报表
DROP PROCEDURE IF EXISTS insertGathering_initial;
CREATE PROCEDURE insertGathering_initial(IN startTime DATETIME,IN endTime DATETIME,IN v_username VARCHAR(20))
BEGIN  
    -- 删除重复数据
delete from td_gathering where create_username=v_username ;
			-- 查询数据到td_gathering
INSERT into td_gathering(diy_site_name,diy_site_phone,main_order_number,order_number,brand_title,seller_real_name,order_time,deliver_type_title,cash_balance_used,un_cash_balance_used,cash_coupon,product_coupon,total_price,payed,pay_type_title,other_pay,owned,real_name,username,shipping_name,shipping_phone,shipping_address,remark,status_id,wh_no,total_goods_price,delivery_date,delivery_detail_id,delivery_time,city_name,diy_site_code,create_username,diy_id) 
select o.diy_site_name,o.diy_site_phone,o.main_order_number,o.order_number,o.brand_title,o.seller_real_name,o.order_time,o.deliver_type_title,o.cash_balance_used,o.un_cash_balance_used,o.cash_coupon,o.product_coupon,o.total_price,omr.payed,o.pay_type_title,o.other_pay,omr.owned,u.real_name,u.username,o.shipping_name,o.shipping_phone,o.shipping_address,o.remark,o.status_id,di.wh_no,o.total_goods_price,o.delivery_date,o.delivery_detail_id,o.delivery_time,o.city,o.diy_site_code,v_username create_username,o.diy_site_id from td_order o
 left JOIN td_own_money_record omr on omr.order_number=o.order_number
left join td_delivery_info di on di.order_number=o.main_order_number
left join td_user u on u.op_user=di.driver
where o.status_id not in(1,2,7,8) and order_time>=startTime and order_time<=endTime;
END;

-- 退货报表
DROP PROCEDURE IF EXISTS insertReturnReport_initial;
CREATE PROCEDURE insertReturnReport_initial(IN startTime DATETIME,IN endTime DATETIME,IN v_username VARCHAR(20))
BEGIN  
    -- 删除重复数据
delete from td_return_report where create_username=v_username ;
			-- 查询数据到td_return_report
INSERT into td_return_report(diy_site_name,order_number,return_number,status_id,brand_title,category_title,seller_real_name,order_time,cancel_time,real_name,username,sku,goods_title,quantity,price,turn_price,cash_coupon,product_coupon,remark_info,wh_no,deliver_real_name,deliver_username,shipping_address,diy_site_code,city_name,create_username,deliver_Type_Title,diy_id) 
select rn.diy_site_title,rn.order_number,rn.return_number,rn.status_id,og.brand_title,g.category_title,o.seller_real_name,o.order_time,rn.order_time,u.real_name,o.username,og.sku,og.goods_title,og.quantity,og.price,rn.turn_price,o.cash_coupon,o.product_coupon,rn.remark_info,di.wh_no,u2.real_name,u2.username,o.shipping_address,o.diy_site_code,o.city,v_username create_username,o.deliver_Type_Title,o.diy_site_id
from td_return_note rn
INNER JOIN td_order o on o.order_number=rn.order_number and o.status_id not in(1,2,3,7,8)
LEFT JOIN td_user u on u.username=o.username
left join td_delivery_info di on di.order_number=o.main_order_number
left join td_user u2 on u2.op_user=di.driver
LEFT JOIN td_order_goods og on og.td_return_id=rn.id 
left join td_goods g on g.id=og.goods_id
where rn.order_time>=startTime and rn.order_time<=endTime;

END;

-- 销售明细报表
DROP PROCEDURE IF EXISTS insertSalesDetail_initial;
CREATE PROCEDURE insertSalesDetail_initial(IN startTime DATETIME,IN endTime DATETIME,IN v_username VARCHAR(20))
BEGIN  

    -- 删除重复数据
delete from td_sales_detail where create_username=v_username ;
			-- 查询数据到td_sales_detail
INSERT into td_sales_detail(diy_site_name,main_order_number,order_number,order_time,status_id,username,real_name,shipping_name,sku,goods_title,quantity,price,total_price,cash_balance_used,un_cash_balance_used,remark,wh_no,deliver_real_name,deliver_username,seller_real_name,title,deliver_type_title,shipping_address,city_name,diy_site_code,create_username,diy_id,shipping_phone,parent_category_title,remark_info) 
select o.diy_site_name,o.main_order_number,o.order_number,o.order_time,o.status_id,o.username,u.real_name,o.shipping_name,og.sku,og.goods_title,og.quantity,og.price,og.quantity*og.price total_price,o.cash_balance_used,o.un_cash_balance_used,o.remark,di.wh_no,u1.real_name deliver_real_name,u1.username deliver_username,o.seller_real_name,pc.title,o.deliver_type_title,o.shipping_address,o.city,o.diy_site_code,v_username create_username,o.diy_site_id,o.shipping_phone,ppc.title,o.remark_info
 from td_order o
INNER JOIN td_order_goods og on og.td_order_id=o.id or og.presented_list_id=o.id or og.gift_list_id=o.id
LEFT JOIN td_user u on u.username=o.username
left join td_delivery_info di on di.order_number=o.main_order_number
left join td_user u1 on u1.op_user=di.driver
left join td_goods g on g.id=og.goods_id
left join td_product_category pc on pc.id=g.category_id
left join td_product_category ppc on ppc.id=pc.parent_id
where o.main_order_number is not null and order_time>=startTime and order_time<=endTime and o.status_id not in(1,2,7,8);
END;