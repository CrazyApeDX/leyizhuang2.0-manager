package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdSalesDetail;
import com.ynyes.lyz.entity.TdSalesDetailForFranchiser;
import com.ynyes.lyz.repository.TdSalesDetailForFranchiserRepo;
import com.ynyes.lyz.repository.TdSalesDetailRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;
import com.ynyes.lyz.util.Utils;

/**
 * 加盟商销售明细报表 服务层
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdSalesDetailForFranchiserService {
	@Autowired
	TdSalesDetailForFranchiserRepo repository;
	
	/**
	 * 查询下载的list
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param cityName 城市
	 * @param diySiteCode 门店
	 * @param roleDiyIds 权限门店
	 * @return
	 */
	public List<TdSalesDetailForFranchiser> queryDownList(Date begin,Date end,String cityName,String diySiteCode,List<String> roleDiyIds){
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
}
