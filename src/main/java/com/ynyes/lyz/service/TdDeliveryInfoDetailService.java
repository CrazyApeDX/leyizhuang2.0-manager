package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdDeliveryInfoDetail;
import com.ynyes.lyz.repository.TdDeliveryInfoDetailRepo;

/**
 * TdAd 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdDeliveryInfoDetailService {

	@Autowired
	TdDeliveryInfoDetailRepo repository;

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
	public void delete(TdDeliveryInfoDetail e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdDeliveryInfoDetail> entities) {
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
	public TdDeliveryInfoDetail findOne(Long id) {
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
	public List<TdDeliveryInfoDetail> findAll(Iterable<Long> ids) {
		return (List<TdDeliveryInfoDetail>) repository.findAll(ids);
	}

	public List<TdDeliveryInfoDetail> findAll() {
		return (List<TdDeliveryInfoDetail>) repository.findAll();
	}

	public Page<TdDeliveryInfoDetail> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findAll(pageRequest);
	}

	public List<TdDeliveryInfoDetail> findByOpUser(String opUser) {
		if (opUser == null) {
			return null;
		}
		return repository.findByOpUser(opUser);
	}
	public List<TdDeliveryInfoDetail> findDistinctSubOrderNumberByTaskNoIn(List<String> taskNoList) {
		if (taskNoList == null) {
			return null;
		}
		return repository.findDistinctSubOrderNumberByTaskNoIn(taskNoList);
	}
	
	public List<TdDeliveryInfoDetail> findByTaskNo(String taskNo)
	{
		if (taskNo == null)
		{
			return null;
		}
		return repository.findByTaskNo(taskNo);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdDeliveryInfoDetail save(TdDeliveryInfoDetail e) {
		return repository.save(e);
	}

	public List<TdDeliveryInfoDetail> save(List<TdDeliveryInfoDetail> entities) {

		return (List<TdDeliveryInfoDetail>) repository.save(entities);
	}

	public List<TdDeliveryInfoDetail> findBySubOrderNumber(String subOrderNumber) {
		if (null == subOrderNumber) {
			return null;
		}
		return repository.findBySubOrderNumber(subOrderNumber);
	}

}
