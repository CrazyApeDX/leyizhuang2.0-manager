package com.ynyes.fitment.web.controller.management;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.fitment.core.constant.CreditOperator;
import com.ynyes.fitment.core.constant.Global;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitCreditChangeLogService;
import com.ynyes.fitment.foundation.service.FitPromotionMoneyLogService;
import com.ynyes.lyz.entity.TdChangeBalanceLog;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.entity.TdSmsAccount;
import com.ynyes.lyz.service.TdChangeBalanceLogService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdSmsAccountService;
import com.ynyes.lyz.util.SiteMagConstant;


@Controller
@RequestMapping(value = "/Verwalter/fitment/wallet")
public class FitManagementWalletController {

	@Autowired
	private FitCompanyService fitCompanyService;
	
	@Autowired
	private TdManagerService tdManagerService;
	
	@Autowired
	private TdSettingService tdSettingService;
	
	@Autowired
	private TdCityService tdRegionService;
	
	@Autowired
	private TdSmsAccountService tdSmsAccountService;
	
	@Autowired
	private TdChangeBalanceLogService tdChangeBalanceLogService;
	
	@Autowired
	private FitPromotionMoneyLogService fitPromotionMoneyLogService;
	
	@Autowired
	private FitCreditChangeLogService fitCreditChangeLogService;
	
	@Autowired
	private TdCityService tdCityService;
	
