package com.ynyes.fitment.foundation.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.core.repo.ApplicationRepo;
import com.ynyes.fitment.foundation.entity.FitPriceLine;

@Repository
public interface FitPriceLineRepo extends ApplicationRepo<FitPriceLine> {

	Page<FitPriceLine> findByHeaderId(Long headerId, Pageable page) throws Exception;

	List<FitPriceLine> findByHeaderId(Long headerId) throws Exception;

	FitPriceLine findByHeaderIdAndGoodsId(Long headerId, Long goodsId) throws Exception;

	@Query("select line from FitPriceLine line where line.headerId = :headerId and line.goodsId = :goodsId and line.startTime > :startTime and (line.endTime < :endTime or line.endTime is null)")
	FitPriceLine findByHeaderIdAndGoodsIdAndStartTimeBeforeAndEndTimeAfter(@Param("headerId") Long headerId,
			@Param("goodsId") Long goodsId, @Param("startTime") Date startTime, @Param("endTime") Date endTime)
			throws Exception;

	FitPriceLine findByHeaderIdAndGoodsIdAndStartTimeBeforeAndEndTimeAfterOrHeaderIdAndGoodsIdAndStartTimeBeforeAndEndTimeIsNull(
			Long headerId1, Long goodsId1, Date startTime1, Date endTime1, Long headerId2, Long goodsId2,
			Date startTime2);

	FitPriceLine findByEbsId(Long ebsId);

	/**
	 *  根据商品SKU模糊查询装饰公司商品价目(分页)
	 * @param keywords
	 * @param page
	 * @param size
	 * @return
	 */
	Page<FitPriceLine> findByHeaderIdAndGoodsSkuContaining(Long headerId,String keywords,Pageable pageable);
}
