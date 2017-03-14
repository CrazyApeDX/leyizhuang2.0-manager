package com.ynyes.lyz.controller.front;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdActivity;
import com.ynyes.lyz.entity.TdAd;
import com.ynyes.lyz.entity.TdAdType;
import com.ynyes.lyz.entity.TdArticle;
import com.ynyes.lyz.entity.TdArticleCategory;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdNaviBarItem;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.TdRequisition;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.entity.TdSmsAccount;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;
import com.ynyes.lyz.interfaces.service.TdCashReciptInfService;
import com.ynyes.lyz.interfaces.service.TdCashRefundInfService;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;
import com.ynyes.lyz.service.TdActivityService;
import com.ynyes.lyz.service.TdAdService;
import com.ynyes.lyz.service.TdAdTypeService;
import com.ynyes.lyz.service.TdArticleCategoryService;
import com.ynyes.lyz.service.TdArticleService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdNaviBarItemService;
import com.ynyes.lyz.service.TdPriceListItemService;
import com.ynyes.lyz.service.TdRequisitionService;
import com.ynyes.lyz.service.TdReturnNoteService;
import com.ynyes.lyz.service.TdSmsAccountService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.ClientConstant;
import com.ynyes.lyz.util.MD5;

@Controller
@RequestMapping(value = "/")
public class TdIndexController {

	@Autowired
	private TdAdTypeService tdAdTypeService;

	@Autowired
	private TdAdService tdAdService;

	@Autowired
	private TdActivityService tdActivityService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdArticleCategoryService tdArticleCategoryService;

	@Autowired
	private TdArticleService tdArticleService;

	@Autowired
	private TdPriceListItemService tdPriceListService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdNaviBarItemService tdNaviBarItemService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdRequisitionService tdRequisitionService;

	@Autowired
	private TdReturnNoteService tdReturnNoteService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdSmsAccountService tdSmsAccountService;
	
	@Autowired
	private TdCashReciptInfService tdCashReciptInfService;
	
	@Autowired
	private TdInterfaceService tdInterfaceService;
	
	@Autowired
	private TdCashRefundInfService tdCashRefundInfService;

	@RequestMapping("/sendRequisition")
	@ResponseBody
	public Map<String, String> testmathod(String aString) {
		TdRequisition requisition = tdRequisitionService.findByOrderNumber(aString);
		Map<String, String> map = new HashMap<>();
		map.put("结果", "要货单不存在");
		if (requisition != null) {
			map.put("结果", "要货单发送成功");
			map = tdCommonService.sendWmsMst(requisition);
		}

		return map;
	}

	@RequestMapping("/tempBack")
	@ResponseBody
	public Map<String, String> tempBack(String number) {
		Map<String, String> map = new HashMap<>();
		List<TdReturnNote> returnNotes = tdReturnNoteService.findByOrderNumberContaining(number);
		if (returnNotes != null && returnNotes.size() >= 1) {
			for (TdReturnNote tdReturnNote : returnNotes) {
				map = tdCommonService.testSendBackMsgToWMS(tdReturnNote);
			}
		}
		return map;
	}

