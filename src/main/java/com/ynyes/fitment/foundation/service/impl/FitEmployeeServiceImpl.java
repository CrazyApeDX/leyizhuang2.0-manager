package com.ynyes.fitment.foundation.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.core.entity.persistent.table.TableEntity.OriginType;
import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.client.ClientEmployee;
import com.ynyes.fitment.foundation.repo.FitEmployeeRepo;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitEmployeeService;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.service.TdCityService;

@Service
@Transactional
public class FitEmployeeServiceImpl extends PageableService implements FitEmployeeService {

	@Autowired
	private FitEmployeeRepo fitEmployeeRepo;
	
	@Autowired
	private TdCityService tdCityService;
	
	@Autowired
	private FitCompanyService fitCompanyService;

	@Override
	public FitEmployee managerSaveEmployee(FitEmployee employee) throws Exception {
		if (null == employee) {
			return null;
		} else {
			if (null != employee.getId()) {
				employee.setAccount(null);
			} else {
				employee.setCreateOrigin(OriginType.ADD);
			}
			FitCompany company = this.fitCompanyService.findOne(employee.getCompanyId());
			employee.setCompanyTitle(company.getName());
			TdCity city = this.tdCityService.findBySobIdCity(company.getSobId());
			employee.setCityTitle(city.getCityName());
			return this.fitEmployeeRepo.save(employee);
		}
	}
	
	@Override
	public void delete(Long id) throws Exception {
		if (null != id) {
			this.fitEmployeeRepo.delete(id);
		}
	}

	@Override
	public FitEmployee findOne(Long id) throws Exception {
		return this.fitEmployeeRepo.findOne(id);
	}

	@Override
	public Page<FitEmployee> findAll(Integer page, Integer size) throws Exception {
		return this.fitEmployeeRepo.findAll(this.initPage(page, size));
	}
	
	@Override
	public Boolean validateRepeatEmployeeByMobile(String mobile, Long id) throws Exception {
		Long count = this.fitEmployeeRepo.countByMobileAndIdNot(mobile, id);
		return (count > 0);
	}

	@Override
	public ClientResult login(String mobile, String password) throws Exception {
		FitEmployee employee = this.fitEmployeeRepo.findByMobileAndPassword(mobile, password);
		if (null == employee) {
			return new ClientResult(ActionCode.FAILURE, "手机号码和密码不匹配");
		} else {
			if (employee.getFrozen() && employee.getFrozenEndTime().getTime() > new Date().getTime()) {
				return new ClientResult(ActionCode.FAILURE, "您的账号已经被冻结，请联系管理员");
			} else {
				employee.setLastLoginTime(new Date());
				this.fitEmployeeRepo.save(employee);
				return new ClientResult(ActionCode.SUCCESS, new ClientEmployee().init(employee));
			}
		}
	}
}
