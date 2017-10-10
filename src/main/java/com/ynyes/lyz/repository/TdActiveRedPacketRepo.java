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
	 * ���ݳ���id�͵�ǰʱ�� ,��ѯ �ʱ��δ���ڵĺ�� 
	 * @param cityId
	 * @param date1
	 * @param date2
	 * @return
	 */
	List<TdActiveRedPacket> findByCityNameEqualsAndBeginDateBeforeAndFinishDateAfterOrderBySortIdAsc
	(String name,Date date1, Date date2);
	
	/**
	 * ���ݳ���id�͵�ǰʱ�� ,��ѯ ʹ��ʱ��δ���ڵĺ�� 
	 * @param cityId
	 * @param date1
	 * @param date2
	 * @return
	 */
	List<TdActiveRedPacket> findByCityNameEqualsAndUseBeginDateBeforeAndUseFinishDateAfterOrderBySortIdAsc
	(String name,Date date1, Date date2);
}
