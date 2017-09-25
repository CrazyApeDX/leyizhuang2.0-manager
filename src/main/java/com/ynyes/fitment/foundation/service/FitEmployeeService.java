package com.ynyes.fitment.foundation.service;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.foundation.entity.FitEmployee;

public interface FitEmployeeService {
	
	FitEmployee managerSaveEmployee(FitEmployee employee) throws Exception;
	
	void delete(Long id) throws Exception;
	
	FitEmployee findOne(Long id) throws Exception;
	
	FitEmployee update(FitEmployee employee) throws Exception;
	
	Page<FitEmployee> findAll(Integer page, Integer size) throws Exception;
	
	Page<FitEmployee> findAllAddConditionDeliveryType(Integer page, Integer size, String keyWords,String cityTitle,String companyTitle,String isMain) throws Exception;
	
	Boolean validateRepeatEmployeeByMobile(String mobile, Long id) throws Exception;

	FitEmployee login(String mobile, String password) throws Exception;
}
