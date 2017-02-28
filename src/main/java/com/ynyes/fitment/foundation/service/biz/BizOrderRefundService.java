package com.ynyes.fitment.foundation.service.biz;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrderRefund;

public interface BizOrderRefundService {
	
	Boolean validateRepeatAudit(Long orderId, AuditStatus status) throws Exception;

	FitOrderRefund init(String receiverName, String receiverMobile, String receiverAddress,
			String receiverAddressDetail, FitEmployee employee, String data, Long orderId) throws Exception;
	
	FitOrderRefund loadOne(Long id) throws Exception;
	
	void auditAgree(FitEmployee operator, FitOrderRefund orderRefund) throws Exception;
	
	void auditReject(FitEmployee operator, FitOrderRefund orderRefund) throws Exception;
}
