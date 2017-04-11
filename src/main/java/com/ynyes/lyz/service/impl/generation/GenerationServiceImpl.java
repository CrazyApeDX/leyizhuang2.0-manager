package com.ynyes.lyz.service.impl.generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.interfaces.entity.TdOrderCouponInf;
import com.ynyes.lyz.interfaces.entity.TdOrderGoodsInf;
import com.ynyes.lyz.interfaces.entity.TdOrderInf;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.service.TdOrderCouponInfService;
import com.ynyes.lyz.interfaces.service.TdOrderGoodsInfService;
import com.ynyes.lyz.interfaces.service.TdOrderInfService;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.interfaces.utils.StringTools;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCouponService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.basic.generation.IGenerationService;

@Service
@Transactional
public class GenerationServiceImpl implements IGenerationService {

	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdPriceCountService tdPriceCountService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdOrderInfService tdOrderInfService;

	@Autowired
	private TdOrderGoodsInfService tdOrderGoodsInfService;

	@Autowired
	private TdOrderCouponInfService tdOrderCouponInfService;

	@Autowired
	private TdCouponService tdCouponService;

	@Override
	public String generateOrderData(TdOrder order) {
		StringBuffer sb = new StringBuffer();
		TdOrderInf orderInf = this.generateOrderInf(order);
		List<TdOrderGoodsInf> orderGoodsInfList = this.generateOrderGoodsInf(order, orderInf);
		List<TdOrderCouponInf> orderCoouponInfList = this.generateOrderCouponInf(order, orderInf);
		sb.append("订单信息").append("\n");
		sb.append(orderInf.toString()).append("\n");
		sb.append("商品信息").append("\n");
		for (TdOrderGoodsInf orderGoodsInf : orderGoodsInfList) {
			sb.append("\t").append(orderGoodsInf.toString()).append("\n");
		}
		sb.append("优惠券信息").append("\n");
		for (TdOrderCouponInf orderCouponInf : orderCoouponInfList) {
			sb.append("\t").append(orderCouponInf.toString()).append("\n");
		}
		sb.append("--------------------------------------------------------------");
		return sb.toString();
	}

	@Override
	public TdOrderInf generateOrderInf(TdOrder order) {
		TdOrderInf orderInf = tdOrderInfService.findByOrderNumber(order.getOrderNumber());
		if (null == orderInf) {
			orderInf = tdInterfaceService.initOrderInf(order);
			String orderInfXML = tdInterfaceService.XmlWithObject(orderInf, INFTYPE.ORDERINF);
			Object[] orderInfObject = { INFConstants.INF_ORDER_STR, "1", orderInfXML };
			String resultStr = tdInterfaceService.ebsWsInvoke(orderInfObject);
			if (StringUtils.isBlank(resultStr)) {
				orderInf.setSendFlag(0);
			} else {
				orderInf.setSendFlag(1);
				orderInf.setErrorMsg(resultStr);
			}
			tdOrderInfService.save(orderInf);
		}
		return orderInf;
	}

