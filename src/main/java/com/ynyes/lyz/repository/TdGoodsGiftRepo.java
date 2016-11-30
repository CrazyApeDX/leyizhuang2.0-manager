package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdGoodsGift;


/**
 * TdGoodsGift 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdGoodsGiftRepo extends
		PagingAndSortingRepository<TdGoodsGift, Long>,
		JpaSpecificationExecutor<TdGoodsGift> 
{
    
}
