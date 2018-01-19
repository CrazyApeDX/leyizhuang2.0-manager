package com.ynyes.fitment.foundation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitSalesManager;
import com.ynyes.fitment.foundation.repo.FitSalesManagerRepo;
import com.ynyes.fitment.foundation.service.FitSalesManagerService;


@Service
@Transactional
public class FitSalesManagerServiceImpl implements FitSalesManagerService{

	@Autowired
	private FitSalesManagerRepo fitSalesManagerRepo;
	
	@Override
	public List<FitSalesManager> findByCityCode(String cityCode) {
		return this.fitSalesManagerRepo.findByCityCode(cityCode);
	}

}
