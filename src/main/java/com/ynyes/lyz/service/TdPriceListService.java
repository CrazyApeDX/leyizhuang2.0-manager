package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdPriceList;
import com.ynyes.lyz.repository.TdPriceListRepo;

@Service
@Transactional
public class TdPriceListService {

	@Autowired
	private TdPriceListRepo repository;

	/* zhangji */
	/*-----------------------------------------------------------------------------------------------*/
	public TdPriceList save(TdPriceList e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}

	// public TdPriceList save(TdPriceList e) {
	// if (null == e) {
	// return null;
	// }
	// // 当修改时，赠品数量减少时，需删除多余的赠品
	// if (null != e.getId() && null != e.getTotalItem() ) {
	// int count = 0;
	// if(null != e.getPriceItemList() && null !=
	// repository.findOne(e.getId()).getPriceItemList())
	// {
	// count = e.getTotalItem();
	//
	// int size = repository.findOne(e.getId()).getPriceItemList().size();
	//
	// if (size > count) {
	// List<TdPriceListItem> subList =
	// repository.findOne(e.getId()).getPriceItemList().subList(count, size);
	// tdPriceListItemService.delete(subList);
	// repository.findOne(e.getId()).getPriceItemList().removeAll(subList);
	// }
	// }
	// }
	//
	// // 保存赠品
	// tdPriceListItemService.save(e.getPriceItemList());
	//
	// e = repository.save(e);
	//
	// return e;
	// }
	/*-------------------------------------------------------------------------------------------------------*/
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public List<TdPriceList> findBySobId(Long sobId) {
		if (sobId == null) {
			return null;
		}
		return repository.findBySobId(sobId);
	}

	public TdPriceList findByListHeaderId(Long listHeaderId) {
		if (listHeaderId == null) {
			return null;
		}
		return repository.findByListHeaderId(listHeaderId);
	}

	public TdPriceList findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdPriceList> findAll() {
		return (List<TdPriceList>) repository.findAll();
	}

	public Page<TdPriceList> searchAll(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByNameContaining(keywords, pageRequest);
	}

	public Page<TdPriceList> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
		return repository.findAll(pageRequest);
	}

	/**
	 * 根据priceType（价目表类型）和cityId查找未过期且可用的价目表
	 * 
	 * @author dengxiao
	 */
	public TdPriceList findByPriceTypeAndCityIdAndStartDateActiveBeforeAndEndDateActiveAfterAndActiveFlagTrue(
			String priceType, Long cityId) {
		if (null == priceType || null == cityId) {
			return null;
		}
		List<TdPriceList> list = repository
				.findByPriceTypeAndCityIdAndStartDateActiveBeforeAndEndDateActiveAfterAndActiveFlag(priceType, cityId,
						new Date(), new Date(), "Y");
		if (null == list || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}

	}

	public List<TdPriceList> findBySobIdAndPriceTypeAndStartDateActiveAndEndDateActive(Long sobId, String priceType,
			Date start, Date end) {
		if (null == sobId || null == priceType || null == start) {
			return null;
		}
		return repository
				.findByCityIdAndPriceTypeAndStartDateActiveBeforeAndEndDateActiveIsNullOrCityIdAndPriceTypeAndStartDateActiveBeforeAndEndDateActiveAfterOrCityIdAndPriceTypeAndStartDateActiveIsNull(
						sobId, priceType, start, sobId, priceType, start, end, sobId, priceType);
	}

	public List<TdPriceList> findByCityId(Long cityId) {
		if (cityId == null) {
			return null;
		}
		return repository.findByCityId(cityId);
	}

	public List<TdPriceList> findByCityIdAndActiveFlag(Long sobId) {
		if (sobId == null) {
			return null;
		}
		return repository.findByCityIdAndActiveFlag(sobId, "Y");
	}
	
	public List<TdPriceList> findByCityIdAndActiveFlag(Long sobId,String flag) {
		if (sobId == null || null == flag) {
			return null;
		}
		return repository.findByCityIdAndActiveFlag(sobId, flag);
	}

}
