package com.ynyes.lyz.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ynyes.lyz.entity.TdDiySitePrice;

/**
 * @title 门店商品价格中间表类
 * @describe
 * @author Generation Road
 * @date 2017年5月8日
 */
public interface TdDiySitePriceRepo extends JpaRepository<TdDiySitePrice, Serializable> {


	TdDiySitePrice findByAssignId(Long assignId);

	@Query("select t from TdDiySitePrice t where t.storeCode = :storeCode and ("
			+ "(t.startDateActive < :now and t.endDateActive > :now) or "
			+ "(t.startDateActive is null and t.endDateActive < :now) or"
			+ "(t.startDateActive < :now and t.endDateActive is null) or"
			+ "(t.startDateActive is null and t.endDateActive is null)" + ")")
	List<TdDiySitePrice> findByStoreCodeAndStartDateActiveBeforeAndEndDateActiveAfter(
			@Param("storeCode") String storeCode, @Param("now") Date now);

	/**
	 * @title 根据分公司ID sobId、门店编码storeCode和门店类型custTypeCode查询未过期的价目表
	 * @describe
	 * @author Generation Road
	 * @date 2017年5月8日
	 * @param sobId
	 * @param storeCode
	 * @param custTypeCode
	 * @param start
	 * @param end
	 * @return
	 */
	List<TdDiySitePrice> findBySobIdAndStoreCodeAndPriceTypeAndStartDateActiveBeforeAndEndDateActiveIsNullOrSobIdAndStoreCodeAndPriceTypeAndStartDateActiveBeforeAndEndDateActiveAfterOrSobIdAndStoreCodeAndPriceTypeAndStartDateActiveIsNull(
			Long sobId, String storeCode, String priceType, Date start, Long sobId2, String storeCode2,
			String priceType2, Date start2, Date end, Long sobId3, String storeCode3, String priceType3);

}
