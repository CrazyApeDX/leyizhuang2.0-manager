package com.ynyes.fitment.foundation.service.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
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
import com.ynyes.fitment.foundation.entity.FitOrderCancel;
import com.ynyes.fitment.foundation.entity.FitOrderRefund;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitCreditChangeLogService;
import com.ynyes.fitment.foundation.service.biz.BizCreditChangeLogService;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;
import com.ynyes.lyz.interfaces.service.TdCashReciptInfService;
import com.ynyes.lyz.interfaces.service.TdCashRefundInfService;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;

@Service
@Transactional
public class BizCreditChangeLogServiceImpl implements BizCreditChangeLogService {

	@Autowired
	private FitCompanyService fitCompanyService;

	@Autowired
	private FitCreditChangeLogService fitCreditChangeLogService;
	
	@Autowired
	private TdCashReciptInfService tdCashReciptInfService;
	
	@Autowired
	private TdCashRefundInfService tdCashRefundInfService;
	
	@Autowired
	private TdInterfaceService tdInterfaceService;

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
	public FitCreditChangeLog cancelLog(FitCompany company, FitOrderCancel orderCancel) throws Exception {
		FitCreditChangeLog log = new FitCreditChangeLog();
		log.setCreateOrigin(OriginType.BUSINESS);
		log.setCreateTime(new Date());
		log.setBeforeChange(company.getCredit() - orderCancel.getCredit()).setAfterChange(company.getCredit())
				.setMoney(orderCancel.getCredit()).setChangeTime(new Date())
				.setReferenceNumber(orderCancel.getOrderNumber().replace("FIT", "")).setType(CreditChangeType.CANCEL)
				.setOperatorType(CreditOperator.PURCHASER).setOperatorId(orderCancel.getAuditorId()).setRemark("订单取消")
				.setCompanyId(company.getId()).setCompanyTitle(company.getName());
		return this.fitCreditChangeLogService.save(log);
	}

	@Override
	public FitCreditChangeLog refundLog(FitCompany company, FitOrderRefund orderRefund) throws Exception {
		FitCreditChangeLog log = new FitCreditChangeLog();
		log.setCreateOrigin(OriginType.BUSINESS);
		log.setCreateTime(new Date());
		log.setBeforeChange(company.getCredit() - orderRefund.getCredit()).setAfterChange(company.getCredit())
				.setMoney(orderRefund.getCredit()).setChangeTime(new Date())
				.setReferenceNumber(orderRefund.getOrderNumber().replace("FIT", "")).setType(CreditChangeType.REFUND)
				.setOperatorType(CreditOperator.PURCHASER).setOperatorId(orderRefund.getAuditorId()).setRemark("订单退货")
				.setCompanyId(company.getId()).setCompanyTitle(company.getName());
		return this.fitCreditChangeLogService.save(log);
	}

	@Override
	public void creditAction(TdManager manager, FitCompany company, Double inputCredit, String remark)
			throws Exception {
		this.manageLog(manager, company, inputCredit, remark);
		if (inputCredit < 0) {
			this.doRefund(company, inputCredit);
		} else {
			this.doReceipt(company, inputCredit);
		}
	}

	private void doReceipt(FitCompany company, Double inputCredit) {
		TdCashReciptInf receipt = new TdCashReciptInf();
		Date now = new Date();
		receipt.setInitDate(now);
		receipt.setModifyDate(now);
		receipt.setAmount(inputCredit);
		receipt.setDiySiteCode(company.getCode());
		receipt.setProductType("CREDIT");
		receipt.setReceiptClass("信用额度");
		receipt.setReceiptDate(now);
		receipt.setReceiptNumber(this.getNumber(now, "RECRE"));
		receipt.setReceiptType("装饰公司信用额度充值");
		receipt.setSobId(company.getSobId());
		receipt.setUserid(company.getId());
		receipt.setUsername(company.getName());
		receipt.setUserphone("00000000000");
		receipt = this.tdCashReciptInfService.save(receipt);
		String resultStr = tdInterfaceService.ebsWithObject(receipt, INFTYPE.CASHRECEIPTINF);
		if (StringUtils.isBlank(resultStr)) {
			receipt.setSendFlag(0);
		} else {
			receipt.setSendFlag(1);
			receipt.setErrorMsg(resultStr);
		}
		tdCashReciptInfService.save(receipt);
	}

	private void doRefund(FitCompany company, Double inputCredit) {
		TdCashRefundInf refund = new TdCashRefundInf();
		Date now = new Date();
		refund.setInitDate(now);
		refund.setModifyDate(now);
		refund.setAmount(-1 * inputCredit);
		refund.setDiySiteCode(company.getCode());
		refund.setProductType("CREDIT");
		refund.setRefundClass("信用额度");
		refund.setRefundDate(now);
		refund.setRefundNumber(this.getNumber(now, "DECRE"));
		refund.setRefundType("装饰公司信用额度充值错误调整");
		refund.setSobId(company.getSobId());
		refund.setUserid(company.getId());
		refund.setUsername(company.getName());
		refund.setUserphone("00000000000");
		refund = tdCashRefundInfService.save(refund);
		tdInterfaceService.ebsWithObject(refund, INFTYPE.CASHREFUNDINF);
		
	}

	private String getNumber(Date date, String type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		int random = new Random().nextInt(900) + 100;
		return type + sdf.format(date) + random;
	}

	@Override
	public FitCreditChangeLog manageLog(TdManager manager, FitCompany company, Double inputCredit, String remark)
			throws Exception {
		company.setCredit(company.getCredit() + inputCredit);
		this.fitCompanyService.save(company);
		FitCreditChangeLog log = new FitCreditChangeLog();
		log.setCreateOrigin(OriginType.ADD);
		log.setCreateTime(new Date());
		log.setBeforeChange(company.getCredit() - inputCredit).setAfterChange(company.getCredit()).setMoney(inputCredit)
				.setChangeTime(new Date()).setReferenceNumber(log.initManagerOperateNumber())
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
