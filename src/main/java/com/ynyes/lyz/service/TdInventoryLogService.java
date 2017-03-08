package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdInventoryLog;
import com.ynyes.lyz.repository.TdInventoryLogRepo;

/**
 * TdManagerLog 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdInventoryLogService {

	@Autowired
	TdInventoryLogRepo repository;

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
	public void delete(TdInventoryLog e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdInventoryLog> entities) {
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
	public TdInventoryLog findOne(Long id) {
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
	public List<TdInventoryLog> findAll(Iterable<Long> ids) {
		return (List<TdInventoryLog>) repository.findAll(ids);
	}

	public List<TdInventoryLog> findAll() {
		return (List<TdInventoryLog>) repository.findAll();
	}

	public Page<TdInventoryLog> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "updateTime"));

		return repository.findAll(pageRequest);
	}

	public List<TdInventoryLog> findAllOrderBySortIdAsc() {
		return (List<TdInventoryLog>) repository.findAll(new Sort(Direction.ASC, "sortId"));
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdInventoryLog save(TdInventoryLog e) {
		return repository.save(e);
	}

	public List<TdInventoryLog> save(List<TdInventoryLog> entities) {

		return (List<TdInventoryLog>) repository.save(entities);
	}

	public void addLog(String type, String detail, HttpServletRequest req) {
		TdInventoryLog log = new TdInventoryLog();

		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}

		String username = (String) req.getSession().getAttribute("manager");

		log.setUsername(username);

		repository.save(log);
	}

	public void addManagerLog(HttpServletRequest req, Long goodsId) {
		TdInventoryLog ManagerLog = new TdInventoryLog();
		ManagerLog.setUpdateTime(new Date());
		ManagerLog.setIsManager(true);
		String manager = (String) req.getSession().getAttribute("manager");
		ManagerLog.setUsername(manager);

		repository.save(ManagerLog);
	}
}
