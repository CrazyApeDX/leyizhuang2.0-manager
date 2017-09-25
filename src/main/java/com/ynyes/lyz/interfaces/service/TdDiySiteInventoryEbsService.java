package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdDiySiteInventoryEbs;
import com.ynyes.lyz.interfaces.repository.TdDiySiteInventoryEbsRepo;

@Service
@Transactional
public class TdDiySiteInventoryEbsService {
	@Autowired
	private TdDiySiteInventoryEbsRepo repository;

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
	public void delete(TdDiySiteInventoryEbs e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdDiySiteInventoryEbs> entities) {
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
	public TdDiySiteInventoryEbs findOne(Long id) {
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
	public List<TdDiySiteInventoryEbs> findAll(Iterable<Long> ids) {
		return (List<TdDiySiteInventoryEbs>) repository.findAll(ids);
	}

	public List<TdDiySiteInventoryEbs> findAll() {
		return (List<TdDiySiteInventoryEbs>) repository.findAll();
	}
	// public TdReturnOrderInf findByOrderNumber(String orderNumber)
	// {
	// return repository.findByOrderNumber(orderNumber);
	// }

	public TdDiySiteInventoryEbs findByTransId(String transId) {
		if (transId == null) {
			return null;
		}
		return repository.findByTransId(Long.parseLong(transId));
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdDiySiteInventoryEbs save(TdDiySiteInventoryEbs e) {
		if (null == e.getInitDate()) {
			e.setInitDate(new Date());
		}
		// if (null == e.getSendFlag())
		// {
		// e.setSendFlag("N");
		// }
		e.setModifyDate(new Date());

		return repository.save(e);
	}

	public List<TdDiySiteInventoryEbs> save(List<TdDiySiteInventoryEbs> entities) {
		return (List<TdDiySiteInventoryEbs>) repository.save(entities);
	}
}
