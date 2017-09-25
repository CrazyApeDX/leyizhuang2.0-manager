package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdChangeBalanceLog;
import com.ynyes.lyz.repository.TdChangeBalanceLogRepo;

@Service
@Transactional
public class TdChangeBalanceLogService {

	@Autowired
	private TdChangeBalanceLogRepo repository;
	
	public TdChangeBalanceLog save(TdChangeBalanceLog e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}
	
	public void delete(Long id) {
		if (null == id) {
			repository.delete(id);
		}
	}
	
	public TdChangeBalanceLog findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}
	
	public List<TdChangeBalanceLog> findAll() {
		return (List<TdChangeBalanceLog>) repository.findAll(new Sort(Direction.DESC, "operateTime"));
	}
	
	public Page<TdChangeBalanceLog> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, Direction.DESC, "operateTime");
		return repository.findAll(pageRequest);
	}
}
