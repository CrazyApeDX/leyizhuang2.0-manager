package com.ynyes.lyz.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdReserveOrder;
import com.ynyes.lyz.repository.TdReserveOrderRepo;

/**
 *  未提货订单
 * 
 * @author
 *
 */

@Service
@Transactional
public class TdReserveOrderService {
	
	@Autowired
	TdReserveOrderRepo repository;
	
	public List<TdReserveOrder> queryDownList(String cityName, String diySiteCode, List<String> roleDiyIds) {
		
			
			if(StringUtils.isBlank(cityName)){
				cityName="%";
			}
			if(StringUtils.isBlank(diySiteCode)){
				diySiteCode="%";
			}
			if(roleDiyIds==null || roleDiyIds.size()==0){
				roleDiyIds.add("0");
			}
			return repository.queryDownList(cityName, diySiteCode, roleDiyIds);
	
	}
	
	
	
}
