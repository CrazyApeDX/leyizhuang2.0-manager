package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdGeoInfo;

/**
 * TdGeoInfo 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdGeoInfoRepo
		extends PagingAndSortingRepository<TdGeoInfo, Long>, JpaSpecificationExecutor<TdGeoInfo> {

	List<TdGeoInfo> findByOpUserOrderByTimeDesc(String opUser);
}
