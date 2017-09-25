package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdActivityGift;

public interface TdActivityGiftRepo
		extends PagingAndSortingRepository<TdActivityGift, Long>, JpaSpecificationExecutor<TdActivityGift> {

	/**
	 * 根据商品类别的id获取当前能够参与的小辅料赠送活动（未过期）
	 * 
	 * @author dengxiao
	 */
	List<TdActivityGift> findByCategoryIdAndIsUseableTrueAndBeginTimeBeforeAndEndTimeAfterAndGiftTypeOrderBySortIdAsc(
			Long categoryId, Date date1, Date date2, Long giftType);
}
