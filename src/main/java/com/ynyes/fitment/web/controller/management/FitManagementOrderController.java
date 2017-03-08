package com.ynyes.fitment.web.controller.management;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping(value = "/Verwalter/fitment/order", produces = "text/html;charset=utf-8")
public class FitManagementOrderController {

	@Autowired
	private TdOrderService tdOrderService;

	@RequestMapping(value = "/list")
	public String fitmentOrderList(String keywords, String orderStartTime, String orderEndTime, Integer page,
			Integer size, Long statusId, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			Long[] listId, Integer[] listChkId, ModelMap map, HttpServletRequest request) {
		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
		}

		statusId = null == statusId ? 0L : statusId;

		map.addAttribute("order_page",
				tdOrderService.findAllAddConditionDeliveryType(keywords, orderStartTime, orderEndTime, null, null, null,
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

		return "/fitment/management/order_list";
	}
}
