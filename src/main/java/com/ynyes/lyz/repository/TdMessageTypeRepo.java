package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdMessageType;

public interface TdMessageTypeRepo
		extends PagingAndSortingRepository<TdMessageType, Long>, JpaSpecificationExecutor<TdMessageType> {

	// 查找所有能使用的消息类型
	List<TdMessageType> findByIsEnableTrueOrderBySortIdAsc();

}
