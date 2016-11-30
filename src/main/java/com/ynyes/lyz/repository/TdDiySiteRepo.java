package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdDiySite;

/**
 * TdDiySite 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdDiySiteRepo
		extends PagingAndSortingRepository<TdDiySite, Long>, JpaSpecificationExecutor<TdDiySite> {
	Page<TdDiySite> findByTitleContainingOrderBySortIdAsc(String keywords, Pageable page);

	List<TdDiySite> findByIsEnableTrue();

	/**
	 * 根据storeCode查找
	 * @param storeCode
	 * @return
	 */
	TdDiySite findByStoreCode(String storeCode);
	
	TdDiySite findByStoreCodeAndIsEnableTrue(String storeCode);
	
	/**
	 * 通过行政区划id查找其下属的所有门店
	 * 
	 * @author dengxiao
	 */
	List<TdDiySite> findByDisctrictIdOrderBySortIdAsc(Long districtId);

	/**
	 * 根据城市id 找下属门店
	 * 
	 * @param regionId
	 * @author Mdj
	 */
	List<TdDiySite> findByRegionIdAndIsEnableTrueOrderBySortIdAsc(Long regionId);

	Page<TdDiySite> findByRegionIdAndIsEnableTrueOrderBySortIdAsc(Long regionId, Pageable page);

	Page<TdDiySite> findByCityIdAndIsEnableTrueOrderBySortIdAsc(Long cityId, Pageable page);
    //  这个方法有问题 不使用
	TdDiySite findByTitleAndIsEnableTrue(String title);

	/**
	 * 根据客户id查找用户的归属门店
	 * 
	 * @author dengxiao
	 */
	TdDiySite findByCustomerId(Long customerId);

	/**
	 * 根据regionId（对应城市的subIdCity字段，是城市的唯一标识）和门店名称查找门店
	 * 
	 * @author dengxiao
	 */
	TdDiySite findByRegionIdAndTitleAndIsEnableTrue(Long regionId, String title);

	/**
	 * 根据regionId和customerId查找门店
	 * 
	 * @author dengxiao
	 */
	TdDiySite findByRegionIdAndCustomerId(Long regionId, Long customerId);

	/**
	 * 根据regionId查找指定城市下的所有门店
	 * 
	 * @author dengxiao
	 */
	List<TdDiySite> findByRegionIdOrderBySortIdAsc(Long regionId);

	/**
	 * 根据regionId和门店名称查找指定的门店（模糊查询）
	 * 
	 * @author DengXiao
	 */
	List<TdDiySite> findByRegionIdAndTitleContainingAndIsEnableTrueOrderBySortIdAsc(Long regionId, String keywords);
	
	/**
	 * 根据城市id查询城市下面的所有门店
	 */
	List<TdDiySite> findByCityId(Long cityId);
	
	List<TdDiySite> findByRegionIdAndStatusAndIsEnableTrue(Long regionId, Long status);
}
