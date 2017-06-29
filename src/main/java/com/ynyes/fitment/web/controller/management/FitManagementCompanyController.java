package com.ynyes.fitment.web.controller.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.fitment.core.constant.Global;
import com.ynyes.fitment.core.constant.LoginSign;
import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.core.entity.persistent.table.TableEntity.OriginType;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitPriceHeader;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitPriceHeaderService;
import com.ynyes.fitment.foundation.service.biz.BizCreditChangeLogService;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdManagerService;

@Controller
@RequestMapping(value = "/Verwalter/fitment/company")
public class FitManagementCompanyController {

	@Autowired
	private FitCompanyService fitCompanyService;

	@Autowired
	private FitPriceHeaderService fitPriceHeaderService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private BizCreditChangeLogService bizCreditChangeLogService;

	@Autowired
	private TdManagerService tdManagerService;

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
		return "/fitment/management/company_list";
	}

	@RequestMapping(value = "/edit/{id}", produces = "text/html;charset=utf-8")
	public String companyEdit(@PathVariable Long id, ModelMap map) {
		FitCompany company = null;
		List<FitPriceHeader> lyzPriceHeaderList = null;
		List<FitPriceHeader> lsPriceHeaderList = null;
		List<FitPriceHeader> yrPriceHeaderList = null;
		List<TdCity> cityList = null;
		try {
			company = this.fitCompanyService.findOne(id);
			lyzPriceHeaderList = this.fitPriceHeaderService.findActivePriceHeaderByProductType("LYZ");
			lsPriceHeaderList = this.fitPriceHeaderService.findActivePriceHeaderByProductType("LS");
			yrPriceHeaderList = this.fitPriceHeaderService.findActivePriceHeaderByProductType("YR");
			cityList = this.tdCityService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("company", company);
		map.addAttribute("lyzPriceHeaderList", lyzPriceHeaderList);
		map.addAttribute("lsPriceHeaderList", lsPriceHeaderList);
		map.addAttribute("yrPriceHeaderList", yrPriceHeaderList);
		map.addAttribute("cityList", cityList);
		return "/fitment/management/company_edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String companySave(FitCompany company) {
		try {
			if (null == company.getId()) {
				company.setCreateOrigin(OriginType.ADD);
			}
			this.fitCompanyService.save(company);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/Verwalter/fitment/company/list";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String companyDelete(@PathVariable Long id) {
		try {
			this.fitCompanyService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/Verwalter/fitment/company/list";
	}

	@RequestMapping(value = "/name/validate/{id}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, String> companyNameValidate(@PathVariable Long id, String param) {
		Map<String, String> res = new HashMap<>();
		res.put("status", "n");
		try {
			Boolean validate = this.fitCompanyService.validateRepeatCompanyByName(param, id);
			if (validate) {
				res.put("info", "该名称的装饰公司已经存在");
			} else {
				res.put("status", "y");
			}
		} catch (Exception e) {
			res.put("info", "出现意外的错误，请稍后重试");
			e.printStackTrace();
		}
		return res;
	}

	@RequestMapping(value = "/code/validate/{id}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, String> companyCodeValidate(@PathVariable Long id, String param) {
		Map<String, String> res = new HashMap<>();
		res.put("status", "n");
		try {
			Boolean validate = this.fitCompanyService.validateRepeatCompanyByCode(param, id);
			if (validate) {
				res.put("info", "该编码的装饰公司已经存在");
			} else {
				res.put("status", "y");
			}
		} catch (Exception e) {
			res.put("info", "出现意外的错误，请稍后重试");
			e.printStackTrace();
		}
		return res;
	}

	@RequestMapping(value = "/credit", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public ClientResult companyCredit(HttpServletRequest request, Long id, Double credit) {
		try {
			String manageUsername = (String) request.getSession().getAttribute(LoginSign.MANAGER_SIGN.toString());
			TdManager manager = tdManagerService.findByUsernameAndIsEnableTrue(manageUsername);
			FitCompany company = this.fitCompanyService.findOne(id);
			this.bizCreditChangeLogService.creditAction(manager, company, credit, "");
			return new ClientResult(ActionCode.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请稍后重试或联系管理员");
		}
	}
	
	@RequestMapping(value = "/promotionMoney", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public ClientResult companyPromotionMoney(HttpServletRequest request, Long id, Double promotionMoney) {
		try {
			String manageUsername = (String) request.getSession().getAttribute(LoginSign.MANAGER_SIGN.toString());
			TdManager manager = tdManagerService.findByUsernameAndIsEnableTrue(manageUsername);
			FitCompany company = this.fitCompanyService.findOne(id);
			this.bizCreditChangeLogService.managePromotionMoneyLog(manager, company, promotionMoney, "");
			return new ClientResult(ActionCode.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请稍后重试或联系管理员");
		}
	}
}
