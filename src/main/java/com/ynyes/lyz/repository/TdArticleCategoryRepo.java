package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdArticleCategory;

/**
 * TdArticleCategory 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdArticleCategoryRepo
		extends PagingAndSortingRepository<TdArticleCategory, Long>, JpaSpecificationExecutor<TdArticleCategory> {
	// 通过父类型查找
	List<TdArticleCategory> findByParentIdOrderBySortIdAsc(Long parentId);

	// 通过菜单ID查找
	List<TdArticleCategory> findByMenuIdOrderBySortIdAsc(Long menuId);

	// 通过菜单ID和父ID查找
	List<TdArticleCategory> findByMenuIdAndParentIdOrderBySortIdAsc(Long menuId, Long parentId);

	// 通过频道ID查找
	List<TdArticleCategory> findByChannelIdOrderBySortIdAsc(Long cid);

	// 通过频道ID和父ID查找
	List<TdArticleCategory> findByChannelIdAndParentIdOrderBySortIdAsc(Long cid, Long parentId);

	// 通过标题查找消息分类
	TdArticleCategory findByTitle(String title);
}
