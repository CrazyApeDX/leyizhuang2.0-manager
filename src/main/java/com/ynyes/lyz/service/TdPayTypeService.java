package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdPayType;
import com.ynyes.lyz.repository.TdPayTypeRepo;

/**
 * TdPayType 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdPayTypeService {
	@Autowired
	TdPayTypeRepo repository;

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
	public void delete(TdPayType e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdPayType> entities) {
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
	public TdPayType findOne(Long id) {
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
	public List<TdPayType> findAll(Iterable<Long> ids) {
		return (List<TdPayType>) repository.findAll(ids);
	}

	public List<TdPayType> findAll() {
		return (List<TdPayType>) repository.findAll();
	}

	public Page<TdPayType> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}

	public Page<TdPayType> searchAllOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByTitleContainingOrderBySortIdAsc(keywords, pageRequest);
	}

	public List<TdPayType> findByIsEnableTrue() {
		return repository.findByIsEnableTrue();
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdPayType save(TdPayType e) {

		return repository.save(e);
	}

	public List<TdPayType> save(List<TdPayType> entities) {

		return (List<TdPayType>) repository.save(entities);
	}

	public List<TdPayType> findAllOrderBySortIdAsc() {
		return (List<TdPayType>) repository.findAll(new Sort("sortId"));
	}

	/**
	 * 通过名称查找支付方式的方法
	 * 
	 * @author dengxiao
	 */
	public TdPayType findByTitleAndIsEnableTrue(String title) {
		if (null == title) {
			return null;
		}
		return repository.findByTitleAndIsEnableTrue(title);
	}

	/**
	 * 查找所有在线支付的支付方式，按照排序号（sortId）正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdPayType> findByIsOnlinePayTrueAndIsEnableTrueOrderBySortIdAsc() {
		return repository.findByIsOnlinePayTrueAndIsEnableTrueOrderBySortIdAsc();
	}

	/**
	 * 查找首个线上支付的支付方式
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月29日上午9:26:37
	 */
	public TdPayType findTopByIsOnlinePayTrueAndIsEnableTrueOrderBySortIdAsc() {
		List<TdPayType> pay_list = repository.findByIsOnlinePayTrueAndIsEnableTrueOrderBySortIdAsc();
		if (null != pay_list && pay_list.size() > 0) {
			return pay_list.get(0);
		}
		return null;
	}
}
