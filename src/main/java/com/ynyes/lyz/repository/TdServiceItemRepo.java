package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdServiceItem;


/**
 * TdMallService 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdServiceItemRepo extends
		PagingAndSortingRepository<TdServiceItem, Long>,
		JpaSpecificationExecutor<TdServiceItem> 
{
    Page<TdServiceItem> findByTitleContainingOrderBySortIdAsc(String keywords, Pageable page);
    
    List<TdServiceItem> findByIsEnableTrue();
    List<TdServiceItem> findByIsEnableTrueOrderBySortIdAsc();
    /**
	 * @author lc
	 * @注释：商品服务
	 */
    List<TdServiceItem> findByIsEnableTrueAndIsGoodsServiceTrueOrderBySortIdAsc();
    List<TdServiceItem> findByIsEnableTrueAndIsGoodsServiceFalseOrderBySortIdAsc();
}