	@Override
	public List<TdOrderGoodsInf> generateOrderGoodsInf(TdOrder order, TdOrderInf orderInf) {
		List<TdOrderGoodsInf> orderGoodsInfList = new ArrayList<>();

		List<TdOrderGoodsInf> sendList = new ArrayList<>();

		String cityName = order.getCity();
		TdCity city = tdCityService.findByCityName(cityName);

		List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
		if (!CollectionUtils.isEmpty(orderGoodsList)) {
			for (TdOrderGoods orderGoods : orderGoodsList) {
				TdOrderGoodsInf orderGoodsInf = tdOrderGoodsInfService
						.findByOrderHeaderIdAndSkuAndGiftFlag(orderInf.getHeaderId(), orderGoods.getSku(), "N");
				if (null == orderGoodsInf) {
					orderGoodsInf = new TdOrderGoodsInf();
					orderGoodsInf.setOrderHeaderId(orderInf.getHeaderId());
					orderGoodsInf.setGoodsId(orderGoods.getGoodsId());
					orderGoodsInf.setGoodsTitle(orderGoods.getGoodsTitle());
					orderGoodsInf.setGoodsSubTitle(orderGoods.getGoodsSubTitle());
					orderGoodsInf.setSku(orderGoods.getSku());
					orderGoodsInf.setQuantity(orderGoods.getQuantity());
					orderGoodsInf.setLsPrice(tdPriceCountService.getGoodsPrice(city.getSobIdCity(), orderGoods));
					orderGoodsInf.setGiftFlag("N");
					orderGoodsInf.setPromotion(orderGoods.getActivityId());
					tdOrderGoodsInfService.save(orderGoodsInf);
					sendList.add(orderGoodsInf);
				}

				orderGoodsInfList.add(orderGoodsInf);
			}
		}

		List<TdOrderGoods> presentedList = order.getPresentedList();
		if (!CollectionUtils.isEmpty(presentedList)) {
			for (TdOrderGoods orderGoods : presentedList) {
				TdOrderGoodsInf orderGoodsInf = tdOrderGoodsInfService
						.findByOrderHeaderIdAndSkuAndGiftFlag(orderInf.getHeaderId(), orderGoods.getSku(), "Y");
				if (null == orderGoodsInf) {
					orderGoodsInf = new TdOrderGoodsInf();
					orderGoodsInf.setOrderHeaderId(orderInf.getHeaderId());
					orderGoodsInf.setGoodsId(orderGoods.getGoodsId());
					orderGoodsInf.setGoodsTitle(orderGoods.getGoodsTitle());
					orderGoodsInf.setGoodsSubTitle(orderGoods.getGoodsSubTitle());
					orderGoodsInf.setSku(orderGoods.getSku());
					orderGoodsInf.setQuantity(orderGoods.getQuantity());
					orderGoodsInf.setLsPrice(tdPriceCountService.getGoodsPrice(city.getSobIdCity(), orderGoods));
					orderGoodsInf.setGiftFlag("Y");
					orderGoodsInf.setPromotion(orderGoods.getActivityId());
					tdOrderGoodsInfService.save(orderGoodsInf);
					sendList.add(orderGoodsInf);
				}

				orderGoodsInfList.add(orderGoodsInf);
			}
		}

		List<TdOrderGoods> giftGoodsList = order.getGiftGoodsList();
		if (!CollectionUtils.isEmpty(giftGoodsList)) {
			for (TdOrderGoods orderGoods : giftGoodsList) {
				TdOrderGoodsInf orderGoodsInf = tdOrderGoodsInfService
						.findByOrderHeaderIdAndSkuAndGiftFlag(orderInf.getHeaderId(), orderGoods.getSku(), "Y");
				if (null == orderGoodsInf) {
					orderGoodsInf = new TdOrderGoodsInf();
					orderGoodsInf.setOrderHeaderId(orderInf.getHeaderId());
					orderGoodsInf.setGoodsId(orderGoods.getGoodsId());
					orderGoodsInf.setGoodsTitle(orderGoods.getGoodsTitle());
					orderGoodsInf.setGoodsSubTitle(orderGoods.getGoodsSubTitle());
					orderGoodsInf.setSku(orderGoods.getSku());
					orderGoodsInf.setQuantity(orderGoods.getQuantity());
					orderGoodsInf.setLsPrice(tdPriceCountService.getGoodsPrice(city.getSobIdCity(), orderGoods));
					orderGoodsInf.setGiftFlag("Y");
					orderGoodsInf.setPromotion(orderGoods.getActivityId());
					tdOrderGoodsInfService.save(orderGoodsInf);
					sendList.add(orderGoodsInf);
				}
				orderGoodsInfList.add(orderGoodsInf);
			}
		}
		String orderGoodsInfXML = tdInterfaceService.XmlWithObject(sendList, INFTYPE.ORDERGOODSINF);
		if (StringUtils.isNotBlank(orderGoodsInfXML)) {
			Object[] orderGoodsInf = { INFConstants.INF_ORDER_GOODS_STR, "1", orderGoodsInfXML };
			String resultStr1 = tdInterfaceService.ebsWsInvoke(orderGoodsInf);
			for (int i = 0; i < orderGoodsInfList.size(); i++) {
				if (StringUtils.isBlank(resultStr1)) {
					orderGoodsInfList.get(i).setSendFlag(0);
				} else {
					orderGoodsInfList.get(i).setSendFlag(1);
					orderGoodsInfList.get(i).setErrorMsg(resultStr1);
				}
			}

			tdOrderGoodsInfService.save(orderGoodsInfList);
		}
		return sendList;
	}

	@Override
	public List<TdOrderCouponInf> generateOrderCouponInf(TdOrder order, TdOrderInf orderInf) {
		List<TdOrderCouponInf> resultCouponList = new ArrayList<>();
		Map<Integer, List<TdCoupon>> result = this.clearUpOrderCoupon(order);

		// 产品券查漏补缺
		resultCouponList = this.validateByCouponTypeId(orderInf, result, resultCouponList, 1);
		// 产品现金券查漏补缺
		resultCouponList = this.validateByCouponTypeId(orderInf, result, resultCouponList, 2);
		// 通用现金券查漏补缺
		resultCouponList = this.validateByCouponTypeId(orderInf, result, resultCouponList, 3);
		// 电子产品券查漏补缺
		resultCouponList = this.validateByCouponTypeId(orderInf, result, resultCouponList, 4);

		String orderCouponInfXML = tdInterfaceService.XmlWithObject(resultCouponList, INFTYPE.ORDERCOUPONINF);
		if (StringUtils.isNotBlank(orderCouponInfXML)) {
			Object[] orderCouponInf = { INFConstants.INF_ORDER_COUPON_STR, "1", orderCouponInfXML };
			String sendResult = tdInterfaceService.ebsWsInvoke(orderCouponInf);
			for (int i = 0; i < resultCouponList.size(); i++) {
				if (StringUtils.isBlank(sendResult)) {
					resultCouponList.get(i).setSendFlag(0);
				} else {
					resultCouponList.get(i).setSendFlag(1);
					resultCouponList.get(i).setErrorMsg(sendResult);
				}
			}
			tdOrderCouponInfService.save(resultCouponList);
		}
		return resultCouponList;
	}

