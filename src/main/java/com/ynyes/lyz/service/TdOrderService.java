package com.ynyes.lyz.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.repository.TdOrderRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;
import com.ynyes.lyz.util.StringUtils;
import com.ynyes.lyz.util.Utils;

/**
 * TdOrder 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOrderService {
	@Autowired
	TdOrderRepo repository;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	private final Integer orderPageSize = 10;

	/**
	 * 删除
	 * 
	 * @param id
	 *            菜单项ID
	 */
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	/**
	 * 删除
	 * 
	 * @param e
	 *            菜单项
	 */
	public void delete(TdOrder e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdOrder> entities) {
		if (null != entities) {
			repository.delete(entities);
		}
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            ID
	 * @return
	 */
	public TdOrder findOne(Long id) {
		if (null == id) {
			return null;
		}

		return repository.findOne(id);
	}

	/**
	 * 查找
	 * 
	 * @param ids
	 * @return
	 */
	public List<TdOrder> findAll(Iterable<Long> ids) {
		return (List<TdOrder>) repository.findAll(ids);
	}

	public List<TdOrder> findAll() {
		return (List<TdOrder>) repository.findAll();
	}

	public Page<TdOrder> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}

	public Page<TdOrder> findAllOrderByIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository.findAll(pageRequest);
	}

	public List<TdOrder> findByCompleteOrder() {
		return repository
				.findByStatusIdAndCashCouponIdNotNullOrStatusIdAndCashCouponIdNotNullOrStatusIdAndProductCouponIdNotNullOrStatusIdAndProductCouponIdNotNullOrderByOrderTimeDesc(
						5L, 6L, 5L, 6L);
	}

	/**
	 * 根据门店查询订单
	 * 
	 * @param diyCode
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdOrder> findByDiyCode(String diyCode, int page, int size) {
		if (diyCode == null) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository.findByDiySiteCode(diyCode, pageRequest);
	}

	public Page<TdOrder> findByDiyCodeAndStatusIdOrderByIdDesc(String diyCode, Long statusId, Integer page,
			Integer size) {
		if (diyCode == null || statusId == null || page == null || size == null) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByDiySiteCodeAndStatusIdOrderByIdDesc(diyCode, statusId, pageRequest);
	}

	public Page<TdOrder> findByDiySiteCodeAndOrderNumberContainingOrDiySiteCodeAndUsernameContainingOrderByIdDesc(
			String diyCode, String orderNumbers, String username, int page, int size) {
		if (diyCode == null || orderNumbers == null || username == null) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByDiySiteCodeAndOrderNumberContainingOrDiySiteCodeAndUsernameContainingOrderByIdDesc(
				diyCode, orderNumbers, diyCode, username, pageRequest);
	}

	public Page<TdOrder> findByOrderNumberContainingOrUsernameContainingOrderByIdDesc(String orderNumbers,
			String username, int size, int page) {
		if (orderNumbers == null || username == null) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByOrderNumberContainingOrUsernameContainingOrderByIdDesc(orderNumbers, username,
				pageRequest);
	}

	public Page<TdOrder> findByStatusIdOrderByIdDesc(long statusId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByStatusIdOrderByIdDesc(statusId, pageRequest);
	}

	public Page<TdOrder> findByUsername(String username, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameOrderByIdDesc(username, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdNot(String username, Long StatusId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdNotOrderByIdDesc(username, StatusId, pageRequest);
		// return repository.findByUsernameOrderByIdDesc(username, pageRequest);
	}

	/**
	 * 根据时间查找
	 * 
	 * @return
	 */
	public List<TdOrder> findByBeginAndEndOrderByOrderTimeDesc(Date begin, Date end) {
		return repository.findByOrderTimeAfterAndOrderTimeBeforeOrderByOrderTimeDesc(begin, end);
	}

	public List<TdOrder> findByDiySiteCodeAndOrderTimeAfterAndOrderTimeBeforeOrderByOrderTimeDesc(String diyCode,
			Date begin, Date end) {
		return repository.findByDiySiteCodeAndOrderTimeAfterAndOrderTimeBeforeOrderByOrderTimeDesc(diyCode, begin, end);
	}

	// zhangji
	public Page<TdOrder> findByUsernameAndStatusIdOrUsernameAndStatusIdOrUsernameAndStatusIdOrderByIdDesc(
			String username1, Long statusId1, String username2, Long statusId2, String username3, Long statusId3,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdOrUsernameAndStatusIdOrUsernameAndStatusIdOrderByIdDesc(username1,
				4L, username2, 5L, username3, 6L, pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByUsernameAndStatusIdOrStatusIdOrStatusIdOrStatusId(String username, Long statusId1,
			Long statusId2, Long statusId3, Long statusId4, Integer page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameAndStatusIdOrStatusIdOrStatusIdOrStatusId(username, 3L, 4L, 6L, 7L,
				pageRequest);
	}

	public TdOrder findByOrderNumber(String orderNumber) {
		return repository.findByOrderNumber(orderNumber);
	}

	public Page<TdOrder> findByUsernameAndTimeAfter(String username, Date time, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndOrderTimeAfterOrderByIdDesc(username, time, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndTimeAfterAndSearch(String username, Date time, String keywords, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(username, time, keywords,
				pageRequest);
	}

	public Page<TdOrder> findByUsernameAndSearch(String username, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndOrderNumberContainingOrderByIdDesc(username, keywords, pageRequest);
	}

	public Page<TdOrder> findByisComplainedByusernameAndSearch(List<Long> orderids, String keywords, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIdInAndOrderNumberContainingOrderByIdDesc(orderids, keywords, pageRequest);
	}

	public Page<TdOrder> findByisComplainedByusername(List<Long> orderids, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIdInOrderByIdDesc(orderids, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdNotAndSearch(String username, Long StatusId, String keywords,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdNotAndOrderNumberContainingOrderByIdDesc(username, StatusId,
				keywords, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusId(String username, long statusId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdOrderByIdDesc(username, statusId, pageRequest);
	}

	// 根据用户名和状态查找订单（不分页）
	public List<TdOrder> findByUsernameAndStatusId(String username, Long statusId) {
		if (null == username || null == statusId) {
			return null;
		}
		return repository.findByUsernameAndStatusIdOrRealUserUsernameAndStatusIdOrderByIdDesc(username, statusId,
				username, statusId);
	}

	// 根据用户名查找所有的订单（不分页）
	public List<TdOrder> findByUsername(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameOrderByIdDesc(username);
	}

	// zhangji
	public Page<TdOrder> findByUsernameAndSearchAndStatusIdOrUsernameAndSearchAndStatusIdOrUsernameAndSearchAndStatusId(
			String username1, String keywords1, Long statuisId1, String username2, String keywords2, Long statuisId2,
			String username3, String keywords3, Long statuisId3, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository
				.findByUsernameAndOrderNumberContainingAndStatusIdOrUsernameAndOrderNumberContainingAndStatusIdOrUsernameAndOrderNumberContainingAndStatusIdOrderByIdDesc(
						username1, keywords1, 4L, username2, keywords2, 5L, username3, keywords3, 6L, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusId(
			String username1, String keywords1, Long statuisId1, String username2, String keywords2, Long statuisId2,
			String username3, String keywords3, Long statuisId3, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository
				.findByUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusIdOrderByIdDesc(
						username1, keywords1, 4L, username2, keywords2, 5L, username3, keywords3, 6L, pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByUsernameAndIsCancelTrue(String username, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndIsCancelTrue(username, pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByIsCancelTrue(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsCancelTrue(pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByIsCancelTrueAndIsRefundFalse(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsCancelTrueAndIsRefundFalse(pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByIsCancelTrueAndIsRefundTrue(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsCancelTrueAndIsRefundTrue(pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdAndSearch(String username, long statusId, String keywords, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdAndOrderNumberContainingOrderByIdDesc(username, statusId, keywords,
				pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdAndTimeAfter(String username, long statusId, Date time, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdAndOrderTimeAfterOrderByIdDesc(username, statusId, time,
				pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdAndTimeAfterAndSearch(String username, long statusId, Date time,
			String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(username,
				statusId, time, keywords, pageRequest);
	}

	public Long countByUsernameAndStatusId(String username, long statusId) {
		return repository.countByUsernameAndStatusId(username, statusId);
	}

	public List<TdOrder> findByStatusId(Long statusId) {
		return repository.findByStatusId(statusId);
	}

	public Long countByStatusId(Long statusId) {
		return repository.countByStatusId(statusId);
	}

	public List<TdOrder> findByStatusIdAndDeliveryTimeAfter(Long statusId, Date time, List<String> orderNumberList) {
		if (null == statusId || null == time || null == orderNumberList || orderNumberList.size() == 0) {
			return null;
		}

		return repository.findDistinctMainOrderNumberByStatusIdAndDeliveryTimeAfterAndOrderNumberInOrderByIdDesc(
				statusId, time, orderNumberList);
	}

	public List<TdOrder> findByStatusIdAndDeliveryTimeAfterOrStatusIdAndDeliveryTimeAfter(Long statusId, Long statusId2,
			List<String> orderNumberList, Date time) {
		if (null == statusId || null == statusId2 || null == time) {
			return null;
		}

		return repository
				.findDistinctMainOrderNumberByStatusIdAndDeliveryTimeAfterAndOrderNumberInOrStatusIdAndDeliveryTimeAfterAndOrderNumberInOrderByIdDesc(
						statusId, time, orderNumberList, statusId2, time, orderNumberList);
	}

	public List<TdOrder> findByStatusIdAndDeliveryTimeBetween(Long statusId, List<String> orderNumberList, Date start,
			Date end) {

		if (null == statusId || null == start || null == end || null == orderNumberList
				|| orderNumberList.size() == 0) {
			return null;
		}

		return repository.findDistinctMainOrderNumberByStatusIdAndDeliveryTimeBetweenAndOrderNumberInOrderByIdDesc(
				statusId, start, end, orderNumberList);
	}

	public List<TdOrder> findByStatusIdAndDeliveryTimeBetweenOrStatusIdAndOrderTimeBetween(Long statusId,
			Long statusId2, List<String> orderNumberList, Date start, Date end) {

		if (null == statusId || null == statusId2 || null == start || null == end || null == orderNumberList
				|| orderNumberList.size() == 0) {
			return null;
		}

		return repository
				.findDistinctMainOrderNumberByStatusIdAndDeliveryTimeBetweenAndOrderNumberInOrStatusIdAndOrderTimeBetweenAndOrderNumberInOrderByIdDesc(
						statusId, start, end, orderNumberList, statusId2, start, end, orderNumberList);
	}

	public Integer countByStatusIdAndDeliveryTimeAfter(Long statusId, Date time, List<String> orderNumberList) {
		if (null == statusId || null == time || null == orderNumberList || orderNumberList.size() == 0) {
			return null;
		}

		return repository.countDistinctMainOrderNumberByStatusIdAndDeliveryTimeAfterAndOrderNumberInOrderByIdDesc(
				statusId, time, orderNumberList);
	}

	public Integer countByStatusIdAndDeliveryTimeAfterOrStatusIdAndDeliveryTimeAfter(Long statusId, Long statusId2,
			List<String> orderNumberList, Date time) {
		if (null == statusId || null == statusId2 || null == time) {
			return null;
		}

		return repository
				.countDistinctMainOrderNumberByStatusIdAndDeliveryTimeAfterAndOrderNumberInOrStatusIdAndDeliveryTimeAfterAndOrderNumberInOrderByIdDesc(
						statusId, time, orderNumberList, statusId2, time, orderNumberList);
	}

	public Integer countByStatusIdAndDeliveryTimeBetween(Long statusId, List<String> orderNumberList, Date start,
			Date end) {

		if (null == statusId || null == start || null == end || null == orderNumberList
				|| orderNumberList.size() == 0) {
			return null;
		}

		return repository.countDistinctMainOrderNumberByStatusIdAndDeliveryTimeBetweenAndOrderNumberInOrderByIdDesc(
				statusId, start, end, orderNumberList);
	}

	public Integer countByStatusIdAndDeliveryTimeBetweenOrStatusIdAndOrderTimeBetween(Long statusId, Long statusId2,
			List<String> orderNumberList, Date start, Date end) {

		if (null == statusId || null == statusId2 || null == start || null == end || null == orderNumberList
				|| orderNumberList.size() == 0) {
			return null;
		}

		return repository
				.countDistinctMainOrderNumberByStatusIdAndDeliveryTimeBetweenAndOrderNumberInOrStatusIdAndDeliveryTimeBetweenAndOrderNumberInOrderByIdDesc(
						statusId, start, end, orderNumberList, statusId2, start, end, orderNumberList);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdOrder save(TdOrder e) {
		if (null == e) {
			return e;
		}
		// 过滤特殊字符
		if (e.getRemark() != null) {
			e.setRemark(StringUtils.StringFilter(e.getRemark()));
		}
		if (e.getRemarkInfo() != null) {
			e.setRemarkInfo(StringUtils.StringFilter(e.getRemarkInfo()));
		}
		return repository.save(e);
	}

	public List<TdOrder> save(List<TdOrder> entities) {

		return (List<TdOrder>) repository.save(entities);
	}

	public List<TdOrder> findByUsernameAndStatusIdNotOrderByOrderTimeDesc(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameAndStatusIdNotOrderByOrderTimeDesc(username, 8L);
	}

	// 查找用户所有非删除的订单
	public Page<TdOrder> findByUsernameAndStatusIdNotOrderByOrderTimeDesc(String username, int page, int size) {
		if (null == username) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameAndStatusIdNotOrRealUserUsernameAndStatusIdNotOrderByOrderTimeDesc(username, 8L,
				username, 8L, pageRequest);
	}

	public List<TdOrder> findByOrderNumberContaining(String orderNumber) {
		if (null == orderNumber) {
			return null;
		}
		return repository.findByOrderNumberContaining(orderNumber);
	}

	public List<TdOrder> findByMainOrderNumberIgnoreCase(String mainOrderNumber) {
		if (null == mainOrderNumber) {
			return null;
		}
		return repository.findByMainOrderNumberIgnoreCase(mainOrderNumber);
	}

	public List<TdOrder> findByStatusIdOrderByOrderTimeDesc(Long statusId) {
		if (null == statusId) {
			return null;
		}
		return repository.findByStatusIdOrderByOrderTimeDesc(statusId);
	}

	/**
	 * 根据时间查询 只查询总单号
	 * 
	 * @return
	 */
	public List<TdOrder> searchMainOrderNumberByTime(Date begin, Date end) {
		return repository.searchOrderByTime(begin, end);
	}

	/**
	 * 根据时间 配送门店 查询总单号
	 * 
	 * @return
	 */
	public List<TdOrder> searchMainOrderNumberByTimeAndDiySiteCode(String diyCode, Date begin, Date end) {
		return repository.searchMainOrderNumberByOrderTimeAndDiySiteCode(diyCode, begin, end);
	}

	/**
	 * 订单条件查询 分页 ======== 2016-12-07 ======= yanle ======= 增加‘配送方式’查询条件 ========
	 * 
	 * @return
	 */
	public Page<TdOrder> findAllAddConditionDeliveryType(String keywords, String orderStartTime, String orderEndTime,
			List<String> usernameList, String sellerRealName, String shippingAddress, String shippingPhone,
			String deliveryTime, String userPhone, String shippingName, String sendTime, Long statusId, String diyCode,
			String city, String deliverTypeTitle, List<String> roleCitys, List<String> roleDiys, int size, int page,
			Boolean isFitment) {
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdOrder> c = new Criteria<TdOrder>();
		if (isFitment) {
			c.add(Restrictions.like("orderNumber", "FIT", true));
		} else {
			c.add(Restrictions.notLike("orderNumber", "FIT", true));
		}
		if (null != keywords && !keywords.equalsIgnoreCase("")) {
			c.add(Restrictions.like("orderNumber", keywords, true));
		}
		if (null != orderStartTime && !orderStartTime.equals("")) {
			c.add(Restrictions.gte("orderTime", stringToDate(orderStartTime, null), true));

		}
		if (null != orderEndTime && !orderEndTime.equals("")) {
			c.add(Restrictions.lte("orderTime", stringToDate(orderEndTime, null), true));
		}

		if (null != userPhone && !"".equals(userPhone)) {
			c.add(Restrictions.like("username", userPhone, true));
		}
		if (null != shippingName && !"".equals(shippingName)) {
			c.add(Restrictions.like("shippingName", shippingName, true));
		}
		if (null != shippingPhone && !"".equals(shippingPhone)) {
			c.add(Restrictions.like("shippingPhone", shippingPhone, true));
		}
		if (null != shippingAddress && !"".equals(shippingAddress)) {
			c.add(Restrictions.like("shippingAddress", shippingAddress, true));
		}

		if (null != usernameList && usernameList.size() > 0) {
			c.add(Restrictions.in("username", usernameList, true));
		}
		if (null != deliveryTime && !deliveryTime.equals("")) {
			c.add(Restrictions.eq("realUserRealName", stringToDate(deliveryTime, null), true));
		}
		if (null != sendTime && !sendTime.equals("")) {
			c.add(Restrictions.eq("sendTime", stringToDate(sendTime, null), true));
		}
		if (null != sellerRealName && !"".equals(sellerRealName)) {
			c.add(Restrictions.eq("sellerRealName", sellerRealName, true));
		}
		if (null != statusId && !statusId.equals(0L)) {
			if (statusId.equals(6L)) {
				// c.add(Restrictions.eq("statusId", statusId, true));
				c.add(Restrictions.or(Restrictions.eq("statusId", 5L, true), Restrictions.eq("statusId", 6L, true)));
			} else {
				c.add(Restrictions.eq("statusId", statusId, true));
			}
		}
		if (null != diyCode && !"".equals(diyCode)) {
			c.add(Restrictions.eq("diySiteCode", diyCode, true));
		}
		// city为收货人地址
		// if (null != city && !"".equals(city)) {
		// c.add(Restrictions.eq("city", city, true));
		// }
		if (null != roleCitys && roleCitys.size() > 0) {
			c.add(Restrictions.in("diySiteCode", roleCitys, true));
		}
		if (null != roleDiys && roleDiys.size() > 0) {
			c.add(Restrictions.in("diySiteCode", roleDiys, true));
		}
		if (null != deliverTypeTitle && !"".equals(deliverTypeTitle)) {
			c.add(Restrictions.eq("deliverTypeTitle", deliverTypeTitle, true));
		}
		c.setOrderByDesc("orderTime");
		return repository.findAll(c, pageRequest);
	}

	
	
	/**
	 * 订单条件查询 分页 ======== 2016-12-07 ======= yanle ======= 增加‘配送方式’查询条件 ========
	 * 
	 * @return
	 */
	public Page<TdOrder> findAllAddConditionDeliveryType2(String company,String keywords, String orderStartTime, String orderEndTime,
			List<String> usernameList, String sellerRealName, String shippingAddress, String shippingPhone,
			String deliveryTime, String userPhone, String shippingName, String sendTime, Long statusId, String diyCode,
			String city, String deliverTypeTitle, List<String> roleCitys, List<String> roleDiys, int size, int page,
			Boolean isFitment) {
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdOrder> c = new Criteria<TdOrder>();
		if (null != company && !"".equals(company)) {
			c.add(Restrictions.eq("diySiteId", company, true));
		}
		if (isFitment) {
			c.add(Restrictions.like("orderNumber", "FIT", true));
		} else {
			c.add(Restrictions.notLike("orderNumber", "FIT", true));
		}
		if (null != keywords && !keywords.equalsIgnoreCase("")) {
			c.add(Restrictions.like("orderNumber", keywords, true));
		}
		if (null != orderStartTime && !orderStartTime.equals("")) {
			c.add(Restrictions.gte("orderTime", stringToDate(orderStartTime, null), true));

		}
		if (null != orderEndTime && !orderEndTime.equals("")) {
			c.add(Restrictions.lte("orderTime", stringToDate(orderEndTime, null), true));
		}

		if (null != userPhone && !"".equals(userPhone)) {
			c.add(Restrictions.like("username", userPhone, true));
		}
		if (null != shippingName && !"".equals(shippingName)) {
			c.add(Restrictions.like("shippingName", shippingName, true));
		}
		if (null != shippingPhone && !"".equals(shippingPhone)) {
			c.add(Restrictions.like("shippingPhone", shippingPhone, true));
		}
		if (null != shippingAddress && !"".equals(shippingAddress)) {
			c.add(Restrictions.like("shippingAddress", shippingAddress, true));
		}

		if (null != usernameList && usernameList.size() > 0) {
			c.add(Restrictions.in("username", usernameList, true));
		}
		if (null != deliveryTime && !deliveryTime.equals("")) {
			c.add(Restrictions.eq("realUserRealName", stringToDate(deliveryTime, null), true));
		}
		if (null != sendTime && !sendTime.equals("")) {
			c.add(Restrictions.eq("sendTime", stringToDate(sendTime, null), true));
		}
		if (null != sellerRealName && !"".equals(sellerRealName)) {
			c.add(Restrictions.eq("sellerRealName", sellerRealName, true));
		}
		if (null != statusId && !statusId.equals(0L)) {
			c.add(Restrictions.eq("statusId", statusId, true));
		}
		if (null != diyCode && !"".equals(diyCode)) {
			c.add(Restrictions.eq("diySiteCode", diyCode, true));
		}
		// city为收货人地址
		// if (null != city && !"".equals(city)) {
		// c.add(Restrictions.eq("city", city, true));
		// }
		if (null != roleCitys && roleCitys.size() > 0) {
			c.add(Restrictions.in("diySiteCode", roleCitys, true));
		}
		if (null != roleDiys && roleDiys.size() > 0) {
			c.add(Restrictions.in("diySiteCode", roleDiys, true));
		}
		if (null != deliverTypeTitle && !"".equals(deliverTypeTitle)) {
			c.add(Restrictions.eq("deliverTypeTitle", deliverTypeTitle, true));
		}
		c.setOrderByDesc("orderTime");
		return repository.findAll(c, pageRequest);
	}
	
	
	
	/**
	 * 订单条件查询 分页 修改城市查询
	 * 
	 * @return
	 */
	public Page<TdOrder> findAll(String keywords, String orderStartTime, String orderEndTime, List<String> usernameList,
			String sellerRealName, String shippingAddress, String shippingPhone, String deliveryTime, String userPhone,
			String shippingName, String sendTime, Long statusId, String diyCode, String city, List<String> roleCitys,
			List<String> roleDiys, int size, int page) {
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdOrder> c = new Criteria<TdOrder>();
		if (null != keywords && !keywords.equalsIgnoreCase("")) {
			c.add(Restrictions.like("orderNumber", keywords, true));
		}
		if (null != orderStartTime && !orderStartTime.equals("")) {
			c.add(Restrictions.gte("orderTime", stringToDate(orderStartTime, null), true));

		}
		if (null != orderEndTime && !orderEndTime.equals("")) {
			c.add(Restrictions.lte("orderTime", stringToDate(orderEndTime, null), true));
		}

		if (null != userPhone && !"".equals(userPhone)) {
			c.add(Restrictions.like("username", userPhone, true));
		}
		if (null != shippingName && !"".equals(shippingName)) {
			c.add(Restrictions.like("shippingName", shippingName, true));
		}
		if (null != shippingPhone && !"".equals(shippingPhone)) {
			c.add(Restrictions.like("shippingPhone", shippingPhone, true));
		}
		if (null != shippingAddress && !"".equals(shippingAddress)) {
			c.add(Restrictions.like("shippingAddress", shippingAddress, true));
		}

		if (null != usernameList && usernameList.size() > 0) {
			c.add(Restrictions.in("username", usernameList, true));
		}
		if (null != deliveryTime && !deliveryTime.equals("")) {
			c.add(Restrictions.eq("realUserRealName", stringToDate(deliveryTime, null), true));
		}
		if (null != sendTime && !sendTime.equals("")) {
			c.add(Restrictions.eq("sendTime", stringToDate(sendTime, null), true));
		}
		if (null != sellerRealName && !"".equals(sellerRealName)) {
			c.add(Restrictions.eq("sellerRealName", sellerRealName, true));
		}
		if (null != statusId && !statusId.equals(0L)) {
			c.add(Restrictions.eq("statusId", statusId, true));
		}
		if (null != diyCode && !"".equals(diyCode)) {
			c.add(Restrictions.eq("diySiteCode", diyCode, true));
		}
		// city为收货人地址
		// if (null != city && !"".equals(city)) {
		// c.add(Restrictions.eq("city", city, true));
		// }
		if (null != roleCitys && roleCitys.size() > 0) {
			c.add(Restrictions.in("diySiteCode", roleCitys, true));
		}
		if (null != roleDiys && roleDiys.size() > 0) {
			c.add(Restrictions.in("diySiteCode", roleDiys, true));
		}
		c.setOrderByDesc("orderTime");
		return repository.findAll(c, pageRequest);
	}

	/**
	 * 字符串转换时间默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	private Date stringToDate(String time, String dateFormat) {
		if (null == dateFormat || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		if (null != time && !time.equals("")) {
			try {
				date = sdf.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 查询指定归属销顾的订单
	 * 
	 * @author DengXiao
	 */
	public List<TdOrder> findBySellerIdAndStatusIdNotOrderByOrderTimeDesc(Long sellerId) {
		if (null == sellerId) {
			return null;
		}
		return repository.findBySellerIdAndStatusIdNotOrderByOrderTimeDesc(sellerId, 8L);
	}

	/**
	 * 查询指定归属销顾的订单
	 * 
	 * @author DengXiao
	 */
	public Page<TdOrder> findBySellerIdAndStatusIdNotOrderByOrderTimeDesc(Long sellerId, int page, int size) {
		if (null == sellerId) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findBySellerIdAndStatusIdNotOrderByOrderTimeDesc(sellerId, 8L, pageRequest);
	}

	/**
	 * 查询指定归属销顾的指定状态的订单
	 * 
	 * @author DengXiao
	 */
	public List<TdOrder> findBySellerIdAndStatusIdOrderByOrderTimeDesc(Long sellerId, Long statusId) {
		if (null == sellerId || null == statusId) {
			return null;
		}
		return repository.findBySellerIdAndStatusIdOrderByOrderTimeDesc(sellerId, statusId);
	}

	public Page<TdOrder> findBySellerIdAndStatusIdOrderByOrderTimeDesc(Long sellerId, Long statusId, Integer page,
			Integer size) {
		if (null == sellerId || null == statusId) {
			return null;
		}
		return repository.findBySellerIdAndStatusIdOrderByOrderTimeDesc(sellerId, statusId,
				new PageRequest(page, size));
	}

	/**
	 * 根据门店的id查询门店下所有的订单
	 * 
	 * @author DengXiao
	 */
	public Page<TdOrder> findByDiySiteIdAndStatusIdNotOrderByOrderTimeDesc(Long diySiteId, int page, int size) {
		if (null == diySiteId) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByDiySiteIdAndStatusIdNotOrderByOrderTimeDesc(diySiteId, 8L, pageRequest);
	}

	/**
	 * 根据门店id查询门店下指定状态的订单
	 * 
	 * @author DengXiao
	 */
	public List<TdOrder> findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(Long diySiteId, Long statusId) {
		if (null == diySiteId || null == statusId) {
			return null;
		}
		return repository.findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(diySiteId, statusId);
	}

	public Page<TdOrder> findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(Long diySiteId, Long statusId, Integer page,
			Integer size) {
		if (null == diySiteId || null == statusId) {
			return null;
		}
		return repository.findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(diySiteId, statusId,
				new PageRequest(page, size));
	}

	/**
	 * 用户模糊查找订单，参与参数：username，orderNumber
	 * 
	 * @author DengXiao
	 */
	public List<TdOrder> findByUsernameContainingAndUsernameOrOrderNumberContainingAndUsernameOrderByOrderTimeDesc(
			String keywords, String username) {
		if (null == keywords || null == username) {
			return null;
		}
		return repository
				.findByUsernameContainingAndUsernameOrOrderNumberContainingAndUsernameOrSellerUsernameContainingAndUsernameOrShippingNameContainingAndUsernameOrShippingAddressContainingAndUsernameOrderByOrderTimeDesc(
						keywords, username, keywords, username, keywords, username, keywords, username, keywords,
						username);
	}

	public Page<TdOrder> findByUsernameContainingAndUsernameOrOrderNumberContainingAndUsernameOrderByOrderTimeDesc(
			String keywords, String username, Integer page, Integer size) {
		if (null == keywords || null == username) {
			return null;
		}
		return repository
				.findByUsernameContainingAndUsernameOrOrderNumberContainingAndUsernameOrSellerUsernameContainingAndUsernameOrShippingNameContainingAndUsernameOrShippingAddressContainingAndUsernameOrderByOrderTimeDesc(
						keywords, username, keywords, username, keywords, username, keywords, username, keywords,
						username, new PageRequest(page, size));
	}

	/**
	 * 销顾模糊查询订单，参与参数：username，orderNumber
	 * 
	 * @author DengXiao
	 */
	public List<TdOrder> findByUsernameContainingAndSellerIdOrOrderNumberContainingAndSellerIdOrderByOrderTimeDesc(
			String keywords, Long sellerId) {
		if (null == keywords || null == sellerId) {
			return null;
		}
		return repository
				.findByUsernameContainingAndSellerIdOrOrderNumberContainingAndSellerIdOrShippingNameContainingAndSellerIdOrShippingAddressContainingAndSellerIdOrRealUserRealNameContainingAndSellerIdOrRealUserUsernameContainingAndSellerIdOrderByOrderTimeDesc(
						keywords, sellerId, keywords, sellerId, keywords, sellerId, keywords, sellerId, keywords,
						sellerId, keywords, sellerId);
	}

	public Page<TdOrder> findByUsernameContainingAndSellerIdOrOrderNumberContainingAndSellerIdOrderByOrderTimeDesc(
			String keywords, Long sellerId, Integer page, Integer size) {
		if (null == keywords || null == sellerId) {
			return null;
		}
		return repository
				.findByUsernameContainingAndSellerIdOrOrderNumberContainingAndSellerIdOrShippingNameContainingAndSellerIdOrShippingAddressContainingAndSellerIdOrRealUserRealNameContainingAndSellerIdOrRealUserUsernameContainingAndSellerIdOrderByOrderTimeDesc(
						keywords, sellerId, keywords, sellerId, keywords, sellerId, keywords, sellerId, keywords,
						sellerId, keywords, sellerId, new PageRequest(page, size));
	}

	/**
	 * 店长模糊查询订单，参与参数：username,orderNumber
	 * 
	 * @author DengXiao
	 */
	public List<TdOrder> findByUsernameContainingAndDiySiteIdOrOrderNumberContainingAndDiySiteIdOrderByOrderTimeDesc(
			String keywords, Long diySiteId) {
		if (null == keywords || null == diySiteId) {
			return null;
		}
		return repository
				.findByUsernameContainingAndDiySiteIdOrOrderNumberContainingAndDiySiteIdOrShippingNameContainingAndDiySiteIdOrShippingAddressContainingAndDiySiteIdOrderByOrderTimeDesc(
						keywords, diySiteId, keywords, diySiteId, keywords, diySiteId, keywords, diySiteId);
	}

	public Page<TdOrder> findByUsernameContainingAndDiySiteIdOrOrderNumberContainingAndDiySiteIdOrderByOrderTimeDesc(
			String keywords, Long diySiteId, Integer page, Integer size) {
		if (null == keywords || null == diySiteId) {
			return null;
		}
		return repository
				.findByUsernameContainingAndDiySiteIdOrOrderNumberContainingAndDiySiteIdOrShippingNameContainingAndDiySiteIdOrShippingAddressContainingAndDiySiteIdOrderByOrderTimeDesc(
						keywords, diySiteId, keywords, diySiteId, keywords, diySiteId, keywords, diySiteId,
						new PageRequest(page, size));
	}

	/**
	 * 根据城市名称和订单时间查询订单
	 * 
	 * @return
	 */
	public List<TdOrder> findByCityAndOrderTimeAfterAndOrderTimeBeforeOrderByOrderTimeDesc(String city, Date begin,
			Date end) {
		return repository.findByCityAndOrderTimeAfterAndOrderTimeBeforeOrderByOrderTimeDesc(city, begin, end);
	}

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
	public List<TdOrder> queryDeliverysearch(List<Long> statusIds, String keyword, String opUser) {
		return repository.queryDeliverysearch(statusIds, keyword, opUser);
	}

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
	public Integer queryCountDeliverysearch(List<Long> statusIds, String keyword, String opUser) {
		return repository.queryCountDeliverysearch(statusIds, keyword, opUser);
	}

	/**
	 * 修改订单实收款
	 * 
	 * @param cash
	 *            收款现金
	 * @param pos
	 *            收款pos
	 * @param mainOrderNumber
	 *            主单号
	 * @return 默认不会出现问题 没想好怎么处理这些异常状态
	 * @author zp
	 */
	public Long modifyOrderPay(Double cash, Double pos, Double other, String mainOrderNumber) {
		// 主单号为空
		if (mainOrderNumber == null) {
			return 1L;
		}
		cash = Utils.checkNull(cash);
		pos = Utils.checkNull(pos);
		other = Utils.checkNull(other);
		// 收款为0
		if (cash == 0.0 && pos == 0.0 && other == 0.0) {
			return 2L;
		}
		List<TdOrder> orderList = repository.findByMainOrderNumberIgnoreCase(mainOrderNumber);
		// 意思一下
		if (orderList != null && orderList.size() > 0) {
			// 循环所有分单
			for (TdOrder order : orderList) {
				// 计算比例(暂定)
				Double point;
				point = order.getPoint();
				if (null == point) {
					point = order.getTotalPrice() / order.getAllTotalPay();
				}
				// 修改实收款
				order.setCashPay(cash * point + Utils.checkNull(order.getCashPay()));
				order.setPosPay(pos * point + Utils.checkNull(order.getPosPay()));
				order.setBackOtherPay(other * point + Utils.checkNull(order.getBackOtherPay()));
				// 保存修改
				repository.save(order);
			}
		}

		return 0L;
	}

	// 根据用户名查找所有的订单（不分页）
	public List<TdOrder> findByRealUserUsernameOrderByIdDesc(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByRealUserUsernameOrderByIdDesc(username);
	}

	public Page<TdOrder> findBySellerUsernameOrderByOrderTimeDesc(String sellerUsername, Long status, Integer page,
			Integer size) {
		if (null == sellerUsername) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		if (status.equals(0L)) {
			return this.repository.findBySellerUsernameOrderByOrderTimeDesc(sellerUsername, pageRequest);
		} else {
			return this.repository.findBySellerUsernameAndStatusIdOrderByOrderTimeDesc(sellerUsername, status,
					pageRequest);
		}
	}

	public Page<TdOrder> findByRealUserUsernameOrderByOrderTimeDesc(String sellerUsername, Long status, Integer page,
			Integer size) {
		if (null == sellerUsername) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		if (status.equals(0L)) {
			return this.repository.findByRealUserUsernameOrderByOrderTimeDesc(sellerUsername, pageRequest);
		} else {
			return this.repository.findByRealUserUsernameAndStatusIdOrderByOrderTimeDesc(sellerUsername, status,
					pageRequest);
		}
	}

	public List<TdOrder> findOrdersOfDeliveryHome(Date begin, Date end, String diySiteCode, String cityName,
			List<String> roleDiyIds) {
		// 判断空值
		if (null == begin || "".equals(begin)) {
			begin = Utils.getSysStartDate();
		}
		if (null == end || "".equals(end)) {
			end = new Date();
		}
		if (org.apache.commons.lang3.StringUtils.isBlank(cityName)) {
			cityName = "%";
		}
		if (org.apache.commons.lang3.StringUtils.isBlank(diySiteCode)) {
			diySiteCode = "%";
		}
		if (null == roleDiyIds || roleDiyIds.size() == 0) {
			roleDiyIds.add("0");
		}
		return repository.queryDownList(begin, end, cityName, diySiteCode, roleDiyIds);
	}

	public TdOrder findFixedFlagByMainOrderNumber(String mainOrderNumber) {
		if (null == mainOrderNumber) {
			return null;
		}
		return repository.findFixedFlagByMainOrderNumber(mainOrderNumber);
	}

	public List<TdOrder> findMissedOrders(Date beginDate, Date endDate) {
		return repository.findMissedOrders(beginDate, endDate);
	}

	Page<TdOrder> findByUsernameAndStatusIdOrUsernameAndStatusIdOrderByIdDesc(String username, Long statusId1,
			Long statusId2, Integer page, Integer size) {
		return repository.findByUsernameAndStatusIdOrUsernameAndStatusIdOrderByIdDesc(username, statusId1, username,
				statusId2, new PageRequest(page, size));

	}

	Page<TdOrder> findBySellerIdAndStatusIdOrSellerIdAndStatusIdOrderByIdDesc(Long sellerId, Long statusId1,
			Long statusId2, Integer page, Integer size) {
		return repository.findBySellerIdAndStatusIdOrSellerIdAndStatusIdOrderByIdDesc(sellerId, statusId1, sellerId,
				statusId2, new PageRequest(page, size));
	}

	Page<TdOrder> findByDiySiteIdAndStatusIdOrDiySiteIdAndStatusIdOrderByIdDesc(Long diySiteId, Long statusId1,
			Long statusId2, Integer page, Integer size) {
		return repository.findByDiySiteIdAndStatusIdOrDiySiteIdAndStatusIdOrderByIdDesc(diySiteId, statusId1, diySiteId,
				statusId2, new PageRequest(page, size));
	}

	/**
	 * 特殊方法，特定用于在会员中心中查看订单列表
	 * 
	 * @param orderType
	 *            当前需要查看的订单订单类型
	 * @param user
	 *            当前登录的用户
	 * @return
	 */
	public Page<TdOrder> findByOrderTypeAndUser(Long orderType, TdUser user, Integer page) {
		// 先判断用户的身份
		Long userType = user.getUserType();
		Long upperDiySiteId = user.getUpperDiySiteId();
		TdDiySite diySite = tdDiySiteService.findOne(upperDiySiteId);
		Page<TdOrder> orderPage = null;
		if (orderType.equals(0L)) {
			if (userType.equals(0L)) {
				orderPage = this.findByUsernameAndStatusIdNotOrderByOrderTimeDesc(user.getUsername(), page,
						this.orderPageSize);
			} else if (userType.equals(1L)) {
				orderPage = this.findBySellerIdAndStatusIdNotOrderByOrderTimeDesc(user.getId(), page,
						this.orderPageSize);
			} else if (userType.equals(2L)) {
				orderPage = this.findByDiySiteIdAndStatusIdNotOrderByOrderTimeDesc(diySite.getId(), page,
						this.orderPageSize);
			}
		} else if (orderType.equals(1L)) {
			if (userType.equals(0L)) {
				orderPage = this.findByUsernameAndStatusId(user.getUsername(), 2L, page, this.orderPageSize);
			} else if (userType.equals(1L)) {
				orderPage = this.findBySellerIdAndStatusIdOrderByOrderTimeDesc(user.getId(), 2L, page,
						this.orderPageSize);
			} else if (userType.equals(2L)) {
				orderPage = this.findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(diySite.getId(), 2L, page,
						this.orderPageSize);
			}
		} else if (orderType.equals(2L)) {
			if (userType.equals(0L)) {
				orderPage = this.findByUsernameAndStatusId(user.getUsername(), 3L, page, this.orderPageSize);
			} else if (userType.equals(1L)) {
				orderPage = this.findBySellerIdAndStatusIdOrderByOrderTimeDesc(user.getId(), 3L, page,
						this.orderPageSize);
			} else if (userType.equals(2L)) {
				orderPage = this.findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(diySite.getId(), 3L, page,
						this.orderPageSize);
			}
		} else if (orderType.equals(3L)) {
			if (userType.equals(0L)) {
				orderPage = this.findByUsernameAndStatusId(user.getUsername(), 4L, page, this.orderPageSize);
			} else if (userType.equals(1L)) {
				orderPage = this.findBySellerIdAndStatusIdOrderByOrderTimeDesc(user.getId(), 4L, page,
						this.orderPageSize);
			} else if (userType.equals(2L)) {
				orderPage = this.findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(diySite.getId(), 4L, page,
						this.orderPageSize);
			}
		} else if (orderType.equals(4L)) {
			if (userType.equals(0L)) {
				orderPage = this.findByUsernameAndStatusIdOrUsernameAndStatusIdOrderByIdDesc(user.getUsername(), 5L, 6L,
						page, this.orderPageSize);
			} else if (userType.equals(1L)) {
				orderPage = this.findBySellerIdAndStatusIdOrSellerIdAndStatusIdOrderByIdDesc(user.getId(), 5L, 6L, page,
						this.orderPageSize);
			} else if (userType.equals(2L)) {
				orderPage = this.findByDiySiteIdAndStatusIdOrDiySiteIdAndStatusIdOrderByIdDesc(diySite.getId(), 5L, 6L,
						page, this.orderPageSize);
			}
		}
		return orderPage;
	}

	public String getKey(Long orderType) {
		if (orderType.equals(0L)) {
			return "all_order_list";
		} else if (orderType.equals(1L)) {
			return "unpayed_order_list";
		} else if (orderType.equals(2L)) {
			return "undeliver_order_list";
		} else if (orderType.equals(3L)) {
			return "unsignin_order_list";
		} else if (orderType.equals(4L)) {
			return "uncomment_order_list";
		} else {
			return null;
		}
	}

	public Page<TdOrder> orderSearch(TdUser user, String keywords, Integer page) {
		Page<TdOrder> orderPage = null;
		if (null != user) {
			// 获取用户的身份
			Long userType = user.getUserType();
			if (null != userType && userType.longValue() == 0L) {
				orderPage = this
						.findByUsernameContainingAndUsernameOrOrderNumberContainingAndUsernameOrderByOrderTimeDesc(
								keywords, user.getUsername(), page, this.orderPageSize);
			} else if (null != userType && userType.longValue() == 1L) {
				orderPage = this
						.findByUsernameContainingAndSellerIdOrOrderNumberContainingAndSellerIdOrderByOrderTimeDesc(
								keywords, user.getId(), page, this.orderPageSize);
			} else if (null != userType && userType.longValue() == 2L) {
				orderPage = this
						.findByUsernameContainingAndDiySiteIdOrOrderNumberContainingAndDiySiteIdOrderByOrderTimeDesc(
								keywords, user.getUpperDiySiteId(), page, this.orderPageSize);
			}
		}
		return orderPage;
	}
	
	/**
	 * 查询用户+导购下产生的销量
	 * @param username
	 * @param sellerId
	 * @return
	 */
	public List<TdOrder> querySalesByusernameAndsellerId(String username,Long sellerId,Date date){
		return repository.querySalesByusernameAndsellerId(username, sellerId,date);
	};
	
	/**
	 * 查询用户+导购下 并且在修改导购日期之后产生的销量 产生的销量
	 * @param username
	 * @param sellerId
	 * @return
	 */
	public List<TdOrder> querySalesByusernameAndsellerIdAndOrderTime(String username,Long sellerId,Date changSellerTime){
		return repository.querySalesByusernameAndsellerIdAndOrderTime(username, sellerId,changSellerTime);
	};
}
