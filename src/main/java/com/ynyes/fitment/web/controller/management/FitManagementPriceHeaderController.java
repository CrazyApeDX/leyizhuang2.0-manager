package com.ynyes.fitment.web.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.fitment.core.constant.Global;
import com.ynyes.fitment.foundation.entity.FitPriceHeader;
import com.ynyes.fitment.foundation.service.FitPriceHeaderService;

@Controller
@RequestMapping(value = "/Verwalter/fitment/price/header")
public class FitManagementPriceHeaderController {

	@Autowired
	private FitPriceHeaderService fitPriceHeaderService;
	
	@RequestMapping(value = "/list", produces = "text/html;charset=utf-8")
	public String priceHeaderList(ModelMap map, Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE) {
		
		if (null != __EVENTTARGET) {
			switch (__EVENTTARGET) {
			case "btnPage":
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
				break;
			}
		}
		
		Page<FitPriceHeader> headerPage = null;
		page = null == page ? Global.DEFAULT_PAGE : page;
		size = null == size ? Global.DEFAULT_SIZE : size;
		try {
			headerPage = this.fitPriceHeaderService.findAll(page, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("headerPage", headerPage);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		return "/fitment/management/price_header_list";
	}
}
