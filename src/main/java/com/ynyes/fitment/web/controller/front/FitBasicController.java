package com.ynyes.fitment.web.controller.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ynyes.fitment.core.constant.LoginSign;
import com.ynyes.fitment.core.entity.session.SessionContent;

public abstract class FitBasicController {
	
	protected String validateEmployeeLoginStatus(HttpServletRequest request) {
		HttpSession session = request.getSession();
		SessionContent<String> sessionContent = (SessionContent<String>) session.getAttribute(LoginSign.EMPLOYEE_SIGN.toString());
		if (null == sessionContent){
			return null;
		} else {
			String sessionId = session.getId();
			if (!sessionId.equals(sessionContent.getSessionId())) {
				return null;
			} else {
				return sessionContent.getContent();
			}
		}
	}

}
