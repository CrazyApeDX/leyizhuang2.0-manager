package com.ynyes.fitment.foundation.service.biz.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.constant.LoginSign;
import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.client.ClientEmployee;
import com.ynyes.fitment.foundation.service.FitEmployeeService;

@Service
@Transactional
public class BizSessionService implements com.ynyes.fitment.foundation.service.biz.BizSessionService {

	@Autowired
	private FitEmployeeService fitEmployeeService;

	@Override
	public ClientResult login(HttpServletRequest request, String mobile, String password) throws Exception {
		FitEmployee employee = fitEmployeeService.login(mobile, password);
		if (null == employee) {
			return new ClientResult(ActionCode.FAILURE, "手机号码和密码不匹配");
		} else {
			if (employee.getFrozen() && employee.getFrozenEndTime().getTime() > new Date().getTime()) {
				return new ClientResult(ActionCode.FAILURE, "您的账号已经被冻结，请联系管理员");
			} else {
				employee.setLastLoginTime(new Date());
				this.fitEmployeeService.update(employee);
				request.getSession().setAttribute(LoginSign.EMPLOYEE_SIGN.toString(), employee);
				return new ClientResult(ActionCode.SUCCESS, new ClientEmployee().init(employee));
			}
		}
	}

}
