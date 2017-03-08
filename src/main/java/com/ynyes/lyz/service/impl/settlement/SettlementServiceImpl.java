package com.ynyes.lyz.service.impl.settlement;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdBalanceLog;
import com.ynyes.lyz.entity.TdBrand;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdPayType;
import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.entity.delivery.TdDeliveryFeeHead;
import com.ynyes.lyz.entity.delivery.TdDeliveryFeeLine;
import com.ynyes.lyz.entity.delivery.TdOrderDeliveryFeeDetail;
import com.ynyes.lyz.entity.user.CreditChangeType;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.excp.AppErrorParamsExcp;
import com.ynyes.lyz.service.TdBalanceLogService;
import com.ynyes.lyz.service.TdBrandService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdCouponService;
import com.ynyes.lyz.service.TdDeliveryFeeHeadService;
import com.ynyes.lyz.service.TdDeliveryFeeLineService;
import com.ynyes.lyz.service.TdOrderDeliveryFeeDetailService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdPayTypeService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.service.basic.settlement.ISettlementService;


/**
 * <p>
 * 标题：SettlementServiceImpl.java
 * </p>
 * <p>
 * 描述：结算相关实现类
 * </p>
 * 
 * @author 作者：CrazyDX
 * @version 版本：2016年10月12日下午1:31:23
 */
@Service("settlementService")
@Transactional
public class SettlementServiceImpl implements ISettlementService {

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdPayTypeService tdPayTypeService;

	@Autowired
	private TdBrandService tdBrandService;

	@Autowired
	private TdCouponService tdCouponService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdBalanceLogService tdBalanceLogService;

	@Autowired
	private TdDeliveryFeeLineService tdDeliveryFeeLineService;

	@Autowired
	private TdSettingService tdSettingService;
	
	@Autowired
	private TdDeliveryFeeHeadService tdDeliveryFeeHeadService;
	
	@Autowired
	private TdOrderDeliveryFeeDetailService tdOrderDeliveryFeedetailService;

	@Override
	public void orderBasicValidate(TdOrder order) throws Exception {
		if (null == order) {
			throw new AppErrorParamsExcp("未能成功获取到订单信息");
		} else if (StringUtils.isBlank(order.getOrderNumber())) {
			throw new AppErrorParamsExcp("未能成功获取到订单信息");
		} else {
			// Passed the validation
		}
	}

	@Override
	public void orderAttributeValidate(TdOrder order, TdUser realUser, TdUser seller) throws Exception {
		// 验证真实用户和销顾信息
		realUserAndSellerValidate(realUser, seller);
		// 验证门店信息
		diySiteValidate(order);
		// 验证支付方式
		payTypeValidate(order);
		// 验证收货地址及配送方式等
		deliveryValidate(order, realUser, seller);
	}

	@Override
	public void mainOrderDataAction(TdOrder order, TdUser realUser) throws Exception {
		// 获取当单使用的总预存款额
		Double actualPay = order.getActualPay();
		if (null == actualPay) {
			actualPay = 0d;
		}
		// 获取用户的可提现余额和不可提现余额
		Double cashBalance = realUser.getCashBalance();
		if (null == cashBalance) {
			cashBalance = 0d;
		}
		Double unCashBalance = realUser.getUnCashBalance();
		if (null == unCashBalance) {
			unCashBalance = 0d;
		}
		// 开始进行逻辑判断
		if (unCashBalance >= actualPay) {
			order.setUnCashBalanceUsed(actualPay);
			order.setCashBalanceUsed(0d);
		} else {
			order.setUnCashBalanceUsed(unCashBalance);
			order.setCashBalanceUsed(actualPay - unCashBalance);
		}

		tdOrderService.save(order);
	}

	@Override
	public Map<String, Object> getClientResult(TdOrder order) throws Exception {
		Map<String, Object> result = new HashMap<>();
		Long payTypeId = order.getPayTypeId();
		TdPayType payType = tdPayTypeService.findOne(payTypeId);
		if (payType.getIsOnlinePay()) {
			if (order.getTotalPrice() > 0) {
				result.put("status", 3);
				result.put("title", payType.getTitle());
				result.put("order_number", order.getOrderNumber());
			} else {
				// 设置支付方式为其他
				order.setPayTypeTitle("其它");
				order.setPayTypeId(0L);

				order.setStatusId(3L);
				order.setUserId(order.getRealUserId());
				order.setUsername(order.getRealUserUsername());

				result.put("status", 0);
				result.put("message", "操作成功");
			}
		} else {
			if (order.getTotalPrice().equals(0d)) {
				// 设置支付方式为其他
				order.setPayTypeTitle("其它");
				order.setPayTypeId(0L);
			}

			order.setStatusId(3L);
			order.setUserId(order.getRealUserId());
			order.setUsername(order.getRealUserUsername());

			result.put("status", 0);
			result.put("message", "操作成功");
		}
		tdOrderService.save(order);
		return result;
	}

	@Override
	public void disminlate(HttpServletRequest req, TdOrder mainOrder, Boolean isFitmentOrder) throws Exception {
		Double deliveryFee = mainOrder.getDeliverFee();
		// 创建一个Map集合存储所有的分单，键值对规则为K-V：【品牌ID】-【分单】
		Map<Long, TdOrder> subOrderMap = new HashMap<>();
		// 拆分商品和小辅料
		disminlateOrderGoodsList(subOrderMap, mainOrder);
		// 拆分优惠券
		disminlateCoupon(subOrderMap, mainOrder);
		// 获取赠品
		getPresentAndGift(req, subOrderMap);
		// 设置运费
		setDeliveryFee(subOrderMap, mainOrder);
		// 拆分预存款
		disminlateBalance(subOrderMap, mainOrder);
		// 存储及发送订单
		saveAndSend(req, subOrderMap, mainOrder, deliveryFee);
	}

