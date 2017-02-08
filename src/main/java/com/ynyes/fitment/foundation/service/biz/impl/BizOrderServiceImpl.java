package com.ynyes.fitment.foundation.service.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.foundation.entity.FitCartGoods;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrder;
import com.ynyes.fitment.foundation.entity.FitOrderGoods;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitOrderGoodsService;
import com.ynyes.fitment.foundation.service.FitOrderService;
import com.ynyes.fitment.foundation.service.biz.BizCartGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizOrderService;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdOrderGoodsService;

@Service
@Transactional
public class BizOrderServiceImpl implements BizOrderService {

	@Autowired
	private BizCartGoodsService bizCartGoodsService;

	@Autowired
	private FitOrderService fitOrderService;

	@Autowired
	private FitOrderGoodsService fitOrderGoodsService;

	@Autowired
	private FitCompanyService fitCompanyService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Override
	public FitOrder initOrder(List<FitCartGoods> cartGoodsList, String receiver, String receiverMobile,
			String baseAddress, String detailAddress, FitEmployee employee) throws Exception {
		List<FitOrderGoods> orderGoodsList = new ArrayList<>();
		for (FitCartGoods cartGoods : cartGoodsList) {
			if (null != cartGoods) {
				FitOrderGoods orderGoods = new FitOrderGoods().init(cartGoods);
				orderGoods = fitOrderGoodsService.save(orderGoods);
				orderGoodsList.add(orderGoods);
				this.bizCartGoodsService.clearCartWithId(cartGoods.getId());
			}
		}

		FitOrder order = new FitOrder();
		order.setCompanyId(employee.getCompanyId());
		order.setCompanyTitle(employee.getCompanyTitle());
		order.setEmployeeId(employee.getId());
		order.setEmployeeName(employee.getName());
		order.setEmployeeMobile(employee.getMobile());
		if (employee.getIsMain()) {
			order.setAuditorId(employee.getId());
			order.setAuditorName(employee.getName());
			order.setAuditorMobile(employee.getMobile());
			order.setAuditTime(new Date());
		}

		order.setStatus(employee.getIsMain() ? AuditStatus.AUDIT_SUCCESS : AuditStatus.WAIT_AUDIT);
		order.setOrderGoodsList(orderGoodsList);
		order.setReceiveName(receiver);
		order.setReceivePhone(receiverMobile);
		order.setReceiveAddress(baseAddress + detailAddress);
		this.loadDeliveryTime(order);
		return this.fitOrderService.save(order.initOrderNumber().initPrice());
	}

	@Override
	public FitOrder auditOrder(Long orderId, FitEmployee auditor, String action) throws Exception {
		FitOrder order = this.findOne(orderId);
		if (null != order) {
			switch (action) {
			case "AGREE":
				order.setStatus(AuditStatus.AUDIT_SUCCESS);
				order.setAuditTime(new Date());
				break;
			case "REJECT":
				order.setStatus(AuditStatus.AUDIT_FAILURE);
				order.setAuditTime(new Date());
				break;
			case "DELETE":
				if (order.getStatus().equals(AuditStatus.AUDIT_FAILURE)) {
					order.setIsDelete(true);
				}
				break;
			}
		}
		order.setAuditorId(auditor.getId());
		order.setAuditorName(auditor.getName());
		order.setAuditorMobile(auditor.getMobile());
		return this.fitOrderService.save(order);
	}

	@Override
	public FitOrder findOne(Long id) throws Exception {
		return this.fitOrderService.findOne(id);
	}

	@Override
	public Page<FitOrder> loadOrderByEmployeeId(Long employeeId, Integer page, Integer size) throws Exception {
		return this.fitOrderService.findByEmployeeId(employeeId, page, size);
	}

	@Override
	public Page<FitOrder> loadOrderByCompanyId(Long companyId, Integer page, Integer size) throws Exception {
		return this.fitOrderService.findByCompanyId(companyId, page, size);
	}

	@Override
	public FitOrder loadDeliveryTime(FitOrder order) throws Exception {

		Long companyId = order.getCompanyId();
		FitCompany company = fitCompanyService.findOne(companyId);
		TdCity city = tdCityService.findBySobIdCity(company.getSobId());

		int hour = Calendar.HOUR_OF_DAY;
		int minute = Calendar.MINUTE;

		Calendar cal = Calendar.getInstance();

		Long delay = city.getDelayHour();
		if (null == delay) {
			delay = 0L;
		}

		Date limitDate = new Date();
		Long tempHour = hour + delay;
		if (24 <= tempHour) {
			cal.add(Calendar.DATE, 1);
			limitDate = cal.getTime();
			tempHour = 9L;
		}

		// 判断能否当天配送
		if (tempHour > city.getFinishHour() || (tempHour == city.getFinishHour() && minute > city.getFinishMinute())) {
			cal.add(Calendar.DATE, 1);
			limitDate = cal.getTime();
			tempHour = 9L;
		}

		order.setEarlyDate(new SimpleDateFormat("yyyy-MM-dd").format(limitDate));
		order.setDeliveryDate(order.getEarlyDate());
		order.setEarlyTime(tempHour);
		order.setDeliveryTime(order.getEarlyTime());
		return order;
	}

