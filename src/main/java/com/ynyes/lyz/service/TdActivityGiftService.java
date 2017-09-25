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

import com.ynyes.lyz.entity.TdActivityGift;
import com.ynyes.lyz.entity.TdActivityGiftList;
import com.ynyes.lyz.repository.TdActivityGiftRepo;

@Service
@Transactional
public class TdActivityGiftService {

	@Autowired
	private TdActivityGiftRepo repository;

	@Autowired
	private TdActivityGiftListService tdActivityGiftListService;

	@Autowired
	private TdProductCategoryService tdProductCategoryService;

	public Page<TdActivityGift> findAllOrderByCreatTime(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "creatTime"));

		return repository.findAll(pageRequest);
	}

	public TdActivityGift save(TdActivityGift e) {
		if (null == e) {
			return null;
		}

		// 城市名
		e.setCategoryName(tdProductCategoryService.findOne(e.getCategoryId()).getTitle());

		// 商品名字，赠品名字
		String giftNames = "";
		for (TdActivityGiftList tdGoodGift : e.getGiftList()) {
			giftNames += tdGoodGift.getGoodsTitle() + ",";
		}

		e.setGiftName(giftNames);

		// 当修改时，赠品数量减少时，需删除多余的赠品
		if (null != e.getId() && null != e.getGiftList() && null != e.getTotalGift()) {
			int count = e.getTotalGift();
			int size = e.getGiftList().size();

			if (size > count) {
				List<TdActivityGiftList> subList = e.getGiftList().subList(count, size);
				tdActivityGiftListService.delete(subList);
				e.getGiftList().removeAll(subList);
			}
		}

		// 保存赠品
		tdActivityGiftListService.save(e.getGiftList());

		return repository.save(e);
	}

	public TdActivityGift findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public List<TdActivityGift> findAll() {
		return (List<TdActivityGift>) repository.findAll();
	}

	/**
	 * 根据商品类别的id获取当前能够参与的小辅料赠送活动（未过期）
	 * 
	 * @author dengxiao
	 */
	public List<TdActivityGift> findByCategoryIdAndIsUseableTrueAndBeginTimeBeforeAndEndTimeAfterAndGiftTypeOrderBySortIdAsc(
			Long categoryId, Date now, Long giftType) {
		if (null == categoryId || null == now || null == giftType) {
			return null;
		}
		return repository.findByCategoryIdAndIsUseableTrueAndBeginTimeBeforeAndEndTimeAfterAndGiftTypeOrderBySortIdAsc(
				categoryId, now, now, giftType);
	}

}
