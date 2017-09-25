package com.ynyes.fitment.web.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.core.exception.ApplicationException;
import com.ynyes.fitment.foundation.service.biz.BizSessionService;
import com.ynyes.lyz.util.MD5;

@RestController
@RequestMapping(value = "/fit/session", produces = "application/json;charset=utf-8")
public class FitSessionController {

	private final Logger LOGGER = LoggerFactory.getLogger(FitSessionController.class);
	
	@Autowired
	private BizSessionService bizSessionService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ClientResult fitSessionPost(HttpServletRequest request, String mobile, String password) {
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("进入登录控制器，参数：mobile = {} password = {}", mobile, password);
			}
			
			// 验证信息
			if (StringUtils.isEmpty(mobile)) {
				return new ClientResult(ActionCode.FAILURE, "请填写手机号码");
			} else if (StringUtils.isEmpty(password)) {
				return new ClientResult(ActionCode.FAILURE, "请填写密码");
			} else {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("参数验证成功，开始查询匹配手机号码和密码的员工信息");
				}
				
				ClientResult clientResult = this.bizSessionService.login(request, mobile, MD5.md5(password, 32));
				
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("查询完成，结果为：result = {}", clientResult.toString());
				}
				
				return clientResult;
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn(e.getMessage());
			}
			return new ClientResult(ActionCode.FAILURE, e.getNotice());
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage());
			}
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请联系管理员");
		}
	}
}
