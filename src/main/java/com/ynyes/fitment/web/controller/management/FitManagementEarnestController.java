package com.ynyes.fitment.web.controller.management;

import java.util.Date;

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

}
