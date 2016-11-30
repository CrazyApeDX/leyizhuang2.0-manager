package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdUserSuggestionCategory;

public interface TdUserSuggestionCategoryRepo extends PagingAndSortingRepository<TdUserSuggestionCategory, Long>,
		JpaSpecificationExecutor<TdUserSuggestionCategory> {

	/**
	 * 查找所有能用的咨询分类并根据sortId正序排序
	 * 
	 * @author dengxiao
	 */
	List<TdUserSuggestionCategory> findByIsEnableTrueOrderBySortIdAsc();
}
