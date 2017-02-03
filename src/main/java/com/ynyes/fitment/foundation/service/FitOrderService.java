package com.ynyes.fitment.foundation.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.foundation.entity.FitOrder;

public interface FitOrderService {

	FitOrder save(FitOrder order) throws Exception;
	
	List<FitOrder> findByCompanyId(Long companyId) throws Exception;

	Page<FitOrder> findByCompanyId(Long companyId, Integer page, Integer size) throws Exception;

	List<FitOrder> findByCompanyIdAndStatus(Long companyId, AuditStatus status) throws Exception;

	Page<FitOrder> findByCompanyIdAndStatus(Long companyId, AuditStatus status, Integer page, Integer size) throws Exception;

	List<FitOrder> findByEmployeeId(Long employeeId) throws Exception;
	
	Page<FitOrder> findByEmployeeId(Long employeeId, Integer page, Integer size) throws Exception;
}
