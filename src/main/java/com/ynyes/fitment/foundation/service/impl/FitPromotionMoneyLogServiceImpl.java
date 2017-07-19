package com.ynyes.fitment.foundation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitPromotionMoneyLog;
import com.ynyes.fitment.foundation.repo.FitPromotionMoneyLogRepo;
import com.ynyes.fitment.foundation.service.FitPromotionMoneyLogService;

@Service
@Transactional
public class FitPromotionMoneyLogServiceImpl implements FitPromotionMoneyLogService {

	@Autowired
	private FitPromotionMoneyLogRepo fitPromotionMoneyLogRepo;
	
	@Override
	public FitPromotionMoneyLog save(FitPromotionMoneyLog log) throws Exception {
		return this.fitPromotionMoneyLogRepo.save(log);
	}

}
