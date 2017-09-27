package com.ynyes.lyz.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.user.TdUserChangeSellerLog;
import com.ynyes.lyz.repository.TdUserChangeSellerLogRepo;

@Service
@Transactional
public class TdUserChangeSellerLogService {
		
	@Autowired
	private TdUserChangeSellerLogRepo repository;
	
	public TdUserChangeSellerLog save(TdUserChangeSellerLog log){
		if(log == null){
			return null;
		}
		
		return repository.save(log);
	}
}
