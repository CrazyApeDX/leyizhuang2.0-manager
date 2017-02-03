package com.ynyes.fitment.foundation.service.biz;

import javax.servlet.http.HttpServletRequest;

import com.ynyes.fitment.core.entity.client.result.ClientResult;

public interface BizSessionService {

	ClientResult login(HttpServletRequest request, String mobile, String password) throws Exception;
}