	/**
	 * <p>
	 * 描述：验证订单真实用户和销顾是否存在的方法
	 * </p>
	 * 
	 * @param realUser
	 *            订单的真实用户
	 * @param seller
	 *            订单的销售顾问
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月18日上午10:04:34
	 */
	private void realUserAndSellerValidate(TdUser realUser, TdUser seller) throws Exception {
		if (null == realUser) {
			throw new AppErrorParamsExcp("未能成功获取到真实用户信息");
		} else if (null == seller) {
			throw new AppErrorParamsExcp("请在\"配送方式\"中选择服务导购");
		} else {
			// Passed the validation
		}
	}

	private void diySiteValidate(TdOrder order) throws Exception {
		if (StringUtils.isBlank(order.getDiySiteCode())) {
			throw new AppErrorParamsExcp("请在\"配送方式\"中选择归属门店");
		} else if (StringUtils.isBlank(order.getDiySiteName())) {
			throw new AppErrorParamsExcp("请在\"配送方式\"中选择归属门店");
		} else if (StringUtils.isBlank(order.getDiySitePhone())) {
			throw new AppErrorParamsExcp("请在\"配送方式\"中选择归属门店");
		} else if (null == order.getDiySiteId()) {
			throw new AppErrorParamsExcp("请在\"配送方式\"中选择归属门店");
		} else {
			// Passed the validation
		}
	}

	/**
	 * <p>
	 * 描述：验证订单配送方式及收货地址的方法
	 * </p>
	 * 
	 * @param order
	 *            订单
	 * @param realUser 订单的真实用户
	 * @param seller
	 *            订单的销售顾问
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月18日上午10:05:39
	 */
	private void deliveryValidate(TdOrder order, TdUser realUser, TdUser seller) throws Exception {
		if (StringUtils.isBlank(order.getDeliveryDate())) {
			throw new AppErrorParamsExcp("请在\"配送方式\"中填写预约时间");
		} else {
			addressValidate(order);
			cashOnDeliveryValidate(order, realUser, seller);
		}
	}

	/**
	 * <p>
	 * 描述：验证收货地址的方法
	 * </p>
	 * 
	 * @param order
	 *            订单
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月18日上午10:07:26
	 */
	private void addressValidate(TdOrder order) throws Exception {
		if (!"门店自提".equals(order.getDeliverTypeTitle()) && !(order.getIsCoupon() != null && order.getIsCoupon())) {
			if (StringUtils.isBlank(order.getShippingAddress()) || StringUtils.isBlank(order.getShippingName())
					|| StringUtils.isBlank(order.getShippingPhone())) {
				throw new AppErrorParamsExcp("请填写收货地址");
			} else {
				// Passed the validation
			}
		} else {
			// Do no validate
		}
	}

	/**
	 * <p>
	 * 描述：验证是否允许货到付款的方法
	 * </p>
	 * 
	 * @param order
	 *            订单
	 * @param realUser
	 *            订单真实用户
	 * @param seller
	 *            订单的销售顾问
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月18日上午10:07:46
	 */
	private void cashOnDeliveryValidate(TdOrder order, TdUser realUser, TdUser seller) throws Exception {
		if ("货到付款".equalsIgnoreCase(order.getPayTypeTitle())) {
			Boolean realUserCashOnDelivery = realUser.getIsCashOnDelivery();
			if (null != realUserCashOnDelivery && !realUserCashOnDelivery) {
				throw new AppErrorParamsExcp("&nbsp;&nbsp;该用户不允许货到付款<br>请选择其他支付方式");
			} else {
				// Real user passed the validation
			}

			Boolean sellerCashOnDelivery = seller.getIsCashOnDelivery();
			if (null != sellerCashOnDelivery && !sellerCashOnDelivery) {
				throw new AppErrorParamsExcp("&nbsp;&nbsp;该销顾不允许货到付款<br>请选择其他支付方式");
			} else {
				// seller passed the validation
			}
		} else {
			// Do no validate
		}
	}

	/**
	 * <p>
	 * 描述：验证支付方式的方法
	 * </p>
	 * 
	 * @param order
	 *            订单
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月18日上午10:08:28
	 */
	private void payTypeValidate(TdOrder order) throws Exception {
		if (null == order.getPayTypeId()) {
			throw new AppErrorParamsExcp("请选择您的支付方式");
		} else if (StringUtils.isBlank(order.getPayTypeTitle())) {
			throw new AppErrorParamsExcp("请选择您的支付方式");
		} else {
			// Passed the validation
		}
	}

