package com.ynyes.lyz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.upstairs.TdUpstairsSetting;
import com.ynyes.lyz.repository.upstairs.TdUpstairsSettingRepo;

@Service
@Transactional
public class TdUpstairsSettingService {

	@Autowired
	private TdUpstairsSettingRepo repository;
	
	@Autowired
	private TdCityService tdCityService;

	public TdUpstairsSetting save(TdUpstairsSetting e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}

	public TdUpstairsSetting findTopBy() {
		TdUpstairsSetting setting = repository.findTopBy();
		setting = (setting == null) ? new TdUpstairsSetting() : setting;
		setting = this.save(setting);
		return setting;
	}
	
	public TdUpstairsSetting findBySobIdCity(Long sobIdCity) {
		if (null == sobIdCity) {
			return null;
		}
		TdUpstairsSetting setting = repository.findBySobIdCity(sobIdCity);
		setting = (setting == null) ? new TdUpstairsSetting(sobIdCity) : setting;
		return setting;
	}

	public Double countUpstairsFee(TdOrder order) {
		Double panelUpstairsFee = 0d;
		Double keelUpstairsFee = 0d;
		
		// 获取订单的sobIdCity
		Long sobIdCity = tdCityService.findByCityName(order.getCity()).getSobIdCity();
		
		TdUpstairsSetting setting = this.findBySobIdCity(sobIdCity);
		Map<String, Long> result = this.countPanelNumber(order, setting);
	

		if ("送货上门".equals(order.getDeliverTypeTitle())) {
			if ("电梯".equals(order.getUpstairsType())) {
				panelUpstairsFee = setting.getPanelEleUnit() * result.get("panel");
				keelUpstairsFee = setting.getKeelEleUnit() * result.get("keel") / setting.getKeelUnitNumber();
			} else if ("步梯".equals(order.getUpstairsType())) {
				panelUpstairsFee = setting.getPanelStepUnit() * result.get("panel") * (order.getFloor() - 1l);
				keelUpstairsFee = setting.getKeelStepUnit() * result.get("keel") / setting.getKeelUnitNumber()
						* order.getFloor();
			} else {
				// do nothing!
			}
		}

		return panelUpstairsFee + keelUpstairsFee;
	}

	public Map<String, Long> countPanelNumber(TdOrder order, TdUpstairsSetting setting) {
		String[] panelSkus = setting.getPanelSkus().split(",");
		String[] keelSkus = setting.getKeelSkus().split(",");

		Map<String, Long> result = new HashMap<>();
		result.put("panel", 0l);
		result.put("keel", 0l);
		result = this.countNumber(order.getOrderGoodsList(), panelSkus, keelSkus, result);
		result = this.countNumber(order.getPresentedList(), panelSkus, keelSkus, result);
		result = this.countNumber(order.getGiftGoodsList(), panelSkus, keelSkus, result);

		return result;
	}

	public Map<String, Long> countNumber(List<TdOrderGoods> orderGoodsList, String[] panelSkus, String[] keelSkus,
			Map<String, Long> result) {
		if (!CollectionUtils.isEmpty(orderGoodsList)) {
			for (TdOrderGoods orderGoods : orderGoodsList) {
				String sku = orderGoods.getSku();
				if (ArrayUtils.contains(panelSkus, sku)) {
					result.put("panel", result.get("panel") + 1l);
				} else if (ArrayUtils.contains(keelSkus, sku)) {
					result.put("keel", result.get("keel") + 1l);
				} else {
					// do nothing!
				}
			}
		}
		return result;
	}
}