	@RequestMapping(value = "/list")
	public String companyList(HttpServletRequest req, ModelMap map, String keywords, Integer page, Integer size, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE) {

		if (null != __EVENTTARGET) {
			switch (__EVENTTARGET) {
			case "btnPage":
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
				break;
			}
		}

		Page<FitCompany> companyPage = null;
		page = null == page ? Global.DEFAULT_PAGE : page;
		size = null == size ? Global.DEFAULT_SIZE : size;
		try {
			companyPage = this.fitCompanyService.findCompany(page, size, keywords);

		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("companyPage", companyPage);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		return "/fitment/management/wallet_list";
	}
	
	
	@RequestMapping(value = "/goUpdate", produces = "text/html;charset=utf-8")
	public String companyEdit(Long id, ModelMap map) {
		FitCompany company = null;
		try {
			company = this.fitCompanyService.findOne(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("company", company);
		return "/fitment/management/update_wallet";
	}
	
	
	@RequestMapping(value = "/manager/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> managerCheck(HttpServletRequest req, String password, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		
		String manager = (String) req.getSession().getAttribute("manager");
		if (null == manager) {
			res.put("message", "未能成功获取到登录管理员的信息，操作失败");
			return res;
		} else {
			FitCompany company = this.fitCompanyService.findOne(id);
			TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(manager);
			if (null == tdManager) {
				res.put("message", "未能成功获取到登录管理员的信息，操作失败");
				return res;
			} else if (null != tdManager.getErrorCount() && 0 == tdManager.getErrorCount().intValue()) {
				res.put("message", "您已经输入错误3次，暂不可修改装饰公司钱包金额，请联系超级管理员");
				return res;
			} else if (!tdManager.getPassword().equals(password)) {
				tdManager.setErrorCount(tdManager.getErrorCount() - 1);
				tdManagerService.save(tdManager);
				if (0 == tdManager.getErrorCount().intValue()) {
					res.put("message", "管理员密码错误，操作失败，您已经输入错误3次，暂不可修改装饰公司钱包金额，请联系超级管理员");
					// 在此给管理员发短信
					TdSetting setting = tdSettingService.findTopBy();
					String phones = setting.getInfPhone();
					if (null != phones && !"".equalsIgnoreCase(phones)) {
						String[] allPhones = phones.split(",");
						for (String phone : allPhones) {
							this.sendSmsCaptcha(req, phone, 
									"管理员" + tdManager.getUsername() + "修改装饰公司" + company.getName() 
											+ "现金返利时，密码输入错误超过3次，为保证用户账户信息安全，现关闭管理员修改装饰公司钱包金额权限", 
											company.getSobId());
						}
					}
					
					
					// 在此存储错误操作信息
					TdChangeBalanceLog log = new TdChangeBalanceLog();
					if (null != company) {
						log.setUsername(company.getName());
						log.setRealName(company.getName());
						log.setDiySiteTitle(company.getCode());
						log.setManager(manager);
						log.setIsSuccess(false);
						log.setOperateTime(new Date());
						try {
							log.setOperateIp(InetAddress.getLocalHost().getHostAddress());
						} catch (UnknownHostException e) {
							e.printStackTrace();
						}
						log.setRemark("密码输入错误3次，锁定管理员修改装饰公司钱包金额功能");
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
	
	private void sendSmsCaptcha(HttpServletRequest req, String phone, String message, Long sobId) {
		String content = null;
		try {
			content = URLEncoder.encode(message, "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
		}

		TdCity region = tdRegionService.findBySobIdCity(sobId);
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
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String userBalanceChangeSave(HttpServletRequest req, Double money, 
			String remark, String writtenRemarks, String code, String arrivalTime ) {
		String managerUsername = (String) req.getSession().getAttribute("manager");
		TdManager manager = tdManagerService.findByUsernameAndIsEnableTrue(managerUsername);
		if (null == manager) {
			return "redirect:/Verwalter/login";
		}
		
		FitCompany company = this.fitCompanyService.findByCode(code);
		if (null == company.getPromotionMoney() || "".equals(company.getPromotionMoney())) {
			company.setPromotionMoney(0D);
		}
		
		if (null == company.getCredit() || "".equals(company.getCredit())) {
			company.setCredit(0D);
		}
		
		if (null == company.getWalletMoney() || "".equals(company.getWalletMoney())){
			company.setWalletMoney(0D);
		}
		
		TdCity region = tdRegionService.findBySobIdCity(company.getSobId());
		
		FitCreditChangeLog log = new FitCreditChangeLog();
		
		log.setBeforeChange(company.getPromotionMoney());
		log.setAfterChange(company.getCredit());
		log.setMoney(money);
		log.setCreateTime(new Date());
		log.setChangeTime(new Date());
		log.setReferenceNumber(log.initManagerOperateNumber());
		log.setOperatorType(CreditOperator.MANAGER);
		log.setType("钱包充值");
		log.setOperatorId(manager.getId());
		log.setOperatorUserName(manager.getUsername());
		log.setRemark(remark);
		log.setCompanyTitle(company.getName());
		log.setCompanyId(company.getId());
		log.setCompanyCode(company.getCode());
		log.setArrivalTime(arrivalTime);
		log.setWrittenRemarks(writtenRemarks);
		log.setCity(region.getCityName());
		log.setAfterChangePromotion(company.getPromotionMoney());
		log.setDistinguish(2);
		log.setAfterChangeWallet(company.getWalletMoney() + money);
		
		company.setWalletMoney(company.getWalletMoney() + money);
		try {
			this.fitCompanyService.save(company);
			this.fitCreditChangeLogService.save(log);
			if (money > 0) {
				this.fitPromotionMoneyLogService.doReceipt(company, money, log.getReferenceNumber());
			} else {
				this.fitPromotionMoneyLogService.doRefund(company, money, log.getReferenceNumber());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/Verwalter/fitment/wallet/list";
	}
	
	@RequestMapping(value="/wallet_detail")
	public String earnestCouponDetail(String city, String companyCode,String keywords,String type, String startTime, String endTime, Integer page,
			Integer size,  String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			ModelMap map, HttpServletRequest request) {
		
		if (null == page || page < 0) {
			page = 0;
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} 
		}
		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
		}
		List<TdCity> cityList = this.tdCityService.findAll();
		Page<FitCreditChangeLog> balance_page= null;
		List<FitCompany> companyList = new ArrayList<FitCompany>();
		TdCity tdCity = null;
		try {
			if (null == city || "".equals(city)) {
				companyList = fitCompanyService.findAll();
			} else {
				tdCity = this.tdCityService.findByCityName(city);
				companyList = fitCompanyService.findBySobId(tdCity.getSobIdCity());
			}
			balance_page = this.fitCreditChangeLogService.queryList(startTime, endTime, city, companyCode, keywords, type, page, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map.addAttribute("balance_page", balance_page);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("companyList", companyList);
		map.addAttribute("cityList", cityList);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("companyCode", companyCode);
		map.addAttribute("type", type);
		map.addAttribute("startTime", startTime);
		map.addAttribute("endTime", endTime);
		map.addAttribute("city", city);

		return "/fitment/management/wallet_money_detail_list";
	}
	
}
