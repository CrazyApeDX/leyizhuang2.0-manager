package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdDiySiteInventory;

public interface TdDiySiteInventoryRepo
		extends PagingAndSortingRepository<TdDiySiteInventory, Long>, JpaSpecificationExecutor<TdDiySiteInventory> {

	TdDiySiteInventory findByGoodsIdAndDiySiteId(Long goodsId, Long siteId);
	
	TdDiySiteInventory findByGoodsCodeAndDiySiteId(String goodsCode, Long siteId);

	@Query("select t.goodsId from TdDiySiteInventory t where t.diySiteId = ?1")
	List<Long> findGoodsIdByDiySiteId(Long siteId);

	TdDiySiteInventory findBygoodsCodeAndDiySiteIdNull(String goodsCode);

	Page<TdDiySiteInventory> findByGoodsCodeContainingOrGoodsTitleContainingOrderByIdAsc(String code, String title,
			Pageable pageable);

	Page<TdDiySiteInventory> findByRegionIdAndGoodsCodeContainingOrRegionIdAndGoodsTitleContainingOrderByIdAsc(
			Long regionId, String code, Long regionid, String title, Pageable pageable);

	Page<TdDiySiteInventory> findByDiySiteIdAndGoodsCodeContainingOrDiySiteIdAndGoodsTitleContainingOrderByIdAsc(
			Long siteId, String code, Long siteid, String title, Pageable pageable);

	List<TdDiySiteInventory> findByDiySiteId(Long siteId);

	Page<TdDiySiteInventory> findByDiySiteId(Long siteId, Pageable pageable);

	Page<TdDiySiteInventory> findByRegionIdOrderByIdAsc(Long regionId, Pageable pageable);

	Page<TdDiySiteInventory> findByRegionIdAndDiySiteIdIsNull(Long regionId, Pageable pageable);

	Page<TdDiySiteInventory> findByRegionIdAndDiySiteIdIsNullAndGoodsCodeContainingOrRegionIdAndDiySiteIdIsNullAndGoodsTitleContainingOrderByIdAsc(
			Long regionId, String code, Long regionid, String title, Pageable pageable);

	TdDiySiteInventory findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(String goodsCode, Long regionId);
	
	TdDiySiteInventory findByGoodsIdAndRegionIdAndDiySiteIdIsNull(Long  goodsId, Long regionId);

	Page<TdDiySiteInventory> findByDiySiteIdIsNullAndGoodsCodeContainingOrDiySiteIdIsNullAndGoodsTitleContainingOrderByIdAsc(
			String code, String title, Pageable pageable);

	Page<TdDiySiteInventory> findByDiySiteIdIsNull(Pageable pageable);

	@Query("select t.goodsId from TdDiySiteInventory t where t.regionId = ?1 and t.diySiteId is null")
	List<Long> findGoodsIdByRegionIdAndDiySiteIdIsNull(Long regionId);
	
	@Modifying
	@Query("update TdDiySiteInventory o set o.inventory = o.inventory + ?3 where o.diySiteId = ?1 and o.goodsId = ?2")
	int updateInventory(Long diySiteId, Long goodId, Long inventory);
}
