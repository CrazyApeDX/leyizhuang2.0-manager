package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdGoodsInOut;
import com.ynyes.lyz.entity.TdGoodsInOutFranchisees;
import com.ynyes.lyz.repository.TdGoodsINOutFranchiseesRepo;
import com.ynyes.lyz.repository.TdGoodsINOutRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;
import com.ynyes.lyz.util.Utils;

/**
 * TdGoodsINOutFranchiseesRepo 服务类
 * 
 * @author
 *
 */

@Service
@Transactional
public class TdGoodsInOutFranchiseesService {
	
	@Autowired
	TdGoodsINOutRepo repository;
	
	@Autowired
	TdGoodsINOutFranchiseesRepo repositorys;
	
	/**
	 * 根据时间,城市,门店查询进出货报表
	 * 增加门店id查询
	 * @return
	 */
	@Deprecated
	public List<TdGoodsInOut> searchGoodsInOut(Date begin,Date end,String cityName,String diySiteCode,String username,List<String> roleDiyIds){
		Criteria<TdGoodsInOut> c = new Criteria<TdGoodsInOut>();
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
	public void callinsertGoodsInOutInitial(Date start,Date end,String username){
		repository.callinsertGoodsInOutInitial(start, end,username);
	}
	
	/**
	 * 分页条件查询 
	 * 增加门店id查询
	 * @return
	 */
	@Deprecated
	public Page<TdGoodsInOut> searchList(String keywords, Date orderStartTime, Date orderEndTime, String diyCode,String city,String username,
			int size, int page,List<String> roleDiyIds) {
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdGoodsInOut> c = new Criteria<TdGoodsInOut>();
		if (null != keywords && !keywords.equalsIgnoreCase("")) {
			c.add(Restrictions.like("orderNumber", keywords, true));
		}
		if(null!=orderStartTime){
			c.add(Restrictions.gte("orderTime", orderStartTime, true));
		}if(null!=orderEndTime){
			c.add(Restrictions.lte("orderTime", orderEndTime, true));
		}
		if (null != diyCode && !"".equals(diyCode)) {
			c.add(Restrictions.eq("diySiteCode", diyCode, true));
		}
		if (null != city && !"".equals(city)) {
			c.add(Restrictions.eq("cityName", city, true));
		}
		if(StringUtils.isNotBlank(username)){
			c.add( Restrictions.eq("createUsername", username, true));
		}
		if(roleDiyIds!=null && roleDiyIds.size()>0){
			c.add(Restrictions.in("diyId", roleDiyIds, true));
		}
		c.setOrderByDesc("orderTime");
		return repository.findAll(c, pageRequest);
	}
	
	/**
	 * 查询下载的list
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param cityName 城市
	 * @param diySiteCode 门店
	 * @return
	 * @param roleDiyIds 权限门店
	 */
	public List<TdGoodsInOut> queryDownList(Date begin,Date end,String cityName,String diySiteCode,List<String> roleDiyIds){
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
	
	public List<TdGoodsInOut> queryDownListTake(Date begin,Date end,String cityName,String diySiteCode,List<String> roleDiyIds){
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
		return repository.queryDownListTake(begin, end, cityName, diySiteCode, roleDiyIds);
	}
	
	/**
	 * 查询下载的lis(加盟商)
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param cityName 城市
	 * @param diySiteCode 门店
	 * @return
	 * @param roleDiyIds 权限门店
	 */
	public List<TdGoodsInOutFranchisees> queryFranchiseesDownList(Date begin,Date end,String cityName,String diySiteCode,List<String> roleDiyIds){
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
		return repositorys.queryDownList(begin, end, cityName, diySiteCode, roleDiyIds);
	}
	
}
