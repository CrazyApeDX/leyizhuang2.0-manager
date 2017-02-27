package com.ynyes.fitment.foundation.service;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.foundation.entity.FitOrderRefund;

public interface FitOrderRefundService {

	FitOrderRefund save(FitOrderRefund orderRefund) throws Exception;

	Long countByOrderIdAndStatus(Long orderId, AuditStatus status) throws Exception;

	Page<FitOrderRefund> findByCompanyIdOrderByRefundTimeDesc(Long companyId, Integer page, Integer size)
			throws Exception;

	Page<FitOrderRefund> findByEmployeeIdOrderByRefundTimeDesc(Long employeeId, Integer page, Integer size)
			throws Exception;
}
