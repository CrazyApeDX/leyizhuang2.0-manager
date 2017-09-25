package com.ynyes.fitment.foundation.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;

public interface FitCreditChangeLogService {

	FitCreditChangeLog save(FitCreditChangeLog log) throws Exception;

	List<FitCreditChangeLog> findByCompanyIdOrderByChangeTimeDesc(Long companyId) throws Exception;

	Page<FitCreditChangeLog> findByCompanyIdOrderByChangeTimeDesc(Long companyId, Integer page, Integer size) throws Exception;
	
	List<FitCreditChangeLog> queryDownList(String begindata, String enddata, String city, String companyCode,
			String keywords, String type);
	
	Page<FitCreditChangeLog> queryList(String begindata, String enddata, String city, String companyCode,
			String keywords, String type, Integer page, Integer size) throws Exception;

	List<FitCreditChangeLog> queryWalletDownList(String begindata, String enddata, String city, String companyCode,
			String keywords, String type);

	Page<FitCreditChangeLog> queryListWallet(String begindata, String enddata, String city, String companyCode,
			String keywords, String type, Integer page, Integer size) throws Exception;
}
