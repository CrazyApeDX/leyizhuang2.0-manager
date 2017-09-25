package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderData;
import com.ynyes.lyz.repository.TdOrderDataRepository;

@Service
@Transactional
public class TdOrderDataService {

	@Autowired
	private TdOrderDataRepository repository;

	public TdOrderData save(TdOrderData e) {
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

	public TdOrderData findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdOrderData> findAll() {
		return repository.findAll();
	}

	public TdOrderData findByMainOrderNumber(String mainOrderNumber) {
		if (null == mainOrderNumber) {
			return null;
		}
		return repository.findByMainOrderNumber(mainOrderNumber);
	}

	public void createOrderDataByMainOrder(TdOrder mainOrder) {
		if (null != mainOrder) {
			new CreateOrderDataThread(mainOrder).start();
		}
	}

	private class CreateOrderDataThread extends Thread {

		private TdOrder mainOrder;

		private CreateOrderDataThread(TdOrder mainOrder) {
			super();
			this.mainOrder = mainOrder;
		}

		@Override
		public void run() {
			TdOrderData data = repository.findByMainOrderNumber(mainOrder.getMainOrderNumber());
			data = null == data ? new TdOrderData() : data;
			data.setReceiverIsMember(mainOrder.getReceiverIsMember());
			data.setUsername(mainOrder.getRealUserUsername());
			data.setMainOrderNumber(mainOrder.getOrderNumber());
			data.setStoreId(mainOrder.getDiySiteId());
			data.setStoreTitle(mainOrder.getDiySiteName());
			data.setStoreCode(mainOrder.getDiySiteCode());
			data.setSellerId(mainOrder.getSellerId());
			data.setSellerName(mainOrder.getSellerRealName());
			data.setSellerPhone(mainOrder.getSellerUsername());
			data.setTotalGoodsPrice(mainOrder.getTotalGoodsPrice());
			data.setMemberDiscount(mainOrder.getDifFee());
			data.setActivitySub(mainOrder.getActivitySubPrice());
			data.setProCouponFee(mainOrder.getProCouponFee());
			data.setCashCouponFee(mainOrder.getCashCoupon());
			data.setBalanceUsed(mainOrder.getActualPay());
			data.setOnlinePay(mainOrder.getOtherPay());
			data.setDeliveryFee(mainOrder.getDeliverFee());
			data.setLeftPrice(mainOrder.getNotPayedFee());
			data.setAgencyRefund(mainOrder.getAgencyRefund());
			data.setDue(mainOrder.getNotPayedFee());
			repository.save(data);
		}

	}
}
