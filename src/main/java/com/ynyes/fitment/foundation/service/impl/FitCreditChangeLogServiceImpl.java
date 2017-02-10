package com.ynyes.fitment.foundation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;
import com.ynyes.fitment.foundation.repo.FitCreditChangeLogRepo;
import com.ynyes.fitment.foundation.service.FitCreditChangeLogService;

@Service
@Transactional
public class FitCreditChangeLogServiceImpl extends PageableService implements FitCreditChangeLogService {

	@Autowired
	private FitCreditChangeLogRepo fitCreditChangeLogRepo;
	
	@Override
	public FitCreditChangeLog save(FitCreditChangeLog log) throws Exception {
		if (null == log) {
			return null;
		}
		return this.fitCreditChangeLogRepo.save(log);
	}

	@Override
	public List<FitCreditChangeLog> findByCompanyIdOrderByChangeTimeDesc(Long companyId) throws Exception {
		if (null == companyId) {
			return  null;
		}
		return this.fitCreditChangeLogRepo.findByCompanyIdOrderByChangeTimeDesc(companyId);
	}

	@Override
	public Page<FitCreditChangeLog> findByCompanyIdOrderByChangeTimeDesc(Long companyId, Integer page, Integer size)
			throws Exception {
		if (null == companyId) {
			return  null;
		}
		return this.fitCreditChangeLogRepo.findByCompanyIdOrderByChangeTimeDesc(companyId, this.initPage(page, size));
	}

}
