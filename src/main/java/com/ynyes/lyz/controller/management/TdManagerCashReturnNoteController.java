package com.ynyes.lyz.controller.management;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.lyz.entity.TdCashReturnNote;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.entity.TdSmsAccount;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;
import com.ynyes.lyz.interfaces.service.TdEbsSenderService;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.service.TdCashReturnNoteService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdReturnNoteService;
import com.ynyes.lyz.service.TdSmsAccountService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * <p>
 * 标题：TdManagerCashReturnNoteController.java
 * </p>
 * <p>
 * 描述：后台与退款申请相关的控制器
 * </p>
 * <p>
 * 公司：http://www.ynsite.com
 * </p>
 * 
 * @author 作者：DengXiao
 * @version 版本：上午10:43:33
 */
@Controller
@RequestMapping(value = "/Verwalter/cash/return/note")
public class TdManagerCashReturnNoteController {

	@Autowired
	private TdCashReturnNoteService tdCashReturnNoteService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdReturnNoteService tdReturnNoteService;

	@Autowired
	private TdInterfaceService tdInterfaceService;
	
	@Autowired
	private TdEbsSenderService tdEbsSenderService;
	
	@Autowired
	private TdCityService tdRegionService;
	
	@Autowired
	private TdSmsAccountService tdSmsAccountService;
	
	@Autowired
	private TdUserService tdUserService;

	@RequestMapping(value = "/list")
	public String cashReturnNoteList(Integer page, Integer size, String keywords, Long type, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, HttpServletRequest req, ModelMap map, Long cityCode,
			Long diyCode, Integer isOperatedNum) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		isOperatedNum = (null == isOperatedNum) ? 0 : isOperatedNum;

		if (null == page) {
			page = 0;
		}

		if (null == size) {
			size = SiteMagConstant.pageSize;
		}

		// // 表示点击了某项申请的“已经打款”按钮，__EVENTARGUMENT表示打款申请单的id
		// if (null != __EVENTARGUMENT) {
		// returnMoney(__EVENTARGUMENT);
		// }

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equals("btnSearch")) {
				page = 0;
			} else if (__EVENTTARGET.equals("returnMoney")) {
				returnMoney(__EVENTARGUMENT, req);
			}
		}

		Page<TdCashReturnNote> cashReturnNote_page = null;
		if (StringUtils.isEmpty(keywords)) {
			if (isOperatedNum.equals(0)) {
				cashReturnNote_page = tdCashReturnNoteService.findAll(page, size);
			} else {
				Boolean isOperate = (isOperatedNum.equals(1)) ? true : false;
				cashReturnNote_page = tdCashReturnNoteService.findByIsOperated(isOperate, page, size);
			}

		} else {
			if (isOperatedNum.equals(0)) {
				cashReturnNote_page = tdCashReturnNoteService
						.findByUsernameContainingOrOrderNumberContainingOrMainOrderNumberContainingOrReturnNoteNumberContaining(
								keywords, page, size);
			} else {
				Boolean isOperate = (isOperatedNum.equals(1)) ? true : false;
				cashReturnNote_page = tdCashReturnNoteService
						.findByUsernameContainingAndIsOperatedOrOrderNumberContainingAndIsOperatedOrMainOrderNumberContainingAndIsOperatedOrReturnNoteNumberContainingAndIsOperated(
								keywords, isOperate, page, size);
			}

		}

		map.addAttribute("cashReturnNote_page", cashReturnNote_page);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("isOperatedNum", isOperatedNum);
		return "/site_mag/cash_return_note_list";
	}

	/**
	 * 确认打款的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：2016年6月17日上午11:38:07
	 */
	private void returnMoney(String sId, HttpServletRequest req) {
		if (null == sId) {
			return;
		}

		try {
			Long id = Long.parseLong(sId);
			TdCashReturnNote note = tdCashReturnNoteService.findOne(id);
			// 第一步操作：改变退款申请单的状态
			note.setFinishTime(new Date());
			note.setIsOperated(true);
			tdCashReturnNoteService.save(note);

			// add Mdj
			TdCashRefundInf cashRefundInf = tdInterfaceService.initCashRefundInf(note);
//			tdInterfaceService.ebsWithObject(cashRefundInf, INFTYPE.CASHREFUNDINF);
			tdEbsSenderService.sendCashRefundToEbsAndRecord(cashRefundInf);

			// 第二步操作：修改分单状态
			String orderNumber = note.getOrderNumber();
			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
			/*
			 * 在此进行对分单的操作，如果不需要对分单进行操作请删除上段代码
			 */
			// ------------------------对分单的操作---------------------------

			tdOrderService.save(order);
			// ------------------------操作结束------------------------------

			// 第三步：对退货单进行操作
			String returnNoteNumber = note.getReturnNoteNumber();
			TdReturnNote returnNote = tdReturnNoteService.findByReturnNumber(returnNoteNumber);
			/*
			 * 在此进行对退货单的操作，如果不需要对退货单进行操作请删除上段代码
			 */
			// ------------------------对退货单的操作---------------------------

			tdReturnNoteService.save(returnNote);
			// ------------------------操作结束------------------------------
			
			String phone = order.getUsername();
			TdUser user = tdUserService.findByUsernameAndIsEnableTrue(phone);
			//发送短信通知
			if (null != phone && !"".equalsIgnoreCase(phone)) {
				this.sendSmsCaptcha(req, phone, 
						"亲爱的用户，您的订单"+order.getOrderNumber()+"退款成功，请您到原支付账户确认退款到账情况。", 
							user.getCityName());
			}
		} catch (Exception e) {
			// 转换类型失败之后也不继续处理
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * 短信接口
	 * @param req
	 * @param phone
	 * @param message
	 * @param cityInfo
	 */
	private void sendSmsCaptcha(HttpServletRequest req, String phone, String message, String cityInfo) {
		String content = null;
		try {
			content = URLEncoder.encode(message, "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
		}

		TdCity region = tdRegionService.findByCityName(cityInfo);
		if (null != region) {
		
			TdSmsAccount account = tdSmsAccountService.findOne(region.getSmsAccountId());
			String url = "http://www.mob800.com/interface/Send.aspx?enCode=" + account.getEncode() + "&enPass="
					+ account.getEnpass() + "&userName=" + account.getUserName() + "&mob=" + phone + "&msg=" + content;
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
	
				try {
					inputStream = httpConn.getInputStream();
					inputStreamReader = new InputStreamReader(inputStream);
					reader = new BufferedReader(inputStreamReader);
	
					while ((tempLine = reader.readLine()) != null) {
						resultBuffer.append(tempLine);
					}
	
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
			}
		}
	}
}
