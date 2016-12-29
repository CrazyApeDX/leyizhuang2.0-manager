package com.ynyes.fitment.foundation.repo;

import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitPriceLine;

@Repository
public interface FitPriceLineRepo extends ApplicationRepo<FitPriceLine> {

	FitPriceLine findByHeaderIdAndGoodsId(Long headerId, Long goodsId) throws Exception;
}
