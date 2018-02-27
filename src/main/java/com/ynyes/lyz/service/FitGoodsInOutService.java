package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.repo.FitCartGoodsRepo;
import com.ynyes.lyz.entity.FitGoodsInOut;
import com.ynyes.lyz.entity.TdGoodsInOut;
import com.ynyes.lyz.repository.FITGoodsINOutRepo;
import com.ynyes.lyz.repository.TdGoodsINOutRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;
import com.ynyes.lyz.util.Utils;

/**
 * 装饰公司出退货报表 服务类
 * 
 * @author
 *
 */

@Service
@Transactional
public class FitGoodsInOutService {
	
	@Autowired
	FITGoodsINOutRepo repository;
	
	
	
	
	/**
	 * 查询下载的list
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param cityName 城市
	 * @param diySiteCode 门店
	 * @return
	 * @param roleDiyIds 权限门店
	 */
	public List<FitGoodsInOut> queryDownList(Date begin,Date end,String cityName,String code,
			List<Long> salesList, Long statusId){
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
		if(StringUtils.isBlank(code)){
			code="%";
		}
		if(salesList==null || salesList.size()==0){
			salesList.add(0L);
		}
		if (statusId.equals(25L)) {
			return repository.queryDownList(begin, end, cityName, code, salesList);
		} else {
			return repository.queryDownList(begin, end, cityName, code);
		}
	}
	
}
