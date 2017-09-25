package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdCity;

public interface TdCityRepo extends PagingAndSortingRepository<TdCity, Long>, JpaSpecificationExecutor<TdCity> {

	// 根据城市名称查询城市实体
	TdCity findByCityName(String cityName);

	// 根据用户的cityId查找城市实体
	TdCity findBySobIdCity(Long sobIdCity);
}
