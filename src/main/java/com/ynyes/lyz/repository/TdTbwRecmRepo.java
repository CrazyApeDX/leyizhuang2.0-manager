package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdTbwRecm;

/**
 * TdAd 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdTbwRecmRepo extends PagingAndSortingRepository<TdTbwRecm, Long>, JpaSpecificationExecutor<TdTbwRecm>
{
	TdTbwRecm findByCRecNo(String cRecNo);
}
