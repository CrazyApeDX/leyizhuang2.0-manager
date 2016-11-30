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
import com.ynyes.lyz.repository.TdSalesDetailRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;
import com.ynyes.lyz.util.Utils;

/**
 * TdSalesDetail 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdSalesDetailService {
	@Autowired
	TdSalesDetailRepo repository;
	
	/**
	 * 根据时间,城市,门店查询销售明细报表 
	 * 增加门店id查询
	 * @return
	 */
	@Deprecated
	public List<TdSalesDetail> searchSalesDetail(Date begin,Date end,String cityName,String diySiteCode,String username,List<String> roleDiyIds){
		Criteria<TdSalesDetail> c = new Criteria<TdSalesDetail>();
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
	@Deprecated
	public void callInsertSalesDetail(Date start,Date end,String username){
		repository.callInsertSalesDetail(start, end,username);
	}
	/**
	 * 根据时间,城市,门店查询销售明细报表
	 * 增加门店id查询
	 * @return
	 */
	@Deprecated
	public Page<TdSalesDetail> searchList(String keywords,Date begin,Date end,String cityName,String diySiteCode,String username,
			int size,int page,List<String> roleDiyIds){
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdSalesDetail> c = new Criteria<TdSalesDetail>();
		if (null != keywords && !keywords.equalsIgnoreCase("")) {
			c.add(Restrictions.like("orderNumber", keywords, true));
		}
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
	
	/**
	 * 查询下载的list
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param cityName 城市
	 * @param diySiteCode 门店
	 * @param roleDiyIds 权限门店
	 * @return
	 */
	public List<TdSalesDetail> queryDownList(Date begin,Date end,String cityName,String diySiteCode,List<String> roleDiyIds){
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
