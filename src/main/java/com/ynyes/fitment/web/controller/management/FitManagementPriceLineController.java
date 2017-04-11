package com.ynyes.fitment.web.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.fitment.core.constant.Global;
import com.ynyes.fitment.foundation.entity.FitPriceLine;
import com.ynyes.fitment.foundation.service.FitPriceLineService;

@Controller
@RequestMapping(value = "/Verwalter/fitment/price/line")
public class FitManagementPriceLineController {

	@Autowired
	private FitPriceLineService fitPriceLineService;

	@RequestMapping(value = "/list/{headerId}", produces = "text/html;charset=utf-8")
	public String priceLineList(@PathVariable Long headerId, Integer page, Integer size, ModelMap map,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE) {

		if (null != __EVENTTARGET) {
			switch (__EVENTTARGET) {
			case "btnPage":
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
				break;
			}
		}

		Page<FitPriceLine> linePage = null;
		page = null == page ? Global.DEFAULT_PAGE : page;
		size = null == size ? Global.DEFAULT_SIZE : size;
		try {
			linePage = this.fitPriceLineService.findByHeaderId(headerId, page, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("linePage", linePage);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("headerId", headerId);
		return "/fitment/management/price_line_list";
	}
}
