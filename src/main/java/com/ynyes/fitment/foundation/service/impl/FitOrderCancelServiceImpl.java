package com.ynyes.fitment.foundation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.foundation.entity.FitOrderCancel;
import com.ynyes.fitment.foundation.repo.FitOrderCancelRepo;
import com.ynyes.fitment.foundation.service.FitOrderCancelService;

@Service
@Transactional
public class FitOrderCancelServiceImpl implements FitOrderCancelService {

	@Autowired
	private FitOrderCancelRepo repository;

	@Override
	public FitOrderCancel save(FitOrderCancel orderCancel) throws Exception {
		if (null == orderCancel) {
			return null;
		}
		return this.repository.save(orderCancel);
	}

	@Override
	public Page<FitOrderCancel> findByEmployeeIdOrderByCancelTimeDesc(Long employeeId, Integer page, Integer size)
			throws Exception {
		if (null == employeeId) {
			return null;
		}
		Pageable pageable = new PageRequest(page, size);
		return this.repository.findByEmployeeIdOrderByCancelTimeDesc(employeeId, pageable);
	}

	@Override
	public Page<FitOrderCancel> findByCompanyIdOrderByCancelTimeDesc(Long companyId, Integer page, Integer size)
			throws Exception {
		if (null == companyId) {
			return null;
		}
		Pageable pageable = new PageRequest(page, size);
		return this.repository.findByCompanyIdOrderByCancelTimeDesc(companyId, pageable);
	}

	@Override
	public Long countByOrderNumber(String orderNumber) throws Exception {
		if (null == orderNumber) {
			return null;
		}
		return this.repository.countByOrderNumber(orderNumber);
	}

	@Override
	public Long countByOrderNumberAndStatus(String orderNumber, AuditStatus status) throws Exception {
		if (null == orderNumber) {
			return null;
		}
		return this.repository.countByOrderNumberAndStatus(orderNumber, status);
	}

	@Override
	public FitOrderCancel findOne(Long id) throws Exception {
		if (null == id) {
			return null;
		}
		return this.repository.findOne(id);
	}

}
