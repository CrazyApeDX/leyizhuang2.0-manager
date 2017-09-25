package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdMessage;

public interface TdMessageRepo
		extends PagingAndSortingRepository<TdMessage, Long>, JpaSpecificationExecutor<TdMessage> {

	// 根据消息类型和用户id查找消息
	List<TdMessage> findByTypeIdAndUserIdOrderByCreateTimeDesc(Long typeId, Long userId);

	// 根据消息类型和用户id查找未读的消息
	List<TdMessage> findByTypeIdAndUserIdAndIsReadFalseOrderByCreateTimeDesc(Long typeId, Long userId);
}
