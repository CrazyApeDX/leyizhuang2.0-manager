package com.ynyes.lyz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdManagerDiySiteRole;
import com.ynyes.lyz.repository.TdDiySiteRepo;

/**
 * TdDiySite 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdDiySiteService {
	@Autowired
	TdDiySiteRepo repository;

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
	 * 删除
	 * 
	 * @param e
	 *            菜单项
	 */
	public void delete(TdDiySite e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdDiySite> entities) {
		if (null != entities) {
			repository.delete(entities);
		}
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            ID
	 * @return
	 */
	public TdDiySite findOne(Long id) {
		if (null == id) {
			return null;
		}

		return repository.findOne(id);
	}
	
	public TdDiySite findByStoreCode(String storeCode)
	{
		if (storeCode == null)
		{
			return null;
		}
		return repository.findByStoreCodeAndIsEnableTrue(storeCode);
	}

	/**
	 * 查找
	 * 
	 * @param ids
	 * @return
	 */
	public List<TdDiySite> findAll(Iterable<Long> ids) {
		return (List<TdDiySite>) repository.findAll(ids);
	}

	public List<TdDiySite> findAll(Sort sort) {

		return (List<TdDiySite>) repository.findAll(sort);
	}

	/**
	 * 根据城市id 找下属门店
	 * 
	 * @param regionId
	 * @author Mdj
	 */
	public List<TdDiySite> findByRegionIdAndIsEnableOrderBySortIdAsc(Long regionId) {
		return repository.findByRegionIdAndIsEnableTrueOrderBySortIdAsc(regionId);
	}

	public Page<TdDiySite> findByRegionIdAndIsEnableTrueOrderBySortIdAsc(Long regionId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByRegionIdAndIsEnableTrueOrderBySortIdAsc(regionId, pageRequest);
	}

	public Page<TdDiySite> findByCityIdAndIsEnableTrueOrderBySortIdAsc(Long cityId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByCityIdAndIsEnableTrueOrderBySortIdAsc(cityId, pageRequest);
	}

	public List<TdDiySite> findByIsEnableTrue() {
		return repository.findByIsEnableTrue();
	}

	public Page<TdDiySite> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}

	public Page<TdDiySite> searchAllOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByTitleContainingOrderBySortIdAsc(keywords, pageRequest);
	}
	
	public Page<TdDiySite> findByCustTypeName(String custTypeName, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByCustTypeNameAndTitleContainingOrderBySortIdAsc(custTypeName, keywords, pageRequest);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdDiySite save(TdDiySite e) {

		return repository.save(e);
	}

	public List<TdDiySite> save(List<TdDiySite> entities) {

		return (List<TdDiySite>) repository.save(entities);
	}

	/**
	 * 通过行政区划id查找其下属的所有门店
	 * 
	 * @author dengxiao
	 */
	public List<TdDiySite> findByDisctrictIdOrderBySortIdAsc(Long districtId) {
		if (null == districtId) {
			return null;
		}
		return repository.findByDisctrictIdOrderBySortIdAsc(districtId);
	}

	public TdDiySite findByTitleAndIsEnableTrue(Long regionId,String title) {
		if (null == title) {
			return null;
		}
		return repository.findByRegionIdAndTitleAndIsEnableTrue(regionId,title);
	}

	/**
	 * 根据regionId（对应城市的subIdCity字段，是城市的唯一标识）和门店名称查找门店
	 * 
	 * @author dengxiao
	 */
	public TdDiySite findByRegionIdAndTitleAndIsEnableTrue(Long regionId, String title) {
		if (null == regionId || null == title) {
			return null;
		}
		return repository.findByRegionIdAndTitleAndIsEnableTrue(regionId, title);
	}

	/**
	 * 根据regionId和customerId查找门店
	 * 
	 * @author dengxiao
	 */
	public TdDiySite findByRegionIdAndCustomerId(Long regionId, Long customerId) {
		if (null == regionId || null == customerId) {
			return null;
		}
		return repository.findByRegionIdAndCustomerId(regionId, customerId);
	}

	/**
	 * 根据regionId查找指定城市下的所有门店
	 * 
	 * @author dengxiao
	 */
	public List<TdDiySite> findByRegionIdOrderBySortIdAsc(Long regionId) {
		if (null == regionId) {
			return null;
		}
		return repository.findByRegionIdOrderBySortIdAsc(regionId);
	}

	/**
	 * 根据regionId和门店名称查找指定的门店（模糊查询）
	 * 
	 * @author DengXiao
	 */
	public List<TdDiySite> findByRegionIdAndTitleContainingOrderBySortIdAsc(Long regionId, String keywords) {
		if (null == regionId || null == keywords) {
			return null;
		}
		return repository.findByRegionIdAndTitleContainingAndIsEnableTrueOrderBySortIdAsc(regionId, keywords);
	}
	
	public List<TdDiySite> findAll(){
		return (List<TdDiySite>) repository.findAll();
	}
	
	public List<TdDiySite> findByCityId(Long cityId){
		if(null == cityId){
			return null;
		}
		return repository.findByCityId(cityId);
	}
	
	/**
	 * 获取用户管辖门店
	 * @param role 用户权限
	 * @return 结果集
	 * @author zp
	 */
	public List<TdDiySite> userRolediy(TdManagerDiySiteRole role){
		//返回结果集合
		List<TdDiySite> resDiyList=new ArrayList<TdDiySite>();
		//判断空值
		if(role != null){
			//获取所有启用门店
			List<TdDiySite> diyList=repository.findByIsEnableTrue();
			//超级管理员还回所有启用门店
			if(role.getIsSys()){
				resDiyList=diyList;
			}else{
				//循环所有城市 
				for (TdDiySite diy : diyList) {
					//判断是否拥有当前门店的管理权限
					if(role.getDiySiteTree()!=null &&role.getDiySiteTree().contains("["+diy.getId()+"]")){
						resDiyList.add(diy);
					}
				}
			}
		}
		return resDiyList;
	}
	
	public List<TdDiySite> findByRegionIdAndStatusAndIsEnableTrue(Long regionId, Long status) {
		if (null == regionId || null == status) {
			return null;
		}
		return repository.findByRegionIdAndStatusAndIsEnableTrue(regionId, status);
	}
}
