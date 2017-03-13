package com.ynyes.fitment.foundation.service.biz.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.ynyes.fitment.foundation.service.biz.BizCreditChangeLogService;
import com.ynyes.fitment.foundation.service.biz.BizOrderService;
import com.ynyes.lyz.entity.TdBrand;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.service.TdBrandService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdOrderGoodsService;
import com.ynyes.lyz.service.TdOrderService;

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

	@Autowired
	private TdBrandService tdBrandService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private BizCreditChangeLogService bizCreditChangeLogService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Override
	public FitOrder initOrder(List<FitCartGoods> cartGoodsList, String receiver, String receiverMobile,
			String baseAddress, String detailAddress, FitEmployee employee, String deliveryDate, Long deliveryTime,
			String remark) throws Exception {
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
		order.setDeliveryDate(deliveryDate);
		order.setDeliveryTime(deliveryTime);
		order.setRemark(remark);
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
		order.setUnCashBalanceUsed(0d);
		order.setDeliverFee(fitOrder.getDeliveryFee());
		order.setDeliveryDetailId(fitOrder.getDeliveryTime());
		order.setDiySitePhone(fitOrder.getAuditorMobile());
		order.setLimitCash(0d);
		order.setUserId(fitOrder.getEmployeeId());
		order.setCredit(fitOrder.getBalancePayed() + fitOrder.getUpstairsBalancePayed());

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
		order.setSellerUsername("FIT" + fitOrder.getAuditorMobile());
		order.setSellerRealName(fitOrder.getAuditorName());
		order.setRealUserId(fitOrder.getEmployeeId());
		order.setRealUserUsername("FIT" + fitOrder.getEmployeeMobile());
		order.setRealUserRealName(fitOrder.getEmployeeName());
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
			orderGoods.setQuantity(fitOrderGoods.getQuantity());
			orderGoods.setCashNumber(0L);
			this.tdOrderGoodsService.save(orderGoods);
			result.add(orderGoods);
		}
		return result;
	}

	@Override
	public Boolean validateEnoughCredit(FitOrder order) throws Exception {
		FitCompany company = this.fitCompanyService.findOne(order.getCompanyId());
		Double leftPrice = order.getLeftPrice();
		if (leftPrice > company.getCredit()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void finishOrder(FitOrder fitOrder) throws Exception {
		this.costCredit(fitOrder);
		this.costInventory(fitOrder);
		TdOrder order = this.transformer(fitOrder);
		Map<Long, TdOrder> subOrderMap = new HashMap<>();
		this.disminlateGoods(order, subOrderMap);
		this.disminlateCredit(order, subOrderMap);
		this.sendOrder(subOrderMap, order);
		fitOrder.setIsFinish(true);
		fitOrderService.save(fitOrder);
	}

	private void costCredit(FitOrder order) throws Exception {
		Long companyId = order.getCompanyId();
		FitCompany company = this.fitCompanyService.findOne(companyId);
		order.setBalancePayed(order.getLeftPrice());
		company.setCredit(company.getCredit() - order.getLeftPrice());
		this.fitOrderService.save(order);
		this.fitCompanyService.save(company);
		this.bizCreditChangeLogService.consumeLog(company, order);
	}

	private void disminlateGoods(TdOrder mainOrder, Map<Long, TdOrder> subOrderMap) throws Exception {
		List<TdOrderGoods> orderGoodsList = mainOrder.getOrderGoodsList();
		if (CollectionUtils.isNotEmpty(orderGoodsList)) {
			for (TdOrderGoods orderGoods : orderGoodsList) {
				Long brandId = orderGoods.getBrandId();
				TdOrder subOrder = subOrderMap.get(brandId);
				if (null == subOrder) {
					subOrder = new TdOrder();
					TdBrand brand = tdBrandService.findOne(brandId);
					subOrder.setOrderNumber(mainOrder.getOrderNumber().replace("XN", brand.getShortName()));
					// 初始化分单信息
					subOrder = this.initSubOrder(mainOrder, subOrder);
				}
				subOrder.getOrderGoodsList().add(orderGoods);
				subOrder.setTotalGoodsPrice(
						subOrder.getTotalGoodsPrice() + orderGoods.getPrice() * orderGoods.getQuantity());
				subOrder.setTotalPrice(subOrder.getTotalPrice() + orderGoods.getRealPrice() * orderGoods.getQuantity());
				tdOrderService.save(subOrder);
				subOrderMap.put(brandId, subOrder);
			}
		}
	}

	private TdOrder initSubOrder(TdOrder mainOrder, TdOrder subOrder) throws Exception {
		subOrder.setDeliverFee(0d);
		subOrder.setTotalGoodsPrice(0d);
		subOrder.setTotalPrice(0d);
		subOrder.setLimitCash(0d);
		subOrder.setCashCoupon(0d);
		subOrder.setProductCoupon("");
		subOrder.setCashCouponId("");
		subOrder.setProductCouponId("");
		subOrder.setStatusId(3L);
		subOrder.setOrderGoodsList(new ArrayList<TdOrderGoods>());
		subOrder.setPresentedList(new ArrayList<TdOrderGoods>());
		subOrder.setGiftGoodsList(new ArrayList<TdOrderGoods>());
		subOrder.setPayTime(new Date());

		subOrder.setOtherIncome(0d);
		subOrder.setPosPay(0d);
		subOrder.setCashPay(0d);
		subOrder.setBackOtherPay(0d);

		subOrder.setCashBalanceUsed(0d);
		subOrder.setUnCashBalanceUsed(0d);

		getCommonFields(subOrder, mainOrder);
		return subOrder;
	}

	private void getCommonFields(TdOrder subOrder, TdOrder mainOrder) throws Exception {
		// 设置基础信息
		subOrder.setProvince(mainOrder.getProvince());
		subOrder.setCity(mainOrder.getCity());
		subOrder.setDisctrict(mainOrder.getDisctrict());
		subOrder.setSubdistrict(mainOrder.getSubdistrict());
		subOrder.setDetailAddress(mainOrder.getDetailAddress());
		subOrder.setDiySiteId(mainOrder.getDiySiteId());
		subOrder.setDiySiteCode(mainOrder.getDiySiteCode());
		subOrder.setDiySiteName(mainOrder.getDiySiteName());
		subOrder.setDiySitePhone(mainOrder.getDiySitePhone());
		subOrder.setShippingAddress(mainOrder.getShippingAddress());
		subOrder.setShippingName(mainOrder.getShippingName());
		subOrder.setShippingPhone(mainOrder.getShippingPhone());
		subOrder.setDeliverTypeTitle(mainOrder.getDeliverTypeTitle());
		subOrder.setDeliveryDate(mainOrder.getDeliveryDate());
		subOrder.setDeliveryDetailId(mainOrder.getDeliveryDetailId());
		subOrder.setUserId(mainOrder.getUserId());
		subOrder.setUsername(mainOrder.getUsername());
		subOrder.setPayTypeId(mainOrder.getPayTypeId());
		subOrder.setPayTypeTitle(mainOrder.getPayTypeTitle());
		subOrder.setOrderTime(mainOrder.getOrderTime());
		subOrder.setRemark(mainOrder.getRemark());
		subOrder.setAllTotalPay(mainOrder.getTotalPrice());
		subOrder.setUpstairsType(mainOrder.getUpstairsType());
		subOrder.setFloor(mainOrder.getFloor());
		subOrder.setUpstairsFee(mainOrder.getUpstairsFee());
		subOrder.setUpstairsBalancePayed(mainOrder.getUpstairsBalancePayed());
		subOrder.setUpstairsOtherPayed(mainOrder.getUpstairsOtherPayed());
		subOrder.setUpstairsLeftFee(subOrder.getUpstairsLeftFee());

		// 设置销顾信息
		subOrder.setSellerId(mainOrder.getSellerId());
		subOrder.setSellerRealName(mainOrder.getSellerRealName());
		subOrder.setSellerUsername(mainOrder.getSellerUsername());

		// 设置真实用户信息
		subOrder.setIsSellerOrder(mainOrder.getIsSellerOrder());
		subOrder.setHaveSeller(mainOrder.getHaveSeller());
		subOrder.setRealUserId(mainOrder.getRealUserId());
		subOrder.setRealUserRealName(mainOrder.getRealUserRealName());
		subOrder.setRealUserUsername(mainOrder.getRealUserUsername());

		// 设置主单号
		subOrder.setMainOrderNumber(mainOrder.getOrderNumber());

		// 设置是否是电子券
		subOrder.setIsCoupon(mainOrder.getIsCoupon());
	}

	private void disminlateCredit(TdOrder mainOrder, Map<Long, TdOrder> subOrderMap) throws Exception {
		Double totalPrice = mainOrder.getTotalPrice() + mainOrder.getUnCashBalanceUsed()
				+ mainOrder.getCashBalanceUsed();
		for (TdOrder subOrder : subOrderMap.values()) {
			if (null != subOrder) {
				Double subPrice = subOrder.getTotalPrice();
				if (null == subPrice || 0.00 == subPrice) {
					subOrder.setCredit(0.00);
				} else {
					Double point;
					if (totalPrice == 0) {
						point = 1.0;
					} else {
						point = subPrice / totalPrice;
					}
					// 比例不能大于1
					if (point > 1) {
						point = 1.0;
					}
					if (null != point) {
						DecimalFormat df = new DecimalFormat("#.00");
						String scale2_uncash = df.format(mainOrder.getCredit() * point);
						if (null == scale2_uncash) {
							scale2_uncash = "0.00";
						}
						subOrder.setCredit(Double.parseDouble(scale2_uncash));
						// subOrder.setActualPay(subOrder.getUnCashBalanceUsed()
						// + subOrder.getCashBalanceUsed());
						subOrder.setPoint(point);
						// String leftPrice = df.format(subOrder.getTotalPrice()
						// - subOrder.getCredit());
						subOrder.setTotalPrice(0d);
						// subOrder.setTotalPrice(Double.parseDouble(leftPrice));

						subOrder = tdOrderService.save(subOrder);
					}
				}
			}
		}
	}

	private void sendOrder(Map<Long, TdOrder> subOrderMap, TdOrder mainOrder) throws Exception {
		String mainOrderNumber = mainOrder.getOrderNumber();
		Boolean sendWMS = true;
		List<TdOrder> sendOrders = new ArrayList<>();
		for (TdOrder subOrder : subOrderMap.values()) {
			if (!("送货上门".equals(subOrder.getDeliverTypeTitle()))) {
				sendWMS = false;
			}
			subOrder = tdOrderService.save(subOrder);
			sendOrders.add(subOrder);
		}

		if (sendWMS) {
			tdCommonService.sendWms(sendOrders, mainOrderNumber, 0d);
		}
		tdCommonService.sendEbs(sendOrders);
	}

	private void costInventory(FitOrder order) {
		Long companyId = order.getCompanyId();
		Long regionId = 0L;
		try {
			FitCompany company = fitCompanyService.findOne(companyId);
			regionId = company.getSobId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<FitOrderGoods> orderGoodsList = order.getOrderGoodsList();
		for (FitOrderGoods orderGoods : orderGoodsList) {
			Long goodsId = orderGoods.getGoodsId();
			Long quantity = orderGoods.getQuantity();

			TdGoods goods = tdGoodsService.findOne(goodsId);
			tdDiySiteInventoryService.costCityInventory(regionId, goods, quantity * -1, order.getOrderNumber(),
					order.getEmployeeMobile(), "配送发货");
		}
	}

	@Override
	public Boolean validateEnoughInventory(FitOrder order) throws Exception {
		Long companyId = order.getCompanyId();
		FitCompany company = this.fitCompanyService.findOne(companyId);
		Long sobId = company.getSobId();
		List<FitOrderGoods> orderGoodsList = order.getOrderGoodsList();
		for (FitOrderGoods orderGoods : orderGoodsList) {
			Long goodsId = orderGoods.getGoodsId();
			Long quantity = orderGoods.getQuantity();
			TdDiySiteInventory inventory = this.tdDiySiteInventoryService
					.findByGoodsIdAndRegionIdAndDiySiteIdIsNull(goodsId, sobId);
			if (null == inventory || inventory.getInventory() < quantity) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Map<String, Object> loadDeliveryTimeInforBaseNow(FitEmployee employee) {
		Map<String, Object> res = new HashMap<>();
		Long companyId = employee.getCompanyId();
		FitCompany company = fitCompanyService.findOne(companyId);
		TdCity city = tdCityService.findBySobIdCity(company.getSobId());

		Date now = new Date();

		int hour = Integer.parseInt(new SimpleDateFormat("HH").format(now));

		int minute = Integer.parseInt(new SimpleDateFormat("mm").format(now));

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

		res.put("EARLY_DATE", new SimpleDateFormat("yyyy-MM-dd").format(limitDate));
		res.put("EARLY_TIME", tempHour);
		return res;
	}

	@Override
	public FitOrder checkDeliveryInfo(FitOrder order, FitEmployee employee) throws Exception {
		Map<String, Object> result = this.loadDeliveryTimeInforBaseNow(employee);
		order.setEarlyDate((String) result.get("EARLY_DATE"));
		order.setEarlyTime((Long) result.get("EARLY_TIME"));

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Date selectedDate = format.parse(order.getDeliveryDate());
		Date limitDate = format.parse(order.getEarlyDate());

		if (selectedDate.getTime() < limitDate.getTime()) {
			order.setDeliveryDate(order.getEarlyDate());
		}

		if (order.getDeliveryDate().equalsIgnoreCase(order.getEarlyDate())) {
			if (order.getEarlyTime() > order.getDeliveryTime()) {
				order.setDeliveryTime(order.getEarlyTime());
			}
		}

		return this.fitOrderService.save(order);
	}

}
