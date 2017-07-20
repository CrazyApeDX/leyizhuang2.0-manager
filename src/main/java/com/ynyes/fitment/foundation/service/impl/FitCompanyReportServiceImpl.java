package com.ynyes.fitment.foundation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitCompanyReport;
import com.ynyes.fitment.foundation.repo.FitCompanyReportRepo;
import com.ynyes.fitment.foundation.service.FitCompanyReportService;

@Service
@Transactional
public class FitCompanyReportServiceImpl implements FitCompanyReportService {

	@Autowired
	private FitCompanyReportRepo fitCompanyReportRepo;
	
	@Override
	public List<FitCompanyReport> queryDownList(String begindata, String enddata, String city, String companyCode,
			String keywords, String type) {
		return this.fitCompanyReportRepo.queryDownList(begindata, enddata, city, companyCode, keywords, type);
	}

	@Override
	public Page<FitCompanyReport> queryList(String begindata, String enddata, String city, String companyCode,
			String keywords, String type, Integer page, Integer size) {
//		Page<FitCompanyReport> pageList = new
		
		return null;
	}

}
