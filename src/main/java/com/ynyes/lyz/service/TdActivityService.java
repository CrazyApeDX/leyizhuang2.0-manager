package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdActivity;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteList;
import com.ynyes.lyz.entity.TdGoodsCombination;
import com.ynyes.lyz.entity.TdGoodsGift;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.repository.TdActivityRepo;

@Service
@Transactional
public class TdActivityService {

	@Autowired
	private TdActivityRepo repository;

	@Autowired
	private TdGoodsGiftService tdGoodsGiftService;

	@Autowired
	private TdGoodsCombinationService tdGoodsCombinationService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdDiySiteListService tdDiySiteListService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdPriceListItemService tdPriceListItemService;

	public TdActivity save(TdActivity e) {
		if (null == e) {
			return null;
		}

		// 城市名
		e.setCityName(tdCityService.findOne(e.getCityId()).getCityName());

		// 商品名字，赠品名字
		String goodsName = "";
		for (TdGoodsCombination tdGoodCombination : e.getCombList()) {
			goodsName += tdGoodCombination.getGoodsTitle() + ",";
		}

		String giftNames = "";
		if (null != e.getGiftList() && e.getGiftList().size() > 0) {
			for (TdGoodsGift tdGoodGift : e.getGiftList()) {
				giftNames += tdGoodGift.getGoodsTitle() + ",";
			}
		}
		e.setGiftNames(giftNames);
		e.setGoodsNames(goodsName);

		// 当修改时，赠品数量减少时，需删除多余的赠品
		if (null != e.getId() && null != e.getGiftList() && null != e.getTotalGift()) {
			int count = e.getTotalGift();
			int size = e.getGiftList().size();

			if (size > count) {
				List<TdGoodsGift> subList = e.getGiftList().subList(count, size);
				tdGoodsGiftService.delete(subList);
				e.getGiftList().removeAll(subList);
			}
		}

		// 当修改时，商品数量减少时，需删除多余的组合商品
		if (null != e.getId() && null != e.getCombList() && null != e.getTotalGoods()) {
			int count = e.getTotalGoods();
			int size = e.getCombList().size();

			if (size > count) {
				List<TdGoodsCombination> subList = e.getCombList().subList(count, size);
				tdGoodsCombinationService.delete(subList);
				e.getCombList().removeAll(subList);
			}
		}
		// 当修改时，商品数量减少时，需删除多余的门店
		if (null != e.getId() && null != e.getSiteList() && null != e.getTotalDiySite()) {
			int count = e.getTotalDiySite();
			int size = e.getSiteList().size();

			if (size > count) {
				List<TdDiySiteList> subList = e.getSiteList().subList(count, size);
				tdDiySiteListService.delete(subList);
				e.getSiteList().removeAll(subList);
			}
		}
		String siteNameString = "";
		String diySiteIdStr = "";
		if (null != e.getSiteList() && e.getSiteList().size() > 0) {
			for (TdDiySiteList siteList : e.getSiteList()) {
				siteNameString += siteList.getTitle() + ",";
			}
			e.setSiteName(siteNameString);
		}

		// 保存赠品
		tdGoodsGiftService.save(e.getGiftList());

		// 保存商品
		tdGoodsCombinationService.save(e.getCombList());

		// 保存门店
		tdDiySiteListService.save(e.getSiteList());
		if (null != e.getSiteList() && e.getSiteList().size() > 0) {
			for (TdDiySiteList siteList : e.getSiteList()) {
				diySiteIdStr += siteList.getSiteId() + ",";
			}
			e.setDiySiteIds(diySiteIdStr);
		}
		e = repository.save(e);

		// 获取所有的城市id
		String siteIds = e.getDiySiteIds();

		// 拆分城市id
		String[] ids = siteIds.split(",");

		// 获取参与活动的【商品id_商品数量】对
		String goodsNumber = e.getGoodsNumber();
		// 拆分【商品id_商品数量】对
		String[] goods_numbers = goodsNumber.split(",");
		// 遍历拆分
		for (String item : goods_numbers) {
			String[] params = item.split("_");
			Long id = Long.parseLong(params[0]);
			// 遍历参加活动的门店
			for (String siteId : ids) {
				// 查找到指定id的门店
				TdDiySite site = tdDiySiteService.findOne(Long.parseLong(siteId));
				// 查找到指定的价目表
				TdPriceListItem priceListItem = tdPriceListItemService
						.findByPriceListIdAndGoodsId(site.getPriceListId(), id);
				if (null != priceListItem) {
					priceListItem.setIsPromotion(true);
					if (null == priceListItem.getActivities()) {
						priceListItem.setActivities(e.getId() + ",");
					} else {
						priceListItem.setActivities(priceListItem.getActivities() + e.getId() + ",");
					}
					tdPriceListItemService.save(priceListItem);
				}
			}
		}

		return e;
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdActivity findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdActivity> findAll() {
		return (List<TdActivity>) repository.findAll();
	}

	/**
	 * 根据门店id查找该门店参与的活动，按照排序号正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdActivity> findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndIsCommendIndexTrueOrderBySortIdAsc(
			String diySiteId, Date now) {
		if (null == diySiteId || null == now) {
			return null;
		}
		return repository
				.findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndIsCommendIndexTrueOrderBySortIdAsc(
						diySiteId + ",", now, now);
	}

	/**
	 * 根据门店id查找该门店参与的未过期的活动，按照排序号正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdActivity> findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterOrderBySortIdAsc(
			String diySiteId, Date now) {
		if (null == diySiteId || null == now) {
			return null;
		}
		return repository.findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterOrderBySortIdAsc(
				diySiteId + ",", now, now);
	}

	public List<TdActivity> findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndGiftTypeOrderBySortIdAsc(
			String diySiteId, Date now, Long giftType) {
		if (null == diySiteId || null == now || null == giftType) {
			return null;
		}
		return repository.findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndGiftTypeOrderBySortIdAsc(
				diySiteId + ",", now, now, giftType);
	}

	public TdActivity findByName(String name) {
		if (name == null) {
			return null;
		}
		return repository.findByName(name);
	}

	/**
	 * 根据商品的数量，商品的id和门店的id查找所有能够参加的未过期的，且非优惠券、非小辅料赠送活动，按照sortId（排序号）正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdActivity> findActivitiesJoinedOrderBySortIdAsc(Integer totalGoods, String diySiteId, String goodsId,
			Date now) {
		if (null == totalGoods || null == diySiteId || null == goodsId || null == now) {
			return null;
		}
		return repository
				.findByDiySiteIdsContainingAndGoodsNumberContainingAndBeginDateBeforeAndFinishDateAfterAndGiftTypeAndTotalGoodsLessThanEqualOrderBySortIdAsc(
						diySiteId, goodsId, now, now, 0L, totalGoods);
	}

	public Page<TdActivity> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
		return repository.findAll(pageRequest);
	}

	/**
	 * 根据商品id和门店id查找活动
	 * 
	 * @author dengxiao
	 */
	public List<TdActivity> findByGoodsNumberContainingAndDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfter(
			String goodsId, String diySiteId, Date now) {
		if (null == goodsId || null == diySiteId || null == now) {
			return null;
		}
		return repository.findByGoodsNumberContainingAndDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfter(
				goodsId, diySiteId, now, now);
	}

}
