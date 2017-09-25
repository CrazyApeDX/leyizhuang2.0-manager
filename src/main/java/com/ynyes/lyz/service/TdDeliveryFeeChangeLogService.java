package com.ynyes.lyz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdDeliveryFeeChangeLog;
import com.ynyes.lyz.repository.TdDeliveryFeeChangeLogRepo;

@Service
@Transactional
public class TdDeliveryFeeChangeLogService {

	@Autowired
	private TdDeliveryFeeChangeLogRepo repository;

	public TdDeliveryFeeChangeLog save(TdDeliveryFeeChangeLog e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}

	public Page<TdDeliveryFeeChangeLog> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "operationTime"));
		return repository.findAll(pageRequest);
	}

	public Page<TdDeliveryFeeChangeLog> findByKeywords(String keywords, int page, int size) {
		if (null == keywords) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByKeywords(keywords, pageRequest);
	}
}
