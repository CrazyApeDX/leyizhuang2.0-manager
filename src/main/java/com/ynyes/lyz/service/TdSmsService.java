package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdSms;
import com.ynyes.lyz.repository.TdSmsRepo;

@Service
@Transactional
public class TdSmsService {

	@Autowired
	private TdSmsRepo repository;
	
	public TdSms save(TdSms entity){
		if(null == entity){
			return null;
		}
		return repository.save(entity);
	}
	
	public void delete(Long id){
		if(null != id){
			repository.delete(id);
		}
	}
	
	public TdSms findOne(Long id){
		if(null == id){
			return null;
		}
		return repository.findOne(id);
	}
	
	public List<TdSms> findAll(){
		return (List<TdSms>) repository.findAll();
	}
}
