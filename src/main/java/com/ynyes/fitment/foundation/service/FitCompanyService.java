package com.ynyes.fitment.foundation.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.foundation.entity.FitCompany;

public interface FitCompanyService {
	
	
	FitCompany save(FitCompany fitCompany) throws Exception;
	
	void delete(Long id) throws Exception;
	
	FitCompany findOne(Long companyId) throws Exception;
	
	Page<FitCompany> findAll(Integer page, Integer size) throws Exception;
	
	List<FitCompany> findAll() throws Exception;
	
	Boolean validateRepeatCompanyByName(String name, Long id) throws Exception;
	
	Boolean validateRepeatCompanyByCode(String code, Long id) throws Exception;
}