	@RequestMapping("/test")
	@ResponseBody
	public Map<String, String> testMethod() {
		Map<String, String> map = new HashMap<>();

		// String xmlStr =
		// "<ERP><TABLE><C_OWNER_NO>001</C_OWNER_NO><C_TASK_NO>SU12031604250002</C_TASK_NO><C_TASK_ID>1</C_TASK_ID><C_TASK_TYPE>一般出货</C_TASK_TYPE><C_OP_TYPE>C</C_OP_TYPE><C_S_LOCATION_NO>F1F0111</C_S_LOCATION_NO><C_S_LOCATION_ID>9</C_S_LOCATION_ID><C_S_CONTAINER_SERNO></C_S_CONTAINER_SERNO><C_S_CONTAINER_NO>OU121604240030</C_S_CONTAINER_NO><C_D_LOCATION_NO></C_D_LOCATION_NO><C_D_CONTAINER_NO></C_D_CONTAINER_NO><C_D_CONTAINER_SERNO></C_D_CONTAINER_SERNO><C_GCODE>TJHD801-5</C_GCODE><C_STOCKATTR_ID>1</C_STOCKATTR_ID><C_PACK_QTY>1</C_PACK_QTY><C_D_REQUEST_QTY>1.00</C_D_REQUEST_QTY><C_D_ACK_BAD_QTY>0.00</C_D_ACK_BAD_QTY><C_D_ACK_QIFT_QTY>0.00</C_D_ACK_QIFT_QTY><C_D_ACK_QTY>1.00</C_D_ACK_QTY><C_OP_USER>100003</C_OP_USER><C_OP_TOOLS>PDA</C_OP_TOOLS><C_OP_STATUS>已出车</C_OP_STATUS><C_WAVE_NO>WA12031604240010</C_WAVE_NO><C_SOURCE_NO>OU121604240030</C_SOURCE_NO><C_RESERVED1>HR20160424165828513486</C_RESERVED1><C_RESERVED2></C_RESERVED2><C_RESERVED3></C_RESERVED3><C_RESERVED4></C_RESERVED4><C_RESERVED5></C_RESERVED5><C_NOTE></C_NOTE><C_MK_DT>2016/4/25
		// 9:43:27</C_MK_DT><C_MK_USERNO>100003</C_MK_USERNO><C_MODIFIED_DT>2016/4/25
		// 9:43:29</C_MODIFIED_DT><C_MODIFIED_USERNO>100003</C_MODIFIED_USERNO><C_UPLOAD_STATUS></C_UPLOAD_STATUS><C_SEND_FALG>否</C_SEND_FALG></TABLE><TABLE><C_OWNER_NO>001</C_OWNER_NO><C_TASK_NO>SU12031604250002</C_TASK_NO><C_TASK_ID>2</C_TASK_ID><C_TASK_TYPE>一般出货</C_TASK_TYPE><C_OP_TYPE>C</C_OP_TYPE><C_S_LOCATION_NO>F1F0111</C_S_LOCATION_NO><C_S_LOCATION_ID>10</C_S_LOCATION_ID><C_S_CONTAINER_SERNO></C_S_CONTAINER_SERNO><C_S_CONTAINER_NO>OU121604240030</C_S_CONTAINER_NO><C_D_LOCATION_NO></C_D_LOCATION_NO><C_D_CONTAINER_NO></C_D_CONTAINER_NO><C_D_CONTAINER_SERNO></C_D_CONTAINER_SERNO><C_GCODE>TJHM8015-5</C_GCODE><C_STOCKATTR_ID>1</C_STOCKATTR_ID><C_PACK_QTY>1</C_PACK_QTY><C_D_REQUEST_QTY>1.00</C_D_REQUEST_QTY><C_D_ACK_BAD_QTY>0.00</C_D_ACK_BAD_QTY><C_D_ACK_QIFT_QTY>0.00</C_D_ACK_QIFT_QTY><C_D_ACK_QTY>1.00</C_D_ACK_QTY><C_OP_USER>100003</C_OP_USER><C_OP_TOOLS>PDA</C_OP_TOOLS><C_OP_STATUS>已出车</C_OP_STATUS><C_WAVE_NO>WA12031604240010</C_WAVE_NO><C_SOURCE_NO>OU121604240030</C_SOURCE_NO><C_RESERVED1>HR20160424165828513486</C_RESERVED1><C_RESERVED2></C_RESERVED2><C_RESERVED3></C_RESERVED3><C_RESERVED4></C_RESERVED4><C_RESERVED5></C_RESERVED5><C_NOTE></C_NOTE><C_MK_DT>2016/4/25
		// 9:43:27</C_MK_DT><C_MK_USERNO>100003</C_MK_USERNO><C_MODIFIED_DT>2016/4/25
		// 9:43:29</C_MODIFIED_DT><C_MODIFIED_USERNO>100003</C_MODIFIED_USERNO><C_UPLOAD_STATUS></C_UPLOAD_STATUS><C_SEND_FALG>否</C_SEND_FALG></TABLE></ERP>";
//		String xmlStr = "<ERP><TABLE><category_id>32164</category_id><concatenated_segments>测试一-测试二</concatenated_segments><category_set_name>电商APP产品分类</category_set_name><segment1>测试一</segment1><segment2>测试二</segment2></TABLE></ERP>";
//		byte[] bs = xmlStr.getBytes();
//		byte[] encodeByte = Base64.encode(bs);
//		String encodeXML = null;
//		try {
//			encodeXML = new String(encodeByte, "UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			System.err.println("MDJ_WMS:XML 编码出错!");
//			map.put("error", "MDJ_WMS:XML 编码出错!");
//			return map;
//		}
//		map.put("result", encodeXML);
		TdUser tdUser = tdUserService.findOne(16669l);
		tdUser.setOpUser("1231");
		tdUser.setBalance(123456d);
//		tdUserService.saveWithOutBalance(tdUser);
		map.put("result", "1");
		return map;
	}

