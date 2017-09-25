package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdMessageType;
import com.ynyes.lyz.repository.TdMessageTypeRepo;

@Service
@Transactional
public class TdMessageTypeService {

	@Autowired
	private TdMessageTypeRepo repository;

	public TdMessageType save(TdMessageType e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	};
	
	public void delete(Long id){
		if(null != id){
			repository.delete(id);
		}
	}
	
	public TdMessageType findOne(Long id){
		if(null == id){
			return null;
		}
		return repository.findOne(id);
	}
	
	public List<TdMessageType> findAll(){
		Sort sort = new Sort(Direction.ASC,"sortId"); //zhangji 2016-1-3 15:14:30
		return (List<TdMessageType>) repository.findAll(sort);
	}
	
	/**
	 * 查找所有能够使用的消息类型
	 * @author dengxiao
	 */
	public List<TdMessageType> findByIsEnableTrueOrderBySortIdAsc(){
		return repository.findByIsEnableTrueOrderBySortIdAsc();
	}
}
