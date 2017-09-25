package com.ynyes.lyz.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdManagerDiySiteRole;
import com.ynyes.lyz.repository.TdCityRepo;

@Service
@Transactional
public class TdCityService {

	@Autowired
	private TdCityRepo repository;

	public TdCity save(TdCity entity) {
		if (null == entity) {
			return null;
		}
		return repository.save(entity);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdCity findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdCity> findAll() {
		return (List<TdCity>) repository.findAll();
	}

	/**
	 * 根据城市名称查询到地区实体
	 * 
	 * @author dengxiao
	 */
	public TdCity findByCityName(String cityName) {
		if (null == cityName) {
			return null;
		}
		return repository.findByCityName(cityName);
	}

	public Page<TdCity> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "sortId"));

		return repository.findAll(pageRequest);
	}

	public TdCity findBySobIdCity(Long sobIdCity) {
		if (null == sobIdCity) {
			return null;
		}
		return repository.findBySobIdCity(sobIdCity);
	}
	
	/**
	 * 获取用户管辖城市
	 * @param role 用户权限
	 * @return 结果集
	 * @author zp
	 */
	public List<TdCity> userRoleCity(TdManagerDiySiteRole role){
		//返回结果集合
		List<TdCity> resCityList=new ArrayList<TdCity>();
		//判断空值
		if(role != null){
			//获取所有城市
			List<TdCity> cityList=(List<TdCity>) repository.findAll();
			//超级管理员还回所有城市
			if(role.getIsSys()){
				resCityList=cityList;
			}else{
				//循环所有城市 
				for (TdCity tdCity : cityList) {
					//判断是否拥有当前城市的管理权限
					if(role.getCityTree()!=null &&role.getCityTree().contains("["+tdCity.getId()+"]")){
						resCityList.add(tdCity);
					}
				}
			}
		}
		return resCityList;
	}
}
