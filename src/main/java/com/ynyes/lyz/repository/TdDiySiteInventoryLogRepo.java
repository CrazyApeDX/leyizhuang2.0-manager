package com.ynyes.lyz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdDiySiteInventoryLog;

public interface TdDiySiteInventoryLogRepo extends PagingAndSortingRepository<TdDiySiteInventoryLog, Long>,
		JpaSpecificationExecutor<TdDiySiteInventoryLog> {

	Page<TdDiySiteInventoryLog> findByDiySiteId(Long siteId,Pageable pageable);
	
	Page<TdDiySiteInventoryLog> findByDiySiteIdAndGoodsSkuContainingOrDiySiteIdAndGoodsTitleContainingOrderByIdAsc(Long siteId,String code,Long siteid,String title,Pageable pageable);

	Page<TdDiySiteInventoryLog> findByRegionIdAndDiySiteIdIsNullAndGoodsSkuContainingOrRegionIdAndDiySiteIdIsNullAndGoodsTitleContainingOrderByIdAsc(
			Long regionId, String keywords, Long regionId2, String keywords2, Pageable pageable);

	Page<TdDiySiteInventoryLog> findByRegionIdAndDiySiteIdIsNull(Long regionId, Pageable pageable);

	Page<TdDiySiteInventoryLog> findByRegionIdOrderByIdAsc(Long regionId, Pageable pageable);

	Page<TdDiySiteInventoryLog> findByRegionIdAndGoodsSkuContainingOrRegionIdAndGoodsTitleContainingOrderByIdAsc(
			Long regionId, String keywords, Long regionId2, String keywords2, Pageable pageable);

	Page<TdDiySiteInventoryLog> findByDiySiteIdIsNull(Pageable pageable);

	Page<TdDiySiteInventoryLog> findByDiySiteIdIsNullAndGoodsSkuContainingOrDiySiteIdIsNullAndGoodsTitleContainingOrderByIdAsc(
			String keywords, String keywords2, Pageable pageable);

	Page<TdDiySiteInventoryLog> findByGoodsSkuContainingOrGoodsTitleContainingOrderByIdAsc(String keywords,
			String keywords2, Pageable pageable);
}