	/**
	 * <p>
	 * 描述：拆分商品的方法
	 * </p>
	 * 
	 * @param subOrderMap
	 *            分单列表
	 * @param orderGoodsList
	 *            商品列表
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月19日上午9:20:39
	 */
	private void disminlateOrderGoodsList(Map<Long, TdOrder> subOrderMap, TdOrder mainOrder) {
		List<TdOrderGoods> orderGoodsList = mainOrder.getOrderGoodsList();
		if (null != orderGoodsList && orderGoodsList.size() > 0) {
			for (TdOrderGoods orderGoods : orderGoodsList) {
				if (null != orderGoods) {
					Long brandId = orderGoods.getBrandId();
					// 从分单集合中取出相对应的订单
					TdOrder subOrder = subOrderMap.get(brandId);
					// 如果分单在集合中不存在，则创建一个
					if (null == subOrder) {
						subOrder = new TdOrder();
						TdBrand brand = tdBrandService.findOne(brandId);
						subOrder.setOrderNumber(mainOrder.getOrderNumber().replace("XN", brand.getShortName()));
						// 初始化分单信息
						initSubOrder(subOrder, mainOrder);
					}

					subOrder.getOrderGoodsList().add(orderGoods);
					subOrder.setTotalGoodsPrice(
							subOrder.getTotalGoodsPrice() + (orderGoods.getPrice() * orderGoods.getQuantity()));
					subOrder.setTotalPrice(
							subOrder.getTotalPrice() + (orderGoods.getPrice() * orderGoods.getQuantity()));
					subOrder = tdOrderService.save(subOrder);
					subOrderMap.put(brandId, subOrder);
				}
			}
		}

		List<TdOrderGoods> giftGoodsList = mainOrder.getGiftGoodsList();
		if (null != giftGoodsList && giftGoodsList.size() > 0) {
			for (TdOrderGoods orderGoods : giftGoodsList) {
				if (null != orderGoods) {
					Long brandId = orderGoods.getBrandId();
					// 从分单集合中取出相对应的订单
					TdOrder subOrder = subOrderMap.get(brandId);
					// 如果分单在集合中不存在，则创建一个
					if (null == subOrder) {
						subOrder = new TdOrder();
						TdBrand brand = tdBrandService.findOne(brandId);
						subOrder.setOrderNumber(mainOrder.getOrderNumber().replace("XN", brand.getShortName()));
						// 初始化分单信息
						initSubOrder(subOrder, mainOrder);
					}

					subOrder.getGiftGoodsList().add(orderGoods);
					subOrder = tdOrderService.save(subOrder);
					subOrderMap.put(brandId, subOrder);
				}
			}
		}
	}

	/**
	 * <p>
	 * 描述：初始化分单信息的方法
	 * </p>
	 * 
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月19日上午9:13:29
	 */
	private void initSubOrder(TdOrder subOrder, TdOrder mainOrder) {
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

		getCommonFields(subOrder, mainOrder);
	}

