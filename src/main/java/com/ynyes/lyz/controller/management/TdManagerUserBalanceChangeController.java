package com.ynyes.lyz.controller.management;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdBalanceLog;
import com.ynyes.lyz.entity.TdChangeBalanceLog;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDeposit;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdRecharge;
import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.entity.TdSmsAccount;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;
import com.ynyes.lyz.service.TdBalanceLogService;
import com.ynyes.lyz.service.TdChangeBalanceLogService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdDepositService;
import com.ynyes.lyz.service.TdDiySiteRoleService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.TdReChargeService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdSmsAccountService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * <p>标题：TdManagerUserBalanceChangeController.java</p>
 * <p>描述：管理预存款修改板块的控制器</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年8月26日上午11:19:20
 */
@Controller
@RequestMapping(value = "/Verwalter/user/balance/change")
public class TdManagerUserBalanceChangeController {
	
	@Autowired
	private TdDiySiteRoleService tdDiySiteRoleService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@Autowired
	private TdManagerService tdManagerService;
	
	@Autowired
	private TdBalanceLogService tdBalanceLogService;
	
	@Autowired
	private TdChangeBalanceLogService tdChangeBalanceLogService;
	
	@Autowired
	private TdSettingService tdSettingService;
	
	@Autowired
	private TdCityService tdRegionService;
	
	@Autowired
	private TdSmsAccountService tdSmsAccountService;

	@Autowired
	private TdReChargeService tdReChargeService;
	
	@Autowired
	private TdPriceCountService tdPriceCountService;
	
	@Autowired
	private TdInterfaceService tdInterfaceService;
	
	@Autowired
	private TdDepositService tdDepositService;
	
	@Autowired
	private TdCommonService tdCommonService;
	
	/**
	 * <p>描述：该控制器获取权限下的所有用户</p>
	 * @author 作者：CrazyDX
	 * @version 版本：上午11:20:34
	 */
	@RequestMapping(value = "/list")
	public String userBalanceChangeList(Integer page, Integer size, String keywords, Long roleId, Long userType,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId,
			ModelMap map, HttpServletRequest req, Long cityCode, Long diyCode) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		// 获取管理员管辖城市
		List<TdCity> cityList = new ArrayList<TdCity>();
		// 获取管理员管辖门店
		List<TdDiySite> diyList = new ArrayList<TdDiySite>();

