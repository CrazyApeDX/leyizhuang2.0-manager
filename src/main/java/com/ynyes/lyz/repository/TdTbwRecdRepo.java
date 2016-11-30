package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdTbwRecd;

/**
 * TdAd 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdTbwRecdRepo extends PagingAndSortingRepository<TdTbwRecd, Long>, JpaSpecificationExecutor<TdTbwRecd> 
{
	List<TdTbwRecd> findByRecNo(String recNo);
}