	/**
	 * <p>
	 * 描述：通过主单获取分单公共属性的方法
	 * </p>
	 * 
	 * @param subOrder
	 *            分单
	 * @param mainOrder
	 *            主单
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月19日上午9:31:00
	 */
	private void getCommonFields(TdOrder subOrder, TdOrder mainOrder) {
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

	/**
	 * <p>
	 * 描述：拆分优惠券的方法
	 * </p>
	 * 
	 * @param subOrderMap
	 *            分单列表
	 * @param mainOrder
	 *            主单
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月19日上午9:34:10
	 */
	private void disminlateCoupon(Map<Long, TdOrder> subOrderMap, TdOrder mainOrder) {
		String cashCouponId = mainOrder.getCashCouponId();
		String productCouponId = mainOrder.getProductCouponId();

		// 拆分现金券
		if (null != cashCouponId && !"".equals(cashCouponId)) {
			String[] coupons = cashCouponId.split(",");
			if (null != coupons && coupons.length > 0) {
				for (String sId : coupons) {
					if (null != sId && !"".equals(sId)) {
						Long id = Long.parseLong(sId);
						if (null != id) {
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon) {
								Long brandId = coupon.getBrandId();
								if (null != brandId) {
									TdOrder subOrder = subOrderMap.get(brandId);
									if (null != subOrder) {
										subOrder.setCashCouponId(subOrder.getCashCouponId() + coupon.getId() + ",");
										if (null == coupon.getRealPrice()) {
											coupon.setRealPrice(0.00);
										}
										subOrder.setCashCoupon(subOrder.getCashCoupon() + coupon.getRealPrice());
										subOrder.setTotalPrice(subOrder.getTotalPrice() - coupon.getRealPrice());
										subOrder = tdOrderService.save(subOrder);

										// 设置该优惠券为已使用
										setCouponUsed(coupon, subOrder);
									}
								}
							}
						}
					}
				}
			}
		}

		// 开始拆分产品券
		if (null != productCouponId && !productCouponId.isEmpty()) {
			String[] coupons = productCouponId.split(",");
			if (null != coupons && coupons.length > 0) {
				for (String sId : coupons) {
					if (null != sId && !sId.isEmpty()) {
						Long id = Long.parseLong(sId);
						if (null != id) {
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon) {
								Long brandId = coupon.getBrandId();
								if (null != brandId) {
									TdOrder subOrder = subOrderMap.get(brandId);
									if (null != subOrder) {
										subOrder.setProductCouponId(
												subOrder.getProductCouponId() + coupon.getId() + ",");
										// 遍历已选，找到指定产品券对应的产品
										List<TdOrderGoods> subOrderGoodsList = subOrder.getOrderGoodsList();
										if (null != subOrderGoodsList && subOrderGoodsList.size() > 0) {
											for (TdOrderGoods orderGoods : subOrderGoodsList) {
												if (null != orderGoods && null != orderGoods.getGoodsId()
														&& null != coupon.getGoodsId() && orderGoods.getGoodsId()
																.longValue() == coupon.getGoodsId().longValue()) {
													subOrder.setProductCoupon(
															subOrder.getProductCoupon() + (orderGoods.getGoodsTitle()
																	+ "【" + orderGoods.getSku() + "】*1,"));
												}
											}
										}
										subOrder.setTotalPrice(subOrder.getTotalPrice() - coupon.getRealPrice());
										subOrder = tdOrderService.save(subOrder);

										// 设置该优惠券为已使用
										setCouponUsed(coupon, subOrder);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * 描述：设置优惠券为已用的方法
	 * </p>
	 * 
	 * @param coupon
	 *            指定优惠券
	 * @param subOrder
	 *            分单号
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月19日上午9:35:48
	 */
	private void setCouponUsed(TdCoupon coupon, TdOrder subOrder) {
		coupon.setIsUsed(true);
		coupon.setUseTime(new Date());
		coupon.setOrderNumber(subOrder.getOrderNumber());

		coupon.setUseDiySiteCode(subOrder.getDiySiteCode());
		coupon.setUseDiySiteId(subOrder.getDiySiteId());
		coupon.setUseDiySiteTitle(subOrder.getDiySiteName());

		coupon.setUseSellerId(subOrder.getSellerId());
		coupon.setUseSellerRealName(subOrder.getSellerRealName());
		coupon.setUseSellerUsername(subOrder.getSellerUsername());

		tdCouponService.save(coupon);
	}

	/**
	 * <p>
	 * 描述：分单获取赠品和小辅料的方法
	 * </p>
	 * 
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月19日上午9:42:21
	 */
	private void getPresentAndGift(HttpServletRequest req, Map<Long, TdOrder> subOrderMap) {
		// 计算促销和小辅料
		for (Long brandId : subOrderMap.keySet()) {
			TdOrder order = subOrderMap.get(brandId);
			if (null != order) {
				// 计算促销
				order = tdCommonService.rePresent(req, order, true);
				// 计算小辅料
				order = tdCommonService.getGift(req, order);
				if (null == order.getActivitySubPrice()) {
					order.setActivitySubPrice(0d);
				}
				order.setTotalPrice(order.getTotalPrice() - order.getActivitySubPrice());
				order = tdOrderService.save(order);
				subOrderMap.put(brandId, order);
			}
		}
	}

	/**
	 * <p>
	 * 描述：将运费设置在乐易装分单上
	 * </p>
	 * 
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月19日上午9:44:23
	 */
	private void setDeliveryFee(Map<Long, TdOrder> subOrderMap, TdOrder mainOrder) {
		if (null != mainOrder.getDeliverTypeTitle() && !"门店自提".equals(mainOrder.getDeliverTypeTitle())) {
			TdOrder order = new TdOrder();
			order.setOrderNumber(mainOrder.getOrderNumber().replace("XN", "YF"));
			this.initSubOrder(order, mainOrder);
			if (null != mainOrder.getDeliverFee() && mainOrder.getDeliverFee() > 0) {
				order.setDeliverFee(mainOrder.getDeliverFee());
				order.setCompanyDeliveryFee(mainOrder.getCompanyDeliveryFee());
				if (null == order.getTotalPrice()) {
					order.setTotalPrice(0.00);
				}
				order.setTotalPrice(order.getTotalPrice() + order.getDeliverFee());
				if (!order.getTotalPrice().equals(0d)) {
					order = tdOrderService.save(order);
					subOrderMap.put(0L, order);
				}
			}
		}
	}

	/**
	 * <p>
	 * 描述：拆分预存款
	 * </p>
	 * 
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月19日上午9:49:27
	 * @throws UnknownHostException
	 */
	private void disminlateBalance(Map<Long, TdOrder> subOrderMap, TdOrder mainOrder) throws UnknownHostException {
		// 获取真实用户
		Long realUserId = mainOrder.getRealUserId();
		TdUser realUser = tdUserService.findOne(realUserId);

		// 获取原单总价
		Double totalPrice = mainOrder.getTotalPrice();

		if (null == totalPrice) {
			totalPrice = 0.00;
		}

		// 获取原订单使用不可提现的金额
		Double unCashBalanceUsed = mainOrder.getUnCashBalanceUsed();

		// 获取原订单使用不可提现的金额
		Double cashBalanceUsed = mainOrder.getCashBalanceUsed();

		// 获取原订单第三方支付的金额
		Double otherPay = mainOrder.getOtherPay();

		if (null == unCashBalanceUsed) {
			unCashBalanceUsed = 0.00;
		}
		if (null == cashBalanceUsed) {
			cashBalanceUsed = 0.00;
		}
		if (null == otherPay) {
			otherPay = 0.00;
		}

		// 获取原始价格
		totalPrice = totalPrice + cashBalanceUsed + unCashBalanceUsed;

		// 记录拆单预存款 用于记录分单号时剩余的预存款
		// Double remainCash = 0.0;
		// Double remainUnCash = 0.0;

		// 遍历当前生成的订单
		for (TdOrder subOrder : subOrderMap.values()) {
			if (null != subOrder) {
				Double subPrice = subOrder.getTotalPrice();
				if (null == subPrice || 0.00 == subPrice) {
					subOrder.setUnCashBalanceUsed(0.00);
					subOrder.setCashBalanceUsed(0.00);
					subOrder.setOtherPay(0.00);
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
						String scale2_uncash = df.format(unCashBalanceUsed * point);
						String scale2_cash = df.format(cashBalanceUsed * point);
						String scale2_other = df.format(otherPay * point);
						if (null == scale2_uncash) {
							scale2_uncash = "0.00";
						}
						if (null == scale2_cash) {
							scale2_cash = "0.00";
						}
						if (null == scale2_other) {
							scale2_other = "0.00";
						}
						subOrder.setUnCashBalanceUsed(Double.parseDouble(scale2_uncash));
						subOrder.setCashBalanceUsed(Double.parseDouble(scale2_cash));
						subOrder.setOtherPay(Double.parseDouble(scale2_other));
						subOrder.setActualPay(subOrder.getUnCashBalanceUsed() + subOrder.getCashBalanceUsed());
						subOrder.setPoint(point);
						String leftPrice = df.format(subOrder.getTotalPrice() - subOrder.getActualPay());
						subOrder.setTotalPrice(Double.parseDouble(leftPrice));

						subOrder = tdOrderService.save(subOrder);

						// 不可提现预存款
						if (new Double(scale2_uncash).doubleValue() != 0d) {
							realUser.setUnCashBalance(realUser.getUnCashBalance() - Double.valueOf(scale2_uncash));
							TdBalanceLog balanceLog = new TdBalanceLog();
							balanceLog.setUserId(subOrder.getRealUserId());
							balanceLog.setUsername(subOrder.getUsername());
							balanceLog.setMoney(Double.valueOf(scale2_uncash));
							balanceLog.setType(3L);
							balanceLog.setCreateTime(new Date());
							balanceLog.setFinishTime(new Date());
							balanceLog.setIsSuccess(true);
							balanceLog.setReason("订单支付使用");
							// 设置变更后的余额
							balanceLog.setBalance(realUser.getUnCashBalance());
							balanceLog.setBalanceType(2L);
							balanceLog.setOperator(subOrder.getUsername());
							balanceLog.setOrderNumber(subOrder.getOrderNumber());
							balanceLog.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
							balanceLog.setDiySiteId(realUser.getUpperDiySiteId());
							balanceLog.setCityId(realUser.getCityId());
							balanceLog.setCashLeft(realUser.getCashBalance());
							balanceLog.setUnCashLeft(realUser.getUnCashBalance());
							balanceLog.setAllLeft(realUser.getBalance());
							tdBalanceLogService.save(balanceLog);
							// remainUnCash += Double.valueOf(scale2_uncash);
						}
						// 可提现预存款
						if (new Double(scale2_cash).doubleValue() != 0d) {
							realUser.setCashBalance(realUser.getCashBalance() - Double.valueOf(scale2_cash));
							TdBalanceLog balanceLog = new TdBalanceLog();
							balanceLog.setUserId(subOrder.getRealUserId());
							balanceLog.setUsername(subOrder.getUsername());
							balanceLog.setMoney(Double.valueOf(scale2_cash));
							balanceLog.setType(3L);
							balanceLog.setCreateTime(new Date());
							balanceLog.setFinishTime(new Date());
							balanceLog.setIsSuccess(true);
							balanceLog.setReason("订单支付使用");
							balanceLog.setBalance(realUser.getCashBalance());
							balanceLog.setBalanceType(1L);
							balanceLog.setOperator(subOrder.getUsername());
							balanceLog.setOrderNumber(subOrder.getOrderNumber());
							balanceLog.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
							balanceLog.setDiySiteId(realUser.getUpperDiySiteId());
							balanceLog.setCityId(realUser.getCityId());
							balanceLog.setCashLeft(realUser.getCashBalance());
							balanceLog.setUnCashLeft(realUser.getUnCashBalance());
							balanceLog.setAllLeft(realUser.getBalance());
							tdBalanceLogService.save(balanceLog);
							// remainCash += Double.valueOf(scale2_cash);
						}
						realUser.getBalance();
						tdUserService.save(realUser);
					}
				}
			}
		}

		// 2016-12-17:完成分单预存款的扣减之后，扣减支付上楼费的预存款
		Double upstairsBalancePayed = mainOrder.getUpstairsBalancePayed();
		Double unCashBalance = realUser.getUnCashBalance();
		Double cashBalance = realUser.getCashBalance();
		if (upstairsBalancePayed > 0) {
			if (unCashBalance >= upstairsBalancePayed) {
				realUser.setUnCashBalance(realUser.getUnCashBalance() - upstairsBalancePayed);
				TdBalanceLog balanceLog = new TdBalanceLog();
				balanceLog.setUserId(mainOrder.getRealUserId());
				balanceLog.setUsername(mainOrder.getUsername());
				balanceLog.setMoney(upstairsBalancePayed);
				balanceLog.setType(3L);
				balanceLog.setCreateTime(new Date());
				balanceLog.setFinishTime(new Date());
				balanceLog.setIsSuccess(true);
				balanceLog.setReason("订单上楼费支付使用");
				// 设置变更后的余额
				balanceLog.setBalance(realUser.getUnCashBalance());
				balanceLog.setBalanceType(2L);
				balanceLog.setOperator(mainOrder.getUsername());
				balanceLog.setOrderNumber(mainOrder.getOrderNumber());
				balanceLog.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
				balanceLog.setDiySiteId(realUser.getUpperDiySiteId());
				balanceLog.setCityId(realUser.getCityId());
				balanceLog.setCashLeft(realUser.getCashBalance());
				balanceLog.setUnCashLeft(realUser.getUnCashBalance());
				balanceLog.setAllLeft(realUser.getBalance());
				tdBalanceLogService.save(balanceLog);
			} else {
				if (unCashBalance > 0) {
					realUser.setUnCashBalance(0d);
					TdBalanceLog balanceLog = new TdBalanceLog();
					balanceLog.setUserId(mainOrder.getRealUserId());
					balanceLog.setUsername(mainOrder.getUsername());
					balanceLog.setMoney(unCashBalance);
					balanceLog.setType(3L);
					balanceLog.setCreateTime(new Date());
					balanceLog.setFinishTime(new Date());
					balanceLog.setIsSuccess(true);
					balanceLog.setReason("订单上楼费支付使用");
					// 设置变更后的余额
					balanceLog.setBalance(realUser.getUnCashBalance());
					balanceLog.setBalanceType(2L);
					balanceLog.setOperator(mainOrder.getUsername());
					balanceLog.setOrderNumber(mainOrder.getOrderNumber());
					balanceLog.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
					balanceLog.setDiySiteId(realUser.getUpperDiySiteId());
					balanceLog.setCityId(realUser.getCityId());
					balanceLog.setCashLeft(realUser.getCashBalance());
					balanceLog.setUnCashLeft(realUser.getUnCashBalance());
					balanceLog.setAllLeft(realUser.getBalance());
					tdBalanceLogService.save(balanceLog);
				}
				realUser.setCashBalance(cashBalance + unCashBalance - upstairsBalancePayed);
				TdBalanceLog balanceLog = new TdBalanceLog();
				balanceLog.setUserId(mainOrder.getRealUserId());
				balanceLog.setUsername(mainOrder.getUsername());
				balanceLog.setMoney(upstairsBalancePayed - unCashBalance);
				balanceLog.setType(3L);
				balanceLog.setCreateTime(new Date());
				balanceLog.setFinishTime(new Date());
				balanceLog.setIsSuccess(true);
				balanceLog.setReason("订单上楼费支付使用");
				// 设置变更后的余额
				balanceLog.setBalance(realUser.getUnCashBalance());
				balanceLog.setBalanceType(1L);
				balanceLog.setOperator(mainOrder.getUsername());
				balanceLog.setOrderNumber(mainOrder.getOrderNumber());
				balanceLog.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
				balanceLog.setDiySiteId(realUser.getUpperDiySiteId());
				balanceLog.setCityId(realUser.getCityId());
				balanceLog.setCashLeft(realUser.getCashBalance());
				balanceLog.setUnCashLeft(realUser.getUnCashBalance());
				balanceLog.setAllLeft(realUser.getBalance());
				tdBalanceLogService.save(balanceLog);
			}
			tdUserService.save(realUser);
		}
		
		this.costCredit(mainOrder);
	}

	private void saveAndSend(HttpServletRequest req, Map<Long, TdOrder> subOrderMap, TdOrder mainOrder,
			Double deliveryFee) {
		String mainOrderNumber = mainOrder.getOrderNumber();
		Boolean sendWMS = true;
		List<TdOrder> sendOrders = new ArrayList<>();
		for (TdOrder subOrder : subOrderMap.values()) {
			if (null != subOrder.getIsCoupon() && subOrder.getIsCoupon()) {
				subOrder.setStatusId(5L);
				tdCommonService.getCoupon(subOrder, "无");
				sendWMS = false;
			}
			if (!("送货上门".equals(subOrder.getDeliverTypeTitle()))) {
				sendWMS = false;
			}
			subOrder = tdOrderService.save(subOrder);
			sendOrders.add(subOrder);
		}

		clearMainOrder(req, mainOrder);

		if (sendWMS) {
			tdCommonService.sendWms(sendOrders, mainOrderNumber, deliveryFee);
		}
		tdCommonService.sendEbs(sendOrders);
	}

	private void clearMainOrder(HttpServletRequest req, TdOrder mainOrder) {
		// 删除虚拟订单
		mainOrder.setGiftGoodsList(null);
		mainOrder.setPresentedList(null);
		mainOrder.setOrderGoodsList(null);
		tdOrderService.delete(mainOrder);

		// 清空session中的虚拟订单
		req.getSession().removeAttribute("order_temp");
	}

	@Override
	public Map<String,Double> countOrderDeliveryFee(TdUser user,TdOrder order) throws Exception {
		
		Map<String,Double> deliveryMap = new HashMap<>();//用map存储用户运费和公司运费
		
		if (order.getDeliverTypeTitle().equals("门店自提")) {
			return deliveryMap;
		} else {
			
			if (null != order.getIsFixedDeliveryFee() && order.getIsFixedDeliveryFee()) {
				deliveryMap.put("user_delivery_fee", order.getDeliverFee());
				deliveryMap.put("company_delivery_fee", order.getCompanyDeliveryFee());
				return deliveryMap;
			} else {
				double bucketsOfPaintFee = 0;//大桶漆配送费
				double nitrolacquerFee = 0;//硝基漆10L配送费
				double carpentryPaintFee = 0;//小桶漆/木器漆配送费
				double belowFourKiloFee = 0;//4kg以下漆类配送费
				double wallAccessories = 0;//订单辅料总金额 
				double consumerDeliveryFee = 0;//客户承担的运费
				double companyDeliveryFee = 0;//公司承担的运费
				
				long consumerAffordQuantity = 0;//客户承担运费的漆桶数
				
				long companyAffordQuantity  = 0;//公司承担运费的漆桶数
				TdOrderDeliveryFeeDetail detail = tdOrderDeliveryFeedetailService.findByMainOrderNumber(order.getOrderNumber());
				if(null == detail){
					detail = new TdOrderDeliveryFeeDetail();
				}
				detail.setMainOrderNumber(null == order.getMainOrderNumber() ? order.getOrderNumber():order.getMainOrderNumber());
				detail.setDiySiteId(order.getDiySiteId());
				detail.setDiySiteName(order.getDiySiteName());
				detail.setOrderTime(null == order.getOrderTime()?null: order.getOrderTime());
				detail.setSellerUsername(order.getSellerUsername());
				detail.setSellerRealName(order.getSellerRealName());
				detail.setUsername(order.getUsername());
				detail.setUserRealName(order.getRealUserRealName());
				detail.setIsCustomerDeliveryFeeModified(false);
				
				Double deliveryFee = 0d;//运费总额
				
				Map<Long, TdOrderGoods> orderGoodsMap = this.countOrderGoodsNumber(order);
				for (TdOrderGoods orderGoods : orderGoodsMap.values()) {
					TdDeliveryFeeHead tdDeliveryFeeHead = tdDeliveryFeeHeadService.findBySobIdAndGoodsId(user.getCityId(), orderGoods.getGoodsId());
					Double fee = this.countOrderGoodsDeliveryFee(user, orderGoods);
					//运费大于0 说明该产品有运费配置，则tdDeliveryFeeHead不为null
					if(null != fee && fee>0){
						switch (tdDeliveryFeeHead.getGoodsTypeId()) {
						case 1:
							bucketsOfPaintFee+=fee;
							break;
						case 2:
							nitrolacquerFee+=fee;
							break;
						case 3:
							carpentryPaintFee+=fee;
							break;
						case 4:
							belowFourKiloFee+=fee;
							break;
						default:
							break;
						}
					}
					
					
				
					if(null != tdDeliveryFeeHead && tdDeliveryFeeHead.getAssumedObjectId()==1){
						consumerDeliveryFee+=fee;
						consumerAffordQuantity+=orderGoods.getQuantity();
						
					}else if(null != tdDeliveryFeeHead && tdDeliveryFeeHead.getAssumedObjectId()==2){
						companyDeliveryFee+=fee;
						companyAffordQuantity+=orderGoods.getQuantity();
					}
					if(orderGoods.getIsWallAccessory() == true){
						wallAccessories += orderGoods.getPrice()*orderGoods.getQuantity();
					}
					
				}
				
				//设置各种类漆的运费金额
				detail.setBucketsOfPaintFee(bucketsOfPaintFee);
				detail.setNitrolacquerFee(nitrolacquerFee);
				detail.setCarpentryPaintFee(carpentryPaintFee);
				detail.setBelowFourKiloFee(belowFourKiloFee);
				
				//设置本单墙面辅料总金额
				detail.setWallAccessories(wallAccessories);
				
				detail.setConsumerDeliveryFee(consumerDeliveryFee);//设置优惠前客户承担运费金额
				detail.setCompanyDeliveryFee(companyDeliveryFee);//设置优惠前公司承担运费金额
				
				deliveryFee = consumerDeliveryFee + companyDeliveryFee;
				
				
				TdSetting setting = tdSettingService.findTopBy();
				
				// 判断总运费金额是否大于等于20，如果小于20，则差额由华润公司承担
				Double settingMinFee = null == setting.getMinDeliveryFee() ? 0d : setting.getMinDeliveryFee();
				if (deliveryFee > 0 && deliveryFee < settingMinFee) {
					companyDeliveryFee = settingMinFee-consumerDeliveryFee;
					detail.setCompanyDeliveryFeeAdjust(companyDeliveryFee - detail.getCompanyDeliveryFee());//设置华润公司补运费差价金额
				}else{
					detail.setCompanyDeliveryFeeAdjust(0.00);
				}
				// 运费折扣优惠，如果用户承担运费的漆类桶数和华润承担运费的类桶数任意一个大于20桶，则双方运费都打7.5折；如果大于100桶，折扣为6折
				if((consumerAffordQuantity>=20 && consumerAffordQuantity<99) || (companyAffordQuantity>=20 && companyAffordQuantity<99)){
					consumerDeliveryFee = consumerDeliveryFee * 0.75;
					companyDeliveryFee = companyDeliveryFee * 0.75;
					deliveryFee = consumerDeliveryFee + companyDeliveryFee;
				}else if(consumerAffordQuantity>= 100 ||companyAffordQuantity>=100 ){
					consumerDeliveryFee = consumerDeliveryFee * 0.6;
					companyDeliveryFee = companyDeliveryFee * 0.6;
					deliveryFee = consumerDeliveryFee + companyDeliveryFee;
				}
				detail.setConsumerDeliveryFeeDiscount(detail.getConsumerDeliveryFee()-consumerDeliveryFee);//设置用户运费打折金额
				detail.setCompanyDeliveryFeeDiscount(detail.getCompanyDeliveryFee()+detail.getCompanyDeliveryFeeAdjust()-companyDeliveryFee);//设置华润公司运费打折金额
				//墙面辅料金额以500为阶梯减免运费。500减20,1000减40，以此类推。其中减免的运费优先由用户享受，如果用户承担的运费小于优惠金额，则剩余的优惠金额才能由华润享受
				if(wallAccessories>=500){
					double reduceDeliveryFee = (wallAccessories/500) * 20;//购辅料减免运费总额
					if(reduceDeliveryFee <= consumerDeliveryFee){//如果辅料减免的运费小于当前用户承担的运费，则全部用来减免用户用费
						detail.setConsumerDeliveryFeeReduce(reduceDeliveryFee);
						detail.setCompanyDeliveryFeeReduce(0.00);
						
						consumerDeliveryFee -= reduceDeliveryFee;
					}else if(reduceDeliveryFee > consumerDeliveryFee){//如果辅料减免运费大于用户承担的运费，则用户用费全面，剩余部分用来减免华润运费
						detail.setConsumerDeliveryFeeReduce(consumerDeliveryFee);
						detail.setCompanyDeliveryFeeReduce(reduceDeliveryFee - consumerDeliveryFee);
						
						companyDeliveryFee -=(reduceDeliveryFee - consumerDeliveryFee); 
						consumerDeliveryFee = 0;
						
					}
				}else{
					detail.setConsumerDeliveryFeeReduce(0.00);
					detail.setCompanyDeliveryFeeReduce(0.00);
				}
				//
				detail.setConsumerDeliveryFeeFinal(consumerDeliveryFee);
				detail.setCompanyDeliveryFeeFinal(companyDeliveryFee);
				detail.setCustomerDeliveryFeeBeforeModified(consumerDeliveryFee);
				tdOrderDeliveryFeedetailService.save(detail);

				/*Double settingMaxFee = null == setting.getMaxDeliveryFee() ? 0d : setting.getMaxDeliveryFee();
				if (deliveryFee > settingMaxFee) {
					deliveryFee = settingMaxFee;
				}*/

				order.setDeliverFee(consumerDeliveryFee);
				order.setReceivableFee(consumerDeliveryFee);
				deliveryMap.put("user_delivery_fee", consumerDeliveryFee);
				deliveryMap.put("company_delivery_fee", companyDeliveryFee);
				return deliveryMap;
			}
		}
	}

	@Override
	public Map<Long, TdOrderGoods> countOrderGoodsNumber(TdOrder order) throws Exception {
		Map<Long, TdOrderGoods> map = new HashMap<>();
		List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
		if (null != orderGoodsList && orderGoodsList.size() > 0) {
			for (TdOrderGoods orderGoods : orderGoodsList) {
				Long goodsId = orderGoods.getGoodsId();
				TdOrderGoods tdOrderGoods = map.get(goodsId);
				if (null == tdOrderGoods) {
					tdOrderGoods = new TdOrderGoods();
					tdOrderGoods.setGoodsId(orderGoods.getGoodsId());
					tdOrderGoods.setQuantity(orderGoods.getQuantity());
					tdOrderGoods.setIsWallAccessory(orderGoods.getIsWallAccessory());
					tdOrderGoods.setPrice(orderGoods.getPrice());
					map.put(goodsId, tdOrderGoods);
				} else {
					tdOrderGoods.setQuantity(tdOrderGoods.getQuantity() + orderGoods.getQuantity());
					map.put(goodsId, tdOrderGoods);
				}
			}
		}

		List<TdOrderGoods> presentedList = order.getPresentedList();
		if (null != presentedList && presentedList.size() > 0) {
			for (TdOrderGoods orderGoods : presentedList) {
				Long goodsId = orderGoods.getGoodsId();
				TdOrderGoods tdOrderGoods = map.get(goodsId);
				if (null == tdOrderGoods) {
					tdOrderGoods = new TdOrderGoods();
					tdOrderGoods.setGoodsId(orderGoods.getGoodsId());
					tdOrderGoods.setQuantity(orderGoods.getQuantity());
					map.put(goodsId, tdOrderGoods);
				} else {
					tdOrderGoods.setQuantity(tdOrderGoods.getQuantity() + orderGoods.getQuantity());
					map.put(goodsId, tdOrderGoods);
				}
			}
		}

		List<TdOrderGoods> giftGoodsList = order.getGiftGoodsList();
		if (null != giftGoodsList && giftGoodsList.size() > 0) {
			for (TdOrderGoods orderGoods : giftGoodsList) {
				Long goodsId = orderGoods.getGoodsId();
				TdOrderGoods tdOrderGoods = map.get(goodsId);
				if (null == tdOrderGoods) {
					tdOrderGoods = new TdOrderGoods();
					tdOrderGoods.setGoodsId(orderGoods.getGoodsId());
					tdOrderGoods.setQuantity(orderGoods.getQuantity());
					map.put(goodsId, tdOrderGoods);
				} else {
					tdOrderGoods.setQuantity(tdOrderGoods.getQuantity() + orderGoods.getQuantity());
					map.put(goodsId, tdOrderGoods);
				}
			}
		}
		return map;
	}

	@Override
	public Double countOrderGoodsDeliveryFee(TdUser user, TdOrderGoods orderGoods) throws Exception {
		Long quantity = orderGoods.getQuantity();
		quantity = null == quantity ? 0 : quantity;
		Long goodsId = orderGoods.getGoodsId();
		TdDeliveryFeeLine deliveryFeeLine = tdDeliveryFeeLineService.findBySobIdAndGoodsIdAndNumber(user.getCityId(),
				goodsId, quantity);
		if (null == deliveryFeeLine) {
			return 0d;
		} else {
			Double unit = deliveryFeeLine.getUnit();
			Double fixedPrice = deliveryFeeLine.getFixedPrice();

			if (null != fixedPrice) {

				return fixedPrice;
			} else {
				return quantity * unit;
			}
		}
	}
	
	private void costCredit(TdOrder order) {
		if (!(null != order.getIsOnlinePay() && order.getIsOnlinePay())) {
			this.tdUserService.useCredit(CreditChangeType.CONSUME, order);
		}
	}
}
