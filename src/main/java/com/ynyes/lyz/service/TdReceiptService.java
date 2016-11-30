package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdReceipt;
import com.ynyes.lyz.repository.TdReceiptRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;
import com.ynyes.lyz.util.Utils;

/**
 * 收款报表服务类
 * 
 * @author yanle
 *
 */

@Service
@Transactional
public class TdReceiptService {
	@Autowired
	TdReceiptRepo repository;

	
	/**
	 * 根据时间,城市,门店查询收款报表
	 * 增加门店id查询
	 * @return
	 */
	public List<TdReceipt> searchReceipt(Date begin,Date end,String cityName,String diySiteCode,String username,List<String> roleDiyIds){
		Criteria<TdReceipt> c = new Criteria<TdReceipt>();
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
	public void callInsertReceipt(Date start,Date end,String username){
		repository.callInsertReceipt(start,end,username);
	}
	
	/**
	 * 根据时间,城市,门店查询收款报表
	 * 增加门店id查询
	 * @return
	 */
	public Page<TdReceipt> searchList(String keywords,Date begin,Date end,String cityName,String diySiteCode,String username,
			int size,int page,List<String> roleDiyIds){
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdReceipt> c = new Criteria<TdReceipt>();
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

	public List<TdReceipt> queryDownList(Date begin, Date end, String cityName, String diySiteCode, String username,
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
		return repository.queryDownList(begin, end, cityName, diySiteCode, roleDiyIds, username);
	}
}
