package com.ynyes.fitment.foundation.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitOrder;

@Repository
public interface FitOrderRepo extends ApplicationRepo<FitOrder> {

	List<FitOrder> findByCompanyIdAndIsDeleteFalseOrderByOrderTimeDesc(Long companyId) throws Exception;

	Page<FitOrder> findByCompanyIdAndIsDeleteFalseOrderByOrderTimeDesc(Long companyId, Pageable page) throws Exception;

	List<FitOrder> findByCompanyIdAndStatus(Long companyId, AuditStatus status) throws Exception;

	Page<FitOrder> findByCompanyIdAndStatus(Long companyId, AuditStatus status, Pageable page) throws Exception;

	List<FitOrder> findByEmployeeIdOrderByOrderTimeDesc(Long employeeId) throws Exception;
	
	Page<FitOrder> findByEmployeeIdOrderByOrderTimeDesc(Long employeeId, Pageable page) throws Exception;
}
