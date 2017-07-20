package com.ynyes.fitment.web.controller.management;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.fitment.core.constant.CreditOperator;
import com.ynyes.fitment.core.constant.Global;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitCreditChangeLogService;
import com.ynyes.fitment.foundation.service.biz.BizCreditChangeLogService;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping(value = "/Verwalter/fitment/earnest")
public class FitManagementEarnestController {

	@Autowired
	private FitCompanyService fitCompanyService;

	@Autowired
	private TdCityService tdCityService;
	
	@Autowired
	private TdManagerService tdManagerService;
	
	@Autowired
	private FitCreditChangeLogService fitCreditChangeLogService;
	
	@Autowired
	private BizCreditChangeLogService bizCreditChangeLogService;

	/**
	 * 跳转到信用金列表
	 * 
	 * @param req
	 * @param map
	 * @param page
	 * @param size
	 * @param __EVENTTARGET
	 * @param __EVENTARGUMENT
	 * @param __VIEWSTATE
	 * @return
	 */
	@RequestMapping(value = "/list", produces = "text/html;charset=utf-8")
	public String companyList(HttpServletRequest req, ModelMap map, Integer page, Integer size, String __EVENTTARGET,
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
			companyPage = this.fitCompanyService.findAll(page, size);

		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("companyPage", companyPage);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		return "/fitment/management/earnest_list";
	}

	/**
	 * 跳转到信用金变更页面
	 * 
	 * @param id
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/update", produces = "text/html;charset=utf-8")
	public String updateEarnest(Long id, HttpServletRequest req, ModelMap map) {
		String manager = (String) req.getSession().getAttribute("manager");
		if (null == manager) {
			return "redirect:/Verwalter/login";
		}
		FitCompany company = this.fitCompanyService.findOne(id);
		Long sobId = company.getSobId();

		TdCity tdCity = tdCityService.findBySobIdCity(sobId);

		map.addAttribute("tdCity", tdCity);
		map.addAttribute("company", company);
		map.addAttribute("id", id);
		return "/fitment/management/update_earnest";
	}

	@RequestMapping(value = "/save/update", method = RequestMethod.POST)
	public String saveUpdate(HttpServletRequest req, ModelMap map, String companyCode, String companyName,
			String changeType, Double money, String transferTime, String remark, String password,
			String cityName,Long id) {
		String managerUsername = (String) req.getSession().getAttribute("manager");
		TdManager manager = tdManagerService.findByUsernameAndIsEnableTrue(managerUsername);
		if(null == manager){
			return"redirect:/Verwalter/login";
		}
		
		FitCompany company = fitCompanyService.findOne(id);
		if (null == company.getPromotionMoney() || "".equals(company.getPromotionMoney())) {
			company.setPromotionMoney(0D);
		}
		
		if (null == company.getCredit() || "".equals(company.getCredit())) {
			company.setCredit(0D);
		}
		
		FitCreditChangeLog log = new FitCreditChangeLog();
		log.setBeforeChange(company.getCredit());
		log.setAfterChange(company.getCredit() + money);
		log.setMoney(money);
		log.setCreateTime(new Date());
		log.setChangeTime(new Date());
		log.setReferenceNumber(log.initManagerOperateNumber());
		log.setOperatorType(CreditOperator.MANAGER);
		log.setType("信用金充值");
		log.setOperatorId(manager.getId());
		log.setOperatorUserName(manager.getUsername());
		log.setRemark(changeType);
		log.setCompanyTitle(company.getName());
		log.setCompanyId(company.getId());
		log.setCompanyCode(company.getCode());
		log.setArrivalTime(transferTime);
		log.setWrittenRemarks(remark);
		log.setCity(cityName);
		log.setAfterChangePromotion(company.getPromotionMoney());
		log.setDistinguish(0);
		company.setCredit(company.getCredit() + money);
		
		try {
			this.fitCompanyService.save(company);
			this.fitCreditChangeLogService.save(log);
			this.bizCreditChangeLogService.creditMoney(manager, company, money, remark, changeType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/Verwalter/fitment/earnest/list";
	}
	
	
	@RequestMapping(value="/earnest_coupon_detail")
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

		return "/fitment/management/earnest_coupon_log_list";
	}

}