	// 根据优惠券类型查找缺失的优惠券
	private List<TdOrderCouponInf> validateByCouponTypeId(TdOrderInf orderInf, Map<Integer, List<TdCoupon>> result,
			List<TdOrderCouponInf> resultCouponList, Integer couponTypeId) {
		// 查找接口表中的通用现金券
		List<TdOrderCouponInf> couponList = tdOrderCouponInfService
				.findByOrderHeaderIdAndCouponTypeId(orderInf.getHeaderId(), couponTypeId);
		if (null == couponList) {
			couponList = new ArrayList<>();
		}
		
		List<TdCoupon> list = result.get(couponTypeId);
		if (null == list) {
			list = new ArrayList<>();
		}
		
		if (list.size() != couponList.size()) {
			for (TdCoupon coupon : list) {
				TdOrderCouponInf existed = null;
				for (TdOrderCouponInf couponInf : couponList) {
					if (coupon.getRealPrice().equals(couponInf.getPrice())
							&& ((null == coupon.getSku() && null == couponInf.getSku())
									|| coupon.getSku().equals(couponInf.getSku()))) {
						existed = couponInf;
						break;
					}
				}
				if (null != existed) {
					couponList.remove(existed);
				} else {
					// 优惠券
					TdOrderCouponInf couponInf = new TdOrderCouponInf();
					couponInf.setOrderHeaderId(orderInf.getHeaderId());
					couponInf.setCouponTypeId(StringTools.coupontypeWithCoupon(coupon));
					couponInf.setSku(null == coupon.getSku() ? null : coupon.getSku().trim());
					couponInf.setQuantity(1L);
					if (null != coupon.getTypeCategoryId()) {
						Long type = coupon.getTypeCategoryId();
						if (type.longValue() == 1L) {
							couponInf.setPrice(coupon.getRealPrice());
						} else if (type.longValue() == 2L) {
							couponInf.setPrice(coupon.getRealPrice());
						} else if (type.longValue() == 3L) {
							if (null != coupon.getIsBuy() && coupon.getIsBuy()) {
								couponInf.setPrice(coupon.getBuyPrice());
							} else {
								couponInf.setPrice(0.00);
							}
						}
					}
					couponInf.setHistoryFlag(StringTools.getHistoryFlagByCouponType(coupon.getTypeCategoryId()));
					couponInf.setAttribute1(coupon.getSign());
					tdOrderCouponInfService.save(couponInf);
					resultCouponList.add(couponInf);
				}
			}
		}
		return resultCouponList;
	}

	// 整合一张订单使用的所有券的方法
	public Map<Integer, List<TdCoupon>> clearUpOrderCoupon(TdOrder order) {
		Map<Integer, List<TdCoupon>> result = new HashMap<>();

		String cashCouponId = order.getCashCouponId();
		String productCouponId = order.getProductCouponId();
		String couponIds = new StringBuffer().append(cashCouponId).append(productCouponId).toString();

		if (!StringUtils.isEmpty(couponIds)) {
			String[] idArray = couponIds.split(",");
			for (String sId : idArray) {
				Long id = Long.parseLong(sId);
				TdCoupon coupon = tdCouponService.findOne(id);
				Long typeCategoryId = coupon.getTypeCategoryId();
				if (typeCategoryId.equals(1L)) {
					Boolean isBuy = coupon.getIsBuy();
					if (null != isBuy && isBuy) {
						List<TdCoupon> list = result.get(5);
						if (null == list) {
							list = new ArrayList<>();
						}
						list.add(coupon);
						result.put(5, list);
					} else {
						List<TdCoupon> list = result.get(3);
						if (null == list) {
							list = new ArrayList<>();
						}
						list.add(coupon);
						result.put(3, list);
					}
				} else if (typeCategoryId.equals(2L)) {
					List<TdCoupon> list = result.get(2);
					if (null == list) {
						list = new ArrayList<>();
					}
					list.add(coupon);
					result.put(2, list);
				} else if (typeCategoryId.equals(3L)) {
					Boolean isBuy = coupon.getIsBuy();
					if (null != isBuy && isBuy) {
						List<TdCoupon> list = result.get(4);
						if (null == list) {
							list = new ArrayList<>();
						}
						list.add(coupon);
						result.put(4, list);
					} else {
						List<TdCoupon> list = result.get(1);
						if (null == list) {
							list = new ArrayList<>();
						}
						list.add(coupon);
						result.put(1, list);
					}
				} else {
					// do nothing!
				}
			}
		}
		
		// 创建一张临时现金券代表满减的金额
		Double activitySubPrice = order.getActivitySubPrice();
		if (null != activitySubPrice && activitySubPrice > 0) {
			TdCoupon coupon = new TdCoupon();
			coupon.setTypeCategoryId(1L);
			coupon.setRealPrice(activitySubPrice);
			List<TdCoupon> list = result.get(3);
			if (null == list) {
				list = new ArrayList<>();
			}
			list.add(coupon);
			result.put(3, list);
		}
		
		return result;
	}

	
}
