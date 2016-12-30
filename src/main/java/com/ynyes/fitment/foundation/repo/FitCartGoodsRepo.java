package com.ynyes.fitment.foundation.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitCartGoods;

@Repository
public interface FitCartGoodsRepo extends ApplicationRepo<FitCartGoods> {

	List<FitCartGoods> findByEmployeeId(Long employeeId) throws Exception;
}
