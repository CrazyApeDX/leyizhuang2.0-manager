package com.ynyes.lyz.service.basic.coupon;

import java.util.List;
import java.util.Map;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.untable.CouponInfo;

/**
 * <p>标题：IReturnCouponService.java</p>
 * <p>描述：退券相关流程</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年10月27日下午1:33:03
 */
public interface IReturnCouponService {

	List<CouponInfo> getCouponInfo(TdOrder order);
	
	Map<Long, Long> getGoodsIdAndQuantityGroup(TdOrder order);
	
	void getReturnPrice(List<CouponInfo> couponList, TdOrder order);
	
	Map<Long, Double> getCashProductCouponUsedByGoodsId(TdOrder order);
}
