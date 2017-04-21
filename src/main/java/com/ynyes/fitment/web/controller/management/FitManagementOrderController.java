package com.ynyes.fitment.web.controller.management;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping(value = "/Verwalter/fitment/order", produces = "text/html;charset=utf-8")
public class FitManagementOrderController {

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private FitCompanyService fitCompanyService;
	
	@RequestMapping(value = "/list")
	public String fitmentOrderList(String company,String keywords, String orderStartTime, String orderEndTime, Integer page,
			Integer size, Long statusId, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			Long[] listId, Integer[] listChkId, ModelMap map, HttpServletRequest request) {
		
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

		statusId = null == statusId ? 0L : statusId;
		
		List<FitCompany> companyList = new ArrayList<FitCompany>();
		try {
			companyList = fitCompanyService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map.addAttribute("order_page",
				tdOrderService.findAllAddConditionDeliveryType2(company,keywords, orderStartTime, orderEndTime, null, null, null,
						null, null, null, null, null, statusId, null, null, null, null, null, size, page, true));
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("statusId", statusId);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("orderStartTime", orderStartTime);
		map.addAttribute("orderEndTime", orderEndTime);
		map.addAttribute("company", company);
		map.addAttribute("companyList", companyList);

		return "/fitment/management/order_list";
	}
}
