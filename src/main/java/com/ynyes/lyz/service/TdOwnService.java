package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdOwn;
import com.ynyes.lyz.entity.TdSubOwn;
import com.ynyes.lyz.repository.TdOwnRepo;
import com.ynyes.lyz.util.Utils;

/**
 * TdGoodsINOutRepo 服务类
 * 
 * @author
 *
 */

@Service
@Transactional
public class TdOwnService {
	
	@Autowired
	TdOwnRepo repository;
	
	public List<TdOwn> queryDownList(Date begin, Date end, String cityName, String diySiteCode, List<String> roleDiyIds) {
		
			//判断空值
			if(begin==null){
				begin=Utils.getSysStartDate();
			}
			if(end==null){
				end=new Date();
			} 
			if(StringUtils.isBlank(cityName)){
				cityName="%";
			}
			if(StringUtils.isBlank(diySiteCode)){
				diySiteCode="%";
			}
			if(roleDiyIds==null || roleDiyIds.size()==0){
				roleDiyIds.add("0");
			}
			return repository.queryDownList(begin, end, cityName, diySiteCode, roleDiyIds);
	
	}
	
	
	public List<TdSubOwn> queryDownListDetail(String orderNumber) {
		
		return repository.queryDownListDetail(orderNumber);

}
	
	
	
	
}
