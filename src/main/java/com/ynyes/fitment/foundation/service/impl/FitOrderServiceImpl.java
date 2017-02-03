package com.ynyes.fitment.foundation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitOrder;
import com.ynyes.fitment.foundation.repo.FitOrderRepo;
import com.ynyes.fitment.foundation.service.FitOrderService;

@Service
@Transactional
public class FitOrderServiceImpl extends PageableService implements FitOrderService {

	@Autowired
	private FitOrderRepo fitOrderRepo;

	@Override
	public FitOrder save(FitOrder order) throws Exception {
		if (null == order) {
			return null;
		}
		return this.fitOrderRepo.save(order);
	}

	@Override
	public List<FitOrder> findByCompanyId(Long companyId) throws Exception {
		if (null == companyId) {
			return null;
		}
		return this.fitOrderRepo.findByCompanyId(companyId);
	}

	@Override
	public Page<FitOrder> findByCompanyId(Long companyId, Integer page, Integer size) throws Exception {
		if (null == companyId) {
			return null;
		}
		return this.fitOrderRepo.findByCompanyId(companyId, this.initPage(page, size));
	}

	@Override
	public List<FitOrder> findByCompanyIdAndStatus(Long companyId, AuditStatus status) throws Exception {
		if (null == companyId || null == status) {
			return null;
		}
		return this.fitOrderRepo.findByCompanyIdAndStatus(companyId, status);
	}

	@Override
	public Page<FitOrder> findByCompanyIdAndStatus(Long companyId, AuditStatus status, Integer page, Integer size)
			throws Exception {
		if (null == companyId || null == status) {
			return null;
		}
		return this.fitOrderRepo.findByCompanyIdAndStatus(companyId, status, this.initPage(page, size));
	}

	@Override
	public List<FitOrder> findByEmployeeId(Long employeeId) throws Exception {
		if (null == employeeId) {
			return null;
		}
		return this.fitOrderRepo.findByEmployeeId(employeeId);
	}

	@Override
	public Page<FitOrder> findByEmployeeId(Long employeeId, Integer page, Integer size) throws Exception {
		if (null == employeeId) {
			return null;
		}
		return this.fitOrderRepo.findByEmployeeId(employeeId, this.initPage(page, size));
	}

}
