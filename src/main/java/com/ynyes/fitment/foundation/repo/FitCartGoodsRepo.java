package com.ynyes.fitment.foundation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitCartGoods;

@Repository
public interface FitCartGoodsRepo extends ApplicationRepo<FitCartGoods> {

	List<FitCartGoods> findByEmployeeId(Long employeeId) throws Exception;

	Long countByEmployeeId(Long employeeId) throws Exception;

	FitCartGoods findByEmployeeIdAndGoodsId(Long employeeId, Long goodsId) throws Exception;

	@Modifying
	@Query("delete from FitCartGoods g where g.employeeId = :employeeId and g.goodsId = :goodsId")
	void deleteByGoodsIdAndEmployeeId(@Param("goodsId") Long goodsId, @Param("employeeId") Long employeeId)
			throws Exception;

	@Query("select sum(g.totalPrice) from FitCartGoods g where g.employeeId = :employeeId")
	Double getTotalCartGoodsPriceByEmployeeId(@Param("employeeId") Long employeeId) throws Exception;
}
