package com.ynyes.fitment.foundation.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.core.entity.persistent.table.TableEntity.OriginType;
import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.client.ClientEmployee;
import com.ynyes.fitment.foundation.repo.FitEmployeeRepo;
import com.ynyes.fitment.foundation.service.FitEmployeeService;
import com.ynyes.lyz.util.MD5;

@Service
@Transactional
public class FitEmployeeServiceImpl extends PageableService implements FitEmployeeService {

	@Autowired
	private FitEmployeeRepo fitEmployeeRepo;

	@Override
	public FitEmployee addMainEmployee(Long managerId, String password, String mobile, String name, Boolean frozen,
			Date frozenEndTime) throws Exception {
		FitEmployee employee = new FitEmployee();
		employee.setCreateId(managerId);
		employee.setCreateOrigin(OriginType.ADD);
		employee.setPassword(MD5.md5(password, 32));
		employee.setMobile(mobile);
		employee.setName(name);
		employee.setFrozen(frozen);
		employee.setFrozenEndTime(frozenEndTime);
		employee = fitEmployeeRepo.save(employee);
		return employee;
	}

	@Override
	public FitEmployee addSubEmployee(Long mainId, String mobile, String password, String name) throws Exception {
		FitEmployee employee = new FitEmployee();
		employee.setCreateId(mainId);
		employee.setPassword(password);
		employee.setMobile(mobile);
		employee.setName(name);
		employee.setMainId(mainId);
		employee.setIsMain(false);
		employee = fitEmployeeRepo.save(employee);
		return employee;
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
