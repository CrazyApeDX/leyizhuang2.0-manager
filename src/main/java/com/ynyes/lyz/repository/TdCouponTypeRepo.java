package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdCouponType;

/**
 * TdCouponType 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdCouponTypeRepo extends
		PagingAndSortingRepository<TdCouponType, Long>,
		JpaSpecificationExecutor<TdCouponType> 
{
    Page<TdCouponType> findByTitleContainingOrderBySortIdAsc(String keywords, Pageable page);
    
    List<TdCouponType> findByCategoryIdIs(Long categoryId);//按照优惠券类型Id查找优惠券
    
    TdCouponType  findByCategoryIdAndPicUriNotNull(Long categoryId); //zhangji
    
}
