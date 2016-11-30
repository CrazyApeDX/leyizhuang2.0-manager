package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdActivity;

public interface TdActivityRepo
		extends PagingAndSortingRepository<TdActivity, Long>, JpaSpecificationExecutor<TdActivity> {

	/**
	 * 根据门店id查找该门店参与的未过期的首页推荐的活动，按照排序号正序排序
	 * 
	 * @author dengxiao
	 */
	List<TdActivity> findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndIsCommendIndexTrueOrderBySortIdAsc(
			String diySiteId, Date date1, Date date2);

	/**
	 * 根据门店id查找该门店参与的未过期的活动，按照排序号正序排序
	 * 
	 * @author dengxiao
	 */
	List<TdActivity> findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterOrderBySortIdAsc(String diySiteId,
			Date date1, Date date2);

	/**
	 * 根据门店id和赠送类型查找参与的未过期的活动，按照排序号正序排序
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月29日下午6:24:15
	 */
	List<TdActivity> findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndGiftTypeOrderBySortIdAsc(
			String diySiteId, Date date1, Date date2, Long giftType);

	/**
	 * 按照活动名查找活动
	 * 
	 * @author dengxiao
	 */
	TdActivity findByName(String name);

	/**
	 * 根据商品的数量，商品的id和门店的id查找所有能够参加的未过期的，且非优惠券、非小辅料赠送活动，按照sortId（排序号）正序排序
	 * 
	 * @author dengxiao
	 */
	List<TdActivity> findByDiySiteIdsContainingAndGoodsNumberContainingAndBeginDateBeforeAndFinishDateAfterAndGiftTypeAndTotalGoodsLessThanEqualOrderBySortIdAsc(
			String diySiteId, String goodsId, Date date1, Date date2, Long giftType, Integer totalGoods);

	/**
	 * 根据商品id和门店id查找活动
	 * 
	 * @author dengxiao
	 */
	List<TdActivity> findByGoodsNumberContainingAndDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfter(
			String goodsId, String diySiteId, Date begin, Date finish);
}
