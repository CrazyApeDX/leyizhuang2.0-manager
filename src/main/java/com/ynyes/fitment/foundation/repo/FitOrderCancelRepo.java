package com.ynyes.fitment.foundation.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitOrderCancel;

@Repository
public interface FitOrderCancelRepo extends ApplicationRepo<FitOrderCancel> {

	Long countByOrderNumber(String orderNumber) throws Exception;
	
	Long countByOrderNumberAndStatus(String orderNumber, AuditStatus status) throws Exception;
	
	Page<FitOrderCancel> findByEmployeeIdOrderByCancelTimeDesc(Long employeeId, Pageable page) throws Exception;

	Page<FitOrderCancel> findByCompanyIdOrderByCancelTimeDesc(Long company, Pageable page) throws Exception;

}
