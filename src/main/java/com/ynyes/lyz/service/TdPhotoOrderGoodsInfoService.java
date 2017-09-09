package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdPhotoOrderGoodsInfo;
import com.ynyes.lyz.repository.TdPhotoOrderGoodsInfoRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;





/**
 * 拍照下单商品 服务类
 * 
 * @author panjie
 *
 */

@Service
@Transactional
public class TdPhotoOrderGoodsInfoService {

	@Autowired
	private TdPhotoOrderGoodsInfoRepo repository;

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
	public TdPhotoOrderGoodsInfo save(TdPhotoOrderGoodsInfo e) {

		return repository.save(e);
	}

	/**
	 * 查询所有的订单
	 * @param page
	 * @param size
	 * @param keywords
	 * @return
	 */
	public Page<TdPhotoOrderGoodsInfo> findAllAddCondition(int page, int size,String keywords) {
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "createTime"));
		Criteria<TdPhotoOrderGoodsInfo> c = new Criteria<TdPhotoOrderGoodsInfo>();
		
		if(null != keywords && !keywords.equalsIgnoreCase("")){
			c.add(Restrictions.or(Restrictions.like("username", keywords, true),Restrictions.like("userRealName", keywords, true)));
		}
		
		return repository.findAll(c, pageRequest);
	}
	
	/**
	 * 根据id查询订单
	 */
	public TdPhotoOrderGoodsInfo findOne(Long id){
		if(null != id){
			return repository.findOne(id);
		}
		return null;
	}
	
	/**
	 * 根据photoOrderId查询
	 */
	public List<TdPhotoOrderGoodsInfo> findByPhotoOrderIdEquals(Long id){
		if(null != id){
			return repository.findByPhotoOrderIdEquals(id);
		}
		return null;
	}
}