		// 管理员获取管辖的城市和门店
		tdDiySiteRoleService.userRoleCityAndDiy(cityList, diyList, username);

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSearch")) {
				page = 0;
			}
		}

		// 修改城市刷新门店列表
		if (cityCode != null) {
			// 需要删除的diy
			List<TdDiySite> diyRemoveList = new ArrayList<TdDiySite>();
			for (TdDiySite tdDiySite : diyList) {
				if (!cityCode.equals(tdDiySite.getRegionId())) {
					diyRemoveList.add(tdDiySite);
					if (tdDiySite.getId() == diyCode) {
						diyCode = null;
					}
				}
			}
			diyList.removeAll(diyRemoveList);
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
		}

		if (null != keywords) {
			keywords = keywords.trim();
		}

		map.addAttribute("cityList", cityList);
		map.addAttribute("diySiteList", diyList);
		map.addAttribute("cityCode", cityCode);
		map.addAttribute("diyCode", diyCode);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("roleId", roleId);
		map.addAttribute("userType", userType);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		Page<TdUser> userPage = null;

		if (null == roleId) {
			// 获取管辖门店id列表
			List<Long> roleDiyIds = new ArrayList<Long>();
			if (diyList != null && diyList.size() > 0) {
				for (TdDiySite diy : diyList) {
					roleDiyIds.add(diy.getId());
				}
			}
			// 获取分页数据
			userPage = tdUserService.searchList(keywords, roleDiyIds, userType, cityCode, diyCode, size, page);
		}

		map.addAttribute("user_page", userPage);

		return "/site_mag/user_balance_change_list";
	}

	@RequestMapping(value = "/edit")
	public String userBalanceChangeEdit(Long id, Long roleId, String action, String __VIEWSTATE, ModelMap map,
			HttpServletRequest req) {
		String manager = (String) req.getSession().getAttribute("manager");
		if (null == manager) {
			return "redirect:/Verwalter/login";
		}
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("roleId", roleId);
		
		TdUser user = tdUserService.findOne(id);
		if (null != user) {
			String username = user.getUsername();
			String realName = user.getRealName();
			Double cashBalance = user.getCashBalance();
			Double unCashBalance = user.getUnCashBalance();
			
			map.addAttribute("username", username);
			map.addAttribute("realName", realName);
			map.addAttribute("cashBalance", cashBalance);
			map.addAttribute("unCashBalance", unCashBalance);
		}
		return "/site_mag/user_balance_change_edit";
	}
	
	@RequestMapping(value = "/manager/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> managerCheck(HttpServletRequest req, String password, String username) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		
		String manager = (String) req.getSession().getAttribute("manager");
		if (null == manager) {
			res.put("message", "未能成功获取到登录管理员的信息，操作失败");
			return res;
		} else {
			TdUser user = tdUserService.findByUsername(username);
			TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(manager);
			if (null == tdManager) {
				res.put("message", "未能成功获取到登录管理员的信息，操作失败");
				return res;
			} else if (null != tdManager.getErrorCount() && 0 == tdManager.getErrorCount().intValue()) {
				res.put("message", "您已经输入错误3次，暂不可修改用于预存款，请联系超级管理员");
				return res;
			} else if (!tdManager.getPassword().equals(password)) {
				tdManager.setErrorCount(tdManager.getErrorCount() - 1);
				tdManagerService.save(tdManager);
				if (0 == tdManager.getErrorCount().intValue()) {
					res.put("message", "管理员密码错误，操作失败，您已经输入错误3次，暂不可修改用于预存款，请联系超级管理员");
					// 在此给管理员发短信
					TdSetting setting = tdSettingService.findTopBy();
					String phones = setting.getInfPhone();
					if (null != phones && !"".equalsIgnoreCase(phones)) {
						String[] allPhones = phones.split(",");
						for (String phone : allPhones) {
							this.sendSmsCaptcha(req, phone, 
									"管理员" + tdManager.getUsername() + "修改用户" + user.getUsername() 
											+ "（" + user.getRealName() + "）预存款时，密码输入错误超过3次，为保证用户账户信息安全，现关闭管理员修改用户预存款权限", 
										user.getCityName());
						}
					}
					
					
					// 在此存储错误操作信息
					TdChangeBalanceLog log = new TdChangeBalanceLog();
					if (null != user) {
						log.setUsername(user.getUsername());
						log.setRealName(user.getRealName());
						log.setDiySiteTitle(user.getDiyName());
						log.setManager(manager);
						log.setIsSuccess(false);
						log.setOperateTime(new Date());
						try {
							log.setOperateIp(InetAddress.getLocalHost().getHostAddress());
						} catch (UnknownHostException e) {
							e.printStackTrace();
						}
						log.setRemark("密码输入错误3次，锁定管理员修改用户预存款功能");
						tdChangeBalanceLogService.save(log);
					}
				} else {
					res.put("message", "管理员密码错误，操作失败");
				}
				return res;
			}
		}
		
		res.put("status", 0);
		return res;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String userBalanceChangeSave(HttpServletRequest req, Double cashBalance, Double unCashBalance, String username, String remark,Integer changeType,String userOrderNumber,String transferTime ) {
		String managerUsername = (String) req.getSession().getAttribute("manager");
		TdManager manager = tdManagerService.findByUsernameAndIsEnableTrue(managerUsername);
		if (null == manager) {
			return "redirect:/Verwalter/login";
		}
		
		TdUser user = tdUserService.findByUsername(username);
		String description = null;
		if(null !=changeType){
			switch (changeType) {
			case 1:
				description="公司刷POS充值";
				break;
			case 2:
				description="网银转账充值";
				break;
			case 3:
				description="交现金充值";
				break;
			case 4:
				description="线上支付宝充值失败";
				break;
			case 5:
				description="线上微信充值失败";
				break;
			case 6:
				description="线上银联充值失败";
				break;
			case 7:
				description="取消订单退支付宝第三方支付";
				break;
			case 8:
				description="取消订单退微信第三方支付";
				break;
			case 9:
				description="取消订单退银联第三方支付";
				break;
			case 10:
				description="装饰公司信用额度充值";
				break;
			case 11:
				description="CRM积分转预存款";
				break;
			default:
				break;
			}
		}else{
			description="NULL";
		}
		
		String changeTypeTitle = null;
		if(null != changeType){
			switch (changeType) {
			case 1:
				changeTypeTitle="公司POS";
				break;
			case 2:
				changeTypeTitle="网银转账";
				break;
			case 3:
				changeTypeTitle="现金";
				break;
			case 4:
				changeTypeTitle="线上支付宝";
				break;
			case 5:
				changeTypeTitle="线上微信";
				break;
			case 6:
				changeTypeTitle="线上银联";
				break;
			case 7:
				changeTypeTitle="线上银联";
				break;
			case 8:
				changeTypeTitle="订单微信";
				break;
			case 9:
				changeTypeTitle="订单银联";
				break;
			case 10:
				changeTypeTitle="信用额度";
				break;
			case 11:
				changeTypeTitle="CRM积分";
				break;
			default:
				break;
			}
		}
		
		String type = null;
		if(changeTypeTitle.equals("信用额度")){
			type = "CREDIT";
		}else if(changeTypeTitle.equals("CRM积分")){
			type = "CRM";  
		}else{
			type ="PREPAY";
		}
		
		if (null != user) {
			Double userCashBalance = user.getCashBalance();
			Double userUnCashBalance = user.getUnCashBalance();
			if (null == userCashBalance) {
				userCashBalance = 0.00;
			}
			if (null == userUnCashBalance) {
				userUnCashBalance = 0.00;
			}
			if (null == unCashBalance) {
				unCashBalance = 0.00;
			}
			if (null == cashBalance) {
				cashBalance = 0.00;
			}
//			Double subCashBalance = cashBalance - userCashBalance;
//			Double subUnCashBalance = unCashBalance - userUnCashBalance;
			user.setCashBalance(userCashBalance + cashBalance);
			if (cashBalance.doubleValue() != 0.00) {
				TdBalanceLog log = new TdBalanceLog();
				log.setUserId(user.getId());
				log.setUsername(user.getUsername());
				log.setMoney(cashBalance);
				log.setType(2L);
				log.setCreateTime(new Date());
				log.setFinishTime(log.getCreateTime());
				log.setIsSuccess(true);
				log.setReason("管理员修改调整("+description+")");
				log.setDetailReason(description);
				log.setBalance(user.getCashBalance());
				log.setBalanceType(1L);
				log.setOperator(managerUsername);
				if(null != userOrderNumber){
					log.setUserOrderNumber(userOrderNumber);
				}else{
					log.setUserOrderNumber("NULL");
				}
				if(null != transferTime){
					log.setTransferTime(transferTime);
				}else{
					log.setTransferTime("NULL");
				}
				try {
					log.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				log.setDiySiteId(user.getUpperDiySiteId());
				log.setCityId(user.getCityId());
				log.setCashLeft(user.getCashBalance());
				log.setUnCashLeft(user.getUnCashBalance());
				log.setAllLeft(user.getBalance());
				tdBalanceLogService.save(log);
			}
			
			user.setUnCashBalance(userUnCashBalance + unCashBalance);
			if (unCashBalance.doubleValue() != 0.00) {
				TdBalanceLog log = new TdBalanceLog();
				log.setUserId(user.getId());
				log.setUsername(user.getUsername());
				log.setMoney(unCashBalance);
				log.setType(2L);
				log.setCreateTime(new Date());
				log.setFinishTime(log.getCreateTime());
				log.setIsSuccess(true);
				log.setReason("管理员修改调整("+description+")");
				log.setDetailReason(description);
				log.setBalance(user.getUnCashBalance());
				log.setBalanceType(2L);
				log.setOperator(managerUsername);
				if(null != userOrderNumber){
					log.setUserOrderNumber(userOrderNumber);
				}else{
					log.setUserOrderNumber("NULL");
				}
				if(null != transferTime){
					log.setTransferTime(transferTime);
				}else{
					log.setTransferTime("NULL");
				}
				try {
					log.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				log.setDiySiteId(user.getUpperDiySiteId());
				log.setCityId(user.getCityId());
				log.setCashLeft(user.getCashBalance());
				log.setUnCashLeft(user.getUnCashBalance());
				log.setAllLeft(user.getBalance());
				tdBalanceLogService.save(log);
			}
			user.setBalance(user.getCashBalance() + user.getUnCashBalance());
			tdUserService.save(user);
			
			TdChangeBalanceLog log = new TdChangeBalanceLog();
			if (null != user) {
				log.setUsername(user.getUsername());
				log.setRealName(user.getRealName());
				log.setDiySiteTitle(user.getDiyName());
				log.setManager(manager.getUsername());
				log.setIsSuccess(true);
				log.setOperateTime(new Date());
				try {
					log.setOperateIp(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				log.setRemark(remark);
				tdChangeBalanceLogService.save(log);
			}
		}
		
		// 生成单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		String sDate = sdf.format(now);
		Random random = new Random();
		String orderNum = null;
		
		// 在此抛接口
		if (cashBalance > 0) {
			Integer suiji = random.nextInt(900) + 100;
			orderNum = sDate + suiji;
			// 生成充值单
			TdRecharge recharge = new TdRecharge();
			recharge.setUsername(user.getUsername());
			recharge.setUserId(user.getId());
			recharge.setNumber("CZ" + orderNum);
			recharge.setTotalPrice(cashBalance);
			recharge.setTypeId(-1L);
			recharge.setTypeTitle(changeTypeTitle);
			recharge.setCreateTime(new Date());
			recharge.setFinishTime(recharge.getCreateTime());
			recharge.setStatusId(2L);
			tdReChargeService.save(recharge);
			tdPriceCountService.saveCashReceiptAndSendToEBS(recharge,user);
		} else if (cashBalance < 0) {
			Integer suiji = random.nextInt(900) + 100;
			orderNum = sDate + suiji;
			// 生成提现单
			TdDeposit deposit = new TdDeposit();
			deposit.setUsername(user.getUsername());
			deposit.setUserId(user.getId());
			deposit.setNumber("TX" + orderNum);
			deposit.setMoney(cashBalance * (-1));
			deposit.setIsAgree(true);
			deposit.setIsRemit(true);
			deposit.setCreateTime(new Date());
			deposit.setAgreeTime(deposit.getCreateTime());
			deposit.setRemitTime(deposit.getRemitTime());
			deposit = tdDepositService.save(deposit);
			
			TdCashRefundInf cashRefundInf = tdCommonService.createCashRefundInfoAccordingToDeposit(deposit, user, changeTypeTitle,type);
			
			tdInterfaceService.ebsWithObject(cashRefundInf, INFTYPE.CASHREFUNDINF);
		}
		
		if (unCashBalance > 0) {
			Integer suiji = random.nextInt(900) + 100;
			orderNum = sDate + suiji;
			// 生成充值单
			TdRecharge recharge = new TdRecharge();
			recharge.setUsername(user.getUsername());
			recharge.setUserId(user.getId());
			recharge.setNumber("CZ" + orderNum);
			recharge.setTotalPrice(unCashBalance);
			recharge.setTypeId(-1L);
			recharge.setTypeTitle(changeTypeTitle);
			recharge.setCreateTime(new Date());
			recharge.setFinishTime(recharge.getCreateTime());
			recharge.setStatusId(2L);
			tdReChargeService.save(recharge);
			tdPriceCountService.saveCashReceiptAndSendToEBS(recharge,user);
		} else if (unCashBalance < 0) {
			Integer suiji = random.nextInt(900) + 100;
			orderNum = sDate + suiji;
			TdDeposit deposit = new TdDeposit();
			deposit.setUsername(user.getUsername());
			deposit.setUserId(user.getId());
			deposit.setNumber("TX" + orderNum);
			deposit.setMoney(unCashBalance * (-1));
			deposit.setIsAgree(true);
			deposit.setIsRemit(true);
			deposit.setCreateTime(new Date());
			deposit.setAgreeTime(deposit.getCreateTime());
			deposit.setRemitTime(deposit.getRemitTime());
			deposit = tdDepositService.save(deposit);
			TdCashRefundInf cashRefundInf = tdCommonService.createCashRefundInfoAccordingToDeposit(deposit, user, changeTypeTitle,"PREPAY");
			tdInterfaceService.ebsWithObject(cashRefundInf, INFTYPE.CASHREFUNDINF);
		}
		
//		this.saveCashReceiptAndSendToEBS(recharge,user);
		
		return "redirect:/Verwalter/user/balance/change/list";
	}
	
	@RequestMapping(value = "/log/list")
	public String userBalanceChangeLogList(Integer page, Integer size, String keywords, Long roleId, Long userType,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId,
			ModelMap map, HttpServletRequest req, Long cityCode, Long diyCode) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		// 获取管理员管辖城市
		List<TdCity> cityList = new ArrayList<TdCity>();
		// 获取管理员管辖门店
		List<TdDiySite> diyList = new ArrayList<TdDiySite>();

		// 管理员获取管辖的城市和门店
		tdDiySiteRoleService.userRoleCityAndDiy(cityList, diyList, username);

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSearch")) {
				page = 0;
			}
		}

		// 修改城市刷新门店列表
		if (cityCode != null) {
			// 需要删除的diy
			List<TdDiySite> diyRemoveList = new ArrayList<TdDiySite>();
			for (TdDiySite tdDiySite : diyList) {
				if (!cityCode.equals(tdDiySite.getRegionId())) {
					diyRemoveList.add(tdDiySite);
					if (tdDiySite.getId() == diyCode) {
						diyCode = null;
					}
				}
			}
			diyList.removeAll(diyRemoveList);
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
		}

		if (null != keywords) {
			keywords = keywords.trim();
		}

		map.addAttribute("cityList", cityList);
		map.addAttribute("diySiteList", diyList);
		map.addAttribute("cityCode", cityCode);
		map.addAttribute("diyCode", diyCode);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("roleId", roleId);
		map.addAttribute("userType", userType);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		Page<TdChangeBalanceLog> log_page = null;

		if (null == roleId) {
			// 获取管辖门店id列表
			List<Long> roleDiyIds = new ArrayList<Long>();
			if (diyList != null && diyList.size() > 0) {
				for (TdDiySite diy : diyList) {
					roleDiyIds.add(diy.getId());
				}
			}
			// 获取分页数据
			log_page = tdChangeBalanceLogService.findAll(page, size);
		}

		map.addAttribute("log_page", log_page);

		return "/site_mag/change_balance_log";
	}
	
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
