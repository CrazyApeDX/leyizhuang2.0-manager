package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdActiveRedPacket;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdPhotoOrderInfo;
import com.ynyes.lyz.repository.TdPhotoOrderInfoRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;





/**
 * 拍照下单 服务类
 * 
 * @author panjie
 *
 */

@Service
@Transactional
public class TdPhotoOrderInfoService {

	@Autowired
	private TdPhotoOrderInfoRepo repository;

	/**
	 * 删除
	 * 
	 * @param id
	 *            菜单项ID
	 */
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}
	
	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdPhotoOrderInfo save(TdPhotoOrderInfo e) {

		return repository.save(e);
	}

	/**
	 * 查询所有的订单
	 * @param page
	 * @param size
	 * @param keywords
	 * @return
	 */
	public Page<TdPhotoOrderInfo> findAllAddCondition(int page, int size,String keywords,String status,String cityInfo) {
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "createTime"));
		Criteria<TdPhotoOrderInfo> c = new Criteria<TdPhotoOrderInfo>();
		
		if(null != keywords && !keywords.equalsIgnoreCase("")){
			c.add(Restrictions.or(Restrictions.like("username", keywords, true),Restrictions.like("userRealName", keywords, true)
					,Restrictions.like("orderNumber", keywords, true)));
		}
		if(null != status && !status.equalsIgnoreCase("")){
			
			c.add(Restrictions.eq("status", TdPhotoOrderInfo.Status.valueOf(status), true));
		}
		if(null != cityInfo && !cityInfo.equalsIgnoreCase("")){
			
			c.add(Restrictions.eq("city", cityInfo, true));
		}
		return repository.findAll(c, pageRequest);
	}
	
	/**
	 * 根据id查询订单
	 */
	public TdPhotoOrderInfo findOne(Long id){
		if(null != id){
			return repository.findOne(id);
		}
		return null;
	}
	
}

