package com.ynyes.fitment.foundation.service.biz;

import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrder;

public interface BizOrderService {

	FitOrder initOrder(String receiver, String receiverMobile, String baseAddress, String detailAddress,
			FitEmployee employee) throws Exception;
	
	FitOrder auditOrder(Long orderId, String action);
}
