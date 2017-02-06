package com.ynyes.fitment.foundation.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;

public interface FitCreditChangeLogService {

	FitCreditChangeLog save(FitCreditChangeLog log) throws Exception;

	List<FitCreditChangeLog> findByCompanyIdOrderByChangeTimeDesc(Long companyId) throws Exception;

	Page<FitCreditChangeLog> findByCompanyIdOrderByChangeTimeDesc(Long companyId, Integer page, Integer size) throws Exception;
}
