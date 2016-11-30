package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdDiySiteList;
import com.ynyes.lyz.repository.TdDiySiteListRepo;

/**
 * TdDiySite 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdDiySiteListService {
	@Autowired
	TdDiySiteListRepo repository;

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
	public void delete(TdDiySiteList e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdDiySiteList> entities) {
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
	public TdDiySiteList findOne(Long id) {
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
	public List<TdDiySiteList> findAll(Iterable<Long> ids) {
		return (List<TdDiySiteList>) repository.findAll(ids);
	}
	
	
	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdDiySiteList save(TdDiySiteList e) {

		return repository.save(e);
	}

	public List<TdDiySiteList> save(List<TdDiySiteList> entities) {

		return (List<TdDiySiteList>) repository.save(entities);
	}
}
