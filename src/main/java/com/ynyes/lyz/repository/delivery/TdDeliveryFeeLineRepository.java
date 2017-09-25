package com.ynyes.lyz.repository.delivery;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ynyes.lyz.entity.delivery.TdDeliveryFeeLine;

@Repository
public interface TdDeliveryFeeLineRepository
		extends PagingAndSortingRepository<TdDeliveryFeeLine, Long>, JpaSpecificationExecutor<TdDeliveryFeeLine> {

	List<TdDeliveryFeeLine> findByHeadId(Long headId);
	
	Page<TdDeliveryFeeLine> findByHeadId(Long headId, Pageable pageRequest);

	@Query("select l from TdDeliveryFeeLine l where l.minNumber <= :number and l.maxNumber >= :number and l.headId = :headId")
	TdDeliveryFeeLine findByOrderGoodsNumberAndHeadId(@Param("number") Long number, @Param("headId") Long headId);

	@Query("select count(*) from TdDeliveryFeeLine l where l.minNumber <= :number and l.maxNumber >= :number and l.headId = :headId")
	Integer findCountByOrderGoodsNumberAndHeadId(@Param("number") Long number, @Param("headId") Long headId);

	@Query("select l from TdDeliveryFeeLine l where l.minNumber <= :number and l.maxNumber >= :number and l.headId = ("
			+ "select h.id from TdDeliveryFeeHead h where h.sobId = :sobId and h.goodsId = :goodsId)")
	TdDeliveryFeeLine findBySobIdAndGoodsIdAndNumber(@Param("sobId") Long sobId, @Param("goodsId") Long goodsId,
			@Param("number") Long number);

	@Query("select l from TdDeliveryFeeLine l where l.minNumber <= :number and l.maxNumber >= :number and l.headId = ("
			+ "select h.id from TdDeliveryFeeHead h where h.sobId = :sobId and h.goodsSku = :goodsSku)")
	TdDeliveryFeeLine findBySobIdAndGoodsSkuAndNumber(@Param("sobId") Long sobId, @Param("goodsSku") String goodsSku,
			@Param("number") Long number);
}
