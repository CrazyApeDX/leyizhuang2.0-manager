package com.ynyes.fitment.foundation.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitPromotionMoneyLog;
import com.ynyes.fitment.foundation.repo.FitPromotionMoneyLogRepo;
import com.ynyes.fitment.foundation.service.FitPromotionMoneyLogService;
import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;
import com.ynyes.lyz.interfaces.service.TdCashReciptInfService;
import com.ynyes.lyz.interfaces.service.TdCashRefundInfService;
import com.ynyes.lyz.interfaces.service.TdEbsSenderService;

@Service
@Transactional
public class FitPromotionMoneyLogServiceImpl implements FitPromotionMoneyLogService {

	@Autowired
	private FitPromotionMoneyLogRepo fitPromotionMoneyLogRepo;
	
	@Autowired
	private TdCashRefundInfService tdCashRefundInfService;
	
	@Autowired
	private TdCashReciptInfService tdCashReciptInfService;
	
	@Autowired
	private TdEbsSenderService tdEbsSenderService;
	
	@Override
	public FitPromotionMoneyLog save(FitPromotionMoneyLog log) throws Exception {
		return this.fitPromotionMoneyLogRepo.save(log);
	}

	@Override
	public void doRefund(FitCompany company, Double money, String referenceNumber) throws Exception {
		TdCashRefundInf refund = new TdCashRefundInf();
		Date now = new Date();
		refund.setInitDate(now);
		refund.setModifyDate(now);
		refund.setAmount(-1 * money);
		refund.setDiySiteCode(company.getCode());
		refund.setProductType("PREPAY");
		refund.setRefundClass("预收款");
		refund.setRefundDate(now);
		refund.setRefundNumber(this.getNumber(now, "DECRE"));
		refund.setRefundType("钱包充值");
		refund.setSobId(company.getSobId());
		refund.setUserid(company.getId());
		refund.setUsername(company.getName());
		refund.setUserphone("00000000000");
		refund.setReturnNumber(referenceNumber);
		refund = tdCashRefundInfService.save(refund);
//		tdInterfaceService.ebsWithObject(refund, INFTYPE.CASHREFUNDINF);
		tdEbsSenderService.sendCashRefundToEbsAndRecord(refund);
		
	}
	@Override
	public void doRefund(FitCompany company, Double money, String referenceNumber,String remark) throws Exception {
		TdCashRefundInf refund = new TdCashRefundInf();
		Date now = new Date();
		refund.setInitDate(now);
		refund.setModifyDate(now);
		refund.setAmount(-1 * money);
		refund.setDiySiteCode(company.getCode());
		refund.setProductType("PREPAY");
		refund.setRefundClass("预收款");
		refund.setRefundDate(now);
		refund.setRefundNumber(this.getNumber(now, "DECRE"));
		refund.setRefundType(remark);
		refund.setSobId(company.getSobId());
		refund.setUserid(company.getId());
		refund.setUsername(company.getName());
		refund.setUserphone("00000000000");
		refund.setReturnNumber(referenceNumber);
		refund = tdCashRefundInfService.save(refund);
//		tdInterfaceService.ebsWithObject(refund, INFTYPE.CASHREFUNDINF);
		tdEbsSenderService.sendCashRefundToEbsAndRecord(refund);
	}

	@Override
	public void doReceipt(FitCompany company, Double money, String referenceNumber) throws Exception {
		TdCashReciptInf receipt = new TdCashReciptInf();
		Date now = new Date();
		receipt.setInitDate(now);
		receipt.setModifyDate(now);
		receipt.setAmount(money);
		receipt.setDiySiteCode(company.getCode());
		receipt.setProductType("PREPAY");
		receipt.setReceiptClass("预收款");
		receipt.setReceiptDate(now);
		receipt.setReceiptNumber(this.getNumber(now, "RECRE"));
		receipt.setReceiptType("钱包充值");
		receipt.setSobId(company.getSobId());
		receipt.setUserid(company.getId());
		receipt.setUsername(company.getName());
		receipt.setUserphone("00000000000");
		receipt.setOrderNumber(referenceNumber);
		receipt = this.tdCashReciptInfService.save(receipt);
		tdEbsSenderService.sendCashReciptToEbsAndRecord(receipt);
//		String resultStr = tdInterfaceService.ebsWithObject(receipt, INFTYPE.CASHRECEIPTINF);
//		if (StringUtils.isBlank(resultStr)) {
//			receipt.setSendFlag(0);
//		} else {
//			receipt.setSendFlag(1);
//			receipt.setErrorMsg(resultStr);
//		}
//		tdCashReciptInfService.save(receipt);
	}
	
	@Override
	public void doReceipt(FitCompany company, Double money, String referenceNumber,String remark) throws Exception {
		TdCashReciptInf receipt = new TdCashReciptInf();
		Date now = new Date();
		receipt.setInitDate(now);
		receipt.setModifyDate(now);
		receipt.setAmount(money);
		receipt.setDiySiteCode(company.getCode());
		receipt.setProductType("PREPAY");
		receipt.setReceiptClass("预收款");
		receipt.setReceiptDate(now);
		receipt.setReceiptNumber(this.getNumber(now, "RECRE"));
		receipt.setReceiptType(remark);
		receipt.setSobId(company.getSobId());
		receipt.setUserid(company.getId());
		receipt.setUsername(company.getName());
		receipt.setUserphone("00000000000");
		receipt.setOrderNumber(referenceNumber);
		receipt = this.tdCashReciptInfService.save(receipt);
		tdEbsSenderService.sendCashReciptToEbsAndRecord(receipt);
//		String resultStr = tdInterfaceService.ebsWithObject(receipt, INFTYPE.CASHRECEIPTINF);
//		if (StringUtils.isBlank(resultStr)) {
//			receipt.setSendFlag(0);
//		} else {
//			receipt.setSendFlag(1);
//			receipt.setErrorMsg(resultStr);
//		}
//		tdCashReciptInfService.save(receipt);
	}
	private String getNumber(Date date, String type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		int random = new Random().nextInt(900) + 100;
		return type + sdf.format(date) + random;
	}

}
