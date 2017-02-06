package com.ynyes.fitment.foundation.service.biz;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrder;

public interface BizOrderService {

	FitOrder initOrder(String receiver, String receiverMobile, String baseAddress, String detailAddress,
			FitEmployee employee) throws Exception;
	
	FitOrder auditOrder(Long orderId, String action) throws Exception;
	
	FitOrder findOne(Long id) throws Exception;
	
	Page<FitOrder> loadOrderByEmployeeId(Long employeeId, Integer page, Integer size) throws Exception;
	
	Page<FitOrder> loadOrderByCompanyId(Long companyId, Integer page, Integer size) throws Exception;
}
