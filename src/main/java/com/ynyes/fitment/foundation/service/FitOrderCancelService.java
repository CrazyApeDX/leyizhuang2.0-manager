package com.ynyes.fitment.foundation.service;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.foundation.entity.FitOrderCancel;

public interface FitOrderCancelService {

	FitOrderCancel save(FitOrderCancel orderCancel) throws Exception;
	
	FitOrderCancel findOne(Long id) throws Exception;

	Page<FitOrderCancel> findByEmployeeIdOrderByCancelTimeDesc(Long employeeId, Integer page, Integer size)
			throws Exception;

	Page<FitOrderCancel> findByCompanyIdOrderByCancelTimeDesc(Long companyId, Integer page, Integer size)
			throws Exception;
	
	Long countByOrderNumber(String orderNumber) throws Exception;
	
	Long countByOrderNumberAndStatus(String orderNumber, AuditStatus status) throws Exception;
}
