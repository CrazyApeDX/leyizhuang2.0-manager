package com.ynyes.fitment.foundation.service.biz.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.constant.CreditChangeType;
import com.ynyes.fitment.core.constant.CreditOperator;
import com.ynyes.fitment.core.entity.persistent.table.TableEntity.OriginType;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrder;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitCreditChangeLogService;
import com.ynyes.fitment.foundation.service.biz.BizCreditChangeLogService;
import com.ynyes.lyz.entity.TdManager;

@Service
@Transactional
public class BizCreditChangeLogServiceImpl implements BizCreditChangeLogService {

	@Autowired
	private FitCompanyService fitCompanyService;

	@Autowired
	private FitCreditChangeLogService fitCreditChangeLogService;

	@Override
	public FitCreditChangeLog consumeLog(FitCompany company, FitOrder order) throws Exception {
		company.setCredit(company.getCredit() - order.getBalancePayed() - order.getUpstairsBalancePayed());
		this.fitCompanyService.save(company);
		FitCreditChangeLog log = new FitCreditChangeLog();
		log.setCreateOrigin(OriginType.BUSINESS);
		log.setCreateTime(new Date());
		log.setBeforeChange(company.getCredit() + order.getBalancePayed() + order.getUpstairsBalancePayed())
				.setAfterChange(company.getCredit())
				.setMoney(-1 * (order.getBalancePayed() + order.getUpstairsBalancePayed())).setChangeTime(new Date())
				.setReferenceNumber(order.getOrderNumber()).setType(CreditChangeType.CONSUME)
				.setOperatorType(CreditOperator.PURCHASER).setOperatorId(order.getEmployeeId()).setRemark("订单消费")
				.setCompanyId(company.getId()).setCompanyTitle(company.getName());
		return this.fitCreditChangeLogService.save(log);
	}

	@Override
	public FitCreditChangeLog manageLog(TdManager manager, FitCompany company, Double inputCredit, String remark)
			throws Exception {
		company.setCredit(company.getCredit() + inputCredit);
		this.fitCompanyService.save(company);
		FitCreditChangeLog log = new FitCreditChangeLog();
		log.setCreateOrigin(OriginType.ADD);
		log.setCreateTime(new Date());
		log.setBeforeChange(company.getCredit() - inputCredit).setAfterChange(company.getCredit())
				.setMoney(inputCredit).setChangeTime(new Date()).setReferenceNumber(log.initManagerOperateNumber())
				.setType(inputCredit < 0 ? CreditChangeType.CUT : CreditChangeType.RECHARGE)
				.setOperatorType(CreditOperator.MANAGER).setOperatorId(manager.getId()).setRemark(remark)
				.setCompanyId(company.getId()).setCompanyTitle(company.getName());
		return this.fitCreditChangeLogService.save(log);
	}

	@Override
	public List<FitCreditChangeLog> employeeGetLog(FitEmployee employee) throws Exception {
		return this.fitCreditChangeLogService.findByCompanyIdOrderByChangeTimeDesc(employee.getCompanyId());
	}

	@Override
	public Page<FitCreditChangeLog> employeeGetLog(FitEmployee employee, Integer page, Integer size) throws Exception {
		return this.fitCreditChangeLogService.findByCompanyIdOrderByChangeTimeDesc(employee.getCompanyId(), page, size);
	}

}
