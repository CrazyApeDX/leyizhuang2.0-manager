package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdOrderGoodsInf;
import com.ynyes.lyz.interfaces.repository.TdOrderGoodsInfRepo;

/**
 * TdAd 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOrderGoodsInfService {

	@Autowired
	private TdOrderGoodsInfRepo repository;

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
	public void delete(TdOrderGoodsInf e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdOrderGoodsInf> entities) {
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
	public TdOrderGoodsInf findOne(Long id) {
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
	public List<TdOrderGoodsInf> findAll(Iterable<Long> ids) {
		return (List<TdOrderGoodsInf>) repository.findAll(ids);
	}

	public List<TdOrderGoodsInf> findAll() {
		return (List<TdOrderGoodsInf>) repository.findAll();
	}

	public List<TdOrderGoodsInf> findByOrderHeaderId(Long headerId) {
		return repository.findByOrderHeaderId(headerId);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdOrderGoodsInf save(TdOrderGoodsInf e) {
		if (null == e.getInitDate()) {
			e.setInitDate(new Date());
		}
		e.setModifyDate(new Date());

		return repository.save(e);
	}

	public List<TdOrderGoodsInf> save(List<TdOrderGoodsInf> entities) {
		return (List<TdOrderGoodsInf>) repository.save(entities);
	}

	public List<TdOrderGoodsInf> findBySendFlag(Integer flag) {
		if (null == flag) {
			return null;
		}
		return repository.findBySendFlag(flag);
	}

}
