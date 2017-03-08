package com.ynyes.fitment.foundation.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitPriceLine;

@Repository
public interface FitPriceLineRepo extends ApplicationRepo<FitPriceLine> {

	Page<FitPriceLine> findByHeaderId(Long headerId, Pageable page) throws Exception;

	FitPriceLine findByHeaderIdAndGoodsId(Long headerId, Long goodsId) throws Exception;

	FitPriceLine findByEbsId(Long ebsId);
}
