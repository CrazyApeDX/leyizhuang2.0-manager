package com.ynyes.fitment.web.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrderCancel;
import com.ynyes.fitment.foundation.service.biz.BizOrderCancelService;

@RestController
@RequestMapping(value = "/fit/my/cancel", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
public class FitOrderCancelAuditController extends FitBasicController {

	@Autowired
	private BizOrderCancelService bizOrderCancelService;

	@RequestMapping
	public ClientResult fitOrderCancel(HttpServletRequest request, String action, Long id) {
		try {
			FitEmployee employee = this.getLoginEmployee(request);
			FitOrderCancel orderCancel = this.bizOrderCancelService.loadOne(id);
			switch (action) {
			case "AGREE":
				orderCancel.setStatus(AuditStatus.AUDIT_SUCCESS);
				this.bizOrderCancelService.auditAgree(employee, orderCancel);
				break;
			case "REJECT":
				orderCancel.setStatus(AuditStatus.AUDIT_FAILURE);
				this.bizOrderCancelService.auditReject(employee, orderCancel);
				break;
			}
			return new ClientResult(ActionCode.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请联系管理员");
		}
	}

}
