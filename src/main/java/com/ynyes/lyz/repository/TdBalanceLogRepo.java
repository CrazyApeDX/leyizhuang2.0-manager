package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdBalanceLog;

public interface TdBalanceLogRepo
		extends PagingAndSortingRepository<TdBalanceLog, Long>, JpaSpecificationExecutor<TdBalanceLog> {

	/**
	 * 查找指定用户的钱包操作记录（按照生成时间倒序排序）
	 * 
	 * @author dengxiao
	 */
	List<TdBalanceLog> findByUserIdOrderByCreateTimeDesc(Long userId);

	/**
	 * 查找指定用户的钱包操作记录——分页（按照生成时间倒序排序）
	 * 
	 * @author dengxiao
	 */
	Page<TdBalanceLog> findByUserIdAndIsSuccessTrueOrderByCreateTimeDesc(Long userId, Pageable pageRequest);
	
	Page<TdBalanceLog> findByUserIdAndIsSuccessTrueOrderByIdDesc(Long userId, Pageable pageRequest);

}
