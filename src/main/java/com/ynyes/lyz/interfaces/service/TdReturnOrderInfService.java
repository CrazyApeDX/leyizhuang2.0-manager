package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdReturnOrderInf;
import com.ynyes.lyz.interfaces.repository.TdReturnOrderInfRepo;

/**
 * TdOrderInf 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdReturnOrderInfService {

	@Autowired
	private TdReturnOrderInfRepo repository;

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
	public void delete(TdReturnOrderInf e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdReturnOrderInf> entities) {
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
	public TdReturnOrderInf findOne(Long id) {
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
	public List<TdReturnOrderInf> findAll(Iterable<Long> ids) {
		return (List<TdReturnOrderInf>) repository.findAll(ids);
	}

	public List<TdReturnOrderInf> findAll() {
		return (List<TdReturnOrderInf>) repository.findAll();
	}

	public TdReturnOrderInf findByReturnNumber(String returnNumber) {
		List<TdReturnOrderInf> returnOrderInfs = repository.findByReturnNumber(returnNumber);
		if (returnOrderInfs == null || returnOrderInfs.size() < 1) {
			return null;
		}
		return returnOrderInfs.get(0);
	}

	public TdReturnOrderInf findByOrderNumber(String orderNumber) {
		List<TdReturnOrderInf> returnOrderInfs = repository.findByOrderNumber(orderNumber);
		if (returnOrderInfs == null || returnOrderInfs.size() < 1) {
			return null;
		}
		return returnOrderInfs.get(0);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdReturnOrderInf save(TdReturnOrderInf e) {
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

	public List<TdReturnOrderInf> save(List<TdReturnOrderInf> entities) {
		return (List<TdReturnOrderInf>) repository.save(entities);
	}

	public List<TdReturnOrderInf> findBySobIdAndSendFlag(Long sobId, Integer sendFlag) {
		if (null == sobId || null == sendFlag) {
			return null;
		}
		return repository.findBySobIdAndSendFlag(sobId, sendFlag);
	}

	public List<TdReturnOrderInf> findBySendFlagOrSendFlagIsNull(Integer sendFlag) {
		
		return repository.findBySendFlagOrSendFlagIsNull(sendFlag);
	}

}
