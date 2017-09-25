package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdCategoryLimit;

public interface TdCategoryLimitRepo
		extends PagingAndSortingRepository<TdCategoryLimit, Long>, JpaSpecificationExecutor<TdCategoryLimit> {

	/**
	 * 查找指定城市id的限购信息
	 * 
	 * @author DengXiao
	 */
	List<TdCategoryLimit> findBySobId(Long sobId);

	/**
	 * 查找指定城市id下的一级分类
	 * 
	 * @author DengXiao
	 */
	List<TdCategoryLimit> findBySobIdAndParentIdIsNullOrderBySortIdAsc(Long sobId);

	/**
	 * 根据城市sobid和分类父id查找指定的分类
	 * 
	 * @author DengXiao
	 */
	List<TdCategoryLimit> findBySobIdAndParentIdOrderBySortIdAsc(Long sobId, Long parentId);
	/**
	 * 根据分类名称模糊查询
	 * @author zp
	 */
	List<TdCategoryLimit> findByTitleContaining(String title);
}
