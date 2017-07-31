package com.ynyes.fitment.foundation.service;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitPromotionMoneyLog;

public interface FitPromotionMoneyLogService {

	FitPromotionMoneyLog save(FitPromotionMoneyLog log) throws Exception;
	
	void doRefund(FitCompany company, Double money, String referenceNumber) throws Exception;
	
	void doReceipt(FitCompany company, Double money, String referenceNumber) throws Exception;
}
