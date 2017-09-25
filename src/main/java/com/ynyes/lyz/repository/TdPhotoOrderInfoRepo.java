package com.ynyes.lyz.repository;



import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdPhotoOrderInfo;



/**
 *  拍照下单实体数据接口
 * 
 * @author panjie
 *
 */

public interface TdPhotoOrderInfoRepo extends
		PagingAndSortingRepository<TdPhotoOrderInfo, Long>,
		JpaSpecificationExecutor<TdPhotoOrderInfo> {
	
}
