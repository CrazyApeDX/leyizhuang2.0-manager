package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdWareHouse;
import com.ynyes.lyz.repository.TdWareHouseRepo;

@Service
@Transactional
public class TdWareHouseService {

	@Autowired
	private TdWareHouseRepo repository;
	

	public TdWareHouse save(TdWareHouse warehouse) {
		if (null == warehouse) {
			return null;
		}
	
		return repository.save(warehouse);
	}

	public void delete(Long id) {
		System.out.println("进入方法");
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdWareHouse findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdWareHouse> findAll() {
		return (List<TdWareHouse>) repository.findAll();
	}
	public Page<TdWareHouse> findAll(int page, int size) {

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}
	public List<TdWareHouse> findBywhNumberOrderBySortIdAsc(String whNumber){
		return repository.findBywhNumberOrderBySortIdAsc(whNumber);
	}
}
