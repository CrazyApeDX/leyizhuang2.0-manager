package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdGathering;
import com.ynyes.lyz.repository.TdGatheringRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;

/**
 * TdOrder 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdGatheringService {
	@Autowired
	TdGatheringRepo repository;

	
	/**
	 * 根据时间,城市,门店查询收款报表
	 * 增加门店id查询
	 * @return
	 */
	public List<TdGathering> searchGathering(Date begin,Date end,String cityName,String diySiteCode,String username,List<String> roleDiyIds){
		Criteria<TdGathering> c = new Criteria<TdGathering>();
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
	public void callInsertGathering(Date start,Date end,String username){
		repository.callInsertGathering(start,end,username);
	}
	
	/**
	 * 根据时间,城市,门店查询收款报表
	 * 增加门店id查询
	 * @return
	 */
	public Page<TdGathering> searchList(String keywords,Date begin,Date end,String cityName,String diySiteCode,String username,
			int size,int page,List<String> roleDiyIds){
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdGathering> c = new Criteria<TdGathering>();
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
}
