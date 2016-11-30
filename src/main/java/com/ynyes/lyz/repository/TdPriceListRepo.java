package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdPriceList;

public interface TdPriceListRepo extends
		PagingAndSortingRepository<TdPriceList, Long>,
		JpaSpecificationExecutor<TdPriceList> {

	Page<TdPriceList> findByNameContaining(String keywords, Pageable page);

	TdPriceList findByListHeaderId(Long listHeaderId);

	TdPriceList findBysobIdAndPriceType(Long sobId, String priceType);

	List<TdPriceList> findBySobId(Long sobId);

	List<TdPriceList> findByCityIdAndPriceTypeAndStartDateActiveBeforeAndEndDateActiveIsNullOrCityIdAndPriceTypeAndStartDateActiveBeforeAndEndDateActiveAfterOrCityIdAndPriceTypeAndStartDateActiveIsNull(
			Long sobId, String priceType, Date start, Long sobId2, String priceType2, Date start2, Date end2,Long sobId3,String priceType3);

	/**
	 * 根据priceType（价目表类型）和cityId查找未过期且可用的价目表
	 * 
	 * @author dengxiao
	 */
	List<TdPriceList> findByPriceTypeAndCityIdAndStartDateActiveBeforeAndEndDateActiveAfterAndActiveFlag(
			String priceType, Long cityId, Date begin, Date finish, String flag);
	/**
	 * 根据城市编号查询价目表头
	 */
	List<TdPriceList> findByCityId(Long cityId);

}
