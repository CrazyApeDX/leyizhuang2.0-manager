package com.ynyes.lyz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdActiveRedPacket;
import com.ynyes.lyz.entity.TdActivity;
import com.ynyes.lyz.repository.TdActiveRedPacketRepo;

/**
 * 红包活动服务类
 * @author panjie
 *
 */
@Service
@Transactional
public class TdActiveRedPacketService {
		
	@Autowired	
	private TdActiveRedPacketRepo repository;
	
	@Autowired
	private TdCityService tdCityService;
	
	public TdActiveRedPacket save(TdActiveRedPacket e){
		if(e == null){
			return null;
		}
		// 城市名
		e.setCityName(tdCityService.findOne(e.getCityId()).getCityName());
		return repository.save(e);
	}
	
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}
	
	public Page<TdActiveRedPacket> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
		return repository.findAll(pageRequest);
	}
	
	public TdActiveRedPacket findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}
}
