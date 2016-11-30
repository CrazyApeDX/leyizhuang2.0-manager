package com.ynyes.lyz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdBrand;

public interface TdBrandRepo extends PagingAndSortingRepository<TdBrand, Long>, JpaSpecificationExecutor<TdBrand> {

	/**
	 * 根据关键词查找品牌（分页）
	 * 
	 * @author dengxiao
	 */
	Page<TdBrand> findByTitleContainingAndShortNameContainingOrderBySortIdAsc(String keywords1, String keywords2,
			Pageable page);

	TdBrand findByShortName(String shortName);
	
	TdBrand findByTitle(String title);
}
