package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdUserSuggestion;

public interface TdUserSuggestionRepo
		extends PagingAndSortingRepository<TdUserSuggestion, Long>, JpaSpecificationExecutor<TdUserSuggestion> {
	// 001
	Page<TdUserSuggestion> findByCategoryId(Long categoryId, Pageable pageable);

	// 0010
	Page<TdUserSuggestion> findByCreateTimeBefore(Date date2, Pageable pageable);

	// 0011
	Page<TdUserSuggestion> findByCreateTimeBeforeAndCategoryId(Date date2, Long categoryId, Pageable pageable);

	// 0100
	Page<TdUserSuggestion> findByCreateTimeAfter(Date date2, Pageable pageable);

	// 0101
	Page<TdUserSuggestion> findByCreateTimeAfterAndCategoryId(Date date2, Long categoryId, Pageable pageable);

	// 0110
	Page<TdUserSuggestion> findByCreateTimeAfterAndCreateTimeBefore(Date date1, Date date2, Pageable pageable);

	// 0111
	Page<TdUserSuggestion> findByCreateTimeAfterAndCreateTimeBeforeAndCategoryId(Date date1, Date date2,
			Long categoryId, Pageable pageable);

	// 1000
	Page<TdUserSuggestion> findByContentContaining(String keywords, Pageable pageable);

	// 1001
	Page<TdUserSuggestion> findByCategoryIdAndContentContaining(Long categoryId, String keywords, Pageable pageable);

	// 1010
	Page<TdUserSuggestion> findByCreateTimeBeforeAndContentContaining(Date date2, String keywords, Pageable pageable);

	// 1011
	Page<TdUserSuggestion> findByCreateTimeBeforeAndCategoryIdAndContentContaining(Date date2, Long categoryId,
			String keywords, Pageable pageable);

	// 1100
	Page<TdUserSuggestion> findByCreateTimeAfterAndContentContaining(Date date1, String keywords, Pageable pageable);

	// 1101
	Page<TdUserSuggestion> findByCreateTimeAfterAndCategoryIdAndContentContaining(Date date1, Long categoryId,
			String keywords, Pageable pageable);

	// 1110
	Page<TdUserSuggestion> findByCreateTimeAfterAndCreateTimeBeforeAndContentContaining(Date date1, Date date2,
			String keywords, Pageable pageable);

	// 1111
	Page<TdUserSuggestion> findByCreateTimeAfterAndCreateTimeBeforeAndCategoryIdAndContentContaining(Date date1,
			Date date2, Long categoryId, String keywords, Pageable pageable);

	// 根据用户id查找其下所有的咨询投诉（未删除的）
	List<TdUserSuggestion> findByUserIdAndParentIdAndIsDeleteFalseOrderByCreateTimeDesc(Long userId, Long parentId);
}
