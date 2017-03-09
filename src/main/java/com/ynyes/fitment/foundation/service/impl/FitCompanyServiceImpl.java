package com.ynyes.fitment.foundation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.repo.FitCompanyRepo;
import com.ynyes.fitment.foundation.service.FitCompanyService;

@Service
@Transactional
public class FitCompanyServiceImpl extends PageableService implements FitCompanyService {

	@Autowired
	private FitCompanyRepo fitCompanyRepo;

	@Override
	public FitCompany save(FitCompany fitCompany) throws Exception {
		if (null == fitCompany) {
			return null;
		}
		return this.fitCompanyRepo.save(fitCompany);
	}

	@Override
	public void delete(Long id) throws Exception {
		if (null != id) {
			this.fitCompanyRepo.delete(id);
		}
	}

	@Override
	public FitCompany findOne(Long companyId) {
		return this.fitCompanyRepo.findOne(companyId);
	}

	@Override
	public List<FitCompany> findAll() throws Exception {
		return this.fitCompanyRepo.findAll();
	}

	@Override
	public Page<FitCompany> findAll(Integer page, Integer size) throws Exception {
		return this.fitCompanyRepo.findAll(this.initPage(page, size));
	}

	@Override
	public Boolean validateRepeatCompanyByName(String name, Long id) throws Exception {
		Long count = this.fitCompanyRepo.countByNameAndIdNot(name, id);
		return (count > 0);
	}

	@Override
	public Boolean validateRepeatCompanyByCode(String code, Long id) throws Exception {
		Long count = this.fitCompanyRepo.countByCodeAndIdNot(code, id);
		return (count > 0);
	}

	@Override
	public FitCompany findByCode(String code) {
		if (null == code) {
			return null;
		} else {
			return this.fitCompanyRepo.findByCode(code);
		}
	}
}
