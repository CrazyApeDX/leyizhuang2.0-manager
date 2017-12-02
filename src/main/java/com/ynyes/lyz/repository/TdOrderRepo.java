package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdOrder;

/**
 * TdOrder 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdOrderRepo extends PagingAndSortingRepository<TdOrder, Long>, JpaSpecificationExecutor<TdOrder> {

	// 根据订单状态
	List<TdOrder> findByStatusIdAndCashCouponIdNotNullOrStatusIdAndCashCouponIdNotNullOrStatusIdAndProductCouponIdNotNullOrStatusIdAndProductCouponIdNotNullOrderByOrderTimeDesc(
			Long statusId1, Long statusId2, Long statusId3, Long statusId4);

	// 根据门店id
	Page<TdOrder> findByDiySiteCode(String diyCode, Pageable page);

	// 根据门店id和订单状态
	Page<TdOrder> findByDiySiteCodeAndStatusIdOrderByIdDesc(String diyCode, Long statusId, Pageable page);

	// 根据门店id，订单号，用户账号
	Page<TdOrder> findByDiySiteCodeAndOrderNumberContainingOrDiySiteCodeAndUsernameContainingOrderByIdDesc(
			String diyCode, String orderNumbers, String diyCode1, String username, Pageable page);

	Page<TdOrder> findByOrderNumberContainingOrUsernameContainingOrderByIdDesc(String orderNumbers, String username,
			Pageable page);

	Page<TdOrder> findByStatusIdOrderByIdDesc(Long statusId, Pageable page);

	Page<TdOrder> findByUsernameOrderByIdDesc(String username, Pageable page);

	List<TdOrder> findByOrderTimeAfterAndOrderTimeBeforeOrderByOrderTimeDesc(Date begin, Date end);

	List<TdOrder> findByDiySiteCodeAndOrderTimeAfterAndOrderTimeBeforeOrderByOrderTimeDesc(String diyCode, Date begin,
			Date end);

	// @Query("select g from TdOrder o join o.orderGoodsList og where o.statusId
	// = ?1 order by orderTime desc")
	List<TdOrder> findByStatusIdOrderByOrderTimeDesc(Long statusid);

	@Query("select o from TdOrder o where o.statusId= ?1 and o.sendTime > ?2 and o.orderNumber in ?3 group by o.mainOrderNumber order by o.id desc")
	List<TdOrder> findDistinctMainOrderNumberByStatusIdAndDeliveryTimeAfterAndOrderNumberInOrderByIdDesc(Long statusId,
			Date time, List<String> orderNumbers);

	@Query("select o from TdOrder o where o.statusId= ?1 and o.sendTime > ?2 and o.orderNumber in ?3 or o.statusId= ?4 and o.sendTime > ?5 and o.orderNumber in ?6 group by o.mainOrderNumber order by o.id desc")
	List<TdOrder> findDistinctMainOrderNumberByStatusIdAndDeliveryTimeAfterAndOrderNumberInOrStatusIdAndDeliveryTimeAfterAndOrderNumberInOrderByIdDesc(
			Long statusId, Date time, List<String> orderNumbers, Long statusId2, Date time2,
			List<String> orderNumbers2);

	@Query("select o from TdOrder o where o.statusId= ?1 and o.sendTime Between ?2 and ?3 and o.orderNumber in ?4 group by o.mainOrderNumber order by o.id desc")
	List<TdOrder> findDistinctMainOrderNumberByStatusIdAndDeliveryTimeBetweenAndOrderNumberInOrderByIdDesc(
			Long statusId, Date start, Date end, List<String> orderNumbers);

	@Query("select o from TdOrder o where o.statusId= ?1 and o.sendTime Between ?2 and ?3 and o.orderNumber in ?4 or o.statusId= ?5 and o.sendTime Between ?6 and ?7 and o.orderNumber in ?8 group by o.mainOrderNumber order by o.id desc")
	List<TdOrder> findDistinctMainOrderNumberByStatusIdAndDeliveryTimeBetweenAndOrderNumberInOrStatusIdAndOrderTimeBetweenAndOrderNumberInOrderByIdDesc(
			Long statusId, Date start, Date end, List<String> orderNumbers, Long statusId2, Date start2, Date end2,
			List<String> orderNumbers2);

	@Query("select count(Distinct o.mainOrderNumber) from TdOrder o where o.statusId= ?1 and o.sendTime > ?2 and o.orderNumber in ?3  order by o.id desc")
	Integer countDistinctMainOrderNumberByStatusIdAndDeliveryTimeAfterAndOrderNumberInOrderByIdDesc(Long statusId,
			Date time, List<String> orderNumbers);

	@Query("select count(Distinct o.mainOrderNumber) from TdOrder o where o.statusId= ?1 and o.sendTime > ?2 and o.orderNumber in ?3 or o.statusId= ?4 and o.sendTime > ?5 and o.orderNumber in ?6  order by o.id desc")
	Integer countDistinctMainOrderNumberByStatusIdAndDeliveryTimeAfterAndOrderNumberInOrStatusIdAndDeliveryTimeAfterAndOrderNumberInOrderByIdDesc(
			Long statusId, Date time, List<String> orderNumbers, Long statusId2, Date time2,
			List<String> orderNumbers2);

	@Query("select count(Distinct o.mainOrderNumber) from TdOrder o where o.statusId= ?1 and o.sendTime Between ?2 and ?3 and o.orderNumber in ?4  order by o.id desc")
	Integer countDistinctMainOrderNumberByStatusIdAndDeliveryTimeBetweenAndOrderNumberInOrderByIdDesc(Long statusId,
			Date start, Date end, List<String> orderNumbers);

	@Query("select count(Distinct o.mainOrderNumber) from TdOrder o where o.statusId= ?1 and o.sendTime Between ?2 and ?3 and o.orderNumber in ?4 or o.statusId= ?5 and o.sendTime Between ?6 and ?7 and o.orderNumber in ?8 order by o.id desc")
	Integer countDistinctMainOrderNumberByStatusIdAndDeliveryTimeBetweenAndOrderNumberInOrStatusIdAndDeliveryTimeBetweenAndOrderNumberInOrderByIdDesc(
			Long statusId, Date start, Date end, List<String> orderNumbers, Long statusId2, Date start2, Date end2,
			List<String> orderNumbers2);

	Page<TdOrder> findByUsernameAndStatusIdNotOrderByIdDesc(String username, Long statusId, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdOrUsernameAndStatusIdOrUsernameAndStatusIdOrderByIdDesc(String username1,
			Long statusId1, String username2, Long statusId2, String username3, Long statusId3, Pageable page);

	Page<TdOrder> findByUsernameAndOrderTimeAfterOrderByIdDesc(String username, Date time, Pageable page);

	Page<TdOrder> findByUsernameAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String username, Date time,
			String keywords, Pageable page);

	Page<TdOrder> findByUsernameAndOrderNumberContainingOrderByIdDesc(String username, String keywords, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdNotAndOrderNumberContainingOrderByIdDesc(String username, Long statusId,
			String keywords, Pageable page);

	Page<TdOrder> findByIdInAndOrderNumberContainingOrderByIdDesc(List<Long> orderids, String keywords, Pageable page);

	Page<TdOrder> findByIdInOrderByIdDesc(List<Long> orderids, Pageable page);

	// zhangji
	Page<TdOrder> findByUsernameAndOrderNumberContainingAndStatusIdOrUsernameAndOrderNumberContainingAndStatusIdOrUsernameAndOrderNumberContainingAndStatusIdOrderByIdDesc(
			String username1, String keywords1, Long statusId1, String username2, String keywords2, Long statusId2,
			String username3, String keywords3, Long statusId3, Pageable page);

	Page<TdOrder> findByUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusIdOrderByIdDesc(
			String username1, String keywords1, Long statusId1, String username2, String keywords2, Long statusId2,
			String username3, String keywords3, Long statusId3, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdOrderByIdDesc(String username, Long statusId, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdOrUsernameAndStatusIdOrderByIdDesc(String username1, Long statusId1,
			String username2, Long statusId2, Pageable page);

	Page<TdOrder> findBySellerIdAndStatusIdOrSellerIdAndStatusIdOrderByIdDesc(Long sellerId1, Long statusId1,
			Long sellerId2, Long statusId2, Pageable page);

	Page<TdOrder> findByDiySiteIdAndStatusIdOrDiySiteIdAndStatusIdOrderByIdDesc(Long diySiteId1, Long statusId1,
			Long diySiteId2, Long statusId2, Pageable page);

	List<TdOrder> findByUsernameAndStatusIdOrRealUserUsernameAndStatusIdOrderByIdDesc(String username, Long statusId,
			String username1, Long statusId1);

	Page<TdOrder> findByUsernameAndStatusIdOrRealUserUsernameAndStatusIdOrderByIdDesc(String username, Long statusId,
			String username1, Long statusId1, Pageable page);

	Page<TdOrder> findByUsernameAndIsCancelTrue(String username, Pageable page); // 取消订单
																					// zhangji

	Page<TdOrder> findByIsCancelTrue(Pageable page); // 取消订单 zhangji

	Page<TdOrder> findByIsCancelTrueAndIsRefundFalse(Pageable page); // 取消订单
																		// zhangji

	Page<TdOrder> findByIsCancelTrueAndIsRefundTrue(Pageable page); // 取消订单
																	// zhangji

	Page<TdOrder> findByUsernameAndStatusIdAndOrderNumberContainingOrderByIdDesc(String username, Long statusId,
			String keywords, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdAndOrderTimeAfterOrderByIdDesc(String username, Long statusId, Date time,
			Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String username,
			Long statusId, Date time, String keywords, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdOrStatusIdOrStatusIdOrStatusId(String username, Long statusId1,
			Long statusId2, Long statusId3, Long statusId4, Pageable page); // zhangji

	Long countByUsernameAndStatusId(String username, Long statusId);

	List<TdOrder> findByStatusId(Long statusId);

	Long countByStatusId(Long statusId);

	TdOrder findByOrderNumber(String orderNumber);

	// 根据用户名查找所有的订单
	List<TdOrder> findByUsernameOrderByIdDesc(String username);

	// 查找用户所有非删除的订单
	List<TdOrder> findByUsernameAndStatusIdNotOrderByOrderTimeDesc(String username, Long status);

	// 查找用户所有非删除的订单
	Page<TdOrder> findByUsernameAndStatusIdNotOrRealUserUsernameAndStatusIdNotOrderByOrderTimeDesc(String username,
			Long status, String username1, Long status1, Pageable page);

	// 根据订单号查找订单
	List<TdOrder> findByOrderNumberContaining(String orderNumber);

	List<TdOrder> findByMainOrderNumberIgnoreCase(String mainOrderNumber);

	/**
	 * 根据时间 查询总单号
	 * 
	 * @return
	 */
	@Query("select o from TdOrder o where o.orderTime between ?1 and ?2 group by o.mainOrderNumber order by o.orderTime desc")
	List<TdOrder> searchOrderByTime(Date begin, Date end);

	/**
	 * 根据时间 配送门店 查询总单号
	 * 
	 * @return
	 */
	@Query("select o from TdOrder o where o.diySiteCode = ?1 and o.orderTime between ?2 and ?3 group by o.mainOrderNumber order by o.orderTime desc")
	List<TdOrder> searchMainOrderNumberByOrderTimeAndDiySiteCode(String diyCode, Date begin, Date end);

	/**
	 * 查询指定归属销顾的订单
	 * 
	 * @author DengXiao
	 */
	List<TdOrder> findBySellerIdAndStatusIdNotOrderByOrderTimeDesc(Long sellerId, Long statusId);

	/**
	 * 查询指定归属销顾的订单
	 * 
	 * @author DengXiao
	 */
	Page<TdOrder> findBySellerIdAndStatusIdNotOrderByOrderTimeDesc(Long sellerId, Long statusId, Pageable page);

	/**
	 * 查询指定归属销顾的指定状态的订单
	 * 
	 * @author DengXiao
	 */
	List<TdOrder> findBySellerIdAndStatusIdOrderByOrderTimeDesc(Long sellerId, Long statusId);

	/**
	 * 查询指定归属销顾的指定状态的订单
	 * 
	 * @author DengXiao
	 */
	Page<TdOrder> findBySellerIdAndStatusIdOrderByOrderTimeDesc(Long sellerId, Long statusId, Pageable page);

	/**
	 * 根据门店的id查询门店下所有的订单
	 * 
	 * @author DengXiao
	 */
	Page<TdOrder> findByDiySiteIdAndStatusIdNotOrderByOrderTimeDesc(Long diySiteId, Long statusId, Pageable page);

	/**
	 * 根据门店id查询门店下指定状态的订单
	 * 
	 * @author DengXiao
	 */
	List<TdOrder> findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(Long diySiteId, Long statusId);

	Page<TdOrder> findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(Long diySiteId, Long statusId, Pageable page);

	/**
	 * 用户模糊查找订单，参与参数：username，orderNumber
	 * 
	 * @author DengXiao OrContainingAndUsername
	 */
	List<TdOrder> findByUsernameContainingAndUsernameOrOrderNumberContainingAndUsernameOrSellerUsernameContainingAndUsernameOrShippingNameContainingAndUsernameOrShippingAddressContainingAndUsernameOrderByOrderTimeDesc(
			String keywords1, String username1, String keywords2, String username2, String keywords3, String username3,
			String keywords4, String username4, String keywords5, String username5);

	Page<TdOrder> findByUsernameContainingAndUsernameOrOrderNumberContainingAndUsernameOrSellerUsernameContainingAndUsernameOrShippingNameContainingAndUsernameOrShippingAddressContainingAndUsernameOrderByOrderTimeDesc(
			String keywords1, String username1, String keywords2, String username2, String keywords3, String username3,
			String keywords4, String username4, String keywords5, String username5, Pageable Page);

	/**
	 * 销顾模糊查询订单，参与参数：username，orderNumber
	 * 
	 * @author DengXiao
	 * 
	 *         OrContainingAndSellerId
	 */
	List<TdOrder> findByUsernameContainingAndSellerIdOrOrderNumberContainingAndSellerIdOrShippingNameContainingAndSellerIdOrShippingAddressContainingAndSellerIdOrderByOrderTimeDesc(
			String keywords1, Long sellerId1, String keywords2, Long sellerId2, String keywords3, Long sellerId3,
			String keywords4, Long sellerId4);

	/**
	 * 销顾模糊查询订单，参与参数：username，orderNumber
	 * 
	 * @author DengXiao
	 * 
	 *         OrContainingAndSellerId
	 */
	List<TdOrder> findByUsernameContainingAndSellerIdOrOrderNumberContainingAndSellerIdOrShippingNameContainingAndSellerIdOrShippingAddressContainingAndSellerIdOrRealUserRealNameContainingAndSellerIdOrRealUserUsernameContainingAndSellerIdOrderByOrderTimeDesc(
			String keywords1, Long sellerId1, String keywords2, Long sellerId2, String keywords3, Long sellerId3,
			String keywords4, Long sellerId4, String keywords5, Long sellerId5, String keywords6, Long sellerId6);

	Page<TdOrder> findByUsernameContainingAndSellerIdOrOrderNumberContainingAndSellerIdOrShippingNameContainingAndSellerIdOrShippingAddressContainingAndSellerIdOrRealUserRealNameContainingAndSellerIdOrRealUserUsernameContainingAndSellerIdOrderByOrderTimeDesc(
			String keywords1, Long sellerId1, String keywords2, Long sellerId2, String keywords3, Long sellerId3,
			String keywords4, Long sellerId4, String keywords5, Long sellerId5, String keywords6, Long sellerId6,
			Pageable page);

	/**
	 * 店长模糊查询订单，参与参数：username,orderNumber
	 * 
	 * @author DengXiao
	 */
	List<TdOrder> findByUsernameContainingAndDiySiteIdOrOrderNumberContainingAndDiySiteIdOrShippingNameContainingAndDiySiteIdOrShippingAddressContainingAndDiySiteIdOrderByOrderTimeDesc(
			String keywords1, Long diySiteId1, String keywords2, Long diySiteId2, String keywords3, Long diySiteId3,
			String keywords4, Long diySiteId4);
	
	Page<TdOrder> findByUsernameContainingAndDiySiteIdOrOrderNumberContainingAndDiySiteIdOrShippingNameContainingAndDiySiteIdOrShippingAddressContainingAndDiySiteIdOrderByOrderTimeDesc(
			String keywords1, Long diySiteId1, String keywords2, Long diySiteId2, String keywords3, Long diySiteId3,
			String keywords4, Long diySiteId4, Pageable page);

	/**
	 * 根据城市名称和订单时间查询订单
	 * 
	 * @return
	 */
	List<TdOrder> findByCityAndOrderTimeAfterAndOrderTimeBeforeOrderByOrderTimeDesc(String city, Date begin, Date end);

	/**
	 * 配送员搜索查询
	 * 
	 * @param statusIds
	 *            订单状态
	 * @param keyword
	 *            关键字 (订单号,收货人,收货人电话,收货人地址)
	 * @param opUser
	 *            配送员编号
	 * @return 查询结果
	 * @author zp
	 */
	@Query("select o from TdOrder o,TdDeliveryInfo d "
			+ "where o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.orderNumber like %?2% or "
			+ "o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.mainOrderNumber like %?2% or "
			+ "o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.shippingName like %?2% or "
			+ "o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.shippingPhone like %?2% or "
			+ "o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.shippingAddress like %?2% or "
			+ "o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.diySiteName like %?2% or "
			+ "o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.sellerRealName like %?2% "
			+ "group by o.mainOrderNumber order by o.orderTime desc")
	List<TdOrder> queryDeliverysearch(List<Long> statusIds, String keyword, String opUser);

	/**
	 * 配送员搜索查询
	 * 
	 * @param statusIds
	 *            订单状态
	 * @param keyword
	 *            关键字 (订单号,收货人,收货人电话,收货人地址)
	 * @param opUser
	 *            配送员编号
	 * @return 查询结果
	 * @author zp
	 */
	@Query("select count(1) from TdOrder o,TdDeliveryInfo d "
			+ "where o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.orderNumber like %?2% or "
			+ "o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.mainOrderNumber like %?2% or "
			+ "o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.shippingName like %?2% or "
			+ "o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.shippingPhone like %?2% or "
			+ "o.statusId in ?1 and d.driver = ?3 and d.orderNumber = o.mainOrderNumber and o.shippingAddress like %?2% "
			+ "group by o.mainOrderNumber order by o.orderTime desc")
	Integer queryCountDeliverysearch(List<Long> statusIds, String keyword, String opUser);

	// 根据真实用户名查找所有的订单
	List<TdOrder> findByRealUserUsernameOrderByIdDesc(String username);

	Page<TdOrder> findBySellerUsernameOrderByOrderTimeDesc(String sellerUsername, Pageable page);

	Page<TdOrder> findBySellerUsernameAndStatusIdOrderByOrderTimeDesc(String sellerUsername, Long statusId,
			Pageable page);

	Page<TdOrder> findByRealUserUsernameOrderByOrderTimeDesc(String sellerUsername, Pageable page);

	Page<TdOrder> findByRealUserUsernameAndStatusIdOrderByOrderTimeDesc(String sellerUsername, Long statusId,
			Pageable page);

	@Query(value = " SELECT " + " 	o.* " + " FROM " + " 	td_order o "
			+ " LEFT JOIN td_diy_site diy ON o.diy_site_id = diy.id " + " WHERE " + " 	o.deliver_type_title = '送货上门' "
			+ " AND o.status_id NOT IN (1, 2, 3, 7, 8) " + " AND o.send_time >= ?1 " + " AND o.send_time <= ?2 "
			+ " AND diy.city LIKE ?3 " + " AND o.diy_site_code LIKE ?4 " + " AND diy.id IN ?5 "
			+ " GROUP BY o.main_order_number" + " ORDER BY " + " 	o.send_time DESC ; ", nativeQuery = true)
	List<TdOrder> queryDownList(Date begin, Date end, String cityName, String diySiteCode, List<String> roleDiyIds);

	@Query(value = " SELECT " + " 	* " + " FROM " + " 	td_order o " + " WHERE " + " 	o.main_order_number = ?1 "
			+ " AND o.order_number LIKE '%YF%'; ", nativeQuery = true)
	TdOrder findFixedFlagByMainOrderNumber(String mainOrderNumber);

	@Query(value = " SELECT " + " 	o.* " + " FROM " + " 	td_order o " + " WHERE " + " 	( "
			+ " 		o.is_coupon IS FALSE " + " 		OR o.is_coupon IS NULL " + " 	) "
			+ " AND o.order_number NOT IN ( " + " 	SELECT " + " 		order_number " + " 	FROM "
			+ " 		td_order_inf " + " 	WHERE " + " 		init_date >= ?1 " + "	AND init_date < ?2" + " ) "
			+ " AND o.order_number NOT LIKE '%XN%' " + " AND o.`status_id` NOT IN (1, 2) " + " AND o.`order_time`>= ?1 "
			+ " AND o.`order_time` < ?2	" + " ORDER BY " + " 	o.order_time DESC; ", nativeQuery = true)
	List<TdOrder> findMissedOrders(Date beginDate, Date endDate);

	@Query(value = "SELECT * from td_order where real_user_username = ?1"+ 
			" and seller_id = ?2"+
			" AND status_id BETWEEN 3 and 7"+
			" and pay_time > ?3",nativeQuery=true)
	List<TdOrder> querySalesByusernameAndsellerId(String username,Long sellerId,Date date);
	
	@Query(value = "SELECT * from td_order where real_user_username = ?1"+ 
			" and seller_id = ?2 and pay_time > ?3"+
			" AND status_id BETWEEN 3 and 7",nativeQuery=true)
	List<TdOrder> querySalesByusernameAndsellerIdAndOrderTime(String username,Long sellerId,Date date);
}
