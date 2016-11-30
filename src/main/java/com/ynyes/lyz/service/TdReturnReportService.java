package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdReturnReport;
import com.ynyes.lyz.repository.TdReturnReportRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;

/**
 * TdReturnReport 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdReturnReportService {
	@Autowired
	TdReturnReportRepo repository;
	
	/**
	 * 根据时间,城市,门店查询退货报表
	 * 增加门店id查询
	 * @return
	 */
	public List<TdReturnReport> searchReturnReport(Date begin,Date end,String cityName,String diySiteCode,String username,List<String> roleDiyIds){
		Criteria<TdReturnReport> c = new Criteria<TdReturnReport>();
		if(null!=begin){
			c.add(Restrictions.gte("orderTime", begin, true));
		}if(null!=end){
			c.add(Restrictions.lte("orderTime", end, true));
		}
		if(StringUtils.isNotBlank(cityName)){
			c.add( Restrictions.eq("cityName", cityName, true));
		}
		if(StringUtils.isNotBlank(diySiteCode)){
			c.add( Restrictions.eq("diySiteCode", diySiteCode, true));
		}
		if(StringUtils.isNotBlank(username)){
			c.add( Restrictions.eq("createUsername", username, true));
		}
		if(roleDiyIds!=null && roleDiyIds.size()>0){
			c.add(Restrictions.in("diyId", roleDiyIds, true));
		}
		c.setOrderByDesc("orderTime");
		return repository.findAll(c);
	}
	/**
	 * 调用存储过程
	 * @return 
	 * @return
	 */
	public void callInsertReturnReport(Date start,Date end,String username){
		repository.callInsertReturnReport(start, end,username);
	}
	/**
	 * 根据时间,城市,门店查询退货报表
	 * 增加门店id查询
	 * @return
	 */
	public Page<TdReturnReport> searchList(String keywords,Date begin,Date end,String cityName,String diySiteCode,String username,
			int size ,int page,List<String> roleDiyIds){
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdReturnReport> c = new Criteria<TdReturnReport>();
		if(null!=begin){
			c.add(Restrictions.gte("orderTime", begin, true));
		}if(null!=end){
			c.add(Restrictions.lte("orderTime", end, true));
		}
		if(StringUtils.isNotBlank(cityName)){
			c.add( Restrictions.eq("cityName", cityName, true));
		}
		if(StringUtils.isNotBlank(diySiteCode)){
			c.add( Restrictions.eq("diySiteCode", diySiteCode, true));
		}
		if(StringUtils.isNotBlank(username)){
			c.add( Restrictions.eq("createUsername", username, true));
		}
		if(roleDiyIds!=null && roleDiyIds.size()>0){
			c.add(Restrictions.in("diyId", roleDiyIds, true));
		}
		c.setOrderByDesc("orderTime");
		return repository.findAll(c,pageRequest);
	}
}