	@RequestMapping
	public String index(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		if (null != user.getUserType()
				&& (user.getUserType().equals(0L) || user.getUserType().equals(1L) || user.getUserType().equals(2L))) {
			tdCommonService.setHeader(req, map);

			// 查找指定用户所属的门店
			TdDiySite diySite = tdCommonService.getDiySite(req);

			// 查找首页轮播广告
			TdAdType adType = tdAdTypeService.findByTitle("首页轮播广告");
			if (null != adType) {
				List<TdAd> circle_ad_list = tdAdService.findByTypeId(adType.getId());
				map.addAttribute("circle_ad_list", circle_ad_list);
			}

			// 查找首页中部广告
			TdAdType index_center_adType = tdAdTypeService.findByTitle("首页中部广告");
			if (null != index_center_adType) {
				List<TdAd> index_center_list = tdAdService.findByTypeId(index_center_adType.getId());
				if (null != index_center_adType && index_center_list.size() > 0) {
					map.addAttribute("index_center", index_center_list.get(0));
				}
			}

			// 查找头条信息
			TdArticleCategory articleCategory = tdArticleCategoryService.findByTitle("头条消息");
			if (null != articleCategory) {
				List<TdArticle> headline_list = tdArticleService.findByCategoryIdAndStatusId(articleCategory.getId());
				map.addAttribute("headline_list", headline_list);
			}

			// 查找导航栏
			List<TdNaviBarItem> navi_bar_list = tdNaviBarItemService.findByIsEnableTrueOrderBySortIdAsc();
			map.addAttribute("navi_bar_list", navi_bar_list);

			// 查找首页推荐商品
			if (null != diySite) {
				Page<TdPriceListItem> commend_page = tdPriceListService
						.findByPriceListIdAndIsCommendIndexTrueOrderBySortIdAsc(diySite.getPriceListId(),
								ClientConstant.pageSize, 0);
				map.addAttribute("commend_page", commend_page);
				if (null != commend_page) {
					List<TdPriceListItem> content = commend_page.getContent();
					if (null != content) {
						for (int i = 0; i < content.size(); i++) {
							TdPriceListItem priceListItem = content.get(i);
							if (null != priceListItem) {
								TdGoods goods = tdGoodsService.findOne(priceListItem.getGoodsId());
								if (null != goods) {
									map.addAttribute("goods" + i, goods);
								}
							}
						}
					}
				}
			}

			// 查找所有首页推荐的未过期的活动
			List<TdActivity> index_activities = tdActivityService
					.findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndIsCommendIndexTrueOrderBySortIdAsc(
							diySite.getId() + "", new Date());

			List<Map<TdGoods, Double>> promotion_list = tdCommonService.getPromotionGoodsAndPrice(req,
					index_activities);
			// 将存储促销信息的集合放入到ModelMap中

			// 清楚session中的订单信息
			req.getSession().setAttribute("order_temp", null);
			map.addAttribute("promotion_list", promotion_list);
		}

		if (null != user.getUserType() && user.getUserType().equals(5L)) {
			return "redirect:/delivery";
		}

		return "/client/index";
	}

	@RequestMapping(value = "/return/password")
	public String returnPassword(HttpServletRequest req, ModelMap map) {
		return "/client/return_password";
	}

	@RequestMapping(value = "/password/code")
	@ResponseBody
	public Map<String, Object> sendCode(HttpServletRequest req, String phone) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 判断用户是否存在
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(phone);
		if (null == user) {
			res.put("message", "该手机号码未注册");
			return res;
		}

		// 获取指定用户的所属城市
		Long cityId = user.getCityId();
		TdCity city = tdCityService.findBySobIdCity(cityId);
		if (null == city) {
			res.put("message", "获取用户归属城市失败");
			return res;
		}

		// 获取城市所使用的短信账户
		TdSmsAccount account = tdSmsAccountService.findOne(city.getSmsAccountId());
		if (null == account) {
			res.put("message", "获取短信账户信息失败");
			return res;
		}

