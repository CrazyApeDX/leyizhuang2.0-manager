package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdActiveRedPacket;
import com.ynyes.lyz.entity.TdPhotoOrderInfo;
import com.ynyes.lyz.repository.TdPhotoOrderInfoRepo;





/**
 * 拍照下单 服务类
 * 
 * @author panjie
 *
 */

@Service
@Transactional
public class TdPhotoOrderInfoService {

	@Autowired
	private TdPhotoOrderInfoRepo repository;

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
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdPhotoOrderInfo save(TdPhotoOrderInfo e) {

		return repository.save(e);
	}

	// 查询所有的订单
	public Page<TdPhotoOrderInfo> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findAll(pageRequest);
	}
	
	// 查询managerId关联的订单
	public Page<TdPhotoOrderInfo> findByManagerId(int page, int size,Long managerId) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByManagerIdEquals(managerId,pageRequest);
	}
}

