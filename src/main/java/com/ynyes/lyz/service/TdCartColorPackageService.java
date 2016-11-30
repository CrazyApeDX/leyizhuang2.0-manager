package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdCartColorPackage;
import com.ynyes.lyz.repository.TdCartColorPackageRepo;

@Service
@Transactional
public class TdCartColorPackageService {

	@Autowired
	private TdCartColorPackageRepo repository;
	
	public TdCartColorPackage save(TdCartColorPackage e){
		if(null == e){
			return null;
		}
		return repository.save(e);
	}
	
	public void delete(Long id){
		if(null != id ){
			repository.delete(id);
		}
	}
	
	public List<TdCartColorPackage> findByUsername(String username)
	{
		if(null == username)
		{
			return null;
		}
		return repository.findByUsername(username);
	}
	
	public TdCartColorPackage findOne(Long id){
		if(null == id){
			return null;
		}
		return repository.findOne(id);
	}
	
	public List<TdCartColorPackage> findAll(){
		return (List<TdCartColorPackage>) repository.findAll();
	}
}
