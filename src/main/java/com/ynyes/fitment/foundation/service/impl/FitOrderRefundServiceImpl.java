package com.ynyes.fitment.foundation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitOrderRefund;
import com.ynyes.fitment.foundation.repo.FitOrderRefundRepo;
import com.ynyes.fitment.foundation.service.FitOrderRefundService;

@Service
@Transactional
public class FitOrderRefundServiceImpl extends PageableService implements FitOrderRefundService {

	@Autowired
	private FitOrderRefundRepo fitOrderRefundRepo;

	@Override
	public FitOrderRefund save(FitOrderRefund orderRefund) throws Exception {
		if (null == orderRefund) {
			return null;
		}
		return this.fitOrderRefundRepo.save(orderRefund);
	}

	@Override
	public Long countByOrderIdAndStatus(Long orderId, AuditStatus status) throws Exception {
		if (null == orderId || null == status) {
			return null;
		}
		return this.fitOrderRefundRepo.countByOrderIdAndStatus(orderId, status);
	}

	@Override
	public Page<FitOrderRefund> findByCompanyIdOrderByRefundTimeDesc(Long companyId, Integer page, Integer size)
			throws Exception {
		if (null == companyId) {
			return null;
		}
		return this.fitOrderRefundRepo.findByCompanyIdOrderByRefundTimeDesc(companyId, this.initPage(page, size));
	}

	@Override
	public Page<FitOrderRefund> findByEmployeeIdOrderByRefundTimeDesc(Long employeeId, Integer page, Integer size)
			throws Exception {
		if (null == employeeId) {
			return null;
		}
		return this.fitOrderRefundRepo.findByEmployeeIdOrderByRefundTimeDesc(employeeId, this.initPage(page, size));
	}

	@Override
	public FitOrderRefund findOne(Long id) throws Exception {
		if (null == id) {
			return null;
		}
		return this.fitOrderRefundRepo.findOne(id);
	}

	@Override
	public FitOrderRefund findByOrderNumberAndStatus(String orderNumber, AuditStatus status) {
		if (null == orderNumber || null == status) {
			return null;
		} else {
			return this.fitOrderRefundRepo.findByOrderNumberAndStatus(orderNumber, status);
		}
	}

}
