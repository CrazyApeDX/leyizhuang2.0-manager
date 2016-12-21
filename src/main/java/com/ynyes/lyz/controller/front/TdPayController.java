package com.ynyes.lyz.controller.front;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.tencent.common.Signature;
import com.tencent.common.TdWXPay;
import com.ynyes.lyz.entity.TdBalanceLog;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdRecharge;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdBalanceLogService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.TdReChargeService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.service.basic.settlement.ISettlementService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping(value = "/pay")
public class TdPayController {

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdBalanceLogService tdBalanceLogService;

	@Autowired
	private TdReChargeService tdRechargeService;

	@Autowired
	private TdPriceCountService tdPriceCountService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	@Qualifier("settlementService")
	private ISettlementService settlementService;

	@RequestMapping(value = "/alipay")
	public String alipay(HttpServletRequest req, ModelMap map, String number, Long type) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/login";
		}
		// // -----请求参数-----
		//
		// 支付类型
		String payment_type = "1";

		Double fee = 0.00;

		if (null != number && number.contains("CZ")) {
			TdRecharge recharge = tdRechargeService.findByNumber(number);
			fee = recharge.getTotalPrice();
		} else {
			TdOrder order = tdOrderService.findByOrderNumber(number);
			fee = order.getTotalPrice() + order.getUpstairsLeftFee();
		}

		BigDecimal bd = new BigDecimal(fee);
		fee = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		// 获取需要支付的订单

		// 页面跳转同步通知页面路径
		String return_url = SiteMagConstant.alipayReturnUrl;
		String notify_url = SiteMagConstant.alipayReturnUrlAsnyc;

		String subject = null;

		if (null != type && type.longValue() == 0L) {
			subject = "乐易装交易线上支付";
		}
		if (null != type && type.longValue() == 1L) {
			subject = "乐易装电子钱包充值";
		}

		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("out_trade_no", number);
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_id", AlipayConfig.seller_id);
		sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
		sParaTemp.put("show_url", "www.leyizhuang.com.cn");
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", fee + "");

		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		map.put("code", sHtmlText);
		return "/client/waiting_pay";
	}

	@RequestMapping(value = "/alipay/return")
	public String alipayReturn(HttpServletRequest req) {
		// String username = (String) req.getSession().getAttribute("username");
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = req.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			try {
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)

		String out_trade_no = null;
		@SuppressWarnings("unused")
		String trade_no = null;
		String trade_status = null;
		String total_fee = null;
		try {
			// 商户订单号
			out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 支付宝交易号
			trade_no = new String(req.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 交易状态
			trade_status = new String(req.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			total_fee = new String(req.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		verify_result = true;
		if (verify_result) {// 验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				if (out_trade_no.contains("CZ")) {
					// 如果是充值的情况下
					TdRecharge recharge = tdRechargeService.findByNumber(out_trade_no);
					if (null != recharge && null != recharge.getStatusId()
							&& 1L == recharge.getStatusId().longValue()) {
						recharge.setStatusId(2L);
						TdUser user = tdUserService.findOne(recharge.getUserId());
						Double cashBalance = user.getCashBalance();
						if (null == cashBalance) {
							cashBalance = 0.00;
						}
						Double balance = user.getBalance();
						if (null == balance) {
							balance = 0.00;
						}

						recharge.setFinishTime(new Date());
						user.setCashBalance(cashBalance + recharge.getTotalPrice());
						user.setBalance(balance + recharge.getTotalPrice());
						tdUserService.save(user);
						tdRechargeService.save(recharge);
						tdPriceCountService.saveCashReceiptAndSendToEBS(recharge, user);

						TdBalanceLog log = new TdBalanceLog();
						log.setUserId(user.getId());
						log.setUsername(user.getUsername());
						log.setMoney(recharge.getTotalPrice());
						log.setType(0L);
						log.setBalanceType(1L);
						log.setCreateTime(new Date());
						log.setFinishTime(new Date());
						log.setIsSuccess(true);
						log.setReason("支付宝充值");
						log.setBalance(user.getCashBalance());
						log.setOrderNumber(out_trade_no);
						log.setDiySiteId(user.getUpperDiySiteId());
						log.setCityId(user.getCityId());
						log.setCashLeft(user.getCashBalance());
						log.setUnCashLeft(user.getUnCashBalance());
						log.setAllLeft(user.getBalance());
						tdBalanceLogService.save(log);
					}
				} else {
					// 如果是下单的情况
					TdOrder order = tdOrderService.findByOrderNumber(out_trade_no);
					if (2L == order.getStatusId().longValue()) {
						order.setOtherPay(Double.parseDouble(total_fee));
						Long id = order.getRealUserId();
						TdUser realUser = tdUserService.findOne(id);
						order.setUpstairsOtherPayed(
								order.getUpstairsOtherPayed() + Double.parseDouble(total_fee) - order.getTotalPrice());
						if (null != order) {
							req.getSession().setAttribute("order_temp", order);
							try {
								settlementService.mainOrderDataAction(order, realUser);
							} catch (Exception e) {
								e.printStackTrace();
							}
							// 虚拟订单需要分单
							if (out_trade_no.contains("XN")) {
								// tdCommonService.dismantleOrder(req);
								if (!"门店自提".equals(order.getDeliverTypeTitle())) {
									// 拆单钱先去扣减库存
									tdDiySiteInventoryService.changeGoodsInventory(order, 2L, req, "发货", null);
								}
								try {
									settlementService.disminlate(req, order);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		return "/client/pay_success";
	}

	@RequestMapping(value = "/pay/alipay/return/async")
	@ResponseBody
	public String payAlipayReturnAsync(HttpServletRequest req) {

		System.out.println("进入到异步请求控制器");

		// String username = (String) req.getSession().getAttribute("username");

		// String username = (String) req.getSession().getAttribute("username");

		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = req.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		String out_trade_no = null;
		@SuppressWarnings("unused")
		String trade_no = null;
		String trade_status = null;
		String total_fee = null;
		try {
			// 商户订单号
			out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 支付宝交易号
			trade_no = new String(req.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 交易状态
			trade_status = new String(req.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			total_fee = new String(req.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (AlipayNotify.verify(params)) {
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				if (out_trade_no.contains("CZ")) {
					// 如果是充值的情况下
					TdRecharge recharge = tdRechargeService.findByNumber(out_trade_no);
					if (null != recharge && null != recharge.getStatusId()
							&& 1L == recharge.getStatusId().longValue()) {
						recharge.setStatusId(2L);
						TdUser user = tdUserService.findOne(recharge.getUserId());
						Double cashBalance = user.getCashBalance();
						if (null == cashBalance) {
							cashBalance = 0.00;
						}
						Double balance = user.getBalance();
						if (null == balance) {
							balance = 0.00;
						}

						recharge.setFinishTime(new Date());
						user.setCashBalance(cashBalance + recharge.getTotalPrice());
						user.setBalance(balance + recharge.getTotalPrice());
						tdUserService.save(user);
						tdRechargeService.save(recharge);
						tdPriceCountService.saveCashReceiptAndSendToEBS(recharge, user);

						TdBalanceLog log = new TdBalanceLog();
						log.setUserId(user.getId());
						log.setUsername(user.getUsername());
						log.setMoney(recharge.getTotalPrice());
						log.setType(0L);
						log.setBalanceType(1L);
						log.setCreateTime(new Date());
						log.setFinishTime(new Date());
						log.setIsSuccess(true);
						log.setReason("支付宝充值");
						log.setBalance(user.getCashBalance());
						log.setOrderNumber(out_trade_no);
						log.setDiySiteId(user.getUpperDiySiteId());
						log.setCityId(user.getCityId());
						log.setCashLeft(user.getCashBalance());
						log.setUnCashLeft(user.getUnCashBalance());
						log.setAllLeft(user.getBalance());
						tdBalanceLogService.save(log);
					}
				} else {
					// 如果是下单的情况
					TdOrder order = tdOrderService.findByOrderNumber(out_trade_no);
					if (2L == order.getStatusId().longValue()) {
						order.setOtherPay(Double.parseDouble(total_fee));
						order.setUpstairsOtherPayed(
								order.getUpstairsOtherPayed() + Double.parseDouble(total_fee) - order.getTotalPrice());
						Long id = order.getRealUserId();
						TdUser realUser = tdUserService.findOne(id);
						if (null != order) {
							req.getSession().setAttribute("order_temp", order);
							try {
								settlementService.mainOrderDataAction(order, realUser);
							} catch (Exception e) {
								e.printStackTrace();
							}
							// 虚拟订单需要分单
							if (out_trade_no.contains("XN")) {
								if (!"门店自提".equals(order.getDeliverTypeTitle())) {
									// 拆单钱先去扣减库存
									tdDiySiteInventoryService.changeGoodsInventory(order, 2L, req, "发货", null);
								}
								try {
									settlementService.disminlate(req, order);
								} catch (Exception e) {
									e.printStackTrace();
								}
								// tdCommonService.dismantleOrder(req);
							}
						}
					}
				}
			}
			return "success";
		} else {
			return "fail";
		}

	}

	@RequestMapping(value = "/union")
	public String unionPay(HttpServletRequest req, ModelMap map, String number, Long type) {
		// 判断用户是否登录
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/login";
		}

		Double fee = 0.00;

		/*
		 * 重新更改订单号
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		String sDate = sdf.format(now);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		String orderNum = sDate + suiji;

		String head = number.substring(0, 2);
		String newNumber = null;

		if (null != number && number.contains("CZ")) {
			TdRecharge recharge = tdRechargeService.findByNumber(number);
			fee = recharge.getTotalPrice();
			recharge.setNumber(head + orderNum);
			tdRechargeService.save(recharge);
			newNumber = recharge.getNumber();
		} else {
			TdOrder order = tdOrderService.findByOrderNumber(number);
			fee = order.getTotalPrice() + order.getUpstairsLeftFee();
			order.setOrderNumber(head + orderNum);
			tdOrderService.save(order);
			newNumber = order.getOrderNumber();
		}

		BigDecimal bd = new BigDecimal(fee);
		fee = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		// 开始组合参数
		String MERCHANTID = "105510148160146";
		String POSID = "632776177";
		String BRANCHID = "510000000";
		String ORDERID = newNumber;
		String PAYMENT = fee + "";
		String CURCODE = "01";
		String TXCODE = "520100";
		String TYPE = "1";
		String PUB = "30819c300d06092a864886f70d010101050003818a00308186028180756c3ad19960d52e9932c000bbbfa13c98726cba9c6117c0ab42391dd2c20fbe750fedffe3ab972f6f98d47d9d048ffb26d7fdfe804bc99e36db9233d6affb1e248faf997b488cdc560ca4548f6722222b924ec239e68d204536220f5d1913d0842a996e83837d328494a729e1b66aaa28fb7149ca35c6e2b0deed7800fe5fa1020111";

		String PUB32 = PUB.substring(0, 30);
		String PUB32TR2 = PUB.substring(PUB.length() - 30);

		map.addAttribute("MERCHANTID", MERCHANTID);
		map.addAttribute("POSID", POSID);
		map.addAttribute("BRANCHID", BRANCHID);
		map.addAttribute("ORDERID", ORDERID);
		map.addAttribute("PAYMENT", PAYMENT);
		map.addAttribute("CURCODE", CURCODE);
		map.addAttribute("TXCODE", TXCODE);
		map.addAttribute("TYPE", TYPE);
		map.addAttribute("PUB32", PUB32);
		map.addAttribute("PUB32TR2", PUB32TR2);

		return "/client/union_pay";
	}

	@RequestMapping(value = "/union/return")
	public String unionReturn(HttpServletRequest req, ModelMap map, String ORDERID, Double PAYMENT, Character SUCCESS) {
		String username = (String) req.getSession().getAttribute("username");
		if (ORDERID.contains("CZ")) {
			TdRecharge recharge = tdRechargeService.findByNumber(ORDERID);
			if (null != recharge) {
				Double totalPrice = recharge.getTotalPrice();
				if (null != totalPrice && totalPrice.longValue() == PAYMENT.longValue()) {
					// 判断是否支付成功
					if (null != SUCCESS && SUCCESS.charValue() == 'Y') {
						if (recharge.getStatusId().longValue() == 1L) {
							recharge.setStatusId(2L);
							if (null == username) {
								username = recharge.getUsername();
								req.getSession().setAttribute("username", username);
							}
							TdUser user = tdUserService.findOne(recharge.getUserId());
							Double cashBalance = user.getCashBalance();
							if (null == cashBalance) {
								cashBalance = 0.00;
							}
							Double balance = user.getBalance();
							if (null == balance) {
								balance = 0.00;
							}

							recharge.setFinishTime(new Date());
							user.setCashBalance(cashBalance + recharge.getTotalPrice());
							user.setBalance(balance + recharge.getTotalPrice());
							tdUserService.save(user);
							tdRechargeService.save(recharge);
							tdPriceCountService.saveCashReceiptAndSendToEBS(recharge, user);

							TdBalanceLog log = new TdBalanceLog();
							log.setUserId(user.getId());
							log.setUsername(user.getUsername());
							log.setMoney(recharge.getTotalPrice());
							log.setType(0L);
							log.setBalanceType(1L);
							log.setCreateTime(new Date());
							log.setFinishTime(new Date());
							log.setIsSuccess(true);
							log.setReason("银联充值");
							log.setBalance(user.getCashBalance());
							log.setOrderNumber(ORDERID);
							log.setDiySiteId(user.getUpperDiySiteId());
							log.setCityId(user.getCityId());
							log.setCashLeft(user.getCashBalance());
							log.setUnCashLeft(user.getUnCashBalance());
							log.setAllLeft(user.getBalance());
							tdBalanceLogService.save(log);
						}
					}
				}
			}
		} else {
			// 根据指定的订单号查找订单
			TdOrder order = tdOrderService.findByOrderNumber(ORDERID);
			if (null == username) {
				username = order.getUsername();
				req.getSession().setAttribute("username", username);
			}
			// 在能查询到具体订单的情况下进行业务逻辑处理
			if (null != order) {
				Double totalPrice = order.getTotalPrice();
				// 在支付金额和实际金额相匹配的情况下再进行业务逻辑的处理
				if (null != totalPrice && totalPrice.longValue() == PAYMENT.longValue()) {
					// 判断是否支付成功
					if (null != SUCCESS && SUCCESS.charValue() == 'Y') {
						if (order.getStatusId().longValue() == 2L) {
							req.getSession().setAttribute("order_temp", order);
							order.setOtherPay(PAYMENT);
							Long id = order.getRealUserId();
							TdUser realUser = tdUserService.findOne(id);
							order.setUpstairsOtherPayed(order.getUpstairsOtherPayed() + PAYMENT - order.getTotalPrice());
							try {
								settlementService.mainOrderDataAction(order, realUser);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							if (ORDERID.contains("XN")) {
								if (!"门店自提".equals(order.getDeliverTypeTitle())) {
									// 拆单钱先去扣减库存
									tdDiySiteInventoryService.changeGoodsInventory(order, 2L, req, "发货", null);
								}
								try {
									settlementService.disminlate(req, order);
								} catch (Exception e) {
									e.printStackTrace();
								}
								// tdCommonService.dismantleOrder(req);
							}
						}
					}
				}
			}
		}
		return "/client/pay_success";
	}

	@RequestMapping(value = "/wx/sign")
	@ResponseBody
	public Map<String, Object> WxPayReturnSign(String orderId, HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("code", 0);
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (user == null) {
			resultMap.put("msg", "请先登录");
			return resultMap;
		}
		if (orderId == null) {
			resultMap.put("msg", "订单Id不存在");
			return resultMap;
		}
		TdOrder tdOrder = tdOrderService.findByOrderNumber(orderId);
		if (tdOrder == null) {
			resultMap.put("msg", "订单不存在");
			return resultMap;
		}
		String orderUsername = tdOrder.getRealUserUsername();
		if (username == null || !username.equalsIgnoreCase(orderUsername)) {
			resultMap.put("msg", "请先登录");
			return resultMap;
		}
		if (tdOrder.getStatusId() == null || tdOrder.getStatusId() != 2) {
			resultMap.put("msg", "参数错误");
			return resultMap;
		}
		String xml = TdWXPay.getUnifiedorderXML(tdOrder);
		ModelMap modelMap = TdWXPay.sendUnifiedorderRequest(xml);

		if (modelMap != null) {
			resultMap.put("sign", modelMap);
		} else {
			resultMap.put("msg", "签名出错");
			return resultMap;
		}
		resultMap.put("code", 1);
		return resultMap;
	}

	@RequestMapping(value = "/wx_notify")
	public void wxPayNotify(HttpServletResponse response, HttpServletRequest request, HttpServletRequest req) {
		try {
			String username = (String) req.getSession().getAttribute("username");
			Map<String, Object> map = TdWXPay.parseXml(request);
			// Map<String, Object> map = new HashMap<>();
			// map.put("is_subscribe", "N");
			// map.put("appid", "wxe9bbe828a4573b95");
			// map.put("fee_type", "CNY");
			// map.put("nonce_str", "gz2c4wfxwsr4xbvi29hhizqzz24w4vxa");
			// map.put("out_trade_no", "XN20160803235532616232");
			// map.put("device_info", "APP");
			// map.put("transaction_id", "4009372001201608030506210998");
			// map.put("trade_type", "APP");
			// map.put("sign", "352CA1B86C62823D825EC3F0C4C291F6");
			// map.put("result_code", "SUCCESS");
			// map.put("mch_id", "1323382001");
			// map.put("total_fee", "1");
			// map.put("time_end", "20160803235554");
			// map.put("openid", "ovuB1s-fS8yuY5KQyGOi8LUF329w");
			// map.put("bank_type", "CFT");
			// map.put("return_code", "SUCCESS");
			// map.put("cash_fee", "1");

			System.err.println("wx_notify -------------->" + map);

			if (!wxNotifyCheak(map))
				return;

			Double PAYMENT = Double.parseDouble((String) map.get("total_fee")) / 100;
			String out_trade_no = (String) map.get("out_trade_no");
			System.out.println(out_trade_no);
			TdOrder order = tdOrderService.findByOrderNumber(out_trade_no);
			if (null == username) {
				username = order.getUsername();
				req.getSession().setAttribute("username", username);
			}
			if (out_trade_no.contains("CZ")) {
				// 如果是充值的情况下
				TdRecharge recharge = tdRechargeService.findByNumber(out_trade_no);
				if (null != recharge && null != recharge.getStatusId() && 1L == recharge.getStatusId().longValue()) {
					recharge.setStatusId(2L);
					TdUser user = tdUserService.findOne(recharge.getUserId());
					Double cashBalance = user.getCashBalance();
					if (null == cashBalance) {
						cashBalance = 0.00;
					}
					Double balance = user.getBalance();
					if (null == balance) {
						balance = 0.00;
					}

					recharge.setFinishTime(new Date());
					user.setCashBalance(cashBalance + recharge.getTotalPrice());
					user.setBalance(balance + recharge.getTotalPrice());
					tdUserService.save(user);
					tdRechargeService.save(recharge);
					tdPriceCountService.saveCashReceiptAndSendToEBS(recharge, user);

					TdBalanceLog log = new TdBalanceLog();
					log.setUserId(user.getId());
					log.setUsername(user.getUsername());
					log.setMoney(recharge.getTotalPrice());
					log.setType(0L);
					log.setBalanceType(1L);
					log.setCreateTime(new Date());
					log.setFinishTime(new Date());
					log.setIsSuccess(true);
					log.setReason("微信充值");
					log.setBalance(user.getCashBalance());
					log.setOrderNumber(out_trade_no);
					log.setDiySiteId(user.getUpperDiySiteId());
					log.setCityId(user.getCityId());
					log.setCashLeft(user.getCashBalance());
					log.setUnCashLeft(user.getUnCashBalance());
					log.setAllLeft(user.getBalance());
					tdBalanceLogService.save(log);
				}
			} else {
				if (null != order && order.getStatusId() == 2L) {
					Double totalPrice = order.getTotalPrice();
					// 在支付金额和实际金额相匹配的情况下再进行业务逻辑的处理
					if (null != totalPrice && totalPrice.equals(PAYMENT)) {
						if (order.getStatusId().longValue() == 2L) {
							req.getSession().setAttribute("order_temp", order);
							order.setOtherPay(PAYMENT);
							Long id = order.getRealUserId();
							TdUser realUser = tdUserService.findOne(id);
							order.setUpstairsOtherPayed(order.getUpstairsOtherPayed() + PAYMENT - order.getTotalPrice());
							settlementService.mainOrderDataAction(order, realUser);
							if (out_trade_no.contains("XN")) {
								if (!"门店自提".equals(order.getDeliverTypeTitle())) {
									// 拆单钱先去扣减库存
									tdDiySiteInventoryService.changeGoodsInventory(order, 2L, req, "发货", null);
								}
								try {
									settlementService.disminlate(req, order);
								} catch (Exception e) {
									e.printStackTrace();
								}
								// tdCommonService.dismantleOrder(req);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean wxNotifyCheak(Map<String, Object> map) {
		String sign = (String) map.get("sign");
		String result_code = (String) map.get("result_code");
		String return_code = (String) map.get("return_code");
		String out_trade_no = (String) map.get("out_trade_no");
		String total_fee = (String) map.get("total_fee");
		if (result_code == null || !result_code.equalsIgnoreCase("SUCCESS")) {
			return false;
		}
		if (return_code == null || !return_code.equalsIgnoreCase("SUCCESS")) {
			return false;
		}
		if (sign == null || out_trade_no == null || total_fee == null) {
			return false;
		}
		map.remove("sign");
		String comfrimSign = Signature.getSign(map);
		return sign.equalsIgnoreCase(comfrimSign) ? true : false;
	}
}
