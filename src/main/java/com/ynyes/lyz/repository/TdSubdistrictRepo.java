package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdSubdistrict;

public interface TdSubdistrictRepo
		extends PagingAndSortingRepository<TdSubdistrict, Long>, JpaSpecificationExecutor<TdSubdistrict> {

	/**
	 * 根据所属区划id查找街道，并按照排序号正序排序
	 * 
	 * @author dengxiao
	 */
	List<TdSubdistrict> findByDistrictIdOrderBySortIdAsc(Long districtId);

	/**
	 * 根据行政区划名称和行政街道名称查找行政街道
	 * 
	 * @author dengxiao
	 */
	List<TdSubdistrict> findByDistrictNameAndNameOrderBySortIdAsc(String districtName, String subDistrictName);
}
