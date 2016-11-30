package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdAgencyFund;
import com.ynyes.lyz.repository.TdAgencyFundRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;
import com.ynyes.lyz.util.Utils;

/**
 * TdOrder 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdAgencyFundService {
	@Autowired
	TdAgencyFundRepo repository;

	/**
	 * 根据订单时间查询代收款报表数据
	 * @return
	 */
	public List<TdAgencyFund> searchAllByTime(Date start,Date end){
		if(null == start || null == end){
			return null;
		}
		return repository.searchAllByTime(start, end);
	}
	
	/**
	 * 根据订单时间,门店查询代收款报表数据
	 * @return
	 */
	public List<TdAgencyFund> searchAllbyDiyCodeAndTime(String diyCode,Date start,Date end){
		if(null == start || null == end){
			return null;
		}
		return repository.searchAllbyDiyCodeAndTime(diyCode,start, end);
	}
	
	/**
	 * 根据订单时间,城市查询代收款报表数据
	 * @return
	 */
	public List<TdAgencyFund> searchAllbyCityAndTime(String city,Date start,Date end){
		if(null == start || null == end){
			return null;
		}
		return repository.searchAllbyCityAndTime(city,start, end);
	}
	
	/**
	 * 根据时间,城市,门店查询代销售报表
	 * 增加门店id查询
	 * @return
	 */
	public List<TdAgencyFund> searchAgencyFund(Date begin,Date end,String cityName,String diySiteCode,String username,List<String> roleDiyIds){
		Criteria<TdAgencyFund> c = new Criteria<TdAgencyFund>();
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
	public void callInsertAgencyFund(Date start,Date end,String username){
		repository.callInsertAgencyFund(start, end,username);
	}
	/**
	 * 根据时间,城市,门店查询代销售报表
	 * 增加门店id查询
	 * @return
	 */
	public Page<TdAgencyFund> searchList(String keywords,Date begin,Date end,String cityName,String diySiteCode,String username,
			int size,int page,List<String> roleDiyIds){
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdAgencyFund> c = new Criteria<TdAgencyFund>();
		if (null != keywords && !keywords.equalsIgnoreCase("")) {
			c.add(Restrictions.like("mainOrderNumber", keywords, true));
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

	public List<TdAgencyFund> queryDownList(Date begin, Date end, String cityName, String diySiteCode, String username,
			List<String> roleDiyIds) {

		// 判断空值
		if (begin == null) {
			begin = Utils.getSysStartDate();
		}
		if (end == null) {
			end = new Date();
		}
		if (StringUtils.isBlank(cityName)) {
			cityName = "%";
		}
		if (StringUtils.isBlank(diySiteCode)) {
			diySiteCode = "%";
		}
		if (roleDiyIds == null || roleDiyIds.size() == 0) {
			roleDiyIds.add("0");
		}
		return repository.queryDownList(begin, end, cityName, diySiteCode, roleDiyIds);

	}
}
