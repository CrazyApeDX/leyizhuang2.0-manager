package com.ynyes.fitment.web.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.service.biz.BizOrderRefundService;

@Controller
@RequestMapping(value = "/fit/my/refund", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
public class FitOrderReturnAuditController extends FitBasicController {

	@Autowired
	private BizOrderRefundService bizOrderRefundService;

	@RequestMapping
	@ResponseBody
	public ClientResult fitOrderRefund(String receiverName, String receiverMobile, String receiverInfo,
			String detailAddress, Long id, String data, HttpServletRequest request) {
		try {
			FitEmployee employee = this.getLoginEmployee(request);
			this.bizOrderRefundService.init(receiverName, receiverMobile, receiverInfo, detailAddress, employee, data,
					id);
			return new ClientResult(ActionCode.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请联系管理员");
		}
	}

	@RequestMapping(value = "/check")
	@ResponseBody
	public ClientResult refundCheck(Long id) {
		try {
			Boolean result = this.bizOrderRefundService.validateRepeatAudit(id, AuditStatus.WAIT_AUDIT);
			if (result) {
				return new ClientResult(ActionCode.FAILURE, "您的申请正在等待审核，请勿重复提交");
			} else {
				return new ClientResult(ActionCode.SUCCESS, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请联系管理员");
		}
	}

}
