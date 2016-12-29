package com.ynyes.fitment.foundation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.repo.FitCompanyRepo;
import com.ynyes.fitment.foundation.service.FitCompanyService;

@Service
@Transactional
public class FitCompanyServiceImpl implements FitCompanyService {

	@Autowired
	private FitCompanyRepo fitCompanyRepo;
	
	@Override
	public FitCompany findOne(Long companyId) throws Exception {
		return fitCompanyRepo.findOne(companyId);
	}

}
