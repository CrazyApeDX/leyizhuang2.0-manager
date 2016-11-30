package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdDistrict;

public interface TdDistrictRepo
		extends PagingAndSortingRepository<TdDistrict, Long>, JpaSpecificationExecutor<TdDistrict> {
	/**
	 * 根据所属城市id查找行政区划，并根据排序号正序排序
	 * 
	 * @author dengxiao
	 */
	List<TdDistrict> findByCityIdOrderBySortIdAsc(Long cityId);
}
