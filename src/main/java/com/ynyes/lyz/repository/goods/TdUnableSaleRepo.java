package com.ynyes.lyz.repository.goods;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.goods.TdUnableSale;

public interface TdUnableSaleRepo
		extends PagingAndSortingRepository<TdUnableSale, Long>, JpaSpecificationExecutor<TdUnableSale> {

	Page<TdUnableSale> findByDiySiteId(Long diySiteId, Pageable page);
	
	List<TdUnableSale> findByDiySiteIdAndGoodsId(Long diySiteId, Long goodsId);
}
