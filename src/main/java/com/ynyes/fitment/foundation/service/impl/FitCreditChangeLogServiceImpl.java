package com.ynyes.fitment.foundation.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;
import com.ynyes.fitment.foundation.repo.FitCreditChangeLogRepo;
import com.ynyes.fitment.foundation.service.FitCreditChangeLogService;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;

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

	@Override
	public List<FitCreditChangeLog> queryDownList(String begindata, String enddata, String city, String companyCode,
			String keywords, String type) {
		return this.fitCreditChangeLogRepo.queryDownList(begindata, enddata, city, companyCode, keywords, type);
	}

	@Override
	public Page<FitCreditChangeLog> queryList(String begindata, String enddata, String city, String companyCode,
			String keywords, String type, Integer page, Integer size) throws Exception {
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<FitCreditChangeLog> c = new Criteria<FitCreditChangeLog>();
		if (null != keywords && !keywords.equalsIgnoreCase("")) {
			c.add(Restrictions.or(Restrictions.like("companyCode",keywords, true),Restrictions.like("companyTitle", keywords, true),Restrictions.like("referenceNumber", keywords, true)));
		}
		if (null != companyCode) {
			c.add(Restrictions.eq("companyCode", companyCode, true));
		}
		
		if (null != type) {
			c.add(Restrictions.eq("type", type, true));
		}
		
		if (null != city) {
			c.add(Restrictions.eq("city", city, true));
		}
		
		if (StringUtils.isNotBlank(begindata)) {
			c.add(Restrictions.gte("changeTime", com.ynyes.lyz.util.StringUtils.stringToDate(begindata, null), true));

		}
		if (StringUtils.isNotBlank(enddata)) {
			c.add(Restrictions.lte("changeTime", com.ynyes.lyz.util.StringUtils.stringToDate(enddata, null), true));
		}
		c.setOrderByDesc("changeTime");
		return fitCreditChangeLogRepo.findAll(c,pageRequest);
	}

}
