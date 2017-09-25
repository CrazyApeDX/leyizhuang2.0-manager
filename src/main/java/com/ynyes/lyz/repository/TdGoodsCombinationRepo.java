package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdGoodsCombination;


/**
 * TdGoodsCombination 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdGoodsCombinationRepo extends
		PagingAndSortingRepository<TdGoodsCombination, Long>,
		JpaSpecificationExecutor<TdGoodsCombination> 
{
    List<TdGoodsCombination> findByGoodsId(Long goodsId);
}
