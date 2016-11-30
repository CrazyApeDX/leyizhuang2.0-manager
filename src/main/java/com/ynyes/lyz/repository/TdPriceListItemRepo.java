package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdPriceListItem;

public interface TdPriceListItemRepo
		extends PagingAndSortingRepository<TdPriceListItem, Long>, JpaSpecificationExecutor<TdPriceListItem> {

	/**
	 * 根据价目表编号和首页推荐查找价目表项（分页）
	 * 
	 * @author dengxiao
	 */
	Page<TdPriceListItem> findByPriceListIdAndIsCommendIndexTrueOrderBySortIdAsc(Long PriceListId, Pageable page);

	/**
	 * 根据价目表编号查找所有参加促销的商品（不分页）
	 * 
	 * @author dengxiao
	 */

	List<TdPriceListItem> findByPriceListIdAndIsPromotionTrueOrderBySortIdAsc(Long PriceListId);

	/**
	 * @author lc
	 * @注释：搜索
	 */
	Page<TdPriceListItem> findByPriceListNameContainingOrCityNameContainingOrCompanyNameContaining(String keyword1,
			String keyword2, String keyword3, Pageable page);

	/**
	 * 根据价目表编号和商品id查找价目表项
	 * 
	 * @author dengxiao
	 */
	TdPriceListItem findByPriceListIdAndGoodsId(Long PriceListId, Long goodsId);

	List<TdPriceListItem> findByPriceListIdOrderBySortIdAsc(Long PriceListId);

	TdPriceListItem findByListHeaderId(Long listHeaderId);

	TdPriceListItem findByListLineId(Long listLineId);

	/**
	 * 根据headLineId和商品的SKU查找启用的未过期的价目表项
	 * 
	 * @author dengxiao
	 */
	List<TdPriceListItem> findByListHeaderIdAndItemNumAndStartDateActiveBeforeAndEndDateActiveAfter(Long headerId, String SKU,
			Date begin, Date finish);
	
	List<TdPriceListItem> findByPriceListIdAndGoodsIdAndStartDateActiveBeforeAndEndDateActiveIsNullOrPriceListIdAndGoodsIdAndStartDateActiveBeforeAndEndDateActiveAfterOrPriceListIdAndGoodsIdAndStartDateActiveIsNull(
																		Long listHeaderId1,Long inventoryItemId1,Date start,
																		Long listHeaderId2,Long inventoryItemId2,Date start2,Date end,
																		Long listHeaderId3,Long inventoryItemId3);
	
	List<TdPriceListItem> findByPriceListIdAndItemNumAndStartDateActiveBeforeAndEndDateActiveIsNullOrPriceListIdAndItemNumAndStartDateActiveBeforeAndEndDateActiveAfterOrPriceListIdAndItemNumAndStartDateActiveIsNull(
			Long listHeaderId1,String inventoryItemId1,Date start,
			Long listHeaderId2,String inventoryItemId2,Date start2,Date end,
			Long listHeaderId3,String inventoryItemId3);
	
	/**
	 * 根据商品名称和编号查询价格表
	 * @return
	 */
	Page<TdPriceListItem> findByItemDescContainingOrItemNumContaining(String itemDesc,String itemNum,Pageable page);
	
	
}
