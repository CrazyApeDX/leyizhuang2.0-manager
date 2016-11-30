package com.ynyes.lyz.controller.front;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.DecimalFormat;
import com.ynyes.lyz.entity.TdCashReturnNote;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.entity.untable.CouponInfo;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;
import com.ynyes.lyz.interfaces.service.TdCashRefundInfService;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;
import com.ynyes.lyz.service.TdCashReturnNoteService;
import com.ynyes.lyz.service.TdCouponService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdOrderGoodsService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.TdReturnNoteService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdUserService;

/**
 * <p>
 * 标题：TdCouponReturnController.java
 * </p>
 * <p>
 * 描述：退券相关控制器
 * </p>
 * 
 * @author 作者：CrazyDX
 * @version 版本：2016年8月30日上午10:27:00
 */
@Controller
@RequestMapping(value = "/coupon/return")
public class TdCouponReturnController {

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdCouponService tdCouponService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Autowired
	private TdPriceCountService tdPriceCountService;

	@Autowired
	private TdReturnNoteService tdReturnNoteService;

	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdCashReturnNoteService tdCashReturnNoteService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdCashRefundInfService tdCashRefundInfService;

	@RequestMapping
	public String couponReturn(HttpServletRequest req, ModelMap map, Long orderId) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/login";
		}

		TdOrder order = tdOrderService.findOne(orderId);
		Map<Long, Long> group = this.getGroup(order);
		if (null != order) {
			// List<TdCoupon> coupon_list =
			// tdCouponService.findByCouponOrderNumberAndIsUsedFalseAndIsOutDateFalse(orderNumber);
			List<CouponInfo> couponOrderInfo = this.getCouponOrderInfo(group, order);
			this.getReturnUnit(couponOrderInfo);
			// List<CouponInfo> couponOrderInfo =
			// returnCouponService.getCouponInfo(order);
			map.addAttribute("couponInfo_list", couponOrderInfo);
		}

		map.addAttribute("order", order);

		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("telphone", setting.getTelephone());

		// 查找所有的退货原因
		// List<TdReturnReason> reason_list = tdReturnReasonService.findAll();
		// map.addAttribute("reason_list", reason_list);
		return "/client/coupon_return";
	}

	/**
	 * <p>
	 * 描述：整理优惠券集合，获取商品ID-数量-总价的综合信息
	 * </p>
	 * 
	 * @author 作者：CrazyDX
	 * @version 版本：2016年8月30日上午11:03:27
	 */
	private List<CouponInfo> getCouponOrderInfo(Map<Long, Long> group, TdOrder order) {
		List<CouponInfo> list = new ArrayList<>();

		Map<Long, Double> subMap = this.getSubTotal(order);

		for (Long goodsId : group.keySet()) {

			Integer leftCount = tdCouponService.findCountByCouponOrderNumberAndIsUsedFalseAndIsOutDateFalseAndGoodsId(
					order.getOrderNumber(), goodsId);

			List<TdCoupon> couponList = tdCouponService.findByCouponOrderNumberAndGoodsId(order.getOrderNumber(),
					goodsId);

			Long quantity = group.get(goodsId);
			CouponInfo couponInf = new CouponInfo();
			couponInf.setGoodsId(goodsId);
			couponInf.setSku(couponList.get(0).getSku());
			couponInf.setQuantity(new Long(leftCount));
			couponInf.setTradeQuantity(quantity);
			couponInf.setTradePrice(couponList.get(0).getTradePrice());
			couponInf.setReturnUnit(0d);
			couponInf.setGoodsTitle(couponList.get(0).getGoodsName());
			couponInf.setGoodsCoverImageUri(couponList.get(0).getPicUri());
			couponInf.setPrice(couponList.get(0).getBuyPrice());

			Double totalPrice = 0d;
			List<TdOrderGoods> orderGoodsList = tdOrderService.findByOrderNumber(order.getOrderNumber()).getOrderGoodsList();
			for (TdOrderGoods tdOrderGoods : orderGoodsList) {
				if (tdOrderGoods.getGoodsId().equals(goodsId)) {
					totalPrice += tdOrderGoods.getRealPrice() * tdOrderGoods.getQuantity();
				}
			}

			couponInf.setTotalPrice(totalPrice);
			couponInf.setTradeTotal(couponInf.getTradePrice() * couponInf.getTradeQuantity());

			Double sub = subMap.get(goodsId);
			if (null != sub) {
				couponInf.setTradeTotal(couponInf.getTradeTotal() - sub);
			}

			list.add(couponInf);
		}

		return list;
	}

	private List<CouponInfo> getReturnUnit(List<CouponInfo> couponOrderInfo) {
		// 创建一个变量用于表示订单总价格
		Double orderTotalPrice = 0d;
		// 创建一个变量用于表示订单价值
		Double orderTradeTotal = 0d;

		DecimalFormat df = new DecimalFormat("#.00");

		// 第一次循环：获取订单总价和订单实际价值
		for (CouponInfo couponInfo : couponOrderInfo) {
			Double tradeTotal = couponInfo.getTradeTotal();
			Double totalPrice = couponInfo.getTotalPrice();

			orderTotalPrice += totalPrice;
			orderTradeTotal += tradeTotal;
		}

		// 计算比例
		// Double point = orderTotalPrice / orderTradeTotal;

		// 第二次循环：获取退货单价
		for (CouponInfo couponInfo : couponOrderInfo) {
			Double tradeTotal = couponInfo.getTradeTotal();
			Double point;
			if (orderTradeTotal.equals(0d)) {
				point = 0d;
			} else {
				point = tradeTotal / orderTradeTotal;
			}
			Double realTotal = orderTotalPrice * point;
			String format = df.format(realTotal / couponInfo.getTradeQuantity());
			couponInfo.setReturnUnit(Double.parseDouble(format));
			// Double tradePrice = couponInfo.getTradePrice() *
			// couponInfo.getQuantity();
			// Double totalPrice = tradePrice * point;
			// String format = df.format(totalPrice);
			// totalPrice = Double.parseDouble(format);
			// Double unit = totalPrice / couponInfo.getQuantity();
			// couponInfo.setReturnUnit(unit);
		}

		return couponOrderInfo;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> send(HttpServletRequest req, ModelMap map, Long orderId, String infos, Long turnType,
			String remark) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		// 根据订单号查找订单
		TdOrder order = tdOrderService.findOne(orderId);
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			res.put("message", "未查询到登录用户信息");
			return res;
		}
		// 判断订单是否已经退货
		if (null != order && !(null != order.getIsRefund() && order.getIsRefund())) {
			TdReturnNote returnNote = new TdReturnNote();
			// 退货单编号
			Date current = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			String curStr = sdf.format(current);
			Random random = new Random();

			returnNote.setReturnNumber("T" + curStr + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
			returnNote.setIsCoupon(true);

			// 添加订单信息
			returnNote.setOrderNumber(order.getOrderNumber());

			// add MDJ
			returnNote.setShoppingAddress(order.getShippingAddress());
			returnNote.setSellerRealName(order.getSellerRealName());
			// end add MDJ

			// 支付方式
			returnNote.setPayTypeId(order.getPayTypeId());
			returnNote.setPayTypeTitle(order.getPayTypeTitle());
			// 门店信息
			if (null != order.getDiySiteId()) {
				TdDiySite diySite = tdDiySiteService.findOne(order.getDiySiteId());
				returnNote.setDiySiteId(order.getDiySiteId());
				returnNote.setDiyCode(diySite.getStoreCode());
				returnNote.setDiySiteTel(diySite.getServiceTele());
				returnNote.setDiySiteTitle(diySite.getTitle());
				returnNote.setDiySiteAddress(diySite.getAddress());
			}

			// 退货信息
			returnNote.setUsername(order.getUsername());
			returnNote.setRemarkInfo(remark);

			// 退货方式
			if ("门店自提".equals(order.getDeliverTypeTitle())) {
				returnNote.setTurnType(1L);
				turnType = 1L;
			} else {
				returnNote.setTurnType(2L);
				turnType = 2L;
			}
			// returnNote.setTurnType(turnType);
			// 原订单配送方式
			if ("门店自提".equals(order.getDeliverTypeTitle())) {
				returnNote.setStatusId(2L); // 门店自提单-门店到店退货 待验货
			} else {
				returnNote.setStatusId(1L); // 送货上门单 物流取货 待取货
			}

			returnNote.setDeliverTypeTitle(order.getDeliverTypeTitle());
			returnNote.setOrderTime(new Date());

			Double totalGoodsPrice = 0.00;
			List<TdOrderGoods> orderGoodsList = new ArrayList<>();
			// 开始解析infos字符串
			if (null != infos) {
				String[] singles = infos.split(",");
				if (null != singles && singles.length > 0) {
					for (String single : singles) {
						if (null != single && !"".equals(single)) {
							String[] param = single.split("-");
							if (null != param && param.length == 4) {
								String sId = param[0];
								Long goodsId = null;
								String sQuantity = param[1];
								Long quantity = 0L;
								String sUnit = param[2];
								Double unit = 0.00;
								String sPrice = param[3];
								Double price = 0.00;

								if (null != sQuantity && !"".equals(sQuantity)) {
									quantity = Long.parseLong(sQuantity);
								}
								if (null != sUnit && !"".equals(sUnit)) {
									unit = Double.parseDouble(sUnit);
								}
								if (null != sPrice && !"".equals(sPrice)) {
									price = Double.parseDouble(sPrice);
								}
								if (null != sId) {
									goodsId = Long.parseLong(sId);
									TdGoods oGoods = tdGoodsService.findOne(goodsId);
									if (null != oGoods) {
										TdOrderGoods orderGoods = new TdOrderGoods();
										orderGoods.setBrandId(oGoods.getBrandId());
										orderGoods.setBrandTitle(oGoods.getBrandTitle());
										orderGoods.setGoodsId(oGoods.getId());
										orderGoods.setGoodsSubTitle(oGoods.getSubTitle());
										orderGoods.setSku(oGoods.getCode());
										orderGoods.setGoodsCoverImageUri(oGoods.getCoverImageUri());
										orderGoods.setGoodsTitle(oGoods.getTitle());
										orderGoods.setPrice(price);
										orderGoods.setQuantity(quantity);
										orderGoods.setReturnNoteNumber(returnNote.getReturnNumber());
										orderGoods.setSubOrderNumber(order.getOrderNumber());
										orderGoods.setReturnUnitPrice(unit);
										// orderGoods.setDeliveredQuantity(oGoods.getDeliveredQuantity());
										// orderGoods.setPoints(oGoods.getPoints());
										// tdOrderGoodsService.save(orderGoods);
										// 添加商品信息
										orderGoodsList.add(orderGoods);

										// 订单商品设置退货为True
										// oGoods.setIsReturnApplied(true);
										// 更新订单商品信息是否退货状态
										tdOrderGoodsService.save(orderGoods);
										totalGoodsPrice += (unit * quantity);
									}
								}
							}
						}
					}
				}
			}

			// 获取订单的总价
			Double main_order_goods_price = order.getTotalGoodsPrice();
			if (null == main_order_goods_price) {
				main_order_goods_price = 0.00;
			}

			if (totalGoodsPrice > main_order_goods_price) {
				totalGoodsPrice = main_order_goods_price;
			}

			returnNote.setTurnPrice(totalGoodsPrice);
			returnNote.setReturnGoodsList(orderGoodsList);
			order.setStatusId(9L);
			order.setIsRefund(true);

			returnNote = this.getReturnDetail(orderId, infos, returnNote);

			tdOrderService.save(order);
			tdReturnNoteService.save(returnNote);

			// 开始退钱退券，通知EBS
			List<TdCashReturnNote> cashReturnNotes = tdPriceCountService.actAccordingWMS(returnNote, order.getId());
			order.setStatusId(12L);
			returnNote.setReturnTime(new Date());
			tdOrderService.save(order);
			if (cashReturnNotes != null && cashReturnNotes.size() > 0) {
				for (TdCashReturnNote tdCashReturnNote : cashReturnNotes) {
					TdOrder tdOrder = tdOrderService.findByOrderNumber(tdCashReturnNote.getOrderNumber());
					TdCashRefundInf cashRefundInf = new TdCashRefundInf();
					cashRefundInf.setSobId(user.getCityId());
					cashRefundInf.setRefundNumber(returnNote.getReturnNumber());
					cashRefundInf.setUserid(tdCashReturnNote.getUserId());
					cashRefundInf.setUsername(tdCashReturnNote.getUsername());
					cashRefundInf.setUserphone(tdCashReturnNote.getUsername());
					cashRefundInf.setDiySiteCode(tdOrder.getDiySiteCode());
					cashRefundInf.setRefundClass("电子券");
					cashRefundInf.setRtHeaderId(returnNote.getId());
					cashRefundInf.setReturnNumber(returnNote.getReturnNumber());
					cashRefundInf.setOrderHeaderId(tdOrder.getId());
					String type = "";
					if (order.getOrderNumber().contains("HR")) {
						type = "HR";
					} else if (order.getOrderNumber().contains("YR")) {
						type = "YR";
					} else if (order.getOrderNumber().contains("LYZ")) {
						type = "LYZ";
					}
					cashRefundInf.setProductType(type);
					cashRefundInf.setRefundType(tdCashReturnNote.getTypeTitle());
					cashRefundInf.setRefundDate(new Date());
					cashRefundInf.setAmount(tdCashReturnNote.getMoney());
					cashRefundInf.setDescription("电子券退款");
					tdCashRefundInfService.save(cashRefundInf);
					tdInterfaceService.ebsWithObject(cashRefundInf, INFTYPE.CASHREFUNDINF);
				}
				for (int index = 0; index < cashReturnNotes.size(); index++) {
					TdCashReturnNote cashReturnNote = cashReturnNotes.get(index);
					if (cashReturnNote != null && cashReturnNote.getMoney() == null
							|| (cashReturnNote.getMoney() != null && cashReturnNote.getMoney() == 0d)) {
						tdCashReturnNoteService.delete(cashReturnNote);
					}
				}
			}
			// 删除相关优惠券
			for (TdOrderGoods orderGoods : orderGoodsList) {
				if (null != orderGoods) {
					Long quantity = orderGoods.getQuantity();
					Long goodsId = orderGoods.getGoodsId();
					List<TdCoupon> couponList = tdCouponService
							.findByCouponOrderNumberAndIsUsedFalseAndIsOutDateFalseAndGoodsId(
									returnNote.getOrderNumber(), goodsId);
					if (couponList.size() >= quantity) {
						for (int i = 0; i < quantity; i++) {
							tdCouponService.delete(couponList.get(i));
						}
					}
				}
			}
			returnNote.setReturnTime(new Date());
			returnNote.setStatusId(5L);// 退货单设置已完成
			tdReturnNoteService.save(returnNote);
		}

		res.put("status", 0);
		return res;
	}

	private TdReturnNote getReturnDetail(Long orderId, String params, TdReturnNote returnNote) {
		StringBuffer sb = new StringBuffer("");

		TdOrder order = tdOrderService.findOne(orderId);
		if (null != order && null != params && !"".equals(params)) {
			Map<String, Object> result = tdPriceCountService.countCouponCondition(orderId);

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
			// 获取用户其他支付
			Double backOtherPay = order.getBackOtherPay();
			if (null == cashPay) {
				cashPay = 0.00;
			}
			// 获取用户支付的POS
			Double posPay = order.getPosPay();
			if (null == posPay) {
				posPay = 0.00;
			}
			if (null == backOtherPay) {
				backOtherPay = 0.00;
			}

			Double all_off_line = posPay + cashPay + backOtherPay;

			// 2016-07-05修改：以现金的方式归还第三方支付的钱，POS和现金还有其他
			Double all_cash_return = 0.00;

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
						if (null != singles && singles.length == 4) {
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
								if (total > 0 || (null != result.get("pro" + goodsId)
										&& (Integer) result.get("pro" + goodsId) > 0)) {
									if (useProCoupon) {
										// 查找本产品是否使用了产品券
										Integer useNumber = (Integer) result.get("pro" + goodsId);
										if (null != useNumber && useNumber > 0) {
											// 开始计算退还几张券
											for (int i = 0; i < useNumber; i++) {
												if (number > 0) {
													sb.append(goods.getTitle() + "【产品券】*1");
													result.put("pro" + goodsId,
															((Integer) result.get("pro" + goodsId) - 1));
													number--;
													total -= unit;
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
											BigDecimal bd = new BigDecimal(cashPrice);
											cashPrice = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
											sb.append(cashPrice + "元【通用现金券】*1");
										}
										total -= cashPrice;
										cashTotal -= cashPrice;
										result.put("cashTotal", cashTotal - cashPrice);

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
									if (uncashBalance > 0) {
										BigDecimal bd = new BigDecimal(uncashBalance);
										uncashBalance = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
										sb.append(uncashBalance + "元【不可提现预存款】");
									}
									// // 开始退还不可提现余额
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
									if (cashBalance > 0) {
										BigDecimal bd = new BigDecimal(cashBalance);
										cashBalance = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
										sb.append(cashBalance + "元【可提现预存款】");
									}
									total -= cashBalance;
									cashBalanceUsed -= cashBalance;
								}
								// 如果还剩余部分金额没有退还，则需要根据支付方式进行第三方退还
								if (total > 0) {

									// 判断用户是否使用了第三方支付
									if (null != otherPay && otherPay > 0.00) {
										// 获取用户的支付方式
										// String payTypeTitle =
										// order.getPayTypeTitle();
										// 定义一个变量表示退款金额数
										Double otherReturn = 0.00;
										if (total < otherPay) {
											otherReturn = total;
										} else {
											otherReturn = otherPay;
										}

										if (otherReturn > 0.00) {
											all_cash_return += otherReturn;
											// infos.add(otherReturn + "元【" +
											// payTypeTitle + "】");
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
											all_cash_return += cashReturn;
											// infos.add(cashReturn + "元【现金】");
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
												all_cash_return += posReturn;
												// infos.add(posReturn +
												// "元【POS退还】");
												posPay -= posReturn;
												total -= posReturn;
											}
										}
										// 如果还有没退还的金额，则退还其他方式的支付金额
										if (total > 0) {
											Double otherReturn = 0.00;

											if (total < backOtherPay) {
												otherReturn = total;
											} else {
												otherReturn = backOtherPay;
											}

											if (otherReturn > 0.00) {
												all_cash_return += otherReturn;
												// infos.add(posReturn +
												// "元【POS退还】");
												backOtherPay -= otherReturn;
												total -= otherReturn;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			if (all_cash_return > 0) {
				if (all_cash_return > all_off_line) {
					all_cash_return = all_off_line;
				}
				BigDecimal bd = new BigDecimal(all_cash_return);
				all_cash_return = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				sb.append(all_cash_return + "元【现金】");
			}
		}
		returnNote.setReturnDetail(sb.toString());
		return returnNote;
	}

	private Map<Long, Long> getGroup(TdOrder order) {
		Map<Long, Long> map = new HashMap<>();

		List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
		for (TdOrderGoods orderGoods : orderGoodsList) {
			Long goodsId = orderGoods.getGoodsId();
			Long quantity = orderGoods.getQuantity();
			Long getQuantity = map.get(goodsId);
			if (null == getQuantity) {
				getQuantity = 0l;
			}
			getQuantity += quantity;
			map.put(goodsId, getQuantity);
		}
		List<TdOrderGoods> presentList = order.getPresentedList();
		for (TdOrderGoods orderGoods : presentList) {
			Long goodsId = orderGoods.getGoodsId();
			Long quantity = orderGoods.getQuantity();
			Long getQuantity = map.get(goodsId);
			if (null == getQuantity) {
				getQuantity = 0l;
			}
			getQuantity += quantity;
			map.put(goodsId, getQuantity);
		}
		List<TdOrderGoods> giftList = order.getGiftGoodsList();
		for (TdOrderGoods orderGoods : giftList) {
			Long goodsId = orderGoods.getGoodsId();
			Long quantity = orderGoods.getQuantity();
			Long getQuantity = map.get(goodsId);
			if (null == getQuantity) {
				getQuantity = 0l;
			}
			getQuantity += quantity;
			map.put(goodsId, getQuantity);
		}

		return map;
	}

	private Map<Long, Double> getSubTotal(TdOrder order) {
		Map<Long, Double> map = new HashMap<>();

		List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
		for (TdOrderGoods orderGoods : orderGoodsList) {
			Long goodsId = orderGoods.getGoodsId();
			Long cashNumber = orderGoods.getCashNumber() == null ? 0 : orderGoods.getCashNumber();
			Double realPrice = orderGoods.getRealPrice();
			Double price = orderGoods.getPrice();

			map.put(goodsId, (cashNumber * (price - realPrice)));
		}

		return map;
	}
}
