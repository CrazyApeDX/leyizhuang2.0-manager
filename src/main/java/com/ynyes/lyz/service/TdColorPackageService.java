package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdColorPackage;
import com.ynyes.lyz.repository.TdColorPackageRepo;

@Service
@Transactional
public class TdColorPackageService {

	@Autowired
	private TdColorPackageRepo repository;

	public TdColorPackage save(TdColorPackage e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}
	
	public void delete(Long id){
		if(null != id){
			repository.delete(id);
		}
	}
	
	public TdColorPackage findOne(Long id){
		if(null == id){
			return null;
		}
		return repository.findOne(id);
	}
	
	public List<TdColorPackage> findAll(){
		return (List<TdColorPackage>) repository.findAll();
	}
	
	/**
	 * 根据调色包名查找调色包
	 * @author dengxiao
	 */
	public TdColorPackage findByName(String name){
		if(null == name){
			return null;
		}
		return repository.findByName(name);
	}
	
	/**
	 * 根据调色包编号查找调色包
	 * @author dengxiao
	 */
	public TdColorPackage findByNumber(String number){
		if(null == number){
			return null;
		}
		return repository.findByNumber(number);
	}
}

