package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdDeliveryType;
import com.ynyes.lyz.repository.TdDeliveryTypeRepo;

/**
 * TdDeliveryType 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdDeliveryTypeService {
	@Autowired
	TdDeliveryTypeRepo repository;

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
	public void delete(TdDeliveryType e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdDeliveryType> entities) {
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
	public TdDeliveryType findOne(Long id) {
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
	public List<TdDeliveryType> findAll(Iterable<Long> ids) {
		return (List<TdDeliveryType>) repository.findAll(ids);
	}
	
	public List<TdDeliveryType> findAllOrderBySortIdAsc(){
		return (List<TdDeliveryType>) repository.findAll(new Sort("sortId"));
	}

	public List<TdDeliveryType> findByIsEnableTrue() {
		return repository.findByIsEnableTrue();
	}

	public Page<TdDeliveryType> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}

	public Page<TdDeliveryType> searchAllOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByTitleContainingOrderBySortIdAsc(keywords, pageRequest);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdDeliveryType save(TdDeliveryType e) {

		return repository.save(e);
	}

	public List<TdDeliveryType> save(List<TdDeliveryType> entities) {

		return (List<TdDeliveryType>) repository.save(entities);
	}

	public List<TdDeliveryType> findAll() {
		return (List<TdDeliveryType>) repository.findAll(new Sort("sortId"));
	}
}
