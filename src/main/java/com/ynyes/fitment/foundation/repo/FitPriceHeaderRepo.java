package com.ynyes.fitment.foundation.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitPriceHeader;

@Repository
public interface FitPriceHeaderRepo extends ApplicationRepo<FitPriceHeader> {

	@Query("select h from FitPriceHeader h where :now > h.startTime and :now < h.endTime and h.priceType = :priceType")
	List<FitPriceHeader> findActivePriceHeaderByPriceType(@Param("priceType") String priceType,
			@Param("now") Date now) throws Exception;
}