	@Override
	public FitOrder saveRemark(FitOrder order, String remark) throws Exception {
		remark.replace("\"", "&quot;").replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
		order.setRemark(remark);
		return fitOrderService.save(order);
	}

	@Override
	public FitOrder saveDelivery(FitOrder order, String deliveryDate, Long deliveryTime, Long floor) throws Exception {
		order.setDeliveryDate(deliveryDate);
		order.setDeliveryTime(deliveryTime);
		order.setFloor(floor);
		return this.fitOrderService.save(order);
	}

	@Override
	public TdOrder transformer(FitOrder fitOrder) throws Exception {
		TdOrder order = new TdOrder();
		order.setOrderGoodsList(this.transOrderGoods(fitOrder));
		order.setDeliverTypeTitle("送货上门");
		order.setOrderNumber("XN" + fitOrder.getOrderNumber());
		order.setOrderTime(fitOrder.getOrderTime());
		order.setPayTime(new Date());
		order.setPayTypeId(0L);
		order.setPayTypeTitle("其它");
		order.setShippingAddress(fitOrder.getReceiveAddress());
		order.setShippingName(fitOrder.getReceiveName());
		order.setShippingPhone(fitOrder.getReceivePhone());
		order.setStatusId(3L);
		order.setTotalGoodsPrice(fitOrder.getTotalGoodsPrice());
		order.setTotalPrice(0d);
		order.setUsername("FIT" + fitOrder.getEmployeeMobile());
		order.setCashCoupon(0d);
		order.setDeliveryDate(fitOrder.getDeliveryDate());
		order.setDiySiteId(fitOrder.getCompanyId());
		order.setDiySiteName(fitOrder.getCompanyTitle());
		order.setRemark(fitOrder.getRemark());
		order.setActualPay(0d);
		order.setCashBalanceUsed(0d);
		order.setProductCouponId("");
		order.setUnCashBalanceUsed(fitOrder.getBalancePayed() + fitOrder.getUpstairsBalancePayed());
		order.setDeliverFee(fitOrder.getDeliveryFee());
		order.setDeliveryDetailId(fitOrder.getDeliveryTime());
		order.setDiySitePhone(fitOrder.getAuditorMobile());
		order.setLimitCash(0d);
		order.setUserId(fitOrder.getEmployeeId());

		FitCompany company = fitCompanyService.findOne(fitOrder.getCompanyId());
		TdCity city = tdCityService.findBySobIdCity(company.getSobId());
		
		order.setCity(city.getCityName());
		order.setDetailAddress(null);
		order.setDisctrict(null);
		order.setProvince(null);
		order.setSubdistrict(null);
		order.setDiySiteCode(company.getCode());
		order.setAllActualPay(0d);
		order.setAllTotalPay(0d);
		order.setSellerId(fitOrder.getAuditorId());
		order.setSellerUsername(fitOrder.getAuditorMobile());
		order.setSellerRealName(fitOrder.getAuditorName());
		order.setRealUserId(fitOrder.getEmployeeId());
		order.setRealUserUsername("FIT" + fitOrder.getEmployeeMobile());
		order.setOtherPay(0d);
		order.setActivitySubPrice(0d);
		order.setCashPay(0d);
		order.setPosPay(0d);
		order.setBackOtherPay(0d);
		order.setFloor(fitOrder.getFloor());
		order.setUpstairsBalancePayed(fitOrder.getUpstairsBalancePayed());
		order.setUpstairsFee(fitOrder.getUpstairsFee());
		order.setUpstairsLeftFee(fitOrder.getUpstairsLeftFee());
		order.setUpstairsOtherPayed(fitOrder.getUpstairsOtherPayed());
		order.setUpstairsType("免费上楼");
		return order;
	}

	private List<TdOrderGoods> transOrderGoods(FitOrder fitOrder) {
		List<TdOrderGoods> result = new ArrayList<>();
		List<FitOrderGoods> orderGoodsList = fitOrder.getOrderGoodsList();

		for (FitOrderGoods fitOrderGoods : orderGoodsList) {
			TdOrderGoods orderGoods = new TdOrderGoods();
			orderGoods.setGoodsCoverImageUri(fitOrderGoods.getGoodsCoverImageUri());
			orderGoods.setGoodsId(fitOrderGoods.getGoodsId());
			orderGoods.setGoodsTitle(fitOrderGoods.getGoodsTitle());
			orderGoods.setPrice(fitOrderGoods.getPrice());
			orderGoods.setRealPrice(fitOrderGoods.getRealPrice());
			orderGoods.setSku(fitOrderGoods.getGoodsSku());
			orderGoods.setBrandId(fitOrderGoods.getBrandId());
			orderGoods.setBrandTitle(fitOrderGoods.getBrandTitle());
			orderGoods.setRealPrice(fitOrderGoods.getRealPrice());
			orderGoods.setCashNumber(0L);
			this.tdOrderGoodsService.save(orderGoods);
			result.add(orderGoods);
		}
		return result;
	}

}
