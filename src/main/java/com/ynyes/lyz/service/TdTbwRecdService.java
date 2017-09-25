package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdTbwRecd;
import com.ynyes.lyz.repository.TdTbwRecdRepo;

/**
 * TdAd 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdTbwRecdService {

	@Autowired
	TdTbwRecdRepo repository;

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
	public void delete(TdTbwRecd e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdTbwRecd> entities) {
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
	public TdTbwRecd findOne(Long id) {
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
	public List<TdTbwRecd> findAll(Iterable<Long> ids) {
		return (List<TdTbwRecd>) repository.findAll(ids);
	}

	public List<TdTbwRecd> findAll() {
		return (List<TdTbwRecd>) repository.findAll();
	}

	public List<TdTbwRecd> findByRecNo(String recNo) {
		if (recNo == null) {
			return null;
		}
		return repository.findByRecNo(recNo);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdTbwRecd save(TdTbwRecd e) {

		return repository.save(e);
	}

	public List<TdTbwRecd> save(List<TdTbwRecd> entities) {
		return (List<TdTbwRecd>) repository.save(entities);
	}

	public Long countByGcodeAndRecNo(String gcode, String recNo) {
		if (null == gcode || null == recNo) {
			return 0l;
		}
		return repository.countByGcodeAndRecNo(gcode, recNo);
	}
}
