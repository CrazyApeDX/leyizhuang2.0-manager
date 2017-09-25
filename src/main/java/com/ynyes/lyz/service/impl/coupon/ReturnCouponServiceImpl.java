package com.ynyes.lyz.service.impl.coupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.text.DecimalFormat;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.untable.CouponInfo;
import com.ynyes.lyz.excp.AppErrorParamsExcp;
import com.ynyes.lyz.service.TdCouponService;
import com.ynyes.lyz.service.basic.coupon.IReturnCouponService;

/**
 * @author 作者：CrazyDX
 * @version 版本：2016年10月27日下午1:37:25
 */
@Service
@Transactional
public class ReturnCouponServiceImpl implements IReturnCouponService {

	@Autowired
	private TdCouponService tdCouponService;

	@Override
	public List<CouponInfo> getCouponInfo(TdOrder order) {
		if (null == order) {
			throw new AppErrorParamsExcp("未获取到券订单信息");
		} else {
			Map<Long, Long> group = this.getGoodsIdAndQuantityGroup(order);
			List<TdCoupon> couponList = tdCouponService
					.findByCouponOrderNumberAndIsUsedFalseAndIsOutDateFalse(order.getOrderNumber());
			Map<Long, CouponInfo> map = new HashMap<>();
			List<CouponInfo> list = new ArrayList<>();
			for (TdCoupon coupon : couponList) {
				CouponInfo couponInfo = map.get(coupon.getGoodsId());
				// 如果指定商品id的优惠券信息不存在，则新生成一个
				if (null == couponInfo) {
					couponInfo = new CouponInfo();
					couponInfo.setGoodsId(coupon.getGoodsId());
					couponInfo.setQuantity(0l);
					couponInfo.setTradeQuantity(0l);
					couponInfo.setTotalPrice(0d);
					couponInfo.setTradePrice(coupon.getTradePrice());
					couponInfo.setReturnUnit(0d);
					couponInfo.setGoodsTitle(coupon.getGoodsName());
					couponInfo.setGoodsCoverImageUri(coupon.getPicUri());
					couponInfo.setPrice(coupon.getBuyPrice());
				}
				
				couponInfo.setQuantity(couponInfo.getQuantity() + 1);
				if (null == coupon.getBuyPrice()) {
					coupon.setBuyPrice(0.00);
				}
				couponInfo.setTotalPrice(couponInfo.getTotalPrice() + coupon.getBuyPrice());
				map.put(coupon.getGoodsId(), couponInfo);
			}
			for (CouponInfo couponInfo : map.values()) {
				Long tradeQuantity = group.get(couponInfo.getGoodsId());
				couponInfo.setTradeQuantity(tradeQuantity);
				if (null != couponInfo) {
					list.add(couponInfo);
				}
			}
			
			this.getReturnPrice(list, order);
			
			return list;
		}
	}
	
	@Override
	public Map<Long, Long> getGoodsIdAndQuantityGroup(TdOrder order) {
		if (null == order) {
			throw new AppErrorParamsExcp("未获取到券订单信息");
		} else {
			Map<Long, Long> group = new HashMap<>();
			
			List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
			if (null != orderGoodsList && orderGoodsList.size() > 0) {
				for (TdOrderGoods orderGoods : orderGoodsList) {
					Long goodsId = orderGoods.getGoodsId();
					Long quantity = orderGoods.getQuantity();
			
					Long goodsQuantity = group.get(goodsId);
					if (null == goodsQuantity) {
						goodsQuantity = 0l;
					}
					goodsQuantity += quantity;
					group.put(goodsId, goodsQuantity);
				}
			}
			
			List<TdOrderGoods> presentedList = order.getPresentedList();
			if (null != presentedList && presentedList.size() > 0) {
				for (TdOrderGoods orderGoods : orderGoodsList) {
					Long goodsId = orderGoods.getGoodsId();
					Long quantity = orderGoods.getQuantity();
			
					Long goodsQuantity = group.get(goodsId);
					if (null == goodsQuantity) {
						goodsQuantity = 0l;
					}
					goodsQuantity += quantity;
					group.put(goodsId, goodsQuantity);
				}
			}
			
			List<TdOrderGoods> giftGoodsList = order.getGiftGoodsList();
			if (null != giftGoodsList && giftGoodsList.size() > 0) {
				for (TdOrderGoods orderGoods : orderGoodsList) {
					Long goodsId = orderGoods.getGoodsId();
					Long quantity = orderGoods.getQuantity();
			
					Long goodsQuantity = group.get(goodsId);
					if (null == goodsQuantity) {
						goodsQuantity = 0l;
					}
					goodsQuantity += quantity;
					group.put(goodsId, goodsQuantity);
				}
			}
			return group;
		}
	}

	@Override
	public void getReturnPrice(List<CouponInfo> couponList, TdOrder order) {
		Double totalPrice = order.getTotalPrice();
		Double cashBalanceUsed = order.getCashBalanceUsed();
		Double unCashBalanceUsed = order.getUnCashBalanceUsed();
		if (null == cashBalanceUsed) {
			cashBalanceUsed = 0d;
		}
		if (null == unCashBalanceUsed) {
			unCashBalanceUsed = 0d;
		}
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		// 实际的总支付价格（去除了指定产品现金券）
		Double actualTotal = totalPrice + cashBalanceUsed + unCashBalanceUsed;
		
		Map<Long, Double> cashProCouponUsed = this.getCashProductCouponUsedByGoodsId(order);
		
		// 计算商品价值
		Double allTradeTotal = 0d;
		for (CouponInfo couponInfo : couponList) {
			Double tradePrice = couponInfo.getTradePrice();
			Long tradeQuantity = couponInfo.getTradeQuantity();
			allTradeTotal += (tradePrice * tradeQuantity);
			Double couponUsed = cashProCouponUsed.get(couponInfo.getGoodsId());
			if (null == couponUsed) {
				couponUsed = 0d;
			}
			allTradeTotal -= couponUsed;
			couponInfo.setTradeTotal(tradePrice * tradeQuantity - couponUsed);
		}
		
		// 第二次循环计算退货单价
		for (CouponInfo couponInfo : couponList) {
			Double point = couponInfo.getTradeTotal() / allTradeTotal;
			if (point > 1d) {
				point = 1d;
			}
			couponInfo.setTotalPrice(actualTotal * point);
			String sUnit = df.format(couponInfo.getTotalPrice() / couponInfo.getTradeQuantity());
			couponInfo.setReturnUnit(NumberUtils.toDouble(sUnit));
		}
	}

	@Override
	public Map<Long, Double> getCashProductCouponUsedByGoodsId(TdOrder order) {
		Map<Long, Double> result = new HashMap<>();
		String cashCouponId = order.getCashCouponId();
		if (StringUtils.isNotBlank(cashCouponId)) {
			String[] group = cashCouponId.split(",");
			for (String sId : group) {
				TdCoupon coupon = tdCouponService.findOne(NumberUtils.toLong(sId));
				if (coupon.getTypeCategoryId().equals(2l)) {
					Long goodsId = coupon.getGoodsId();
					Double realPrice = coupon.getRealPrice();
					Double useCouponPrice = result.get(goodsId);
					if (null == useCouponPrice) {
						useCouponPrice = 0d;
					}
					useCouponPrice += realPrice;
					result.put(goodsId, useCouponPrice);
				}
			}
		}
		return result;
	}
}
