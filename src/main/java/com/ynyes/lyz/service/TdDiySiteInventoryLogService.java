package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdDiySiteInventoryLog;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.repository.TdDiySiteInventoryLogRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;

@Service
@Transactional
public class TdDiySiteInventoryLogService {

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdDiySiteInventoryLogRepo repository;

	public TdDiySiteInventoryLog save(TdDiySiteInventoryLog e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdDiySiteInventoryLog findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdDiySiteInventoryLog> findAll() {
		return (List<TdDiySiteInventoryLog>) repository.findAll();
	}

	public Page<TdDiySiteInventoryLog> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "changeDate"));
		return repository.findAll(pageRequest);
	}

	public void saveWithRequsition(HttpServletRequest req, Long oldValue, Long newValue) {
		TdDiySiteInventoryLog log = new TdDiySiteInventoryLog();
		log.setChangeDate(new Date());
	}

	public Boolean saveChangeLog(TdDiySiteInventory diySiteInventory, Long changeValue, String orderNumber,
			HttpServletRequest req, String changeName) {
		String username = null;
		String changeType = null;
		// 请求不能为空
		if (req != null) {
			// 判断是订单修改还是管理员修改
			if (orderNumber == null) {
				username = (String) req.getSession().getAttribute("manager");
				changeType = "管理员修改";
			} else {
				username = (String) req.getSession().getAttribute("username");
				changeType = "订单修改";
			}
		}

		TdDiySiteInventoryLog log = new TdDiySiteInventoryLog();
		log.setDiySiteTitle(diySiteInventory.getDiySiteName());
		log.setRegionName(diySiteInventory.getRegionName());
		log.setRegionId(diySiteInventory.getRegionId());
		log.setDiySiteId(diySiteInventory.getDiySiteId());
		log.setGoodsId(diySiteInventory.getGoodsId());
		log.setGoodsTitle(diySiteInventory.getGoodsTitle());
		log.setGoodsSku(diySiteInventory.getGoodsCode());
		log.setAfterChange(diySiteInventory.getInventory());
		log.setChangeValue(changeValue);
		log.setChangeDate(new Date());
		log.setDescription(changeType);
		log.setOrderNumber(orderNumber);
		log.setManager(username);
		log.setChangeType(changeName);
		this.save(log);
		return true;

	}

	/**
	 * 根据城市,门店查找库存
	 * 
	 * @param regionId
	 * @param keywords
	 * @param page
	 * @param size
	 * @return
	 */
	public List<TdDiySiteInventoryLog> searchList(Long regionId, Long diyId, String keywords, Date startTime,
			Date endDate, Integer type) {
		Criteria<TdDiySiteInventoryLog> c = new Criteria<TdDiySiteInventoryLog>();
		String cityName = "";
		if (type == 1) {
			if (regionId != null) {
				c.add(Restrictions.eq("regionId", regionId, true));
			} else {
				c.add(Restrictions.isNull("diySiteId"));
			}
		} else if (type == 2) {
			if (diyId != null) {
				c.add(Restrictions.eq("diySiteId", diyId, true));
			} else {
				c.add(Restrictions.isNotNull("diySiteId"));
			}
		}

		if (StringUtils.isNotBlank(keywords)) {
			c.add(Restrictions.or(Restrictions.eq("goodsTitle", keywords, true),
					Restrictions.eq("goodsSku", keywords, true)));
		}
		if (startTime != null) {
			c.add(Restrictions.gte("changeDate", startTime, true));
		}
		if (endDate != null) {
			c.add(Restrictions.lte("changeDate", endDate, true));
		}
		if(type == 1){
			c.add(Restrictions.notLike("changeType", "门店", true));
		}
		if(type == 2){
			if(regionId == 2033L){
				cityName = "郑州市";
			}else if(regionId == 2121L){
				cityName = "成都市";
			}else {
				cityName = "重庆市";
			}
			c.add(Restrictions.like("regionName", cityName, true));
		}
		
		c.setOrderByAsc("goodsSku");
		return repository.findAll(c);
	}

	public Page<TdDiySiteInventoryLog> findBySiteIdAndKeywords(Long siteId, String keywords, int page, int size) {
		PageRequest pageable = new PageRequest(page, size);
		if (StringUtils.isBlank(keywords)) {
			return repository.findByDiySiteId(siteId, pageable);
		} else {
			return repository.findByDiySiteIdAndGoodsSkuContainingOrDiySiteIdAndGoodsTitleContainingOrderByIdAsc(siteId,
					keywords, siteId, keywords, pageable);
		}
	}

	public Page<TdDiySiteInventoryLog> findByRegionIdOnlyAndKeywords(Long regionId, String keywords, int page,
			int size) {
		PageRequest pageable = new PageRequest(page, size);
		if (StringUtils.isBlank(keywords))
			return repository.findByRegionIdAndDiySiteIdIsNull(regionId, pageable);
		return repository
				.findByRegionIdAndDiySiteIdIsNullAndGoodsSkuContainingOrRegionIdAndDiySiteIdIsNullAndGoodsTitleContainingOrderByIdAsc(
						regionId, keywords, regionId, keywords, pageable);
	}

	public Page<TdDiySiteInventoryLog> findByRegionIdAndKeywords(Long regionId, String keywords, int page, int size) {
		PageRequest pageable = new PageRequest(page, size);
		if (StringUtils.isBlank(keywords))
			return repository.findByRegionIdOrderByIdAsc(regionId, pageable);
		return repository.findByRegionIdAndGoodsSkuContainingOrRegionIdAndGoodsTitleContainingOrderByIdAsc(regionId,
				keywords, regionId, keywords, pageable);
	}

	public Page<TdDiySiteInventoryLog> findCityInventoryAndKeywords(String keywords, int page, int size) {
		PageRequest pageable = new PageRequest(page, size);
		if (StringUtils.isBlank(keywords)) {
			return repository.findByDiySiteIdIsNull(pageable);
		}
		return repository
				.findByDiySiteIdIsNullAndGoodsSkuContainingOrDiySiteIdIsNullAndGoodsTitleContainingOrderByIdAsc(
						keywords, keywords, pageable);
	}

	public Page<TdDiySiteInventoryLog> findAll(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		if (StringUtils.isNotBlank(keywords))
			return repository.findByGoodsSkuContainingOrGoodsTitleContainingOrderByIdAsc(keywords, keywords,
					pageRequest);
		return repository.findAll(pageRequest);
	}

	public void fitmentCreateLog(String description, TdGoods goods, Long changeValue, String orderNumber, Long regionId,
			String manager, Long afterChange, String type) {
		TdDiySiteInventoryLog log = new TdDiySiteInventoryLog();
		log.setChangeDate(new Date());
		log.setDescription(description);
		log.setGoodsId(goods.getId());
		log.setGoodsTitle(goods.getTitle());
		log.setGoodsSku(goods.getCode());
		log.setChangeValue(changeValue);
		log.setOrderNumber(orderNumber);
		log.setRegionId(regionId);
		TdCity city = this.tdCityService.findBySobIdCity(regionId);
		log.setRegionName(city.getCityName());
		log.setManager(manager);
		log.setAfterChange(afterChange);
		log.setChangeType(type);
		repository.save(log);
	}
}
