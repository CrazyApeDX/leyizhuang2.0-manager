package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.repository.TdReturnNoteRepo;
import com.ynyes.lyz.strategy.refund.ReturnNoteStrategyFactory;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;

@Service
@Transactional
public class TdReturnNoteService {

	@Autowired
	TdReturnNoteRepo repository;

	@Autowired
	private ReturnNoteStrategyFactory factory;

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
	public void delete(TdReturnNote e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdReturnNote> entities) {
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
	public TdReturnNote findOne(Long id) {
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
	public List<TdReturnNote> findAll(Iterable<Long> ids) {
		return (List<TdReturnNote>) repository.findAll(ids);
	}

	public List<TdReturnNote> findByOrderNumberContaining(String orderNumber) {
		if (orderNumber == null) {
			return null;
		}
		return repository.findByOrderNumberContaining(orderNumber);
	}

	// zhangji 修改用户名所用
	public List<TdReturnNote> findByUsername(String username) {
		return (List<TdReturnNote>) repository.findByUsername(username);
	}

	public List<TdReturnNote> findAll() {
		return (List<TdReturnNote>) repository.findAll();
	}

	// 以前的退货查询方法
	public List<TdReturnNote> findByStatusIdAndOrderTimeBetween(Long statusId, Date start, Date end) {

		if (null == statusId || null == start || null == end) {
			return null;
		}

		return repository.findByStatusIdAndOrderTimeBetweenOrderByIdDesc(statusId, start, end);
	}

	// 以前的退货查询方法
	public Integer countByStatusIdAndOrderTimeBetween(Long statusId, Date start, Date end) {

		if (null == statusId || null == start || null == end) {
			return null;
		}

		return repository.countByStatusIdAndOrderTimeBetweenOrderByIdDesc(statusId, start, end);
	}

	// 以前的退货查询方法
	public List<TdReturnNote> findByStatusIdAndOrderTimeAfter(Long statusId, Date time) {

		if (null == statusId || null == time) {
			return null;
		}

		return repository.findByStatusIdAndOrderTimeAfterOrderByIdDesc(statusId, time);
	}

	// 以前的退货查询方法
	public Integer countByStatusIdAndOrderTimeAfter(Long statusId, Date time) {

		if (null == statusId || null == time) {
			return null;
		}

		return repository.countByStatusIdAndOrderTimeAfterOrderByIdDesc(statusId, time);
	}

	public Page<TdReturnNote> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository.findAll(pageRequest);
	}

	public Page<TdReturnNote> searchAll(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository.findByDiySiteTitleOrOrderNumberOrReturnNumberOrUsername(keywords, keywords, keywords,
				keywords, pageRequest);
	}

	public Page<TdReturnNote> findByDiySiteTitleAndOrderNumberOrReturnNumberOrUsername(String title, String keyword,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository.findByDiySiteTitleOrOrderNumberOrReturnNumberOrUsername(title, keyword, keyword, keyword,
				pageRequest);
	}

	public Page<TdReturnNote> findByDiySiteId(Long siteId, int page, int size) {
		if (siteId == null) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository.findByDiySiteId(siteId, pageRequest);
	}

	public Page<TdReturnNote> findBySiteIdAndKeywords(Long siteId, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		if (StringUtils.isNotBlank(keywords)) {
			repository
					.findByDiySiteIdAndReturnNumberContainingOrDiySiteIdAndOrderNumberContainingOrDiySiteIdAndUsernameContaining(
							siteId, keywords, siteId, keywords, siteId, keywords, pageRequest);
		} else {
			return repository.findByDiySiteId(siteId, pageRequest);
		}
		return null;
	}

	public String findSiteTitleByUserName(String username) {
		return repository.findSiteTitleByUserName(username);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdReturnNote save(TdReturnNote e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}

	public void testSave(TdReturnNote note) {
		if (note == null) {
			return;
		}
		repository.save(note);
	}

	public List<TdReturnNote> save(List<TdReturnNote> entities) {

		return (List<TdReturnNote>) repository.save(entities);
	}

	public TdReturnNote findByReturnNumber(String returnNumber) {
		if (null == returnNumber) {
			return null;
		}
		return repository.findByReturnNumber(returnNumber);

	}

	/**
	 * 根据时间和门店查询退货单
	 * 
	 * @return
	 */
	public List<TdReturnNote> findByOrderTimeOrderByOrderTimeDesc(Date begin, Date end, String siteName,
			List<String> siteNameList) {
		Criteria<TdReturnNote> c = new Criteria<TdReturnNote>();
		if (null != begin) {
			c.add(Restrictions.gte("orderTime", begin, true));
		}
		if (null != end) {
			c.add(Restrictions.lte("orderTime", end, true));
		}
		if (null != siteName && !"".equals(siteName)) {
			c.add(Restrictions.eq("diySiteTitle", siteName, true));
		}
		if (null != siteNameList && siteNameList.size() > 0) {
			c.add(Restrictions.in("diySiteTitle", siteNameList, true));
		}
		c.setOrderByDesc("orderTime");
		return repository.findAll(c);
	}

	// 退货查询 增加配送员编号
	public List<TdReturnNote> findByDriverAndStatusIdAndOrderTimeBetween(String driver, Long statusId, Date start,
			Date end) {

		if (null == statusId || null == start || null == end) {
			return null;
		}
		return repository.findByDriverAndStatusIdAndOrderTimeBetweenOrderByIdDesc(driver, statusId, start, end);
	}

	// 退货查询 增加配送员编号
	public Integer countByDriverAndStatusIdAndOrderTimeBetween(String driver, Long statusId, Date start, Date end) {

		if (null == statusId || null == start || null == end) {
			return null;
		}

		return repository.countByDriverAndStatusIdAndOrderTimeBetweenOrderByIdDesc(driver, statusId, start, end);
	}

	// 退货查询 增加配送员编号
	public List<TdReturnNote> findByDriverAndStatusIdAndOrderTimeAfter(String driver, Long statusId, Date time) {

		if (null == statusId || null == time) {
			return null;
		}

		return repository.findByDriverAndStatusIdAndOrderTimeAfterOrderByIdDesc(driver, statusId, time);
	}

	// 退货查询 增加配送员编号
	public Integer countByDriverAndStatusIdAndOrderTimeAfter(String driver, Long statusId, Date time) {

		if (null == statusId || null == time) {
			return null;
		}

		return repository.countByDriverAndStatusIdAndOrderTimeAfterOrderByIdDesc(driver, statusId, time);
	}

	/**
	 * 退货单 搜索功能
	 * 
	 * @param driver
	 *            配送员编号
	 * @param keyword
	 *            关键字
	 * @return 结果集
	 * @author zp
	 */
	public List<TdReturnNote> findReturnSearch(String driver, String keyword) {
		return repository
				.findByDriverAndOrderNumberContainingOrDriverAndReturnNumberContainingOrDriverAndDiySiteTelContainingOrDriverAndDiySiteTitleContainingOrDriverAndDiySiteAddressContainingOrderByIdDesc(
						driver, keyword, driver, keyword, driver, keyword, driver, keyword, driver, keyword);
	}

	/**
	 * 根据用户名分页查询
	 * 
	 * @param username
	 *            用户名
	 * @param page
	 *            分页
	 * @return 结果列表
	 * @author zp
	 */
	public Page<TdReturnNote> findByUsername(String username, String remark, Integer page, Integer size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository.findByUsernameAndRemarkInfoNot(username, remark, pageRequest);
	}

	/**
	 * 根据退货单号或者订单号查询退货单
	 * 
	 * @author zp
	 */
	public List<TdReturnNote> findByReturnNumberContainingOrorderNumberContaining(String keywords, String remark,
			String username) {
		return repository
				.findByReturnNumberContainingAndUsernameAndRemarkInfoNotOrOrderNumberContainingAndUsernameAndRemarkInfoNotOrderByOrderTimeDesc(
						keywords, username, remark, keywords, username, remark);
	}

	/**
	 * 退货列表查询
	 * 
	 * @param keyword
	 *            关键字
	 * @param diyCode
	 *            门店
	 * @param roleDiyCodes
	 *            权限门店
	 * @param cityDiyCodes
	 *            权限城市
	 * @param size
	 *            每页数据
	 * @param page
	 *            当前页
	 * @param statusId
	 *            退货单状态
	 * @return 分页结果集
	 * @author zp
	 */
	public Page<TdReturnNote> searchReturnList(String keyword, Long diyCode, List<Long> roleDiyCodes,
			List<Long> cityDiyCodes, int size, int page, Long statusId) {
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdReturnNote> c = new Criteria<TdReturnNote>();

		if (StringUtils.isNotBlank(keyword)) {
			c.add(Restrictions.or(Restrictions.like("orderNumber", keyword, true),
					Restrictions.like("returnNumber", keyword, true), Restrictions.like("username", keyword, true)));
		}
		if (null != diyCode) {
			c.add(Restrictions.eq("diySiteId", diyCode, true));
		}
		if (null != roleDiyCodes && roleDiyCodes.size() > 0) {
			c.add(Restrictions.in("diySiteId", roleDiyCodes, true));
		}
		if (null != cityDiyCodes && cityDiyCodes.size() > 0) {
			c.add(Restrictions.in("diySiteId", cityDiyCodes, true));
		}
		if (null != statusId) {
			c.add(Restrictions.eq("statusId", statusId, true));
		}
		c.add(Restrictions.notLike("remarkInfo", "取消订单，退货", true));
		c.add(Restrictions.notLike("remarkInfo", "取消订单,退货", true));

		c.setOrderByDesc("id");
		return repository.findAll(c, pageRequest);
	}

	public List<TdReturnNote> findMissedReturnOrder(Date beginDate) {

		return repository.findMissedReturnOrder(beginDate);
	}

	public List<TdReturnNote> findRefusedReturnOrder(Date beginDate) {
		return repository.findRefusedReturnOrder(beginDate);
	}

	public List<TdReturnNote> findNormalReturnOrder(Date beginDate) {
		return repository.findNormalReturnOrder(beginDate);
	}

	public Boolean doCancel(Long id) throws Exception {
		TdReturnNote returnNote = this.findOne(id);
		return factory.build(returnNote).doAction(returnNote);
	}
}
