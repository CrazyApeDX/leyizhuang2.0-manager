package com.ynyes.lyz.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdSalesForContinuousBuy;
import com.ynyes.lyz.repository.TdSalesForContinuousBuyRepo;

/**
 * 会员连续月份购买记录报表  服务类
 * 
 * @author
 *
 */

@Service
@Transactional
public class TdSalesForContinuousBuyService {

	@Autowired
	TdSalesForContinuousBuyRepo repository;

	public List<TdSalesForContinuousBuy> queryDownList(String beginStr, String endStr, String cityName, String diySiteCode,
			List<String> roleDiyIds) {
		
		
		if (StringUtils.isBlank(cityName)) {
			cityName = "%";
		}
		if (StringUtils.isBlank(diySiteCode)) {
			diySiteCode = "%";
		}
		if (roleDiyIds == null || roleDiyIds.size() == 0) {
			roleDiyIds.add("0");
		}
		return repository.queryDownList(Integer.parseInt(beginStr), Integer.parseInt(endStr), cityName, diySiteCode, roleDiyIds);

	}

	public Integer retriveSale(String diySiteName, String username, String sellerUsername,
			String monthStr) {
		return repository.retriveSale(diySiteName,username,sellerUsername,monthStr);
	}

	







	
}
