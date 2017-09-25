package com.ynyes.fitment.web.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.service.biz.BizEmployeeService;

@RestController
@RequestMapping(value = "/fit/password", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
public class FitPasswordController extends FitBasicController {

	@Autowired
	private BizEmployeeService bizEmployeeService;
	
	@RequestMapping
	public ClientResult fitPassword(HttpServletRequest request, String oldPassword, String newPassword) {
		try {
			FitEmployee employee = this.getLoginEmployee(request);
			Boolean result = this.bizEmployeeService.changePassword(employee, oldPassword, newPassword);
			if (result) {
				return new ClientResult(ActionCode.SUCCESS, null); 
			} else {
				return new ClientResult(ActionCode.FAILURE, "原密码输入错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请联系管理员");
		}
	}
}
