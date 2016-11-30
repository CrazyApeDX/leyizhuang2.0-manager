package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdProductCategory;

/**
 * TdProductCategory 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdProductCategoryRepo
		extends PagingAndSortingRepository<TdProductCategory, Long>, JpaSpecificationExecutor<TdProductCategory> {
	List<TdProductCategory> findByParentIdIsNullOrderBySortIdAsc();

	List<TdProductCategory> findByParentIdNotNullOrderBySortIdAsc();

	List<TdProductCategory> findByParentIdOrderBySortIdAsc(Long parentId);

	TdProductCategory findByTitle(String title);

	TdProductCategory findTopByTitleContaining(String title);

	TdProductCategory findByTitleAndIdNot(String title, Long id);

	/**
	 * 查找指定id范围内的一级分类
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午10:57:35
	 */
	List<TdProductCategory> findByParentIdIsNullAndIdInOrderBySortIdAsc(List<Long> ids);

	/**
	 * 查找指定id范围下所有二级分类的父类id
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午11:25:12
	 */
	@Query("select c.parentId from TdProductCategory c where c.id in ?1 group by c.parentId order by c.sortId asc")
	List<Long> findParentIdAndIdIn(List<Long> ids);

	/**
	 * 根据指定的父类在一定id范围内查找子类
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午11:09:45
	 */
	List<TdProductCategory> findByParentIdAndIdInOrderBySortIdAsc(Long parentId, List<Long> ids);

	/**
	 * 查找一定范围下所有的分类（不重复）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午11:29:53
	 */
	@Query("select c from TdProductCategory c where c.id in ?1 group by c.id order by c.sortId asc")
	List<TdProductCategory> findByIdInGroupByIdOrderBySortIdAsc(List<Long> ids);
	
	/**
	 * 查找指定父类下面的子类 
	 * @author zp
	 */
	TdProductCategory findByTitleAndParentId(String title,Long parentId);
}