		Random random = new Random();
		String smscode = random.nextInt(9000) + 1000 + "";
		HttpSession session = req.getSession();
		session.setAttribute("SMS" + phone, smscode);
		String info = "您正在执行【找回密码】操作，验证码为" + smscode + "，请在页面中输入以完成验证。";
		String content = null;
		try {
			content = URLEncoder.encode(info, "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
			res.put("message", "验证码生成失败");
			return res;
		}

		String url = "http://115.28.146.248:6070/interface/Send.aspx?enCode=" + account.getEncode() + "&enPass="
				+ account.getEnpass() + "&userName=" + account.getUserName() + "&mob=" + phone + "&msg=" + content;
		StringBuffer return_code = null;
		try {
			URL u = new URL(url);
			URLConnection connection = u.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			httpConn.setRequestProperty("Content-type", "text/html");
			httpConn.setRequestProperty("Accept-Charset", "utf-8");
			httpConn.setRequestProperty("contentType", "utf-8");
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader reader = null;
			StringBuffer resultBuffer = new StringBuffer();
			String tempLine = null;

			if (httpConn.getResponseCode() >= 300) {
				res.put("message", "HTTP Request is not success, Response code is " + httpConn.getResponseCode());
				return res;
			}

			try {
				inputStream = httpConn.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);

				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
				}
				return_code = resultBuffer;

			} finally {
				if (reader != null) {
					reader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.put("message", "验证码生成失败");
			return res;
		}
		res.put("status", 0);
		res.put("code", return_code);
		return res;
	}

	@RequestMapping(value = "/sms/check")
	@ResponseBody
	public Map<String, Object> smsCheck(String phone, String code, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		if (null == phone || null == code) {
			res.put("message", "获取数据失败");
			return res;
		}

		// 获取session中的验证码
		String sms_session = (String) req.getSession().getAttribute("SMS" + phone);
		if (!(code.equalsIgnoreCase(sms_session))) {
			res.put("message", "验证码输入错误");
			return res;
		}

		res.put("phone", phone);
		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/change/password")
	public String changePassword(HttpServletRequest req, ModelMap map, String phone) {
		if (null == phone) {
			return "redirect:/return/password";
		}
		map.addAttribute("phone", phone);
		return "/client/change_password";
	}

	@RequestMapping(value = "/password/save")
	@ResponseBody
	public Map<String, Object> passwordSave(String password, String username) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		if (null == password || null == username) {
			res.put("message", "数据获取失败");
			return res;
		}
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		user.setPassword(MD5.md5(password, 32));
		tdUserService.save(user);
		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/prompt")
	public String prompt(HttpServletRequest req, ModelMap map, String msg) {
		map.addAttribute("msg", msg);
		return "/client/common_prompt";
	}
	
	@RequestMapping(value = "/recover/recharge")
	@ResponseBody
	public Map<String, Object> recoverRecharge(String token) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		if (!"600ca5685906e53f453f191711125a38".equals(token) && !"f14d1baec94eda4de55466471eb54489".equals(token)) {
			res.put("message", "token验证失败");
			return res;
		}
		
		// 查询所有未传递成功的充值类型的td_cash_recipt_inf
		List<TdCashReciptInf> list01 = tdCashReciptInfService.findByReceiptTypeAndSendFlag("充值其他", 1);
		List<TdCashReciptInf> list02 = tdCashReciptInfService.findByReceiptTypeAndSendFlag("财务转账", 1);
		if ((null == list01 && null == list02) || (list01.size() == 0 && list02.size() == 0)) {
			res.put("message", "未找到传递失败的充值记录");
			return res;
		}
		
		StringBuffer result = new StringBuffer();
		
		for (TdCashReciptInf item : list01) {
			if (null != item) {
				String resultStr = tdInterfaceService.ebsWithObject(item, INFTYPE.CASHRECEIPTINF);
				result.append(resultStr);
			}
		}
		
		for (TdCashReciptInf item : list02) {
			if (null != item) {
				String resultStr = tdInterfaceService.ebsWithObject(item, INFTYPE.CASHRECEIPTINF);
				result.append(resultStr);
			}
		}
		
		res.put("message", "调用接口成功");
		res.put("EBS_MESSAGE", result.toString());
		res.put("status", 0);
		return res;
	}
	
