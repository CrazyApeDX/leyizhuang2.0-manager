package com.ynyes.fitment.foundation.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitOrderRefund;

@Repository
public interface FitOrderRefundRepo extends ApplicationRepo<FitOrderRefund> {

	Long countByOrderIdAndStatus(Long orderId, AuditStatus status) throws Exception;
	
	Page<FitOrderRefund> findByCompanyIdOrderByRefundTimeDesc(Long companyId, Pageable page) throws Exception;
	
	Page<FitOrderRefund> findByEmployeeIdOrderByRefundTimeDesc(Long employeeId, Pageable page) throws Exception;
	
	FitOrderRefund findByOrderNumberAndStatus(String orderNumber, AuditStatus status);
	
}
