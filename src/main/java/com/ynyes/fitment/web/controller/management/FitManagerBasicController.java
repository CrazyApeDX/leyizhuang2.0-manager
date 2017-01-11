package com.ynyes.fitment.web.controller.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ynyes.fitment.core.constant.LoginSign;
import com.ynyes.fitment.core.entity.session.SessionContent;

public class FitManagerBasicController {

	protected String getLoginManager(HttpServletRequest request) {
		HttpSession session = request.getSession();
		SessionContent<String> sessionContent = (SessionContent<String>) session.getAttribute(LoginSign.MANAGER_SIGN.toString());
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
