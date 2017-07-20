package com.ynyes.fitment.foundation.service;

import java.util.List;


import com.ynyes.fitment.foundation.entity.FitCompanyReport;

public interface FitCompanyReportService {
	
	List<FitCompanyReport> queryDownList(String begindata, String enddata,
			String city, Long companyId,String keywords,String type);

}
