package com.ynyes.lyz.service;

import java.math.BigDecimal;
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

import com.ibm.icu.util.Calendar;
import com.ynyes.lyz.entity.TdBalanceLog;
import com.ynyes.lyz.entity.TdCashReturnNote;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdCouponModule;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdPayType;
import com.ynyes.lyz.entity.TdPriceList;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.TdRecharge;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.service.TdCashReciptInfService;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.service.basic.settlement.ISettlementService;

@Service
@Transactional
public class TdPriceCountService {

	@Autowired
	private TdCouponService tdCouponService;

	@Autowired
	private TdPayTypeService tdPayTypeService;

	@Autowired
	private ISettlementService settlementService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdCouponModuleService tdCouponModuleService;

	@Autowired
	private TdBalanceLogService tdBalanceLogService;

	@Autowired
	private TdCashReturnNoteService tdCashReturnNoteService;

	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdCashReciptInfService tdCashReciptInfService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Autowired
	private TdPriceListService tdPriceListService;

	@Autowired
	private TdPriceListItemService tdPriceListItemService;

	@Autowired
	private TdUpstairsSettingService tdUpstairSettingService;

	/**
	 * 计算订单价格和能使用的最大的预存款的方法
	 * 
	 * @author dengxiao
	 * @throws Exception
	 */
	public Map<String, Object> countPrice(TdOrder order, TdUser user) {
		// 判断传入的参数是否为空，如果为空就没有计算的必要了
		if (null == order || null == user) {
			return null;
		}

		Boolean isFixedDeliveryFee = order.getIsFixedDeliveryFee();

		// 创建一个用于存储最后结果的map
		Map<String, Object> results = new HashMap<>();

		// 创建一个变量用于表示能够使用的最大预存款
		Double max_use = 0.00;

		// 创建一个标识符用于判断能否使用预存款和优惠券
		Boolean canUseBalance = true;

		// 每次重新计算的时候，清空优惠券的使用说明
		order.setCashCoupon(0.00);
		order.setProductCoupon("");

		List<TdOrderGoods> goodsList = order.getOrderGoodsList();
		// 如果订单里面没有商品，则也没有计算的必要
		if (null == goodsList || goodsList.size() <= 0) {
			return null;
		}

		// 初始化订单金额
		order.setTotalPrice(0.00);
		order.setTotalGoodsPrice(0.00);

		// 开始计算原始商品总额和原始订单金额
		for (TdOrderGoods orderGoods : goodsList) {
			Double total = 0.00;
			if (null != orderGoods) {
				Double price = orderGoods.getPrice();
				Long quantity = orderGoods.getQuantity();
				// 进行空值判定
				if (null == price) {
					price = 0.00;
				}
				if (null == quantity) {
					quantity = 0L;
				}
				total += (price * quantity);
			}
			// 给订单设置属性
			order.setTotalPrice(order.getTotalPrice() + total);
			order.setTotalGoodsPrice(order.getTotalGoodsPrice() + total);
		}

		// 计算订单的运费
		// order.setDeliverFee(0.00);

		// 获取订单的收货街道
		// List<TdSubdistrict> subDistrict_list = tdSubDistrictService
		// .findByDistrictNameAndNameOrderBySortIdAsc(order.getDisctrict(),
		// order.getSubdistrict());
		// if (null != subDistrict_list && subDistrict_list.size() > 0) {
		// TdSubdistrict subdistrict = subDistrict_list.get(0);
		// if (null != subdistrict) {
		// Double fee = subdistrict.getDeliveryFee();
		// if (null != fee) {
		// order.setDeliverFee(fee);
		// }
		// }
		// }

		// Double fee = 0d;
		try {
			Map<String, Double> depiveryFeeMap = new HashMap<>();
			depiveryFeeMap = settlementService.countOrderDeliveryFee(user, order);
			order.setDeliverFee(depiveryFeeMap.get("user_delivery_fee"));
			order.setCompanyDeliveryFee(depiveryFeeMap.get("company_delivery_fee"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 如果订单的配送方式是到店支付，则不计算运费（新增：电子券订单也不计算运费 —— @Date 2016年4月27日）
		String title = order.getDeliverTypeTitle();
		if ((null != title && "门店自提".equals(title)) || (null != order.getIsCoupon() && order.getIsCoupon())) {
			order.setDeliverFee(0.00);
			// 同时判断能否使用券和预存款
			Long payTypeId = order.getPayTypeId();
			if (null != payTypeId) {
				TdPayType type = tdPayTypeService.findOne(payTypeId);
				// 如果支付方式属于线下支付，则不能够使用预存款和券
				if (null != type && null != type.getIsOnlinePay() && !type.getIsOnlinePay()) {
					// 到店支付可以使用预存款和优惠卷
					if (!"到店支付".equals(type.getTitle())) {
						order.setCashCouponId("");
						order.setProductCouponId("");
						canUseBalance = false;
					}

				}
			}
		}
		// 将运费的费用添加到订单总额中
		order.setTotalPrice(order.getTotalPrice() + order.getDeliverFee());

		// // 判断能否使用预存款和优惠券（支付方式为到店支付的情况下不能够使用预存款和优惠券）
		// String payTypeTitle = order.getPayTypeTitle();
		// if (null != payTypeTitle && payTypeTitle.equalsIgnoreCase("到店支付")) {
		// order.setCashCouponId("");
		// order.setProductCouponId("");
		// canUseBalance = false;
		// }

		if (canUseBalance) {
			// 开始计算使用的现金券的价值
			String cashCouponId = order.getCashCouponId();
			if (null == order.getCashCoupon()) {
				order.setCashCoupon(0.00);
			}
			// 拆分现金券
			if (null != cashCouponId && !"".equals(cashCouponId)) {
				String[] coupons = cashCouponId.split(",");
				if (null != coupons && coupons.length > 0) {
					for (String sCouponId : coupons) {
						Long id = Long.parseLong(sCouponId);
						// 获取该张优惠券实体信息
						TdCoupon coupon = tdCouponService.findOne(id);
						// 如果该张优惠券存在，具有金额，具有真实使用价值，同时没有过期，没有使用，则有效
						if (null != coupon && null != coupon.getPrice() && null != coupon.getRealPrice()
								&& null != coupon.getIsOutDate() && null != coupon.getIsUsed() && !coupon.getIsOutDate()
								&& !coupon.getIsUsed()) {
							Double price = coupon.getRealPrice();
							order.setTotalPrice(order.getTotalPrice() - price);
							order.setCashCoupon(order.getCashCoupon() + price);
						}
					}
				}
			}

			// 开始计算产品券产品券的价值
			String productCouponId = order.getProductCouponId();
			// 拆分产品券
			if (null != productCouponId && !"".equals(productCouponId)) {
				String[] coupons = productCouponId.split(",");
				if (null != coupons && coupons.length > 0) {
					for (String sCouponId : coupons) {
						Long id = Long.parseLong(sCouponId);
						// 获取该张优惠券的实体信息
						TdCoupon coupon = tdCouponService.findOne(id);
						// 如果该张优惠券存在，具有指定商品，具有真实价值，同时没有过期，没有使用，则有效
						if (null != coupon && null != coupon.getGoodsId() && null != coupon.getRealPrice()
								&& null != coupon.getIsOutDate() && null != coupon.getIsUsed() && !coupon.getIsOutDate()
								&& !coupon.getIsUsed()) {
							// 遍历所有的已选商品，查找与优惠券对应的商品，获取其价格
							order.setTotalPrice(order.getTotalPrice() - coupon.getRealPrice());
						}
					}
				}
			}
		}

		// 计算上楼费
		Double upstairsFee = tdUpstairSettingService.countUpstairsFee(order);
		order.setUpstairsFee(upstairsFee);

		// 计算促销减少的价格
		Double activitySubPrice = order.getActivitySubPrice();
		if (null == activitySubPrice) {
			activitySubPrice = 0.00;
		}
		order.setTotalPrice(order.getTotalPrice() - activitySubPrice);
		if (0 > order.getTotalPrice()) {
			order.setTotalPrice(0.00);
		}

		order.setIsFixedDeliveryFee(isFixedDeliveryFee);

		// 开始计算最大能使用的预存款的额度
		if (canUseBalance) {
			Double balance = user.getBalance();
			if (null == balance) {
				balance = 0.00;
			}
			// 如果预存款小于订单金额，则能够使用的最大预存款额度为用户的预存款
			if (balance < (order.getTotalPrice() + order.getUpstairsFee())) {
				max_use = balance;
			}
			// 其他情况则为订单的金额
			else {
				max_use = (order.getTotalPrice() + order.getUpstairsFee());
			}
		} else {
			max_use = 0.00;
		}

		// 根据当前预存款使用额，判断当前订单的金额
		if (canUseBalance) {
			if (null == order.getActualPay()) {
				// 如果当前使用预存款为null，则代表没有使用预存款
				order.setActualPay(0.00);
			}
		} else {
			order.setActualPay(0.00);
		}

		// 判断是否使用的预存款大于了订单的总金额
		if (order.getActualPay() > order.getTotalPrice()) {
			order.setActualPay(order.getTotalPrice());
		}

		// 判断上楼费是否多收
		if (order.getUpstairsBalancePayed() > order.getUpstairsFee()) {
			order.setUpstairsBalancePayed(order.getUpstairsFee());
		}

		order.setTotalPrice(order.getTotalPrice() - order.getActualPay());

		tdOrderService.save(order);

		// max_use = order.getTotalPrice() + order.getActualPay();

		results.put("result", order);
		results.put("max", max_use);
		results.put("isCoupon", canUseBalance);

		return results;
	}

	/**
	 * 根据用户的已选，计算每个品牌能够使用的优惠券的额度和已使用的额度
	 * 
	 * @param Double[]的规则为【可使用额度，已使用额度】
	 * 
	 * @author dengxiao
	 */
	public Map<Long, Double[]> getPermit(TdOrder order) {
		// 如果参数为NULL，则没有计算的必要了
		if (null == order) {
			return null;
		}

		Map<Long, Double[]> map = new HashMap<>();

		List<TdOrderGoods> goodsList = order.getOrderGoodsList();

		// 如果订单没有商品，则也没有计算的价值
		if (null == goodsList) {
			return null;
		}

		for (TdOrderGoods orderGoods : goodsList) {
			if (null != orderGoods) {
				Long brandId = orderGoods.getBrandId();
				if (null != brandId) {
					// 创建一个变量表示该件商品能够使用优惠券的额度
					Double permitCash = 0.00;

					// TdCouponModule couponModule = tdCouponModuleService
					// .findByGoodsIdAndCityTitle(orderGoods.getGoodsId(),
					// order.getCity());

					TdCouponModule couponModule = tdCouponModuleService
							.findByGoodsIdAndCityTitleAndType(orderGoods.getGoodsId(), order.getCity(), 0L);

					// 可用通用现金券的金额
					Double normalPrice = 0d;
					// 可用优工券的金额
					Double YGPrice = 0d;

					// 如果在模板里面配置了通用现金券的金额，则采用模板里配置的数据，否则就为0，表示不能用通用现金券
					if (null != couponModule) {
						normalPrice = couponModule.getPrice();
					}

					// 根据价目表的价差获取差价券的金额
					Double realPrice = null == orderGoods.getRealPrice() ? 0d : orderGoods.getRealPrice();
					Double price = null == orderGoods.getPrice() ? 0d : orderGoods.getPrice();

					YGPrice = price - realPrice;

					Long quantity = orderGoods.getQuantity();
					if (null == quantity) {
						quantity = 0L;
					}
					Long couponNumber = orderGoods.getCouponNumber();
					if (null == couponNumber) {
						couponNumber = 0L;
					}
					permitCash = (YGPrice + normalPrice) * (quantity - couponNumber);

					// 获取指定品牌下的【可使用额度，已使用额度】数组
					Double[] permits = map.get(brandId);
					if (null == permits || permits.length != 2) {
						permits = new Double[2];
						permits[0] = 0.00;
						permits[1] = 0.00;
					}
					if (null == permits[0]) {
						permits[0] = 0.00;
					}
					// 计算最大额度
					permits[0] += permitCash;
					map.put(brandId, permits);
				}
			}
		}

		// 计算已使用的现金券额度
		String cashCouponId = order.getCashCouponId();
		if (null != cashCouponId && !"".equals(cashCouponId)) {
			String[] coupons = cashCouponId.split(",");
			if (null != coupons && coupons.length > 0) {
				for (String sCouponId : coupons) {
					if (null != sCouponId) {
						Long id = Long.parseLong(sCouponId);
						if (null != id) {
							// 查找到指定id的现金券
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon) {
								// 获取优惠券的品牌
								Long brandId = coupon.getBrandId();
								if (null != brandId) {
									// 获取优惠券的价格
									Double price = coupon.getPrice();
									// 获取指定品牌下的【可使用额度，已使用额度】数组
									Double[] permits = map.get(brandId);
									if (null == permits || permits.length != 2) {
										permits = new Double[2];
										permits[0] = 0.00;
										permits[1] = 0.00;
									}
									if (null == permits[1]) {
										permits[1] = 0.00;
									}
									// 计算最大额度
									permits[1] += price;
									map.put(brandId, permits);
								}
							}
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 计算订单中的优惠券是否被使用
	 * 
	 * @author dengxiao
	 */
	public TdOrder checkCouponIsUsed(TdOrder order) {
		// 如果参数为空，就没有计算的必要了
		if (null == order) {
			return null;
		}

		// 判定现金券是否被使用
		order.setCashCouponId(this.couponIsUsed(order.getCashCouponId()));
		// 判定产品券是否被使用
		order.setProductCouponId(this.couponIsUsed(order.getProductCouponId()));

		return order;
	}

	/**
	 * 计算一些优惠券是否被使用
	 * 
	 * @author dengxiao
	 */
	public String couponIsUsed(String couponsId) {
		if (null == couponsId) {
			return "";
		}
		// 定义一个字符串变量用于存储最后的使用优惠券的情况
		String realUsed = "";
		if (!"".equals(couponsId)) {
			String[] coupons = couponsId.split(",");
			if (null != coupons && coupons.length > 0) {
				for (String sCouponId : coupons) {
					if (null != sCouponId) {
						Long id = Long.parseLong(sCouponId);
						if (null != id) {
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon && null != coupon.getIsOutDate() && null != coupon.getIsUsed()
									&& !coupon.getIsOutDate() && !coupon.getIsUsed()) {
								realUsed += coupon.getId() + ",";
							}
						}
					}
				}
			}
		}

		return realUsed;
	}

	/**
	 * 进行资金和优惠券退还的方法
	 * 
	 * @author dengxiao
	 */
	public void cashAndCouponBack(TdOrder order, TdUser user, TdReturnNote returnNote) {
		// 如果参数为NULL，则没有继续的必要了
		if (null == order || null == user) {
			return;
		}

		Double balance = user.getBalance();
		if (null == balance) {
			balance = 0.00;
		}
		Double unCashBalanceUsed = order.getUnCashBalanceUsed();
		if (null == unCashBalanceUsed) {
			unCashBalanceUsed = 0.00;
		}
		Double cashBalanceUsed = order.getCashBalanceUsed();
		if (null == cashBalanceUsed) {
			cashBalanceUsed = 0.00;
		}

		// 获取订单中的产品券使用id记录
		String productCouponId = order.getProductCouponId();
		if (null == productCouponId) {
			productCouponId = "";
		}
		// 获取订单中的现金券使用id记录
		String cashCouponId = order.getCashCouponId();
		if (null == cashCouponId) {
			cashCouponId = "";
		}

		// 开始返还用户的总额
		user.setBalance(user.getBalance() + unCashBalanceUsed);
		// 开始返还用户的不可提现余额
		user.setUnCashBalance(user.getUnCashBalance() + unCashBalanceUsed);
		if (unCashBalanceUsed > 0) {
			// 添加退还日志
			TdBalanceLog balanceLog = new TdBalanceLog();
			balanceLog.setUserId(user.getId());
			balanceLog.setUsername(user.getUsername());
			balanceLog.setMoney(unCashBalanceUsed);
			balanceLog.setType(4L);
			balanceLog.setCreateTime(new Date());
			balanceLog.setFinishTime(new Date());
			balanceLog.setIsSuccess(true);
			balanceLog.setBalanceType(4L);
			balanceLog.setBalance(user.getUnCashBalance());
			balanceLog.setOperator(user.getUsername());
			try {
				balanceLog.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				System.out.println("获取ip地址报错");
				e.printStackTrace();
			}
			balanceLog.setReason("取消订单退款");
			balanceLog.setOrderNumber(order.getOrderNumber());
			balanceLog.setDiySiteId(user.getUpperDiySiteId());
			balanceLog.setCityId(user.getCityId());
			balanceLog.setCashLeft(user.getCashBalance());
			balanceLog.setUnCashLeft(user.getUnCashBalance());
			balanceLog.setAllLeft(user.getBalance());
			tdBalanceLogService.save(balanceLog);
		}
		// 开始返还用户的总额
		user.setBalance(user.getBalance() + cashBalanceUsed);
		// 开始返还用户的可提现余额
		user.setCashBalance(user.getCashBalance() + cashBalanceUsed);
		if (cashBalanceUsed > 0) {
			// 添加退还日志
			TdBalanceLog balanceLog = new TdBalanceLog();
			balanceLog.setUserId(user.getId());
			balanceLog.setUsername(user.getUsername());
			balanceLog.setMoney(cashBalanceUsed);
			balanceLog.setType(4L);
			balanceLog.setCreateTime(new Date());
			balanceLog.setFinishTime(new Date());
			balanceLog.setIsSuccess(true);
			balanceLog.setBalanceType(3L);
			balanceLog.setBalance(user.getCashBalance());
			balanceLog.setOperator(user.getUsername());
			try {
				balanceLog.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				System.out.println("获取ip地址报错");
				e.printStackTrace();
			}
			balanceLog.setReason("取消订单退款");
			balanceLog.setOrderNumber(order.getOrderNumber());
			balanceLog.setDiySiteId(user.getUpperDiySiteId());
			balanceLog.setCityId(user.getCityId());
			balanceLog.setCashLeft(user.getCashBalance());
			balanceLog.setUnCashLeft(user.getUnCashBalance());
			balanceLog.setAllLeft(user.getBalance());
			tdBalanceLogService.save(balanceLog);
		}
		user = tdUserService.save(user);

		// 拆分使用的现金券的id
		if (null != cashCouponId && !"".equals(cashCouponId)) {
			String[] cashs = cashCouponId.split(",");
			if (null != cashs && cashs.length > 0) {
				for (String sId : cashs) {
					if (null != sId) {
						Long id = Long.valueOf(sId);
						TdCoupon coupon = tdCouponService.findOne(id);
						if (null != coupon && coupon.getTypeCategoryId().longValue() != 2L) {
							coupon.setIsUsed(false);
							coupon.setUseTime(null);
							tdCouponService.save(coupon);
						}
					}
				}
			}
		}

		// 拆分使用的产品券
		if (null != productCouponId && !"".equals(productCouponId)) {
			String[] products = productCouponId.split(",");
			if (null != products && products.length > 0) {
				for (String sId : products) {
					if (null != sId) {
						Long id = Long.valueOf(sId);
						TdCoupon coupon = tdCouponService.findOne(id);
						if (null != coupon) {
							coupon.setIsUsed(false);
							coupon.setUseTime(null);
							tdCouponService.save(coupon);
						}
					}
				}
			}
		}

		// 退还门店收取的资金
		// Double cashPay = order.getCashPay();
		// Double posPay = order.getPosPay();
		// Double otherPay = order.getOtherPay();
		//
		// if (null == cashPay) {
		// cashPay = 0.00;
		// }
		// if (null == posPay) {
		// posPay = 0.00;
		// }
		// if (null == otherPay) {
		// otherPay = 0.00;
		// }
		//
		// Double amount = cashPay + posPay + otherPay;
		//
		// TdCashRefundInf refundInf = new TdCashRefundInf();
		// refundInf.setSobId(user.getCityId());
		// refundInf.setRefundNumber(order.getOrderNumber());
		// refundInf.setUserid(user.getId());
		// refundInf.setUsername(user.getUsername());
		// refundInf.setUserphone(user.getUsername());
		// refundInf.setDiySiteCode(user.getDiyCode());
		// if (null != order.getIsCoupon() && order.getIsCoupon()) {
		// refundInf.setRefundClass("电子券");
		// refundInf.setDescription("电子券退款");
		//
		// } else {
		// refundInf.setRefundClass("订单");
		// refundInf.setDescription("订单退款");
		// }
		//
		// refundInf.setRtHeaderId(returnNote.getId());
		// refundInf.setReturnNumber(returnNote.getReturnNumber());
		// refundInf.setOrderHeaderId(order.getId());
		// if (order.getOrderNumber().contains("HR")) {
		// refundInf.setProductType("HR");
		// } else if (order.getOrderNumber().contains("YR")) {
		// refundInf.setProductType("YR");
		// } else if (order.getOrderNumber().contains("LYZ")) {
		// refundInf.setProductType("LYZ");
		// }
		//
		// refundInf.setRefundType("门店现金");
		// refundInf.setRefundDate(new Date());
		// refundInf.setAmount(amount);
		// tdCashRefundInfService.save(refundInf);
		//
		// // 生成打款记录
		// TdCashReturnNote new_return_note = new TdCashReturnNote();
		// new_return_note.setCreateTime(new Date());
		// new_return_note.setMoney(amount);
		// new_return_note.setTypeId(-1L);
		// new_return_note.setTypeTitle("门店现金");
		// new_return_note.setOrderNumber(order.getOrderNumber());
		// new_return_note.setMainOrderNumber(order.getMainOrderNumber());
		// new_return_note.setReturnNoteNumber(returnNote.getReturnNumber());
		// new_return_note.setUserId(user.getId());
		// new_return_note.setUsername(user.getUsername());
		// new_return_note.setIsOperated(true);
		// new_return_note.setFinishTime(new Date());
		// tdCashReturnNoteService.save(new_return_note);
		//
		// tdInterfaceService.ebsWithObject(refundInf, INFTYPE.CASHREFUNDINF);
	}

	/**
	 * 计算一个订单中所有的商品，包括促销赠品
	 * 
	 * @author DengXiao
	 */
	public Map<Long, Double[]> getAllOrderGoods(TdOrder order) {
		// result参数中key代表商品的id，Double数组中依次存储：0. 商品的购买单价；1. 商品的购买数量；
		// 2. 商品的总价；3.退货单价
		Map<Long, Double[]> result = new HashMap<>();
		if (null != order) {
			List<TdOrderGoods> goodsList = order.getOrderGoodsList();
			List<TdOrderGoods> presentedList = order.getPresentedList();
			if (null != goodsList && goodsList.size() > 0) {
				for (TdOrderGoods orderGoods : goodsList) {
					if (null != orderGoods) {
						Long goodsId = orderGoods.getGoodsId();
						Long quantity = orderGoods.getQuantity();
						Double price = orderGoods.getPrice();
						if (null == quantity) {
							quantity = 0L;
						}
						if (null == price) {
							price = 0.00;
						}
						if (null != goodsId && null == result.get(goodsId)) {
							Double[] val = new Double[3];
							val[0] = price;
							val[1] = new Double(quantity);
							val[2] = price * quantity;
							result.put(goodsId, val);
						} else if (null != goodsId && null != result.get(goodsId)) {
							Double[] val = result.get(goodsId);
							val[0] = price;
							val[1] = val[1] + new Double(quantity);
							val[2] = val[0] * val[1];
							result.put(goodsId, val);
						}
					}
				}
			}

			if (null != presentedList && presentedList.size() > 0) {
				for (TdOrderGoods orderGoods : presentedList) {
					if (null != orderGoods) {
						Long goodsId = orderGoods.getGoodsId();
						Long quantity = orderGoods.getQuantity();
						Double price = orderGoods.getGiftPrice();
						if (null == quantity) {
							quantity = 0L;
						}
						if (null == price) {
							price = 0.00;
						}
						if (null != goodsId && null == result.get(goodsId)) {
							Double[] val = new Double[3];
							val[0] = price;
							val[1] = new Double(quantity);
							val[2] = val[0] * val[1];
							result.put(goodsId, val);
						} else if (null != goodsId && null != result.get(goodsId)) {
							Double[] val = result.get(goodsId);
							val[0] = price;
							val[1] = val[1] + new Double(quantity);
							val[2] = val[0] * val[1];
							result.put(goodsId, val);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获取订单总价格的方法
	 * 
	 * @author DengXiao
	 */
	public Double getRealPrice(TdOrder order) {
		if (null == order) {
			return null;
		}
		Double total = 0.00;
		List<TdOrderGoods> goodsList = order.getOrderGoodsList();
		if (null == goodsList) {
			return null;
		}
		for (TdOrderGoods orderGoods : goodsList) {
			if (null != orderGoods && null != orderGoods.getPrice()) {
				total += (orderGoods.getPrice() * orderGoods.getQuantity());
			}
		}
		String productCouponId = order.getProductCouponId();
		if (null != productCouponId && !"".equalsIgnoreCase(productCouponId)) {
			String[] sIds = productCouponId.split(",");
			if (null != sIds && sIds.length > 0) {
				for (String sId : sIds) {
					if (null != sId && !"".equalsIgnoreCase(sId)) {
						Long id = Long.parseLong(sId);
						TdCoupon coupon = tdCouponService.findOne(id);
						if (null != coupon && null != coupon.getRealPrice() && coupon.getRealPrice() > 0) {
							total -= coupon.getRealPrice();
						}
					}
				}
			}
		}

		String cashProCouponId = order.getCashCouponId();
		if (null != cashProCouponId && !"".equalsIgnoreCase(cashProCouponId)) {
			String[] sIds = cashProCouponId.split(",");
			if (null != sIds && sIds.length > 0) {
				for (String sId : sIds) {
					if (null != sId && !"".equalsIgnoreCase(sId)) {
						Long id = Long.parseLong(sId);
						TdCoupon coupon = tdCouponService.findOne(id);
						if (isProductCashCoupon(coupon)) {
							total -= coupon.getPrice();
						}
					}
				}
			}
		}

		Double activitySubPrice = order.getActivitySubPrice();
		if (null == activitySubPrice) {
			activitySubPrice = 0.0;
		}
		total -= activitySubPrice;
		return total;
	}

	public Boolean isProductCashCoupon(TdCoupon tdCoupon) {
		if (tdCoupon == null) {
			return false;
		} else if (tdCoupon.getTypeCategoryId() == null) {
			return false;
		} else if (tdCoupon.getTypeCategoryId() != 2) {
			return false;
		} else if (tdCoupon.getPrice() == null) {
			return false;
		} else if (tdCoupon.getPrice() <= 0) {
			return false;
		} else if (tdCoupon.getIsBuy() != null && tdCoupon.getIsBuy() == true) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 计算订单商品实际价值的方法
	 * 
	 * @author DengXiao
	 */
	public Double getTotalOrderGoodsPrice(TdOrder order) {
		Map<Long, Double[]> map = this.getAllOrderGoods(order);
		// 创建一个变量存储商品实际总价
		Double totalPrice = 0.00;
		// 获取订单实际总价
		for (Double[] counts : map.values()) {
			if (null != counts && counts.length == 3) {
				Double single_total = counts[2];
				if (null == single_total) {
					single_total = 0.00;
				}
				totalPrice += single_total;
			}
		}

		String productCouponId = order.getProductCouponId();
		if (null != productCouponId && !"".equalsIgnoreCase(productCouponId)) {
			String[] sIds = productCouponId.split(",");
			if (null != sIds && sIds.length > 0) {
				for (String sId : sIds) {
					if (null != sId && !"".equalsIgnoreCase(sId)) {
						Long id = Long.parseLong(sId);
						TdCoupon coupon = tdCouponService.findOne(id);
						if (null != coupon && null != coupon.getRealPrice() && coupon.getRealPrice() > 0) {
							totalPrice -= coupon.getRealPrice();
						}
					}
				}
			}
		}

		// 修改：2016-08-26价格排除指定产品现金券的金额
		String cashCouponId = order.getCashCouponId();
		if (null != cashCouponId && !"".equalsIgnoreCase(cashCouponId)) {
			String[] sIds = cashCouponId.split(",");
			if (null != sIds && sIds.length > 0) {
				for (String sId : sIds) {
					if (null != sId && !"".equalsIgnoreCase(sId)) {
						Long id = Long.parseLong(sId);
						TdCoupon coupon = tdCouponService.findOne(id);
						if (null != coupon && null != coupon.getRealPrice() && coupon.getRealPrice() > 0
								&& coupon.getTypeCategoryId().longValue() == 2L) {
							totalPrice -= coupon.getRealPrice();
						}
					}
				}
			}
		}

		return totalPrice;
	}

	/**
	 * 计算订单单品退货单价的方法
	 * 
	 * @author DengXiao
	 */
	public Map<Long, Double> getReturnUnitPrice(TdOrder order) {
		Map<Long, Double> res = new HashMap<>();
		if (null == order) {
			return res;
		}
		// 获取订单的总价格
		Double realPrice = this.getRealPrice(order);
		// 获取订单的商品实际价值
		Double totalOrderGoodsPrice = this.getTotalOrderGoodsPrice(order);

		DecimalFormat decimalFormat = new DecimalFormat("#.00");

		// 获取用户使用的产品券
		String productCouponId = order.getProductCouponId();
		// 获取用户使用的现金券
		String cashCouponId = order.getCashCouponId();

		// 获取订单商品数量、总价队列
		Map<Long, Double[]> allOrderGoods = this.getAllOrderGoods(order);
		if (null != allOrderGoods && null != realPrice && 0.0 != realPrice && null != totalOrderGoodsPrice
				&& 0.0 != totalOrderGoodsPrice) {
			for (Long goodsId : allOrderGoods.keySet()) {
				// 获取指定商品的购买单价、购买数量、购买总价、退货单价队列
				if (null != goodsId) {
					Double[] infos = allOrderGoods.get(goodsId);
					// 判断是否获取到一个正确的队列
					if (null != infos && infos.length == 3) {
						// 修改：2016-06-26 在计算单价的时候排除产品券的使用价值和使用数量
						if (null != productCouponId && !"".equals(productCouponId)) {
							String[] sIds = productCouponId.split(",");
							if (null != sIds && sIds.length > 0) {
								for (String sId : sIds) {
									Long id = Long.parseLong(sId);
									TdCoupon coupon = tdCouponService.findOne(id);
									if (null != coupon && null != coupon.getTypeCategoryId()
											&& coupon.getTypeCategoryId().longValue() == 3L
											&& null != coupon.getGoodsId()
											&& coupon.getGoodsId().longValue() == goodsId.longValue()) {
										Double productCouponRealPrice = coupon.getRealPrice();
										if (null == productCouponRealPrice) {
											productCouponRealPrice = 0.00;
										}
										infos[1] -= 1;
									}
								}
							}
						}
						// ----------------------------2016-06-26修改结束-------------------------
						// 取出商品的总价
						Double goodsTotalPrice = infos[0] * infos[1];
						// 修改：2016-08-26需要忽略指定产品现金券使用的金额
						if (null != cashCouponId && !"".equals(cashCouponId)) {
							String[] sIds = cashCouponId.split(",");
							if (null != sIds && sIds.length > 0) {
								for (String sId : sIds) {
									Long id = Long.parseLong(sId);
									TdCoupon coupon = tdCouponService.findOne(id);
									if (null != coupon && null != coupon.getTypeCategoryId()
											&& coupon.getTypeCategoryId().longValue() == 2L
											&& null != coupon.getGoodsId()
											&& coupon.getGoodsId().longValue() == goodsId.longValue()) {
										Double couponRealPrice = coupon.getRealPrice();
										if (null == couponRealPrice) {
											couponRealPrice = 0.00;
										}
										goodsTotalPrice -= couponRealPrice;
									}
								}
							}
						}
						// ----------------------------2016-08-26修改结束-------------------------
						if (null != goodsTotalPrice) {
							// 开始计算该件商品的价值比例
							Double point = goodsTotalPrice / totalOrderGoodsPrice;

							// 通过这个比例计算实际上的商品退货单价
							Double GoodsRealTotal = realPrice * point;
							if (null != GoodsRealTotal && null != infos[1] && 0.0 != infos[1]) {
								Double unit = GoodsRealTotal / infos[1];
								String sUnit = decimalFormat.format(unit);
								unit = Double.parseDouble(sUnit);

								res.put(goodsId, unit);
							}
						}
					}
				}
			}
		}
		return res;
	}

	/**
	 * 获取指定订单的优惠券使用情况
	 * 
	 * @author DengXiao
	 */
	public Map<String, Object> countCouponCondition(Long orderId) {
		Map<String, Object> result = new HashMap<>();
		// 默认未使用指定产品现金券
		result.put("useProCashCoupon", false);
		// 默认未使用产品券
		result.put("useProCoupon", false);
		// 默认未使用通用现金券
		result.put("useCashCoupon", false);

		TdOrder order = tdOrderService.findOne(orderId);
		if (null == order) {
			return result;
		}
		String cashCouponId = order.getCashCouponId();
		String productCouponId = order.getProductCouponId();

		// 定义一个变量用于表示使用的通用现金券的真实总额
		Double cashTotal = 0.00;

		// 开始拆分使用的现金券
		if (null != cashCouponId && !"".equals(cashCouponId)) {
			String[] ids = cashCouponId.split(",");
			if (null != ids && ids.length > 0) {
				for (String sId : ids) {
					if (null != sId && !"".equals(sId)) {
						Long id = Long.parseLong(sId);
						TdCoupon coupon = tdCouponService.findOne(id);
						if (null != coupon) {
							Long categoryId = coupon.getTypeCategoryId();
							if (null != categoryId && 1L == categoryId.longValue()) {
								if (null == coupon.getRealPrice()) {
									coupon.setRealPrice(0.00);
								}
								cashTotal += coupon.getRealPrice();
								result.put("useCashCoupon", true);
							}
							if (null != categoryId && 2L == categoryId.longValue()) {
								Long goodsId = coupon.getGoodsId();
								Double price = coupon.getPrice();
								if (null == price) {
									price = 0.00;
								}
								if (null != goodsId && null != result.get("proCash" + goodsId)) {
									Double usePrice = (Double) result.get("proCash" + goodsId);
									if (null == usePrice) {
										usePrice = 0.00;
									}
									result.put("proCash" + goodsId, usePrice + price);
								}
								if (null != goodsId && null == result.get("proCash" + goodsId)) {
									result.put("proCash" + goodsId, price);
								}
								result.put("useProCashCoupon", true);
							}
						}
					}
				}
			}
		}

		// 开始拆分使用的产品券
		if (null != productCouponId && !"".equals(productCouponId)) {
			String[] ids = productCouponId.split(",");
			if (null != ids && ids.length > 0) {
				for (String sId : ids) {
					if (null != sId && !"".equals(sId)) {
						Long id = Long.parseLong(sId);
						TdCoupon coupon = tdCouponService.findOne(id);
						if (null != coupon) {
							Long goodsId = coupon.getGoodsId();

							// add 计算电子产品券金额 mdj
							Boolean isCashPro = coupon.getIsBuy();// 判断是否是电子产品券
							if (isCashPro != null && isCashPro && null != null
									&& null != result.get("cashPro" + goodsId)) {
								Integer cashPorNumber = (Integer) result.get("cashPro" + goodsId);
								@SuppressWarnings("unchecked")
								List<Double> doubles = (List<Double>) result.get("cashPrice" + goodsId);
								doubles.add(coupon.getPrice());
								result.put("cashPro" + goodsId, cashPorNumber + 1);
							}
							if (isCashPro != null && isCashPro && null != null
									&& null == result.get("cashPro" + goodsId)) {
								result.put("cashPro" + goodsId, 1);
								List<Double> doubles = new ArrayList<Double>();
								result.put("cashPrice" + goodsId, doubles.add(coupon.getPrice()));
							}
							// add end

							if (null != goodsId && null != result.get("pro" + goodsId)) {
								Integer number = (Integer) result.get("pro" + goodsId);
								result.put("pro" + goodsId, number + 1);
							}
							if (null != goodsId && null == result.get("pro" + goodsId)) {
								result.put("pro" + goodsId, 1);
							}
							result.put("useProCoupon", true);
						}
					}
				}
			}
		}

		result.put("cashTotal", cashTotal);
		return result;
	}

	/**
	 * 开始退还钱/券的方法
	 * 
	 * @param params的规则为【商品id】-【退货数量】-【退货单价】
	 * @author DengXiao
	 */
	public List<TdCashReturnNote> returnCashOrCoupon(Long orderId, String params, String returnNoteNumber) {
		List<TdCashReturnNote> tdCashReturnNotes = new ArrayList<>();
		TdOrder order = tdOrderService.findOne(orderId);
		Long userId = order.getRealUserId();
		TdUser user = tdUserService.findOne(userId);
		String cityName = user.getCityName();
		TdCity city = tdCityService.findByCityName(cityName);
		if (null != order && null != params && !"".equals(params)
				&& (null != order.getIsRefund() && order.getIsRefund())) {
			Map<String, Object> result = this.countCouponCondition(orderId);

			Boolean useProCoupon = (Boolean) result.get("useProCoupon");
			Boolean useCashCoupon = (Boolean) result.get("useCashCoupon");

			Double cashTotal = (Double) result.get("cashTotal");
			// 获取订单使用的总不可提现余额
			Double unCashBalanceUsed = order.getUnCashBalanceUsed();
			// 获取用户使用的可提现余额
			Double cashBalanceUsed = order.getCashBalanceUsed();
			// 获取用户第三方支付的金额
			Double otherPay = order.getOtherPay();
			// 获取用户支付的现金
			Double cashPay = order.getCashPay();
			if (cashPay == null) {
				cashPay = 0d;
			}
			// 获取用户支付的POS
			Double posPay = order.getPosPay();
			if (null == posPay) {
				posPay = 0d;
			}
			// 获取用户支付的其他
			Double backOtherPay = order.getBackOtherPay();
			if (null == backOtherPay) {
				backOtherPay = 0d;
			}

			/* Double all_off_line = posPay + cashPay + backOtherPay; */

			// 获取一年后的时间（新的券的有效时间）
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.YEAR, 1);
			Date endTime = cal.getTime();

			// 2016-07-05修改：目前的退款全部退还现金
			// 创建一个打款申请用于存储需要打款的现金，该实体不会进行持久化操作
			TdCashReturnNote new_return_note = new TdCashReturnNote();
			new_return_note.setCreateTime(new Date());
			new_return_note.setMoney(0.00);
			new_return_note.setTypeId(-1L);
			new_return_note.setTypeTitle("门店现金");
			new_return_note.setOrderNumber(order.getOrderNumber());
			new_return_note.setMainOrderNumber(order.getMainOrderNumber());
			new_return_note.setReturnNoteNumber(returnNoteNumber);
			new_return_note.setUserId(user.getId());
			new_return_note.setUsername(user.getUsername());
			new_return_note.setIsOperated(true);
			new_return_note.setFinishTime(new Date());
			// 修改结束

			Map<Long, Double> price_difference = new HashMap<>();

			// 2016-06-26修改：需要获取用户使用赠送的产品券和购买的产品券的集合
			Map<Long, ArrayList<TdCoupon>> buy_pro_coupon_condition = new HashMap<>();
			Map<Long, ArrayList<TdCoupon>> send_pro_coupon_condition = new HashMap<>();

			String productCouponId = order.getProductCouponId();

			if (null != productCouponId && !"".equalsIgnoreCase(productCouponId)) {
				String[] sIds = productCouponId.split(",");
				if (null != sIds && sIds.length > 0) {
					for (String sId : sIds) {
						if (null != sId && !"".equalsIgnoreCase(sId)) {
							Long couponId = Long.parseLong(sId);
							TdCoupon coupon = tdCouponService.findOne(couponId);
							if (null != coupon) {
								Long goodsId = coupon.getGoodsId();
								if (null != coupon.getIsBuy() && coupon.getIsBuy()) {
									ArrayList<TdCoupon> list = buy_pro_coupon_condition.get(goodsId);
									if (null == list) {
										list = new ArrayList<>();
									}
									list.add(coupon);
									buy_pro_coupon_condition.put(goodsId, list);
								} else {
									ArrayList<TdCoupon> list = send_pro_coupon_condition.get(goodsId);
									if (null == list) {
										list = new ArrayList<>();
									}
									list.add(coupon);
									send_pro_coupon_condition.put(goodsId, list);
								}
							}
						}
					}
				}
			}
			// -----------------------------修改结束--------------------------------

			// 修改：2016-06-26 计算这些商品使用的指定产品现金券的金额，这部分金额是不会退还的
			Map<Long, ArrayList<TdCoupon>> cash__pro_coupon_condition = new HashMap<>();
			String cashCouponId = order.getCashCouponId();
			if (null != cashCouponId && !"".equalsIgnoreCase(cashCouponId)) {
				String[] sCashIds = cashCouponId.split(",");
				if (null != sCashIds && sCashIds.length > 0) {
					for (String sId : sCashIds) {
						if (null != sId && !"".equalsIgnoreCase(sId)) {
							Long id = Long.parseLong(sId);
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon && null != coupon.getTypeCategoryId()
									&& 2L == coupon.getTypeCategoryId().longValue()) {
								Long goodsId = coupon.getGoodsId();
								ArrayList<TdCoupon> cash_coupon = cash__pro_coupon_condition.get(goodsId);
								if (null == cash_coupon) {
									cash_coupon = new ArrayList<>();
								}
								cash_coupon.add(coupon);
								cash__pro_coupon_condition.put(goodsId, cash_coupon);
							}
						}
					}
				}
			}
			// -----------------------------修改结束-----------------------------------

			// 开始拆分退货参数
			String[] param = params.split(",");
			if (null != param && param.length > 0) {
				for (String group : param) {
					if (null != group && !"".equals(group)) {
						String[] singles = group.split("-");
						// 判断singles是否为一个正确的参数
						if (null != singles && singles.length == 3) {
							String sGoodsId = singles[0];
							Long goodsId = null;
							String sNumber = singles[1];
							Long number = 0L;
							String sUnit = singles[2];
							Double unit = 0.00;
							if (null != sGoodsId && !"".equals(sGoodsId)) {
								goodsId = Long.parseLong(sGoodsId);
							}
							if (null != sNumber && !"".equals(sNumber)) {
								number = Long.parseLong(sNumber);
							}
							if (null != sUnit && !"".equals(sUnit)) {
								unit = Double.parseDouble(sUnit);
							}

							// 计算该商品的退货总额
							Double total = number * unit;
							if (null != goodsId) {
								TdGoods goods = tdGoodsService.findOne(goodsId);

								// 2016-06-24修改：需要排除是退货部分使用的指定产品现金券的价格
								Double sub_coupon_price = 0.00;
								ArrayList<TdCoupon> coupon_list = cash__pro_coupon_condition.get(goodsId);
								List<TdCoupon> delete_coupon = new ArrayList<>();
								if (null != coupon_list) {
									for (int i = 0; i < number; i++) {
										if (coupon_list.size() > i) {
											TdCoupon tdCoupon = coupon_list.get(i);
											Double realPrice = tdCoupon.getRealPrice();
											sub_coupon_price += realPrice;
											delete_coupon.add(tdCoupon);
										}
									}

									for (TdCoupon tdCoupon : delete_coupon) {
										coupon_list.remove(tdCoupon);
									}
									delete_coupon = null;
									cash__pro_coupon_condition.put(goodsId, coupon_list);
								}

								// total -= sub_coupon_price;
								Double record = price_difference.get(goodsId);
								if (null == record) {
									record = 0.00;
								}
								record += sub_coupon_price;
								price_difference.put(goodsId, record);
								// --------------------修改结束-----------------------

								// 开始退还产品券
								if (total >= 0) {
									if (useProCoupon) {
										// 查找本产品是否使用了产品券
										Integer useNumber = (Integer) result.get("pro" + goodsId);
										if (null != useNumber && useNumber > 0) {
											// 开始计算退还几张券
											for (int i = 0; i < useNumber; i++) {
												if (number > 0) {
													TdCoupon proCoupon = new TdCoupon();
													proCoupon.setTypeId(3L);
													proCoupon.setTypeCategoryId(3L);
													if (null != goods) {
														proCoupon.setBrandId(goods.getBrandId());
														proCoupon.setBrandTitle(goods.getBrandTitle());
													}
													proCoupon.setPicUri(goods.getCoverImageUri());
													proCoupon.setGoodsId(goods.getId());
													proCoupon.setPrice(0.0);
													proCoupon.setTypeTitle("退货返还的优惠券");
													proCoupon.setGoodsName(goods.getTitle());
													proCoupon.setIsDistributted(true);
													if (null != city) {
														proCoupon.setCityName(city.getCityName());
														proCoupon.setCityId(city.getId());
													}
													proCoupon.setGetTime(new Date());
													proCoupon.setAddTime(new Date());
													proCoupon.setGetNumber(1L);
													proCoupon.setExpireTime(endTime);
													proCoupon.setUsername(order.getUsername());
													proCoupon.setIsUsed(false);
													proCoupon.setIsOutDate(false);
													proCoupon.setMobile(order.getUsername());
													proCoupon.setSku(goods.getCode());
													// add MDJ
													proCoupon.setOrderId(orderId);
													proCoupon.setOrderNumber(order.getOrderNumber());
													// add end

													// 在此判断是返回送的券还是返回买的券，优先返还送的券
													ArrayList<TdCoupon> buy_list = buy_pro_coupon_condition
															.get(goodsId);
													ArrayList<TdCoupon> send_list = send_pro_coupon_condition
															.get(goodsId);

													if (null != send_list && send_list.size() > 0) {
														TdCoupon tdCoupon = send_list.get(0);
														proCoupon.setIsBuy(false);
														proCoupon.setRealPrice(tdCoupon.getRealPrice());
														proCoupon.setBuyPrice(0.00);
														proCoupon.setDiySiteCode(tdCoupon.getDiySiteCode());
														proCoupon.setDiySiteTital(tdCoupon.getDiySiteTital());
														proCoupon.setSellerId(tdCoupon.getSellerId());
														proCoupon.setSellerRealName(tdCoupon.getSellerRealName());
														proCoupon.setSellerUsername(tdCoupon.getSellerUsername());
														proCoupon.setCouponOrderNumber(tdCoupon.getCouponOrderNumber());
														send_list.remove(tdCoupon);
														send_pro_coupon_condition.put(goodsId, send_list);
													} else if (null != buy_list && buy_list.size() > 0) {
														TdCoupon tdCoupon = buy_list.get(0);
														proCoupon.setIsBuy(true);
														proCoupon.setRealPrice(tdCoupon.getRealPrice());
														proCoupon.setBuyPrice(tdCoupon.getBuyPrice());
														proCoupon.setDiySiteCode(tdCoupon.getDiySiteCode());
														proCoupon.setDiySiteTital(tdCoupon.getDiySiteTital());
														proCoupon.setSellerId(tdCoupon.getSellerId());
														proCoupon.setSellerRealName(tdCoupon.getSellerRealName());
														proCoupon.setSellerUsername(tdCoupon.getSellerUsername());
														proCoupon.setCouponOrderNumber(tdCoupon.getCouponOrderNumber());
														buy_list.remove(tdCoupon);
														buy_pro_coupon_condition.put(goodsId, buy_list);
													}

													tdCouponService.save(proCoupon);
													total -= unit;
													number--;
													result.put("pro" + goodsId,
															((Integer) result.get("pro" + goodsId) - 1));
												}
											}
										}
									}
								}
								// 开始退还通用现金券
								if (total > 0) {
									if (useCashCoupon) {
										// 声明一个变量用来表示退还的通用现金券的面额
										Double cashPrice = 0.00;
										if (cashTotal > total) {
											cashPrice = total;
										} else {
											cashPrice = cashTotal;
										}
										if (cashPrice > 0) {
											TdCoupon cashCoupon = new TdCoupon();
											cashCoupon.setTypeId(3L);
											cashCoupon.setTypeCategoryId(1L);
											if (null != goods) {
												cashCoupon.setBrandId(goods.getBrandId());
												cashCoupon.setBrandTitle(goods.getBrandTitle());
											}
											cashCoupon.setPicUri(goods.getCoverImageUri());
											BigDecimal bd = new BigDecimal(cashPrice);
											cashPrice = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
											cashCoupon.setPrice(cashPrice);
											cashCoupon.setTypeTitle("退货返还的优惠券");
											cashCoupon.setGetNumber(1L);
											if (null != city) {
												cashCoupon.setCityName(city.getCityName());
												cashCoupon.setCityId(city.getId());
											}
											cashCoupon.setAddTime(new Date());
											cashCoupon.setIsDistributted(true);
											cashCoupon.setGetTime(new Date());
											cashCoupon.setExpireTime(endTime);
											cashCoupon.setUsername(order.getUsername());
											cashCoupon.setIsUsed(false);
											cashCoupon.setIsOutDate(false);
											cashCoupon.setMobile(order.getUsername());
											// add MDJ
											cashCoupon.setOrderId(orderId);
											cashCoupon.setOrderNumber(order.getOrderNumber());
											// add end
											tdCouponService.save(cashCoupon);

											total -= cashPrice;
											cashTotal -= cashPrice;
											result.put("cashTotal", cashTotal - cashPrice);
										}
									}
								}
								// 如果还有金额没有退还，再退还不可提现余额
								if (total > 0) {
									// 定义实际退还的不可提现余额
									Double uncashBalance = 0.00;

									if (null == unCashBalanceUsed) {
										unCashBalanceUsed = 0.00;
									}

									if (total < unCashBalanceUsed) {
										// 需要退还的金额小于用户使用的总的不可提现余额，则直接退还退还的总额
										uncashBalance = total;
									} else {
										// 如果需要退还的金额大于等于用户使用的不可提现余额，则只能够退还用户使用的不可提现余额
										uncashBalance = unCashBalanceUsed;
									}
									// 开始退还不可提现余额
									if (uncashBalance > 0) {
										BigDecimal bd = new BigDecimal(uncashBalance);
										uncashBalance = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
										user.setUnCashBalance(user.getUnCashBalance() + uncashBalance);
										user.setBalance(user.getBalance() + uncashBalance);
										// 添加用于余额变更明细
										TdBalanceLog balanceLog = new TdBalanceLog();
										balanceLog.setUserId(user.getId());
										balanceLog.setUsername(user.getUsername());
										balanceLog.setMoney(uncashBalance);
										balanceLog.setType(4L);
										balanceLog.setCreateTime(new Date());
										balanceLog.setFinishTime(new Date());
										balanceLog.setIsSuccess(true);
										balanceLog.setBalanceType(4L);
										balanceLog.setBalance(user.getUnCashBalance());
										balanceLog.setOperator(user.getUsername());
										try {
											balanceLog.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
										} catch (UnknownHostException e) {
											System.out.println("获取ip地址报错");
											e.printStackTrace();
										}
										balanceLog.setReason("订单退货退款");
										balanceLog.setOrderNumber(order.getOrderNumber());
										balanceLog.setDiySiteId(user.getUpperDiySiteId());
										balanceLog.setCityId(user.getCityId());
										balanceLog.setCashLeft(user.getCashBalance());
										balanceLog.setUnCashLeft(user.getUnCashBalance());
										balanceLog.setAllLeft(user.getBalance());
										tdBalanceLogService.save(balanceLog);
									}
									// 判断是否剩余部分金额需要退还
									total -= uncashBalance;
									unCashBalanceUsed -= uncashBalance;
								}

								// 如果还有剩余的金额没有退还，则开始退还可提现余额
								if (total > 0) {
									if (null == cashBalanceUsed) {
										cashBalanceUsed = 0.00;
									}
									// 定义一个变量用于获取用户使用的可提现余额
									Double cashBalance = 0.00;
									// 如果需要退还的金额小于用户使用的可提现余额，则将所有的金额以可提现余额的形式退还
									if (total < cashBalanceUsed) {
										cashBalance = total;
									} else {
										cashBalance = cashBalanceUsed;
									}
									// 开始退还余额
									if (cashBalance > 0) {
										BigDecimal bd = new BigDecimal(cashBalance);
										cashBalance = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
										user.setCashBalance(user.getCashBalance() + cashBalance);
										user.setBalance(user.getBalance() + cashBalance);
										// 记录余额变更明细
										TdBalanceLog balanceLog = new TdBalanceLog();
										balanceLog.setUserId(user.getId());
										balanceLog.setUsername(user.getUsername());
										balanceLog.setMoney(cashBalance);
										balanceLog.setType(4L);
										balanceLog.setCreateTime(new Date());
										balanceLog.setFinishTime(new Date());
										balanceLog.setIsSuccess(true);
										balanceLog.setBalanceType(3L);
										balanceLog.setBalance(user.getCashBalance());
										balanceLog.setOperator(user.getUsername());
										try {
											balanceLog.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
										} catch (UnknownHostException e) {
											System.out.println("获取ip地址报错");
											e.printStackTrace();
										}
										balanceLog.setReason("订单退货退款");
										balanceLog.setOrderNumber(order.getOrderNumber());
										balanceLog.setDiySiteId(user.getUpperDiySiteId());
										balanceLog.setCityId(user.getCityId());
										balanceLog.setCashLeft(user.getCashBalance());
										balanceLog.setUnCashLeft(user.getUnCashBalance());
										balanceLog.setAllLeft(user.getBalance());
										tdBalanceLogService.save(balanceLog);
									}
									total -= cashBalance;
									cashBalanceUsed -= cashBalance;
								}
								// 如果还剩余部分金额没有退还，则需要根据支付方式进行第三方退还
								if (total > 0) {

									// 判断用户是否使用了第三方支付
									if (null != otherPay && otherPay > 0.00) {
										// 获取用户的支付方式
										Long payTypeId = order.getPayTypeId();
										TdPayType payType = tdPayTypeService.findOne(payTypeId);
										// 定义一个变量表示退款金额数
										Double otherReturn = 0.00;
										if (total < otherPay) {
											otherReturn = total;
										} else {
											otherReturn = otherPay;
										}

										if (otherReturn > 0.00) {
											// 根据退款方式和退货金额生成一个资金退还申请单据
											TdCashReturnNote note = new TdCashReturnNote();
											note.setCreateTime(new Date());
											note.setMoney(otherReturn);
											// 如果支付方式属于线上支付，那么一定是支付宝、微信、银行卡的一种，则按照正常逻辑处理
											// 当订单价格为0 支付方式为其他 payType值为null
											note.setTypeId(payTypeId);
											note.setTypeTitle(payType.getTitle());
											note.setOrderNumber(order.getOrderNumber());
											note.setMainOrderNumber(order.getMainOrderNumber());
											note.setReturnNoteNumber(returnNoteNumber);
											note.setUserId(user.getId());
											note.setUsername(user.getUsername());
											note.setIsOperated(false);

											// 2016-07-05修改：以现金的方式退还第三方的钱，不做持久化操作
											// note =
											// tdCashReturnNoteService.save(note);
											new_return_note.setMoney(new_return_note.getMoney() + otherReturn);
											// 修改结束

											otherPay -= otherReturn;
										}
										// ----------在此处理退款申请单的一系列操作动作-------------------

									} else {
										if (null == cashPay) {
											cashPay = 0.00;
										}

										Double cashReturn = 0.00;

										if (total < cashPay) {
											cashReturn = total;
										} else {
											cashReturn = cashPay;
										}

										if (cashReturn > 0.00) {
											// 根据退款方式和退货金额生成一个资金退还申请单据
											TdCashReturnNote note = new TdCashReturnNote();
											note.setCreateTime(new Date());
											note.setMoney(cashReturn);
											note.setTypeId(-1L);
											note.setTypeTitle("现金");
											note.setOrderNumber(order.getOrderNumber());
											note.setMainOrderNumber(order.getMainOrderNumber());
											note.setReturnNoteNumber(returnNoteNumber);
											note.setUserId(user.getId());
											note.setUsername(user.getUsername());
											note.setIsOperated(false);

											// 2016-07-05修改：不做持久化操作
											// note =
											// tdCashReturnNoteService.save(note);
											new_return_note.setMoney(new_return_note.getMoney() + cashReturn);
											// 修改结束

											total -= cashReturn;
											cashPay -= cashReturn;
										}
										// 退还现金后还剩余未退还的金额，就退还POS
										if (total > 0) {
											Double posReturn = 0.00;

											if (total < posPay) {
												posReturn = total;
											} else {
												posReturn = posPay;
											}

											if (posReturn > 0.00) {
												// 根据退款方式和退货金额生成一个资金退还申请单据
												TdCashReturnNote note01 = new TdCashReturnNote();
												note01.setCreateTime(new Date());
												note01.setMoney(posReturn);
												// 如果支付方式属于线上支付，那么一定是支付宝、微信、银行卡的一种，则按照正常逻辑处理
												// 当订单价格为0 支付方式为其他 payType值为null
												note01.setTypeId(-2L);
												note01.setTypeTitle("POS");
												note01.setOrderNumber(order.getOrderNumber());
												note01.setMainOrderNumber(order.getMainOrderNumber());
												note01.setReturnNoteNumber(returnNoteNumber);
												note01.setUserId(user.getId());
												note01.setUsername(user.getUsername());
												note01.setIsOperated(false);
												// 2016-07-05修改：以现金的方式退还POS支付的钱，不做持久化操作
												// note01 =
												// tdCashReturnNoteService.save(note01);
												new_return_note.setMoney(new_return_note.getMoney() + posReturn);
												// 修改结束

												total -= posReturn;
												posPay -= posReturn;
											}
										}
										// 如果还有未退还的金额，则退还其他支付方式支付的金额
										if (total > 0) {
											if (null == backOtherPay) {
												backOtherPay = 0.00;
											}

											Double otherReturn = 0.00;

											if (total < backOtherPay) {
												otherReturn = total;
											} else {
												otherReturn = backOtherPay;
											}

											if (otherReturn > 0.00) {
												// 根据退款方式和退货金额生成一个资金退还申请单据
												TdCashReturnNote note = new TdCashReturnNote();
												note.setCreateTime(new Date());
												note.setMoney(otherReturn);
												note.setTypeId(-3L);
												note.setTypeTitle("其他");
												note.setOrderNumber(order.getOrderNumber());
												note.setMainOrderNumber(order.getMainOrderNumber());
												note.setReturnNoteNumber(returnNoteNumber);
												note.setUserId(user.getId());
												note.setUsername(user.getUsername());
												note.setIsOperated(false);

												// 2016-07-05修改：不做持久化操作
												// note =
												// tdCashReturnNoteService.save(note);
												new_return_note.setMoney(new_return_note.getMoney() + otherReturn);
												// 修改结束

												total -= otherReturn;
												backOtherPay -= otherReturn;
											}
										}
									}
								}
							}
						}
					}
				}

				/*
				 * if (new_return_note.getMoney() > all_off_line) {
				 * new_return_note.setMoney(all_off_line); }
				 */

				// 2016-07-05修改：持久化总的打款记录，当收款现金为零不持久化
				if (new_return_note.getMoney() != 0) {
					tdCashReturnNoteService.save(new_return_note);
				}
				if (new_return_note != null) {
					tdCashReturnNotes.add(new_return_note);
				}
				// 修改结束
			}
			tdUserService.save(user);
			if (tdCashReturnNotes != null && tdCashReturnNotes.size() > 0) {
				return tdCashReturnNotes;
			}
		}
		return null;
	}

	/**
	 * 根据WMS返回信息退还券和钱
	 * 
	 * @author DengXiao
	 */
	public List<TdCashReturnNote> actAccordingWMS(TdReturnNote returnNote, Long orderId) {
		if (null != returnNote) {
			List<TdOrderGoods> returnGoodsList = returnNote.getReturnGoodsList();
			if (null != returnGoodsList && returnGoodsList.size() > 0) {
				String params = "";
				for (TdOrderGoods goods : returnGoodsList) {
					if (null != goods && null != goods.getGoodsId() && null != goods.getQuantity()
							&& null != goods.getPrice()) {
						params += goods.getGoodsId() + "-" + goods.getQuantity() + "-" + goods.getReturnUnitPrice()
								+ ",";
					}
				}
				return this.returnCashOrCoupon(orderId, params, returnNote.getReturnNumber());
			}
		}
		return null;
	}

	/**
	 * 自动发放指定产品现金券的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月26日下午6:33:44
	 */
	public void sendCoupon(TdOrder order) {
		// 获取真实用户
		Long realUserId = order.getRealUserId();
		TdUser user = tdUserService.findOne(realUserId);
		// 只有认证会员才会被赠送优惠券
		if (null != user && null != user.getSellerId()) {

			// 获取所有的商品
			List<TdOrderGoods> goodsList = order.getOrderGoodsList();

			// 确定有购买的商品才进行送券
			if (null != goodsList && goodsList.size() > 0) {
				// 获取3个月后的日期
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.MONTH, 3);
				Date expiredTime = cal.getTime();

				// 获取城市
				TdCity city = tdCityService.findBySobIdCity(user.getCityId());
				if (null == city) {
					return;
				}

				Long type = 0L;
				if (null != order.getIsCoupon() && order.getIsCoupon()) {
					type = 1L;
				}

				for (TdOrderGoods orderGoods : goodsList) {
					// 如果买的商品本身就是券，则不赠送
					if (null != orderGoods) {
						Long goodsId = orderGoods.getGoodsId();
						Long quantity = orderGoods.getQuantity();
						if (null == goodsId || null == quantity) {
							continue;
						}

						// 根据商品id和城市id查找模板
						TdCouponModule module = tdCouponModuleService.findByGoodsIdAndCityIdAndType(goodsId,
								user.getCityId(), type);
						TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(user.getCityId(), goodsId);
						// if (null != module) {
						// 购买的数量为多少就赠送多少
						for (int i = 0; i < quantity; i++) {
							TdCoupon coupon = new TdCoupon();
							coupon.setTypeId(4L);
							coupon.setTypeCategoryId(2L);
							coupon.setBrandId(module.getBrandId());
							coupon.setBrandTitle(module.getBrandTitle());
							coupon.setGoodsId(module.getGoodsId());
							coupon.setPicUri(module.getPicUri());
							coupon.setGoodsName(module.getGoodsTitle());
							coupon.setTypeTitle(module.getSku() + "产品现金券");
							coupon.setTypeDescription(module.getSku() + "产品现金券");
							coupon.setTypePicUri(module.getPicUri());
							coupon.setPrice(priceListItem.getSalePrice() - priceListItem.getRealSalePrice());
							coupon.setIsDistributted(true);
							coupon.setGetTime(new Date());
							coupon.setUsername(user.getUsername());
							coupon.setIsUsed(false);
							coupon.setIsOutDate(false);
							coupon.setExpireTime(expiredTime);
							coupon.setMobile(user.getUsername());
							coupon.setSku(module.getSku());
							coupon.setCityId(city.getId());
							coupon.setCityName(city.getCityName());
							tdCouponService.save(coupon);
						}
						// }
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param order
	 *            订单
	 * @param returnNote
	 *            退单
	 * @return resultMap 包含券和预存款
	 */
	public Map<String, Object> getBalanceAndCouponWithReturnNoteAndOrder(TdOrder order, TdReturnNote returnNote) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (order == null || returnNote == null) {
			return null;
		}

		// 退换的券
		List<TdCoupon> returnCouponList = new ArrayList<TdCoupon>();

		// 退换的预存款
		Double returnBalance = 0.0;

		// 退货商品会员价和零售价差价
		Map<String, Double> price_difference = new HashMap<>();

		String params = "";

		if (null != returnNote) {
			List<TdOrderGoods> returnGoodsList = returnNote.getReturnGoodsList();
			if (null != returnGoodsList && returnGoodsList.size() > 0) {
				for (TdOrderGoods goods : returnGoodsList) {
					if (null != goods && null != goods.getGoodsId() && null != goods.getQuantity()
							&& null != goods.getPrice()) {
						params += goods.getGoodsId() + "-" + goods.getQuantity() + "-" + goods.getReturnUnitPrice()
								+ ",";
					}
				}
			}
		}

		Long userId = order.getRealUserId();
		TdUser user = tdUserService.findOne(userId);
		String cityName = user.getCityName();
		TdCity city = tdCityService.findByCityName(cityName);
		if (null != order && null != params && !"".equals(params)
				&& (null != order.getIsRefund() && order.getIsRefund())) {
			Map<String, Object> result = this.countCouponCondition(order.getId());

			Boolean useProCoupon = (Boolean) result.get("useProCoupon");
			Boolean useCashCoupon = (Boolean) result.get("useCashCoupon");

			Double cashTotal = (Double) result.get("cashTotal");
			// 获取订单使用的总不可提现余额
			Double unCashBalanceUsed = order.getUnCashBalanceUsed();
			// 获取用户使用的可提现余额
			Double cashBalanceUsed = order.getCashBalanceUsed();
			// 获取用户第三方支付的金额
			Double otherPay = order.getOtherPay();
			// 获取用户支付的现金
			Double cashPay = order.getCashPay();
			if (null == cashPay) {
				cashPay = 0d;
			}
			// 获取用户支付的POS
			Double posPay = order.getPosPay();
			if (null == posPay) {
				posPay = 0d;
			}
			Double backOtherPay = order.getBackOtherPay();
			if (null == backOtherPay) {
				backOtherPay = 0d;
			}

			// 获取一年后的时间（新的券的有效时间）
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.YEAR, 1);
			Date endTime = cal.getTime();

			// Map<Long, Double> price_difference = new HashMap<>();

			// 2016-06-26修改：需要获取用户使用赠送的产品券和购买的产品券的集合
			Map<Long, ArrayList<TdCoupon>> buy_pro_coupon_condition = new HashMap<>();
			Map<Long, ArrayList<TdCoupon>> send_pro_coupon_condition = new HashMap<>();

			String productCouponId = order.getProductCouponId();

			if (null != productCouponId && !"".equalsIgnoreCase(productCouponId)) {
				String[] sIds = productCouponId.split(",");
				if (null != sIds && sIds.length > 0) {
					for (String sId : sIds) {
						if (null != sId && !"".equalsIgnoreCase(sId)) {
							Long couponId = Long.parseLong(sId);
							TdCoupon coupon = tdCouponService.findOne(couponId);
							if (null != coupon) {
								Long goodsId = coupon.getGoodsId();
								if (null != coupon.getIsBuy() && coupon.getIsBuy()) {
									ArrayList<TdCoupon> list = buy_pro_coupon_condition.get(goodsId);
									if (null == list) {
										list = new ArrayList<>();
									}
									list.add(coupon);
									buy_pro_coupon_condition.put(goodsId, list);
								} else {
									ArrayList<TdCoupon> list = send_pro_coupon_condition.get(goodsId);
									if (null == list) {
										list = new ArrayList<>();
									}
									list.add(coupon);
									send_pro_coupon_condition.put(goodsId, list);
								}
							}
						}
					}
				}
			}
			// -----------------------------修改结束--------------------------------

			// 修改：2016-06-26 计算这些商品使用的指定产品现金券的金额，这部分金额是不会退还的
			Map<Long, ArrayList<TdCoupon>> cash__pro_coupon_condition = new HashMap<>();
			String cashCouponId = order.getCashCouponId();
			if (null != cashCouponId && !"".equalsIgnoreCase(cashCouponId)) {
				String[] sCashIds = cashCouponId.split(",");
				if (null != sCashIds && sCashIds.length > 0) {
					for (String sId : sCashIds) {
						if (null != sId && !"".equalsIgnoreCase(sId)) {
							Long id = Long.parseLong(sId);
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon && null != coupon.getTypeCategoryId()
									&& 2L == coupon.getTypeCategoryId().longValue()) {
								Long goodsId = coupon.getGoodsId();
								ArrayList<TdCoupon> cash_coupon = cash__pro_coupon_condition.get(goodsId);
								if (null == cash_coupon) {
									cash_coupon = new ArrayList<>();
								}
								cash_coupon.add(coupon);
								cash__pro_coupon_condition.put(goodsId, cash_coupon);
							}
						}
					}
				}
			}
			// -----------------------------修改结束-----------------------------------

			// 开始拆分退货参数
			String[] param = params.split(",");
			if (null != param && param.length > 0) {
				for (String group : param) {
					if (null != group && !"".equals(group)) {
						String[] singles = group.split("-");
						// 判断singles是否为一个正确的参数
						if (null != singles && singles.length == 3) {
							String sGoodsId = singles[0];
							Long goodsId = null;
							String sNumber = singles[1];
							Long number = 0L;
							String sUnit = singles[2];
							Double unit = 0.00;
							if (null != sGoodsId && !"".equals(sGoodsId)) {
								goodsId = Long.parseLong(sGoodsId);
							}
							if (null != sNumber && !"".equals(sNumber)) {
								number = Long.parseLong(sNumber);
							}
							if (null != sUnit && !"".equals(sUnit)) {
								unit = Double.parseDouble(sUnit);
							}

							// 计算该商品的退货总额
							Double total = number * unit;
							if (null != goodsId) {
								TdGoods goods = tdGoodsService.findOne(goodsId);

								// 2016-06-24修改：需要排除是退货部分使用的指定产品现金券的价格
								Double sub_coupon_price = 0.00;
								ArrayList<TdCoupon> coupon_list = cash__pro_coupon_condition.get(goodsId);
								List<TdCoupon> delete_coupon = new ArrayList<>();
								if (null != coupon_list) {
									for (int i = 0; i < number; i++) {
										if (coupon_list.size() > i) {
											TdCoupon tdCoupon = coupon_list.get(i);
											Double realPrice = tdCoupon.getRealPrice();
											sub_coupon_price += realPrice;
											delete_coupon.add(tdCoupon);
										}
									}

									for (TdCoupon tdCoupon : delete_coupon) {
										coupon_list.remove(tdCoupon);
									}
									delete_coupon = null;
									cash__pro_coupon_condition.put(goodsId, coupon_list);
								}

								// total -= sub_coupon_price;
								Double record = price_difference.get(goods.getCode());
								if (null == record) {
									record = 0.00;
								}
								record += sub_coupon_price;
								if (record > 0) {
									price_difference.put(goods.getCode(), record);
								}
								// --------------------修改结束-----------------------

								// 开始退还产品券
								if (total > 0 || (null != result.get("pro" + goodsId)
										&& (Integer) result.get("pro" + goodsId) > 0)) {
									if (useProCoupon) {
										// 查找本产品是否使用了产品券
										Integer useNumber = (Integer) result.get("pro" + goodsId);
										if (null != useNumber && useNumber > 0) {
											// 开始计算退还几张券
											for (int i = 0; i < useNumber; i++) {
												if (number > 0) {
													TdCoupon proCoupon = new TdCoupon();
													proCoupon.setTypeId(3L);
													proCoupon.setTypeCategoryId(3L);
													if (null != goods) {
														proCoupon.setBrandId(goods.getBrandId());
														proCoupon.setBrandTitle(goods.getBrandTitle());
													}
													proCoupon.setPicUri(goods.getCoverImageUri());
													proCoupon.setGoodsId(goods.getId());
													proCoupon.setPrice(0.0);
													proCoupon.setTypeTitle("退货返还的优惠券");
													proCoupon.setGoodsName(goods.getTitle());
													proCoupon.setIsDistributted(true);
													if (null != city) {
														proCoupon.setCityName(city.getCityName());
														proCoupon.setCityId(city.getId());
													}
													proCoupon.setGetTime(new Date());
													proCoupon.setAddTime(new Date());
													proCoupon.setGetNumber(1L);
													proCoupon.setExpireTime(endTime);
													proCoupon.setUsername(order.getUsername());
													proCoupon.setIsUsed(false);
													proCoupon.setIsOutDate(false);
													proCoupon.setMobile(order.getUsername());
													proCoupon.setSku(goods.getCode());
													// add MDJ
													proCoupon.setOrderId(order.getId());
													proCoupon.setOrderNumber(order.getOrderNumber());
													// add end

													// 在此判断是返回送的券还是返回买的券，优先返还送的券
													ArrayList<TdCoupon> buy_list = buy_pro_coupon_condition
															.get(goodsId);
													ArrayList<TdCoupon> send_list = send_pro_coupon_condition
															.get(goodsId);

													if (null != send_list && send_list.size() > 0) {
														TdCoupon tdCoupon = send_list.get(0);
														proCoupon.setIsBuy(false);
														proCoupon.setRealPrice(tdCoupon.getRealPrice());
														proCoupon.setBuyPrice(0.00);
														send_list.remove(tdCoupon);
														send_pro_coupon_condition.put(goodsId, send_list);
													} else if (null != buy_list && buy_list.size() > 0) {
														TdCoupon tdCoupon = buy_list.get(0);
														proCoupon.setIsBuy(true);
														proCoupon.setRealPrice(tdCoupon.getRealPrice());
														proCoupon.setBuyPrice(tdCoupon.getBuyPrice());
														buy_list.remove(tdCoupon);
														buy_pro_coupon_condition.put(goodsId, buy_list);
													}
													returnCouponList.add(proCoupon);
													total -= unit;
													number--;
													result.put("pro" + goodsId,
															((Integer) result.get("pro" + goodsId) - 1));
												}
											}
										}
									}
								}
								// 开始退还通用现金券
								if (total > 0) {
									if (useCashCoupon) {
										// 声明一个变量用来表示退还的通用现金券的面额
										Double cashPrice = 0.00;
										if (cashTotal > total) {
											cashPrice = total;
										} else {
											cashPrice = cashTotal;
										}
										if (cashPrice > 0) {
											TdCoupon cashCoupon = new TdCoupon();
											cashCoupon.setTypeId(3L);
											cashCoupon.setTypeCategoryId(1L);
											if (null != goods) {
												cashCoupon.setBrandId(goods.getBrandId());
												cashCoupon.setBrandTitle(goods.getBrandTitle());
											}
											cashCoupon.setPicUri(goods.getCoverImageUri());
											cashCoupon.setGoodsName(goods.getTitle());
											cashCoupon.setPrice(cashPrice);
											cashCoupon.setTypeTitle("退货返还的优惠券");
											cashCoupon.setGetNumber(1L);
											if (null != city) {
												cashCoupon.setCityName(city.getCityName());
												cashCoupon.setCityId(city.getId());
											}
											cashCoupon.setAddTime(new Date());
											cashCoupon.setIsDistributted(true);
											cashCoupon.setGetTime(new Date());
											cashCoupon.setExpireTime(endTime);
											cashCoupon.setUsername(order.getUsername());
											cashCoupon.setIsUsed(false);
											cashCoupon.setIsOutDate(false);
											cashCoupon.setMobile(order.getUsername());
											// add MDJ
											cashCoupon.setOrderId(order.getId());
											cashCoupon.setOrderNumber(order.getOrderNumber());
											// add end
											// tdCouponService.save(cashCoupon);
											returnCouponList.add(cashCoupon);
											total -= cashPrice;
											result.put("cashTotal", cashTotal - cashPrice);
										}
									}
								}
								// 如果还有金额没有退还，再退还不可提现余额
								if (total > 0) {
									// 定义实际退还的不可提现余额
									Double uncashBalance = 0.00;

									if (null == unCashBalanceUsed) {
										unCashBalanceUsed = 0.00;
									}

									if (total < unCashBalanceUsed) {
										// 需要退还的金额小于用户使用的总的不可提现余额，则直接退还退还的总额
										uncashBalance = total;
									} else {
										// 如果需要退还的金额大于等于用户使用的不可提现余额，则只能够退还用户使用的不可提现余额
										uncashBalance = unCashBalanceUsed;
									}

									// 判断是否剩余部分金额需要退还
									// BigDecimal bd = new
									// BigDecimal(uncashBalance);
									// uncashBalance = bd.setScale(2,
									// BigDecimal.ROUND_HALF_UP).doubleValue();
									returnBalance += uncashBalance;
									total -= uncashBalance;
									unCashBalanceUsed -= uncashBalance;
								}

								// 如果还有剩余的金额没有退还，则开始退还可提现余额
								if (total > 0) {
									if (null == cashBalanceUsed) {
										cashBalanceUsed = 0.00;
									}
									// 定义一个变量用于获取用户使用的可提现余额
									Double cashBalance = 0.00;
									// 如果需要退还的金额小于用户使用的可提现余额，则将所有的金额以可提现余额的形式退还
									if (total < cashBalanceUsed) {
										cashBalance = total;
									} else {
										cashBalance = cashBalanceUsed;
									}

									// 开始退还余额
									// BigDecimal bd = new
									// BigDecimal(cashBalance);
									// cashBalance = bd.setScale(2,
									// BigDecimal.ROUND_HALF_UP).doubleValue();
									returnBalance += cashBalance;
									total -= cashBalance;
									cashBalanceUsed -= cashBalance;
								}
								// 如果还剩余部分金额没有退还，则需要根据支付方式进行第三方退还
								if (total > 0) {

									// 判断用户是否使用了第三方支付
									if (null != otherPay && otherPay > 0.00) {
										// 获取用户的支付方式
										Long payTypeId = order.getPayTypeId();
										TdPayType payType = tdPayTypeService.findOne(payTypeId);
										// 定义一个变量表示退款金额数
										Double otherReturn = 0.00;
										if (total < otherPay) {
											otherReturn = total;
										} else {
											otherReturn = otherPay;
										}

										if (otherReturn > 0.00) {
											// 根据退款方式和退货金额生成一个资金退还申请单据
											BigDecimal bd = new BigDecimal(otherReturn);
											otherReturn = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
											TdCashReturnNote note = new TdCashReturnNote();
											note.setCreateTime(new Date());
											note.setMoney(otherReturn);
											// 如果支付方式属于线上支付，那么一定是支付宝、微信、银行卡的一种，则按照正常逻辑处理
											// 当订单价格为0 支付方式为其他 payType值为null
											note.setTypeId(payTypeId);
											note.setTypeTitle(payType.getTitle());
											note.setOrderNumber(order.getOrderNumber());
											note.setMainOrderNumber(order.getMainOrderNumber());
											note.setReturnNoteNumber(returnNote.getReturnNumber());
											note.setUserId(user.getId());
											note.setUsername(user.getUsername());
											note.setIsOperated(false);
											// note =
											// tdCashReturnNoteService.save(note);

											otherPay -= otherReturn;
										}
										// ----------在此处理退款申请单的一系列操作动作-------------------

									} else {
										if (null == cashPay) {
											cashPay = 0.00;
										}

										Double cashReturn = 0.00;

										if (total < cashPay) {
											cashReturn = total;
										} else {
											cashReturn = cashPay;
										}

										if (cashReturn > 0.00) {
											// 根据退款方式和退货金额生成一个资金退还申请单据
											BigDecimal bd = new BigDecimal(cashReturn);
											cashReturn = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
											TdCashReturnNote note = new TdCashReturnNote();
											note.setCreateTime(new Date());
											note.setMoney(cashReturn);
											note.setTypeId(-1L);
											note.setTypeTitle("现金");
											note.setOrderNumber(order.getOrderNumber());
											note.setMainOrderNumber(order.getMainOrderNumber());
											note.setReturnNoteNumber(returnNote.getReturnNumber());
											note.setUserId(user.getId());
											note.setUsername(user.getUsername());
											note.setIsOperated(false);
											// note =
											// tdCashReturnNoteService.save(note);

											total -= cashReturn;
											cashPay -= cashReturn;
										}
										// 退还现金后还剩余未退还的金额，就退还POS
										if (total > 0) {
											Double posReturn = 0.00;

											if (total < posPay) {
												posReturn = total;
											} else {
												posReturn = posPay;
											}

											if (posReturn > 0.00) {
												// 根据退款方式和退货金额生成一个资金退还申请单据
												BigDecimal bd = new BigDecimal(posReturn);
												posReturn = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
												TdCashReturnNote note01 = new TdCashReturnNote();
												note01.setCreateTime(new Date());
												note01.setMoney(posReturn);
												// 如果支付方式属于线上支付，那么一定是支付宝、微信、银行卡的一种，则按照正常逻辑处理
												// 当订单价格为0 支付方式为其他 payType值为null
												note01.setTypeId(-2L);
												note01.setTypeTitle("POS");
												note01.setOrderNumber(order.getOrderNumber());
												note01.setMainOrderNumber(order.getMainOrderNumber());
												note01.setReturnNoteNumber(returnNote.getReturnNumber());
												note01.setUserId(user.getId());
												note01.setUsername(user.getUsername());
												note01.setIsOperated(false);
												// note01 =
												// tdCashReturnNoteService.save(note01);

												total -= posReturn;
												posPay -= posReturn;
											}
										}
										// 如果还有未退还的金额，则退还其他支付方式支付的金额
										if (total > 0) {
											if (null == backOtherPay) {
												backOtherPay = 0.00;
											}

											Double otherReturn = 0.00;

											if (total < backOtherPay) {
												otherReturn = total;
											} else {
												otherReturn = backOtherPay;
											}

											if (otherReturn > 0.00) {
												// 根据退款方式和退货金额生成一个资金退还申请单据
												TdCashReturnNote note = new TdCashReturnNote();
												note.setCreateTime(new Date());
												note.setMoney(otherReturn);
												note.setTypeId(-3L);
												note.setTypeTitle("其他");
												note.setOrderNumber(order.getOrderNumber());
												note.setMainOrderNumber(order.getMainOrderNumber());
												note.setReturnNoteNumber(returnNote.getReturnNumber());
												note.setUserId(user.getId());
												note.setUsername(user.getUsername());
												note.setIsOperated(false);

												// 2016-07-05修改：不做持久化操作
												// note =
												// tdCashReturnNoteService.save(note);
												// new_return_note.setMoney(new_return_note.getMoney()
												// + otherReturn);
												// 修改结束

												total -= otherReturn;
												backOtherPay -= otherReturn;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (returnCouponList != null && returnCouponList.size() > 0) {
			resultMap.put(INFConstants.kCouponList, returnCouponList);
		}
		if (returnBalance > 0) {
			resultMap.put(INFConstants.kBalance, returnBalance);
		}
		if (price_difference.size() > 0) {
			resultMap.put(INFConstants.kPrcieDif, price_difference);
		}

		return resultMap;
	}

	public void saveCashReceiptAndSendToEBS(TdRecharge tdRecharge, TdUser tdUser) {
		if (tdRecharge == null || tdUser == null) {
			return;
		}
		TdCashReciptInf cashReciptInf = tdInterfaceService.initCashReciptByReCharge(tdRecharge, tdUser);
		String resultStr = tdInterfaceService.ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
		if (StringUtils.isBlank(resultStr)) {
			cashReciptInf.setSendFlag(0);
		} else {
			cashReciptInf.setSendFlag(1);
			cashReciptInf.setErrorMsg(resultStr);
		}
		tdCashReciptInfService.save(cashReciptInf);
	}

	/**
	 * 修改订单商品的价格为最新价格
	 */
	public void changeOrderToNewPrice(HttpServletRequest req, TdOrder order) {

		List<TdOrderGoods> orderGoods = order.getOrderGoodsList();

		if (null != orderGoods && orderGoods.size() > 0) {// 检查非空
			for (TdOrderGoods tdOrderGood : orderGoods) {
				TdGoods good = tdGoodsService.findOne(tdOrderGood.getGoodsId());
				// 获取指定商品的价目表项
				TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, good);
				if (null != priceListItem) {// 检查非空
					// 修改订单商品中的价格为最新价格
					tdOrderGood.setPrice(priceListItem.getSalePrice());
					tdOrderGood.setRealPrice(priceListItem.getRealSalePrice());
					tdOrderGoodsService.save(tdOrderGood);
				}
			}
		}
	}

	public Double getGoodsPrice(Long sobId, TdOrderGoods goods) {

		if (null == goods) {
			return 0d;
		}

		String productFlag = goods.getBrandTitle();

		if (null == productFlag) {
			return 0d;
		}

		String priceType = null;

		// 零售价
		if (productFlag.equalsIgnoreCase("华润")) {
			priceType = "LS";
		}
		// 乐意装价
		else if (productFlag.equalsIgnoreCase("乐易装")) {
			priceType = "LYZ";
		}
		// 莹润价
		else if (productFlag.equalsIgnoreCase("莹润")) {
			priceType = "YR";
		}
		// 不支持的价格
		else {
			return 0d;
		}

		List<TdPriceList> priceList_list = tdPriceListService
				.findBySobIdAndPriceTypeAndStartDateActiveAndEndDateActive(sobId, priceType, new Date(), new Date());

		if (null == priceList_list || priceList_list.size() == 0 || priceList_list.size() > 1) {
			return 0d;
		}

		// 价目表ID
		Long list_header_id = 0L;
		list_header_id = priceList_list.get(0).getListHeaderId();

		List<TdPriceListItem> priceItemList = tdPriceListItemService
				.findValidItemByPriceListHeaderIdAndGoodsCode(list_header_id, goods.getSku());

		if (null == priceItemList || priceItemList.size() == 0 || priceItemList.size() > 1) {
			return 0d;
		}

		return priceItemList.get(0).getPrice();
	}
}
