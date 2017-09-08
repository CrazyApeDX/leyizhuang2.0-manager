package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdActiveRedPacket;
import com.ynyes.lyz.entity.TdActivity;

public interface TdActiveRedPacketRepo 
 				extends PagingAndSortingRepository<TdActiveRedPacket, Long>, JpaSpecificationExecutor<TdActiveRedPacket>  {
	/**
	 * 根据城市id和当前时间 ,查询 活动时间未过期的红包 
	 * @param cityId
	 * @param date1
	 * @param date2
	 * @return
	 */
	List<TdActiveRedPacket> findByCityNameEqualsAndBeginDateBeforeAndFinishDateAfterOrderBySortIdAsc
	(String name,Date date1, Date date2);
	
	/**
	 * 根据城市id和当前时间 ,查询 使用时间未过期的红包 
	 * @param cityId
	 * @param date1
	 * @param date2
	 * @return
	 */
	List<TdActiveRedPacket> findByCityNameEqualsAndUseBeginDateBeforeAndUseFinishDateAfterOrderBySortIdAsc
	(String name,Date date1, Date date2);
}
