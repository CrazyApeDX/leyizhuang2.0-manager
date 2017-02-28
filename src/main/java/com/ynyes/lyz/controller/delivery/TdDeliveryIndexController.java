package com.ynyes.lyz.controller.delivery;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ynyes.lyz.entity.TdDeliveryInfo;
import com.ynyes.lyz.entity.TdDeliveryInfoDetail;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdGeoInfo;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdOwnMoneyRecord;
import com.ynyes.lyz.entity.TdPayType;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.entity.user.CreditChangeType;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdDeliveryInfoDetailService;
import com.ynyes.lyz.service.TdDeliveryInfoService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGeoInfoService;
import com.ynyes.lyz.service.TdOrderGoodsService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdOwnMoneyRecordService;
import com.ynyes.lyz.service.TdPayTypeService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.TdReturnNoteService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping(value = "/delivery")
public class TdDeliveryIndexController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdOwnMoneyRecordService tdOwnMoneyRecordService;

	@Autowired
	private TdGeoInfoService tdGeoInfoService;

	@Autowired
	private TdDeliveryInfoDetailService tdDeliveryInfoDetailService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Autowired
	private TdReturnNoteService tdReturnNoteService;

	@Autowired
	private TdDeliveryInfoService tdDeliveryInfoService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdPriceCountService tdPriceCountService;

	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	private TdPayTypeService tdPayTypeService;

	@RequestMapping
	public String deliveryIndex(HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		return "/client/delivery_select_type";
	}

	@RequestMapping(value = "/return")
	public String deliveryReturnIndex(String start, String end, Integer days, Integer type, HttpServletRequest req,
			ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null == user) {
			return "redirect:/login";
		}

		if (null == type) {
			type = 1;
		}

		map.addAttribute("type", type);

		if (null == start && null == end && null == days) {
			days = 3;
		}

		Date startDate = null, endDate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		if (null != start) {
			try {
				cal.setTime(sdf.parse(start));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			startDate = cal.getTime();
			map.addAttribute("startDate", startDate);
		}

		if (null != end) {
			try {
				cal.setTime(sdf.parse(end));
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			endDate = cal.getTime();
			map.addAttribute("endDate", endDate);
		}

		if (null == startDate && null == endDate) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);

			// 7天内
			if (days.equals(7)) {
				cal.add(Calendar.DATE, -7);
				startDate = cal.getTime();
			} else {
				cal.add(Calendar.DATE, -3);
				startDate = cal.getTime();
			}

			map.addAttribute("days", days);
		}

		List<TdReturnNote> rnList = new ArrayList<TdReturnNote>();

		// type : 1: 待取货 2: 已取货 3: 已完成
		if (null != startDate && null != user.getOpUser()) {
			if (null != endDate) {
				if (type.equals(1)) {
					rnList = tdReturnNoteService.findByDriverAndStatusIdAndOrderTimeBetween(user.getOpUser(), 2L,
							startDate, endDate);
				} else if (type.equals(2)) {
					rnList = tdReturnNoteService.findByDriverAndStatusIdAndOrderTimeBetween(user.getOpUser(), 3L,
							startDate, endDate);
				} else if (type.equals(3)) {
					rnList = tdReturnNoteService.findByDriverAndStatusIdAndOrderTimeBetween(user.getOpUser(), 4L,
							startDate, endDate);
				}

				map.addAttribute("count_type_1", tdReturnNoteService
						.countByDriverAndStatusIdAndOrderTimeBetween(user.getOpUser(), 2L, startDate, endDate));
				map.addAttribute("count_type_2", tdReturnNoteService
						.countByDriverAndStatusIdAndOrderTimeBetween(user.getOpUser(), 3L, startDate, endDate));
				map.addAttribute("count_type_3", tdReturnNoteService
						.countByDriverAndStatusIdAndOrderTimeBetween(user.getOpUser(), 4L, startDate, endDate));
			} else {
				if (type.equals(1)) {
					rnList = tdReturnNoteService.findByDriverAndStatusIdAndOrderTimeAfter(user.getOpUser(), 2L,
							startDate);
				} else if (type.equals(2)) {
					rnList = tdReturnNoteService.findByDriverAndStatusIdAndOrderTimeAfter(user.getOpUser(), 3L,
							startDate);
				} else if (type.equals(3)) {
					rnList = tdReturnNoteService.findByDriverAndStatusIdAndOrderTimeAfter(user.getOpUser(), 4L,
							startDate);
				}

				map.addAttribute("count_type_1",
						tdReturnNoteService.countByDriverAndStatusIdAndOrderTimeAfter(user.getOpUser(), 2L, startDate));
				map.addAttribute("count_type_2",
						tdReturnNoteService.countByDriverAndStatusIdAndOrderTimeAfter(user.getOpUser(), 3L, startDate));
				map.addAttribute("count_type_3",
						tdReturnNoteService.countByDriverAndStatusIdAndOrderTimeAfter(user.getOpUser(), 4L, startDate));
			}
		}

		map.addAttribute("return_list", rnList);

		return "/client/return_list";
	}

	/**
	 * 获取配送列表
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @param days
	 *            几天内
	 * @param type
	 *            类型
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/order")
	public String deliveryIndex(String start, String end, Integer days, Integer type, HttpServletRequest req,
			ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null == user) {
			return "redirect:/login";
		}

		if (null == type) {
			type = 2;
		}

		map.addAttribute("type", type);

		if (null == start && null == end && null == days) {
			days = 3;
		}

		Date startDate = null, endDate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		if (null != start) {
			try {
				cal.setTime(sdf.parse(start));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			startDate = cal.getTime();
			map.addAttribute("startDate", startDate);
		}

		if (null != end) {
			try {
				cal.setTime(sdf.parse(end));
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			endDate = cal.getTime();
			map.addAttribute("endDate", endDate);
		}

		if (null == startDate && null == endDate) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);

			// 7天内
			if (days.equals(7)) {
				cal.add(Calendar.DATE, -7);
				startDate = cal.getTime();
			} else {
				cal.add(Calendar.DATE, -1);
				startDate = cal.getTime();
			}

			map.addAttribute("days", days);
		}

		// 查看本配送员所有的订单号
		List<String> orderNumberList = queryOrderNumberList(user.getOpUser());

		List<TdOrder> orderList = null;

		if (null != startDate && orderNumberList.size() > 0) {
			if (null != endDate) {
				if (type.equals(1)) {
					orderList = tdOrderService.findByStatusIdAndDeliveryTimeBetweenOrStatusIdAndOrderTimeBetween(5L, 6L,
							orderNumberList, startDate, endDate);
				} else if (type.equals(2)) {
					orderList = tdOrderService.findByStatusIdAndDeliveryTimeBetween(4L, orderNumberList, startDate,
							endDate);
				} else if (type.equals(3)) {
					orderList = tdOrderService.findByStatusIdAndDeliveryTimeBetween(3L, orderNumberList, startDate,
							endDate);
				}

				map.addAttribute("count_type_1",
						tdOrderService.countByStatusIdAndDeliveryTimeBetweenOrStatusIdAndOrderTimeBetween(5L, 6L,
								orderNumberList, startDate, endDate));
				map.addAttribute("count_type_2",
						tdOrderService.countByStatusIdAndDeliveryTimeBetween(4L, orderNumberList, startDate, endDate));
				map.addAttribute("count_type_3",
						tdOrderService.countByStatusIdAndDeliveryTimeBetween(3L, orderNumberList, startDate, endDate));
			} else {
				if (type.equals(1)) {
					orderList = tdOrderService.findByStatusIdAndDeliveryTimeAfterOrStatusIdAndDeliveryTimeAfter(5L, 6L,
							orderNumberList, startDate);
				} else if (type.equals(2)) {
					orderList = tdOrderService.findByStatusIdAndDeliveryTimeAfter(4L, startDate, orderNumberList);
				} else if (type.equals(3)) {
					orderList = tdOrderService.findByStatusIdAndDeliveryTimeAfter(3L, startDate, orderNumberList);
				}

				map.addAttribute("count_type_1",
						tdOrderService.countByStatusIdAndDeliveryTimeAfterOrStatusIdAndDeliveryTimeAfter(5L, 6L,
								orderNumberList, startDate));
				map.addAttribute("count_type_2",
						tdOrderService.countByStatusIdAndDeliveryTimeAfter(4L, startDate, orderNumberList));
				map.addAttribute("count_type_3",
						tdOrderService.countByStatusIdAndDeliveryTimeAfter(3L, startDate, orderNumberList));
			}
		}

		map.addAttribute("order_list", orderList);

		return "/client/delivery_list";
	}

	/**
	 * 订单详情
	 * 
	 * @param id
	 * @param req
	 * @param map
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable Long id, HttpServletRequest req, ModelMap map, Long msg) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null == user) {
			return "redirect:/login";
		}

		if (null != id) {

			TdOrder order = tdOrderService.findOne(id);

			if (null != order && null != order.getMainOrderNumber()) {
				List<TdOrder> orderList = tdOrderService.findByMainOrderNumberIgnoreCase(order.getMainOrderNumber());
				Double allMoney = 0.00;
				if (null != orderList && orderList.size() > 0) {
					for (TdOrder tdOrder : orderList) {
						allMoney += tdOrder.getTotalPrice();
					}
				}
				map.addAttribute("allMoney", allMoney);
				map.addAttribute("sub_order_list", orderList);
			}
			if (null != order && null != order.getOrderNumber()) {
				List<TdOwnMoneyRecord> records = tdOwnMoneyRecordService
						.findByOrderNumberIgnoreCase(order.getMainOrderNumber());
				if (records != null && records.size() > 0) {
					TdOwnMoneyRecord record = records.get(0);
					map.addAttribute("ownrecord", record);
				}
			}
			map.addAttribute("td_order", order);
			TdPayType payType = tdPayTypeService.findOne(order.getPayTypeId());
			if (payType != null && payType.getIsOnlinePay()) {
				map.addAttribute("isOnlinePay", true);
			} else {
				map.addAttribute("isOnlinePay", false);
			}
		}

		map.addAttribute("id", id);
		map.addAttribute("msg", msg);
		return "/client/delivery_detail";
	}

	@RequestMapping(value = "/return/detail/{id}", method = RequestMethod.GET)
	public String returnDetail(@PathVariable Long id, HttpServletRequest req, ModelMap map, Long msg) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null == user) {
			return "redirect:/login";
		}

		if (null != id) {
			TdReturnNote returnNote = tdReturnNoteService.findOne(id);
			TdOrder order = tdOrderService.findByOrderNumber(returnNote.getOrderNumber());
			map.addAttribute("td_return_order", returnNote);
			map.addAttribute("order", order);
		}

		map.addAttribute("id", id);
		map.addAttribute("msg", msg);
		return "/client/return_detail";
	}

	/**
	 * 确认收货
	 * 
	 * @param id
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/submitDelivery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submit(Long id, HttpServletRequest req, ModelMap map) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);

		if (null == id) {
			res.put("message", "ID不能为空");
			return res;
		}

		TdOrder order = tdOrderService.findOne(id);

		if (null == order) {
			res.put("message", "订单不存在");
			return res;
		}

		// 所有子单都处理
		if (StringUtils.isNotBlank(order.getMainOrderNumber())) {
			List<TdOrder> orderList = tdOrderService.findByMainOrderNumberIgnoreCase(order.getMainOrderNumber());

			if (null != orderList) {
				for (TdOrder subOrder : orderList) {
					if (subOrder.getOrderNumber().contains("YF")) {
						subOrder.setStatusId(6L);
					} else {
						subOrder.setStatusId(5L);
					}
					subOrder.setDeliveryTime(new Date());
					subOrder = tdOrderService.save(subOrder);
				}
			}
		}

		res.put("code", 0);

		return res;
	}

	/**
	 * 确认从门店取货
	 * 
	 * @param id
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/return/recv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitReturnRecv(Long id, HttpServletRequest req, ModelMap map) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);

		if (null == id) {
			res.put("message", "ID不能为空");
			return res;
		}

		TdReturnNote returnNote = tdReturnNoteService.findOne(id);

		if (null == returnNote) {
			res.put("message", "退货单不存在");
			return res;
		}

		if (null != returnNote.getStatusId() && !returnNote.getStatusId().equals(2L)) {
			res.put("message", "退货单状态错误");
			return res;
		}

		Date current = new Date();
		returnNote.setStatusId(4L);
		returnNote.setRecvTime(current);
		returnNote.setReceiveTime(current);

		returnNote = tdReturnNoteService.save(returnNote);

		// 增加库存
		tdDiySiteInventoryService.changeGoodsInventory(returnNote, req);

		// 自动通知WMS
		// tdCommonService.sendBackMsgToWMS(returnNote);

		// 获取主单id
		// String orderNumber = returnNote.getOrderNumber();

		// TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
		// 退还钱/券
		// if (null != order) {
		// tdPriceCountService.actAccordingWMS(returnNote, order.getId());
		// }
		res.put("code", 0);

		return res;
	}

	/**
	 * 拒签退货
	 * 
	 * @param id
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/submitReturn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitReturn(Long id, HttpServletRequest req, ModelMap map) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);

		if (null == id) {
			res.put("message", "ID不能为空");
			return res;
		}

		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			res.put("message", "请重新登录");
			return res;
		}

		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null == user) {
			res.put("message", "请重新登录");
			return res;
		}

		TdOrder order = tdOrderService.findOne(id);

		if (null == order) {
			res.put("message", "订单不存在");
			return res;
		}

		// 所有子单
		if (null != order.getMainOrderNumber() && order.getStatusId() == 4L) {
			List<TdOrder> orderList = tdOrderService.findByMainOrderNumberIgnoreCase(order.getMainOrderNumber());

			if (null != orderList) {
				for (TdOrder subOrder : orderList) {
					String orderNumber = subOrder.getOrderNumber();
					if (orderNumber.contains("YF")) {
						if (!subOrder.getTotalPrice().equals(0d)) {
							// 在此生成一张欠款单
							// 只记录主单号
							if (null != order.getMainOrderNumber()) {
								List<TdOwnMoneyRecord> recList = tdOwnMoneyRecordService
										.findByOrderNumberIgnoreCase(order.getMainOrderNumber());
								TdOwnMoneyRecord rec = new TdOwnMoneyRecord();
								if (null != recList && recList.size() > 0) {
									// 如果存在修改欠款申请
									rec = recList.get(0);
								} else {
									rec.setCreateTime(new Date());
								}
								rec.setOrderNumber(order.getMainOrderNumber());
								rec.setDiyCode(order.getDiySiteCode());
								rec.setOwned(subOrder.getTotalPrice());
								rec.setPayed(0d);
								rec.setMoney(0d);
								rec.setPos(0d);
								rec.setUsername(order.getUsername());
								rec.setIspassed(true);
								rec.setIsOwn(true);
								rec.setIsEnable(true);
								rec.setIsPayed(false);
								rec.setSortId(99L);
								rec = tdOwnMoneyRecordService.save(rec);
							}
						} else {
							// do nothing!
						}
						subOrder.setStatusId(6L);
						tdOrderService.save(subOrder);
					} else {

						// 获取退货单价
						Map<Long, Double> returnUnitPrice = tdPriceCountService.getReturnUnitPrice(subOrder);

						subOrder.setStatusId(12L);
						subOrder.setDeliveryTime(new Date());

						subOrder = tdOrderService.save(subOrder);

						// 生成退货单
						if (null != subOrder) {
							TdReturnNote returnNote = new TdReturnNote();

							// 退货单编号
							Date current = new Date();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
							String curStr = sdf.format(current);
							Random random = new Random();

							returnNote.setReturnNumber(
									"T" + curStr + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));

							// 添加订单信息
							returnNote.setOrderNumber(subOrder.getOrderNumber());

							// add MDJ
							returnNote.setShoppingAddress(order.getShippingAddress());
							returnNote.setSellerRealName(order.getSellerRealName());
							// end add MDJ

							// 支付方式
							returnNote.setPayTypeId(subOrder.getPayTypeId());
							returnNote.setPayTypeTitle(subOrder.getPayTypeTitle());
							// 门店信息
							if (null != subOrder.getDiySiteId()) {
								TdDiySite diySite = tdDiySiteService.findOne(subOrder.getDiySiteId());
								returnNote.setDiySiteId(subOrder.getDiySiteId());
								returnNote.setDiyCode(diySite.getStoreCode());
								returnNote.setDiySiteTel(diySite.getServiceTele());
								returnNote.setDiySiteTitle(diySite.getTitle());
								returnNote.setDiySiteAddress(diySite.getAddress());
							}

							// 退货信息
							returnNote.setUsername(subOrder.getUsername());
							returnNote.setRemarkInfo("拒签退货");

							// 退货方式 物流取货
							returnNote.setTurnType(2L);

							// 快递员为自己
							returnNote.setDriver(user.getOpUser());

							// 退货完成
							returnNote.setStatusId(5L);

							returnNote.setDeliverTypeTitle(subOrder.getDeliverTypeTitle());

							// 生成退单时间
							returnNote.setOrderTime(current);

							// 配送员确认收货时间
							returnNote.setReceiveTime(current);

							returnNote.setTurnPrice(subOrder.getTotalGoodsPrice());
							List<TdOrderGoods> orderGoodsList = new ArrayList<>();
							if (null != subOrder.getOrderGoodsList()) {
								for (TdOrderGoods oGoods : subOrder.getOrderGoodsList()) {
									TdOrderGoods orderGoods = new TdOrderGoods();

									orderGoods.setBrandId(oGoods.getBrandId());
									orderGoods.setBrandTitle(oGoods.getBrandTitle());
									orderGoods.setGoodsId(oGoods.getGoodsId());
									orderGoods.setGoodsSubTitle(oGoods.getGoodsSubTitle());
									orderGoods.setSku(oGoods.getSku());
									orderGoods.setGoodsCoverImageUri(oGoods.getGoodsCoverImageUri());
									orderGoods.setGoodsColor(oGoods.getGoodsColor());
									orderGoods.setGoodsCapacity(oGoods.getGoodsCapacity());
									orderGoods.setGoodsVersion(oGoods.getGoodsVersion());
									orderGoods.setGoodsSaleType(oGoods.getGoodsSaleType());
									orderGoods.setGoodsTitle(oGoods.getGoodsTitle());

									orderGoods.setPrice(oGoods.getPrice());
									orderGoods.setQuantity(oGoods.getQuantity());

									orderGoods.setDeliveredQuantity(oGoods.getDeliveredQuantity());
									orderGoods.setPoints(oGoods.getPoints());
									orderGoods.setReturnNoteNumber(returnNote.getReturnNumber());
									// tdOrderGoodsService.save(orderGoods);
									Double returnUnit = returnUnitPrice.get(oGoods.getGoodsId());
									returnUnit = null == returnUnit ? 0d : returnUnit;
									orderGoods.setReturnUnitPrice(returnUnit);
									// 添加商品信息
									orderGoodsList.add(orderGoods);

									// 订单商品设置退货为True
									oGoods.setIsReturnApplied(true);
									// 更新订单商品信息是否退货状态

									tdOrderGoodsService.save(oGoods);
								}
							}
							if (null != subOrder.getGiftGoodsList()) {
								for (TdOrderGoods oGoods : subOrder.getGiftGoodsList()) {
									TdOrderGoods orderGoods = new TdOrderGoods();

									orderGoods.setBrandId(oGoods.getBrandId());
									orderGoods.setBrandTitle(oGoods.getBrandTitle());
									orderGoods.setGoodsId(oGoods.getGoodsId());
									orderGoods.setGoodsSubTitle(oGoods.getGoodsSubTitle());
									orderGoods.setSku(oGoods.getSku());
									orderGoods.setGoodsCoverImageUri(oGoods.getGoodsCoverImageUri());
									orderGoods.setGoodsColor(oGoods.getGoodsColor());
									orderGoods.setGoodsCapacity(oGoods.getGoodsCapacity());
									orderGoods.setGoodsVersion(oGoods.getGoodsVersion());
									orderGoods.setGoodsSaleType(oGoods.getGoodsSaleType());
									orderGoods.setGoodsTitle(oGoods.getGoodsTitle());

									orderGoods.setPrice(oGoods.getPrice());
									orderGoods.setQuantity(oGoods.getQuantity());

									orderGoods.setDeliveredQuantity(oGoods.getDeliveredQuantity());
									orderGoods.setPoints(oGoods.getPoints());
									orderGoods.setReturnNoteNumber(returnNote.getReturnNumber());
									// tdOrderGoodsService.save(orderGoods);
									Double returnUnit = returnUnitPrice.get(oGoods.getGoodsId());
									returnUnit = null == returnUnit ? 0d : returnUnit;
									orderGoods.setReturnUnitPrice(returnUnit);
									// 添加商品信息
									orderGoodsList.add(orderGoods);

									// 订单商品设置退货为True
									oGoods.setIsReturnApplied(true);
									// 更新订单商品信息是否退货状态
									tdOrderGoodsService.save(oGoods);
								}
							}
							if (null != subOrder.getPresentedList()) {
								for (TdOrderGoods oGoods : subOrder.getPresentedList()) {
									TdOrderGoods orderGoods = new TdOrderGoods();

									orderGoods.setBrandId(oGoods.getBrandId());
									orderGoods.setBrandTitle(oGoods.getBrandTitle());
									orderGoods.setGoodsId(oGoods.getGoodsId());
									orderGoods.setGoodsSubTitle(oGoods.getGoodsSubTitle());
									orderGoods.setSku(oGoods.getSku());
									orderGoods.setGoodsCoverImageUri(oGoods.getGoodsCoverImageUri());
									orderGoods.setGoodsColor(oGoods.getGoodsColor());
									orderGoods.setGoodsCapacity(oGoods.getGoodsCapacity());
									orderGoods.setGoodsVersion(oGoods.getGoodsVersion());
									orderGoods.setGoodsSaleType(oGoods.getGoodsSaleType());
									orderGoods.setGoodsTitle(oGoods.getGoodsTitle());

									orderGoods.setPrice(oGoods.getPrice());
									orderGoods.setQuantity(oGoods.getQuantity());

									orderGoods.setDeliveredQuantity(oGoods.getDeliveredQuantity());
									orderGoods.setPoints(oGoods.getPoints());
									orderGoods.setReturnNoteNumber(returnNote.getReturnNumber());
									// tdOrderGoodsService.save(orderGoods);
									Double returnUnit = returnUnitPrice.get(oGoods.getGoodsId());
									returnUnit = null == returnUnit ? 0d : returnUnit;
									orderGoods.setReturnUnitPrice(returnUnit);
									// 添加商品信息
									orderGoodsList.add(orderGoods);

									// 订单商品设置退货为True
									oGoods.setIsReturnApplied(true);
									// 更新订单商品信息是否退货状态
									tdOrderGoodsService.save(oGoods);
								}
							}

							returnNote.setReturnGoodsList(orderGoodsList);
							tdOrderGoodsService.save(orderGoodsList);

							// 在此进行资金和优惠券的退还
							Long realUserId = order.getRealUserId();
							TdUser realUser = null;
							if (realUserId == null) {
								realUser = tdUserService.findByUsername(order.getUsername());
							} else {
								realUser = tdUserService.findOne(realUserId);
							}
							// 在此进行资金和优惠券的退还
							tdPriceCountService.cashAndCouponBack(subOrder, realUser, returnNote);
							// 退款时间
							returnNote.setReturnTime(new Date());
							// 发送物流通知
							tdCommonService.sendBackMsgToWMS(returnNote);
							// 通知物流时间
							returnNote.setSendWmsTime(new Date());

							// 保存退货单
							tdReturnNoteService.save(returnNote);

							// 增加库存
							tdDiySiteInventoryService.changeGoodsInventory(subOrder, 1L, req, "退货", null);

							tdInterfaceService.initReturnOrder(returnNote, INFConstants.INF_RETURN_ORDER_SUB_INT);
							tdInterfaceService.initReturnCouponInfByOrder(subOrder,
									INFConstants.INF_RETURN_ORDER_CANCEL_INT);
							tdInterfaceService.sendReturnOrderByAsyn(returnNote);

							subOrder.setStatusId(12L);
							subOrder.setIsRefund(true);
							subOrder = tdOrderService.save(subOrder);
						}

					}
				}
			}
		}

		res.put("code", 0);

		return res;
	}

	/*
	 * 申请欠款 isOwn 默认为true 增加现金 和pos 增加收款时间
	 */
	@RequestMapping(value = "/submitOwnMoney/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitMoney(@PathVariable Long id, Double payed, Double owned, HttpServletRequest req,
			ModelMap map, Double money, Double pos) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);

		if (null == payed || null == owned || null == money || null == pos) {
			res.put("message", "金额不能为空");
			return res;
		}

		TdOrder order = tdOrderService.findOne(id);

		if (null == order) {
			res.put("message", "订单不存在");
			return res;
		}
		if (payed != (money + pos)) {
			res.put("message", "现金和pos总额不等于欠款金额");
			return res;
		}

		if (owned != null && owned == 0) { // 欠款为0 修改订单时付款
			modifyPayment(order.getMainOrderNumber(), payed);
		}

		// 只记录主单号
		if (null != order.getMainOrderNumber()) {
			List<TdOwnMoneyRecord> recList = tdOwnMoneyRecordService
					.findByOrderNumberIgnoreCase(order.getMainOrderNumber());
			TdOwnMoneyRecord rec = new TdOwnMoneyRecord();
			if (null != recList && recList.size() > 0) {
				// 如果存在修改欠款申请
				rec = recList.get(0);
			} else {
				rec.setCreateTime(new Date());
			}

			rec.setOrderNumber(order.getMainOrderNumber());
			rec.setDiyCode(order.getDiySiteCode());
			rec.setOwned(owned);
			rec.setPayed(payed);
			rec.setMoney(money);
			rec.setPos(pos);
			rec.setUsername(order.getUsername());
			if (owned != null && owned == 0) {
				rec.setIsOwn(false);

			} else {
				rec.setIsOwn(true);
			}
			rec.setIsEnable(false);
			rec.setIsPayed(false);
			rec.setSortId(99L);
			rec = tdOwnMoneyRecordService.save(rec);

			if (order.getIsSellerOrder()) {
				TdUser seller = tdUserService.findOne(order.getSellerId());
				tdUserService.repayCredit(CreditChangeType.REPAY, seller, payed, order.getMainOrderNumber());
			}

			// 全额收款
			if (rec.getIsOwn() == false) {
				// 修改订单收款金额
				tdOrderService.modifyOrderPay(rec.getMoney(), rec.getPos(), 0.00, rec.getOrderNumber());
				tdInterfaceService.initCashReciptByTdOwnMoneyRecord(rec, INFConstants.INF_RECEIPT_TYPE_DELIVER_INT);
			}
		}

		res.put("code", 0);
		res.put("owned", owned);
		return res;
	}

	@RequestMapping(value = "/geo/submit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitMoney(TdGeoInfo geoInfo, HttpServletRequest req, ModelMap map) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);

		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			res.put("message", "请重新登录");
			return res;
		}

		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null == user) {
			res.put("message", "请重新登录");
			return res;
		}

		if (null == geoInfo.getLongitude() || null == geoInfo.getLatitude()) {
			res.put("message", "定位信息有误");
			return res;
		}

		geoInfo.setUsername(username);
		geoInfo.setTime(new Date());
		geoInfo.setOpUser(user.getOpUser());

		tdGeoInfoService.save(geoInfo);

		res.put("code", 0);

		return res;
	}

	@RequestMapping(value = "/img", method = RequestMethod.POST)
	public String uploadImg(@RequestParam MultipartFile Filedata, HttpServletRequest req, String orderNumber, Long id) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}

		String name = Filedata.getOriginalFilename();

		String ext = name.substring(name.lastIndexOf("."));

		if (!".jpg".equals(ext) && !".png".equals(ext)) {
			return "redirect:/delivery/detail/" + id + "?msg=1";
		}

		try {
			byte[] bytes = Filedata.getBytes();

			Date dt = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String fileName = sdf.format(dt) + ext;

			String uri = SiteMagConstant.imagePath + "/" + fileName;

			File file = new File(uri);

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			stream.close();

			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
			if (null != order) {

				// 所有子单都确认收货
				if (null != order.getMainOrderNumber()) {
					List<TdOrder> orderList = tdOrderService
							.findByMainOrderNumberIgnoreCase(order.getMainOrderNumber());

					if (null != orderList) {
						for (TdOrder subOrder : orderList) {
							subOrder.setPhoto("/images/" + fileName);
							subOrder = tdOrderService.save(subOrder);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/delivery/detail/" + id + "?msg=0";
	}

	/**
	 * 获取配送列表
	 * 
	 * @param keyword
	 *            搜索关键字
	 * @param type
	 *            类型
	 * @param req
	 * @param map
	 * @return 搜索页面
	 * @author zp
	 */
	@RequestMapping(value = "/order/search")
	public String orderSearch(String keyword, Integer type, HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null == user) {
			return "redirect:/login";
		}

		if (null == type) {
			type = 1;
		}

		map.addAttribute("type", type);

		List<TdOrder> orderList = null;

		List<Long> statusIds = new ArrayList<Long>();
		statusIds.add(5L);
		statusIds.add(6L);
		statusIds.add(4L);
		statusIds.add(3L);
		// 根据类型查询相应数据
		orderList = tdOrderService.queryDeliverysearch(statusIds, keyword, user.getOpUser());

		map.addAttribute("order_list", orderList);

		return "/client/delivery_list_search";
	}

	/**
	 * 退货单 搜索功能
	 * 
	 * @param keyword
	 *            关键字
	 * @param req
	 * @param map
	 * @return 结果页面
	 */
	@RequestMapping(value = "/return/search")
	public String returnSearch(String keyword, HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null == user) {
			return "redirect:/login";
		}
		// 根据条件查询
		List<TdReturnNote> rnList = tdReturnNoteService.findReturnSearch(user.getOpUser(), keyword);

		map.addAttribute("return_list", rnList);

		return "/client/return_list_search";
	}

	/**
	 * 修改订单实付款 (有问题的方法)
	 * 
	 * @param mainOrderNumber
	 *            主单号
	 * @param payed
	 *            代收款
	 */
	private void modifyPayment(String mainOrderNumber, Double payed) {
		// // 所有子单都处理
		// if (StringUtils.isNotBlank(mainOrderNumber)) {
		// List<TdOrder> orderList =
		// tdOrderService.findByMainOrderNumberIgnoreCase(mainOrderNumber);
		//
		// if (null != orderList) {
		// for (TdOrder subOrder : orderList) {
		// //修改实付款
		// subOrder.setActualPay(subOrder.getActualPay()==null?0:subOrder.getActualPay()
		// + payed);
		// subOrder = tdOrderService.save(subOrder);
		// }
		// }
		// }
	}

	/**
	 * 查询快递员配送的订单号
	 * 
	 * @param opUser
	 *            快递员编号
	 * @return 订单号集合
	 * @author zp
	 */
	private List<String> queryOrderNumberList(String opUser) {

		List<String> orderNumberList = new ArrayList<String>();

		// 根据快递员编号找task_no
		List<TdDeliveryInfo> deliveryInfoList = tdDeliveryInfoService.findDistinctTaskNoByDriver(opUser);

		if (null != deliveryInfoList && deliveryInfoList.size() > 0) {
			List<String> taskNoList = new ArrayList<String>();

			for (TdDeliveryInfo deInfo : deliveryInfoList) {
				taskNoList.add(deInfo.getTaskNo());
			}

			if (taskNoList.size() > 0)

			{
				List<TdDeliveryInfoDetail> detailList = tdDeliveryInfoDetailService
						.findDistinctSubOrderNumberByTaskNoIn(taskNoList);

				if (null != detailList && detailList.size() > 0) {
					for (TdDeliveryInfoDetail detail : detailList) {
						if (null != detail.getSubOrderNumber() && !detail.getSubOrderNumber().isEmpty()) {
							orderNumberList.add(detail.getSubOrderNumber());
						}
					}
				}
			}
		}
		return orderNumberList;
	}

}
