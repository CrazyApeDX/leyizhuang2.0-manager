package com.ynyes.fitment.foundation.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitCompanyGoods;

@Repository
public interface FitCompanyGoodsRepo extends ApplicationRepo<FitCompanyGoods> {

	Page<FitCompanyGoods> findByCompanyIdOrderByGoodsSortIdAsc(Long companyId, Pageable page) throws Exception;

	List<FitCompanyGoods> findByCompanyIdAndCategoryIdOrderByGoodsSortIdAsc(Long companyId, Long categoryId)
			throws Exception;

	Long countByCompanyIdAndGoodsId(Long companyId, Long goodsId) throws Exception;

	@Query("select goods from FitCompanyGoods goods where goods.companyId = :companyId and (goods.goodsTitle like concat('%',:keyName,'%') or goods.goodsSku like concat('%',:keyName,'%')) order by goodsSortId asc")
	List<FitCompanyGoods> findByCompanyIdAndKeywords(@Param("companyId") Long companyId,
			@Param("keywords") String keywords);
}
