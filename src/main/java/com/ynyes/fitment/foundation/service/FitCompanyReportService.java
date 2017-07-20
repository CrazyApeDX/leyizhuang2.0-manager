package com.ynyes.fitment.foundation.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.foundation.entity.FitCompanyReport;

public interface FitCompanyReportService {
	
	List<FitCompanyReport> queryDownList(String begindata, String enddata,
			String city, String companyCode,String keywords,String type);
	
	Page<FitCompanyReport> queryList(String begindata, String enddata,
			String city, String companyCode,String keywords,String type, Integer page,
			Integer size);

}
