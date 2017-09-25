package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdProduct;


/**
 * TdProduct 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdProductRepo extends
		PagingAndSortingRepository<TdProduct, Long>,
		JpaSpecificationExecutor<TdProduct> 
{
    
    List<TdProduct> findByProductCategoryTreeContaining(String productCategoryId);
    
    Page<TdProduct> findByProductCategoryTreeContaining(String productCategoryId, Pageable page);
    
    TdProduct findByProductCategoryTreeContainingAndTitle(String productCategoryId, String title);
    
    TdProduct findByProductCategoryTreeContainingAndTitleAndIdNot(String productCategoryId, String title, Long id);
    
    TdProduct findByTitle(String title);
    
    TdProduct findByTitleAndIdNot(String title, Long id);
    
    Page<TdProduct> findByTitleContainingOrderBySortIdAsc(String keywords, Pageable page);
}
