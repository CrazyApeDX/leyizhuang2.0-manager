package com.ynyes.lyz.service.impl.generation;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.interfaces.entity.TdOrderCouponInf;
import com.ynyes.lyz.interfaces.entity.TdOrderGoodsInf;
import com.ynyes.lyz.interfaces.entity.TdOrderInf;
import com.ynyes.lyz.interfaces.service.TdOrderInfService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.basic.generation.IGenerationService;

@Service
@Transactional
public class GenerationServiceImpl implements IGenerationService {

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdOrderInfService tdOrderInfService;

	@Override
	public void generateOrderData(List<TdOrder> orderList) {
		if (!CollectionUtils.isEmpty(orderList)) {
			for (TdOrder order : orderList) {
				this.generateOrderInf(order);
				this.generateOrderGoodsInf(order);
				this.generateOrderCouponInf(order);
			}
		}
	}

	@Override
	public TdOrderInf generateOrderInf(TdOrder order) {
		return null;
	}

	@Override
	public List<TdOrderGoodsInf> generateOrderGoodsInf(TdOrder order) {
		List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
		for (TdOrderGoods orderGoods : orderGoodsList) {
			
		}
		return null;
	}

	@Override
	public List<TdOrderCouponInf> generateOrderCouponInf(TdOrder order) {
		String cashCouponId = order.getCashCouponId();
		String productCouponId = order.getProductCouponId();
		return null;
	}

}
