package com.ynyes.lyz.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdPhotoOrderGoodsInfo;
import com.ynyes.lyz.entity.TdPhotoOrderInfo;



/**
 *  拍照下单关联商品实体数据接口
 * 
 * @author panjie
 *
 */

public interface TdPhotoOrderGoodsInfoRepo extends
		PagingAndSortingRepository<TdPhotoOrderGoodsInfo, Long>,
		JpaSpecificationExecutor<TdPhotoOrderGoodsInfo> {
	List<TdPhotoOrderGoodsInfo> findByPhotoOrderIdEquals(Long id);
		
	
}
