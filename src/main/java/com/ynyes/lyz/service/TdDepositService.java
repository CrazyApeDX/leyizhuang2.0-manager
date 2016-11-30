package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdDeposit;
import com.ynyes.lyz.repository.TdDepositRepo;

@Service
@Transactional
public class TdDepositService {

	@Autowired
	private TdDepositRepo repository;
	
	public TdDeposit save(TdDeposit t) {
		if (null == t) {
			return null;
		}
		return repository.save(t);
	}
	
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}
	
	public TdDeposit findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}
	
	public List<TdDeposit> findAll() {
		return (List<TdDeposit>) repository.findAll();
	}
}
