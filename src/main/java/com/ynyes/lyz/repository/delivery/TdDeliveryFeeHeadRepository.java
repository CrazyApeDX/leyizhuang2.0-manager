package com.ynyes.lyz.repository.delivery;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ynyes.lyz.entity.delivery.TdDeliveryFeeHead;

@Repository
public interface TdDeliveryFeeHeadRepository
		extends PagingAndSortingRepository<TdDeliveryFeeHead, Long>, JpaSpecificationExecutor<TdDeliveryFeeHead> {

	TdDeliveryFeeHead findBySobIdAndGoodsId(Long sobId, Long goodsId);
	
	Integer countBySobIdAndGoodsId(Long sobId, Long goodsId);
	
	TdDeliveryFeeHead findBySobIdAndGoodsSku(Long sobId, String goodsSku);
	
	List<TdDeliveryFeeHead> findBySobId(Long sobId);
	
	Page<TdDeliveryFeeHead> findBySobId(Long sobId, Pageable pageRequest);
	
	Page<TdDeliveryFeeHead> findBySobIdAndGoodsSkuContaining(Long sobId, String keywords, Pageable pageRequest);
}
