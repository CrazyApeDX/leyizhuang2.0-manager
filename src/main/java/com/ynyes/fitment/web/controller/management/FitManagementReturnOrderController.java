package com.ynyes.fitment.web.controller.management;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdManagerRole;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdManagerRoleService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdReturnNoteService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping(value = "/Verwalter/fitment/retuenOrder", produces = "text/html;charset=utf-8")
public class FitManagementReturnOrderController {
	
	@Autowired
	private TdManagerService tdManagerService;
	
	@Autowired
	private TdManagerRoleService tdManagerRoleService;
	
	@Autowired
	TdManagerLogService tdManagerLogService;
	
	@Autowired
	TdReturnNoteService tdReturnNoteService;
	
	@Autowired
	private FitCompanyService fitCompanyService;
	
	// 列表
	@RequestMapping(value = "/list")
	public String list(String company,String keywords, String orderStartTime, String orderEndTime, Integer page, Integer size, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId, Double[] listSortId,
			ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		TdManagerRole tdManagerRole = null;
		if (tdManager != null && tdManager.getRoleId() != null) {
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
		}
		if (tdManagerRole == null) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} 
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);




		Page<TdReturnNote> returnNotePage = tdReturnNoteService.searchFitReturnList(keywords, orderStartTime,
				orderEndTime, company, size, page);
		map.addAttribute("returnNote_page", returnNotePage);
		// 循环获取用户名称
		List<FitCompany> companyList = new ArrayList<FitCompany>();
		try {
			companyList = fitCompanyService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("orderStartTime", orderStartTime);
		map.addAttribute("orderEndTime", orderEndTime);
		map.addAttribute("company", company);
		map.addAttribute("companyList", companyList);

		return "/fitment/management/return_order_list";
	}
	
	

}