	@RequestMapping(value = "/recover/recharge/all")
	@ResponseBody
	public Map<String, Object> recoverRechargeAll(String token) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		if (!"600ca5685906e53f453f191711125a38".equals(token) && !"f14d1baec94eda4de55466471eb54489".equals(token)) {
			res.put("message", "token验证失败");
			return res;
		}
		
		List<TdCashReciptInf> list01 = tdCashReciptInfService.findBySendFlag(1);
		StringBuffer result = new StringBuffer();
		
		for (TdCashReciptInf item : list01) {
			if (null != item) {
				String resultStr = tdInterfaceService.ebsWithObject(item, INFTYPE.CASHRECEIPTINF);
				result.append(resultStr);
			}
		}
		res.put("message", "调用接口成功");
		res.put("EBS_MESSAGE", result.toString());
		res.put("status", 0);
		return res;
	}
	
	@RequestMapping(value = "/recover/recharge/{id}")
	@ResponseBody
	public Map<String, Object> recoverRechargeById(@PathVariable Long id, String token){
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		if (!"600ca5685906e53f453f191711125a38".equals(token) && !"f14d1baec94eda4de55466471eb54489".equals(token)) {
			res.put("message", "token验证失败");
			return res;
		}
		
		TdCashReciptInf item = tdCashReciptInfService.findOne(id);
		if (null == item) {
			res.put("message", "指定id的收款记录不存在");
			return res;
		}
		
		if (null != item.getReceiptType()) {
			if (!item.getReceiptType().equals("充值其他") && !item.getReceiptType().equals("财务转账")) {
				res.put("message", "指定id的收款记录不属于充值记录");
				return res;
			}
		}
		
		if (null != item.getSendFlag() && item.getSendFlag().intValue() == 0) {
			res.put("message", "指定id的收款记录已经传递成功，不能重复传递");
			return res;
		}
		
		String resultStr = tdInterfaceService.ebsWithObject(item, INFTYPE.CASHRECEIPTINF);
		
		res.put("message", "调用接口成功");
		res.put("EBS_MESSAGE", resultStr.toString());
		res.put("status", 0);
		return res;		
	}
	
	@RequestMapping(value = "/recover/deposit")
	@ResponseBody
	public Map<String, Object> recoverDeposit(String token) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		if (!"600ca5685906e53f453f191711125a38".equals(token) && !"f14d1baec94eda4de55466471eb54489".equals(token)) {
			res.put("message", "token验证失败");
			return res;
		}
		
		List<TdCashRefundInf> list = tdCashRefundInfService.findByRefundTypeAndSendFlag("财务转账", 1);
		if (null == list || list.size() == 0) {
			res.put("message", "未查询到传递失败的提现记录");
			return res;
		}
		
		StringBuffer result = new StringBuffer();
		
		for (TdCashRefundInf item : list) {
			if (null != item) {
				String ebsInfo = tdInterfaceService.ebsWithObject(item, INFTYPE.CASHREFUNDINF);
				result.append(ebsInfo);
			}
		}
		
		res.put("message", "调用接口成功");
		res.put("EBS_MESSAGE", result.toString());
		res.put("status", 0);
		return res;
	}
	
	@RequestMapping(value = "/recover/deposit/{id}")
	@ResponseBody
	public Map<String, Object> recoverDepositById(@PathVariable Long id, String token) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		if (!"600ca5685906e53f453f191711125a38".equals(token) && !"f14d1baec94eda4de55466471eb54489".equals(token)) {
			res.put("message", "token验证失败");
			return res;
		}
		
		TdCashRefundInf item = tdCashRefundInfService.findOne(id);
		if (null == item) {
			res.put("message", "未查找到指定id的提现退款信息");
			return res;
		}
		
		if (null != item.getRefundType() && !"财务转账".equals(item.getRefundType())) {
			res.put("message", "指定id的退款记录不属于提现退款记录");
			return res;
		}
		
		if (null != item.getSendFlag() && item.getSendFlag().intValue() == 0) {
			res.put("message", "指定id的提现退款记录已经发送成功，不能重新发送");
			return res;
		}
		
		String ebsInfo = tdInterfaceService.ebsWithObject(item, INFTYPE.CASHREFUNDINF);
		res.put("message", "调用接口成功");
		res.put("EBS_MESSAGE", ebsInfo);
		res.put("status", 0);
		return res;
	}
}
