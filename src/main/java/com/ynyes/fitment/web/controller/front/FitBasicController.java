package com.ynyes.fitment.web.controller.front;

import javax.servlet.http.HttpServletRequest;

import com.ynyes.fitment.core.constant.LoginSign;
import com.ynyes.fitment.foundation.entity.FitEmployee;

public abstract class FitBasicController {
	
	protected FitEmployee getLoginEmployee(HttpServletRequest request) {
		return (FitEmployee) request.getSession().getAttribute(LoginSign.EMPLOYEE_SIGN.toString());
	}

}
