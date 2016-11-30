package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdBalanceLog;
import com.ynyes.lyz.repository.TdBalanceLogRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;

@Service
@Transactional
public class TdBalanceLogService {

	@Autowired
	private TdBalanceLogRepo repository;

	public TdBalanceLog save(TdBalanceLog e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdBalanceLog findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdBalanceLog> findAll() {
		return (List<TdBalanceLog>) repository.findAll();
	}

	/**
	 * 查找指定用户的钱包操作记录（按照生成时间倒序排序）
	 * 
	 * @author dengxiao
	 */
	public List<TdBalanceLog> findByUserIdOrderByCreateTimeDesc(Long userId) {
		if (null == userId) {
			return null;
		}
		return repository.findByUserIdOrderByCreateTimeDesc(userId);
	}

	/**
	 * 查找指定用户的钱包操作记录——分页（按照生成时间倒序排序）
	 * 
	 * @author dengxiao
	 */
	public Page<TdBalanceLog> findByUserIdAndIsSuccessTrueOrderByCreateTimeDesc(Long userId, int page, int size) {
		if (null == userId) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUserIdAndIsSuccessTrueOrderByCreateTimeDesc(userId, pageRequest);
	}
	
	public Page<TdBalanceLog> findByUserIdAndIsSuccessTrueOrderByIdDesc(Long userId, int page, int size) {
		if (null == userId) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUserIdAndIsSuccessTrueOrderByIdDesc(userId, pageRequest);
	}

	public Page<TdBalanceLog> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "finishTime"));
		return repository.findAll(pageRequest);
	}
	
	/**
	 * 预存款变更记录查询(分页)
	 * @param keywords 关键字
	 * @param roleDiyIds 权限门店
	 * @param type 类型
	 * @param page 分页
	 * @param size 分页
	 * @param cityCode 城市
	 * @param diyCode 门店
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public Page<TdBalanceLog> searchList(String keywords,List<Long> roleDiyIds,Long type, int page, int size,Long cityCode,Long diyCode,String startTime,
			String endTime){
		Sort sort = new Sort(Sort.Direction.DESC, "createTime").and(new Sort(Sort.Direction.DESC, "id"));
		PageRequest pageRequest = new PageRequest(page, size,sort);
		Criteria<TdBalanceLog> c = new Criteria<TdBalanceLog>();
		//用户名
		if (StringUtils.isNotBlank(keywords)) {
			c.add(Restrictions.or(Restrictions.like("orderNumber",keywords, true),Restrictions.like("username", keywords, true)));
		}
		if(roleDiyIds!=null && roleDiyIds.size()>0){
			c.add(Restrictions.in("diySiteId", roleDiyIds, true));
		}
		if (type!=null) {
			c.add(Restrictions.eq("type", type, true));
		}
		if(diyCode!=null){
			c.add(Restrictions.eq("diySiteId", diyCode, true));
		}
		if(cityCode!=null){
			c.add(Restrictions.eq("cityId", cityCode, true));
		}
		if (StringUtils.isNotBlank(startTime)) {
			c.add(Restrictions.gte("createTime", com.ynyes.lyz.util.StringUtils.stringToDate(startTime, null), true));

		}
		if (StringUtils.isNotBlank(endTime)) {
			c.add(Restrictions.lte("createTime", com.ynyes.lyz.util.StringUtils.stringToDate(endTime, null), true));
		}
//		c.add(Restrictions.ne("type", 2L, true));
		c.add(Restrictions.eq("isSuccess", true, true));
		c.setOrderByDesc("createTime");
		return repository.findAll(c,pageRequest);
	}
	
	/**
	 * 预存款变更记录查询
	 * @param keywords 关键字
	 * @param roleDiyIds 权限门店
	 * @param type 类型
	 * @param cityCode 城市
	 * @param diyCode 门店
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public List<TdBalanceLog> searchList(String keywords,List<Long> roleDiyIds,Long type,Long cityCode,Long diyCode,String startTime,
			String endTime){
		Criteria<TdBalanceLog> c = new Criteria<TdBalanceLog>();
		//用户名
		if (StringUtils.isNotBlank(keywords)) {
			c.add(Restrictions.or(Restrictions.like("orderNumber",keywords, true),Restrictions.like("username", keywords, true)));
		}
		if(roleDiyIds!=null && roleDiyIds.size()>0){
			c.add(Restrictions.in("diySiteId", roleDiyIds, true));
		}
		if (type!=null) {
			c.add(Restrictions.eq("type", type, true));
		}
		if(diyCode!=null){
			c.add(Restrictions.eq("diySiteId", diyCode, true));
		}
		if(cityCode!=null){
			c.add(Restrictions.eq("cityId", cityCode, true));
		}
		if (StringUtils.isNotBlank(startTime)) {
			c.add(Restrictions.gte("createTime", com.ynyes.lyz.util.StringUtils.stringToDate(startTime, null), true));

		}
		if (StringUtils.isNotBlank(endTime)) {
			c.add(Restrictions.lte("createTime", com.ynyes.lyz.util.StringUtils.stringToDate(endTime, null), true));
		}
//		c.add(Restrictions.ne("type", 2L, true));
		c.add(Restrictions.eq("isSuccess", true, true));
		c.setOrderByDesc("id");
		return repository.findAll(c);
	}
}
