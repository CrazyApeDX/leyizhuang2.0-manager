package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdPayType;

/**
 * TdPayType 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdPayTypeRepo
		extends PagingAndSortingRepository<TdPayType, Long>, JpaSpecificationExecutor<TdPayType> {
	Page<TdPayType> findByTitleContainingOrderBySortIdAsc(String keywords, Pageable page);

	List<TdPayType> findByIsEnableTrue();

	/**
	 * 根据名称查找支付方式
	 * 
	 * @author dengxiao
	 */
	TdPayType findByTitleAndIsEnableTrue(String title);

	/**
	 * 查找所有在线支付的支付方式，按照排序号（sortId）正序排序
	 * 
	 * @author dengxiao
	 */
	List<TdPayType> findByIsOnlinePayTrueAndIsEnableTrueOrderBySortIdAsc();

}
